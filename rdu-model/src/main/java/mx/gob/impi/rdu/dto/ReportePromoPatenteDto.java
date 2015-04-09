/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.gob.impi.rdu.dto;

import java.util.Iterator;
import java.util.List;
import mx.gob.impi.rdu.persistence.model.CatSubtiposolicitud;
import mx.gob.impi.rdu.persistence.model.EntidadFederativa;
import mx.gob.impi.rdu.persistence.model.NumerosSolicitud;
import mx.gob.impi.rdu.persistence.model.TramitePersona;
import mx.gob.impi.rdu.persistence.model.TramitePromocionMarca;
import mx.gob.impi.rdu.util.Constantes;
import mx.gob.impi.rdu.util.Util;

/**
 *
 * @author sdiaz
 */
public class ReportePromoPatenteDto {
    
    
    private String oficinaOficio;
    private String anioOficio;
    private String folioOficio;
    private String descripcionOficio;

    private String tipoPromocion;
    private String areaPromocion;

    private String numExpediente;
    private String registroConcedido;

    private String descripcionPromocion;

    private String plazoAdicional;
    
    private Integer descuento;
    
    private String oficinaExpediente;
    private String tipoExpediente;
    private String serieExpediente;
    private String folioExpediente;
    
     private String rutaFirmaImpi;
    
    private String tipoSolicitud="";
    private String subTipoSolicitud="";
    private String nombre="";
    private String telefonoTitular="";
    private String correoTitular="";
    private String calleNotificacion="";
    private String numeroNotificacion="";
    private String coloniaNotificacion="";
    private String poblacionNotificacion="";
    private String estadoNotificacion="";
    private String codigoPostal="";
    private String telefonoNotificacion="";
    private String correoNotificacion="";
    
    private String folio="123456";
    private String fecha="14/05/2012";
    private String numero="";
    private String codigoBarras =/*Codigo de barras de prueba*/ "1234545676";
    private String expediente="";
    private String anoSolRegistro="";
    private String expedienteRegistro="";
    private String registro="";
    private String denominacion="";
    private String apoderado="";
    private String titularRegistroAnexo="";
    private String tipoSolRegistroAnexo="";
    private String anoSolRegistroAnexo="";
    private String expedienteRegistroAnexo="";
    private String registroAnexo="";
    private String denominacionAnexo="";
    private String apoderadoAnexo="";
    private String titularRegistro="";
    private String tipoSolRegistro="";

    




    public ReportePromoPatenteDto(TramitePromocionMarca tramitePromocion, String rutaFirmaImpi, List<CatSubtiposolicitud> listaSubTiposSolicitud, List<EntidadFederativa> entidadesFederativas) { //,

            llenarDatos(tramitePromocion, rutaFirmaImpi,listaSubTiposSolicitud, entidadesFederativas);
    }


