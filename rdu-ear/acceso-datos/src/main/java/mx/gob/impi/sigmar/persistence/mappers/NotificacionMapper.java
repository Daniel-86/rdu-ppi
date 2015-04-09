package mx.gob.impi.sigmar.persistence.mappers;

import mx.gob.impi.sigmar.persistence.model.Notificacion;



public interface NotificacionMapper {
    int insert(mx.gob.impi.sigmar.persistence.model.Notificacion record);

    int insertSelective(mx.gob.impi.sigmar.persistence.model.Notificacion record);
}