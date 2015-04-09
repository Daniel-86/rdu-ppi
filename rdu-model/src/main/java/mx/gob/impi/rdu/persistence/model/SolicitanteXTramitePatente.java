package mx.gob.impi.rdu.persistence.model;

import java.io.Serializable;

public class SolicitanteXTramitePatente implements Serializable {

    private Long idSolicitante;
    private Long idTramitePatente;

    public Long getIdSolicitante() {
        return idSolicitante;
    }

    public void setIdSolicitante(Long idSolicitante) {
        this.idSolicitante = idSolicitante;
    }

    public Long getIdTramitePatente() {
        return idTramitePatente;
    }

    public void setIdTramitePatente(Long idTramitePatente) {
        this.idTramitePatente = idTramitePatente;
    }
}
