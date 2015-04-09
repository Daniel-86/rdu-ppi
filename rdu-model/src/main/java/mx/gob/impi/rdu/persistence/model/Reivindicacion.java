package mx.gob.impi.rdu.persistence.model;

import java.io.Serializable;

public class Reivindicacion implements Serializable {

    private Long idReivindicacion;
    private Long idTramite;
    private String descripcion;
    private Integer orden;
    private Integer idIdiomas;

    public Reivindicacion(Long idReivindicacion, Long idTramite, String descripcion, Integer orden, Integer idIdiomas) {
        this.idReivindicacion = idReivindicacion;
        this.idTramite = idTramite;
        this.descripcion = descripcion;
        this.orden = orden;
        this.idIdiomas = idIdiomas;
    }

    public Reivindicacion() {
    }

    public Long getIdReivindicacion() {
        return idReivindicacion;
    }

    public void setIdReivindicacion(Long idReivindicacion) {
        this.idReivindicacion = idReivindicacion;
    }

    public Long getIdTramite() {
        return idTramite;
    }

    public void setIdTramite(Long idTramite) {
        this.idTramite = idTramite;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion == null ? null : descripcion.trim();
    }

    public Integer getOrden() {
        return orden;
    }

    public void setOrden(Integer orden) {
        this.orden = orden;
    }

    public Integer getIdIdiomas() {
        return idIdiomas;
    }

    public void setIdIdiomas(Integer idIdiomas) {
        this.idIdiomas = idIdiomas;
    }
    
}
