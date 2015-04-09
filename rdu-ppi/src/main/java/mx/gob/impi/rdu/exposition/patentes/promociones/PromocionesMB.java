/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.gob.impi.rdu.exposition.patentes.promociones;

import java.io.ByteArrayOutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;
import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import mx.gob.impi.rdu.dto.PromoventeDto;
import mx.gob.impi.rdu.dto.ReportePromoPatenteDto;
import mx.gob.impi.rdu.dto.ReportePromocionesDto;
import mx.gob.impi.rdu.dto.SolicitudPreparacionDto;
import mx.gob.impi.rdu.exposition.SesionRDU;
import mx.gob.impi.rdu.exposition.flujosGenerales.reporte.GenerarReporte;
import mx.gob.impi.rdu.persistence.model.CatTipopersona;
import mx.gob.impi.rdu.persistence.model.Datoscontacto;
import mx.gob.impi.rdu.persistence.model.Domicilio;
import mx.gob.impi.rdu.persistence.model.Pais;
import mx.gob.impi.rdu.persistence.model.Persona;
import mx.gob.impi.rdu.persistence.model.TramitePromocionPatente;
import mx.gob.impi.rdu.service.PromocionesServiceImpl;
import mx.gob.impi.rdu.util.Constantes;
import mx.gob.impi.rdu.util.ContextUtils;
import mx.gob.impi.rdu.util.PromocionNoExisteException;
import mx.gob.impi.rdu.util.SolicitudNoExisteException;
import mx.gob.impi.rdu.util.TipoTramiteEnum;
import mx.gob.impi.sagpat.persistence.model.ResultadoOficioPromocion;
import mx.gob.impi.sagpat.persistence.model.TramiteOficio;
import org.primefaces.context.RequestContext;

/**
 *
 * @author Alejandro Rodríguez Vizcaíno
 */
@ManagedBean(name = "promocionesMB")
@ViewScoped
public class PromocionesMB implements Serializable {

    private static String MESSAGE_BUNDLE = "mx.gob.impi.rdu.i18n.promociones_patentes";
    private ResourceBundle bundle;
    @ManagedProperty(value = "#{promocionesService}")
    private PromocionesServiceImpl promocionesService;
    private String oficinaOficio;
    private String anioOficio;
    private String folioOficio;
    private String descripcionOficio;
    private String tipoPromocion;
    private String descPromocion;
    private String area;
    private String oficinaExpediente;
    private String tipoExpediente;
    private String serieExpediente;
    private String folioExpediente;
    private String registroConcedido;
    private int tipoSolicitante;
    private boolean mostrarPlazoAdicional;
    private boolean mostrarDescuento;
    private List<String> errores;
    private boolean verErrores;
    private Integer plazoAdicional;
    private boolean descuento;
    private String descripcionPromocion;
    private Long idPromocionCargada;
    private String anioOficioCargado;
    private String folioOficioCargado;
    private boolean revisionFinalizaCaptura;
    private Persona promovente = new Persona();
    private HttpSession session = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(true);
    private Boolean vistaPrevia = false;

    @PostConstruct
    public void init() {

        SesionRDU obtSession = ContextUtils.obtenerSesionUsuario();
        promovente = obtenerPromovente(obtSession);

        // Cargar mensajes
        bundle = ResourceBundle.getBundle(MESSAGE_BUNDLE);
        errores = new ArrayList();


        ExternalContext context = FacesContext.getCurrentInstance().getExternalContext();
        HttpServletRequest req = (HttpServletRequest) context.getRequest();
        SolicitudPreparacionDto promocion = (SolicitudPreparacionDto) req.getAttribute("promocion");

        // Por si se seleccionó del listado y está INCOMPLETA
        if (promocion != null) {

            setIdPromocionCargada(promocion.getIdPromocion());

            if (promocion.isTieneOficio()) {
                oficinaOficio = promocion.getOficioCodOficina();
                anioOficio = anioOficioCargado = promocion.getOficioSerie();
                folioOficio = folioOficioCargado = promocion.getOficioFolio();
                descripcionOficio = promocion.getDescOficio();

                descPromocion = promocion.getDescTipoPromocion();
            }

            tipoPromocion = generarTipoPromocion(promocion.getIdTipoPromocion());
            area = promocion.getDescripcionArea();

            oficinaExpediente = promocion.getCodOficinaExped();
            tipoExpediente = promocion.getTipExped();
            serieExpediente = promocion.getSerExped();
            folioExpediente = promocion.getNumExped();

            registroConcedido = promocion.getNumConcesion();

            descripcionPromocion = promocion.getDescPromocion();
            
            tipoSolicitante = promocion.getTipoSolicitante();

            if (promocion.getMostrarDescuento() != null && promocion.getMostrarDescuento() > 0) {
                mostrarDescuento = true;
                descuento = (promocion.getDescuento() != null && promocion.getDescuento() > 0);
            }

            if (promocion.getMostrarPlazoAdicional() != null && promocion.getMostrarPlazoAdicional() > 0) {
                mostrarPlazoAdicional = true;
                plazoAdicional = promocion.getPlazoAdicional();
            }
        }

        System.out.println("------------------> en init PromocionesMB...");
        System.out.println("-------------> promocion: " + promocion);
    }

