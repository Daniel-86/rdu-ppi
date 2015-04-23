package mx.gob.impi.sigappi.persistence.mappers;

import java.util.List;
import mx.gob.impi.sigappi.persistence.mappers.*;
import mx.gob.impi.sigappi.persistence.model.UsuariosSigappi;
import org.apache.ibatis.annotations.Param;



public interface UsuariosSigappiMapper {
    int insert(mx.gob.impi.sigappi.persistence.model.UsuariosSigappi record);

    
    List<UsuariosSigappi> selectByCveUsuario(@Param("cveUsuario")String cveUsuario);
}