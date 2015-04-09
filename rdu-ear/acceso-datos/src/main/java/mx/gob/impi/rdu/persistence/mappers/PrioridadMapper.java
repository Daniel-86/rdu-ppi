package mx.gob.impi.rdu.persistence.mappers;

import java.util.List;
import mx.gob.impi.rdu.persistence.model.Prioridad;

public interface PrioridadMapper {

    int deleteByPrimaryKey(Long idPrioridad);

    Prioridad selectByPrimaryKey(Long idPrioridad);

    int insert(Prioridad record);

    int updateByPrimaryKey(Prioridad record);
    List<Prioridad> selectPrioridadesTramitePatente(Long idTramite);
    
    int selectByIdAnexo(Long idAnexo);
    int updateByIdAnexo(Prioridad record);
}
