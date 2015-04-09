/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.gob.impi.rdu.exposition.patentes.promociones;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.StringTokenizer;
import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import mx.gob.impi.rdu.persistence.model.CatAreaPromPatentes;
import mx.gob.impi.rdu.persistence.model.CatTipoPromPatentes;
import mx.gob.impi.rdu.persistence.model.CatTiposolicitud;
import mx.gob.impi.rdu.persistence.model.ListaExpedientePromPat;
import mx.gob.impi.rdu.persistence.model.PromocionesPatentes;
import mx.gob.impi.rdu.persistence.model.TipoPromPatByOficio;
import mx.gob.impi.rdu.service.PatentesViewServiceImpl;
import mx.gob.impi.rdu.util.Constantes;
import mx.gob.impi.rdu.util.Util;
import org.apache.log4j.Logger;

/**
 *
 * @author marisol
 */
@ManagedBean(name = "promocionPatentesMB")
@ViewScoped
public class PromocionPatentesMB implements Serializable {

    private String oficinaOficio;
    private String anioOficio;
    private String folioOficio;
    private String descripcionOficio;
    private String tipoPromocion;
    private String areaPromocion;
    private String numExpediente;
    private String registroConcedido;
    private String descripcionPromocion;
    private String oficinaExpediente;
    private String tipoExpediente;
    private String serieExpediente;
    private String folioExpediente;
    private String delimiter;
    String[] temp;
    private String plazoAdicional;
    private boolean aplicaDescuento;
    private String descuento;
    private boolean ocultar;
    private List<String> errores;
    private boolean verErrores;
    private List<CatTiposolicitud> listTiposSol = Collections.emptyList();
    private List<String> listPlazo = new ArrayList();
    // Para el Guardado
    PromocionesPatentes promocionesPatentes = new PromocionesPatentes();
    List<ListaExpedientePromPat> listaexp = new ArrayList<ListaExpedientePromPat>();
    ListaExpedientePromPat listaExpedientePromPat = new ListaExpedientePromPat();
    //Lista para mensajes 
    List<CatAreaPromPatentes> listaCatAreaPromPatentes = new ArrayList<CatAreaPromPatentes>();
    List<TipoPromPatByOficio> listaTipoPromPatByOficio = new ArrayList<TipoPromPatByOficio>();
    @ManagedProperty(value = "#{patentesViewService}")
    private PatentesViewServiceImpl patentesViewService;
    private Logger log = Logger.getLogger(this.getClass());

    @PostConstruct
    public void init() {
        inicializaTramite();

        this.ocultar = true;

        getListPlazo().add("0");
        getListPlazo().add("1");
        getListPlazo().add("2");


        agregarOpcionArea("Forma","Forma");
        agregarOpcionArea("Fondo","Fondo");

    }

    private void agregarOpcionArea(String id, String desc) {
        CatAreaPromPatentes op1 = new CatAreaPromPatentes();
        op1.setIdAreaPromPat(id);
        op1.setDescripcionPromPat(desc);
        listaCatAreaPromPatentes.add(op1);
    }

    public void mostrar() {

        System.out.println("-----------> en mostrar...");

        String idByOficio = this.oficinaOficio + "/" + this.anioOficio + "/" + this.folioOficio;

        System.out.println("---------------> idByOficio:" + idByOficio);

        String msg3 = patentesViewService.getTipoPromByOficioJms(idByOficio);

        System.out.println("------------> msg3: " + msg3);

        listaTipoPromPatByOficio = patentesViewService.getTipoPromByOficio(msg3);

        System.out.println("------------> listaTipoPromPatByOficio: " + listaTipoPromPatByOficio);

        if (listaTipoPromPatByOficio != null && !listaTipoPromPatByOficio.isEmpty()) {

            this.descripcionOficio = listaTipoPromPatByOficio.get(0).getDesOficio();
            this.descripcionPromocion = listaTipoPromPatByOficio.get(0).getDesTipoPromocion();
            this.tipoPromocion = listaTipoPromPatByOficio.get(0).getTipoPromocion();
            // Si getIdDepartamento() == 1 
            // this.areaPromocio="Fondo"
            // else
            // this.areaPromocion="Forma

            try {
                int depto = Integer.parseInt(listaTipoPromPatByOficio.get(0).getIdDepartamento());
                if (depto == 1) {
                    this.areaPromocion = "Forma";
                } else {
                    this.areaPromocion = "Fondo";
                }
            } catch (Exception e) {
                System.out.println("-- NO HAY idDepartamento --");
            }
            //this.areaPromocion = listaTipoPromPatByOficio.get(0).getIdDepartamento();
            this.numExpediente = listaTipoPromPatByOficio.get(0).getExpediente();
            this.registroConcedido = listaTipoPromPatByOficio.get(0).getConcesion();
            delimiter = "/";
            temp = numExpediente.split(delimiter);
            this.oficinaExpediente = temp[0];
            this.tipoExpediente = temp[1];
            this.serieExpediente = temp[2];
            this.folioExpediente = temp[3];
        }
    }

