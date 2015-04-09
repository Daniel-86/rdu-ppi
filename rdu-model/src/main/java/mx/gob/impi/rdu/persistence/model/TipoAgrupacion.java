package mx.gob.impi.rdu.persistence.model;

  
  /**
   *
   * Bean TipoAgrupacion que representa el tipo al que pertenece el perfil
   */
   
   @SuppressWarnings("serial")
   public class TipoAgrupacion implements java.io.Serializable { 

    /**
     *Constructor vacio por default
     */
   
    public TipoAgrupacion() {}
    
     protected Integer idTipoAgrupacion;   
     protected String nombre;

    public Integer getIdTipoAgrupacion()
    {
        return idTipoAgrupacion;
    }

    public void setIdTipoAgrupacion(Integer idTipoAgrupacion)
    {
        this.idTipoAgrupacion = idTipoAgrupacion;
    }

    public String getNombre()
    {
        return nombre;
    }

    public void setNombre(String nombre)
    {
        this.nombre = nombre;
    }

     
   

    }
  