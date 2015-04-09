package mx.gob.impi.rdu.dto;

import java.io.Serializable;
import mx.gob.impi.rdu.persistence.model.CatArea;

/**
 *
 * @author Juan Bernardo Moreno Márquez
 */
@SuppressWarnings("serial")
public class tipoSolicitudDto implements Serializable {
    private Long idSubtiposolicitud;
    private String descripcion;
    private Long idTiposolicitud;;
    private Long idArea;

    public Long getIdSubtiposolicitud() {
        return idSubtiposolicitud;
    }

    public void setIdSubtiposolicitud(Long idSubtiposolicitud) {
        this.idSubtiposolicitud = idSubtiposolicitud;
    }
   
    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public Long getIdTiposolicitud() {
        return idTiposolicitud;
    }

    public void setIdTiposolicitud(Long idTiposolicitud) {
        this.idTiposolicitud = idTiposolicitud;
    }

    public Long getIdArea() {
        return idArea;
    }

    public void setIdArea(Long idArea) {
        this.idArea = idArea;
    }

}