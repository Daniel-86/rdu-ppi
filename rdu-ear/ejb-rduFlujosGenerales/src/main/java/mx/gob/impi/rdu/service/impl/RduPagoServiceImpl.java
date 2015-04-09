package mx.gob.impi.rdu.service.impl;


import java.util.List;
import mx.gob.impi.rdu.persistence.mappers.PagoMapper;
import mx.gob.impi.rdu.persistence.model.Pago;
import mx.gob.impi.rdu.service.RduPagoService;
import org.apache.log4j.Logger;

public class RduPagoServiceImpl implements RduPagoService {
    private Logger lger = Logger.getLogger(this.getClass());
    private PagoMapper rduPagoMapper;

    public void setRduPagoMapper(PagoMapper rduPagoMapper) {
        this.rduPagoMapper = rduPagoMapper;
    }
	
	public String holaMundo() {
		String resultado="";
	
		System.out.println("******entra AL>1: servicio.holamundo()");
		try{
			
			
			System.out.println("******ejecutando: dao.nombreempleados");
			
		}catch(Exception e){
			System.out.println("******Errrorr>>>>>>>>>mensaje: "+e.getMessage()+" localized: " + e.getLocalizedMessage());
			//throw e.getCause();
		}
		return resultado;		
		//return "Hola Mundo";
	}

    public int insertarpago(Pago pago) {
        int tramitesActualizados=0;
        try {
            tramitesActualizados=this.rduPagoMapper.insert(pago);
        }catch(Exception e){
                lger.error("Ocurrio un error en el metodo RduPagoServiceImpl.insertarpago: ", e);
        }
        return tramitesActualizados;
    }

    
    public List<Pago> selectByTramiteId(Long idTramite) {
        try {
            return this.rduPagoMapper.selectByTramiteId(idTramite);
        } catch (Exception e) {
            lger.error("Ocurrio un error en el metodo RduPagoServiceImpl.selectByTramiteId: ", e);
            e.printStackTrace();
            
        }
        return null;
        
    }

}
