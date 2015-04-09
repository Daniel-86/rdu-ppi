
  package mx.gob.impi.rdu.persistence.mappers;
  
  
import mx.gob.impi.rdu.persistence.model.CoordinacionEstatal;
import org.apache.log4j.Logger;
  
  /**
   *
   * Clase CoordinacionEstatalDynamic que contiene los metodos de 
   * persistencia y consulta de la entidad coordinacion_estatal,
   * estos se generan de manera dinamica, de acuerdo a las propiedades del
   * bean (parametro)
   */
   
   public class CoordinacionEstatalDynamic  { 



    /**
     * Este codigo se genero con Arquitecto MVC.
     * Este mÃƒÂ©todo selecciona un conjunto de registros en  
     * la tabla coordinacion_estatal
     * mediante los criterios  de busqueda existentes	
     */
    public String selectCoordinacionEstatalSql(CoordinacionEstatal pCoordinacionEstatal){
	    org.apache.ibatis.jdbc.SelectBuilder.BEGIN(); 
	    
		org.apache.ibatis.jdbc.SelectBuilder.SELECT("coordinacionEstatal.id_coordinacion as idCoordinacion ");
		
		org.apache.ibatis.jdbc.SelectBuilder.SELECT("coordinacionEstatal.nombre as nombre ");
		
		org.apache.ibatis.jdbc.SelectBuilder.SELECT("coordinacionEstatal.id_entidad_federativa as idEntidadFederativa ");
		
		org.apache.ibatis.jdbc.SelectBuilder.SELECT("coordinacionEstatal.id_pais as idPais ");
		
		org.apache.ibatis.jdbc.SelectBuilder.FROM(" coordinacion_estatal coordinacionEstatal");
	    
	    if (pCoordinacionEstatal.getIdCoordinacion() != null
	    		 && pCoordinacionEstatal.getIdCoordinacion().trim().length() > 0  ) {
	    
    		org.apache.ibatis.jdbc.SelectBuilder.WHERE(" coordinacionEstatal.id_coordinacion like #{idCoordinacion} ");
	    
	   	}
	    if (pCoordinacionEstatal.getNombre() != null
	    		 && pCoordinacionEstatal.getNombre().trim().length() > 0  ) {
	    
    		org.apache.ibatis.jdbc.SelectBuilder.WHERE(" coordinacionEstatal.nombre like #{nombre} ");
	    
	   	}
	    if (pCoordinacionEstatal.getIdEntidadFederativa() != null
	     ) {
	    
	     	org.apache.ibatis.jdbc.SelectBuilder.WHERE(" coordinacionEstatal.id_entidad_federativa = #{idEntidadFederativa} ");
	     
	   	}
	    if (pCoordinacionEstatal.getIdPais() != null
	     ) {
	    
	     	org.apache.ibatis.jdbc.SelectBuilder.WHERE(" coordinacionEstatal.id_pais = #{idPais} ");
	     
	   	}
            
            org.apache.ibatis.jdbc.SelectBuilder.ORDER_BY(" coordinacionEstatal.nombre ");
        
            
		return org.apache.ibatis.jdbc.SelectBuilder.SQL();
     
    } 
     

	
	

    }
  