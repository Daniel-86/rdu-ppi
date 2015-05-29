/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.gob.impi.rdu.exposition.notifications;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
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
import mx.gob.impi.rdu.exposition.flujosGenerales.CommonUserNotificationMB;
import mx.gob.impi.rdu.service.FlujosGralesViewServiceImpl;
import mx.gob.impi.rdu.service.MailService;
import mx.gob.impi.rdu.util.ContextUtils;
import mx.gob.impi.sigappi.persistence.model.KfContenedores;
import mx.gob.impi.sigappi.persistence.model.Notification;
import mx.gob.impi.sigappi.persistence.model.SolicitudRevision;
import mx.gob.impi.sigappi.persistence.model.SolicitudWeb;
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
public class CaptureNotificationsMB {

    public static enum ID_TYPE {

        TITLE, PC, UNKNOWN
    }

    private static final String TITLE_PATTERN = "PI/[A-Z]{1}/[0-9]{4}/[0-9]{6}";
    private static final String PC_PATTERN = "[A-Z.]+\\d+/\\d{4}\\([A-Z](-\\d+)?\\)\\d+";
    private static final int SUBSCRIBED = -999;
    private static final int UNSUBSCRIBED = -1;
    private static final int DELETED = -2;

    private String recordId;
    private String fullName;
    private String rfc;
    private String username;
    private Integer userId;
    private String fullContact;
    private String fullAddress;
    private List<Notification> notificationsInView;
    private List<SolicitudRevision> notificationsInSession;
    private PromoventeDto user;
    private List<TiposRelacion> relations;
    private String basePath;
    private String documentName;
    private Integer sessionId;

    public String getRecordId() {
        return recordId;
    }

    public void setRecordId(String recordId) {
        this.recordId = recordId;
    }

    public List<Notification> getNotificationsInView() {
        return notificationsInView;
    }

    public void setNotificationsInView(List<Notification> notificationsInView) {
        this.notificationsInView = notificationsInView;
    }

    public String getFullName() {
        return fullName;
    }

    public String getRfc() {
        return rfc;
    }

    public String getUsername() {
        return username;
    }

    public Integer getUserId() {
        return userId;
    }

    public String getFullContact() {
        return fullContact;
    }

    public String getFullAddress() {
        return fullAddress;
    }

    public List<TiposRelacion> getRelations() {
        return relations;
    }

    public String getDocumentName() {
        return documentName;
    }
    
    
    
    
    @PostConstruct
    public void init() {
        SesionRDU obtSession = ContextUtils.obtenerSesionUsuario();
        user = obtenerPromovente(obtSession);
        HttpServletRequest request = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
        userId = user.getId_promovente();
        username = user.getLogin();
        fullName = Utils.getFullName(user);
        rfc = user.getRfc();
        fullAddress = Utils.getFullAddress(user);
        fullContact = Utils.getFullContact(user);
        
        basePath = request.getRealPath("");
        
        Integer sessionParam = Integer.valueOf(request.getParameter("sessionId"));
        
        if(sessionParam != null)
            openSession(sessionParam);
        else {
            notificationsInSession = new ArrayList<>();
            notificationsInView = new ArrayList<>();
        }

        relations = flujosgralesViewService.listTiposRelacion();
    }
    
    public void findRecord() {

        String msgError;
        Notification requested;
        List<KfContenedores> dummyList = null;
        KfContenedores record;

        if (!Utils.isEmptyString(recordId) && isValidId(recordId)) {
            ID_TYPE idType = idTypeIs(recordId);
            if(Utils.isAlreadyPresent(notificationsInView, recordId)) {
                msgError = "El expediente con ";
                msgError += idType == ID_TYPE.TITLE? "title ": idType == ID_TYPE.PC? "pc ": "";
                msgError += "ya está cargado en la tabla";
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, null, msgError));
                return;
            }
            
