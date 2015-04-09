package mx.gob.impi.rdu.persistence.model;

import java.io.Serializable;
import mx.gob.impi.rdu.util.Util;

public class Persona implements Serializable {

    private Long idSolicitante;
    private String nombrecompleto;
    private String nombrecompletotmp;
    private String nombreConcatenado;
    private Long idNacionalidad;
    private Short idTipopersona;
    private Long idDatoscontacto;
    private Long idDomiclio;
    private Domicilio domicilioObj;
    private Datoscontacto datosContacto = new Datoscontacto();
    private Pais pais;
    private CatTipopersona tipoPersona;
    private CatTipoSolicitante tipoSolicitante;
    private Pais nacionalidad;
    private Long idTipoSolicitante;
    private Short inventor;
    private String rfc;
    private String rgp;
    private Short descuento;
    private boolean aplicarDescuento;
    private Short principal;
    private Long idUsuarioFirmante;
    private String primerApellido;
    private String segundoApellido;
    private Short orden;

    //IJZA Se agrega variable para saber si es Apoderado o Mandatorio 23/02/2015
    
    private Boolean esMandatorio;

    public boolean isAplicarDescuento() {
        return aplicarDescuento;
    }

    public void setAplicarDescuento(boolean aplicarDescuento) {
        this.aplicarDescuento = aplicarDescuento;
    }

    public Long getIdTipoSolicitante() {
        return idTipoSolicitante;
    }

    public void setIdTipoSolicitante(Long idTipoSolicitante) {
        this.idTipoSolicitante = idTipoSolicitante;
    }

    public Short getDescuento() {
        return descuento;
    }

    public void setDescuento(Short descuento) {
        this.descuento = descuento;
    }

    public Short getInventor() {
        return inventor;
    }

    public void setInventor(Short inventor) {
        this.inventor = inventor;
    }

    public Short getPrincipal() {
        return principal;
    }

    public void setPrincipal(Short principal) {
        this.principal = principal;
    }

    public String getRfc() {
        return rfc;
    }

    public void setRfc(String rfc) {
        this.rfc = rfc;
    }

    public String getRgp() {
        return rgp;
    }

    public void setRgp(String rgp) {
        this.rgp = rgp;
    }

    public CatTipoSolicitante getTipoSolicitante() {
        return tipoSolicitante;
    }

    public void setTipoSolicitante(CatTipoSolicitante tipoSolicitante) {
        this.tipoSolicitante = tipoSolicitante;
    }

    public Pais getPais() {
        return pais;
    }

    public void setPais(Pais pais) {
        this.pais = pais;
    }

    public CatTipopersona getTipoPersona() {
        return tipoPersona;
    }

    public void setTipoPersona(CatTipopersona tipoPersona) {
        this.tipoPersona = tipoPersona;
    }

    public Long getIdDomiclio() {
        return idDomiclio;
    }

    public void setIdDomiclio(Long idDomiclio) {
        this.idDomiclio = idDomiclio;
    }

    public Long getIdSolicitante() {
        return idSolicitante;
    }

    public void setIdSolicitante(Long idSolicitante) {
        this.idSolicitante = idSolicitante;
    }

    public String getNombrecompleto() {
        return nombrecompleto;
    }

    public void setNombrecompleto(String nombrecompleto) {
        this.nombrecompleto = nombrecompleto == null ? null : nombrecompleto.trim();
        if (nombrecompleto != null) {
            
            this.nombrecompletotmp = Util.reemplazaAcentos(nombrecompleto.trim());
        }
        
    }

    public String getNombreConcatenado() {
        return nombreConcatenado;
    }

    public void setNombreConcatenado(String nombreConcatenado) {
        this.nombreConcatenado = nombreConcatenado;
    }
    

    public Short getIdTipopersona() {
        return idTipopersona;
    }

    public void setIdTipopersona(Short idTipopersona) {
        this.idTipopersona = idTipopersona;
    }

    public Long getIdDatoscontacto() {
        return idDatoscontacto;
    }

    public void setIdDatoscontacto(Long idDatoscontacto) {
        this.idDatoscontacto = idDatoscontacto;
    }

    public Long getIdNacionalidad() {
        return idNacionalidad;
    }

    public void setIdNacionalidad(Long idNacionalidad) {
        this.idNacionalidad = idNacionalidad;
    }

    public Datoscontacto getDatosContacto() {
        return datosContacto;
    }

    public void setDatosContacto(Datoscontacto datosContacto) {
        this.datosContacto = datosContacto;
    }

    public Domicilio getDomicilioObj() {
        return domicilioObj;
    }

    public void setDomicilioObj(Domicilio domicilioObj) {
        this.domicilioObj = domicilioObj;
    }

    public Pais getNacionalidad() {
        return nacionalidad;
    }

    public void setNacionalidad(Pais nacionalidad) {
        this.nacionalidad = nacionalidad;
    }

    public Long getIdUsuarioFirmante() {
        return idUsuarioFirmante;
    }

    public void setIdUsuarioFirmante(Long idUsuarioFirmante) {
        this.idUsuarioFirmante = idUsuarioFirmante;
    }

    /**
     * @return the nombrecompletotmp
     */
    public String getNombrecompletotmp() {
        return nombrecompletotmp;
    }

    /**
     * @param nombrecompletotmp the nombrecompletotmp to set
     */
    public void setNombrecompletotmp(String nombrecompletotmp) {
        this.nombrecompletotmp = nombrecompletotmp == null ? null : nombrecompletotmp.trim();        
    }

    public String getPrimerApellido() {
        return primerApellido;
    }

    public void setPrimerApellido(String primerApellido) {
        this.primerApellido = primerApellido;
    }

    public String getSegundoApellido() {
        return segundoApellido;
    }

    public void setSegundoApellido(String segundoApellido) {
        this.segundoApellido = segundoApellido;
    }

    public Short getOrden() {
        return orden;
    }

    public void setOrden(Short orden) {
        this.orden = orden;
    }
    
  
    public Boolean getEsMandatorio() {
        return esMandatorio;
}

 
    public void setEsMandatorio(Boolean esMandatorio) {
        this.esMandatorio = esMandatorio;
    }


    
}
