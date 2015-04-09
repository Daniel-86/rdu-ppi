/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.gob.impi.rdu.util;

/**
 *
 * @author cesar
 */
public enum FiltroExtraEnum {

    TODOS(1L, "Todos"),
    ULTIMA_SEMANA(2L, "Ultima Semana"),
    ULTIMO_MES(3L, "Ultimo Mes"),
    RANGO(4L, "Por Rango");

    private FiltroExtraEnum(Long idFiltroExtra, String descripcion) {
        this.idFiltroExtra = idFiltroExtra;
        this.descripcion = descripcion;
    }
    private Long idFiltroExtra;
    private String descripcion;

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public Long getIdFiltroExtra() {
        return idFiltroExtra;
    }

    public void setIdFiltroExtra(Long idFiltroExtra) {
        this.idFiltroExtra = idFiltroExtra;
    }
}
