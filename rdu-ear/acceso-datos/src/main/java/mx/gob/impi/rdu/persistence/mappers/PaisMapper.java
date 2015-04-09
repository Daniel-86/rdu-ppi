package mx.gob.impi.rdu.persistence.mappers;

import java.util.List;
import mx.gob.impi.rdu.persistence.model.Pais;

public interface PaisMapper {

    int deleteByPrimaryKey(Long idPais);

    int insert(Pais record);

    int insertSelective(Pais record);

    Pais selectByPrimaryKey(Long idPais);

    int updateByPrimaryKeySelective(Pais record);

    int updateByPrimaryKey(Pais record);

    List<Pais> selectDynamic(Pais pais);

    List<Pais> selectNacionalidades(Pais pais);
}
