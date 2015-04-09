package mx.gob.impi.rdu.persistence.mappers;

import mx.gob.impi.rdu.persistence.model.Domicilionotificacion;

public interface DomicilionotificacionMapper {
    int deleteByPrimaryKey(Integer idDomicilionotificacion);

    int insert(Domicilionotificacion record);

    int insertSelective(Domicilionotificacion record);

    Domicilionotificacion selectByPrimaryKey(Long idDomicilionotificacion);

    int updateByPrimaryKeySelective(Domicilionotificacion record);

    int updateByPrimaryKey(Domicilionotificacion record);
}