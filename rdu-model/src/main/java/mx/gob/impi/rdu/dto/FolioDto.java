/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.gob.impi.rdu.dto;

import java.io.Serializable;

/**
 *
 * @author
 */
public class FolioDto  implements Serializable{
    
    private Long idFolio;

    private Long idArea;

    private String tipoFolio;

    private String figuraClasificacion;

    private Long folio;

      public FolioDto() {
    }

    
    public FolioDto(Long idFolio, String tipoFolio) {
        this.idFolio = idFolio;
        this.tipoFolio = tipoFolio;
    }

    public FolioDto(String tipoFolio) {
        this.tipoFolio = tipoFolio;
    }

  
    
    public String getFiguraClasificacion() {
        return figuraClasificacion;
    }

    public Long getFolio() {
        return folio;
    }

    public Long getIdArea() {
        return idArea;
    }

    public Long getIdFolio() {
        return idFolio;
    }

    public String getTipoFolio() {
        return tipoFolio;
    }

    public void setFiguraClasificacion(String figuraClasificacion) {
        this.figuraClasificacion = figuraClasificacion;
    }

    public void setFolio(Long folio) {
        this.folio = folio;
    }

    public void setIdArea(Long idArea) {
        this.idArea = idArea;
    }

    public void setIdFolio(Long idFolio) {
        this.idFolio = idFolio;
    }

    public void setTipoFolio(String tipoFolio) {
        this.tipoFolio = tipoFolio;
    }
    
    
    
}
