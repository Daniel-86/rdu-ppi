package mx.gob.impi.sigappi.persistence.model;

import mx.gob.impi.sigappi.persistence.model.*;
import java.io.Serializable;
import java.util.Date;

public class Area implements Serializable {

    private Integer cveArea;
    private String descripcion;
    private String descrip;
    private Integer cveAreaPadre;

    public Integer getCveArea() {
        return cveArea;
    }

    public void setCveArea(Integer cveArea) {
        this.cveArea = cveArea;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getDescrip() {
        return descrip;
    }

    public void setDescrip(String descrip) {
        this.descrip = descrip;
    }

    public Integer getCveAreaPadre() {
        return cveAreaPadre;
    }

    public void setCveAreaPadre(Integer cveAreaPadre) {
        this.cveAreaPadre = cveAreaPadre;
    }


}
