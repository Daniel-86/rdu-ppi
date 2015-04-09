/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.gob.impi.rdu.exposition.firma;

import java.io.ByteArrayOutputStream;
import java.io.Serializable;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import mx.gob.impi.pase.persistence.model.Promovente;
import mx.gob.impi.rdu.dto.*;
import mx.gob.impi.rdu.exposition.SesionRDU;
import mx.gob.impi.rdu.exposition.flujosGenerales.reporte.GenerarReporte;
import mx.gob.impi.rdu.firma.service.CertificateValidator;
import mx.gob.impi.rdu.persistence.model.Firma;
import mx.gob.impi.rdu.persistence.model.Notificacion;
import mx.gob.impi.rdu.persistence.model.NotificacionFirma;
import mx.gob.impi.rdu.service.FlujosGralesViewServiceImpl;
import mx.gob.impi.rdu.service.MailService;
import mx.gob.impi.rdu.util.BundleUtils;
import mx.gob.impi.rdu.util.ContextUtils;
import mx.gob.impi.rdu.util.FiltroExtraEnum;
import mx.gob.impi.rdu.util.Util;
import org.apache.log4j.Logger;
import org.springframework.web.util.WebUtils;

/**
 *
 * @author
 */
@ManagedBean(name = "firmaNotificacionMB")
@ViewScoped
@SuppressWarnings("serial")
public class FirmaNotificacionMB implements Serializable {

    private Notificacion[] selectedNotifs;
    private Notificacion selectedNotif;
    private List<Notificacion> allNotificaciones;
    private List<Notificacion> listSelectedNotifs = new ArrayList<Notificacion>();
    private List<FirmaDto> notFirmadas;
    private NotificacionDataModel mediumCarsModel;
    @ManagedProperty(value = "#{flujosgralesViewService}")
    private FlujosGralesViewServiceImpl flujosgralesViewService;
    @ManagedProperty(value = "#{certificateValidatorService}")
    private CertificateValidator certificateValidatorService;
    @ManagedProperty(value = "#{mailService}")
    private MailService mailService;   
    private String cert;
    private String firmanteNombre;
    private String firmanteId;
    private String certificadora;
    private String firmaDigitalPromovente;
    private String firmaImpi;
    private String serialNumberSolicitante;
    private String ocspResponse;
    private String fechaOcsp;
    private String fechaExpiracion;
    private String firmanteEncode;
    private String publicKey;
    private String verify;
    private String verifyEnc;
    private Integer idPromovente;
    private boolean mostrarResumen;
    private FiltroTablero filtroExtra = new FiltroTablero(FiltroExtraEnum.ULTIMA_SEMANA.getIdFiltroExtra(), FiltroExtraEnum.ULTIMA_SEMANA.getDescripcion());
    private Boolean mostrarRango = false;
    private Boolean success = false;
    private String folios = "";
    private int idTipoTramite = 0;
    private Date fechaInicio;
    private Date fechaFin;
    private List<Promovente> filtrosTipoTramite = new ArrayList<Promovente>();
    private Logger lger = Logger.getLogger(this.getClass().getName());
    private List<FiltroTablero> filtrosExtras = new ArrayList<FiltroTablero>();

    public List<Promovente> getFiltrosTipoTramite() {
        return filtrosTipoTramite;
    }

    public void setFiltrosTipoTramite(List<Promovente> filtrosTipoTramite) {
        this.filtrosTipoTramite = filtrosTipoTramite;
    }

    public int getIdTipoTramite() {
        return idTipoTramite;
    }

    public void setIdTipoTramite(int idTipoTramite) {
        this.idTipoTramite = idTipoTramite;
    }

    public Boolean getMostrarRango() {
        return mostrarRango;
    }

    public void setMostrarRango(Boolean mostrarRango) {
        this.mostrarRango = mostrarRango;
    }

    public void setMailService(MailService mailService) {
        this.mailService = mailService;
    }  