    public Persona obtenerPromovente(SesionRDU obtSession) {
        Persona prom = new Persona();
        PromoventeDto promoventeDto = null;
        if (obtSession != null) {
            promoventeDto = obtSession.getPromovente();
            Domicilio domicilio = new Domicilio();
            Datoscontacto datosContacto = new Datoscontacto();
            prom.setDomicilioObj(domicilio);
            prom.setDatosContacto(datosContacto);
            domicilio.setPais(new Pais());
            prom.setTipoPersona(new CatTipopersona());
            prom.setIdSolicitante((long) promoventeDto.getId_promovente());
            prom.setIdUsuarioFirmante((long) promoventeDto.getId_promovente());
            prom.setNombrecompleto(promoventeDto.getNombre().toUpperCase() + " " + promoventeDto.getApaterno().toUpperCase() + " " + promoventeDto.getAmaterno().toUpperCase());
            domicilio.setIdPais(Constantes.ID_PAIS);
            domicilio.getPais().setIdPais(Constantes.ID_PAIS);
            domicilio.getPais().setNombre(Constantes.NOMBRE_PAIS);
            domicilio.getPais().setNacionalidad(Constantes.NOMBRE_NACIONALIDAD);
            prom.setNacionalidad(domicilio.getPais());
            domicilio.setIdEntidad(promoventeDto.getId_estado() + "");
            domicilio.getEntidad().setIdEntidadFederativa(promoventeDto.getId_estado());
            domicilio.getEntidad().setNombre(promoventeDto.getDescEstado().toUpperCase());
            prom.getTipoPersona().setIdTipopersona((short) promoventeDto.getTipo_persona());
            domicilio.setCodigopostal(promoventeDto.getCodigo_postal().toString());
            domicilio.setPoblacion(promoventeDto.getDescMunicipio().toUpperCase());
            domicilio.setColonia(promoventeDto.getColonia().toUpperCase());
            domicilio.setCalle(promoventeDto.getCalle_numero().toUpperCase()
                    + (promoventeDto.getNumero_exterior() != null
                    ? promoventeDto.getNumero_exterior().length() > 0
                    ? " " + promoventeDto.getNumero_exterior() : "" : "")
                    + (promoventeDto.getNumero_interior() != null
                    ? promoventeDto.getNumero_interior().length() > 0
                    ? " " + promoventeDto.getNumero_interior() : "" : ""));
            datosContacto.setTelefono(promoventeDto.getTelefono().toUpperCase());
            datosContacto.setCorreoelectronico(promoventeDto.getEmail());
            datosContacto.setFax(promoventeDto.getFax());
        }
        return prom;
    }

    private String generarTipoPromocion(Long idTipoPromocion) {
        String ceros = "";
        if (idTipoPromocion < 10) {
            ceros = "00";
        } else if (idTipoPromocion < 100) {
            ceros = "0";
        }
        return ceros + idTipoPromocion;
    }

    // Regresa un mensaje del archivo de propiedades
    private String getMensaje(String codigo) {
        return bundle.getString(codigo);
    }

    // Regresa un mensaje de error
    private String getMensajeError(String codigo) {
        return getMensaje("mensaje.error." + codigo);
    }

    // Regresa un mensaje de éxito
    private String getMensajeExito(String codigo) {
        return getMensaje("mensaje.exito." + codigo);
    }

    private void clearCampos() {
        setIdPromocionCargada(null);
        anioOficioCargado = null;
        folioOficioCargado = null;
        descripcionOficio = "";
        descPromocion = "";
        tipoPromocion = null;
        area = "";
        oficinaExpediente = "";
        tipoExpediente = "";
        serieExpediente = "";
        folioExpediente = "";
        registroConcedido = "";
        descripcionPromocion = "";
        mostrarPlazoAdicional = false;
        mostrarDescuento = false;
    }

