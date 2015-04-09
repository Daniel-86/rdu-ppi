package mx.gob.impi.rdu.persistence.mappers;

import mx.gob.impi.rdu.persistence.model.Tipoclaseseleccionada;

public interface TipoclaseseleccionadaMapper {
    int deleteByPrimaryKey(Long idTipoclaseseleccionada);

    int insert(Tipoclaseseleccionada record);

    int insertSelective(Tipoclaseseleccionada record);

    Tipoclaseseleccionada selectByPrimaryKey(Long idTipoclaseseleccionada);

    Tipoclaseseleccionada selectSelective(Tipoclaseseleccionada record);

    int updateByPrimaryKeySelective(Tipoclaseseleccionada record);

    int updateByPrimaryKey(Tipoclaseseleccionada record);
}