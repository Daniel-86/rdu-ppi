package mx.gob.impi.rdu.persistence.mappers;

import mx.gob.impi.rdu.persistence.model.NotificacionFirma;



public interface NotificacionFirmaMapper {
    int deleteByPrimaryKey(Integer id);

    NotificacionFirma selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(NotificacionFirma record);

    int updateByPrimaryKey(NotificacionFirma record);
    
    int insert(NotificacionFirma record);   
    
}