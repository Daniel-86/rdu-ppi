package mx.gob.impi.sigappi.persistence.model;

import mx.gob.impi.sigappi.persistence.model.*;
import java.io.Serializable;
import java.util.Date;

public class TipoAnexo implements Serializable {

    
    private Integer categoria;
private Integer division;
private Integer seccion;
private String descripcion;

    public Integer getCategoria() {
        return categoria;
    }

    public void setCategoria(Integer categoria) {
        this.categoria = categoria;
    }

    public Integer getDivision() {
        return division;
    }

    public void setDivision(Integer division) {
        this.division = division;
    }

    public Integer getSeccion() {
        return seccion;
    }

    public void setSeccion(Integer seccion) {
        this.seccion = seccion;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }


}
