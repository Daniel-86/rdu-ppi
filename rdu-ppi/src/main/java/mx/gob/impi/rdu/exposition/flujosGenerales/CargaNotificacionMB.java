/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.gob.impi.rdu.exposition.flujosGenerales;

import com.mx.impi.vidoc.contenedor.webservice.*;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.logging.Level;
import java.util.regex.Pattern;
import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;
import javax.xml.ws.WebServiceRef;
import mx.gob.impi.pase.persistence.model.Promovente;
import mx.gob.impi.rdu.dataModel.NotificacionViewDM;
import mx.gob.impi.rdu.dto.PromoventeDto;
import mx.gob.impi.rdu.exposition.SesionRDU;
import mx.gob.impi.rdu.persistence.model.Notificacion;
import mx.gob.impi.rdu.service.FlujosGralesViewService;
import mx.gob.impi.rdu.service.FlujosGralesViewServiceImpl;
import mx.gob.impi.rdu.util.ContextUtils;
import mx.gob.impi.rdu.util.FileServicesUtil;
import mx.gob.impi.rdu.util.Util;
import mx.gob.impi.sigappi.persistence.model.KfAlmacenar;
import mx.gob.impi.sigappi.persistence.model.KfContenedores;
import mx.gob.impi.sigappi.persistence.model.KfFolios;
import mx.gob.impi.sigappi.persistence.model.KffoliosNotificacion;
import mx.gob.impi.sigappi.persistence.model.SolicitudInteresados;
import mx.gob.impi.sigappi.persistence.model.TiposRelacion;
import mx.gob.impi.sigmar.persistence.model.NotificacionView;
import org.apache.log4j.Logger;
import org.primefaces.event.FileUploadEvent;
import org.primefaces.model.DefaultStreamedContent;
import org.primefaces.model.StreamedContent;
import org.primefaces.model.UploadedFile;
import org.springframework.beans.BeanUtils;

/**
 *
 * @author oracle
 */
@ManagedBean
@ViewScoped
public class CargaNotificacionMB {
    @WebServiceRef(wsdlLocation = "WEB-INF/wsdl/localhost_8081/wsImpiDocContainer/wsImpiDocContainer.wsdl")
    private com.mx.impi.vidoc.contenedor.webservice.WsImpiDocContainer_Service service;
    

    Logger logger = Logger.getLogger(this.getClass());
    private List<Notificacion> notificaciones = new ArrayList<Notificacion>();
    private List<NotificacionView> notificacionesView = new ArrayList<NotificacionView>();
    NotificacionViewDM notificacionDM = null;
    NotificacionView notificacionesSelected[];
    List<NotificacionView> notificacionesSelect = new ArrayList<NotificacionView>();
    private List<Promovente> promoventes = new ArrayList<Promovente>();
    private Promovente promoventeSelected = new Promovente();
    private PromoventeDto usuarioCarga;
    private Integer idArea;
    private List<FileUploadEvent> files = new ArrayList<FileUploadEvent>();
    private String nombreArchivos = "";
    private String codbarrasAcuerdo = "";
    public String recordID = "";
    private String msgErrors;
    private boolean mostrarResumen;
    public static final String PATTERN_OFICION_SALIDA_PATENTES = "MX\\_[0-9]{4}\\_[0-9]{1,7}.pdf";
    public static final String PATTERN_CODBARRAS_SIGAPPI = "PI/[A-Z]{1}/[0-9]{4}/[0-9]{6}";
    @ManagedProperty(value = "#{flujosgralesViewService}")
    private FlujosGralesViewServiceImpl flujosgralesViewService;
    

