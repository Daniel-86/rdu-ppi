/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.gob.impi.rdu.exposition.flujosGenerales;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;
import mx.gob.impi.rdu.exposition.SesionRDU;
import static mx.gob.impi.rdu.exposition.flujosGenerales.CargaNotificacionMB.validarCodbarrasSigappi;
import mx.gob.impi.rdu.service.FlujosGralesViewServiceImpl;
import mx.gob.impi.rdu.util.ContextUtils;
import mx.gob.impi.sigappi.persistence.model.KfContenedores;

/**
 *
 * @author daniel
 */
@ManagedBean
@ViewScoped
public class CommonUserNotificationMB {
    
    private String searchedTitle;
    private List<Notification> persistedNots;
    private List<Notification> viewNots;
    private Integer requestingUser;
    private Integer relationType;
    private List<Notification> selected;
    
    @ManagedProperty(value = "#{flujosgralesViewService}")
    private FlujosGralesViewServiceImpl flujosgralesViewService;

    public List<Notification> getPersistedNots() {
        return persistedNots;
    }

    public List<Notification> getViewNots() {
        return viewNots;
    }

    public Integer getRequestingUser() {
        return requestingUser;
    }

    public Integer getRelationType() {
        return relationType;
    }

    public void setPersistedNots(List<Notification> persistedNots) {
        this.persistedNots = persistedNots;
    }

    public void setViewNots(List<Notification> viewNots) {
        this.viewNots = viewNots;
    }

    public void setRequestingUser(Integer requestingUser) {
        this.requestingUser = requestingUser;
    }

    public void setRelationType(Integer relationType) {
        this.relationType = relationType;
    }

    public List<Notification> getSelected() {
        return selected;
    }

    public void setSelected(List<Notification> selected) {
        this.selected = selected;
    }
    
    public void init() {
        SesionRDU obtSession = ContextUtils.obtenerSesionUsuario();
//        usuarioCarga = obtenerPromovente(obtSession);
        FacesContext context = FacesContext.getCurrentInstance();
        HttpSession session = (HttpSession) context.getExternalContext().getSession(false);
        viewNots = new ArrayList<>();
    }
    
    public String getSearchedTitle() {return searchedTitle;}
    public void setSearchedTitle(String id) {searchedTitle = id;}
    
    public void setFlujosgralesViewService(FlujosGralesViewServiceImpl flujosgralesViewService) {
        this.flujosgralesViewService = flujosgralesViewService;
    }
    
    public String findRecord() {
        String msgError = "";
        List<Notification> currentNotifications = null;
        Notification requested;
//        NotificacionView requested = new NotificacionView();
        List<KfContenedores> dummyList;
        KfContenedores record;

        
        System.out.println("Entró en findRecord");
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
                requested.setUserId(requestingUser);
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

    }
    
    public void dropSelected() {
        if (selected != null && selected.size() > 0) {
            for (Notification notView : selected) {
                viewNots.remove(notView);
            }
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Eliminacion correcta"));
        } else {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("No hay archivos seleccionados"));
        }
    }

    public void dropAll() {
        if (selected!= null && selected.size() > 0) {
            viewNots.clear();
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Eliminacion correcta"));
        } else {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("No hay archivos seleccionados"));
        }
    }
}
