package mx.gob.impi.rdu.persistence.model;

import java.io.Serializable;

public class Folio  implements Serializable{
    private Long idFolio;

    private Long idArea;

    private String tipoFolio;

    private String figuraClasificacion;

    private Long folio;

    public Folio(Long idFolio) {
        this.idFolio = idFolio;
    }

    public Folio() {
    }
       
    public Long getIdFolio() {
        return idFolio;
    }

    public void setIdFolio(Long idFolio) {
        this.idFolio = idFolio;
    }

    public Long getIdArea() {
        return idArea;
    }

    public void setIdArea(Long idArea) {
        this.idArea = idArea;
    }

    public String getTipoFolio() {
        return tipoFolio;
    }

    public void setTipoFolio(String tipoFolio) {
        this.tipoFolio = tipoFolio == null ? null : tipoFolio.trim();
    }

    public String getFiguraClasificacion() {
        return figuraClasificacion;
    }

    public void setFiguraClasificacion(String figuraClasificacion) {
        this.figuraClasificacion = figuraClasificacion == null ? null : figuraClasificacion.trim();
    }

    public Long getFolio() {
        return folio;
    }

    public void setFolio(Long folio) {
        this.folio = folio;
    }
}