    @PostConstruct
    public void init() throws Exception {
        SesionRDU obtSession = ContextUtils.obtenerSesionUsuario();
        this.idPromovente = obtSession.getPromovente().getId_promovente();
        //this.idPromovente =20421;


        allNotificaciones = this.flujosgralesViewService.consultarNotificaciones(this.idPromovente);
        String area = (String) ContextUtils.getSession().getAttribute("area");
        if (area.equals("8")) {
            this.filtrosTipoTramite.add(new Promovente(BigDecimal.ZERO, "Seleccione un usuario..."));
            this.filtrosTipoTramite.addAll(this.flujosgralesViewService.selectPromoventeByPerfil(22));
        } else {
            this.filtrosTipoTramite.add(new Promovente(BigDecimal.ZERO, "Seleccione un usuario..."));
            filtrosTipoTramite.addAll(this.flujosgralesViewService.selectPromoventeByPerfil(42));
        }

        filtrosExtras = new ArrayList<FiltroTablero>();
        filtrosExtras.add(new FiltroTablero(1L, "Ultima semana"));
        filtrosExtras.add(new FiltroTablero(2L, "Ultimo mes"));
        filtrosExtras.add(new FiltroTablero(3L, "Rango de fechas"));

        //Unir la tabla notificaciones con la tabla promoventes dependiendo el perfil
        this.setNombreExaminador(allNotificaciones, filtrosTipoTramite);

        mediumCarsModel = new NotificacionDataModel(allNotificaciones);
        this.setVerify("notificacion");
        this.setVerifyEnc(Util.encodeObject(this.getVerify()));

    }

    public void buscaNotificaciones() {

        this.success = false;
        allNotificaciones = this.flujosgralesViewService.consultarNotificaciones(1);
        mediumCarsModel = new NotificacionDataModel(allNotificaciones);


    }

