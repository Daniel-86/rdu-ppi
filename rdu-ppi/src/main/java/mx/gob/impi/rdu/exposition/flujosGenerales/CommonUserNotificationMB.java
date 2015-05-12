/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.gob.impi.rdu.exposition.flujosGenerales;

import java.io.File;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;
import mx.gob.impi.rdu.dto.PromoventeDto;
import mx.gob.impi.rdu.exposition.SesionRDU;
import static mx.gob.impi.rdu.exposition.flujosGenerales.CargaNotificacionMB.validarCodbarrasSigappi;
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
    
    private String searchedTitle;
    private List<SolicitudInteresados> persistedAndSubscribed;
    private List<Notification> viewNots;
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
//        FacesContext context = FacesContext.getCurrentInstance();
//        HttpSession session = (HttpSession) context.getExternalContext().getSession(false);
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
//            selectedN = (Notification[]) viewNots.toArray();
        relations = flujosgralesViewService.listTiposRelacion();
    }
    
    public String findRecord() {

        String msgError = "";
        Notification requested;
//        NotificacionView requested = new NotificacionView();
        List<KfContenedores> dummyList;
        KfContenedores record;

        if (isAlreadyPresent(viewNots, searchedTitle)) {
            msgError = "El expediente con título " + searchedTitle + " ya está cargado en la tabla";
        } else if (searchedTitle != null && !searchedTitle.isEmpty() && validarCodbarrasSigappi(searchedTitle)) {
//            currentNotifications = (List<Notification>) flujosgralesViewService.findAllByUser(1234567891L);
            dummyList = flujosgralesViewService.selectKfContenedoresByTitle(searchedTitle);
            if (dummyList != null && dummyList.size() > 0) {
                requested = new Notification();
                record = dummyList.get(0);

                requested.setTitle(record.getTitle());
                requested.setPc(record.getPerson());
                requested.setUserId(requestingUser.getId_promovente());
                requested.setUsertype(relationType);
                requested.setLastUpdated(new Date());

                viewNots.add(requested);
            } else {
                msgError = "Expediente no encontrado";
            }
//            requested = flujosgralesViewService.findByTitle(this.searchedTitle);
        } else {
            msgError = "El ID " + this.searchedTitle + " no es valido";
        }
        return msgError;
    }

    public boolean isAlreadyPresent(List<Notification> notificationsInView, String id) {
        boolean result = false;
        for (Notification notification : notificationsInView) {
            if (notification.getTitle().equals(id)) {
                result = true;
                break;
            }
        }
        return result;
    }

    public void updateNotificationsPrefs() {
        List<SolicitudInteresados> notsInView = buildFromView(viewNots);
        List<SolicitudInteresados> notsToPersist = new ArrayList<>();
        List<SolicitudInteresados> notsToUpdate = new ArrayList<>();
        for (SolicitudInteresados noti : notsInView) {
            boolean isPersisted = false;
            boolean isSelected = false;
            for (Notification n : selected) {
                if ((n.getTitle() == null ? n.getTitle() == null : n.getTitle().equals(noti.getTitle())) 
                        && Objects.equals(n.getUserId(), noti.getCodInteresado())) {
                    isSelected = true;
                    break;
                }
            }
            for (SolicitudInteresados pers : persistedAndSubscribed) {
                if ((pers.getTitle() == null ? noti.getTitle() == null : pers.getTitle().equals(noti.getTitle())) 
                        && Objects.equals(pers.getCodInteresado(), noti.getCodInteresado())) {
                    isPersisted = true;
                    break;
                }
            }
            if (!isPersisted && isSelected) {
                notsToPersist.add(noti);
            }
            if (isPersisted && !isSelected) {
                noti.setSecuencia(0);
                notsToUpdate.add(noti);
            }
        }
//        notsToPersist.removeAll(persisted);
        for (SolicitudInteresados n : notsToPersist) {
            flujosgralesViewService.insert(n);
        }
        persistedAndSubscribed.addAll(notsToPersist);
        
        for(SolicitudInteresados n: notsToUpdate) {
            flujosgralesViewService.updateNotificationSubscription(n.getTitle(), requestingUser.getId_promovente(), 0);
            persistedAndSubscribed.remove(n);
        }
        persistedNoSubscribed.addAll(notsToUpdate);
        
        String basura = "para poner un breakpoint";
        
        generateDocument();
    }

    private List<SolicitudInteresados> buildFromView(List<Notification> viewNotifications) {
        List<SolicitudInteresados> notifications = new ArrayList<>();
        SolicitudInteresados noti;
        for (Notification n : viewNotifications) {
            noti = new SolicitudInteresados();
            noti.setTitle(n.getTitle());
            noti.setCodInteresado(requestingUser.getId_promovente());
            noti.setFechaModificacion(new Date());
            noti.setSecuencia(999);
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

            viewNotifications.add(noti);
        }
        return viewNotifications;
    }
    
    public void dropSelected() {
        if (selected != null && selected.length > 0) {
            for (Notification notView : selected) {
                viewNots.remove(notView);
            }
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Eliminacion correcta"));
        } else {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("No hay archivos seleccionados"));
        }
    }

    public void dropAll() {
        if (selected!= null && selected.length > 0) {
            viewNots.clear();
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Eliminacion correcta"));
        } else {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("No hay archivos seleccionados"));
        }
    }
    
    public PromoventeDto obtenerPromovente(SesionRDU obtSession) {
        if (null != obtSession) {
            PromoventeDto promovente = obtSession.getPromovente();
            if (promovente != null) {
                return promovente;
            } else {
                return null;
            }
        } else {
            return null;
        }
    }

    private void generateDocument() {
        String sourceFileName = basePath + "/content/reportes/user_notifications.jasper";
//        String sourceFileName = request.getRealPath("") + "/content/reportes/newReport.jasper";
        String printFileName;
        
        Map parameters = new HashMap();
        parameters.put("logo", basePath + "/content/imagenes/firma_impi.jpg");
        parameters.put("fullName", requestingUser.getNombre() + " " + requestingUser.getApaterno() + " " +  requestingUser.getAmaterno());
        parameters.put("rfc", requestingUser.getRfc());
        parameters.put("email", requestingUser.getEmail());
        parameters.put("fullAddress", requestingUser.getCalle_numero() + " " + requestingUser.getNumero_interior());
        
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
}
