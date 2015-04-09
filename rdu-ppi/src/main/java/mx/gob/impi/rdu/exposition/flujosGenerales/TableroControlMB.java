/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.gob.impi.rdu.exposition.flujosGenerales;

import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import mx.gob.impi.rdu.exposition.SesionRDU;
import mx.gob.impi.rdu.service.FlujosGralesViewServiceImpl;
import mx.gob.impi.rdu.util.ContextUtils;
import org.apache.log4j.Logger;
import mx.gob.impi.rdu.dto.ExpedientesDto;
import org.primefaces.model.DefaultStreamedContent;
import org.primefaces.model.StreamedContent;
import mx.gob.impi.rdu.util.Util;
import mx.gob.impi.rdu.util.MimeType;
import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Collections;
import java.util.Date;
import java.util.ResourceBundle;
import javax.faces.model.DataModel;
import javax.naming.NamingException;
import mx.gob.impi.rdu.dataModel.ExpedienteDM;
import mx.gob.impi.rdu.dataModel.ExpedienteLazyDM;
import mx.gob.impi.rdu.dto.ArchivoDigitalDto;
import mx.gob.impi.rdu.dto.FiltroTablero;
import mx.gob.impi.rdu.dto.TipoTramiteDto;
import mx.gob.impi.rdu.persistence.model.Firma;
import mx.gob.impi.rdu.util.*;
import org.primefaces.context.RequestContext;

/**
 *
 * @author usradmin
 */
@ManagedBean(name = "tableroControlMB")
@ViewScoped
public class TableroControlMB implements Serializable {

    private Logger lger = Logger.getLogger(this.getClass());
    private List<ExpedientesDto> expedientes = new ArrayList();
    private ExpedientesDto[] expedientesSelected;
    private ExpedientesDto expedienteSelected = new ExpedientesDto();
    private static final String BUNDLE_PARAMETROS = "mx.gob.impi.rdu.i18n.Tableros";
    final ResourceBundle bdlePrmTablero = ResourceBundle.getBundle(BUNDLE_PARAMETROS);
    private static final String BUNDLE_PARAMETROSCONFIG = "mx.gob.impi.rdu.i18n.parametros";
    final ResourceBundle bundleParametrosConfig = ResourceBundle.getBundle(BUNDLE_PARAMETROSCONFIG);
    private int idTipoTramite = 0;
    private String area = "";
    private List<TipoTramiteDto> filtrosTipoTramite = new ArrayList();
    private List<FiltroTablero> filtrosExtras = new ArrayList<FiltroTablero>();
    private List<Integer> pUsuarios = new ArrayList();
    //private ExpedienteDM expedienteDM;
    private ExpedienteLazyDM expedienteDM;
    private Date fechaInicio;
    private Date fechaFin;
    private Boolean mostrarRango = false;
    private FiltroTablero filtroExtra = new FiltroTablero(FiltroExtraEnum.ULTIMA_SEMANA.getIdFiltroExtra(), FiltroExtraEnum.ULTIMA_SEMANA.getDescripcion());
    SesionRDU obtSession = ContextUtils.obtenerSesionUsuario();
    @ManagedProperty(value = "#{flujosgralesViewService}")
    private FlujosGralesViewServiceImpl flujosgralesViewService;

    public void setFlujosgralesViewService(FlujosGralesViewServiceImpl flujosgralesViewService) {
        this.flujosgralesViewService = flujosgralesViewService;
    }