    public String guardaFirma() {
        this.success = false;

        SesionRDU obtSession = ContextUtils.obtenerSesionUsuario();
        int idPromovente = obtSession.getPromovente().getId_promovente();

        Pattern pattern = Pattern.compile(BundleUtils.getResource("firma.pattern.nombresol"));
        Matcher matcher = pattern.matcher(this.getFirmanteNombre());
        String nombreFirmante = null;
        if (matcher.find()) {
            nombreFirmante = matcher.group(1);
        }
        this.setFirmanteNombre(nombreFirmante);

        matcher = pattern.matcher(this.getCertificadora());
        if (matcher.find()) {
            this.setCertificadora(matcher.group(1));
        }

        if (!(nombreFirmante != null && nombreFirmante.toUpperCase().trim().equals(obtSession.getPromovente().getNombre() + " " + obtSession.getPromovente().getApaterno() + " " + obtSession.getPromovente().getAmaterno()))) {

            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Nombre del firmante y certificado no coinciden", "No firmado"));
            return null;
        }

        if (this.selectedNotifs != null && this.selectedNotifs.length > 0) {

            listSelectedNotifs = Arrays.asList(selectedNotifs);

            List<Integer> nts = new ArrayList<Integer>();

            for (Notificacion nt : this.selectedNotifs) {
                nts.add(nt.getIdNotificacion().intValue());
            }


            List<Notificacion> nots = this.flujosgralesViewService.selectNotificacionesByIds(nts);
            this.notFirmadas = new ArrayList<FirmaDto>();
            for (Notificacion notif : nots) {

                Date sysDate = this.flujosgralesViewService.getSysDate();
                FirmaDto firma = new FirmaDto();
                firma.setAnexoXml(new byte[]{});//*
                firma.setCadenaImpi(this.generaCadena(notif, sysDate));//*            
                firma.setCertificadora(this.getCertificadora());
                String ar = (String) ContextUtils.getSession().getAttribute("area");

                if ("8".equals(ar)) {
                    //firma.setExpediente(new Long(notif.getExpediente()));
                    Firma fm = this.flujosgralesViewService.getFirmaByExp(new Long(notif.getExpediente()), null);
                    if (fm != null) {
                        firma.setIdTramite(fm.getIdTramite());
                    }
                } else {
                    //firma.setClaveExpediente(notif.getExpediente());
                    //firma.setExpediente(0L);
                    Firma fm = this.flujosgralesViewService.getFirmaByExp(null, notif.getExpediente());
                    if (null != fm) {
                        firma.setIdPatente(fm.getIdPatente());
                    }
                }

                firma.setExpediente(0L);
                firma.setFechaRegistro(sysDate);
                firma.setFirmaImpi(this.generaSelloDigitalPromovente(this.getFirmaImpi(), firma.getCadenaImpi()));//*
                firma.setFirmaSolicitante("");//*
                firma.setFolio(notif.getFolio());
                firma.setNombreFirmante(nombreFirmante);
                firma.setAcusePdf(this.generaReporte(notif, firma));
                firma.setAnexoXml(new AnexoNotificacionXml(notif, firma).getDocumentoXml());
                //    aqui voy los view service listos
                Long idFirma = this.flujosgralesViewService.insertaFirma(firma);
                firma.setExpedienteId(notif.getTitular());
                firma.setCertificadora(notif.getExpediente());
                this.notFirmadas.add(firma);
                if (idFirma != null) {
                    Long idNotifFirm = this.flujosgralesViewService.saveFirmaNotificacion(new NotificacionFirma(notif.getIdNotificacion(), idFirma));
                    // if (idNotifFirm != null) {
                    this.flujosgralesViewService.updateNotificacionFirmada(notif);
                    // }

                    try {
                        String email = null;
                        if ("8".equals(ar)) {
                            email = this.flujosgralesViewService.getEmailByExp(firma.getIdTramite(), ar);
                        } else {
                            email = this.flujosgralesViewService.getEmailByExp(firma.getIdPatente(), ar);
                        }



                        if (email != null) {
                            this.mailService.sendMail(BundleUtils.getResource("firma.email.rdu"), email, BundleUtils.getResource("firma.email.subject"), BundleUtils.getResource("firma.email.bodymessnot") + " " + firma.getFolio());
                        }


                    } catch (Exception ee) {
                    }





                } else {
                }


            }
        }

        if (notFirmadas != null && notFirmadas.size() > 0) {
            String fs = "";
            for (FirmaDto n : notFirmadas) {
                fs = fs + n.getFolio() + "  ";
            }
            this.success = true;
            this.folios = fs;
            allNotificaciones = this.flujosgralesViewService.consultarNotificaciones(this.idPromovente);

            //Unir la tabla notificaciones con la tabla promoventes
            this.setNombreExaminador(allNotificaciones, filtrosTipoTramite);

            mediumCarsModel = new NotificacionDataModel(allNotificaciones);
            WebUtils.setSessionAttribute((HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest(), "folionot", fs);
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "FIRMADO EXITOSO", "Folios: " + fs));
            HttpSession se = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(true);
            try {
                se.setAttribute("notfirms", se);
                //FacesContext.getCurrentInstance().getExternalContext().redirect("/rdu-ppi/content/restricted/firma/notificacionesfirmadas.faces");

            } catch (Exception e) {
                e.printStackTrace();
            }
            mostrarResumen = true;
        } else {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "NO SE FIRMARON NOTIFICACIONES", "...ExceptionError "));
        }
        return null;
    }

    public String generaSelloDigitalPromovente(String enc, String cadenaSolicitante) {

        try {
            Object obj = Util.decodeObject(enc);
            return this.certificateValidatorService.generaSelloDigitalPromovente(obj, cadenaSolicitante);
        } catch (Exception e) {
            return "++++";
        }
    }

    public byte[] generaReporte(Notificacion not, FirmaDto firm) {
        HttpSession session = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(true);
        HttpServletRequest request = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
        ByteArrayOutputStream byt = null;
        session.removeAttribute("reporteStream");
        GenerarReporte repps = new GenerarReporte();

        ReporteNotificacionAcuseDto deno = new ReporteNotificacionAcuseDto(not, firm, request.getRealPath("") + "/content/imagenes/firma_impi.png");
        //byt= repps.eliminarPagina(/*Metodo para crear la marca de agua*/ repps.crearMarcaAgua(/*Metodo para concatenar los pdfs*/repps.generaRepporte(request.getRealPath("") + "/content/reportes/impi_transRegimen.jasper", deno)), 1);
        byt = repps.generaRepporte(request.getRealPath("") + "/content/reportes/impi_notificacion.jasper", deno);
        //byt = new ByteArrayOutputStream(19);
        List<byte[]> mm = new ArrayList<byte[]>();
        mm.add(not.getArchivo());
        byt = repps.concatenarPdfs(byt, mm);
        return byt.toByteArray();
    }

    public String generaSelloDigitalmpi(String cadanaImpiGen) {
        String sello;
        try {
            sello = this.certificateValidatorService.generaSelloDigital(cadanaImpiGen);
        } catch (Exception e) {
            sello = "****";
        }

        return sello;
    }

    public String generaCadena(final Notificacion not, Date sysDate) {
        StringBuilder sb = new StringBuilder("|");
        sb.append(not.getArchivoNombre());
        sb.append("|");
        sb.append(not.getExpediente());
        sb.append("|");
        sb.append(Util.formatearFecha(sysDate, Util.FORMATODDMMYYYYHHMMSS));
        sb.append("|");
        sb.append(Util.getDigest(not.getArchivo()));
        sb.append("|");
        sb.append(not.getIdUsuarioFirma());
        sb.append("|");

        return sb.toString();
    }

    public void cmbConsultar_actionListener() {
        this.success = false;
        mostrarResumen = false;
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd-MM-yyyy");

        if (fechaInicio != null && fechaFin != null && filtroExtra.getIdFiltro().intValue() == 3) {
            allNotificaciones = this.flujosgralesViewService.selectNotificacionesByDates(simpleDateFormat.format(fechaInicio), simpleDateFormat.format(fechaFin), null, null, this.idPromovente);

            //Unir la tabla notificaciones con la tabla promoventes
            this.setNombreExaminador(allNotificaciones, filtrosTipoTramite);

            mediumCarsModel = new NotificacionDataModel(allNotificaciones);
        }
    }

    public void cmbBorrar_actionListener() {

        this.success = false;
        mostrarResumen = false;
        if (this.selectedNotifs != null && this.selectedNotifs.length > 0) {


            List<Integer> nts = new ArrayList<Integer>();

            for (Notificacion nt : this.selectedNotifs) {
                nts.add(nt.getIdNotificacion().intValue());
            }

            this.flujosgralesViewService.deleteNotificacionesByIds(nts);
            allNotificaciones = this.flujosgralesViewService.consultarNotificaciones(this.idPromovente);
            mediumCarsModel = new NotificacionDataModel(allNotificaciones);
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "BORRADO EXITOSO", ":: "));
        }

    }

    public void tipoFiltroExtraSelect_changeValue() {//busar por fechas++++++++
        //filtroExtra.getIdFiltro().intValue()) {//falta aplicar filtros por seleccion
        this.success = false;
        mostrarResumen = false;
        int typ = filtroExtra.getIdFiltro().intValue();
        //1 ultima semana

        if (typ == 1) {
            mediumCarsModel = new NotificacionDataModel(this.flujosgralesViewService.selectNotificacionesByDates(null, null, 1, null, this.idPromovente));
            this.mostrarRango = false;
        } else if (typ == 2) {
            mediumCarsModel = new NotificacionDataModel(this.flujosgralesViewService.selectNotificacionesByDates(null, null, null, 1, this.idPromovente));
            this.mostrarRango = false;
        } else if (typ == 3) {
            //this.flujosgralesViewService.selectNotificacionesByDates(fechaInicio, fechaFinal, ultimaSemana, ultimoMes);
            this.mostrarRango = true;
        }



        Calendar c = Calendar.getInstance();
        c.add(Calendar.DAY_OF_MONTH, -7);

        //2 ultimo mes
        c.add(Calendar.DAY_OF_MONTH, -31);
    }

    public void tipoSolictudselect_changeValue() {//buscar por tipo de persona +++++++++++++++++++
        this.success = false;
        mostrarResumen = false;
        if (this.idTipoTramite != 0) {

            //Unir la tabla notificaciones con la tabla promoventes
            allNotificaciones = flujosgralesViewService.getNotificacionesUserLoad(this.idTipoTramite, this.idPromovente);
            this.setNombreExaminador(allNotificaciones, filtrosTipoTramite);

            mediumCarsModel = new NotificacionDataModel(allNotificaciones);
        }
        // tipoFiltroExtraSelect_changeValue();
    }

    public void setNombreExaminador(List<Notificacion> notificaciones, List<Promovente> promoventes) {
        for (Notificacion notificacion : notificaciones) {
            for (Promovente promovente : promoventes) {
                if (promovente.getIdPromovente().longValue() == notificacion.getIdUsuarioCarga().longValue()) {
                    notificacion.setNombreExaminador(promovente.getNombre());
                    break;
                }
            }
        }

    }

    public void filtrarExpedientes(Integer idArea, Integer idTipoSolicitud,
            Integer ultimaSemana, Integer ultimoMes, String fechaInicio, String fechaFin) {
        Long idUsuario = 0L;
        SesionRDU obtSession = ContextUtils.obtenerSesionUsuario();
        try {
            if (null != obtSession) {
                if (obtSession.getUsuario().getIdUsuario() > 0) {
                    if (obtSession.getUsuario().getPerfiles().get(0).getIdPerfil().equals("18")) {
                        idUsuario = 0L;

                    } else {
                        idUsuario = obtSession.getUsuario().getIdUsuario();

                    }
                    //this.expedientes = this.flujosgralesViewService.obtenerExpedientes(pUsuarios, idArea, idTipoSolicitud, ultimaSemana, ultimoMes, fechaInicio, fechaFin);
                    // expedienteDM = new ExpedienteDM(expedientes);
                }
            }

        } catch (Exception e) {
            lger.fatal("Informacion de Error: -- " + e + " -- ubicado en: " + e.getLocalizedMessage());
        }
    }

    public String getNombreFirmante() {
        SesionRDU obtSession = ContextUtils.obtenerSesionUsuario();
        return obtSession.getPromovente().getNombre() + " " + obtSession.getPromovente().getApaterno() + " " + obtSession.getPromovente().getAmaterno();
    }

    public String getSysdate() {
        return Util.formatearFecha(flujosgralesViewService.getSysDate(), Util.FORMATODDMMYYYYHHMMSS);
    }

    public String getCert() {
        return cert;
    }

    public void setCert(String cert) {
        this.cert = cert;
    }

    public String getCertificadora() {
        return certificadora;
    }

    public void setCertificadora(String certificadora) {
        this.certificadora = certificadora;
    }

    public String getFechaExpiracion() {
        return fechaExpiracion;
    }

    public void setFechaExpiracion(String fechaExpiracion) {
        this.fechaExpiracion = fechaExpiracion;
    }

    public String getFechaOcsp() {
        return fechaOcsp;
    }

    public void setFechaOcsp(String fechaOcsp) {
        this.fechaOcsp = fechaOcsp;
    }

    public String getFirmaDigitalPromovente() {
        return firmaDigitalPromovente;
    }

    public void setFirmaDigitalPromovente(String firmaDigitalPromovente) {
        this.firmaDigitalPromovente = firmaDigitalPromovente;
    }

    public String getFirmaImpi() {
        return firmaImpi;
    }

    public void setFirmaImpi(String firmaImpi) {
        this.firmaImpi = firmaImpi;
    }

    public String getFirmanteEncode() {
        return firmanteEncode;
    }

    public void setFirmanteEncode(String firmanteEncode) {
        this.firmanteEncode = firmanteEncode;
    }

    public String getFirmanteId() {
        return firmanteId;
    }

    public void setFirmanteId(String firmanteId) {
        this.firmanteId = firmanteId;
    }

    public String getFirmanteNombre() {
        return firmanteNombre;
    }

    public void setFirmanteNombre(String firmanteNombre) {
        this.firmanteNombre = firmanteNombre;
    }

    public String getOcspResponse() {
        return ocspResponse;
    }

    public void setOcspResponse(String ocspResponse) {
        this.ocspResponse = ocspResponse;
    }

    public String getPublicKey() {
        return publicKey;
    }

    public void setPublicKey(String publicKey) {
        this.publicKey = publicKey;
    }

    public String getSerialNumberSolicitante() {
        return serialNumberSolicitante;
    }

    public void setSerialNumberSolicitante(String serialNumberSolicitante) {
        this.serialNumberSolicitante = serialNumberSolicitante;
    }

    public NotificacionDataModel getMediumCarsModel() {
        return mediumCarsModel;
    }

    public void setMediumCarsModel(NotificacionDataModel mediumCarsModel) {
        this.mediumCarsModel = mediumCarsModel;
    }

    public List<Notificacion> getAllNotificaciones() {
        return allNotificaciones;
    }

    public void setAllNotificaciones(List<Notificacion> allNotificaciones) {
        this.allNotificaciones = allNotificaciones;
    }

    public Notificacion[] getSelectedNotifs() {
        return selectedNotifs;
    }

    public void setSelectedNotifs(Notificacion[] selectedNotif) {
        this.selectedNotifs = selectedNotif;
    }

    public Notificacion getSelectedNotif() {
        return selectedNotif;
    }

    public void setSelectedNotif(Notificacion selectedNotif) {
        this.selectedNotif = selectedNotif;
    }

    public void setCertificateValidatorService(CertificateValidator certificateValidatorService) {
        this.certificateValidatorService = certificateValidatorService;
    }

    public void setFlujosgralesViewService(FlujosGralesViewServiceImpl flujosgralesViewService) {
        this.flujosgralesViewService = flujosgralesViewService;
    }

    public String getVerify() {
        return verify;
    }

    public void setVerify(String verify) {
        this.verify = verify;
    }

    public String getVerifyEnc() {
        return verifyEnc;
    }

    public void setVerifyEnc(String verifyEnc) {
        this.verifyEnc = verifyEnc;
    }

    public List<FiltroTablero> getFiltrosExtras() {
        return filtrosExtras;
    }

    public void setFiltrosExtras(List<FiltroTablero> filtrosExtras) {
        this.filtrosExtras = filtrosExtras;
    }

    public FiltroTablero getFiltroExtra() {
        return filtroExtra;
    }

    public void setFiltroExtra(FiltroTablero filtroExtra) {
        this.filtroExtra = filtroExtra;
    }

    public Date getFechaFin() {
        return fechaFin;
    }

    public void setFechaFin(Date fechaFin) {
        this.fechaFin = fechaFin;
    }

    public Date getFechaInicio() {
        return fechaInicio;
    }

    public void setFechaInicio(Date fechaInicio) {
        this.fechaInicio = fechaInicio;
    }

    public Integer getIdPromovente() {
        return idPromovente;
    }

    public void setIdPromovente(Integer idPromovente) {
        this.idPromovente = idPromovente;
    }

    public List<FirmaDto> getNotFirmadas() {
        return notFirmadas;
    }

    public void setNotFirmadas(List<FirmaDto> notFirmadas) {
        this.notFirmadas = notFirmadas;
    }

    public Boolean getSuccess() {
        return success;
    }

    public void setSuccess(Boolean success) {
        this.success = success;
    }

    public String getFolios() {
        return folios;
    }

    public void setFolios(String folios) {
        this.folios = folios;
    }

    public List<Notificacion> getListSelectedNotifs() {
        return listSelectedNotifs;
    }

    public void setListSelectedNotifs(List<Notificacion> listSelectedNotifs) {
        this.listSelectedNotifs = listSelectedNotifs;
    }

    public boolean isMostrarResumen() {
        return mostrarResumen;
    }

    public void setMostrarResumen(boolean mostrarResumen) {
        this.mostrarResumen = mostrarResumen;
    }
}
