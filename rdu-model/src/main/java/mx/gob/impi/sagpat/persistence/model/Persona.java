package mx.gob.impi.sagpat.persistence.model;

import java.io.Serializable;

public class Persona implements  Serializable{
    private Long codPersona;

    private Long rowVersion;

    private String nomPersona;

    private String tipPersona;

    private String codPaisNacion;

    private Integer codDespacho;
    
    private Long numRgp;
    
    private Long secDireccion;
    
    public Persona(String nomPersona, String tipPersona, String codPaisNacion) {
        this.nomPersona = nomPersona;
        this.tipPersona = tipPersona;
        this.codPaisNacion = codPaisNacion;
    }

    public Persona() {
    }

    public Persona(Long codPersona) {
        this.codPersona = codPersona;
    }

    public Long getSecDireccion() {
        return secDireccion;
    }

    public void setSecDireccion(Long secDireccion) {
        this.secDireccion = secDireccion;
    }   

    public Long getCodPersona() {
        return codPersona;
    }

    public void setCodPersona(Long codPersona) {
        this.codPersona = codPersona;
    }

    public Long getRowVersion() {
        return rowVersion;
    }

    public void setRowVersion(Long rowVersion) {
        this.rowVersion = rowVersion;
    }

    public String getNomPersona() {
        return nomPersona;
    }

    public void setNomPersona(String nomPersona) {
        this.nomPersona = nomPersona == null ? null : nomPersona.trim();
    }

    public String getTipPersona() {
        return tipPersona;
    }

    public void setTipPersona(String tipPersona) {
        this.tipPersona = tipPersona == null ? null : tipPersona.trim();
    }

    public String getCodPaisNacion() {
        return codPaisNacion;
    }

    public void setCodPaisNacion(String codPaisNacion) {
        this.codPaisNacion = codPaisNacion == null ? null : codPaisNacion.trim();
    }

    public Integer getCodDespacho() {
        return codDespacho;
    }

    public void setCodDespacho(Integer codDespacho) {
        this.codDespacho = codDespacho;
    }

    public Long getNumRgp() {
        return numRgp;
    }

    public void setNumRgp(Long numRgp) {
        this.numRgp = numRgp;
    }
    
    
}