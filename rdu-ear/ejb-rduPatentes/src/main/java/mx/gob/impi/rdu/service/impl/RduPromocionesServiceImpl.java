/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.gob.impi.rdu.service.impl;

import java.util.List;
import mx.gob.impi.rdu.dto.PromocionesConOficioDto;
import mx.gob.impi.rdu.persistence.model.CatTipoPromPatentes;
import mx.gob.impi.rdu.service.RduPromocionesService;
import mx.gob.impi.sagpat.persistence.mappers.TipoPromocionMapper;
import org.springframework.beans.factory.annotation.Autowired;
/**
 *
 * @author oracle
 */
public class RduPromocionesServiceImpl implements RduPromocionesService{
    
    @Autowired
    private TipoPromocionMapper rduTipoPromocionMapper;

    public void setRduTipoPromocionMapper(TipoPromocionMapper rduTipoPromocionMapper) {
        this.rduTipoPromocionMapper = rduTipoPromocionMapper;
    }   

    public List<CatTipoPromPatentes> selectTipoPromociones() {
        
        return rduTipoPromocionMapper.selectTipoPromociones();
    }

    public PromocionesConOficioDto getNumeroOficio(PromocionesConOficioDto promocionesConOficio) {
        return rduTipoPromocionMapper.getNumeroOficio(promocionesConOficio);
    }
      
    
}
