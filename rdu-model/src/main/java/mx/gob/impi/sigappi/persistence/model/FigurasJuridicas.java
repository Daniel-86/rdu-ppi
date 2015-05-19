package mx.gob.impi.sigappi.persistence.model;

import mx.gob.impi.sigappi.persistence.model.*;
import java.io.Serializable;
import java.util.Date;

public class FigurasJuridicas implements Serializable {
private Integer numFigura;
private String cveFigura;
private String descripcion;
private Integer cveDireccion;
private String registroLey;

    public Integer getNumFigura() {
        return numFigura;
    }

    public void setNumFigura(Integer numFigura) {
        this.numFigura = numFigura;
    }

    public String getCveFigura() {
        return cveFigura;
    }

    public void setCveFigura(String cveFigura) {
        this.cveFigura = cveFigura;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public Integer getCveDireccion() {
        return cveDireccion;
    }

    public void setCveDireccion(Integer cveDireccion) {
        this.cveDireccion = cveDireccion;
    }

    public String getRegistroLey() {
        return registroLey;
    }

    public void setRegistroLey(String registroLey) {
        this.registroLey = registroLey;
    }


}
