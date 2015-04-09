package mx.gob.impi.rdu.persistence.model;


/**
 *
 * Bean CoordinacionEstatal que representa la entidad coordinacion_estatal
 */
@SuppressWarnings("serial")
public class CoordinacionEstatal implements java.io.Serializable
{

    /**
     *Constructor vacio por default
     */
    public CoordinacionEstatal()
    {
        this.idEntidadFederativa = 0;
        this.entidadFederativa = new EntidadFederativa();
    }
    /**
     * Este codigo se genero con Arquitecto MVC.
     * Esta propiedad corresponde con el mapeo en base de datos 
     * de la columna coordinacion_estatal.id_coordinacion
     *
     */
    protected String idCoordinacion;

    /**
     * Asigna el valor de la propiedad idCoordinacion
     */
    public void setIdCoordinacion(String idCoordinacion)
    {
        this.idCoordinacion = idCoordinacion;
    }

    /**
     * Retorna el valor de la propiedad idCoordinacion
     */
    public String getIdCoordinacion()
    {
        return this.idCoordinacion;
    }
    /**
     * Este codigo se genero con Arquitecto MVC.
     * Esta propiedad corresponde con el mapeo en base de datos 
     * de la columna coordinacion_estatal.nombre
     *
     */
    protected String nombre;

    /**
     * Asigna el valor de la propiedad nombre
     */
    public void setNombre(String nombre)
    {
        this.nombre = nombre;
    }

    /**
     * Retorna el valor de la propiedad nombre
     */
    public String getNombre()
    {
        return this.nombre;
    }
    /**
     * Este codigo se genero con Arquitecto MVC.
     * Esta propiedad corresponde con el mapeo en base de datos 
     * de la columna coordinacion_estatal.id_entidad_federativa
     *
     */
    protected Integer idEntidadFederativa;

    /**
     * Asigna el valor de la propiedad idEntidadFederativa
     */
    public void setIdEntidadFederativa(Integer idEntidadFederativa)
    {
        this.idEntidadFederativa = idEntidadFederativa;
    }

    /**
     * Retorna el valor de la propiedad idEntidadFederativa
     */
    public Integer getIdEntidadFederativa()
    {
        return this.idEntidadFederativa;
    }
    /**
     * Este codigo se genero con Arquitecto MVC.
     * Esta propiedad corresponde con el mapeo en base de datos 
     * de la columna coordinacion_estatal.id_pais
     *
     */
    protected Long idPais;

    /**
     * Asigna el valor de la propiedad idPais
     */
    public void setIdPais(Long idPais)
    {
        this.idPais = idPais;
    }

    /**
     * Retorna el valor de la propiedad idPais
     */
    public Long getIdPais()
    {
        return this.idPais;
    }
    /**
     * Este codigo se genero con Arquitecto MVC.
     * Esta propiedad corresponde con el mapeo en base de datos 
     * de la relacion coordinacion_estatal.id_entidad_federativa
     *
     */
    private EntidadFederativa entidadFederativa;

    /**
     * Asigna el valor de la relacion idEntidadFederativa
     */
    public EntidadFederativa getEntidadFederativa()
    {
        return this.entidadFederativa;
    }

    /**
     * Retorna el valor de la relacion idEntidadFederativa
     */
    public void setEntidadFederativa(EntidadFederativa entidadFederativa)
    {
        this.entidadFederativa = entidadFederativa;
    }

//    /**
//     * Este codigo se genero con Arquitecto MVC.
//     * Esta propiedad corresponde con el mapeo en base de datos 
//     * de la relacion coordinacion_estatal.id_pais
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
    public String toString()
    {
        StringBuilder strBuffer = new StringBuilder();

        strBuffer.append("idCoordinacion=" + (idCoordinacion == null ? "" : idCoordinacion) + "\n ");

        strBuffer.append("nombre=" + (nombre == null ? "" : nombre) + "\n ");

        strBuffer.append("idEntidadFederativa=" + (idEntidadFederativa == null ? "" : idEntidadFederativa) + "\n ");

        strBuffer.append("idPais=" + (idPais == null ? "" : idPais) + "\n ");

        return strBuffer.toString();
    }

    /**
     * metodo copyPropertiesTo 
     */
    public void copyPropertiesTo(CoordinacionEstatal mTarget)
    {

        mTarget.setIdCoordinacion(this.getIdCoordinacion());

        mTarget.setNombre(this.getNombre());

        mTarget.setIdEntidadFederativa(this.getIdEntidadFederativa());

        mTarget.setIdPais(this.getIdPais());

    }

    /**
     * metodo equals
     */
    @Override
    public boolean equals(Object mobjPrueba)
    {
        if (!(mobjPrueba instanceof CoordinacionEstatal))
            return false;
        CoordinacionEstatal objBuffer = (CoordinacionEstatal) mobjPrueba;

        if (this.getIdCoordinacion() != objBuffer.getIdCoordinacion()
                && (this.getIdCoordinacion() == null || objBuffer.getIdCoordinacion() == null
                || !this.getIdCoordinacion().equals(objBuffer.getIdCoordinacion())))
            return false;

        return true;
    }

    /**
     * metodo hashCode
     */
    @Override
    public int hashCode()
    {
        int ret = 0;

        if (this.getIdCoordinacion() != null)
            ret += this.getIdCoordinacion().hashCode();
        ret *= 29;

        return ret;
    }
}
