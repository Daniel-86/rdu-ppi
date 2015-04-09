package mx.gob.impi.sagpat.persistence.model;

public class SolicitudResumenWord implements java.io.Serializable { 
    private String codOficina;

    private String codPais;

    private Integer numExped;

    private Short serExped;

    private String tipExped;

    private Integer rowVersion;

    private Integer largo;

    private String resumenWord;

    public String getCodOficina() {
        return codOficina;
    }

    public void setCodOficina(String codOficina) {
        this.codOficina = codOficina == null ? null : codOficina.trim();
    }

    public String getCodPais() {
        return codPais;
    }

    public void setCodPais(String codPais) {
        this.codPais = codPais == null ? null : codPais.trim();
    }

    public Integer getNumExped() {
        return numExped;
    }

    public void setNumExped(Integer numExped) {
        this.numExped = numExped;
    }

    public Short getSerExped() {
        return serExped;
    }

    public void setSerExped(Short serExped) {
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

    public String getResumenWord() {
        return resumenWord;
    }

    public void setResumenWord(String resumenWord) {
        this.resumenWord = resumenWord == null ? null : resumenWord.trim();
    }
}