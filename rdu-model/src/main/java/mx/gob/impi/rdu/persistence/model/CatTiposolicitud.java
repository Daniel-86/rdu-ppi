package mx.gob.impi.rdu.persistence.model;

import java.io.Serializable;

public class CatTiposolicitud  implements Serializable{
    
   private Long idTiposolicitud;

    private String descripcion;

    private Short indActivo;

    private Integer idArea;

    private CatArea area;

    private String urlsigaccion;

    public Long getIdTiposolicitud() {
        return idTiposolicitud;
    }

    public void setIdTiposolicitud(Long idTiposolicitud) {
        this.idTiposolicitud = idTiposolicitud;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion == null ? null : descripcion.trim();
    }

    public Short getIndActivo() {
        return indActivo;
    }

    public void setIndActivo(Short indActivo) {
        this.indActivo = indActivo;
    }

    public Integer getIdArea() {
        return idArea;
    }

    public void setIdArea(Integer idArea) {
        this.idArea = idArea;
    }

    public CatArea getArea() {
        return area;
    }

    public void setArea(CatArea area) {
        this.area = area;
    }

    public String getUrlsigaccion() {
        return urlsigaccion;
    }

    public void setUrlsigaccion(String urlsigaccion) {
        this.urlsigaccion = urlsigaccion;
    }
    
}