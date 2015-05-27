/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.gob.impi.sigappi.persistence.mappers;

import java.util.List;
import mx.gob.impi.sigappi.persistence.model.SolicitudWeb;
import org.apache.ibatis.annotations.Param;

/**
 *
 * @author daniel
 */
public interface SolicitudWebMapper {
    
    int insert(mx.gob.impi.sigappi.persistence.model.SolicitudWeb s);
    int update(mx.gob.impi.sigappi.persistence.model.SolicitudWeb s);
    List<SolicitudWeb> findAllByUserAndStatus(@Param("idPromovente")Integer idPromovente, @Param("idStatus")Integer idStatus);
    List<SolicitudWeb> findAllByUser(@Param("idPromovente") Integer idPromovente);
    SolicitudWeb findBySession(@Param("idSolicitud")Integer idSolicitud);
    int nextSequence();
    
}