    private boolean validarCampoRequerido(String valor, String codigoError) {
        if (valor == null) {
            addError(codigoError);
            return false;
        }

        if (valor.trim().length() == 0) {
            addError(codigoError);
            return false;
        }
        return true;
    }

    private boolean validar() {
        boolean valido = true;

        if (!validarCampoRequerido(tipoPromocion, "tipoPromocion")) {
            valido = false;
        }

        if (!validarCampoRequerido(oficinaExpediente, "oficinaExpediente")) {
            valido = false;
        }

        if (!validarCampoRequerido(tipoExpediente, "tipoExpediente")) {
            valido = false;
        }

        if (!validarCampoRequerido(serieExpediente, "serieExpediente")) {
            valido = false;
        } else {
            if (!esNumero(serieExpediente.trim())) {
                addError("serieExpedienteNumero");
                valido = false;
            }
        }

        if (!validarCampoRequerido(folioExpediente, "folioExpediente")) {
            valido = false;
        } else {
            if (!esNumero(folioExpediente.trim())) {
                addError("folioExpedienteNumero");
                valido = false;
            }
        }

        if (registroConcedido != null && registroConcedido.trim().length() > 0) {
            if (!esNumero(registroConcedido.trim())) {
                addError("registroConcedidoNumero");
                valido = false;
            }
        }

        if (!validarCampoRequerido(descripcionPromocion, "descripcionPromocion")) {
            valido = false;
        }

        return valido;
    }

    private boolean esNumero(String s) {
        try {
            Integer.parseInt(s);
        } catch (Exception e) {
            return false;
        }
        return true;
    }

