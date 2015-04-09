package mx.gob.impi.rdu.persistence.mappers;

import mx.gob.impi.rdu.persistence.model.CatClasePersona;

/**
 *
 * @author JBMM
 */
public interface CatClasePersonaMapper {
    int deleteByPrimaryKey(Short idClasePersona);

    int insert(CatClasePersona clasePersona);

    CatClasePersona selectByPrimaryKey(Short idClasePersona);

    int updateByPrimaryKeySelective(CatClasePersona record);

    int updateByPrimaryKey(CatClasePersona record);
}