    @PostConstruct
    public void init() {
        SesionRDU obtSession = ContextUtils.obtenerSesionUsuario();
        usuarioCarga = obtenerPromovente(obtSession);
        FacesContext context = FacesContext.getCurrentInstance();
        HttpSession session = (HttpSession) context.getExternalContext().getSession(false);
//        if (usuarioCarga.getId_perfil() == 22 || usuarioCarga.getId_perfil() == 42) {
            idArea = new Integer((String) session.getAttribute("area"));
            notificacionDM = new NotificacionViewDM(notificacionesView);
            promoventes = flujosgralesViewService.selectPromoventeByPerfil(obtenerPromovente(obtSession).getId_perfil() + 1);
            promoventes.add(flujosgralesViewService.buscaPromovente(Long.parseLong("30023")));
            if (promoventes != null && !promoventes.isEmpty()) {
                BeanUtils.copyProperties(promoventes.get(0), promoventeSelected);
            }
            
            persisted = flujosgralesViewService.selectSolicitudInteresadosByCodInteresado(usuarioCarga.getId_promovente());
            viewNots = buildNotsFromSols(new ArrayList(persisted));
//            viewNots = buildNotsFromSols(new ArrayList<SolicitudInteresados>());
//            viewNots = new ArrayList<>();
            selected = new ArrayList<>();
            relations = flujosgralesViewService.listTiposRelacion();
//            relations = new ArrayList<>();
            String basura = "para poner un breakpoint";
//        } else {
//            try {
//                context.getExternalContext().redirect("/rdu-web/content/restricted/firma/firmaerror.faces");
//            } catch (IOException ex) {
//                ex.printStackTrace();
//            }
//        }
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

    public void cargarArchivos(FileUploadEvent event) {
        if (event != null) {
            files.add(event);
            nombreArchivos += event.getFile().getFileName() + "; ";
        }
    }

    public void buscarAcuerdo() {
        msgErrors = "";
        mostrarResumen = false;
        msgErrors += buscaAcuerdo();
         
        if (msgErrors != null && msgErrors.length() > 0) {
            mostrarErrores(msgErrors, FacesContext.getCurrentInstance());
        }
        
        codbarrasAcuerdo = "";
    }
    public void cargarArchivo() {
        msgErrors = "";
        mostrarResumen = false;
        if (!files.isEmpty()) {
            for (FileUploadEvent file : files) {
                msgErrors += cargarArchivo(file);
            }
        } else {
            msgErrors += "Noy hay archivos cargados|";
        }
        if (msgErrors != null && msgErrors.length() > 0) {
            mostrarErrores(msgErrors, FacesContext.getCurrentInstance());
        }
        files.clear();
        nombreArchivos = "";
    }
    
    public StreamedContent descargaArchivo(byte[] bytes,String name) {
    InputStream is = new ByteArrayInputStream(bytes);
    name=name.replace("/", "_")+".pdf";
    System.out.println("Descargando el archivo ["+name+"], size file : "+bytes.length);
    StreamedContent file = new DefaultStreamedContent(is, "application/pdf", name);
    return file;
}

    public void cancelarArchivo() {
        nombreArchivos = "";
        files.clear();
    }
    
    
    public String buscaAcuerdo() {
        String msgError = "";
        //UploadedFile file = event.getFile();
        Notificacion not = null; // de rdu
        NotificacionView notView = new NotificacionView();
        KffoliosNotificacion kffoliosNotificacion=null;
        KfFolios kfFolios=null;
        KfAlmacenar kfAlmacenar=null;
        KfContenedores kfContenedores=null;
        if (this.codbarrasAcuerdo != null) {
            try {
                
                    if (validarCodbarrasSigappi(this.codbarrasAcuerdo)) {
                            //not = new Notificacion(file.getFileName().substring(0, file.getFileName().length() - 4), file.getFileName(), file.getContents());
                            not = new Notificacion(codbarrasAcuerdo);
                            Notificacion notifi = null;
                           
                                notifi = flujosgralesViewService.getNotificacionesByFolio(not.getFolio());
                            
                            if (notifi == null) {
                                
                                    try {
                                        kfFolios = !flujosgralesViewService.selectKfFoliosByCodbarras(not.getFolio()).isEmpty() ? flujosgralesViewService.selectKfFoliosByCodbarras(not.getFolio()).get(0): null;
                                        } catch (Exception nfe) {
                                        nfe.printStackTrace();
                                    }
                                
                                if (kfFolios != null) {
                                    //notView.setArchivo(not.getArchivo());
                                    if (!existeNotificacion(notificacionesView, kfFolios)) {
                                        
                                        
                                       
                                        DocFile docFile=null;
                                        
                                        try { // Call Web Service Operation
                                            WsImpiDocContainer port = service.getWsImpiDocContainerPort();
                                            // TODO initialize WS operation arguments here
                                            InputDocumentDocInfo inputDocument = new InputDocumentDocInfo();
                                            inputDocument.setUser("webmaster_vidoc@impi.gob.mx");
                                            inputDocument.setPassword("portal123");
                                            inputDocument.setCodbarras(not.getFolio());
                                            inputDocument.setIdArea(7);
                                            docFile = port.getDocumentFile(inputDocument);
                                            System.out.println("Result = "+docFile.getMensaje());
                                        } catch (Exception ex) {
                                             msgError += "El Acuerdo " + not.getFolio()  + " no se puede recuperar de UCM (ERROR)....|";
                                             logger.info("UCM (ERROR):"+ex.getMessage());
                                        }
                                        if(docFile!=null && docFile.getFile()!=null){
                                            notView.setArchivo(docFile.getFile().getFileContent());
                                            notView.setOficioSalida(kfFolios.getCodbarras());
                                            notView.setDescripcion(kfFolios.getDescription());
                                            notView.setFechaMovimiento(kfFolios.getFecha());
                                            //notView.setDenominacion(kfFolios.getPerson());
                                            notView.setTitular(kfFolios.getAnalista());
                                            kfAlmacenar = !flujosgralesViewService.selectKfAlmacenarByCodbarras(not.getFolio()).isEmpty() ? flujosgralesViewService.selectKfAlmacenarByCodbarras(not.getFolio()).get(0): null;
                                        
                                            if (kfAlmacenar != null) {
                                                kfContenedores = !flujosgralesViewService.selectKfContenedoresByTitle(kfAlmacenar.getTitle()).isEmpty() ? flujosgralesViewService.selectKfContenedoresByTitle(kfAlmacenar.getTitle()).get(0): null;
                                                if (kfAlmacenar != null) {
                                                    notView.setDenominacion(kfContenedores.getPc());
                                                }
                                            
                                                notView.setExpediente(kfAlmacenar.getTitle());
                                            }
                                            notificacionesView.add(notView);
                                            /*  idNotificacion,folio,idUsuarioCarga,fechaCarga,idUsuarioFirma,archivoNombre,idActivo,idEstatus,archivo;
                                            expediente,denominacion,titular,idArea,nombreExaminador;
                                            */
                                            not.setArchivo(docFile.getFile().getFileContent());
                                            not.setArchivoNombre(codbarrasAcuerdo.replace("/", "_"));
                                            not.setDenominacion(notView.getDenominacion());
                                            not.setDescripcion(notView.getDescripcion());
                                            not.setFechaCarga(notView.getFechaMovimiento());
                                            not.setExpediente(notView.getExpediente());
                                            not.setTitular(notView.getTitular());
                                            notificaciones.add(not);
                                        }else {
                                            msgError += "El Acuerdo " + not.getFolio()  + " no se puede recuperar de UCM....|";
                                        }
                                    } else {
                                        msgError += "El Acuerdo " + not.getFolio()  + " ya se ha cargado en la tabla....|";
                                    }
                                } else {
                                    msgError += "Folio de salida: " + not.getFolio() + " no encontrado....|";
                                }

                            
                    } else {
                        msgError += "El Acuerdo " + not.getFolio() + " ya existe en la base de datos....|";
                    }

                } else {
                    msgError += "El codbarras " + this.codbarrasAcuerdo + " no tiene un formato valido|";
                }

            } catch (IllegalArgumentException iae) {
                msgError += "Solamente se pueden cargar archivos *.pdf....|";
            }
        }
        return msgError;
    }

    
    public String cargarArchivo(FileUploadEvent event) {
        int area = 0;
        String msgError = "";
        UploadedFile file = event.getFile();
        Notificacion not = null;
        NotificacionView notView = null;
        if (file != null) {
            try {
                if (FileServicesUtil.checkMimeType(file.getFileName(), file.getContents())) {
                    area = validarOficioSalida(file.getFileName());
                    if (area == (idArea == 8 ? 1 : idArea == 20 ? 2 : -1)) {
                        if (!FileServicesUtil.validarArchivoProtegido(file.getContents())) {
                            not = new Notificacion(file.getFileName().substring(0, file.getFileName().length() - 4), file.getFileName(), file.getContents());
                            Notificacion notifi = null;
                            if (area == 2) {
                                notifi = flujosgralesViewService.getNotificacionesByFolio(not.getFolio().substring(0, 2) + "_" + new Long(not.getFolio().substring(3, 7)) + "_" + new Long(not.getFolio().substring(8)));
                            } else {
                                notifi = flujosgralesViewService.getNotificacionesByFolio(not.getFolio());
                            }
                            if (notifi == null) {
                                if (area == 1 && idArea == 8) {
                                    try {
                                        notView = !flujosgralesViewService.consultarNotificacionView(new Long(not.getFolio())).isEmpty() ? flujosgralesViewService.consultarNotificacionView(new Long(not.getFolio())).get(0) : null;
                                    } catch (NumberFormatException nfe) {
                                        nfe.printStackTrace();
                                    }
                                } else if (area == 2 && idArea == 20) {
                                    notView = !flujosgralesViewService.consultarNotificacionView(not.getFolio().substring(0, 2), new Long(not.getFolio().substring(3, 7)), new Long(not.getFolio().substring(8))).isEmpty() ? flujosgralesViewService.consultarNotificacionView(not.getFolio().substring(0, 2), new Long(not.getFolio().substring(3, 7)), new Long(not.getFolio().substring(8))).get(0) : null;
                                }
                                if (notView != null) {
                                    notView.setArchivo(not.getArchivo());
                                    if (!existeNotificacion(notificacionesView, notView)) {
                                        notificacionesView.add(notView);
                                        not.setDenominacion(notView.getDenominacion());
                                        not.setExpediente(notView.getExpediente());
                                        not.setTitular(notView.getTitular());
                                        notificaciones.add(not);
                                    } else {
                                        msgError += "El archivo " + file.getFileName() + " ya se ha cargado en la tabla....|";
                                    }
                                } else {
                                    msgError += "Folio de salida: " + not.getFolio() + " no encontrado....|";
                                }
                            } else {
                                msgError += "El archivo " + file.getFileName() + " ya existe en la base de datos....|";
                            }
                        } else {
                            msgError += "Archivo " + file.getFileName() + " protegido....|";
                        }
                    } else {
                        msgError += "El archivo " + file.getFileName() + " no tiene un formato valido|";
                    }
                } else {
                    msgError += file.getFileName() + " no es un archivo pdf....|";
                }
            } catch (IllegalArgumentException iae) {
                msgError += "Solamente se pueden cargar archivos *.pdf....|";
            } catch (IOException ioe) {
                ioe.printStackTrace();
            }
        }
        return msgError;
    }

    public static int validarOficioSalida(String oficioSalida) {
        if (Pattern.matches(PATTERN_OFICION_SALIDA_PATENTES.toLowerCase(), oficioSalida.toLowerCase())) {
            return 2;
        } else {
            return 0;
        }
    }
    public static boolean validarCodbarrasSigappi(String codbarras) {
        if (Pattern.matches(PATTERN_CODBARRAS_SIGAPPI.toUpperCase(), codbarras.toUpperCase())) {
            return true;
        } else {
            return false;
        }
    }

    public void mostrarErrores(String errors, FacesContext ctx) {
        for (String error : errors.split("\\|")) {
            ctx.addMessage(null, new FacesMessage(error));
        }
    }

    public boolean existeNotificacion(List<NotificacionView> notificacionesView, NotificacionView notificacionView) {
        boolean result = false;
        for (NotificacionView notView : notificacionesView) {
            if (notView.getOficioSalida().equals(notificacionView.getOficioSalida())) {
                result = true;
                break;
            }
        }
        return result;
    }
    
    public boolean existeNotificacion(List<NotificacionView> notificacionesView, KfFolios kfFolios) {
        boolean result = false;
        for (NotificacionView notView : notificacionesView) {
            if (notView.getOficioSalida().equals(kfFolios.getCodbarras())) {
                result = true;
                break;
            }
        }
        return result;
    }
    
    public boolean existeNotificacion(List<NotificacionView> notificacionesView, KfContenedores kfconte) {
        boolean result = false;
        for(NotificacionView notView: notificacionesView) 
            if(notView.getOficioSalida().equals(kfconte.getTitle())) {result = true; break;}
        return result;
    }

    public void enviarNotificaciones() {
        logger.info("Total de notificaciones: " + notificacionesSelected.length);
        if (notificacionesSelected != null && notificacionesSelected.length > 0) {
            guardarNotificacion(notificacionesSelected);
            notificacionesSelect = Arrays.asList(notificacionesSelected);
            mostrarResumen = true;
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Operación exitosa"));
        } else {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("No hay archivos seleccionados para enviar"));
        }
    }

