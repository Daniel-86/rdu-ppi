package mx.gob.impi.rdu.persistence.mappers;

import mx.gob.impi.rdu.persistence.model.CatEstatus;

public interface CatEstatusMapper {
    int deleteByPrimaryKey(Integer idEstatus);

    int insert(CatEstatus record);

    int insertSelective(CatEstatus record);

    CatEstatus selectByPrimaryKey(Long idEstatus);

    int updateByPrimaryKeySelective(CatEstatus record);

    int updateByPrimaryKey(CatEstatus record);
}