package mx.gob.impi.rdu.persistence.model;
  
import java.sql.Clob;
import java.sql.Timestamp;
  
  /**
   *
   * Bean Municipio que representa la entidad municipio
   */
   
   @SuppressWarnings("serial")
   public class Municipio implements java.io.Serializable { 

    /**
     *Constructor vacio por default
     */
   
    public Municipio() {}
    
    
         
    
    /**
     * Este codigo se genero con Arquitecto MVC.
     * Esta propiedad corresponde con el mapeo en base de datos 
     * de la columna municipio.id_municipio
     *
     */
    
     protected Integer idMunicipio;

   /**
    * Asigna el valor de la propiedad idMunicipio
    */
    public void setIdMunicipio(Integer idMunicipio) {
        this.idMunicipio = idMunicipio;
    }

   /**
    * Retorna el valor de la propiedad idMunicipio
    */
    public Integer getIdMunicipio() {
        return this.idMunicipio;
    }
    
    /**
     * Este codigo se genero con Arquitecto MVC.
     * Esta propiedad corresponde con el mapeo en base de datos 
     * de la columna municipio.nombre
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
     * Este codigo se genero con Arquitecto MVC.
     * Esta propiedad corresponde con el mapeo en base de datos 
     * de la columna municipio.id_entidad_federativa
     *
     */
    
     protected Integer idEntidadFederativa;

   /**
    * Asigna el valor de la propiedad idEntidadFederativa
    */
    public void setIdEntidadFederativa(Integer idEntidadFederativa) {
        this.idEntidadFederativa = idEntidadFederativa;
    }

   /**
    * Retorna el valor de la propiedad idEntidadFederativa
    */
    public Integer getIdEntidadFederativa() {
        return this.idEntidadFederativa;
    }
    
    /**
     * Este codigo se genero con Arquitecto MVC.
     * Esta propiedad corresponde con el mapeo en base de datos 
     * de la columna municipio.id_pais
     *
     */
    
     protected Long idPais;

   /**
    * Asigna el valor de la propiedad idPais
    */
    public void setIdPais(Long idPais) {
        this.idPais = idPais;
    }

   /**
    * Retorna el valor de la propiedad idPais
    */
    public Long getIdPais() {
        return this.idPais;
    }
    
    /**
     * Este codigo se genero con Arquitecto MVC.
     * Esta propiedad corresponde con el mapeo en base de datos 
     * de la relacion municipio.id_entidad_federativa
     *
     */
	
	private EntidadFederativa entidadFederativa;
	
   /**
    * Asigna el valor de la relacion idEntidadFederativa
    */
	public EntidadFederativa getEntidadFederativa(){
		return this.entidadFederativa;
	}
	
   /**
    * Retorna el valor de la relacion idEntidadFederativa
    */
	public void setEntidadFederativa(EntidadFederativa entidadFederativa){
		this.entidadFederativa = entidadFederativa;
	}
	
	
//    /**
//     * Este codigo se genero con Arquitecto MVC.
//     * Esta propiedad corresponde con el mapeo en base de datos 
//     * de la relacion municipio.id_pais
//     *
//     */
//	
//	private EntidadFederativa entidadFederativa;
//	
//   /**
//    * Asigna el valor de la relacion idPais
//    */
//	public EntidadFederativa getEntidadFederativa(){
//		return this.entidadFederativa;
//	}
//	
//   /**
//    * Retorna el valor de la relacion idPais
//    */
//	public void setEntidadFederativa(EntidadFederativa entidadFederativa){
//		this.entidadFederativa = entidadFederativa;
//	}
//	
	



   /**
    * metodo toString 
    */
    @Override
	public String toString(){
        StringBuilder strBuffer = new StringBuilder();
    
        	strBuffer.append ("idMunicipio=" + (idMunicipio == null ? "":idMunicipio ) + "\n ");
    
        	strBuffer.append ("nombre=" + (nombre == null ? "":nombre ) + "\n ");
    
        	strBuffer.append ("idEntidadFederativa=" + (idEntidadFederativa == null ? "":idEntidadFederativa ) + "\n ");
    
        	strBuffer.append ("idPais=" + (idPais == null ? "":idPais ) + "\n ");
    
        return strBuffer.toString();
	}
    
   /**
    * metodo copyPropertiesTo 
    */
	public void copyPropertiesTo (Municipio mTarget){
    
    	 mTarget.setIdMunicipio( this.getIdMunicipio());
    
    	 mTarget.setNombre( this.getNombre());
    
    	 mTarget.setIdEntidadFederativa( this.getIdEntidadFederativa());
    
    	 mTarget.setIdPais( this.getIdPais());
    
	}
	
	
   /**
    * metodo equals
    */
    @Override
    public boolean equals (Object mobjPrueba) {
        if (!(mobjPrueba instanceof Municipio))
           return false;
        Municipio objBuffer = (Municipio) mobjPrueba;
	    
	        	if (this.getIdMunicipio () != objBuffer.getIdMunicipio() && 
	                   (this.getIdMunicipio () == null  || objBuffer.getIdMunicipio() == null  ||
	                   !this.getIdMunicipio ().equals ( objBuffer.getIdMunicipio())))
	               return false;
	    	
        return true;
    }
	
	
   /**
    * metodo hashCode
    */
    @Override
    public int hashCode () {
        int ret = 0;
	    
        if (this.getIdMunicipio() != null) 
            ret += this.getIdMunicipio().hashCode();
        ret *=  29;
        
    return ret;
    }
	

    }
  