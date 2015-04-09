package mx.gob.impi.rdu.persistence.mappers;

import java.util.List;
import mx.gob.impi.rdu.persistence.model.PersonaNotXTramitePatente;

public interface PersonaNotXTramitePatenteMapper {

    int insertByExample(PersonaNotXTramitePatente personaNotXTramitePatente);

    int deleteByPrimaryKey(Long idTramite);

    List<PersonaNotXTramitePatente> obtenerPersonasNotXTramitePatente(Long idTramite);
}
