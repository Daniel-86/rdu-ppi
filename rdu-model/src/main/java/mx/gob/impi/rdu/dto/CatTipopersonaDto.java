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
public class CatTipopersonaDto  implements Serializable{
  
     private Short idTipopersona;

    private String descripcion;

    private Short indActivo;

    public String getDescripcion() {
        return descripcion;
    }

    public Short getIdTipopersona() {
        return idTipopersona;
    }

    public Short getIndActivo() {
        return indActivo;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public void setIdTipopersona(Short idTipopersona) {
        this.idTipopersona = idTipopersona;
    }

    public void setIndActivo(Short indActivo) {
        this.indActivo = indActivo;
    }

    
    
    
}
