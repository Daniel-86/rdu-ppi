package mx.gob.impi.rdu.persistence.model;

import java.io.Serializable;

public class NotificacionFirma implements  Serializable {
    private Long id;

    private Long idNotificacion;

    private Long idFirma;

    public NotificacionFirma(Long idNotificacion, Long idFirma) {
        this.idNotificacion = idNotificacion;
        this.idFirma = idFirma;
    }

    public NotificacionFirma() {
    }
    
    

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getIdNotificacion() {
        return idNotificacion;
    }

    public void setIdNotificacion(Long idNotificacion) {
        this.idNotificacion = idNotificacion;
    }

    public Long getIdFirma() {
        return idFirma;
    }

    public void setIdFirma(Long idFirma) {
        this.idFirma = idFirma;
    }
}