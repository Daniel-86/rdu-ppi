package mx.gob.impi.sagpat.persistence.model;



public class PalabraIgnorar implements java.io.Serializable { 
    private String palabra;

    private Integer rowVersion;

    public String getPalabra() {
        return palabra;
    }

    public void setPalabra(String palabra) {
        this.palabra = palabra == null ? null : palabra.trim();
    }

    public Integer getRowVersion() {
        return rowVersion;
    }

    public void setRowVersion(Integer rowVersion) {
        this.rowVersion = rowVersion;
    }
}