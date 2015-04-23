package mx.gob.impi.sigappi.persistence.model;

import mx.gob.impi.sigappi.persistence.model.*;
import java.io.Serializable;
import java.util.Date;

public class TiposRelacion implements Serializable {

    private Integer codRelacion;
    private String descripcion;
    private Date fechaModificacion;
    private String parte;
    private Integer codRelacionRep;

    public Integer getCodRelacion() {
        return codRelacion;
    }

    public void setCodRelacion(Integer codRelacion) {
        this.codRelacion = codRelacion;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public Date getFechaModificacion() {
        return fechaModificacion;
    }

    public void setFechaModificacion(Date fechaModificacion) {
        this.fechaModificacion = fechaModificacion;
    }

    public String getParte() {
        return parte;
    }

    public void setParte(String parte) {
        this.parte = parte;
    }

    public Integer getCodRelacionRep() {
        return codRelacionRep;
    }

    public void setCodRelacionRep(Integer codRelacionRep) {
        this.codRelacionRep = codRelacionRep;
    }

}
