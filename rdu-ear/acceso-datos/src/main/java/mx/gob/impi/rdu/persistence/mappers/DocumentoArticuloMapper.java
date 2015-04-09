package mx.gob.impi.rdu.persistence.mappers;

import java.util.List;
import mx.gob.impi.rdu.persistence.model.DocumentoArticulo;
import org.apache.ibatis.annotations.Param;

public interface DocumentoArticuloMapper {
    int deleteByPrimaryKey(@Param("articuloPago") String articuloPago, @Param("idArea") Integer idArea, @Param("idTipoDocumento") Integer idTipoDocumento, @Param("idTipoSolicitud") Integer idTipoSolicitud, @Param("inciso") String inciso, @Param("sentido") String sentido);

    int insert(DocumentoArticulo record);

    int insertSelective(DocumentoArticulo record);

    DocumentoArticulo selectByPrimaryKey(@Param("articuloPago") String articuloPago, @Param("idArea") Integer idArea, @Param("idTipoDocumento") Integer idTipoDocumento, @Param("idTipoSolicitud") Integer idTipoSolicitud, @Param("inciso") String inciso, @Param("sentido") String sentido);

    int updateByPrimaryKeySelective(DocumentoArticulo record);

    int updateByPrimaryKey(DocumentoArticulo record);

    List<DocumentoArticulo> getByCriterio(DocumentoArticulo documento);
}