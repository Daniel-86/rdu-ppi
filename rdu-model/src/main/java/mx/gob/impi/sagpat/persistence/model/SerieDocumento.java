package mx.gob.impi.sagpat.persistence.model;



public class SerieDocumento implements java.io.Serializable { 
    private Short serDocum;

    private Integer rowVersion;

    private String indActiva;

    public Short getSerDocum() {
        return serDocum;
    }

    public void setSerDocum(Short serDocum) {
        this.serDocum = serDocum;
    }

    public Integer getRowVersion() {
        return rowVersion;
    }

    public void setRowVersion(Integer rowVersion) {
        this.rowVersion = rowVersion;
    }

    public String getIndActiva() {
        return indActiva;
    }

    public void setIndActiva(String indActiva) {
        this.indActiva = indActiva == null ? null : indActiva.trim();
    }
}