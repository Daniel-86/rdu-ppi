package mx.gob.impi.sagpat.persistence.mappers;


import mx.gob.impi.sagpat.persistence.model.PersonaPalabra;
import org.apache.ibatis.annotations.Param;

public interface PersonaPalabraMapper {
    int deleteByPrimaryKey(@Param("codPalabra") String codPalabra, @Param("codPersona") Integer codPersona);

    int insert(PersonaPalabra record);

    int insertSelective(PersonaPalabra record);

    PersonaPalabra selectByPrimaryKey(@Param("codPalabra") String codPalabra, @Param("codPersona") Integer codPersona);

    int updateByPrimaryKeySelective(PersonaPalabra record);

    int updateByPrimaryKey(PersonaPalabra record);
}