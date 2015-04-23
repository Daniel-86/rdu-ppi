/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.gob.impi.rdu.exposition.flujosGenerales;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.context.ExternalContext;
import mx.gob.impi.rdu.exposition.SesionRDU;
import mx.gob.impi.rdu.service.FlujosGralesViewServiceImpl;
import mx.gob.impi.rdu.util.ContextUtils;
import org.apache.log4j.Logger;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.naming.NamingException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import mx.gob.impi.rdu.dto.TipoTramiteDto;
import mx.gob.impi.rdu.dto.RespuestaDto;
import mx.gob.impi.rdu.dto.SolicitudPreparacionDto;
import mx.gob.impi.rdu.util.JndiPropertiesUtils;
import mx.gob.impi.rdu.util.PerfilEnumeration;
import mx.gob.impi.rdu.util.TipoTramiteEnum;
import mx.gob.impi.rdu.util.Util;

/**
 *
 * @author usradmin
 */
@ManagedBean(name = "docsPreparacionMB")
@ViewScoped
public class DocsPreparacionMB implements Serializable {

    private Logger lger = Logger.getLogger(this.getClass());
    private List<SolicitudPreparacionDto> tramites = new ArrayList();
    private int idTipoTramite = 0;
    private int idSubTipo = 0;
    private SolicitudPreparacionDto tramiteSelected;
    private List<TipoTramiteDto> filtrosTipoTramite = new ArrayList();
    private int idTipoTramiteSelected;
    private long idUsuario = 0;
    private String imagenEstatus;
    private boolean muestraImgEstatus;
    private List<Integer> pUsuarios = new ArrayList();
    private static final String BUNDLE_PARAMETROSCONFIG = "mx.gob.impi.rdu.i18n.parametros";
    final ResourceBundle bundleParametrosConfig = ResourceBundle.getBundle(BUNDLE_PARAMETROSCONFIG);
    @ManagedProperty(value = "#{flujosgralesViewService}")
    private FlujosGralesViewServiceImpl flujosgralesViewService;
    HttpSession session = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(true);
    private boolean promocionesInit;

    public void setFlujosgralesViewService(FlujosGralesViewServiceImpl flujosgralesViewService) {
        this.flujosgralesViewService = flujosgralesViewService;
    }

    public DocsPreparacionMB() {
        this.tramiteSelected = new SolicitudPreparacionDto();
    }

    @PostConstruct
    public void init() {
        String ambienteProduccion = null;
        try {
            ambienteProduccion = (String) JndiPropertiesUtils.getProperty("action.ambienteProduccion");
        } catch (NamingException ne) {
            ne.printStackTrace();
        }
        try {
            int tipoTableros = Integer.parseInt(bundleParametrosConfig.getString("action.TablerosAmbiente"));

            //this.filtrosTipoTramite = Util.geTiposTramiteTipoTableros(tipoTableros);
            this.filtrosTipoTramite = new ArrayList();
            String area = (String) ContextUtils.getSession().getAttribute("area");
            if (area.equals("4")|| area.equals("20")) {
                if (ambienteProduccion.equals("1")) {
                    this.idTipoTramite = TipoTramiteEnum.SOL_SIT.getIdTipoTramite();
                    this.filtrosTipoTramite.add(new TipoTramiteDto(TipoTramiteEnum.SOL_SIT.getIdTipoTramite(), TipoTramiteEnum.SOL_SIT.getDescripcion()));
                    //this.filtrosTipoTramite.add(new TipoTramiteDto(TipoTramiteEnum.PROM_PATENTES.getIdTipoTramite(), TipoTramiteEnum.PROM_PATENTES.getDescripcion()));
                } else {
                    this.idTipoTramite = TipoTramiteEnum.SOL_SIT.getIdTipoTramite();
                    this.filtrosTipoTramite = Util.geTiposTramite(area);
                }
            }
            
            if (area.equals("40")) {
                if (ambienteProduccion.equals("1")) {
                    this.idTipoTramite = TipoTramiteEnum.SOL_PPI.getIdTipoTramite();
                    this.filtrosTipoTramite.add(new TipoTramiteDto(TipoTramiteEnum.SOL_PPI.getIdTipoTramite(), TipoTramiteEnum.SOL_PPI.getDescripcion()));
                    //this.filtrosTipoTramite.add(new TipoTramiteDto(TipoTramiteEnum.PROM_PATENTES.getIdTipoTramite(), TipoTramiteEnum.PROM_PATENTES.getDescripcion()));
                } else {
                    this.idTipoTramite = TipoTramiteEnum.SOL_PPI.getIdTipoTramite();
                    this.filtrosTipoTramite = Util.geTiposTramite(area);
                }
            }


            //this.filtrosTipoTramite = Util.geTiposTramite();            

            SesionRDU obtSession = ContextUtils.obtenerSesionUsuario();
            if (null != obtSession) {
                if (obtSession.getUsuario().getIdUsuario() > 0) {
                    idUsuario = obtSession.getUsuario().getIdUsuario();

                    if (obtSession.getUsuario().getPerfiles().get(0).getIdPerfil().equals(PerfilEnumeration.ROLE_USUARIO_PROMOVENTE.getIdPerfil())) {
                        pUsuarios.add((int) idUsuario);
                    }
                    if (obtSession.getUsuario().getPerfiles().get(0).getIdPerfil().equals(PerfilEnumeration.ROLE_USUARIO_MAESTRO.getIdPerfil())) {
                        pUsuarios = ContextUtils.obtenerUsuariosId();
                    }

                    this.tramites = this.flujosgralesViewService.extraerSolicitudesPreparacion(pUsuarios, idTipoTramite);
                    if (this.tramites.size() > 0) {
                        this.tramiteSelected = this.tramites.get(0);
                        this.verImgTramiteEstatus();
                    }

                }
            }
            //this.imagenEstatus="train-tablero-01.PNG";
            //this.muestraImgEstatus=false;
        } catch (Exception e) {
            lger.fatal("Informacion de Error: -- " + e + " -- ubicado en: " + e.getLocalizedMessage());
        }
    }

