package mx.gob.impi.sagpat.persistence.model;

public class DocumentoExpediente implements java.io.Serializable { 
    private String codOficinaDocum;

    private String codOficinaExped;

    private Long numDocum;

    private Long numExped;

    private Integer serDocum;

    private Integer serExped;

    private String tipExped;

    private String tipLibro;

    private Integer rowVersion;

    public String getCodOficinaDocum() {
        return codOficinaDocum;
    }

    public void setCodOficinaDocum(String codOficinaDocum) {
        this.codOficinaDocum = codOficinaDocum == null ? null : codOficinaDocum.trim();
    }

    public String getCodOficinaExped() {
        return codOficinaExped;
    }

    public void setCodOficinaExped(String codOficinaExped) {
        this.codOficinaExped = codOficinaExped == null ? null : codOficinaExped.trim();
    }

    public Long getNumDocum() {
        return numDocum;
    }

    public void setNumDocum(Long numDocum) {
        this.numDocum = numDocum;
    }

    public Long getNumExped() {
        return numExped;
    }

    public void setNumExped(Long numExped) {
        this.numExped = numExped;
    }

    public Integer getSerDocum() {
        return serDocum;
    }

    public void setSerDocum(Integer serDocum) {
        this.serDocum = serDocum;
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

    public String getTipLibro() {
        return tipLibro;
    }

    public void setTipLibro(String tipLibro) {
        this.tipLibro = tipLibro == null ? null : tipLibro.trim();
    }

    public Integer getRowVersion() {
        return rowVersion;
    }

    public void setRowVersion(Integer rowVersion) {
        this.rowVersion = rowVersion;
    }
}