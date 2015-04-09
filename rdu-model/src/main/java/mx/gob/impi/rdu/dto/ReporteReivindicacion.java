/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.gob.impi.rdu.dto;

/**
 *
 * @author cesar
 */
public class ReporteReivindicacion {

    private String descripcion;
    private String expedienteDivisional;
    private Integer paginas;
    public ReporteReivindicacion() {
    }
    
    public ReporteReivindicacion(String descripcion , String expedienteDivisional)
    {
        this.descripcion = descripcion;
        this.expedienteDivisional = expedienteDivisional;
    }

    public ReporteReivindicacion(String descripcion) {
        this.descripcion = descripcion;
    }
    
    public ReporteReivindicacion(String descripcion, Integer paginas) {
        this.descripcion = descripcion;
        this.paginas= paginas;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getExpedienteDivisional() {
        return expedienteDivisional;
    }

    public void setExpedienteDivisional(String expedienteDivisional) {
        this.expedienteDivisional = expedienteDivisional;
    }
    public Integer getPaginas() {
        return paginas;
    }

    public void setPaginas(Integer paginas) {
        this.paginas = paginas;
    }
    
}
