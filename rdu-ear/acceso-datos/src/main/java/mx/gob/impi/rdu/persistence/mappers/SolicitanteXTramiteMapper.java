package mx.gob.impi.rdu.persistence.mappers;

import java.util.List;
import mx.gob.impi.rdu.persistence.model.SolicitanteXTramite;
import org.apache.ibatis.annotations.Param;

public interface SolicitanteXTramiteMapper {
    int deleteByPrimaryKey(@Param("idSolicitante") Integer idSolicitante, @Param("idTramite") Integer idTramite);

    int insert(SolicitanteXTramite record);

    int insertSelective(SolicitanteXTramite record);

    List<SolicitanteXTramite> obtenerSolicitantesXTramite(Long idTramite);

    int deleteByTramite(Long idTramite);
}