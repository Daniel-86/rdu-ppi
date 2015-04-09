package mx.gob.impi.rdu.persistence.mappers;

import java.util.List;
import mx.gob.impi.rdu.persistence.model.PrioridadXTramitePatente;
import org.apache.ibatis.annotations.Param;

public interface PrioridadXTramitePatenteMapper {
    
    int insertRelPrioridadTramite(PrioridadXTramitePatente record);

    List<PrioridadXTramitePatente> obtenerPrioridadXTramite(Long idTramite);

    int deleteRelPrioridadByTramite(Long idTramite);
    
    List<PrioridadXTramitePatente> obtenerPrioridadXIDPrioridad(@Param("prioridadesIds") String prioridadesIds);
       
}
