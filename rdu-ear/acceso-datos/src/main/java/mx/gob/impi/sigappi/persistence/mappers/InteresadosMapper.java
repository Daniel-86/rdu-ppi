package mx.gob.impi.sigappi.persistence.mappers;

import java.util.List;
import mx.gob.impi.sigappi.persistence.mappers.*;
import mx.gob.impi.sigappi.persistence.model.Interesados;
import org.apache.ibatis.annotations.Param;



public interface InteresadosMapper {

    
    List<Interesados> selectByCodInteresado(@Param("codInteresado")Integer codInteresado);
}