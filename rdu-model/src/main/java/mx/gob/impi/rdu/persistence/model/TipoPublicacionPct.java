/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.gob.impi.rdu.persistence.model;

/**
 *
 * @author oracle
 */
public class TipoPublicacionPct {

    private Integer idTipoPublicacionPct;
    private String descripcion;

    public TipoPublicacionPct() {
    }

    public TipoPublicacionPct(Integer idTipoPublicacionPct, String descripcion) {
        this.idTipoPublicacionPct = idTipoPublicacionPct;
        this.descripcion = descripcion;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public Integer getIdTipoPublicacionPct() {
        return idTipoPublicacionPct;
    }

    public void setIdTipoPublicacionPct(Integer idTipoPublicacionPct) {
        this.idTipoPublicacionPct = idTipoPublicacionPct;
    }
}