    public void initPromociones() {
        if (!promocionesInit) {
            System.out.println("----------> Inicializando promociones...");
            this.idTipoTramite = TipoTramiteEnum.PROM_PATENTES.getIdTipoTramite();
            ejecutarBusqueda();
            promocionesInit=true;
        }
    }

    public void eliminar(ActionEvent actionEvent) {
        String msgAviso = "";

        RespuestaDto respView = new RespuestaDto();
        if (null != this.tramiteSelected) {

            respView = this.flujosgralesViewService.EliminarSolicitud(tramiteSelected);
            if (null != respView) {
                if (respView.getRespuesta() == 1) {
                    this.tramites.remove(this.tramiteSelected);
                }

                msgAviso = respView.getMsgRespuesta();
            }
        } else {
            msgAviso = "Seleccione una solicitud para eliminarla";
        }

        FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, "Aviso", msgAviso);
        FacesContext.getCurrentInstance().addMessage(null, message);

    }

    public void eliminarPromocion(ActionEvent actionEvent) {
        String msgAviso = "";

        RespuestaDto respView = new RespuestaDto();
        if (null != this.tramiteSelected) {

            respView = this.flujosgralesViewService.eliminarPromocion(tramiteSelected);
            if (null != respView) {
                if (respView.getRespuesta() == 1) {
                    this.tramites.remove(this.tramiteSelected);
                }

                msgAviso = respView.getMsgRespuesta();
            }
        } else {
            msgAviso = "Seleccione una promoción para eliminarla";
        }

        FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, "Aviso", msgAviso);
        FacesContext.getCurrentInstance().addMessage(null, message);

    }

    public void setCopiarTramite() {
        SesionRDU obtSession = ContextUtils.obtenerSesionUsuario();
        if (null != obtSession) {
            if (this.tramiteSelected != null) {
                obtSession.setCopiarTramite(true);
                ContextUtils.crearSesionRdu(obtSession);
            }
        }
        irSiguienteAccion();
    }

    public void verImgTramiteEstatus() {
        if (this.tramiteSelected != null) {
            switch (this.tramiteSelected.getIdEstatus().intValue()) {
                case 1:
                    this.imagenEstatus = "train-tablero-01.PNG";
                    break;
                case 2:
                    this.imagenEstatus = "train-tablero-02.png";
                    break;
                case 3:
                    this.imagenEstatus = "train-tablero-03.png";
                    break;
            }
            this.muestraImgEstatus = true;
        }




    }

    public void actTramiteEnSesion() {
        //logger.info("entra");
        SesionRDU obtSession = ContextUtils.obtenerSesionUsuario();
        if (null != obtSession) {
            if (this.tramiteSelected != null) {
                obtSession.setIdTramite(this.tramiteSelected.getIdTramite());
            }
            obtSession.setIdTipoTramite(this.idTipoTramite);
            ContextUtils.crearSesionRdu(obtSession);

        }
    }

    public void irSiguienteAccionPromociones() {

        boolean tieneOficio = tramiteSelected.isTieneOficio();
        Long idPromocion = tramiteSelected.getIdPromocion();
        Long idEstatus = tramiteSelected.getIdEstatus();
        boolean seActualizoSesion = false;

        try {

            System.out.println("------------> tieneOficio: " + tieneOficio);
            System.out.println("------------> idPromocion: " + idPromocion);
            System.out.println("------------> idEstatus: " + idEstatus);
            
            SesionRDU obtSession = ContextUtils.obtenerSesionUsuario();
            if (null != obtSession) {
                if (this.tramiteSelected != null) {
                    obtSession.setIdTramite(this.tramiteSelected.getIdPromocion());
                    obtSession.setIdTipoTramite(this.idTipoTramite);
//                    obtSession.setSubTipo(this.tramiteSelected.getIdSubtiposolicitud().intValue());
//                    if (obtSession.getIdMenu() == 0) {
//                        obtSession.setIdMenu(2);
//                    }

                    ContextUtils.crearSesionRdu(obtSession);
                    seActualizoSesion = true;
                    if (lger.isInfoEnabled()) {
                        lger.info("Id Tramite en session: " + obtSession.getIdTramite());
                    }
                }
            }
            if (seActualizoSesion == false) {
                return;
            }
            
            // TODO: Determinamos la url de acuerdo al estatus
            String url = "";

// URL Para estatus 1 (INCOMPLETA)
            if (idEstatus == 1) {
                lger.info("RUTA    "+this.tramiteSelected.getUrl_TipoSolicitud());
                url = "/content/restricted/patentes/promociones/promocionesPatentes.faces";
            } else if (idEstatus == 3) {
                url = "../../firma/firma.faces";
            }

            FacesContext.getCurrentInstance().getExternalContext().redirect(url);  
            
        } catch (Exception e) {
            System.err.println(e);
        }

    }

    public void irSiguienteAccion() {
        if (lger.isInfoEnabled()) {
            lger.info("Id Tramite Seleccionado: " + this.tramiteSelected.getIdTramite());
        }
        boolean seActualizoSesion = false;
        String irSigAccion = "";
        try {
            SesionRDU obtSession = ContextUtils.obtenerSesionUsuario();
            if (null != obtSession) {
                if (this.tramiteSelected != null) {
                    obtSession.setIdTramite(this.tramiteSelected.getIdTramite());
                    obtSession.setIdTipoTramite(this.idTipoTramite);
                    obtSession.setSubTipo(this.tramiteSelected.getIdSubtiposolicitud().intValue());
                    if (obtSession.getIdMenu() == 0) {
                        obtSession.setIdMenu(2);
                    }

                    ContextUtils.crearSesionRdu(obtSession);
                    seActualizoSesion = true;
                    if (lger.isInfoEnabled()) {
                        lger.info("Id Tramite en session: " + obtSession.getIdTramite());
                    }
                }
            }
            if (seActualizoSesion == false) {
                return;
            }

            if (this.tramiteSelected.getIdEstatus() == 1) {
                //irSigAccion=this.tramiteSelected.getSubTipoSolicitud().getTipoSolicitud().getUrlsigaccion();
                irSigAccion = this.tramiteSelected.getUrl_TipoSolicitud();
            } else {
                //irSigAccion=this.tramiteSelected.getEstatus().getUrlsigaccion();
                irSigAccion = this.tramiteSelected.getUrl_SigAccion_Estatus();
            }

            FacesContext.getCurrentInstance().getExternalContext().redirect(irSigAccion);


        } catch (Exception e) {
            lger.fatal("Informacion de Error: -- " + e + " -- ubicado en: " + e.getLocalizedMessage());
        }

    }

    public void crearCabecero(SolicitudPreparacionDto solicitudPreparacion) {
        session.removeAttribute("cabecero");
        session.setAttribute("cabecero", solicitudPreparacion);
//        session.setAttribute("mostraHerramientas", new Integer(1));
    }

    public void irSiguienteAccionPagar(ActionEvent actionEvent) {
        try {
            SesionRDU obtSession = ContextUtils.obtenerSesionUsuario();
            if (null != obtSession) {
                if (this.tramiteSelected != null) {
                    lger.info("  ** VALOR A CHECAR tramiteSelected.getIdTramite() ==>  " + this.tramiteSelected.getIdTramite());
                }

                obtSession.setIdTramite(this.tramiteSelected.getIdTramite());
                obtSession.setIdMenu(0);
                obtSession.setIdTipoTramite(this.tramiteSelected.getIdTipoTramite());
                /*
                 if (obtSession.getIdMenu()==0)
                 obtSession.setIdMenu(2);
                 */
                ContextUtils.crearSesionRdu(obtSession);
//                    FacesContext.getCurrentInstance().getExternalContext().redirect("../include/salir.faces");
                
                if(getTipoPromocion()){
                    FacesContext.getCurrentInstance().getExternalContext().redirect("../../pago/generarPago.faces");
                }else{
                    FacesContext.getCurrentInstance().getExternalContext().redirect("../pago/generarPago.faces");
                }
            }



        } catch (Exception e) {
            lger.fatal("Informacion de Error: -- " + e + " -- ubicado en: " + e.getLocalizedMessage());
        }

    }

    public void ejecutarBusqueda() {
        try {
            this.muestraImgEstatus = false;
            this.tramites = this.flujosgralesViewService.extraerSolicitudesPreparacion(pUsuarios, idTipoTramite);
            if (this.tramites.size() > 0) {
                this.tramiteSelected = this.tramites.get(0);
                this.verImgTramiteEstatus();
            }

        } catch (Exception e) {
            lger.fatal("Informacion de Error: -- " + e + " -- ubicado en: " + e.getLocalizedMessage());
        }
    }

    public long getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(long idUsuario) {
        this.idUsuario = idUsuario;
    }

    public int getIdTipoTramite() {
        return idTipoTramite;
    }

    public void setIdTipoTramite(int idTipoTramite) {
        this.idTipoTramite = idTipoTramite;
    }

    public SolicitudPreparacionDto getTramiteSelected() {
        return tramiteSelected;
    }

    public void setTramiteSelected(SolicitudPreparacionDto tramiteSelected) {
        this.tramiteSelected = tramiteSelected;
    }

    public List<SolicitudPreparacionDto> getTramites() {
        return tramites;
    }

    public void setTramites(List<SolicitudPreparacionDto> tramites) {
        this.tramites = tramites;
    }

    public List<TipoTramiteDto> getFiltrosTipoTramite() {
        return filtrosTipoTramite;
    }

    public void setFiltrosTipoTramite(List<TipoTramiteDto> filtrosTipoTramite) {
        this.filtrosTipoTramite = filtrosTipoTramite;
    }

    public int getIdTipoTramiteSelected() {
        return idTipoTramiteSelected;
    }

    public void setIdTipoTramiteSelected(int idTipoTramiteSelected) {
        this.idTipoTramiteSelected = idTipoTramiteSelected;
    }

    public String getImagenEstatus() {
        return imagenEstatus;
    }

    public void setImagenEstatus(String imagenEstatus) {
        this.imagenEstatus = imagenEstatus;
    }

    public boolean isMuestraImgEstatus() {
        return muestraImgEstatus;
    }

    public void setMuestraImgEstatus(boolean muestraImgEstatus) {
        this.muestraImgEstatus = muestraImgEstatus;
    }

    public int getIdSubTipo() {
        return idSubTipo;
    }

    public void setIdSubTipo(int idSubTipo) {
        this.idSubTipo = idSubTipo;
    }

    public void verAnexos() {
        if (null != this.tramiteSelected) {
            lger.info("sin anexos");
        }

    }

    public boolean getTipoSolicitud() {
        return idTipoTramite == TipoTramiteEnum.SOL_SIT.getIdTipoTramite();
    }

    public boolean getTipoPromocion() {
        return idTipoTramite == TipoTramiteEnum.PROM_PATENTES.getIdTipoTramite();
    }
}
