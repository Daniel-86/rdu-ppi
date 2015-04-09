package mx.gob.impi.rdu.persistence.model;

import java.io.Serializable;

public class InventorXTramitePatente implements  Serializable {
    private Long idInventor;

    private Long idTramitePatente;

    public Long getIdInventor() {
        return idInventor;
    }

    public void setIdInventor(Long idInventor) {
        this.idInventor = idInventor;
    }

    public Long getIdTramitePatente() {
        return idTramitePatente;
    }

    public void setIdTramitePatente(Long idTramitePatente) {
        this.idTramitePatente = idTramitePatente;
    }
}