    public void mostrarVistaPrevia() {
//
//        GenerarReporte generarReporte = new GenerarReporte();
//        HttpSession session = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(true);
//        HttpServletRequest request = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
//       // ReportePromoPatenteDto reportePromocionPat = ReportePromoPatenteDto(tramiteProm, request.getRealPath("") + "/content/imagenes/firma_impi.png",listaSubTiposSolicitud,entidadesFederativas);
//        //ReporteRenovacionDto reporteRenovacionDto = new ReporteRenovacionDto(tramiteProm, request.getRealPath("") + "/content/imagenes/firma_impi.png",listaSubTiposSolicitud,entidadesFederativas);
//        session.removeAttribute("reporteStream");
//        session.setAttribute("reporteStream",
//                generarReporte.generaRepporte(request.getRealPath("")
//                + "/content/reportes/promocionPatentes.jasper", reportePromocionPat));
//        RequestContext context = RequestContext.getCurrentInstance();
//        context.execute("reporteDialog.show();");
//                context.execute(
//                "window.open ('http://localhost:8080/rdu-web/MostrarReporte','mywindow','menubar=1,resizable=1,width=850,height=550');");
//
        //add facesmessage
        //FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Success", "Success"));
    }

    public void inicializaTramite() {
//        tramiteProm = new TramitePromocionMarca();
//        CatSubtiposolicitud subtipoS = new CatSubtiposolicitud();
//        subtipoS.setIdSubtiposolicitud(Long.valueOf("-1"));
//        tramiteProm.setSubTipoSolicitud(subtipoS);
//        tramiteProm.setIdTramitePromocionMarca(0L);
//        tramiteProm.setIdSubtiposolicitud(24);
    }

    public String validarSolicitudCompleta() {
        String msg = "";
        //bundlePromMarcas
        if (this.oficinaOficio == null || this.oficinaOficio.length() == 0) {
            msg += "Error no capturo Oficina" + "||";
        }
        if (this.anioOficio == null || this.anioOficio.length() == 0) {
            msg += "Error falta el año" + "||";
        }
        if (this.folioOficio == null || this.folioOficio.length() == 0) {
            msg += "Error falta folio" + "||";
        }

        if (this.descripcionOficio == null || this.descripcionOficio.length() == 0) {
            msg += "Error falta descripcion" + "||";
        }
        if (this.tipoPromocion == null || this.tipoPromocion.length() == 0) {
            msg += "Error falta tipo promocion" + "||";
        }

        if (this.areaPromocion == null || this.areaPromocion.length() == 0) {
            msg += "Error falta area" + "||";
        }
        if (this.numExpediente == null || this.numExpediente.length() == 0) {
            msg += "Error falta nº de Expediente" + "||";
        }
        if (this.descripcionPromocion == null || this.descripcionPromocion.length() == 0) {
            msg += "Error falta descripcion de la promocion";
        }



        return msg;
    }

//    public void mostrar(){
////        System.out.println("Oficina: "+ this.oficinaOficio);
////        System.out.println("Año: "+ this.anioOficio);
////        System.out.println("Folio: "+ this.folioOficio);
//        
//    }
    public void convierteListaErrores(String error) {
        errores = new ArrayList<String>();
        if (error != null && error.length() != 0) {
            StringTokenizer st = new StringTokenizer(error, "||");

            while (st.hasMoreTokens()) {
                errores.add(st.nextToken());
            }

        }


    }

