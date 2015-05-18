/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.gob.impi.rdu.exposition.flujosGenerales;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Pattern;
import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;
import mx.gob.impi.rdu.dto.PromoventeDto;
import mx.gob.impi.rdu.exposition.SesionRDU;
import mx.gob.impi.rdu.service.FlujosGralesViewServiceImpl;
import mx.gob.impi.rdu.util.ContextUtils;
import mx.gob.impi.sigappi.persistence.model.KfContenedores;
import mx.gob.impi.sigappi.persistence.model.SolicitudInteresados;
import mx.gob.impi.sigappi.persistence.model.TiposRelacion;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;

/**
 *
 * @author daniel
 */
@ManagedBean
@ViewScoped
public class CommonUserNotificationMB {
    
    public static enum ID_TYPE {
        TITLE, PC, UNKNOWN
    }

    private static final String TITLE_PATTERN = "PI/[A-Z]{1}/[0-9]{4}/[0-9]{6}";
    private static final String PC_PATTERN = "[A-Z.]+\\d+/\\d{4}\\([A-Z](-\\d+)?\\)\\d+";
    private static final int SUBSCRIBED = -999;
    private static final int UNSUBSCRIBED = -1;
    private static final int DELETED = -2;
    
    private String searchedTitle;
    private List<SolicitudInteresados> persistedAndSubscribed;
    private List<Notification> viewNots;
    private List<Notification> viewNotsCanceled;
    private PromoventeDto requestingUser;
    private Integer relationType;
    private Notification[] selected;
    private List<TiposRelacion> relations;
    private List<SolicitudInteresados> persistedNoSubscribed;
    private String documentUrl;
    private String basePath;
    private String documentName;
    private String btnText = "Generar forma";
    private boolean error = false;
    private boolean generateDoc = false;
    
    @ManagedProperty(value = "#{flujosgralesViewService}")
    private FlujosGralesViewServiceImpl flujosgralesViewService;
    
    public boolean isGenerateDoc() {
        return generateDoc;
    }
    
    public boolean isError() {
        return error;
    }
    public List<Notification> getViewNotsCanceled() {
        return viewNotsCanceled;
    }

    public void setViewNotsCanceled(List<Notification> viewNotsCanceled) {
        this.viewNotsCanceled = viewNotsCanceled;
    }

    public List<Notification> getViewNots() {
        return viewNots;
    }

    public Integer getRelationType() {
        return relationType;
    }

    public void setViewNots(List<Notification> viewNots) {
        this.viewNots = viewNots;
    }

    public void setRelationType(Integer relationType) {
        this.relationType = relationType;
    }

    public Notification[] getSelected() {
        return selected;
    }

    public void setSelected(Notification[] selected) {
        this.selected = selected;
    }
    
    public String getSearchedTitle() {
        return searchedTitle;
    }

    public void setSearchedTitle(String id) {
        searchedTitle = id;
    }
    
    public List<TiposRelacion> getRelations() {
        return relations;
    }

    public void setRelations(List<TiposRelacion> relations) {
        this.relations = relations;
    }
    
    public String getDocumentUrl() {
        return documentUrl;
    }
    
    public String getDocumentName() {
        return documentName;
    }
    
    public String getBtnText() {
        return btnText;
    }

    public void setBtnText(String btnText) {
        this.btnText = btnText;
    }

    public void setFlujosgralesViewService(FlujosGralesViewServiceImpl flujosgralesViewService) {
        this.flujosgralesViewService = flujosgralesViewService;
    }
    
    @PostConstruct
    public void init() {
        SesionRDU obtSession = ContextUtils.obtenerSesionUsuario();
        requestingUser = obtenerPromovente(obtSession);
        HttpServletRequest request = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
        basePath = request.getRealPath("");
        
        persistedAndSubscribed = flujosgralesViewService.selectSolicitudInteresadosByCodInteresadoAndSecuencia(requestingUser.getId_promovente(), SUBSCRIBED);
        viewNots = buildNotsFromSols(new ArrayList(persistedAndSubscribed), true);
        selected = viewNots != null ? new Notification[viewNots.size()] : null;
        int i = 0;
        for (Notification n : viewNots) {
            selected[i++] = n;
        }

        persistedNoSubscribed = flujosgralesViewService.selectSolicitudInteresadosByCodInteresadoAndSecuencia(requestingUser.getId_promovente(), UNSUBSCRIBED);
        viewNotsCanceled = buildNotsFromSols(new ArrayList(persistedNoSubscribed), true);
            
        relations = flujosgralesViewService.listTiposRelacion();
    }
    
