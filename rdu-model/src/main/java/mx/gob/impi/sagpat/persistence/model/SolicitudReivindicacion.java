package mx.gob.impi.sagpat.persistence.model;



public class SolicitudReivindicacion implements java.io.Serializable { 
    private String codOficina;

    private Long numExped;

    private Short secReivindicacion;

    private Integer serExped;

    private String tipExped;

    private Integer rowVersion;

    private String reivindicacion;

    public String getCodOficina() {
        return codOficina;
    }

    public void setCodOficina(String codOficina) {
        this.codOficina = codOficina == null ? null : codOficina.trim();
    }

    public Long getNumExped() {
        return numExped;
    }

    public void setNumExped(Long numExped) {
        this.numExped = numExped;
    }

    public Short getSecReivindicacion() {
        return secReivindicacion;
    }

    public void setSecReivindicacion(Short secReivindicacion) {
        this.secReivindicacion = secReivindicacion;
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

    public String getReivindicacion() {
        return reivindicacion;
    }

    public void setReivindicacion(String reivindicacion) {
        this.reivindicacion = reivindicacion == null ? null : reivindicacion.trim();
    }
}