    public void guardadito(int finalizar) {
        String msgAviso = "";
        String msgAvisoAct = "";
        boolean resul = false;
        boolean completa = true;
        verErrores = false;


        msgAviso = validarSolicitudCompleta();
        if (msgAviso.length() != 0) {
            convierteListaErrores(msgAviso);
            verErrores = true;
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(msgAviso));

        } else {
            // FALTA LA LISTA DE EXPEDIENTES, POR LO MIENTRAS ESTA HARD CODE
            listaExpedientePromPat.setIdPromocion("51");
            listaExpedientePromPat.setExpCodOficina("PA");
            listaExpedientePromPat.setExpTipo("a");
            listaExpedientePromPat.setExpSerie("2010");
            listaExpedientePromPat.setExpNumero("08736");
            listaExpedientePromPat.setRegistro("11644");
            listaexp.add(listaExpedientePromPat);
            // TERMINA EL HARD CORE

            // CUANDO SEPAMOS DONDE SACAR EL ID PROMOCION CAMBIAR EL ACTUALIZA POR 0 EL GUARDARA LOS NUEVOS DATOS

            promocionesPatentes.setActualiza("1");



            promocionesPatentes.setIdPromocion("51"); // Preguntar donde sacar el ID Promocion
            promocionesPatentes.setIdUsuario("15"); // Preguntar donde sacar el Usuario
            promocionesPatentes.setIdTipoPromPat("86");
            promocionesPatentes.setOficioCodOficina(oficinaExpediente);
            promocionesPatentes.setOficioFolio(oficinaExpediente);
            promocionesPatentes.setOficioSerie(serieExpediente);
            promocionesPatentes.setPlazoAdicional("1");
            promocionesPatentes.setIndDescuento("1");
            promocionesPatentes.setDescripcionProm(descripcionPromocion);
            promocionesPatentes.setListaExpedientePromPat(listaexp);
            promocionesPatentes.setActualiza("1");


            String msg1 = patentesViewService.setTramitePromoPatJms(promocionesPatentes);
            System.out.println("Guardado------" + msg1);
            String msgDescripcion = patentesViewService.setTramitePromoPatJmsAdmin(msg1);
            System.out.println("Descripcion Tramite: " + msgDescripcion);
        }
    }

    // METODOS PARA CARGAS COMBOS    ====================================================
    /**
     * COMBO TIPO DE PROMOCIONES
     *
     * @return
     */
    public List<CatTipoPromPatentes> getListaCatTipoPromPatentes() {

        return patentesViewService.selectTipoPromociones();
    }

    /**
     * COMBO AÑOS
     *
     * @return the listAnio
     */
    public List<String> getListAnio() {
        return new Util().generarRangoAnios(Constantes.anioMinimoPromociones);
    }

    //GETTERS Y SETTERS
    public void setPatentesViewService(PatentesViewServiceImpl patentesViewService) {
        this.patentesViewService = patentesViewService;
    }

    public List<CatAreaPromPatentes> getListaCatAreaPromPatentes() {
        return listaCatAreaPromPatentes;
    }

    public void setListaCatAreaPromPatentes(List<CatAreaPromPatentes> listaCatAreaPromPatentes) {
        this.listaCatAreaPromPatentes = listaCatAreaPromPatentes;
    }

    public String getTipoExpediente() {
        return tipoExpediente;
    }

    public void setTipoExpediente(String tipoExpediente) {
        this.tipoExpediente = tipoExpediente;
    }

    public String getOficinaExpediente() {
        return oficinaExpediente;
    }

    public void setOficinaExpediente(String oficinaExpediente) {
        this.oficinaExpediente = oficinaExpediente;
    }

    public String getSerieExpediente() {
        return serieExpediente;
    }

    public void setSerieExpediente(String serieExpediente) {
        this.serieExpediente = serieExpediente;
    }

    public String getFolioExpediente() {
        return folioExpediente;
    }

    public void setFolioExpediente(String folioExpediente) {
        this.folioExpediente = folioExpediente;
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
     * @return the descripcionOficio
     */
    public String getDescripcionOficio() {
//         System.out.println("GET>>>>> " + descripcionOficio);
        return descripcionOficio;
    }

    /**
     * @param descripcionOficio the descripcionOficio to set
     */
    public void setDescripcionOficio(String descripcionOficio) {
        this.descripcionOficio = descripcionOficio;
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
     * @return the areaPromocion
     */
    public String getAreaPromocion() {

        //System.out.println("GET" + areaPromocion);
        return areaPromocion;
    }

    /**
     * @param areaPromocion the areaPromocion to set
     */
    public void setAreaPromocion(String areaPromocion) {
        this.areaPromocion = areaPromocion;
    }

    /**
     * @return the numExpediente
     */
    public String getNumExpediente() {
        return numExpediente;
    }

    /**
     * @param numExpediente the numExpediente to set
     */
    public void setNumExpediente(String numExpediente) {
        this.numExpediente = numExpediente;
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
     * @return the plazoAdicional
     */
    public String getPlazoAdicional() {
        return plazoAdicional;
    }

    /**
     * @param plazoAdicional the plazoAdicional to set
     */
    public void setPlazoAdicional(String plazoAdicional) {
        this.plazoAdicional = plazoAdicional;
    }

    /**
     * @return the aplicaDescuento
     */
    public boolean isAplicaDescuento() {
        return aplicaDescuento;
    }

    /**
     * @param aplicaDescuento the aplicaDescuento to set
     */
    public void setAplicaDescuento(boolean aplicaDescuento) {
        this.aplicaDescuento = aplicaDescuento;
    }

    /**
     * @return the errores
     */
    public List<String> getErrores() {
        return errores;
    }

    /**
     * @param errores the errores to set
     */
    public void setErrores(List<String> errores) {
        this.errores = errores;
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
     * @return the listTiposSol
     */
    public List<CatTiposolicitud> getListTiposSol() {
        return listTiposSol;
    }

    public boolean isOcultar() {
        return ocultar;
    }

    public void setOcultar(boolean ocultar) {
        this.ocultar = ocultar;
    }

    /**
     * @param listTiposSol the listTiposSol to set
     */
    public void setListTiposSol(List<CatTiposolicitud> listTiposSol) {
        this.listTiposSol = listTiposSol;
    }

    /**
     * @return the listPlazo
     */
    public List<String> getListPlazo() {
        return listPlazo;
    }

    /**
     * @param listPlazo the listPlazo to set
     */
    public void setListPlazo(List<String> listPlazo) {
        this.listPlazo = listPlazo;
    }

    /**
     * @return the descuento
     */
    public String getDescuento() {
        return descuento;
    }

    /**
     * @param descuento the descuento to set
     */
    public void setDescuento(String descuento) {
        this.descuento = descuento;
    }
}
