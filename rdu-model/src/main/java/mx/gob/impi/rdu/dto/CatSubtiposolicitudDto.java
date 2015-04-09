/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.gob.impi.rdu.dto;

import java.io.Serializable;
import mx.gob.impi.rdu.persistence.model.CatTiposolicitud;

/**
 *
 * @author
 */
public class CatSubtiposolicitudDto  implements Serializable {
    
    private Long idSubtiposolicitud;

    private String descripcion;

    private Long idTiposolicitud;

    private CatTiposolicitud tipoSolicitud;

    private Short indActivo;

    public String getDescripcion() {
        return descripcion;
    }

    public Long getIdSubtiposolicitud() {
        return idSubtiposolicitud;
    }

    public Long getIdTiposolicitud() {
        return idTiposolicitud;
    }

    public Short getIndActivo() {
        return indActivo;
    }

    public CatTiposolicitud getTipoSolicitud() {
        return tipoSolicitud;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public void setIdSubtiposolicitud(Long idSubtiposolicitud) {
        this.idSubtiposolicitud = idSubtiposolicitud;
    }

    public void setIdTiposolicitud(Long idTiposolicitud) {
        this.idTiposolicitud = idTiposolicitud;
    }

    public void setIndActivo(Short indActivo) {
        this.indActivo = indActivo;
    }

    public void setTipoSolicitud(CatTiposolicitud tipoSolicitud) {
        this.tipoSolicitud = tipoSolicitud;
    }
    
    
    
}
