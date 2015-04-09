
  package mx.gob.impi.rdu.persistence.mappers;
  
  
import mx.gob.impi.rdu.persistence.model.EntidadFederativa;
import org.apache.log4j.Logger;
  
  /**
   *
   * Clase EntidadFederativaDynamic que contiene los metodos de 
   * persistencia y consulta de la entidad entidad_federativa,
   * estos se generan de manera dinamica, de acuerdo a las propiedades del
   * bean (parametro)
   */
   
   public class EntidadFederativaDynamic  { 



    /**
     * Este codigo se genero con Arquitecto MVC.
     * Este mÃƒÂ©todo selecciona un conjunto de registros en  
     * la tabla entidad_federativa
     * mediante los criterios  de busqueda existentes	
     */
    public String selectEntidadFederativaSql(EntidadFederativa pEntidadFederativa){
	    org.apache.ibatis.jdbc.SelectBuilder.BEGIN(); 
	    
		org.apache.ibatis.jdbc.SelectBuilder.SELECT("entidadFederativa.id_entidad_federativa as idEntidadFederativa ");
		
		org.apache.ibatis.jdbc.SelectBuilder.SELECT("entidadFederativa.nombre as nombre ");
		
		org.apache.ibatis.jdbc.SelectBuilder.SELECT("entidadFederativa.id_pais as idPais ");
		
		org.apache.ibatis.jdbc.SelectBuilder.FROM(" entidad_federativa entidadFederativa");
	    
	    if (pEntidadFederativa.getIdEntidadFederativa() != null
	     ) {
	    
	     	org.apache.ibatis.jdbc.SelectBuilder.WHERE(" entidadFederativa.id_entidad_federativa = #{idEntidadFederativa} ");
	     
	   	}
	    if (pEntidadFederativa.getNombre() != null
	    		 && pEntidadFederativa.getNombre().trim().length() > 0  ) {
	    
    		org.apache.ibatis.jdbc.SelectBuilder.WHERE(" entidadFederativa.nombre like #{nombre} ");
	    
	   	}
	    if (pEntidadFederativa.getIdPais() != null
	     ) {
	    
	     	org.apache.ibatis.jdbc.SelectBuilder.WHERE(" entidadFederativa.id_pais = #{idPais} ");
	     
	   	}
		return org.apache.ibatis.jdbc.SelectBuilder.SQL();
     
    } 
     

	
	

    }
  