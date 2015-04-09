package mx.gob.impi.rdu.persistence.model;

import java.io.Serializable;

public class SolicitanteXTramite implements Serializable {
    private Long idSolicitante;

    private Long idTramite;

    public Long getIdSolicitante() {
        return idSolicitante;
    }

    public void setIdSolicitante(Long idSolicitante) {
        this.idSolicitante = idSolicitante;
    }

    public Long getIdTramite() {
        return idTramite;
    }

    public void setIdTramite(Long idTramite) {
        this.idTramite = idTramite;
    }

}