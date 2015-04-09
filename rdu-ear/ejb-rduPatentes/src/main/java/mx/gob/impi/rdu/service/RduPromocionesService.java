/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.gob.impi.rdu.service;

import java.util.List;
import mx.gob.impi.rdu.dto.PromocionesConOficioDto;
import mx.gob.impi.rdu.persistence.model.CatTipoPromPatentes;

/**
 *
 * @author oracle
 */
public interface RduPromocionesService {
    List<CatTipoPromPatentes> selectTipoPromociones();
    PromocionesConOficioDto getNumeroOficio(PromocionesConOficioDto promocionesConOficio);
    
}
