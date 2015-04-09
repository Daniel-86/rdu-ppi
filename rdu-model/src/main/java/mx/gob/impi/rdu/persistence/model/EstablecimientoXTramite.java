package mx.gob.impi.rdu.persistence.model;

import java.io.Serializable;

public class EstablecimientoXTramite implements Serializable {
    private Long idDomicilio;

    private Long idTramite;
    
    private Domicilio domicilio;

    public EstablecimientoXTramite() {
    }

    public EstablecimientoXTramite(Domicilio domicilio) {
        this.domicilio = domicilio;
    }
    
    
    public void setDomicilio(Domicilio domicilio) {
        this.domicilio = domicilio;
    }

    public Domicilio getDomicilio() {
        return domicilio;
    }
    
    public Long getIdDomicilio() {
        return idDomicilio;
    }

    public void setIdDomicilio(Long idDomicilio) {
        this.idDomicilio = idDomicilio;
    }

    public Long getIdTramite() {
        return idTramite;
    }

    public void setIdTramite(Long idTramite) {
        this.idTramite = idTramite;
    }

}