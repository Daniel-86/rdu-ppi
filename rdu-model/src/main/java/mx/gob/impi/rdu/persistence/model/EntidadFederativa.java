package mx.gob.impi.rdu.persistence.model;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * Bean EntidadFederativa que representa la entidad entidad_federativa
 */
@SuppressWarnings("serial")
@XmlAccessorType(XmlAccessType.FIELD)
public class EntidadFederativa implements java.io.Serializable {

    /**
     *Constructor vacio por default
     */
    public EntidadFederativa() {
        //this.idEntidadFederativa = 0;
        this.idPais = 1L;
    }

    public EntidadFederativa(Integer idEntidadFederativa) {
        this.idEntidadFederativa = idEntidadFederativa;
    }

    public EntidadFederativa(String nombre) {
        this.nombre = nombre;
    }
    
    
    
    
    /**
     * Este codigo se genero con Arquitecto MVC.
     * Esta propiedad corresponde con el mapeo en base de datos 
     * de la columna entidad_federativa.id_entidad_federativa
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
     * de la columna entidad_federativa.nombre
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
     * de la columna entidad_federativa.id_pais
     *
     */
    @XmlTransient
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
     * de la relacion entidad_federativa.id_pais
     *
     */
    private Pais pais;

    /**
     * Asigna el valor de la relacion idPais
     */
    public Pais getPais() {
        return this.pais;
    }

    /**
     * Retorna el valor de la relacion idPais
     */
    public void setPais(Pais pais) {
        this.pais = pais;
    }

    /**
     * metodo toString 
     */
    @Override
    public String toString() {
        StringBuilder strBuffer = new StringBuilder();

        strBuffer.append("idEntidadFederativa=").append(idEntidadFederativa == null ? "" : idEntidadFederativa).append("\n ");

        strBuffer.append("nombre=").append(nombre == null ? "" : nombre).append("\n ");

        strBuffer.append("idPais=").append(idPais == null ? "" : idPais).append("\n ");

        return strBuffer.toString();
    }

    /**
     * metodo copyPropertiesTo 
     */
    public void copyPropertiesTo(EntidadFederativa mTarget) {

        mTarget.setIdEntidadFederativa(this.getIdEntidadFederativa());

        mTarget.setNombre(this.getNombre());

        mTarget.setIdPais(this.getIdPais());

    }

    /**
     * metodo equals
     */
    @Override
    public boolean equals(Object mobjPrueba) {
        if (!(mobjPrueba instanceof EntidadFederativa)) {
            return false;
        }
        EntidadFederativa objBuffer = (EntidadFederativa) mobjPrueba;

        if (this.getIdEntidadFederativa() != objBuffer.getIdEntidadFederativa()
                && (this.getIdEntidadFederativa() == null || objBuffer.getIdEntidadFederativa() == null
                || !this.getIdEntidadFederativa().equals(objBuffer.getIdEntidadFederativa()))) {
            return false;
        }

        if (this.getIdPais() != objBuffer.getIdPais()
                && (this.getIdPais() == null || objBuffer.getIdPais() == null
                || !this.getIdPais().equals(objBuffer.getIdPais()))) {
            return false;
        }

        return true;
    }

    /**
     * metodo hashCode
     */
    @Override
    public int hashCode() {
        int ret = 0;

        if (this.getIdEntidadFederativa() != null) {
            ret += this.getIdEntidadFederativa().hashCode();
        }
        ret *= 29;

        if (this.getIdPais() != null) {
            ret += this.getIdPais().hashCode();
        }
        ret *= 29;

        return ret;
    }
}
