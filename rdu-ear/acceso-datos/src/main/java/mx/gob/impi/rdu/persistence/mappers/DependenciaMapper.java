package mx.gob.impi.rdu.persistence.mappers;

import mx.gob.impi.rdu.persistence.model.Dependencia;

public interface DependenciaMapper {
    int deleteByPrimaryKey(Short idDependencia);

    int insert(Dependencia record);

    int insertSelective(Dependencia record);

    Dependencia selectByPrimaryKey(Short idDependencia);

    int updateByPrimaryKeySelective(Dependencia record);

    int updateByPrimaryKey(Dependencia record);
}