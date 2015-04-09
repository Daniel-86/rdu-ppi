package mx.gob.impi.sagpat.persistence.model;



public class SolicitudResumenUnicode implements java.io.Serializable { 
    private String codOficina;

    private String codPais;

    private Long numExped;

    private Integer serExped;

    private String tipExped;

    private Integer rowVersion;

    private String resumenUnicode;

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

    public String getResumenUnicode() {
        return resumenUnicode;
    }

    public void setResumenUnicode(String resumenUnicode) {
        this.resumenUnicode = resumenUnicode == null ? null : resumenUnicode.trim();
    }
}