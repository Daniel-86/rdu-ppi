package mx.gob.impi.rdu.service;

import mx.gob.impi.rdu.persistence.model.PromocionesPatentes;
public interface JmsService {
	
	 String getCatalogoJms(String nombreCatalogo);
         String getCatTipoPromocionJms(String nombreCatalogo); 
         String getTipoPromByOficioJms(String idOficio);
         String setTramitePromoPatJms(PromocionesPatentes promocionesPatentes);

}
