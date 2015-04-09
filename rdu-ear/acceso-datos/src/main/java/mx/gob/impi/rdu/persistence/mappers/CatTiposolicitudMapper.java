package mx.gob.impi.rdu.persistence.mappers;

import java.util.List;
import mx.gob.impi.rdu.persistence.model.CatTiposolicitud;

public interface CatTiposolicitudMapper {
    int deleteByPrimaryKey(Integer idTiposolicitud);

    int insert(CatTiposolicitud record);

    int insertSelective(CatTiposolicitud record);

    CatTiposolicitud selectByPrimaryKey(Long idTiposolicitud);

    int updateByPrimaryKeySelective(CatTiposolicitud record);

    int updateByPrimaryKey(CatTiposolicitud record);
    List<CatTiposolicitud> getTiposSolicitudesByCriterio(CatTiposolicitud criterio);
}