    @PostConstruct
    public void init() {
        //System.out.println("Es coordinador recepcion: " + perfilUsuario.coordinadorRecepcion);

        String ambienteProduccion = null;
        try {
            ambienteProduccion = (String) JndiPropertiesUtils.getProperty("action.ambienteProduccion");
        } catch (NamingException ne) {
            ne.printStackTrace();
        }

        Long idUsuario = 0L;
        int tipoTableros = Integer.parseInt(bundleParametrosConfig.getString("action.TablerosAmbiente"));
        //this.filtrosTipoTramite = Util.geTiposTramiteTipoTableros(tipoTableros);

        this.filtrosTipoTramite = new ArrayList();
        area = (String) ContextUtils.getSession().getAttribute("area");
        if (area.equals("4") || area.equals("20")) {
            if (ambienteProduccion.equals("1")) {
                this.filtrosTipoTramite.add(new TipoTramiteDto(TipoTramiteEnum.SOL_SIT.getIdTipoTramite(), TipoTramiteEnum.SOL_SIT.getDescripcion()));
                this.filtrosTipoTramite.add(new TipoTramiteDto(TipoTramiteEnum.NOTIFICACIONES.getIdTipoTramite(), TipoTramiteEnum.NOTIFICACIONES.getDescripcion()));
                //this.filtrosTipoTramite.add(new TipoTramiteDto(TipoTramiteEnum.PROM_PATENTES.getIdTipoTramite(), TipoTramiteEnum.PROM_PATENTES.getDescripcion()));
            
            } else {
                this.filtrosTipoTramite.add(new TipoTramiteDto(TipoTramiteEnum.SOL_SIT.getIdTipoTramite(), TipoTramiteEnum.SOL_SIT.getDescripcion()));
                this.filtrosTipoTramite.add(new TipoTramiteDto(TipoTramiteEnum.NOTIFICACIONES.getIdTipoTramite(), TipoTramiteEnum.NOTIFICACIONES.getDescripcion()));
                //this.filtrosTipoTramite.add(new TipoTramiteDto(TipoTramiteEnum.PROM_PATENTES.getIdTipoTramite(), TipoTramiteEnum.PROM_PATENTES.getDescripcion()));
            }

        }



        //this.filtrosTipoTramite = Util.geTiposTramite();
        idTipoTramite = filtrosTipoTramite.get(0).getIdTipoTramite();
        filtrosExtras = Util.geFiltrosExtra();
        try {
            if (null != obtSession) {
                if (obtSession.getUsuario().getIdUsuario() > 0) {
                    lger.info("  ** IdPerfil ==> " + obtSession.getUsuario().getPerfiles().get(0).getIdPerfil());
                    //obtSession.getUsuario().getPerfiles().get(0).setIdPerfil("18");
                    if (obtSession.getUsuario().getPerfiles().get(0).getIdPerfil().equals(PerfilEnumeration.ROLE_COORDINADOR_RECEPCION.getIdPerfil())) {
                        idUsuario = 0L;
                        pUsuarios.add(0, idUsuario.intValue());
                        filtroExtra = new FiltroTablero(FiltroExtraEnum.RANGO.getIdFiltroExtra(), FiltroExtraEnum.RANGO.getDescripcion());
                        tipoFiltroExtraSelect_changeValue();
                    } else if (obtSession.getUsuario().getPerfiles().get(0).getIdPerfil().equals(PerfilEnumeration.ROLE_USUARIO_PROMOVENTE.getIdPerfil())) {                        
                        idUsuario = obtSession.getUsuario().getIdUsuario();
                        pUsuarios.add(0, idUsuario.intValue());                        
                        expedienteDM = new ExpedienteLazyDM(flujosgralesViewService,pUsuarios, null, idTipoTramite, 1, null, null, null);
                        
                        
                    } else {                    
                        idUsuario = obtSession.getUsuario().getIdUsuario();
                        pUsuarios = ContextUtils.obtenerUsuariosId();                        
                        expedienteDM = new ExpedienteLazyDM(flujosgralesViewService,pUsuarios, null, idTipoTramite, 1, null, null, null);                        
                        
                    }
                }
            }
           

        } catch (Exception e) {
            lger.fatal("Informacion de Error: -- " + e + " -- ubicado en: " + e.getLocalizedMessage());
        }
    }

