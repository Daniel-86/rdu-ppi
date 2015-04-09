package mx.gob.impi.sagpat.persistence.model;



public class SolicitudPalabra implements java.io.Serializable { 
    private String codOficina;

    private String codPalabra;

    private String codUso;

    private Long numExped;

    private Integer serExped;

    private String tipExped;

    private Integer rowVersion;

    public String getCodOficina() {
        return codOficina;
    }

    public void setCodOficina(String codOficina) {
        this.codOficina = codOficina == null ? null : codOficina.trim();
    }

    public String getCodPalabra() {
        return codPalabra;
    }

    public void setCodPalabra(String codPalabra) {
        this.codPalabra = codPalabra == null ? null : codPalabra.trim();
    }

    public String getCodUso() {
        return codUso;
    }

    public void setCodUso(String codUso) {
        this.codUso = codUso == null ? null : codUso.trim();
    }

    public Long getNumExped() {
        return numExped;
    }

    public void setNumExped(Long numExped) {
        this.numExped = numExped;
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
}