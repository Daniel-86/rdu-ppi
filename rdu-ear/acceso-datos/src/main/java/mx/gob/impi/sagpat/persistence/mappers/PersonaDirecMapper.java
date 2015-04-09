package mx.gob.impi.sagpat.persistence.mappers;


import java.util.List;
import mx.gob.impi.sagpat.persistence.model.PersonaDirec;
import org.apache.ibatis.annotations.Param;

public interface PersonaDirecMapper {
    int deleteByPrimaryKey(@Param("codPersona") Integer codPersona, @Param("secDireccion") Short secDireccion);

    int insert(PersonaDirec record);

    int insertSelective(PersonaDirec record);

    PersonaDirec selectByPrimaryKey(@Param("codPersona") Integer codPersona, @Param("secDireccion") Short secDireccion);
    
    List<PersonaDirec> selectByCodPersona(@Param("codPersona") Integer codPersona);
    
    PersonaDirec selectByCodPersonaDireccion(@Param("codPersona") Integer codPersona, @Param("secDireccion") Long secDireccion,@Param("dirCalle") String dirCalle );

    int updateByPrimaryKeySelective(PersonaDirec record);

    int updateByPrimaryKey(PersonaDirec record);
}