    public void expedientesTable_onRowSelect() {
        if (!obtSession.getUsuario().getPerfiles().get(0).getIdPerfil().equals(PerfilEnumeration.ROLE_COORDINADOR_RECEPCION.getIdPerfil())) {
            if (expedienteSelected.getFechaRevision() == null) {
                Firma pFirma = new Firma();
                expedienteSelected.setFechaRevision(new Date());
                pFirma.setIdFirma(expedienteSelected.getIdFirma());
                pFirma.setFechaRevision(expedienteSelected.getFechaRevision());
                this.flujosgralesViewService.updateSelectiveFirma(pFirma);
            }
        }
    }

    public StreamedContent getAcuseDownload() {
        lger.info("getAcuseDownload");
        lger.info("Tiene Acuse PDF: " + expedienteSelected.getTieneAcusePdf());
                
        StreamedContent file = null;
        InputStream stream = null;
        Util utilArch = new Util();

        if (null != expedienteSelected) {           
            if(expedienteSelected.getTieneAcusePdf()){                
                
                ArchivoDigitalDto archivoDigital = flujosgralesViewService.obtenerAcusePdf(expedienteSelected.getIdFirma());                                
                stream = new ByteArrayInputStream(archivoDigital.getArchivo());
                MimeType mType = utilArch.extractMime("pdf");
                String sMime = mType.getMime();
                String sNmDownload = "acuse.pdf";


                sNmDownload = Util.nPosiciones(expedienteSelected.getExpediente(), 7);
                //Nombre del archivo a descargar para una notificacion
                if (expedienteSelected.getTipoSolicitud().equals("NOTIFICACIONES")) {
                    sNmDownload = expedienteSelected.getFolio();
                }
                sNmDownload += ".pdf";
                lger.info("******Nombre del acuse==> " + sNmDownload);

                if (!obtSession.getUsuario().getPerfiles().get(0).getIdPerfil().equals(PerfilEnumeration.ROLE_COORDINADOR_RECEPCION.getIdPerfil())) {
                    if (expedienteSelected.getFechaRevision() == null) {
                        Firma pFirma = new Firma();
                        expedienteSelected.setFechaRevision(new Date());
                        pFirma.setIdFirma(expedienteSelected.getIdFirma());
                        pFirma.setFechaRevision(expedienteSelected.getFechaRevision());
                        this.flujosgralesViewService.updateSelectiveFirma(pFirma);
                    }
                }
                file = new DefaultStreamedContent(stream, sMime, sNmDownload);
            }
        }
        return file;
    }

    /*
     PdfReader reader = new PdfReader(bytes);
     ByteArrayOutputStream baos = new ByteArrayOutputStream();
     PdfStamper stamper = new PdfStamper(reader, baos);
     PdfWriter writer = stamper.getWriter();
     PdfAction action = new PdfAction(PdfAction.PRINTDIALOG);
     writer.setOpenAction(action);
     stamper.close();
     */
    public StreamedContent getXmlDownload() {
        lger.info("getXmlDownload");
        StreamedContent file = null;
        InputStream stream = null;
        Util utilArch = new Util();

        if (null != expedienteSelected) {
             if(expedienteSelected.getTieneAcusePdf()){                
                ArchivoDigitalDto archivoDigital = flujosgralesViewService.obtenerAnexoXml(expedienteSelected.getIdFirma());                                
                stream = new ByteArrayInputStream(archivoDigital.getArchivo());
                
                MimeType mType = utilArch.extractMime("xml");
                String sMime = mType.getMime();
                String sNmDownload = "acuse.xml";
                sNmDownload = Util.nPosiciones(expedienteSelected.getExpediente(), 7);
                sNmDownload += ".xml";
                lger.info("******Nombre del xml==> " + sNmDownload);

                file = new DefaultStreamedContent(stream, sMime, sNmDownload);
            }
        }
        return file;
    }

