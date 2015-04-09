package mx.gob.impi.sagpat.persistence.model;



public class SolicitudReivindicacionWord implements java.io.Serializable { 
    private String codOficina;

    private Long numExped;

    private Short secReivindicacion;

    private Integer serExped;

    private String tipExped;

    private Integer rowVersion;

    private Integer largo;

    private String reivindicacionWord;

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

    public Integer getLargo() {
        return largo;
    }

    public void setLargo(Integer largo) {
        this.largo = largo;
    }

    public String getReivindicacionWord() {
        return reivindicacionWord;
    }

    public void setReivindicacionWord(String reivindicacionWord) {
        this.reivindicacionWord = reivindicacionWord == null ? null : reivindicacionWord.trim();
    }
}