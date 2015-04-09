package mx.gob.impi.rdu.persistence.mappers;

import java.util.List;
import mx.gob.impi.rdu.persistence.model.CodigosPostales;

public interface CodigosPostalesMapper {

    List<CodigosPostales> selectById(String codigoPostal);
}