    public void llenarDatos(TramitePromocionMarca tramitePromocion, String pathFirmaImpi,List<CatSubtiposolicitud> listaSubTiposSolicitud, List<EntidadFederativa> entidadesFederativas){ //

       this.rutaFirmaImpi = pathFirmaImpi;
//       this.listaSubTiposSolicitud = listaSubTiposSolicitud;
       this.setFolio(tramitePromocion.getFolio());
       this.setCodigoBarras(tramitePromocion.getCodBarras());
       this.setFecha(tramitePromocion.getFechaSysDate()== null?"" :Util.formatearFecha(tramitePromocion.getFechaSysDate(), Util.FORMATODDMMYYYYHHMMSS));

       if(tramitePromocion.getNumeros()!=null){
           List<NumerosSolicitud> registros = tramitePromocion.getNumeros();
           for(NumerosSolicitud registro: registros){
               this.numero = registro.getNumero().toString();
           }

       }
       //!= null
       if(tramitePromocion.getIdSubtiposolicitud() > 0 ){
             for (CatSubtiposolicitud temp : listaSubTiposSolicitud) {
                if (temp.getIdSubtiposolicitud().intValue() == tramitePromocion.getIdSubtiposolicitud()) {
                     this.subTipoSolicitud =temp.getDescripcion();
                    break;
                }
             }
       }

       //titular y nuevo domicilio de notificacion
       if(!tramitePromocion.getListaTramitePersona().isEmpty()){
            for (Iterator iter = tramitePromocion.getListaTramitePersona().iterator(); iter.hasNext();) {
                TramitePersona oTramitePersona = (TramitePersona) iter.next();
                if (oTramitePersona.getIdClasePersona().compareTo(Constantes.CLS_PERSONA_TITULAR)==0) {
                    if(oTramitePersona.getPersona().getNombrecompleto() != null)
                        this.nombre = oTramitePersona.getPersona().getNombrecompleto();
                     if (null != oTramitePersona.getPersona().getDatosContacto()){
                         if(oTramitePersona.getPersona().getDatosContacto().getTelefono() != null)
                            this.telefonoTitular = oTramitePersona.getPersona().getDatosContacto().getTelefono();
                         if(oTramitePersona.getPersona().getDatosContacto().getCorreoelectronico() != null)
                            this.correoTitular = oTramitePersona.getPersona().getDatosContacto().getCorreoelectronico();

                     }

               }
           }
        }

        //domicilio de notificacion
        if (tramitePromocion.getNotificacion()!=null){
            if (tramitePromocion.getNotificacion().getDomicilio()!=null) {
                    if (tramitePromocion.getNotificacion().getDomicilio().getCalle() !=null)
                       this.calleNotificacion = tramitePromocion.getNotificacion().getDomicilio().getCalle();
                    if (tramitePromocion.getNotificacion().getDomicilio().getColonia() !=null)
                       this.coloniaNotificacion = tramitePromocion.getNotificacion().getDomicilio().getColonia();
                    if (tramitePromocion.getNotificacion().getDomicilio().getCodigopostal() !=null)
                       this.codigoPostal = tramitePromocion.getNotificacion().getDomicilio().getCodigopostal();
                    if (tramitePromocion.getNotificacion().getDomicilio().getPoblacion() !=null)
                       this.poblacionNotificacion = tramitePromocion.getNotificacion().getDomicilio().getPoblacion();
                    if (tramitePromocion.getNotificacion().getDomicilio().getIdPais().compareTo(Constantes.ID_PAIS_MEXICO)==0)
                        this.estadoNotificacion = descripcionEntidad(entidadesFederativas, Integer.parseInt(tramitePromocion.getNotificacion().getDomicilio().getIdEntidad()));
             }
             //datos contacto
             if (tramitePromocion.getNotificacion().getDatosContacto()!=null){
                 if(tramitePromocion.getNotificacion().getDatosContacto().getCorreoelectronico() !=null)
                    this.correoNotificacion = tramitePromocion.getNotificacion().getDatosContacto().getCorreoelectronico();
                 if (tramitePromocion.getNotificacion().getDatosContacto().getTelefono() !=null)
                    this.telefonoNotificacion = tramitePromocion.getNotificacion().getDatosContacto().getTelefono();
             }
         }
    }

       public String descripcionEntidad(List<EntidadFederativa> entidadesFederativas,int idEntidad){
        String DescEntidad = "";
        for (EntidadFederativa tempEntidad : entidadesFederativas) {
            if ( tempEntidad.getIdEntidadFederativa().intValue() == idEntidad) {
                DescEntidad = tempEntidad.getNombre();
                break;
            }
        }
        return DescEntidad;
    }

        
    
      


    /**
     * Getters and setters
     */
    
    
    public String getOficinaOficio() {
        return oficinaOficio;
    }

    public void setOficinaOficio(String oficinaOficio) {
        this.oficinaOficio = oficinaOficio;
    }

    public String getAnioOficio() {
        return anioOficio;
    }

    public String getFolioOficio() {
        return folioOficio;
    }

    public void setFolioOficio(String folioOficio) {
        this.folioOficio = folioOficio;
    }

  

