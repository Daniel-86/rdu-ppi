package mx.gob.impi.rdu.persistence.mappers;

import mx.gob.impi.rdu.persistence.model.CatTipotelefono;

public interface CatTipotelefonoMapper {
    int deleteByPrimaryKey(Short idTipotelefono);

    int insert(CatTipotelefono record);

    int insertSelective(CatTipotelefono record);

    CatTipotelefono selectByPrimaryKey(Short idTipotelefono);

    int updateByPrimaryKeySelective(CatTipotelefono record);

    int updateByPrimaryKey(CatTipotelefono record);
}