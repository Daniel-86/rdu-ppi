package mx.gob.impi.sagpat.persistence.model;



public class SolicitudTituloUnicode implements java.io.Serializable { 
    private String codOficina;

    private String codPais;

    private Long numExped;

    private Integer serExped;

    private String tipExped;

    private Integer rowVersion;

    private String tituloUnicode;

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

    public String getTituloUnicode() {
        return tituloUnicode;
    }

    public void setTituloUnicode(String tituloUnicode) {
        this.tituloUnicode = tituloUnicode == null ? null : tituloUnicode.trim();
    }
}