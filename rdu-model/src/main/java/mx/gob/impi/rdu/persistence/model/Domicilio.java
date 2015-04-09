package mx.gob.impi.rdu.persistence.model;

import java.io.Serializable;

public class Domicilio implements Serializable{
    private Long idDomicilio;

    private Long idPais;
    
    private String idEntidad;

    private String calle;

    private String numero;

    private String colonia;

    private String poblacion;

    private String codigopostal;

    private Pais pais;
    private EntidadFederativa entidad = new EntidadFederativa();
    
    private String numExt;
    private String numInt;

    public Domicilio() {
    }

    public Domicilio(Long idPais, String idEntidad, String calle, String colonia, String poblacion, String codigopostal) {
        this.idPais = idPais;
        this.idEntidad = idEntidad;
        this.calle = calle;
        this.colonia = colonia;
        this.poblacion = poblacion;
        this.codigopostal = codigopostal;
    }

    public EntidadFederativa getEntidad() {
        return entidad;
    }

    public void setEntidad(EntidadFederativa entidad) {
        this.entidad = entidad;
    }

    public Pais getPais() {
        return pais;
    }

    public void setPais(Pais pais) {
        this.pais = pais;
    }

    public Long getIdPais() {
        return idPais;
    }

    public void setIdPais(Long idPais) {
        this.idPais = idPais;
    }

    public String getIdEntidad() {
        return idEntidad;
    }

    public void setIdEntidad(String idEntidad) {
        this.idEntidad = idEntidad == null ? null : idEntidad.trim();
    }

    public String getCalle() {
        return calle;
    }

    public void setCalle(String calle) {
        this.calle = calle == null ? null : calle.trim();
    }

    public String getNumero() {
        return numero;
    }

    public void setNumero(String numero) {
        this.numero = numero == null ? null : numero.trim();
    }

    public String getColonia() {
        return colonia;
    }

    public void setColonia(String colonia) {
        this.colonia = colonia == null ? null : colonia.trim();
    }

    public String getPoblacion() {
        return poblacion;
    }

    public void setPoblacion(String poblacion) {
        this.poblacion = poblacion == null ? null : poblacion.trim();
    }

    public String getCodigopostal() {
        return codigopostal;
    }

    public void setCodigopostal(String codigopostal) {
        this.codigopostal = codigopostal == null ? null : codigopostal.trim();
    }

    public Long getIdDomicilio() {
        return idDomicilio;
    }

    public void setIdDomicilio(Long idDomicilio) {
        this.idDomicilio = idDomicilio;
    }

    public String getNumExt() {
        return numExt;
    }

    public void setNumExt(String numExt) {
        this.numExt = numExt;
    }

    public String getNumInt() {
        return numInt;
    }

    public void setNumInt(String numInt) {
        this.numInt = numInt;
    }

}