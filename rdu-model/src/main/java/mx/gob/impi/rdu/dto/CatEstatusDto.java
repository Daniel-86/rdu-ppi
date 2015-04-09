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
public class CatEstatusDto  implements Serializable{
    
    private Integer idEstatus;

    private String descripcion;

    private Integer indActivo;

    private String sigaccion;

    private String urlsigaccion;

    public String getDescripcion() {
        return descripcion;
    }

    public Integer getIdEstatus() {
        return idEstatus;
    }

    public Integer getIndActivo() {
        return indActivo;
    }

    public String getSigaccion() {
        return sigaccion;
    }

    public String getUrlsigaccion() {
        return urlsigaccion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public void setIdEstatus(Integer idEstatus) {
        this.idEstatus = idEstatus;
    }

    public void setIndActivo(Integer indActivo) {
        this.indActivo = indActivo;
    }

    public void setSigaccion(String sigaccion) {
        this.sigaccion = sigaccion;
    }

    public void setUrlsigaccion(String urlsigaccion) {
        this.urlsigaccion = urlsigaccion;
    }

    
    
}
