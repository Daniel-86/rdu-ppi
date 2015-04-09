package mx.gob.impi.rdu.persistence.model;

import java.util.Date;
import java.io.Serializable;
/**
 *
 * @author JBMM
 */
@SuppressWarnings("serial")
public class BitacoraErrores implements Serializable{
    private Short idError;
    private String descripcion;
    private Date fechaError;
    
    public Short getIdError() {
        return idError;
    }

    public void setIdError(Short idError) {
        this.idError = idError;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion == null ? null : descripcion.trim();
    }

    public Date getFechaError() {
        return fechaError;
    }

    public void setFechaError(Date fechaError) {
        this.fechaError = fechaError;
    }
}