            record = flujosgralesViewService.findKfContenedoresByTitleOrPc(recordId);
            if(record != null) {
                requested = new Notification();
                requested.setTitle(record.getTitle());
                requested.setPc(record.getPerson());
                requested.setUserId(userId);
                notificationsInView.add(requested);
            } else {
                msgError = "El expediente con ";
                msgError += idType == ID_TYPE.TITLE ? "title " : idType == ID_TYPE.PC ? "pc " : "";
                msgError += "no existe";
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, null, msgError));
                return;
            }
        } else
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, null, recordId + " no es una ID válido"));
    }

    @ManagedProperty(value = "#{flujosgralesViewService}")
    private FlujosGralesViewServiceImpl flujosgralesViewService;

    @ManagedProperty(value = "#{mailService}")
    private MailService mailService;

    public void setMailService(MailService mailService) {
        this.mailService = mailService;
    }

    public void setFlujosgralesViewService(FlujosGralesViewServiceImpl flujosgralesViewService) {
        this.flujosgralesViewService = flujosgralesViewService;
    }


 
    public void save() {
        List<SolicitudRevision> toDelete = new ArrayList<>(notificationsInSession);
        for(Notification n: notificationsInView) {
            SolicitudRevision s = notificationToSolicitudRevision(n);
            if(notificationsInSession.contains(s)) {
                s.setIdSolicitud(sessionId);
                flujosgralesViewService.update(s);
            }
            else {
                if(sessionId == null) { 
                    sessionId = flujosgralesViewService.nextSolicitudSequence();
                    SolicitudWeb document = new SolicitudWeb();
                    document.setIdSolicitud(sessionId);
                    document.setIdPromovente(userId);
                    document.setTipoDocumento(0);
                    document.setIdStatus(0);
                    flujosgralesViewService.insert(document);
//                    sessionId = document.getIdSolicitud();
                    s.setIdSolicitud(sessionId);
                    Integer asdf = flujosgralesViewService.insert(s);
                }
                else {
                    s.setIdSolicitud(sessionId);
                    flujosgralesViewService.append(s);
                }
                notificationsInSession.add(s); 
            }
        }
        toDelete.removeAll(notificationsInView);
        for(SolicitudRevision s: toDelete) {
            flujosgralesViewService.delete(s);
        }
        
    }
    
    public void reset() {
//        for(SolicitudRevision s: notificationsInSession) {
//            flujosgralesViewService.delete(s);
//        }
        notificationsInView.clear();
    }
    
    public void finish() {
        if(notificationsInView.size() > 0) {
            if(sessionId == null)
                save();
            SolicitudWeb document = flujosgralesViewService.findSolicitudWebBySession(sessionId);
            document.setIdStatus(1);
            flujosgralesViewService.update(document);
            for(SolicitudRevision s: notificationsInSession) {
                flujosgralesViewService.update(s);
            }
            generateDocument();
        }
    }



    private List<SolicitudRevision> convertNotificationsToSolicitudRevisions(List<Notification> viewNotifications) {
        List<SolicitudRevision> notifications = new ArrayList<>();
        SolicitudRevision noti;
        for (Notification n : viewNotifications) {
            n.setUserId(userId);
//            noti = new SolicitudInteresados(n);
            noti = notificationToSolicitudRevision(n);

            notifications.add(noti);
        }
        return notifications;
    }

    private SolicitudRevision notificationToSolicitudRevision(Notification n) {
        SolicitudRevision s = new SolicitudRevision();
        s.setTitle(n.getTitle());
        s.setSecuencia(n.getSequence());
        s.setCodRelacion(n.getUsertype());
        s.setCveUsuario(n.getAuthorizedBy());
        s.setCodInteresado(n.getUserId());
        return s;
    }

    private List<Notification> buildNotsFromSols(List<SolicitudRevision> notifications) {
        return buildNotsFromSols(notifications, false);
    }

    private List<Notification> buildNotsFromSols(List<SolicitudRevision> notifications, boolean isPersisted) {
        List<Notification> viewNotifications = new ArrayList<>();
        Notification noti;
        for (SolicitudRevision s : notifications) {
            noti = buildNotFromSol(s, isPersisted);

            viewNotifications.add(noti);
        }
        return viewNotifications;
    }

    private Notification buildNotFromSol(SolicitudRevision s, boolean isPersisted) {
        Notification n = new Notification(s);
        n.setPc(flujosgralesViewService.selectKfContenedoresByTitle(s.getTitle()).get(0).getPerson());
        n.setUserTypeDescription(flujosgralesViewService.selectTiposRelacionByCodRelacion(s.getCodRelacion()).get(0).getParte());
        n.setPersisted(isPersisted);
        return n;
    }

    public PromoventeDto obtenerPromovente(SesionRDU obtSession) {
        return (null != obtSession ? obtSession.getPromovente() : null);
    }

    private void generateDocument() {
        String sourceFileName = basePath + "/content/reportes/user_notifications.jasper";
        String printFileName;

        Map parameters = new HashMap();
        parameters.put("logo", basePath + "/content/imagenes/firma_impi.jpg");
        parameters.put("fullName", fullName);
        parameters.put("rfc", rfc);
        parameters.put("email", user.getEmail());
        parameters.put("fullAddress", fullAddress);
        parameters.put("username", username);
        parameters.put("userId", userId);

        JRBeanCollectionDataSource notifications = new JRBeanCollectionDataSource(notificationsInView);

        try {
            printFileName = JasperFillManager.fillReportToFile(sourceFileName, parameters, notifications);

            if (printFileName != null) {
                documentName = "/notificaciones_suscripcion_" + userId + ".pdf";
                JasperExportManager.exportReportToPdfFile(printFileName, basePath + documentName);
            }
        } catch (JRException e) {
            e.printStackTrace();
        }
    }

    public void deleteDocument() {
        File pdfFile = new File(basePath + documentName);
        if (pdfFile.exists()) {
            pdfFile.delete();
        }
    }

    public boolean isValidId(String id) {
        return (Pattern.matches(TITLE_PATTERN, id.toUpperCase()) || Pattern.matches(PC_PATTERN, id.toUpperCase()));
    }

    private ID_TYPE idTypeIs(String id) {
        ID_TYPE idType = ID_TYPE.UNKNOWN;
        if (Pattern.matches(TITLE_PATTERN, id.toUpperCase())) {
            idType = ID_TYPE.TITLE;
        } else if (Pattern.matches(PC_PATTERN, id.toUpperCase())) {
            idType = ID_TYPE.PC;
        }
        return idType;
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

    public void viewDocument() {
        generateDocument();
    }

    public boolean existsDocument() {
        File file = new File(basePath + documentName);
        return (notificationsInSession != null 
                && notificationsInSession.size() > 0
                && file.exists()
                && !file.isDirectory());
    }

    public void emailDocument() {
        HashMap properties = new HashMap();
        String[] to = {"exoskeletol@gmail.com", "exoskeletol@outlook.com"};
//        properties.put("to", "exoskeletol@gmail.com");
        properties.put("to", to);
        List filePaths = new ArrayList();
        filePaths.add(basePath + documentName);
        filePaths.add(basePath + "/historial.pdf");
//        properties.put("file", basePath + documentName);
        properties.put("files", filePaths);
        try {
            mailService.sendMail(properties);
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(
                    FacesMessage.SEVERITY_INFO, null, "Se te ha enviado el documento por email"));
        } catch (Exception ex) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, null, ex.getMessage()));
        }
    }

//    public void deleteRow(ActionEvent e) {
//        UIComponent uic = e.getComponent();
//        String asdf = "";
//    }
    public void deleteRow(Notification n) {
        if (notificationsInSession.contains(n)) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "", "No puedes eliminar esa fila"));
        } else {
            notificationsInView.remove(n);
        }
        String asdf = "";
    }

    public void duplicateRow(Notification n) {
        Notification nn = new Notification(n);
        int indx = notificationsInView.indexOf(n);
        notificationsInView.add(indx + 1, nn);
    }
    
    public void openSession(Integer sessionId) {
        this.sessionId = sessionId;
        
        notificationsInSession = flujosgralesViewService.findSolicitudRevisionByUserAndSession(userId, sessionId);
        if (notificationsInSession == null && notificationsInSession.size() > 0) {
            notificationsInSession = new ArrayList<>();
        }
        notificationsInView = buildNotsFromSols(notificationsInSession, true);
    }
    
}
    
