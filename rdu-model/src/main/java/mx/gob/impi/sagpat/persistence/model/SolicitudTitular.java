package mx.gob.impi.sagpat.persistence.model;

public class SolicitudTitular implements java.io.Serializable { 
    private String codOficina;

    private Long codPersona;

    private Long numExped;

    private Long secDireccion;

    private Integer serExped;

    private String tipExped;

    private Integer rowVersion;

    private String obsParticipacion;

    public String getCodOficina() {
        return codOficina;
    }

    public void setCodOficina(String codOficina) {
        this.codOficina = codOficina == null ? null : codOficina.trim();
    }

    public Long getCodPersona() {
        return codPersona;
    }

    public void setCodPersona(Long codPersona) {
        this.codPersona = codPersona;
    }

    public Long getNumExped() {
        return numExped;
    }

    public void setNumExped(Long numExped) {
        this.numExped = numExped;
    }

    public Long getSecDireccion() {
        return secDireccion;
    }

    public void setSecDireccion(Long secDireccion) {
        this.secDireccion = secDireccion;
    }

    public Integer getSerExped() {
        return serExped;
    }

    public void setSerExped(Integer serExped) {
        this.serExped = serExped;
    }

    public String getTipExped() {
        return tipExped;
    }

    public void setTipExped(String tipExped) {
        this.tipExped = tipExped == null ? null : tipExped.trim();
    }

    public Integer getRowVersion() {
        return rowVersion;
    }

    public void setRowVersion(Integer rowVersion) {
        this.rowVersion = rowVersion;
    }

    public String getObsParticipacion() {
        return obsParticipacion;
    }

    public void setObsParticipacion(String obsParticipacion) {
        this.obsParticipacion = obsParticipacion == null ? null : obsParticipacion.trim();
    }
}