/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package mx.gob.impi.rdu.util;

/**
 *
 * @author usradmin
 */
public enum TipoCriterioEnum {
    TODO(1,"Todo"),
    ULTIMA_SEMANA(2,"Ultima semana"),
    ULTIMO_MES(3,"Ultimo mes"),
    POR_RANGO(4,"Por Rango");

    private TipoCriterioEnum(int idTipoFecha, String descripcion) {
        this.idTipoFecha = idTipoFecha;
        this.descripcion = descripcion;
    }
    
    private int idTipoFecha;
    private String descripcion;

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
