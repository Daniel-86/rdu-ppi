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
public class TipoTramiteDto implements Serializable{
    private int idTipoTramite;
    private String descripcion;

    public TipoTramiteDto(int idTipoTramite, String descripcion) {
        this.idTipoTramite = idTipoTramite;
        this.descripcion = descripcion;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public int getIdTipoTramite() {
        return idTipoTramite;
    }

    public void setIdTipoTramite(int idTipoTramite) {
        this.idTipoTramite = idTipoTramite;
    }


}
