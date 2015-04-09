/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.gob.impi.rdu.service;

import java.util.List;
import mx.gob.impi.rdu.persistence.model.TramitePromocionPatente;
import mx.gob.impi.rdu.util.PromocionNoExisteException;
import mx.gob.impi.rdu.util.SolicitudNoExisteException;
import mx.gob.impi.sagpat.persistence.model.ResultadoOficioPromocion;
import mx.gob.impi.sagpat.persistence.model.SolicitudTitularDto;
import mx.gob.impi.sagpat.persistence.model.TramiteOficio;

/**
 *
 * @author User
 */
public interface PromocionesService {
    
    ResultadoOficioPromocion obtenerDatosOficioPromocion(TramiteOficio tramiteOficio);
    
    int guardarPromocionPatente(TramitePromocionPatente promocion) throws SolicitudNoExisteException, PromocionNoExisteException;
    
    List<SolicitudTitularDto> selectTitularesByPromocion(SolicitudTitularDto solicitudTitular);
}
