package mx.gob.impi.sigappi.persistence.mappers;

import java.util.List;
import mx.gob.impi.sigappi.persistence.mappers.*;
import mx.gob.impi.sigappi.persistence.model.DerechosAsociados;
import org.apache.ibatis.annotations.Param;



public interface DerechosAsociadosMapper {

    
    List<DerechosAsociados> selectByTitle(@Param("title")String title);
}