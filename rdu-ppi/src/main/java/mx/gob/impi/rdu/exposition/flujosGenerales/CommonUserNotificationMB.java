/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.gob.impi.rdu.exposition.flujosGenerales;

import java.io.File;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.regex.Pattern;
import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
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
    
    @ManagedProperty(value = "#{flujosgralesViewService}")
    private FlujosGralesViewServiceImpl flujosgralesViewService;
    
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

    public void setFlujosgralesViewService(FlujosGralesViewServiceImpl flujosgralesViewService) {
        this.flujosgralesViewService = flujosgralesViewService;
    }
    
    @PostConstruct
    public void init() {
        SesionRDU obtSession = ContextUtils.obtenerSesionUsuario();
        requestingUser = obtenerPromovente(obtSession);
        HttpServletRequest request = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
        basePath = request.getRealPath("");
        
        persistedAndSubscribed = flujosgralesViewService.selectSolicitudInteresadosByCodInteresado(requestingUser.getId_promovente());
        viewNots = buildNotsFromSols(new ArrayList(persistedAndSubscribed));
        selected = viewNots != null ? new Notification[viewNots.size()] : null;
        int i = 0;
        for (Notification n : viewNots) {
            selected[i++] = n;
        }

        persistedNoSubscribed = flujosgralesViewService.selectSolicitudInteresadosByCodInteresadoAndSecuencia(requestingUser.getId_promovente(), 0);
        viewNotsCanceled = buildNotsFromSols(new ArrayList(persistedNoSubscribed));
            
        relations = flujosgralesViewService.listTiposRelacion();
    }
    
    public void findRecord() {

        String msgError = "";
        Notification requested = null;
        List<KfContenedores> dummyList = null;
        KfContenedores record;

        if (isAlreadyPresent(viewNots, searchedTitle)) {
            msgError = "El expediente con título o PC " + searchedTitle + " ya está cargado en la tabla";
        } else if (searchedTitle != null && !searchedTitle.isEmpty() && isValidId(searchedTitle)) {
            ID_TYPE idType = idTypeIs(searchedTitle);
            if(isAlreadyPresent(viewNotsCanceled, searchedTitle)) {
                for(Notification n: viewNotsCanceled) {
                    if(((idType == ID_TYPE.TITLE && n.getTitle().equals(searchedTitle)) 
                            || (idType == ID_TYPE.PC && n.getPc().equals(searchedTitle)))
                            && n.getUserId() == requestingUser.getId_promovente()) {
                        requested = n;
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
                    msgError = "Expediente no encontrado";
                }
            }
            if(requested != null) 
                viewNots.add(requested);
        } else {
            msgError = "El ID " + this.searchedTitle + " no es valido";
        }
        
        if(!"".equals(msgError)) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, msgError, msgError));
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
        List<SolicitudInteresados> subscribedNoChange = new ArrayList<>(persistedAndSubscribed);
        List<SolicitudInteresados> unsubscribedNoChange = new ArrayList<>(persistedNoSubscribed);
        List<SolicitudInteresados> totalPersisted = new ArrayList<>(persistedAndSubscribed);
        totalPersisted.addAll(persistedNoSubscribed);
        
        searchedTitle = "";
        for (SolicitudInteresados noti : notsInView) {
            boolean isPersisted = false;
            boolean isSelected = false;
            boolean isSubscribed = false;
            for (Notification n : selected) {
                if ((n.getTitle() == null ? n.getTitle() == null : n.getTitle().equals(noti.getTitle())) 
                        && Objects.equals(n.getUserId(), noti.getCodInteresado())) {
                    isSelected = true;
                    break;
                }
            }
            for (SolicitudInteresados pers : totalPersisted) {
                if ((pers.getTitle() == null ? noti.getTitle() == null : pers.getTitle().equals(noti.getTitle())) 
                        && Objects.equals(pers.getCodInteresado(), noti.getCodInteresado())) {
                    isPersisted = true;
                    if(noti.getSecuencia() == 999)
                        isSubscribed = true;
                    break;
                }
            }
            if (!isPersisted && isSelected) {
                notsToPersist.add(noti);
            }
            if(isPersisted && isSelected && !isSubscribed) {
                noti.setSecuencia(999);
                notsToSubscribe.add(noti);
            }
            if (isPersisted && !isSelected && isSubscribed) {
                noti.setSecuencia(0);
                notsToCancel.add(noti);
            }
        }
        
        for (SolicitudInteresados n : notsToPersist) {
            flujosgralesViewService.insert(n);
        }
        persistedAndSubscribed.addAll(notsToPersist);
        
        for(SolicitudInteresados n: notsToCancel) {
            flujosgralesViewService.updateNotificationSubscription(n.getTitle(), requestingUser.getId_promovente(), 0);
            for(SolicitudInteresados s: subscribedNoChange) {
                if(s.getTitle().equals(n.getTitle()) && Objects.equals(s.getCodInteresado(), n.getCodInteresado()))
                    persistedAndSubscribed.remove(s);
            }
        }
        persistedNoSubscribed.addAll(notsToCancel);
        
        for(SolicitudInteresados n: notsToSubscribe) {
            flujosgralesViewService.updateNotificationSubscription(n.getTitle(), requestingUser.getId_promovente(), 999);
            for(SolicitudInteresados s: unsubscribedNoChange) {
                if (s.getTitle().equals(n.getTitle()) && Objects.equals(s.getCodInteresado(), n.getCodInteresado()))
                    persistedNoSubscribed.remove(s);
            }
        }
        persistedAndSubscribed.addAll(notsToSubscribe);
        
        viewNots = buildNotsFromSols(persistedAndSubscribed);
        viewNotsCanceled = buildNotsFromSols(persistedNoSubscribed);
        
        if(viewNots.size() > 0)
            generateDocument();
        else
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("No tienes suscripciones a expedientes"));
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

    private List<Notification> buildNotsFromSols(List<SolicitudInteresados> persistedNotifications) {
        List<Notification> viewNotifications = new ArrayList<>();
        Notification noti;
        for (SolicitudInteresados s : persistedNotifications) {
            noti = new Notification();
            noti.setTitle(s.getTitle());
            noti.setPc(flujosgralesViewService.selectKfContenedoresByTitle(s.getTitle()).get(0).getPerson());
            noti.setUserId(s.getCodInteresado());
            noti.setUsertype(s.getCodRelacion());
            noti.setSequence(s.getSecuencia());
            noti.setAuthorizedBy(s.getCveUsuario());
            noti.setUserTypeDescription(flujosgralesViewService.selectTiposRelacionByCodRelacion(s.getCodRelacion()).get(0).getParte());

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
}
