package mx.gob.impi.rdu.persistence.mappers;

import mx.gob.impi.rdu.persistence.model.PreguntaSecreta;

public interface PreguntaSecretaMapper {
    int deleteByPrimaryKey(Short idPreguntaSecreta);

    int insert(PreguntaSecreta record);

    int insertSelective(PreguntaSecreta record);

    PreguntaSecreta selectByPrimaryKey(Short idPreguntaSecreta);

    int updateByPrimaryKeySelective(PreguntaSecreta record);

    int updateByPrimaryKey(PreguntaSecreta record);
}