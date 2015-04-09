    package mx.gob.impi.rdu.persistence.model;

import java.io.Serializable;

public class PrioridadXTramitePatente implements  Serializable{

    private Long idPrioridad;
    private Long idTramitePatente;

    public Long getIdPrioridad() {
        return idPrioridad;
    }

    public void setIdPrioridad(Long idPrioridad) {
        this.idPrioridad = idPrioridad;
    }

    public Long getIdTramitePatente() {
        return idTramitePatente;
    }

    public void setIdTramitePatente(Long idTramitePatente) {
        this.idTramitePatente = idTramitePatente;
    }
}
