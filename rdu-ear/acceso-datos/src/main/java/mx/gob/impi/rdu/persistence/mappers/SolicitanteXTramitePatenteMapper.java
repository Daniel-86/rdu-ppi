package mx.gob.impi.rdu.persistence.mappers;

import java.util.List;
import mx.gob.impi.rdu.persistence.model.SolicitanteXTramitePatente;

public interface SolicitanteXTramitePatenteMapper {


    int insert(SolicitanteXTramitePatente record);

    List<SolicitanteXTramitePatente> obtenerSolicitantesXTramite(Long idTramite);

    int deleteByTramite(Long idTramite);
}
