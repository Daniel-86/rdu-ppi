/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.gob.impi.sigappi.persistence.mappers;

import java.util.List;
import mx.gob.impi.sigappi.persistence.model.SolicitudRevision;
import org.apache.ibatis.annotations.Param;

/**
 *
 * @author daniel
 */
public interface SolicitudRevisionMapper {

    int insert(mx.gob.impi.sigappi.persistence.model.SolicitudRevision record);

    int update(mx.gob.impi.sigappi.persistence.model.SolicitudRevision record);

    List<SolicitudRevision> findByCodInteresadoAndSecuencia(@Param("codInteresado") Integer codInteresado, @Param("secuencia") Integer secuencia);
    
    void delete(mx.gob.impi.sigappi.persistence.model.SolicitudRevision record);
    
    int append(mx.gob.impi.sigappi.persistence.model.SolicitudRevision record);
    
    List<SolicitudRevision> findByCodInteresadoAndSecuenciaAndSession(@Param("codInteresado") Integer codInteresado, @Param("secuencia") Integer secuencia, @Param("idSolicitud")Integer idSolicitud);
}
