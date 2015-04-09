
package mx.gob.impi.rdu.persistence.model;
  
import java.sql.Clob;
import java.sql.Timestamp;
  
  /**
   *
   * Bean Dependencia que representa la entidad dependencia
   */
   
   @SuppressWarnings("serial")
   public class Dependencia implements java.io.Serializable { 

    /**
     *Constructor vacio por default
     */
   
    public Dependencia() {}
    
    
         
    
    /**
     * Este codigo se genero con Arquitecto MVC.
     * Esta propiedad corresponde con el mapeo en base de datos 
     * de la columna dependencia.id_dependencia
     *
     */
    
     protected Integer idDependencia;

   /**
    * Asigna el valor de la propiedad idDependencia
    */
    public void setIdDependencia(Integer idDependencia) {
        this.idDependencia = idDependencia;
    }

   /**
    * Retorna el valor de la propiedad idDependencia
    */
    public Integer getIdDependencia() {
        return this.idDependencia;
    }
    
    /**
     * Este codigo se genero con Arquitecto MVC.
     * Esta propiedad corresponde con el mapeo en base de datos 
     * de la columna dependencia.nombre
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
    
        	strBuffer.append ("idDependencia=" + (idDependencia == null ? "":idDependencia ) + "\n ");
    
        	strBuffer.append ("nombre=" + (nombre == null ? "":nombre ) + "\n ");
    
        return strBuffer.toString();
	}
    
   /**
    * metodo copyPropertiesTo 
    */
	public void copyPropertiesTo (Dependencia mTarget){
    
    	 mTarget.setIdDependencia( this.getIdDependencia());
    
    	 mTarget.setNombre( this.getNombre());
    
	}
	
	
   /**
    * metodo equals
    */
    @Override
    public boolean equals (Object mobjPrueba) {
        if (!(mobjPrueba instanceof Dependencia))
           return false;
        Dependencia objBuffer = (Dependencia) mobjPrueba;
	    
	        	if (this.getIdDependencia () != objBuffer.getIdDependencia() && 
	                   (this.getIdDependencia () == null  || objBuffer.getIdDependencia() == null  ||
	                   !this.getIdDependencia ().equals ( objBuffer.getIdDependencia())))
	               return false;
	    	
        return true;
    }
	
	
   /**
    * metodo hashCode
    */
    @Override
    public int hashCode () {
        int ret = 0;
	    
        if (this.getIdDependencia() != null) 
            ret += this.getIdDependencia().hashCode();
        ret *=  29;
        
    return ret;
    }
	

    }
  