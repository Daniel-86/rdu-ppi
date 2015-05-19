package mx.gob.impi.sigappi.persistence.mappers;

import java.util.List;
import mx.gob.impi.sigappi.persistence.mappers.*;
import mx.gob.impi.sigappi.persistence.model.TipoAnexo;
import org.apache.ibatis.annotations.Param;



public interface TipoAnexoMapper {

    
    List<TipoAnexo> selectByCategoriaDivisionSeccion(@Param("categoria")Integer categoria,@Param("division")Integer division,@Param("seccion")Integer seccion);
}