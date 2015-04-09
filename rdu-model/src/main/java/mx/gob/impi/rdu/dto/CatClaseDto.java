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
public class CatClaseDto  implements Serializable{
    
    private Long idclase;

    private String descripcion;

    private String tipoClase;

    public String getDescripcion() {
        return descripcion;
    }

    public Long getIdclase() {
        return idclase;
    }

    public String getTipoClase() {
        return tipoClase;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public void setIdclase(Long idclase) {
        this.idclase = idclase;
    }

    public void setTipoClase(String tipoClase) {
        this.tipoClase = tipoClase;
    }
    
    
    
    
}
