package mx.gob.impi.rdu.persistence.model;

import java.io.Serializable;

public class CatArea  implements Serializable{
    private Integer idArea;

    private String descripcion;

    private Integer indActivo;

    public Integer getIdArea() {
        return idArea;
    }

    public void setIdArea(Integer idArea) {
        this.idArea = idArea;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion == null ? null : descripcion.trim();
    }

    public Integer getIndActivo() {
        return indActivo;
    }

    public void setIndActivo(Integer indActivo) {
        this.indActivo = indActivo;
    }
}