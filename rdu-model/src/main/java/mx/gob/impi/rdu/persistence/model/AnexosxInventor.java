package mx.gob.impi.rdu.persistence.model;

import java.io.Serializable;

/**
 *
 * @author JBMM
 *
 */
public class AnexosxInventor implements Serializable {
    private Long IdSolicitante;
    private Long IdAnexo;

    public Long getIdSolicitante() {
        return IdSolicitante;
    }

    public void setIdSolicitante(Long IdSolicitante) {
        this.IdSolicitante = IdSolicitante;
    }

    public Long getIdAnexo() {
        return IdAnexo;
    }

    public void setIdAnexo(Long IdAnexo) {
        this.IdAnexo = IdAnexo;
    }
    
}