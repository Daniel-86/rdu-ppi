package mx.gob.impi.sigappi.persistence.mappers;

import java.util.List;
import mx.gob.impi.sigappi.persistence.mappers.*;
import mx.gob.impi.sigappi.persistence.model.FigurasJuridicas;
import org.apache.ibatis.annotations.Param;



public interface FigurasJuridicasMapper {
    

    
    List<FigurasJuridicas> selectByNumFigura(@Param("numFigura")Integer numFigura);
}