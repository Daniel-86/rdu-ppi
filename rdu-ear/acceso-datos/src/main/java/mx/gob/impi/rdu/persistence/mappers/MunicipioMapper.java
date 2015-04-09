package mx.gob.impi.rdu.persistence.mappers;

import mx.gob.impi.rdu.persistence.model.Municipio;
import org.apache.ibatis.annotations.Param;

public interface MunicipioMapper {
    int deleteByPrimaryKey(@Param("idEntidadFederativa") Short idEntidadFederativa, @Param("idMunicipio") Short idMunicipio, @Param("idPais") Short idPais);

    int insert(Municipio record);

    int insertSelective(Municipio record);

    Municipio selectByPrimaryKey(@Param("idEntidadFederativa") Short idEntidadFederativa, @Param("idMunicipio") Short idMunicipio, @Param("idPais") Short idPais);

    int updateByPrimaryKeySelective(Municipio record);

    int updateByPrimaryKey(Municipio record);
}