
  package mx.gob.impi.rdu.persistence.mappers;
  
  
import mx.gob.impi.rdu.persistence.model.UsuarioSeguridad;
import org.apache.log4j.Logger;
  
  /**
   *
   * Clase UsuarioSeguridadDynamic que contiene los metodos de 
   * persistencia y consulta de la entidad usuario_seguridad,
   * estos se generan de manera dinamica, de acuerdo a las propiedades del
   * bean (parametro)
   */
   
   public class UsuarioSeguridadDynamic  { 



    /**
     * Este codigo se genero con Arquitecto MVC.
     * Este mÃƒÂ©todo selecciona un conjunto de registros en  
     * la tabla usuario_seguridad
     * mediante los criterios  de busqueda existentes	
     */
    public String selectUsuarioSeguridadSql(UsuarioSeguridad pUsuarioSeguridad){
	    org.apache.ibatis.jdbc.SelectBuilder.BEGIN(); 
	    
		org.apache.ibatis.jdbc.SelectBuilder.SELECT("usuarioSeguridad.usuario as usuario ");
		
		org.apache.ibatis.jdbc.SelectBuilder.SELECT("usuarioSeguridad.clave as clave ");
		
		org.apache.ibatis.jdbc.SelectBuilder.SELECT("usuarioSeguridad.correo as correo ");
		
		org.apache.ibatis.jdbc.SelectBuilder.SELECT("usuarioSeguridad.fecha_creacion as fechaCreacion ");
		
		org.apache.ibatis.jdbc.SelectBuilder.SELECT("usuarioSeguridad.cuenta_no_expirada as cuentaNoExpirada ");
		
		org.apache.ibatis.jdbc.SelectBuilder.SELECT("usuarioSeguridad.cuenta_no_bloqueda as cuentaNoBloqueda ");
		
		org.apache.ibatis.jdbc.SelectBuilder.SELECT("usuarioSeguridad.credencial_no_expirada as credencialNoExpirada ");
		
		org.apache.ibatis.jdbc.SelectBuilder.SELECT("usuarioSeguridad.habilitado as habilitado ");
		
		org.apache.ibatis.jdbc.SelectBuilder.SELECT("usuarioSeguridad.intentos_fallidos as intentosFallidos ");
		
		org.apache.ibatis.jdbc.SelectBuilder.SELECT("usuarioSeguridad.instante_bloqueo as instanteBloqueo ");
		
		org.apache.ibatis.jdbc.SelectBuilder.SELECT("usuarioSeguridad.respuesta_secreta as respuestaSecreta ");
		
		org.apache.ibatis.jdbc.SelectBuilder.SELECT("usuarioSeguridad.id_seguridad as idSeguridad ");
		
		org.apache.ibatis.jdbc.SelectBuilder.SELECT("usuarioSeguridad.ventana_id_seguridad as ventanaIdSeguridad ");
		
		org.apache.ibatis.jdbc.SelectBuilder.SELECT("usuarioSeguridad.id_usuario as idUsuario ");
		
		org.apache.ibatis.jdbc.SelectBuilder.SELECT("usuarioSeguridad.id_pregunta_secreta as idPreguntaSecreta ");
		
		org.apache.ibatis.jdbc.SelectBuilder.FROM(" usuario_seguridad usuarioSeguridad");
	    
	    if (pUsuarioSeguridad.getUsuario() != null
	    		 && pUsuarioSeguridad.getUsuario().toString().trim().length() > 0  ) { // originalmente sólo estaba "trim()", se añadió "toString().trim()"
	    
    		org.apache.ibatis.jdbc.SelectBuilder.WHERE(" usuarioSeguridad.usuario like #{usuario} ");
	    
	   	}
	    if (pUsuarioSeguridad.getClave() != null
	    		 && pUsuarioSeguridad.getClave().trim().length() > 0  ) {
	    
    		org.apache.ibatis.jdbc.SelectBuilder.WHERE(" usuarioSeguridad.clave like #{clave} ");
	    
	   	}
	    if (pUsuarioSeguridad.getCorreo() != null
	    		 && pUsuarioSeguridad.getCorreo().trim().length() > 0  ) {
	    
    		org.apache.ibatis.jdbc.SelectBuilder.WHERE(" usuarioSeguridad.correo like #{correo} ");
	    
	   	}
	    if (pUsuarioSeguridad.getFechaCreacion() != null
	     ) {
	    
	     	org.apache.ibatis.jdbc.SelectBuilder.WHERE(" usuarioSeguridad.fecha_creacion = #{fechaCreacion} ");
	     
	   	}
	    if (pUsuarioSeguridad.getCuentaNoExpirada() != null
	     ) {
	    
	     	org.apache.ibatis.jdbc.SelectBuilder.WHERE(" usuarioSeguridad.cuenta_no_expirada = #{cuentaNoExpirada} ");
	     
	   	}
	    if (pUsuarioSeguridad.getCuentaNoBloqueda() != null
	     ) {
	    
	     	org.apache.ibatis.jdbc.SelectBuilder.WHERE(" usuarioSeguridad.cuenta_no_bloqueda = #{cuentaNoBloqueda} ");
	     
	   	}
	    if (pUsuarioSeguridad.getCredencialNoExpirada() != null
	     ) {
	    
	     	org.apache.ibatis.jdbc.SelectBuilder.WHERE(" usuarioSeguridad.credencial_no_expirada = #{credencialNoExpirada} ");
	     
	   	}
	    if (pUsuarioSeguridad.getHabilitado() != null
	     ) {
	    
	     	org.apache.ibatis.jdbc.SelectBuilder.WHERE(" usuarioSeguridad.habilitado = #{habilitado} ");
	     
	   	}
	    if (pUsuarioSeguridad.getIntentosFallidos() != null
	     ) {
	    
	     	org.apache.ibatis.jdbc.SelectBuilder.WHERE(" usuarioSeguridad.intentos_fallidos = #{intentosFallidos} ");
	     
	   	}
	    if (pUsuarioSeguridad.getInstanteBloqueo() != null
	     ) {
	    
	     	org.apache.ibatis.jdbc.SelectBuilder.WHERE(" usuarioSeguridad.instante_bloqueo = #{instanteBloqueo} ");
	     
	   	}
	    if (pUsuarioSeguridad.getRespuestaSecreta() != null
	    		 && pUsuarioSeguridad.getRespuestaSecreta().trim().length() > 0  ) {
	    
    		org.apache.ibatis.jdbc.SelectBuilder.WHERE(" usuarioSeguridad.respuesta_secreta like #{respuestaSecreta} ");
	    
	   	}
	    if (pUsuarioSeguridad.getIdSeguridad() != null
	    		 && pUsuarioSeguridad.getIdSeguridad().trim().length() > 0  ) {
	    
    		org.apache.ibatis.jdbc.SelectBuilder.WHERE(" usuarioSeguridad.id_seguridad like #{idSeguridad} ");
	    
	   	}
	    if (pUsuarioSeguridad.getVentanaIdSeguridad() != null
	     ) {
	    
	     	org.apache.ibatis.jdbc.SelectBuilder.WHERE(" usuarioSeguridad.ventana_id_seguridad = #{ventanaIdSeguridad} ");
	     
	   	}
	    if (pUsuarioSeguridad.getIdUsuario() != null
	     ) {
	    
	     	org.apache.ibatis.jdbc.SelectBuilder.WHERE(" usuarioSeguridad.id_usuario = #{idUsuario} ");
	     
	   	}
	    if (pUsuarioSeguridad.getIdPreguntaSecreta() != null
	     ) {
	    
	     	org.apache.ibatis.jdbc.SelectBuilder.WHERE(" usuarioSeguridad.id_pregunta_secreta = #{idPreguntaSecreta} ");
	     
	   	}
		return org.apache.ibatis.jdbc.SelectBuilder.SQL();
     
    } 
     

	
	

    }
  