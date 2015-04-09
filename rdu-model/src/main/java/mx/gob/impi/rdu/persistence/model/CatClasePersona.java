package mx.gob.impi.rdu.persistence.model;

import java.io.Serializable;

/**
 *
 * @author JBMM
 */
public class CatClasePersona implements Serializable{
    private Short idClasePersona;
    private String descripcion;

    public Short getIdClasePersona() {
        return idClasePersona;
    }

    public void setIdClasePersona(Short idClasePersona) {
        this.idClasePersona = idClasePersona;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion == null ? null : descripcion.trim();
    }
}