    public void guardar(int finalizar) {
        
        System.out.println("----------> En guardar...");

        clearErrores();

        if (!validar()) {
            return;
        }

        boolean actualizando = false;

        try {
            TramitePromocionPatente promocion = new TramitePromocionPatente();

            if (getIdPromocionCargada() != null) {
                actualizando = true;
                promocion.setIdPromocion(getIdPromocionCargada().intValue());
            }

            // TODO: EL estatus depende de si guardaron o finalizaron la captura
            // TODO: Si se está actualizando... checar el estatus...
            // debe de ser menor que 3... si es 3 mandar un mensaje de que
            // no se puede actualizar porque ya fue pagada

            // TODO: Actualizar la promoción a estatus 3 cuando haya sido pagada
            promocion.setIdEstatus(1);
            promocion.setIndActivo((short) 1);

            promocion.setOficioCodOficina(oficinaOficio);
            promocion.setOficioSerie(anioOficioCargado);
            promocion.setOficioFolio(folioOficioCargado);

            promocion.setDescOficio(descripcionOficio);

            promocion.setPlazoAdicional(plazoAdicional);
            promocion.setDescuento(descuento ? (short) 1 : (short) 0);
            promocion.setMostrarDescuento(mostrarDescuento ? (short) 1 : (short) 0);
            promocion.setMostrarPlazoAdicional(mostrarPlazoAdicional ? (short) 1 : (short) 0);

            promocion.setIdTipoPromocionPatente(Long.parseLong(tipoPromocion));
            promocion.setArea(area);
            promocion.setDescripcionPromocion(descripcionPromocion);

            promocion.setCodOficinaExped(oficinaExpediente);
            promocion.setTipExped(tipoExpediente);
            promocion.setSerExped(Integer.parseInt(serieExpediente));
            promocion.setNumExped(Integer.parseInt(folioExpediente));
            promocion.setTipoSolicitante(tipoSolicitante);

            if (registroConcedido != null && registroConcedido.trim().length() > 0) {
                promocion.setNumConcesion(Integer.parseInt(registroConcedido));
            }

            System.out.println("-------------> idPromovente: " + promovente.getIdSolicitante());
            promocion.setIdUsuariocaptura(promovente.getIdSolicitante().intValue());

            int idPromocion = promocionesService.guardarPromocionPatente(promocion);
            idPromocionCargada = new Long(idPromocion);

            System.out.println("-----------------> idPromocion: " + idPromocion);

            String mensaje;
            // Mostrar el mensaje de guardado
            if (actualizando) {
                mensaje = getMensajeExito("actualizar");
            } else {
                mensaje = getMensajeExito("guardar");
            }
            
            if (finalizar == Constantes.EXISTE) {
//                        msgAviso = validarSolicitudCompleta();
//                        if (msgAviso.length() == 0) {
//                            log.info("solicitud completa");
                SesionRDU obtSession = ContextUtils.obtenerSesionUsuario();
                if (null != obtSession) {
                    obtSession.setIdTramite(idPromocionCargada);
                    obtSession.setIdTipoTramite(TipoTramiteEnum.PROM_PATENTES.getIdTipoTramite());
                    obtSession.setSubTipo(Integer.parseInt(tipoPromocion));
                    obtSession.setDescuento(promocion.getDescuento());
                    obtSession.setTipoSolicitante(promocion.getTipoSolicitante());
                    
                    ContextUtils.crearSesionRdu(obtSession);
                }

                System.out.println("----------> Redireccionando a Pagos...");
                FacesContext.getCurrentInstance().getExternalContext().redirect("/rdu-ppi/content/restricted/pago/Pagos.faces");
                verErrores = false;

//                        }
                    }

            FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, mensaje, mensaje);
            FacesContext.getCurrentInstance().addMessage(null, message);

        } catch (SolicitudNoExisteException e) {
            addError("solicitudNoExiste");
        } catch (PromocionNoExisteException e) {
            addError("promocionNoExiste");
        } catch (Exception e) {
            // Mostrar el mensaje de error
            if (actualizando) {
                addError("actualizar");
            } else {
                addError("guardar");
            }
        }
    }

    public void mostrarVistaPrevia() {
        System.out.println("En mostrarVistaPrevia...");
        GenerarReporte generarReporte = new GenerarReporte();
        HttpServletRequest request = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
        session.removeAttribute("reporteStream");
        
        String numExpediente = oficinaExpediente+"/"+tipoExpediente+"/"+serieExpediente+"/"+folioExpediente;
        
        ReportePromocionesDto reportePromociones = new ReportePromocionesDto();
        reportePromociones.setArea(area);
        reportePromociones.setDescripcionOficio(descripcionPromocion);
        reportePromociones.setTipoPromocion(descPromocion);
        reportePromociones.setNumExpediente(numExpediente);
        reportePromociones.setFecha(new Date());
        
        ByteArrayOutputStream reporteFinal = generarReporte.generaRepporte(request.getRealPath("") + "/content/reportes/promo_con_contestacion.jasper", 
                                         reportePromociones);
        
        
        session.setAttribute("reporteStream", reporteFinal);
        vistaPrevia = true;
        RequestContext context = RequestContext.getCurrentInstance();
        context.execute("reportePromocionesDialog.show();");
    }

    public void buscarOficioAResponder() {

        clearErrores();
        clearCampos();

        double serOficio;
        double numOficio;

        System.out.println("-----------> anioOficio: " + anioOficio);

        try {
            serOficio = Double.parseDouble(anioOficio);
        } catch (Exception e) {
            addError("anioNumerico");
            return;
        }

        System.out.println("--------------> folioOficio: " + folioOficio);

        try {
            numOficio = Double.parseDouble(folioOficio);
        } catch (Exception e) {
            addError("folioNumerico");
            return;
        }

        // Para que no se puedan guardar/actualizar valores diferentes a los cargados
        anioOficioCargado = anioOficio;
        folioOficioCargado = folioOficio;

        TramiteOficio consulta = new TramiteOficio(oficinaOficio, serOficio, numOficio);

        ResultadoOficioPromocion resultado = promocionesService.obtenerDatosOficioPromocion(consulta);

        if (resultado != null) {
            // Checar si tiene errores
            if (resultado.getTieneError()) {
                // Mostrar los errores
                addError(resultado.getCodigoError());

            } else {
                descripcionOficio = resultado.getDescOficio();
                tipoPromocion = resultado.getTipoPromocion();
                descPromocion = resultado.getDescPromocion();
                area = resultado.getArea();
                oficinaExpediente = resultado.getCodOficinaExped();
                tipoExpediente = resultado.getTipoExped();
                serieExpediente = resultado.getSerExped();
                folioExpediente = resultado.getNumExped();
                registroConcedido = resultado.getNumConcesion();
                mostrarPlazoAdicional = resultado.isPlazoAdicional();
                mostrarDescuento = resultado.isAplicaDescuento();
                tipoSolicitante = resultado.getTipoSolicitante();
            }
//            System.out.println("-----------> tipoTramite: " + resultado.getTipoTramite());
//            System.out.println("-----------> numTramite: " + resultado.getNumTramite());
//            System.out.println("-----------> codStatus: " + resultado.getCodStatus());
//            System.out.println("-----------> tipOficio: " + resultado.getTipoOficio());
        } else {
            // Ésto nunca debería de suceder
            System.out.println("======== resultado nulo!!!!");
            addError("buscar");
        }

    }

    public void crearCabeceroTramite() {

        // Debe de haber un tipo de promoción seleccionado
        if (!validarCampoRequerido(tipoPromocion, "tipoPromocion")) {
            return;
        }

        // El tipo de la promoción (de impostor)
        Long idSubtipoSolicitud = Long.parseLong(tipoPromocion) + 10000;
        System.out.println("-----------> idSubtipoSolicitud: " + idSubtipoSolicitud);

        SolicitudPreparacionDto promocion = new SolicitudPreparacionDto();
        promocion.setIdSubtiposolicitud(idSubtipoSolicitud);

        if (getIdPromocionCargada() != null) {
            promocion.setIdTramite(idPromocionCargada);
            promocion.setIdPromocion(idPromocionCargada);
        } else {
            promocion.setIdTramite(new Long(0));
        }

        session.setAttribute("cabecero", promocion);
        session.setAttribute("mostraHerramientas", new Integer(1));
    }

    /**
     * @return the oficinaOficio
     */
    public String getOficinaOficio() {
        return oficinaOficio = Constantes.oficinaDefaultPromociones;
    }

    /**
     * @param oficinaOficio the oficinaOficio to set
     */
    public void setOficinaOficio(String oficinaOficio) {
        this.oficinaOficio = oficinaOficio;
    }

    /**
     * @return the anioOficio
     */
    public String getAnioOficio() {
        return anioOficio;
    }

    /**
     * @param anioOficio the anioOficio to set
     */
    public void setAnioOficio(String anioOficio) {
        this.anioOficio = anioOficio;
    }

    /**
     * @return the folioOficio
     */
    public String getFolioOficio() {
        return folioOficio;
    }

    /**
     * @param folioOficio the folioOficio to set
     */
    public void setFolioOficio(String folioOficio) {
        this.folioOficio = folioOficio;
    }

    /**
     * @param promocionesService the promocionesService to set
     */
    public void setPromocionesService(PromocionesServiceImpl promocionesService) {
        this.promocionesService = promocionesService;
    }

    /**
     * @return the errores
     */
    public List<String> getErrores() {
        return errores;
    }

    private void addError(String error) {
        errores.add(getMensajeError(error));
        verErrores = true;
    }

    private void clearErrores() {
        errores.clear();
        verErrores = false;
    }

    /**
     * @return the verErrores
     */
    public boolean isVerErrores() {
        return verErrores;
    }

    /**
     * @param verErrores the verErrores to set
     */
    public void setVerErrores(boolean verErrores) {
        this.verErrores = verErrores;
    }

    /**
     * @return the descripcionOficio
     */
    public String getDescripcionOficio() {
        return descripcionOficio;
    }

    /**
     * @param descripcionOficio the descripcionOficio to set
     */
    public void setDescripcionOficio(String descripcionOficio) {
        this.descripcionOficio = descripcionOficio;
    }

    /**
     * @return the descPromocion
     */
    public String getDescPromocion() {
        return descPromocion;
    }

    /**
     * @param descPromocion the descPromocion to set
     */
    public void setDescPromocion(String descPromocion) {
        this.descPromocion = descPromocion;
    }

    /**
     * @return the area
     */
    public String getArea() {
        return area;
    }

    /**
     * @param area the area to set
     */
    public void setArea(String area) {
        this.area = area;
    }

    /**
     * @return the oficinaExpediente
     */
    public String getOficinaExpediente() {
        return oficinaExpediente;
    }

    /**
     * @param oficinaExpediente the oficinaExpediente to set
     */
    public void setOficinaExpediente(String oficinaExpediente) {
        this.oficinaExpediente = oficinaExpediente;
    }

    /**
     * @return the tipoExpediente
     */
    public String getTipoExpediente() {
        return tipoExpediente;
    }

    /**
     * @param tipoExpediente the tipoExpediente to set
     */
    public void setTipoExpediente(String tipoExpediente) {
        this.tipoExpediente = tipoExpediente;
    }

    /**
     * @return the serieExpediente
     */
    public String getSerieExpediente() {
        return serieExpediente;
    }

    /**
     * @param serieExpediente the serieExpediente to set
     */
    public void setSerieExpediente(String serieExpediente) {
        this.serieExpediente = serieExpediente;
    }

    /**
     * @return the folioExpediente
     */
    public String getFolioExpediente() {
        return folioExpediente;
    }

    /**
     * @param folioExpediente the folioExpediente to set
     */
    public void setFolioExpediente(String folioExpediente) {
        this.folioExpediente = folioExpediente;
    }

    /**
     * @return the registroConcedido
     */
    public String getRegistroConcedido() {
        return registroConcedido;
    }

    /**
     * @param registroConcedido the registroConcedido to set
     */
    public void setRegistroConcedido(String registroConcedido) {
        this.registroConcedido = registroConcedido;
    }

    /**
     * @return the mostrarPlazoAdicional
     */
    public boolean isMostrarPlazoAdicional() {
        return mostrarPlazoAdicional;
    }

    /**
     * @param mostrarPlazoAdicional the mostrarPlazoAdicional to set
     */
    public void setMostrarPlazoAdicional(boolean mostrarPlazoAdicional) {
        this.mostrarPlazoAdicional = mostrarPlazoAdicional;
    }

    /**
     * @return the mostrarDescuento
     */
    public boolean isMostrarDescuento() {
        return mostrarDescuento;
    }

    /**
     * @param mostrarDescuento the mostrarDescuento to set
     */
    public void setMostrarDescuento(boolean mostrarDescuento) {
        this.mostrarDescuento = mostrarDescuento;
    }

    /**
     * @return the tipoPromocion
     */
    public String getTipoPromocion() {
        return tipoPromocion;
    }

    /**
     * @param tipoPromocion the tipoPromocion to set
     */
    public void setTipoPromocion(String tipoPromocion) {
        this.tipoPromocion = tipoPromocion;
    }

    /**
     * @return the plazoAdicional
     */
    public Integer getPlazoAdicional() {
        return plazoAdicional;
    }

    /**
     * @param plazoAdicional the plazoAdicional to set
     */
    public void setPlazoAdicional(Integer plazoAdicional) {
        this.plazoAdicional = plazoAdicional;
    }

    /**
     * @return the descuento
     */
    public boolean getDescuento() {
        return descuento;
    }

    /**
     * @param descuento the descuento to set
     */
    public void setDescuento(boolean descuento) {
        this.descuento = descuento;
    }

    /**
     * @return the descripcionPromocion
     */
    public String getDescripcionPromocion() {
        return descripcionPromocion;
    }

    /**
     * @param descripcionPromocion the descripcionPromocion to set
     */
    public void setDescripcionPromocion(String descripcionPromocion) {
        this.descripcionPromocion = descripcionPromocion;
    }

    /**
     * @return the idPromocionCargada
     */
    public Long getIdPromocionCargada() {
        return idPromocionCargada;
    }

    /**
     * @param idPromocionCargada the idPromocionCargada to set
     */
    public void setIdPromocionCargada(Long idPromocionCargada) {
        this.idPromocionCargada = idPromocionCargada;
    }

    /**
     * @return the revisionFinalizaCaptura
     */
    public boolean isRevisionFinalizaCaptura() {
        return revisionFinalizaCaptura;
    }

    /**
     * @param revisionFinalizaCaptura the revisionFinalizaCaptura to set
     */
    public void setRevisionFinalizaCaptura(boolean revisionFinalizaCaptura) {
        this.revisionFinalizaCaptura = revisionFinalizaCaptura;
    }

    /**
     * @return the promovente
     */
    public Persona getPromovente() {
        return promovente;
    }

    /**
     * @param promovente the promovente to set
     */
    public void setPromovente(Persona promovente) {
        this.promovente = promovente;
    }

    /**
     * @return the tipoSolicitante
     */
    public int getTipoSolicitante() {
        return tipoSolicitante;
    }

    /**
     * @param tipoSolicitante the tipoSolicitante to set
     */
    public void setTipoSolicitante(int tipoSolicitante) {
        this.tipoSolicitante = tipoSolicitante;
    }

    public Boolean getVistaPrevia() {
        return vistaPrevia;
    }

    public void setVistaPrevia(Boolean vistaPrevia) {
        this.vistaPrevia = vistaPrevia;
    }
    
}
