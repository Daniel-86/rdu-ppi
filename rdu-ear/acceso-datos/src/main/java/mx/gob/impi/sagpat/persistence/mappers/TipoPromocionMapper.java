package mx.gob.impi.sagpat.persistence.mappers;

import java.util.List;
import mx.gob.impi.rdu.dto.PromocionesConOficioDto;
import mx.gob.impi.rdu.persistence.model.CatTipoPromPatentes;

public interface TipoPromocionMapper {
       
    List<CatTipoPromPatentes> selectTipoPromociones();
    PromocionesConOficioDto getNumeroOficio(PromocionesConOficioDto promocionesConOficio);    
    
}