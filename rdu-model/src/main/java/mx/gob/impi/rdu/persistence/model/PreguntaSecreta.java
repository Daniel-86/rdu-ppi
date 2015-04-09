package mx.gob.impi.rdu.persistence.model;
  
import java.sql.Clob;
import java.sql.Timestamp;
  
  /**
   *
   * Bean PreguntaSecreta que representa la entidad pregunta_secreta
   */
   
   @SuppressWarnings("serial")
   public class PreguntaSecreta implements java.io.Serializable { 

    /**
     *Constructor vacio por default
     */
   
    public PreguntaSecreta() {}
    
    
         
    
    /**
     * Este codigo se genero con Arquitecto MVC.
     * Esta propiedad corresponde con el mapeo en base de datos 
     * de la columna pregunta_secreta.id_pregunta_secreta
     *
     */
    
     protected Integer idPreguntaSecreta;

   /**
    * Asigna el valor de la propiedad idPreguntaSecreta
    */
    public void setIdPreguntaSecreta(Integer idPreguntaSecreta) {
        this.idPreguntaSecreta = idPreguntaSecreta;
    }

   /**
    * Retorna el valor de la propiedad idPreguntaSecreta
    */
    public Integer getIdPreguntaSecreta() {
        return this.idPreguntaSecreta;
    }
    
    /**
     * Este codigo se genero con Arquitecto MVC.
     * Esta propiedad corresponde con el mapeo en base de datos 
     * de la columna pregunta_secreta.pregunta
     *
     */
    
     protected String pregunta;

   /**
    * Asigna el valor de la propiedad pregunta
    */
    public void setPregunta(String pregunta) {
        this.pregunta = pregunta;
    }

   /**
    * Retorna el valor de la propiedad pregunta
    */
    public String getPregunta() {
        return this.pregunta;
    }
    



   /**
    * metodo toString 
    */
    @Override
	public String toString(){
        StringBuilder strBuffer = new StringBuilder();
    
        	strBuffer.append ("idPreguntaSecreta=" + (idPreguntaSecreta == null ? "":idPreguntaSecreta ) + "\n ");
    
        	strBuffer.append ("pregunta=" + (pregunta == null ? "":pregunta ) + "\n ");
    
        return strBuffer.toString();
	}
    
   /**
    * metodo copyPropertiesTo 
    */
	public void copyPropertiesTo (PreguntaSecreta mTarget){
    
    	 mTarget.setIdPreguntaSecreta( this.getIdPreguntaSecreta());
    
    	 mTarget.setPregunta( this.getPregunta());
    
	}
	
	
   /**
    * metodo equals
    */
    @Override
    public boolean equals (Object mobjPrueba) {
        if (!(mobjPrueba instanceof PreguntaSecreta))
           return false;
        PreguntaSecreta objBuffer = (PreguntaSecreta) mobjPrueba;
	    
	        	if (this.getIdPreguntaSecreta () != objBuffer.getIdPreguntaSecreta() && 
	                   (this.getIdPreguntaSecreta () == null  || objBuffer.getIdPreguntaSecreta() == null  ||
	                   !this.getIdPreguntaSecreta ().equals ( objBuffer.getIdPreguntaSecreta())))
	               return false;
	    	
        return true;
    }
	
	
   /**
    * metodo hashCode
    */
    @Override
    public int hashCode () {
        int ret = 0;
	    
        if (this.getIdPreguntaSecreta() != null) 
            ret += this.getIdPreguntaSecreta().hashCode();
        ret *=  29;
        
    return ret;
    }
	

    }
  