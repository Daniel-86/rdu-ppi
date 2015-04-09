package mx.gob.impi.rdu.persistence.mappers;

import java.util.List;
import mx.gob.impi.rdu.persistence.model.ApoderadoXTramitePatente;

public interface ApoderadoXTramitePatenteMapper {

    int insertByExample(ApoderadoXTramitePatente apoderadoXTramitePatente);

    int deleteByPrimaryKey(Long idTramite);

    List<ApoderadoXTramitePatente> obtenerApoderadosXTramitePatente(Long idTramite);
}
