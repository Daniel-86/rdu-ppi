package mx.gob.impi.rdu.service;

import java.util.List;
import mx.gob.impi.rdu.persistence.model.CatAreaPromPatentes;
import mx.gob.impi.rdu.persistence.model.CatTipoPromPatentes;
import mx.gob.impi.rdu.persistence.model.TipoPromPatByOficio;



public interface AdminJmsService{
	
	List<CatAreaPromPatentes> getCatalogo(String cadenaXML);
        List<CatTipoPromPatentes> getCatTipoPromPat(String cadenaXML);
        List<TipoPromPatByOficio> getTipoPromByOficio(String cadenaXML);
        String getTramitePromocionPatente(String cadenaXML);
        String setTramitePromoPatJms(String cadenaXML);
}