    public StreamedContent getLogosDownload() {
        lger.info("getLogosDownload");
        StreamedContent file = null;
        InputStream stream = null;
        Util utilArch = new Util();
        String nameFile = "7";

        if (null != expedienteSelected) {
            if(expedienteSelected.getExisteAcuseLogo().equals(1)){                
                ArchivoDigitalDto archivoDigitalDto = flujosgralesViewService.obtenerAcuseLogo(expedienteSelected.getIdTramite());
                
                stream = new ByteArrayInputStream(archivoDigitalDto.getArchivo());
                MimeType mType = utilArch.extractMime("gif");
                String sMime = mType.getMime();
                
                lger.info("Extraer acuse logo");

                //lger.info("******expedienteSelected.getAcuseLogo()==> " + expedienteSelected.getAcuseLogo().length);

                if (expedienteSelected.getAcuseLogo()!=null &&  expedienteSelected.getAcuseLogo().length < 7) {
                    nameFile += Util.nPosiciones(expedienteSelected.getExpediente(), 7);
                } else {
                    nameFile = expedienteSelected.getExpediente().toString();
                }

                lger.info("******Nombre archivo==> " + nameFile);

                String sNmDownload = "7" + nameFile + ".gif";
                file = new DefaultStreamedContent(stream, sMime, sNmDownload);
            }
        }
        return file;
    }

    //ucm - extraer acuse
    public void extraerAcuseUCM() {
        lger.info("Extraer acuse ucm");
        String irUCM = "";
        String folioExpediente = "";
        Long oFolio;

        try {
            if (this.expedienteSelected != null) {
                folioExpediente = Constantes.PREFIX_MARCA + "/";
                switch (this.expedienteSelected.getIdSubtiposolicitud().intValue()) {
                    case 1:
                        folioExpediente += Constantes.FIG_CLASS_MARCA + "/";
                        break;
                    case 2:
                        folioExpediente += Constantes.FIG_CLASS_NOMBRE + "/";
                        break;
                    case 3:
                        folioExpediente += Constantes.FIG_CLASS_AVISO + "/";
                        break;
                    case 4: //marca colectiva
                        folioExpediente += Constantes.FIG_CLASS_MARCA + "/";
                        break;
                }
//                oFolio = Long.valueOf(this.expedienteSelected.getFolio());
                folioExpediente += Constantes.YEAR_FOLIO + "/" + Util.nPosiciones(this.expedienteSelected.getFolio(), 7);
                //folioExpediente="MA/M/1985/1000000";
                irUCM = this.bdlePrmTablero.getString("ucm.direccion") + folioExpediente;
                //FacesContext.getCurrentInstance().getExternalContext().redirect(irUCM);

                irUCM = "mostrarGuiaUsuario('" + irUCM + "');";
                lger.info("redirecionar a UCM: " + irUCM);
                RequestContext context = RequestContext.getCurrentInstance();
                context.execute(irUCM);
                if (!obtSession.getUsuario().getPerfiles().get(0).getIdPerfil().equals(PerfilEnumeration.ROLE_COORDINADOR_RECEPCION.getIdPerfil())) {
                    if (expedienteSelected.getFechaRevision() == null) {
                        Firma pFirma = new Firma();
                        expedienteSelected.setFechaRevision(new Date());
                        pFirma.setIdFirma(expedienteSelected.getIdFirma());
                        pFirma.setFechaRevision(expedienteSelected.getFechaRevision());
                        this.flujosgralesViewService.updateSelectiveFirma(pFirma);
                    }
                }
            }

        } catch (Exception e) {
            lger.fatal("Informacion de Error: -- " + e + " -- ubicado en: " + e.getLocalizedMessage());
            e.printStackTrace();
        }

    }

    public void tipoSolictudselect_changeValue() {
        tipoFiltroExtraSelect_changeValue();
    }

