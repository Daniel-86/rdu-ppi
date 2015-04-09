package mx.gob.impi.rdu.persistence.mappers;

import java.util.List;
import mx.gob.impi.rdu.persistence.model.CatTipoSolicitante;

public interface CatTipoSolicitanteMapper {

    int deleteByPrimaryKey(Long idTipoSolicitante);

    CatTipoSolicitante selectByPrimaryKey(Long idTipoSolicitante);

    int updateByPrimaryKeySelective(CatTipoSolicitante record);

    int updateByPrimaryKey(CatTipoSolicitante record);
     List<CatTipoSolicitante> getByCriterio(CatTipoSolicitante criterio);
}