    public void guardarNotificacion(NotificacionView[] notificaciones) {
        logger.info("Id usuario firma: " + promoventeSelected.getIdPromovente());
        for (NotificacionView notView : notificaciones) {
            Notificacion notificacion = new Notificacion();
            notificacion.setFolio(notView.getOficioSalida().toString());
            notificacion.setArchivoNombre(notView.getOficioSalida() + ".pdf");
            notificacion.setArchivo(notView.getArchivo());
            notificacion.setDenominacion(notView.getDenominacion());
            notificacion.setExpediente(notView.getExpediente());
            notificacion.setIdUsuarioCarga(usuarioCarga.getId_promovente());
            notificacion.setIdUsuarioFirma(promoventeSelected.getIdPromovente().intValue());
            notificacion.setTitular(notView.getTitular());
            notificacion.setIdArea(idArea);
            flujosgralesViewService.insertarNotificacion(notificacion);
            notificacionesView.remove(notView);
        }
    }

    public void eliminarNotSeleccionadas() {
        logger.info("Total de notificaciones: " + notificacionesSelected.length);
        if (notificacionesSelected.length > 0) {
            for (NotificacionView notView : notificacionesSelected) {
                notificacionesView.remove(notView);
            }
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Eliminacion correcta"));
        } else {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("No hay archivos seleccionados"));
        }
    }

    public void eliminarTodasNot() {
        logger.info("Total de notificaciones: " + notificacionesSelected.length);
        if (notificacionesSelected.length > 0) {
            notificacionesView.clear();
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Eliminacion correcta"));
        } else {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("No hay archivos seleccionados"));
        }
    }

    public void overrideNombreCoordinador_select() {
        for (Promovente prom : promoventes) {
            if (prom.getIdPromovente().equals(promoventeSelected.getIdPromovente())) {
                BeanUtils.copyProperties(prom, promoventeSelected);
                break;
            }
        }
    }

    public String getSysdate() {
        return Util.formatearFecha(flujosgralesViewService.getSysDate(), Util.FORMATODDMMYYYYHHMMSS);
    }

    public void setFlujosgralesViewService(FlujosGralesViewServiceImpl flujosgralesViewService) {
        this.flujosgralesViewService = flujosgralesViewService;
    }

    public List<Notificacion> getNotificaciones() {
        return notificaciones;
    }

    public void setNotificaciones(List<Notificacion> notificaciones) {
        this.notificaciones = notificaciones;
    }

    public List<NotificacionView> getNotificacionesView() {
        return notificacionesView;
    }

    public void setNotificacionesView(List<NotificacionView> notificacionesView) {
        this.notificacionesView = notificacionesView;
    }

    public NotificacionViewDM getNotificacionDM() {
        return notificacionDM;
    }

    public void setNotificacionDM(NotificacionViewDM notificacionDM) {
        this.notificacionDM = notificacionDM;
    }

    public NotificacionView[] getNotificacionesSelected() {
        return notificacionesSelected;
    }

    public void setNotificacionesSelected(NotificacionView[] notificacionesSelected) {
        this.notificacionesSelected = notificacionesSelected;
    }

    public List<Promovente> getPromoventes() {
        return promoventes;
    }

    public void setPromoventes(List<Promovente> promoventes) {
        this.promoventes = promoventes;
    }

    public Promovente getPromoventeSelected() {
        return promoventeSelected;
    }

    public void setPromoventeSelected(Promovente promoventeSelected) {
        this.promoventeSelected = promoventeSelected;
    }

    public int getLengthOfArray() {
        if (notificacionesSelected != null) {
            return notificacionesSelected.length;
        } else {
            return 0;
        }
    }

    public PromoventeDto getUsuarioCarga() {
        return usuarioCarga;
    }

    public void setUsuarioCarga(PromoventeDto usuarioCarga) {
        this.usuarioCarga = usuarioCarga;
    }

    public String getNombreArchivos() {
        return nombreArchivos;
    }

    public void setNombreArchivos(String nombreArchivos) {
        this.nombreArchivos = nombreArchivos;
    }

    public String getCodbarrasAcuerdo() {
        return codbarrasAcuerdo;
    }

    public void setCodbarrasAcuerdo(String codbarrasAcuerdo) {
        this.codbarrasAcuerdo = codbarrasAcuerdo;
    }

    
    public String getMsgErrors() {
        return msgErrors;
    }

    public void setMsgErrors(String msgErrors) {
        this.msgErrors = msgErrors;
    }

    public List<NotificacionView> getNotificacionesSelect() {
        return notificacionesSelect;
    }

    public void setNotificacionesSelect(List<NotificacionView> notificacionesSelect) {
        this.notificacionesSelect = notificacionesSelect;
    }

    public boolean isMostrarResumen() {
        return mostrarResumen;
    }

    public void setMostrarResumen(boolean mostrarResumen) {
        this.mostrarResumen = mostrarResumen;
    }
    
    public String tester() {
        System.out.println("i am a useless tester");
        return "Basura";
    }
    
    public String findRecord() {        
        
        String msgError = "";
        Notification requested;
//        NotificacionView requested = new NotificacionView();
        List<KfContenedores> dummyList;
        KfContenedores record;

        if (isAlreadyPresent(viewNots, codbarrasAcuerdo)) {
            msgError = "El expediente con título " + codbarrasAcuerdo + " ya está cargado en la tabla";
        } else if (codbarrasAcuerdo != null && !codbarrasAcuerdo.isEmpty() && validarCodbarrasSigappi(codbarrasAcuerdo)) {
//            currentNotifications = (List<Notification>) flujosgralesViewService.findAllByUser(1234567891L);
            dummyList = flujosgralesViewService.selectKfContenedoresByTitle(codbarrasAcuerdo);
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
//            requested = flujosgralesViewService.findByTitle(this.codbarrasAcuerdo);
        } else {
            msgError = "El ID " + this.codbarrasAcuerdo + " no es valido";
        }
        return msgError;
    }
    
    private Integer relationType;
    private Integer requestingUser = 12345;
    private List<Notification> viewNots;
    private List<Notification> selected;
    private List<SolicitudInteresados> persisted;
    private List<TiposRelacion> relations;
    
    public Integer getRelationType() {return relationType;}
    public void setRelationType(Integer r) {relationType = r;}
    public Integer getRequestingUser() {return requestingUser;}
    public void setRequestingUser(Integer u) {requestingUser = u;}
    public List<Notification> getViewNots() {return viewNots;}
    public void setViewNots(List<Notification> nots) {viewNots = nots;}
    public List<Notification> getSelected() {return selected;}
    public void setSelected(List<Notification> s) {selected = s;}

    public List<TiposRelacion> getRelations() {
        return relations;
    }

    public void setRelations(List<TiposRelacion> relations) {
        this.relations = relations;
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
        for(SolicitudInteresados noti: notsInView) {
            for(SolicitudInteresados pers: persisted) {
                if(!(pers.getTitle() == null ? noti.getTitle() == null : pers.getTitle().equals(noti.getTitle())) || !Objects.equals(pers.getCodInteresado(), noti.getCodInteresado()))
                    notsToPersist.add(noti);
            }
        }
//        notsToPersist.removeAll(persisted);
        for(SolicitudInteresados n: notsToPersist) {
            flujosgralesViewService.insert(n);
        }
        persisted.addAll(notsToPersist);
    }
    
    private List<SolicitudInteresados> buildFromView(List<Notification> viewNotifications) {
        List<SolicitudInteresados> notifications = new ArrayList<>();
        SolicitudInteresados noti;
        for(Notification n: viewNotifications) {
            noti = new SolicitudInteresados();
            noti.setTitle(n.getTitle());
            noti.setCodInteresado(usuarioCarga.getId_promovente());
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
        for(SolicitudInteresados s: persistedNotifications) {
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
    
    public List<TiposRelacion> listAllRelationTypes() {
        relations = flujosgralesViewService.selectTiposRelacionByCodRelacion(1);
        return relations;
    }
    
}
