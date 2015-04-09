
package mx.gob.impi.rdu.persistence.model;
  
import java.sql.Clob;
import java.sql.Timestamp;
  
  /**
   *
   * Bean UsuarioPerfil que representa la entidad usuario_perfil
   */
   
   @SuppressWarnings("serial")
   public class UsuarioPerfil implements java.io.Serializable { 

    /**
     *Constructor vacio por default
     */
   
    public UsuarioPerfil() {}
    
    
         
    
    /**
     * Este codigo se genero con Arquitecto MVC.
     * Esta propiedad corresponde con el mapeo en base de datos 
     * de la columna usuario_perfil.id_perfil
     *
     */
    
     protected String idPerfil;

   /**
    * Asigna el valor de la propiedad idPerfil
    */
    public void setIdPerfil(String idPerfil) {
        this.idPerfil = idPerfil;
    }

   /**
    * Retorna el valor de la propiedad idPerfil
    */
    public String getIdPerfil() {
        return this.idPerfil;
    }
    
    /**
     * Este codigo se genero con Arquitecto MVC.
     * Esta propiedad corresponde con el mapeo en base de datos 
     * de la columna usuario_perfil.id_usuario
     *
     */
    
     protected Long idUsuario;

   /**
    * Asigna el valor de la propiedad idUsuario
    */
    public void setIdUsuario(Long idUsuario) {
        this.idUsuario = idUsuario;
    }

   /**
    * Retorna el valor de la propiedad idUsuario
    */
    public Long getIdUsuario() {
        return this.idUsuario;
    }
    
    /**
     * Este codigo se genero con Arquitecto MVC.
     * Esta propiedad corresponde con el mapeo en base de datos 
     * de la relacion usuario_perfil.id_perfil
     *
     */
	
	private Perfil perfil;
	
   /**
    * Asigna el valor de la relacion idPerfil
    */
	public Perfil getPerfil(){
		return this.perfil;
	}
	
   /**
    * Retorna el valor de la relacion idPerfil
    */
	public void setPerfil(Perfil perfil){
		this.perfil = perfil;
	}
	
	
    /**
     * Este codigo se genero con Arquitecto MVC.
     * Esta propiedad corresponde con el mapeo en base de datos 
     * de la relacion usuario_perfil.id_usuario
     *
     */
	
	private Usuario usuario;
	
   /**
    * Asigna el valor de la relacion idUsuario
    */
	public Usuario getUsuario(){
		return this.usuario;
	}
	
   /**
    * Retorna el valor de la relacion idUsuario
    */
	public void setUsuario(Usuario usuario){
		this.usuario = usuario;
	}
	
	



   /**
    * metodo toString 
    */
    @Override
	public String toString(){
        StringBuilder strBuffer = new StringBuilder();
    
        	strBuffer.append ("idPerfil=" + (idPerfil == null ? "":idPerfil ) + "\n ");
    
        	strBuffer.append ("idUsuario=" + (idUsuario == null ? "":idUsuario ) + "\n ");
    
        return strBuffer.toString();
	}
    
   /**
    * metodo copyPropertiesTo 
    */
	public void copyPropertiesTo (UsuarioPerfil mTarget){
    
    	 mTarget.setIdPerfil( this.getIdPerfil());
    
    	 mTarget.setIdUsuario( this.getIdUsuario());
    
	}
	
	
   /**
    * metodo equals
    */
    @Override
    public boolean equals (Object mobjPrueba) {
        if (!(mobjPrueba instanceof UsuarioPerfil))
           return false;
        UsuarioPerfil objBuffer = (UsuarioPerfil) mobjPrueba;
	    
	        	if (this.getIdPerfil () != objBuffer.getIdPerfil() && 
	                   (this.getIdPerfil () == null  || objBuffer.getIdPerfil() == null  ||
	                   !this.getIdPerfil ().equals ( objBuffer.getIdPerfil())))
	               return false;
	    	
	        	if (this.getIdUsuario () != objBuffer.getIdUsuario() && 
	                   (this.getIdUsuario () == null  || objBuffer.getIdUsuario() == null  ||
	                   !this.getIdUsuario ().equals ( objBuffer.getIdUsuario())))
	               return false;
	    	
        return true;
    }
	
	
   /**
    * metodo hashCode
    */
    @Override
    public int hashCode () {
        int ret = 0;
	    
        if (this.getIdPerfil() != null) 
            ret += this.getIdPerfil().hashCode();
        ret *=  29;
        
        if (this.getIdUsuario() != null) 
            ret += this.getIdUsuario().hashCode();
        ret *=  29;
        
    return ret;
    }
	

    }
  