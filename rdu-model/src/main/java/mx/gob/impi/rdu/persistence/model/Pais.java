package mx.gob.impi.rdu.persistence.model;


import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * Bean Pais que representa la entidad pais
 */
@SuppressWarnings("serial")
@XmlAccessorType(XmlAccessType.FIELD)
public class Pais implements java.io.Serializable {

    @XmlTransient
    private Boolean excluirOrg;
    @XmlTransient
    private Boolean incluirOrg;

    /**
     * Constructor vacio por default
     */
    public Pais() {
    }
    /**
     * Este codigo se genero con Arquitecto MVC. Esta propiedad corresponde con
     * el mapeo en base de datos de la columna pais.id_pais
     *
     */
    @XmlTransient
    private Long idArea;
    @XmlTransient
    private Long idPais;
    private String nacionalidad;
    private String clavePais;
    private String nombre;

    public Pais(Long idArea) {
        this.idArea = idArea;
    }

    public Pais(Long idArea, Boolean excluirOrg) {
        this.excluirOrg = excluirOrg;
        this.idArea = idArea;
    }

    public Pais(Long idArea, Boolean excluirOrg, Boolean incluirOrg) {
        this.excluirOrg = excluirOrg;
        this.incluirOrg = incluirOrg;
        this.idArea = idArea;
    }

    public Long getIdArea() {
        return idArea;
    }

    public void setIdArea(Long idArea) {
        this.idArea = idArea;
    }

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

    public String getNacionalidad() {
        return nacionalidad;
    }

    public void setNacionalidad(String nacionalidad) {
        this.nacionalidad = nacionalidad;
    }

    /**
     * Este codigo se genero con Arquitecto MVC. Esta propiedad corresponde con
     * el mapeo en base de datos de la columna pais.nombre
     *
     */
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

    public String getClavePais() {
        return clavePais;
    }

    public void setClavePais(String clavePais) {
        this.clavePais = clavePais;
    }

    /**
     * metodo toString
     */
    @Override
    public String toString() {
        StringBuilder strBuffer = new StringBuilder();

        strBuffer.append("idPais=" + (idPais == null ? "" : idPais) + "\n ");

        strBuffer.append("nombre=" + (nombre == null ? "" : nombre) + "\n ");

        return strBuffer.toString();
    }

    /**
     * metodo copyPropertiesTo
     */
    public void copyPropertiesTo(Pais mTarget) {

        mTarget.setIdPais(this.getIdPais());

        mTarget.setNombre(this.getNombre());

    }

    /**
     * metodo equals
     */
    @Override
    public boolean equals(Object mobjPrueba) {
        if (!(mobjPrueba instanceof Pais)) {
            return false;
        }
        Pais objBuffer = (Pais) mobjPrueba;

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

        if (this.getIdPais() != null) {
            ret += this.getIdPais().hashCode();
        }
        ret *= 29;

        return ret;
    }

    public Boolean getExcluirOrg() {
        return excluirOrg;
    }

    public void setExcluirOrg(Boolean excluirOrg) {
        this.excluirOrg = excluirOrg;
    }

    public Boolean getIncluirOrg() {
        return incluirOrg;
    }

    public void setIncluirOrg(Boolean incluirOrg) {
        this.incluirOrg = incluirOrg;
    }
}
