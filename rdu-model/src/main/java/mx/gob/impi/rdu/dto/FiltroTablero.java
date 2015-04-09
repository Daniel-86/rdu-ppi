/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.gob.impi.rdu.dto;

/**
 *
 * @author cesar
 */
public class FiltroTablero {

    public FiltroTablero() {
    }

    public FiltroTablero(Long idFiltro, String descripcion) {
        this.idFiltro = idFiltro;
        this.descripcion = descripcion;
    }
    private Long idFiltro;
    private String descripcion;

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public Long getIdFiltro() {
        return idFiltro;
    }

    public void setIdFiltro(Long idFiltro) {
        this.idFiltro = idFiltro;
    }
}
