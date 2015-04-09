
  package mx.gob.impi.rdu.persistence.mappers;
  
  
import mx.gob.impi.rdu.persistence.model.CoordinacionEstatal;
import mx.gob.impi.rdu.persistence.model.EntidadFederativa;
import mx.gob.impi.rdu.persistence.model.Usuario;
import org.apache.ibatis.jdbc.SelectBuilder;  
  
  /**
   *
   * Clase UsuarioDynamic que contiene los metodos de 
   * persistencia y consulta de la entidad usuario,
   * estos se generan de manera dinamica, de acuerdo a las propiedades del
   * bean (parametro)
   */
   
   public class UsuarioDynamic  { 



    /**
     * Este codigo se genero con Arquitecto MVC.
     * Este mÃƒÂ©todo selecciona un conjunto de registros en  
     * la tabla usuario
     * mediante los criterios  de busqueda existentes	
     */
    public String selectUsuarioSql(Usuario pUsuario){
	    org.apache.ibatis.jdbc.SelectBuilder.BEGIN(); 
	    
		org.apache.ibatis.jdbc.SelectBuilder.SELECT("usuario.id_usuario as idUsuario ");
		
		org.apache.ibatis.jdbc.SelectBuilder.SELECT("usuario.nombre as nombre ");
		
		org.apache.ibatis.jdbc.SelectBuilder.SELECT("usuario.apellido_paterno as apellidoPaterno ");
		
		org.apache.ibatis.jdbc.SelectBuilder.SELECT("usuario.apellido_materno as apellidoMaterno ");
		
		org.apache.ibatis.jdbc.SelectBuilder.SELECT("usuario.id_domicilio as idDomicilio ");
		
		org.apache.ibatis.jdbc.SelectBuilder.SELECT("usuario.id_tipo as idTipo ");
		
		org.apache.ibatis.jdbc.SelectBuilder.SELECT("usuario.id_dependencia as idDependencia ");				
		
		org.apache.ibatis.jdbc.SelectBuilder.SELECT("usuario.id_pais as idPais ");
		
		org.apache.ibatis.jdbc.SelectBuilder.FROM(" usuario usuario");
	    
	    if (pUsuario.getIdUsuario() != null
	     ) {
	    
	     	org.apache.ibatis.jdbc.SelectBuilder.WHERE(" usuario.id_usuario = #{idUsuario} ");
	     
	   	}
	    if (pUsuario.getNombre() != null
	    		 && pUsuario.getNombre().trim().length() > 0  ) {
	    
    		org.apache.ibatis.jdbc.SelectBuilder.WHERE(" usuario.nombre like #{nombre} ");
	    
	   	}
	    if (pUsuario.getApellidoPaterno() != null
	    		 && pUsuario.getApellidoPaterno().trim().length() > 0  ) {
	    
    		org.apache.ibatis.jdbc.SelectBuilder.WHERE(" usuario.apellido_paterno like #{apellidoPaterno} ");
	    
	   	}
	    if (pUsuario.getApellidoMaterno() != null
	    		 && pUsuario.getApellidoMaterno().trim().length() > 0  ) {
	    
    		org.apache.ibatis.jdbc.SelectBuilder.WHERE(" usuario.apellido_materno like #{apellidoMaterno} ");
	    
	   	}
	    if (pUsuario.getIdDomicilio() != null
	     ) {
	    
	     	org.apache.ibatis.jdbc.SelectBuilder.WHERE(" usuario.id_domicilio = #{idDomicilio} ");
	     
	   	}
	    if (pUsuario.getIdTipo() != null
	     ) {
	    
	     	org.apache.ibatis.jdbc.SelectBuilder.WHERE(" usuario.id_tipo = #{idTipo} ");
	     
	   	}
	    if (pUsuario.getIdDependencia() != null
	     ) {
	    
	     	org.apache.ibatis.jdbc.SelectBuilder.WHERE(" usuario.id_dependencia = #{idDependencia} ");
	     
	   	}	    
	    if (pUsuario.getIdPais() != null
	     ) {
	    
	     	org.apache.ibatis.jdbc.SelectBuilder.WHERE(" usuario.id_pais = #{idPais} ");
	     
	   	}
		return org.apache.ibatis.jdbc.SelectBuilder.SQL();
     
    } 
     

    public String selectByFiltro(CoordinacionEstatal coordinacionEstatal) {

        SelectBuilder.BEGIN();
        SelectBuilder.SELECT(" usuario.id_usuario ");
        SelectBuilder.SELECT(" usuario.nombre ");
        SelectBuilder.SELECT(" usuario.apellido_paterno ");
        SelectBuilder.SELECT(" usuario.apellido_materno ");
        SelectBuilder.SELECT(" usuario.id_domicilio ");
        SelectBuilder.SELECT(" usuario.id_tipo ");
        SelectBuilder.SELECT(" usuario.id_dependencia ");        
        SelectBuilder.SELECT(" usuario.id_coordinacion ");
        SelectBuilder.SELECT(" usuario.id_pais ");
        SelectBuilder.SELECT(" ce.ID_ENTIDAD_FEDERATIVA ");
        SelectBuilder.SELECT(" ef.NOMBRE as entidad_federativa ");
        SelectBuilder.SELECT(" ce.NOMBRE as coordinacion ");
                
        SelectBuilder.FROM(" usuario usuario ");
        SelectBuilder.LEFT_OUTER_JOIN(" COORDINACION_ESTATAL ce ON TRIM(usuario.ID_COORDINACION) = TRIM(ce.ID_COORDINACION) ");
        SelectBuilder.LEFT_OUTER_JOIN(" ENTIDAD_FEDERATIVA ef ON ce.ID_ENTIDAD_FEDERATIVA = ef.ID_ENTIDAD_FEDERATIVA ");
        if (coordinacionEstatal != null) {
            EntidadFederativa entidadFederativa = coordinacionEstatal.getEntidadFederativa();
            if (entidadFederativa != null && entidadFederativa.getIdEntidadFederativa() != null
                    && entidadFederativa.getIdEntidadFederativa() > 0) {

                SelectBuilder.WHERE(" ce.ID_ENTIDAD_FEDERATIVA = #{entidadFederativa.idEntidadFederativa} ");
            }
            
            if(coordinacionEstatal.getIdCoordinacion() != null && !coordinacionEstatal.getIdCoordinacion().trim().equals("")){

                SelectBuilder.WHERE(" ce.ID_COORDINACION = #{idCoordinacion} ");
            }
        }
        
       return SelectBuilder.SQL();
    }
	
}
