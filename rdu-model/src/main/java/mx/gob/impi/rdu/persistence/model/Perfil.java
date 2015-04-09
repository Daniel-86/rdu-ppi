package mx.gob.impi.rdu.persistence.model;



/**
 *
 * Bean Perfil que representa la entidad perfil
 */
@SuppressWarnings("serial")
public class Perfil implements java.io.Serializable
{

    /**
     * Constructor vacio por default
     */
    public Perfil()
    {
        this.idPerfil = "0";
        this.nombre = "";
    }

    public Perfil(String idPerfil, String nombre)
    {
        this.idPerfil = idPerfil;
        this.nombre = nombre;
    }

    public Perfil(String constante)
    {
        this.constante = constante;
    }
    /**
     * Este codigo se genero con Arquitecto MVC. Esta propiedad corresponde con
     * el mapeo en base de datos de la columna perfil.id_perfil
     *
     */
    protected String idPerfil;

    /**
     * Asigna el valor de la propiedad idPerfil
     */
    public void setIdPerfil(String idPerfil)
    {
        this.idPerfil = idPerfil;
    }

    /**
     * Retorna el valor de la propiedad idPerfil
     */
    public String getIdPerfil()
    {
        return this.idPerfil;
    }
    /**
     * Este codigo se genero con Arquitecto MVC. Esta propiedad corresponde con
     * el mapeo en base de datos de la columna perfil.nombre
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
    protected Integer idTipoAgrupacion;

    public Integer getIdTipoAgrupacion()
    {
        return idTipoAgrupacion;
    }

    public void setIdTipoAgrupacion(Integer idTipoAgrupacion)
    {
        this.idTipoAgrupacion = idTipoAgrupacion;
    }
    
    protected String constante;

    public String getConstante()
    {
        return constante;
    }

    public void setConstante(String constante)
    {
        this.constante = constante;
    }
    
    

    /**
     * metodo toString
     */
    @Override
    public String toString()
    {
        StringBuilder strBuffer = new StringBuilder();

        strBuffer.append("idPerfil=" + (idPerfil == null ? "" : idPerfil) + "\n ");

        strBuffer.append("nombre=" + (nombre == null ? "" : nombre) + "\n ");

        return strBuffer.toString();
    }

    /**
     * metodo copyPropertiesTo
     */
    public void copyPropertiesTo(Perfil mTarget)
    {

        mTarget.setIdPerfil(this.getIdPerfil());

        mTarget.setNombre(this.getNombre());

    }

    /**
     * metodo equals
     */
    @Override
    public boolean equals(Object object){
        if(object == null){
            return false;
        }
        
        if (!(object instanceof Perfil)){
            return false;
        }
        Perfil perfil = (Perfil) object;

        if(idPerfil != null && !idPerfil.equals(perfil.getIdPerfil())){
            return false;
        }
        
        return true;
    }

    /**
     * metodo hashCode
     */
    @Override
    public int hashCode()
    {
        int ret = 0;

        if (this.getIdPerfil() != null)
            ret += this.getIdPerfil().hashCode();
        ret *= 29;

        return ret;
    }
}
