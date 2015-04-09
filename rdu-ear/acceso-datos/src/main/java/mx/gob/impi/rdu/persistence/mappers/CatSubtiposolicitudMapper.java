package mx.gob.impi.rdu.persistence.mappers;

import java.util.List;
import mx.gob.impi.rdu.dto.tipoSolicitudDto;
import mx.gob.impi.rdu.persistence.model.CatSubtiposolicitud;

public interface CatSubtiposolicitudMapper {
    int deleteByPrimaryKey(Integer idSubtiposolicitud);

    int insert(CatSubtiposolicitud record);

    int insertSelective(CatSubtiposolicitud record);

    CatSubtiposolicitud selectByPrimaryKey(Long idSubtiposolicitud);

    int updateByPrimaryKeySelective(CatSubtiposolicitud record);

    int updateByPrimaryKey(CatSubtiposolicitud record);
    List<CatSubtiposolicitud> getByCriterio(CatSubtiposolicitud criterio);
    tipoSolicitudDto areaTipoSolicitud(Integer idSubtipoSolicitud);
}