package mx.gob.impi.rdu.persistence.model;

import java.io.Serializable;

public class Tipoclaseseleccionada implements Serializable {
    private Long idTipoclaseseleccionada;

    private Long idTramite;

    private Short idFormaseleccionclase;

    private String descripcionlibre;

    private long idClase;
    private String tipoClase;

    public Tipoclaseseleccionada() {
        super();
    }

    public Tipoclaseseleccionada(Long idTramite) {
        this.idTramite = idTramite;
    }
      

    public String getTipoClase() {
        return tipoClase;
    }

    public void setTipoClase(String tipoClase) {
        this.tipoClase = tipoClase;
    }

    
    
    

    public Long getIdTipoclaseseleccionada() {
        return idTipoclaseseleccionada;
    }

    public void setIdTipoclaseseleccionada(Long idTipoclaseseleccionada) {
        this.idTipoclaseseleccionada = idTipoclaseseleccionada;
    }

    public Long getIdTramite() {
        return idTramite;
    }

    public void setIdTramite(Long idTramite) {
        this.idTramite = idTramite;
    }

    public Short getIdFormaseleccionclase() {
        return idFormaseleccionclase;
    }

    public void setIdFormaseleccionclase(Short idFormaseleccionclase) {
        this.idFormaseleccionclase = idFormaseleccionclase;
    }

    public String getDescripcionlibre() {
        return descripcionlibre;
    }

    public void setDescripcionlibre(String descripcionlibre) {
        this.descripcionlibre = descripcionlibre == null ? null : descripcionlibre.trim();
    }

    public long getIdClase() {
        return idClase;
    }

    public void setIdClase(long idClase) {
        this.idClase = idClase;
    }
}