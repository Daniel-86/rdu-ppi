package mx.gob.impi.sigappi.persistence.mappers;

import java.util.List;
import mx.gob.impi.sigappi.persistence.mappers.*;
import mx.gob.impi.sigappi.persistence.model.TiposRelacion;
import org.apache.ibatis.annotations.Param;



public interface TiposRelacionMapper {
    int insert(mx.gob.impi.sigappi.persistence.model.TiposRelacion record);

    
    List<TiposRelacion> selectByCodRelacion(@Param("codRelacion")Integer codRelacion);
}