    public void tipoFiltroExtraSelect_changeValue() {
        switch (filtroExtra.getIdFiltro().intValue()) {
            case 1:
                filtrarExpedientes(null, idTipoTramite, null, null, null, null);
                mostrarRango = false;
                break;
            case 2:
                filtrarExpedientes(null, idTipoTramite, 1, null, null, null);
                mostrarRango = false;
                break;
            case 3:
                filtrarExpedientes(null, idTipoTramite, null, 1, null, null);
                mostrarRango = false;
                break;
            case 4:
                filtrarExpedientes(null, idTipoTramite, null, 1, null, null);
                mostrarRango = false;
                break;
        }
    }

    public void cmbConsultar_actionListener() {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd-MM-yyyy");
        if (fechaInicio != null && fechaFin != null && filtroExtra.getIdFiltro().intValue() == FiltroExtraEnum.RANGO.getIdFiltroExtra().intValue()) {
            filtrarExpedientes(null, idTipoTramite, null, null, simpleDateFormat.format(fechaInicio), simpleDateFormat.format(fechaFin));
        }
    }

    public void filtrarExpedientes(Integer idArea, Integer idTipoSolicitud,
            Integer ultimaSemana, Integer ultimoMes, String fechaInicio, String fechaFin) {
        Long idUsuario = 0L;
        try {
            if (null != obtSession) {
                if (obtSession.getUsuario().getIdUsuario() > 0) {
                    if (obtSession.getUsuario().getPerfiles().get(0).getIdPerfil().equals("18")) {
                        idUsuario = 0L;
                        pUsuarios.add(0, idUsuario.intValue());
                    } else {
                        idUsuario = obtSession.getUsuario().getIdUsuario();
                        pUsuarios.add(0, idUsuario.intValue());
                    }
                    
                    if (idTipoSolicitud.intValue() == TipoTramiteEnum.NOTIFICACIONES.getIdTipoTramite()) {
                        idArea = new Integer(area);
                    }

                    expedienteDM = new ExpedienteLazyDM(flujosgralesViewService,pUsuarios, idArea, idTipoSolicitud, ultimaSemana, ultimoMes, fechaInicio, fechaFin);
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public List<ExpedientesDto> getExpedientes() {
        return expedientes;
    }

    public void setExpedientes(List<ExpedientesDto> expedientes) {
        this.expedientes = expedientes;
    }

    public ExpedientesDto getExpedienteSelected() {
        return expedienteSelected;
    }

    public void setExpedienteSelected(ExpedientesDto expedienteSelected) {
        this.expedienteSelected = expedienteSelected;
    }

    public List<TipoTramiteDto> getFiltrosTipoTramite() {
        return filtrosTipoTramite;
    }

    public void setFiltrosTipoTramite(List<TipoTramiteDto> filtrosTipoTramite) {
        this.filtrosTipoTramite = filtrosTipoTramite;
    }

    public int getIdTipoTramite() {
        return idTipoTramite;
    }

    public void setIdTipoTramite(int idTipoTramite) {
        this.idTipoTramite = idTipoTramite;
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

    public FiltroTablero getFiltroExtra() {
        return filtroExtra;
    }

    public void setFiltroExtra(FiltroTablero filtroExtra) {
        this.filtroExtra = filtroExtra;
    }

    public List<FiltroTablero> getFiltrosExtras() {
        return filtrosExtras;
    }

    public void setFiltrosExtras(List<FiltroTablero> filtrosExtras) {
        this.filtrosExtras = filtrosExtras;
    }

    public boolean isMostrarRango() {
        return mostrarRango;
    }

    public void setMostrarRango(boolean mostrarRango) {
        this.mostrarRango = mostrarRango;
    }

    public ExpedienteLazyDM getExpedienteDM() {
        return expedienteDM;
    }

    public void setExpedienteDM(ExpedienteLazyDM expedienteDM) {
        this.expedienteDM = expedienteDM;
    }

    public ExpedientesDto[] getExpedientesSelected() {
        return expedientesSelected;
    }

    public void setExpedientesSelected(ExpedientesDto[] expedientesSelected) {
        this.expedientesSelected = expedientesSelected;
    }
}