    public void findRecord() {

        String msgError;
        Notification requested = null;
        List<KfContenedores> dummyList = null;
        KfContenedores record;

        if (searchedTitle != null && !searchedTitle.isEmpty() && isValidId(searchedTitle)) {
            ID_TYPE idType = idTypeIs(searchedTitle);
            if(isAlreadyPresent(viewNotsCanceled, searchedTitle)) {
                for(Notification n: viewNotsCanceled) {
                    if(((idType == ID_TYPE.TITLE && n.getTitle().equals(searchedTitle)) 
                            || (idType == ID_TYPE.PC && n.getPc().equals(searchedTitle)))
                            && n.getUserId() == requestingUser.getId_promovente()) {
                        requested = new Notification(n);
                        break;
                    }
                }
            }
            else {
                if (idType == ID_TYPE.TITLE) {
                    dummyList = flujosgralesViewService.selectKfContenedoresByTitle(searchedTitle);
                } else if (idType == ID_TYPE.PC) {
                    dummyList = flujosgralesViewService.selectKfContenedoresByPC(searchedTitle);
                }
                if (dummyList != null && dummyList.size() > 0) {
                    requested = new Notification();
                    record = dummyList.get(0);

                    requested.setTitle(record.getTitle());
                    requested.setPc(record.getPerson());
                    requested.setUserId(requestingUser.getId_promovente());
                    requested.setUsertype(relationType);
                    requested.setLastUpdated(new Date());

                } else {
                    msgError = 
                            "No hay registro del expediente con " 
                            + (idType == ID_TYPE.TITLE? "title: ": idType == ID_TYPE.PC? "PC: ": "esto nunca debe mostrarse ") 
                            + searchedTitle;
                    FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(
                            FacesMessage.SEVERITY_WARN, 
                            "Expediente no encontrado", 
                            msgError));
                }
            }
            if(requested != null) 
                viewNots.add(requested);
        } else {
            msgError = "El ID " + this.searchedTitle + " no es valido";
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, msgError, ""));
        }
        
    }

    public boolean isAlreadyPresent(List<Notification> notificationsInView, String id) {
        boolean result = false;
        for (Notification notification : notificationsInView) {
            if (notification.getTitle().equals(id) || notification.getPc().equals(id)) {
                result = true;
                break;
            }
        }
        return result;
    }

    public void updateNotificationsPrefs() {
        List<SolicitudInteresados> notsInView = buildFromView(viewNots);
        List<SolicitudInteresados> notsToPersist = new ArrayList<>();
        List<SolicitudInteresados> notsToCancel = new ArrayList<>();
        List<SolicitudInteresados> notsToSubscribe = new ArrayList<>();
        List<SolicitudInteresados> totalPersisted = new ArrayList<>(persistedAndSubscribed);
        totalPersisted.addAll(persistedNoSubscribed);
        error = false;
        generateDoc = false;
        
        searchedTitle = "";
        for (SolicitudInteresados noti : notsInView) {
            if(noti.getCodRelacion() == null) {
                error = true; 
                break;}
            boolean isPersisted = false;
            boolean isSelected = false;
            boolean isSubscribed = false;
            boolean isAuthorized = false;
            for (Notification n : selected) {
                if ((n.getTitle() == null ? n.getTitle() == null : n.getTitle().equals(noti.getTitle())) 
                        && Objects.equals(n.getUserId(), noti.getCodInteresado()) 
                        && n.getUsertype().equals(noti.getCodRelacion())) {
                    isSelected = true;
                    break;
                }
            }
            if(totalPersisted.contains(noti))
                isPersisted = true;
            if(isPersisted && noti.getSecuencia() == SUBSCRIBED)
                isSubscribed = true;
            if(isPersisted && !isEmptyString(noti.getCveUsuario()))
                isAuthorized = true;
            
            if (!isPersisted && isSelected) {
                notsToPersist.add(noti);
            }
            if(isPersisted && isSelected && !isSubscribed) {
                noti.setSecuencia(SUBSCRIBED);
                notsToSubscribe.add(noti);
            }
            if (isPersisted && !isSelected && isSubscribed && isAuthorized) {
                noti.setSecuencia(UNSUBSCRIBED);
                notsToCancel.add(noti);
            }
            if(isPersisted && !isSelected && isSubscribed && !isAuthorized) {
                FacesContext.getCurrentInstance()
                        .addMessage(null, new FacesMessage(
                                FacesMessage.SEVERITY_WARN, 
                                "Operacion no permitida", "La notificacion con title " 
                                        + noti.getTitle() 
                                        + " no ha sido autorizada, no puedes cancelarla"));
                error = true;
                int i = selected.length;
                Notification nt = new Notification();
                nt.setTitle(noti.getTitle());
                nt.setPc(flujosgralesViewService.selectKfContenedoresByTitle(noti.getTitle()).get(0).getPerson());
                nt.setPersisted(true);
                nt.setSequence(noti.getSecuencia());
                nt.setUsertype(noti.getCodRelacion());
                nt.setAuthorizedBy(noti.getCveUsuario());
                nt.setUserId(noti.getCodInteresado());
                nt.setUserTypeDescription(flujosgralesViewService.selectTiposRelacionByCodRelacion(noti.getCodRelacion()).get(0).getParte());
                List<Notification> nArr = new ArrayList(Arrays.asList(selected));
                nArr.add(nt);
                selected = makeArray(nArr);
            }
        }
        
        if(error)
            return;
        
        for (SolicitudInteresados n : notsToPersist) {
            flujosgralesViewService.insert(n);
        }
        persistedAndSubscribed.addAll(notsToPersist);
        
        for(SolicitudInteresados n: notsToCancel) {
            flujosgralesViewService.updateNotificationSubscription(n.getTitle(), requestingUser.getId_promovente(), UNSUBSCRIBED);
        }
        persistedAndSubscribed.removeAll(notsToCancel);
        persistedNoSubscribed.addAll(notsToCancel);
        
        for(SolicitudInteresados n: notsToSubscribe) {
            flujosgralesViewService.updateNotificationSubscription(n.getTitle(), requestingUser.getId_promovente(), SUBSCRIBED);
        }
        persistedNoSubscribed.removeAll(notsToSubscribe);
        persistedAndSubscribed.addAll(notsToSubscribe);
        
        viewNots = buildNotsFromSols(persistedAndSubscribed, true);
        viewNotsCanceled = buildNotsFromSols(persistedNoSubscribed, true);
        
        if(notsToPersist.size() > 0) {
            generateDoc = true;
            generateDocument();
        }
        else if(persistedAndSubscribed.size() < 1) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("No tienes suscripciones a expedientes"));
        }
    }

    private List<SolicitudInteresados> buildFromView(List<Notification> viewNotifications) {
        List<SolicitudInteresados> notifications = new ArrayList<>();
        SolicitudInteresados noti;
        for (Notification n : viewNotifications) {
            noti = new SolicitudInteresados();
            noti.setTitle(n.getTitle());
            noti.setCodInteresado(requestingUser.getId_promovente());
            noti.setFechaModificacion(new Date());
            noti.setSecuencia(n.getSequence());
            noti.setCodRelacion(n.getUsertype());
            noti.setCveUsuario(n.getAuthorizedBy());

            notifications.add(noti);
        }
        return notifications;
    }

    private List<Notification> buildNotsFromSols(List<SolicitudInteresados> notifications) {
        return buildNotsFromSols(notifications, false);
    }
    
    private List<Notification> buildNotsFromSols(List<SolicitudInteresados> notifications, boolean isPersisted) {
        List<Notification> viewNotifications = new ArrayList<>();
        Notification noti;
        for (SolicitudInteresados s : notifications) {
            noti = new Notification();
            noti.setTitle(s.getTitle());
            noti.setPc(flujosgralesViewService.selectKfContenedoresByTitle(s.getTitle()).get(0).getPerson());
            noti.setUserId(s.getCodInteresado());
            noti.setUsertype(s.getCodRelacion());
            noti.setSequence(s.getSecuencia());
            noti.setAuthorizedBy(s.getCveUsuario());
            noti.setUserTypeDescription(flujosgralesViewService.selectTiposRelacionByCodRelacion(s.getCodRelacion()).get(0).getParte());
            noti.setPersisted(isPersisted);

            viewNotifications.add(noti);
        }
        return viewNotifications;
    }
    
    public PromoventeDto obtenerPromovente(SesionRDU obtSession) {
        return (null != obtSession? obtSession.getPromovente(): null);
    }

    private void generateDocument() {
        String sourceFileName = basePath + "/content/reportes/user_notifications.jasper";
        String printFileName;
        
        Map parameters = new HashMap();
        parameters.put("logo", basePath + "/content/imagenes/firma_impi.jpg");
        parameters.put("fullName", getFullName());
        parameters.put("rfc", requestingUser.getRfc());
        parameters.put("email", requestingUser.getEmail());
        parameters.put("fullAddress", getFullAddress());
        parameters.put("username", getUsername());
        parameters.put("userId", getUserId());
        
        JRBeanCollectionDataSource notifications = new JRBeanCollectionDataSource(viewNots);
        
        try {
            printFileName = JasperFillManager.fillReportToFile(sourceFileName, parameters, notifications);
            
            if(printFileName != null) {
                documentName = "/notificaciones_suscripcion_" + requestingUser.getId_promovente() + ".pdf";
                JasperExportManager.exportReportToPdfFile(printFileName, basePath + documentName);
            }
        } catch(JRException e) {
        }
    }
    
    public void deleteDocument() {
        File pdfFile = new File(basePath + documentName);
        if(pdfFile.exists())
            pdfFile.delete();
    }
    
    public boolean isValidId(String id) {
        return (Pattern.matches(TITLE_PATTERN, id.toUpperCase()) || Pattern.matches(PC_PATTERN, id.toUpperCase()));
    }
    
    private ID_TYPE idTypeIs(String id) {
        ID_TYPE idType = ID_TYPE.UNKNOWN;
        if(Pattern.matches(TITLE_PATTERN, id.toUpperCase())) idType = ID_TYPE.TITLE;
        else if(Pattern.matches(PC_PATTERN, id.toUpperCase())) idType = ID_TYPE.PC;
        return idType;
    }
    
    public String getFullName() {
        return capitalize(requestingUser.getNombre()) + " " + capitalize(requestingUser.getApaterno()) + " " + capitalize(requestingUser.getAmaterno());
    }
    
    private String capitalize(String s) {
        return s.length() == 0? "": s.substring(0, 1).toUpperCase() + s.substring(1).toLowerCase();
    }
    
    public String getFullAddress() {
        String fullAddress = requestingUser.getCalle_numero() 
                + " " + requestingUser.getNumero_exterior()
                + (isEmptyString(requestingUser.getNumero_interior())? "": " No. ext " + requestingUser.getNumero_interior())
                + ", Colonia " + capitalize(requestingUser.getColonia())
                + ", Código postal " + requestingUser.getCodigo_postal()
                + ", en " + capitalize(requestingUser.getDescMunicipio())
                + ", " + capitalize(requestingUser.getDescEstado());
        return fullAddress;
    }
    
    private boolean isEmptyString(String s) {
        return s == null || s.trim().equals("") || s.trim().equals("null");
    }
    
    public String getRFC() {
        return requestingUser.getRfc();
    }
    
    public String getFullContact() {
        String fullContact = (isEmptyString(requestingUser.getEmail())? "": "<strong>Email: </strong>" + requestingUser.getEmail())
                + (isEmptyString(requestingUser.getTelefono())? "": " <strong>Teléfono: </strong>" + requestingUser.getTelefono())
                + (isEmptyString(requestingUser.getFax())? "": " <strong>Fax: </strong>" + requestingUser.getFax());
        return fullContact;
    }
    
    public String getUsername() {
        return requestingUser.getLogin();
    }
    
    public Integer getUserId() {
        return requestingUser.getId_promovente();
    }
    
    public void markForDeletion() {
        error = false;
        List<SolicitudInteresados> notifications = buildFromView(new ArrayList<>(Arrays.asList(selected)));
        for(SolicitudInteresados s: notifications) {
            if(!isEmptyString(s.getCveUsuario())) {
                flujosgralesViewService.updateNotificationSubscription(s.getTitle(), s.getCodInteresado(), DELETED);
                persistedAndSubscribed.remove(s);
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(
                        FacesMessage.SEVERITY_INFO, 
                        "", 
                        "La notificación con title: " + s.getTitle() + " ha sido marcada para ser borrada"));
            }
            else {
                FacesContext.getCurrentInstance()
                        .addMessage(null, new FacesMessage(
                                        FacesMessage.SEVERITY_WARN,
                                        "Operacion no permitida", "La notificacion con title "
                                        + s.getTitle()
                                        + " no ha sido autorizada, no puedes eliminarla"));
                error = true;
            }
        }
        viewNots = buildNotsFromSols(persistedAndSubscribed, true);
    }
    
    public Notification[] makeArray(List<Notification> nots) {
        Notification [] select;
        select = nots != null ? new Notification[nots.size()] : null;
        int i = 0;
        for (Notification n : nots) {
            select[i++] = n;
        }
        return select;
    }
    
    public void downloadFile() {
        ExternalContext ec = FacesContext.getCurrentInstance().getExternalContext();
        byte[] data;
        try {
            data = Files.readAllBytes(Paths.get(basePath + documentName));
            ec.setResponseContentType("application / pdf");
            ec.setResponseHeader("Content-Disposition", "attachment; filename=\"" + documentName + "\"");
            ec.setResponseContentLength(data.length);
            ec.getResponseOutputStream().write(data);
            ec.getResponseOutputStream().flush();
            ec.getResponseOutputStream().close();
        } catch (IOException ex) {
            Logger.getLogger(CommonUserNotificationMB.class.getName()).log(Level.SEVERE, null, ex);
        }
        FacesContext.getCurrentInstance().responseComplete();
    }
    
    public int getSelectedCount() {
        return selected != null? selected.length: 0;
    }
    
    public void viewDocument() {
        generateDocument();
    }
    
    public boolean existDocument() {
        return (persistedAndSubscribed != null && persistedAndSubscribed.size() > 0);
    }
    
    public void addNotification(String s) {
        
    }
}
