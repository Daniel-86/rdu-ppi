package mx.gob.impi.sigappi.persistence.mappers;

import java.util.List;
import mx.gob.impi.sigappi.persistence.mappers.*;
import mx.gob.impi.sigappi.persistence.model.KfAlmacenar;
import org.apache.ibatis.annotations.Param;



public interface KfAlmacenarMapper {
    int insert(mx.gob.impi.sigappi.persistence.model.KfAlmacenar record);

    
    List<KfAlmacenar> selectByCodbarras(@Param("codbarras")String codbarras);
    List<KfAlmacenar> selectByTitle(@Param("title")String title);
}