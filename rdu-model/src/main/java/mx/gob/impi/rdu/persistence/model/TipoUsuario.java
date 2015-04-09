
package mx.gob.impi.rdu.persistence.model;
  
import java.sql.Clob;
import java.sql.Timestamp;
  
  /**
   *
   * Bean TipoUsuario que representa la entidad tipo_usuario
   */
   
   @SuppressWarnings("serial")
   public class TipoUsuario implements java.io.Serializable { 

    /**
     *Constructor vacio por default
     */
   
    public TipoUsuario() {}
    
    
         
    
    /**
     * Este codigo se genero con Arquitecto MVC.
     * Esta propiedad corresponde con el mapeo en base de datos 
     * de la columna tipo_usuario.id_tipo
     *
     */
    
     protected Integer idTipo;

   /**
    * Asigna el valor de la propiedad idTipo
    */
    public void setIdTipo(Integer idTipo) {
        this.idTipo = idTipo;
    }

   /**
    * Retorna el valor de la propiedad idTipo
    */
    public Integer getIdTipo() {
        return this.idTipo;
    }
    
    /**
     * Este codigo se genero con Arquitecto MVC.
     * Esta propiedad corresponde con el mapeo en base de datos 
     * de la columna tipo_usuario.nombre
     *
     */
    
     protected String nombre;

   /**
    * Asigna el valor de la propiedad nombre
    */
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

   /**
    * Retorna el valor de la propiedad nombre
    */
    public String getNombre() {
        return this.nombre;
    }
    



   /**
    * metodo toString 
    */
    @Override
	public String toString(){
        StringBuilder strBuffer = new StringBuilder();
    
        	strBuffer.append ("idTipo=" + (idTipo == null ? "":idTipo ) + "\n ");
    
        	strBuffer.append ("nombre=" + (nombre == null ? "":nombre ) + "\n ");
    
        return strBuffer.toString();
	}
    
   /**
    * metodo copyPropertiesTo 
    */
	public void copyPropertiesTo (TipoUsuario mTarget){
    
    	 mTarget.setIdTipo( this.getIdTipo());
    
    	 mTarget.setNombre( this.getNombre());
    
	}
	
	
   /**
    * metodo equals
    */
    @Override
    public boolean equals (Object mobjPrueba) {
        if (!(mobjPrueba instanceof TipoUsuario))
           return false;
        TipoUsuario objBuffer = (TipoUsuario) mobjPrueba;
	    
	        	if (this.getIdTipo () != objBuffer.getIdTipo() && 
	                   (this.getIdTipo () == null  || objBuffer.getIdTipo() == null  ||
	                   !this.getIdTipo ().equals ( objBuffer.getIdTipo())))
	               return false;
	    	
        return true;
    }
	
	
   /**
    * metodo hashCode
    */
    @Override
    public int hashCode () {
        int ret = 0;
	    
        if (this.getIdTipo() != null) 
            ret += this.getIdTipo().hashCode();
        ret *=  29;
        
    return ret;
    }
	

    }
  