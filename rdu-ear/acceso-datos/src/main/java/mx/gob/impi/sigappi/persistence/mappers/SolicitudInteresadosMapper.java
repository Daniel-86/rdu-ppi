package mx.gob.impi.sigappi.persistence.mappers;

import java.util.List;
import mx.gob.impi.sigappi.persistence.mappers.*;
import mx.gob.impi.sigappi.persistence.model.SolicitudInteresados;
import org.apache.ibatis.annotations.Param;



public interface SolicitudInteresadosMapper {
    int insert(mx.gob.impi.sigappi.persistence.model.SolicitudInteresados record);

    
    List<SolicitudInteresados> selectByTitle(@Param("title")String title);
    List<SolicitudInteresados> selectByCodInteresado(@Param("codInteresado")Integer codInteresado);
    List<SolicitudInteresados> findByUserAndSequence(@Param("codInteresado")Integer codInteresado, @Param("secuencia")Integer secuencia);
    int updateSequence(@Param("title")String title, @Param("codInteresado") Integer codInteresado, @Param("secuencia") Integer secuencia);
}