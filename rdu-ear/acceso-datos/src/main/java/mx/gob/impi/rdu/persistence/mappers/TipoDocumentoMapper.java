package mx.gob.impi.rdu.persistence.mappers;

import java.util.List;
import mx.gob.impi.rdu.persistence.model.TipoDocumento;
import org.apache.ibatis.annotations.Param;

public interface TipoDocumentoMapper {
    int deleteByPrimaryKey(@Param("idArea") Integer idArea, @Param("idTipoDocumento") Integer idTipoDocumento, @Param("sentido") String sentido);

    int insert(TipoDocumento record);

    int insertSelective(TipoDocumento record);

    TipoDocumento selectByPrimaryKey(@Param("idArea") Integer idArea, @Param("idTipoDocumento") Integer idTipoDocumento, @Param("sentido") String sentido);

    int updateByPrimaryKeySelective(TipoDocumento record);

    int updateByPrimaryKey(TipoDocumento record);

    List<TipoDocumento> getByCriterio(TipoDocumento tipoDocumento);
}