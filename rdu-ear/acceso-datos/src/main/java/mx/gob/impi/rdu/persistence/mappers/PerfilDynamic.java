
  package mx.gob.impi.rdu.persistence.mappers;
  
  
import mx.gob.impi.rdu.persistence.model.Perfil;
import org.apache.log4j.Logger;
  
  /**
   *
   * Clase PerfilDynamic que contiene los metodos de 
   * persistencia y consulta de la entidad perfil,
   * estos se generan de manera dinamica, de acuerdo a las propiedades del
   * bean (parametro)
   */
   
   public class PerfilDynamic  { 



    /**
     * Este codigo se genero con Arquitecto MVC.
     * Este mÃƒÂ©todo selecciona un conjunto de registros en  
     * la tabla perfil
     * mediante los criterios  de busqueda existentes	
     */
    public String selectPerfilSql(Perfil pPerfil){
	    org.apache.ibatis.jdbc.SelectBuilder.BEGIN(); 
	    
		org.apache.ibatis.jdbc.SelectBuilder.SELECT("perfil.id_perfil as idPerfil ");
		
		org.apache.ibatis.jdbc.SelectBuilder.SELECT("perfil.nombre as nombre ");
		
		org.apache.ibatis.jdbc.SelectBuilder.FROM(" perfil perfil");
	    
	    if (pPerfil.getIdPerfil() != null
	    		 && pPerfil.getIdPerfil().trim().length() > 0  ) {
	    
    		org.apache.ibatis.jdbc.SelectBuilder.WHERE(" perfil.id_perfil like #{idPerfil} ");
	    
	   	}
	    if (pPerfil.getNombre() != null
	    		 && pPerfil.getNombre().trim().length() > 0  ) {
	    
    		org.apache.ibatis.jdbc.SelectBuilder.WHERE(" perfil.nombre like #{nombre} ");
	    
	   	}
		return org.apache.ibatis.jdbc.SelectBuilder.SQL();
     
    } 
     

	
	

    }
  