
  package mx.gob.impi.rdu.persistence.mappers;
  
  
import mx.gob.impi.rdu.persistence.model.TipoUsuario;
import org.apache.log4j.Logger;
  
  /**
   *
   * Clase TipoUsuarioDynamic que contiene los metodos de 
   * persistencia y consulta de la entidad tipo_usuario,
   * estos se generan de manera dinamica, de acuerdo a las propiedades del
   * bean (parametro)
   */
   
   public class TipoUsuarioDynamic  { 



    /**
     * Este codigo se genero con Arquitecto MVC.
     * Este mÃƒÂ©todo selecciona un conjunto de registros en  
     * la tabla tipo_usuario
     * mediante los criterios  de busqueda existentes	
     */
    public String selectTipoUsuarioSql(TipoUsuario pTipoUsuario){
	    org.apache.ibatis.jdbc.SelectBuilder.BEGIN(); 
	    
		org.apache.ibatis.jdbc.SelectBuilder.SELECT("tipoUsuario.id_tipo as idTipo ");
		
		org.apache.ibatis.jdbc.SelectBuilder.SELECT("tipoUsuario.nombre as nombre ");
		
		org.apache.ibatis.jdbc.SelectBuilder.FROM(" tipo_usuario tipoUsuario");
	    
	    if (pTipoUsuario.getIdTipo() != null
	     ) {
	    
	     	org.apache.ibatis.jdbc.SelectBuilder.WHERE(" tipoUsuario.id_tipo = #{idTipo} ");
	     
	   	}
	    if (pTipoUsuario.getNombre() != null
	    		 && pTipoUsuario.getNombre().trim().length() > 0  ) {
	    
    		org.apache.ibatis.jdbc.SelectBuilder.WHERE(" tipoUsuario.nombre like #{nombre} ");
	    
	   	}
		return org.apache.ibatis.jdbc.SelectBuilder.SQL();
     
    } 
     

	
	

    }
  