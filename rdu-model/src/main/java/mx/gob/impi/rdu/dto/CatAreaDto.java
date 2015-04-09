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
public class CatAreaDto  implements Serializable{
    private Integer idArea;

    private String descripcion;

    private Integer indActivo;

    public String getDescripcion() {
        return descripcion;
    }

    public Integer getIdArea() {
        return idArea;
    }

    public Integer getIndActivo() {
        return indActivo;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public void setIdArea(Integer idArea) {
        this.idArea = idArea;
    }

    public void setIndActivo(Integer indActivo) {
        this.indActivo = indActivo;
    }
    
    
    
}
