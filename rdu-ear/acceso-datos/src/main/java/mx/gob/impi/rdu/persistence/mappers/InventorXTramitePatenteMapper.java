package mx.gob.impi.rdu.persistence.mappers;

import java.util.List;
import mx.gob.impi.rdu.persistence.model.InventorXTramitePatente;


public interface InventorXTramitePatenteMapper {

    int insertInventorXTramitePatente(InventorXTramitePatente record);

    List<InventorXTramitePatente> obtenerInventoresXTramite(Long idTramite);

    int deleteInventoresByTramite(Long idTramite);
}
