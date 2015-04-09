/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.gob.impi.rdu.dto;

/**
 *
 * @author cesar
 */
public class ReporteResumen {

    private String descripcion;
    private Integer paginas;
    public ReporteResumen() {
    }
    

    public ReporteResumen(String descripcion) {
        this.descripcion = descripcion;
    }
    
    public ReporteResumen(String descripcion, Integer paginas) {
        this.descripcion = descripcion;
        this.paginas= paginas;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public Integer getPaginas() {
        return paginas;
    }

    public void setPaginas(Integer paginas) {
        this.paginas = paginas;
    }
    
}
