package mx.gob.impi.sigappi.persistence.mappers;

import java.util.List;
import mx.gob.impi.sigappi.persistence.mappers.*;
import mx.gob.impi.sigappi.persistence.model.Area;
import org.apache.ibatis.annotations.Param;



public interface AreaMapper {

    
    List<Area> selectByCveArea(@Param("cveArea")Integer cveArea);
}