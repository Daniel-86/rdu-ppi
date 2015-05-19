package mx.gob.impi.sigappi.persistence.mappers;

import java.util.List;
import mx.gob.impi.sigappi.persistence.mappers.*;
import mx.gob.impi.sigappi.persistence.model.Anexo;
import org.apache.ibatis.annotations.Param;



public interface AnexoMapper {
    

    
    List<Anexo> selectByCodbarras(@Param("codbarras")String codbarras);
}