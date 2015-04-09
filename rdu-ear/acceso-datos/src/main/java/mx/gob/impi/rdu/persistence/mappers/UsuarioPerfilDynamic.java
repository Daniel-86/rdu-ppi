
  package mx.gob.impi.rdu.persistence.mappers;
  
  
import mx.gob.impi.rdu.persistence.model.UsuarioPerfil;
import org.apache.log4j.Logger;
  
  /**
   *
   * Clase UsuarioPerfilDynamic que contiene los metodos de 
   * persistencia y consulta de la entidad usuario_perfil,
   * estos se generan de manera dinamica, de acuerdo a las propiedades del
   * bean (parametro)
   */
   
   public class UsuarioPerfilDynamic  { 



    /**
     * Este codigo se genero con Arquitecto MVC.
     * Este mÃƒÂ©todo selecciona un conjunto de registros en  
     * la tabla usuario_perfil
     * mediante los criterios  de busqueda existentes	
     */
    public String selectUsuarioPerfilSql(UsuarioPerfil pUsuarioPerfil){
	    org.apache.ibatis.jdbc.SelectBuilder.BEGIN(); 
	    
		org.apache.ibatis.jdbc.SelectBuilder.SELECT("usuarioPerfil.id_perfil as idPerfil ");
		
		org.apache.ibatis.jdbc.SelectBuilder.SELECT("usuarioPerfil.id_usuario as idUsuario ");
		
		org.apache.ibatis.jdbc.SelectBuilder.FROM(" usuario_perfil usuarioPerfil");
	    
	    if (pUsuarioPerfil.getIdPerfil() != null
	    		 && pUsuarioPerfil.getIdPerfil().trim().length() > 0  ) {
	    
    		org.apache.ibatis.jdbc.SelectBuilder.WHERE(" usuarioPerfil.id_perfil like #{idPerfil} ");
	    
	   	}
	    if (pUsuarioPerfil.getIdUsuario() != null
	     ) {
	    
	     	org.apache.ibatis.jdbc.SelectBuilder.WHERE(" usuarioPerfil.id_usuario = #{idUsuario} ");
	     
	   	}
		return org.apache.ibatis.jdbc.SelectBuilder.SQL();
     
    } 
     

	
	

    }
  