    public String getDescripcionOficio() {
        return descripcionOficio;
    }

    public void setDescripcionOficio(String descripcionOficio) {
        this.descripcionOficio = descripcionOficio;
    }

    public String getTipoPromocion() {
        return tipoPromocion;
    }

    public void setTipoPromocion(String tipoPromocion) {
        this.tipoPromocion = tipoPromocion;
    }

    public String getAreaPromocion() {
        return areaPromocion;
    }

  
    public void setAreaPromocion(String areaPromocion) {
        this.areaPromocion = areaPromocion;
    }

    public String getNumExpediente() {
        return numExpediente;
    }

   
    public void setNumExpediente(String numExpediente) {
        this.numExpediente = numExpediente;
    }

   
    public String getRegistroConcedido() {
        return registroConcedido;
    }

    
    public void setRegistroConcedido(String registroConcedido) {
        this.registroConcedido = registroConcedido;
    }

   
    public String getDescripcionPromocion() {
        return descripcionPromocion;
    }

    public void setDescripcionPromocion(String descripcionPromocion) {
        this.descripcionPromocion = descripcionPromocion;
    }

    public String getPlazoAdicional() {
        return plazoAdicional;
    }

    public void setPlazoAdicional(String plazoAdicional) {
        this.plazoAdicional = plazoAdicional;
    }

    public Integer getDescuento() {
        return descuento;
    }

    public void setDescuento(Integer descuento) {
        this.descuento = descuento;
    }

    public String getOficinaExpediente() {
        return oficinaExpediente;
    }

    public void setOficinaExpediente(String oficinaExpediente) {
        this.oficinaExpediente = oficinaExpediente;
    }

    public String getTipoExpediente() {
        return tipoExpediente;
    }

