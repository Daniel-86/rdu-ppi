package mx.gob.impi.sagpat.persistence.model;

public class PersonaPalabra implements java.io.Serializable { 
    private String codPalabra;

    private Long codPersona;

    private Integer rowVersion;

    public String getCodPalabra() {
        return codPalabra;
    }

    public void setCodPalabra(String codPalabra) {
        this.codPalabra = codPalabra == null ? null : codPalabra.trim();
    }

    public Long getCodPersona() {
        return codPersona;
    }

    public void setCodPersona(Long codPersona) {
        this.codPersona = codPersona;
    }

    public Integer getRowVersion() {
        return rowVersion;
    }

    public void setRowVersion(Integer rowVersion) {
        this.rowVersion = rowVersion;
    }
}