package mx.gob.impi.rdu.service;

import java.util.List;
import mx.gob.impi.rdu.persistence.model.Pago;

public interface RduPagoService {
	String holaMundo();
        public int insertarpago (Pago pago);
        
        public List<Pago> selectByTramiteId(Long idTramite);
        
}