    public void setTipoExpediente(String tipoExpediente) {
        this.tipoExpediente = tipoExpediente;
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
    
       public String getRutaFirmaImpi() {
        return rutaFirmaImpi;
    }

    public void setRutaFirmaImpi(String rutaFirmaImpi) {
        this.rutaFirmaImpi = rutaFirmaImpi;
    }

    

    


       
       //========
    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
    
    public String getTelefonoNotificacion() {
        return telefonoNotificacion;
    }
   
    public void setTelefonoNotificacion(String telefonoNotificacion) {
        this.telefonoNotificacion = telefonoNotificacion;
    }

    public String getTipoSolicitud() {
        return tipoSolicitud;
    }

    public void setTipoSolicitud(String tipoSolicitud) {
        this.tipoSolicitud = tipoSolicitud;
    }

    public String getSubTipoSolicitud() {
        return subTipoSolicitud;
    }

    public void setSubTipoSolicitud(String subTipoSolicitud) {
        this.subTipoSolicitud = subTipoSolicitud;
    }

    public String getTelefonoTitular() {
        return telefonoTitular;
    }

    public void setTelefonoTitular(String telefonoTitular) {
        this.telefonoTitular = telefonoTitular;
    }

    public String getCorreoTitular() {
        return correoTitular;
    }

    public void setCorreoTitular(String correoTitular) {
        this.correoTitular = correoTitular;
    }

    public String getCalleNotificacion() {
        return calleNotificacion;
    }

    public void setCalleNotificacion(String calleNotificacion) {
        this.calleNotificacion = calleNotificacion;
    }

    public String getNumeroNotificacion() {
        return numeroNotificacion;
    }

    public void setNumeroNotificacion(String numeroNotificacion) {
        this.numeroNotificacion = numeroNotificacion;
    }

    public String getColoniaNotificacion() {
        return coloniaNotificacion;
    }

    public void setColoniaNotificacion(String coloniaNotificacion) {
        this.coloniaNotificacion = coloniaNotificacion;
    }

    public String getCorreoNotificacion() {
        return correoNotificacion;
    }
   
    public void setCorreoNotificacion(String correoNotificacion) {
        this.correoNotificacion = correoNotificacion;
    }
   
 

    public String getPoblacionNotificacion() {
        return poblacionNotificacion;
    }

    public void setPoblacionNotificacion(String poblacionNotificacion) {
        this.poblacionNotificacion = poblacionNotificacion;
    }

    public String getEstadoNotificacion() {
        return estadoNotificacion;
    }

    public void setEstadoNotificacion(String estadoNotificacion) {
        this.estadoNotificacion = estadoNotificacion;
    }

    public String getCodigoPostal() {
        return codigoPostal;
    }

    public void setCodigoPostal(String codigoPostal) {
        this.codigoPostal = codigoPostal;
    }

    public String getFolio() {
        return folio;
    }

    public void setFolio(String folio) {
        this.folio = folio;
    }

    public String getFecha() {
        return fecha;
    }
  
    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public String getNumero() {
        return numero;
    }

    public void setNumero(String numero) {
        this.numero = numero;
    }

       public String getCodigoBarras() {
        return codigoBarras;
    }

    public void setCodigoBarras(String codigoBarras) {
        this.codigoBarras = codigoBarras;
    }

    public String getExpediente() {
        return expediente;
    }

    public void setExpediente(String expediente) {
        this.expediente = expediente;
    }

    public String getAnoSolRegistro() {
        return anoSolRegistro;
    }

    
    public void setAnoSolRegistro(String anoSolRegistro) {
        this.anoSolRegistro = anoSolRegistro;
    }

    public String getExpedienteRegistro() {
        return expedienteRegistro;
    }

    public void setExpedienteRegistro(String expedienteRegistro) {
        this.expedienteRegistro = expedienteRegistro;
    }

    public String getRegistro() {
        return registro;
    }

    public void setRegistro(String registro) {
        this.registro = registro;
    }

    public String getDenominacion() {
        return denominacion;
    }

    public void setDenominacion(String denominacion) {
        this.denominacion = denominacion;
    }

    public String getApoderado() {
        return apoderado;
    }

    public void setApoderado(String apoderado) {
        this.apoderado = apoderado;
    }

    public String getTitularRegistroAnexo() {
        return titularRegistroAnexo;
    }

    public void setTitularRegistroAnexo(String titularRegistroAnexo) {
        this.titularRegistroAnexo = titularRegistroAnexo;
    }

    public String getTipoSolRegistroAnexo() {
        return tipoSolRegistroAnexo;
    }

    public void setTipoSolRegistroAnexo(String tipoSolRegistroAnexo) {
        this.tipoSolRegistroAnexo = tipoSolRegistroAnexo;
    }

    public String getAnoSolRegistroAnexo() {
        return anoSolRegistroAnexo;
    }

    public void setAnoSolRegistroAnexo(String anoSolRegistroAnexo) {
        this.anoSolRegistroAnexo = anoSolRegistroAnexo;
    }

    public String getExpedienteRegistroAnexo() {
        return expedienteRegistroAnexo;
    }

    public void setExpedienteRegistroAnexo(String expedienteRegistroAnexo) {
        this.expedienteRegistroAnexo = expedienteRegistroAnexo;
    }

    public String getRegistroAnexo() {
        return registroAnexo;
    }

    public void setRegistroAnexo(String registroAnexo) {
        this.registroAnexo = registroAnexo;
    }

    public String getDenominacionAnexo() {
        return denominacionAnexo;
    }

    public void setDenominacionAnexo(String denominacionAnexo) {
        this.denominacionAnexo = denominacionAnexo;
    }

    public String getApoderadoAnexo() {
        return apoderadoAnexo;
    }

    public void setApoderadoAnexo(String apoderadoAnexo) {
        this.apoderadoAnexo = apoderadoAnexo;
    }

    public String getTitularRegistro() {
        return titularRegistro;
    }

    public void setTitularRegistro(String titularRegistro) {
        this.titularRegistro = titularRegistro;
    }

    public String getTipoSolRegistro() {
        return tipoSolRegistro;
    }

    public void setTipoSolRegistro(String tipoSolRegistro) {
        this.tipoSolRegistro = tipoSolRegistro;
    }

    

    
}