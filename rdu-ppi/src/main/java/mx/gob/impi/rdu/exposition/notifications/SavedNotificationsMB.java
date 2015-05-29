/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.gob.impi.rdu.exposition.notifications;

import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;
import mx.gob.impi.rdu.dto.PromoventeDto;
import mx.gob.impi.rdu.exposition.SesionRDU;
import mx.gob.impi.rdu.service.FlujosGralesViewServiceImpl;
import mx.gob.impi.rdu.service.MailService;
import mx.gob.impi.rdu.util.ContextUtils;
import mx.gob.impi.sigappi.persistence.model.Notification;
import mx.gob.impi.sigappi.persistence.model.SolicitudRevision;
import mx.gob.impi.sigappi.persistence.model.SolicitudWeb;

/**
 *
 * @author daniel
 */
@ManagedBean
@ViewScoped
public class SavedNotificationsMB {
    
    private List<SolicitudWeb> sessions;
    private PromoventeDto user;
    private Integer userId;

    public List<SolicitudWeb> getSessions() {
        return sessions;
    }

    public void setSessions(List<SolicitudWeb> sessions) {
        this.sessions = sessions;
    }
    
    
    @ManagedProperty(value = "#{flujosgralesViewService}")
    private FlujosGralesViewServiceImpl flujosgralesViewService;

    @ManagedProperty(value = "#{mailService}")
    private MailService mailService;

    public void setFlujosgralesViewService(FlujosGralesViewServiceImpl flujosgralesViewService) {
        this.flujosgralesViewService = flujosgralesViewService;
    }

    public void setMailService(MailService mailService) {
        this.mailService = mailService;
    }
    
    
    
    @PostConstruct
    public void init() {
        SesionRDU obtSession = ContextUtils.obtenerSesionUsuario();
        user = (null != obtSession ? obtSession.getPromovente() : null);
        HttpServletRequest request = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
        userId = user.getId_promovente();
        
        sessions = flujosgralesViewService.findAllSolicitudWebByUser(userId);
        if(sessions == null || sessions.size() <= 0)
            sessions = new ArrayList<>();
    }

    public Integer finishedStatus() {
        return SolicitudWeb.Status.FINISHED.getCode();
    }

    public Integer savedStatus() {
        return SolicitudWeb.Status.SAVED.getCode();
    }
    
    public void deleteSession(SolicitudWeb session) {
        
    }
    
    public void showRequestDocument(SolicitudWeb session) {
        
    }
    
    public void showExtraDocument(String documentName) {
        
    }
    
    public List<String> extraDocuments(SolicitudWeb session) {
        List extraDocuments = new ArrayList<>();
        return extraDocuments;
    }
    
}
