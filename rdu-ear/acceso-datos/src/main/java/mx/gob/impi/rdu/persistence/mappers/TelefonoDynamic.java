
  package mx.gob.impi.rdu.persistence.mappers;
  
  
import mx.gob.impi.rdu.persistence.model.Telefono;
import org.apache.log4j.Logger;
  
  /**
   *
   * Clase TelefonoDynamic que contiene los metodos de 
   * persistencia y consulta de la entidad telefono,
   * estos se generan de manera dinamica, de acuerdo a las propiedades del
   * bean (parametro)
   */
   
   public class TelefonoDynamic  { 



    /**
     * Este codigo se genero con Arquitecto MVC.
     * Este mÃƒÂ©todo selecciona un conjunto de registros en  
     * la tabla telefono
     * mediante los criterios  de busqueda existentes	
     */
    public String selectTelefonoSql(Telefono pTelefono){
	    org.apache.ibatis.jdbc.SelectBuilder.BEGIN(); 
	    
		org.apache.ibatis.jdbc.SelectBuilder.SELECT("telefono.id_telefono as idTelefono ");
		
		org.apache.ibatis.jdbc.SelectBuilder.SELECT("telefono.tipo_telefono as tipoTelefono ");
		
		org.apache.ibatis.jdbc.SelectBuilder.SELECT("telefono.lada as lada ");
		
		org.apache.ibatis.jdbc.SelectBuilder.SELECT("telefono.numero as numero ");
		
		org.apache.ibatis.jdbc.SelectBuilder.SELECT("telefono.id_usuario as idUsuario ");
		
		org.apache.ibatis.jdbc.SelectBuilder.FROM(" telefono telefono");
	    
	    if (pTelefono.getIdTelefono() != null
	     ) {
	    
	     	org.apache.ibatis.jdbc.SelectBuilder.WHERE(" telefono.id_telefono = #{idTelefono} ");
	     
	   	}
	    if (pTelefono.getTipoTelefono() != null
	    		   ) {
	    
    		org.apache.ibatis.jdbc.SelectBuilder.WHERE(" telefono.tipo_telefono like #{tipoTelefono} ");
	    
	   	}
	    if (pTelefono.getLada() != null
	    		 && pTelefono.getLada().trim().length() > 0  ) {
	    
    		org.apache.ibatis.jdbc.SelectBuilder.WHERE(" telefono.lada like #{lada} ");
	    
	   	}
	    if (pTelefono.getNumero() != null
	    		 && pTelefono.getNumero().trim().length() > 0  ) {
	    
    		org.apache.ibatis.jdbc.SelectBuilder.WHERE(" telefono.numero like #{numero} ");
	    
	   	}
	    if (pTelefono.getIdUsuario() != null
	     ) {
	    
	     	org.apache.ibatis.jdbc.SelectBuilder.WHERE(" telefono.id_usuario = #{idUsuario} ");
	     
	   	}
		return org.apache.ibatis.jdbc.SelectBuilder.SQL();
     
    } 
     

	
	

    }
  