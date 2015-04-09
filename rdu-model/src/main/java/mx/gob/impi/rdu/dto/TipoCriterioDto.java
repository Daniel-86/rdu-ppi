/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package mx.gob.impi.rdu.dto;
import java.io.Serializable;
/**
 *
 * @author usradmin
 */
@SuppressWarnings("serial")
public class TipoCriterioDto implements Serializable{
    private int idTipoFecha;
    private String descripcion;

    public TipoCriterioDto(int idTipoFecha, String descripcion) {
        this.idTipoFecha = idTipoFecha;
        this.descripcion = descripcion;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public int getIdTipoFecha() {
        return idTipoFecha;
    }

    public void setIdTipoFecha(int idTipoFecha) {
        this.idTipoFecha = idTipoFecha;
    }
 

}
