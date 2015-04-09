package mx.gob.impi.rdu.dto;

/**
 *
 * @author JBMM
 */
public class ReporteDescripcionDto {

    private String descripcion;
    private String preambulo;
    private String descripcionVistas;
    private String textoAdicional;
    private String desReivindicaciones;
    private String tituloDescripcion;
    private Integer paginas;

    public ReporteDescripcionDto() {
    }
    
    public ReporteDescripcionDto(String descripcion, String desReivindicaciones) {
        this.descripcion = descripcion;
        this.desReivindicaciones = desReivindicaciones;
    }

    public ReporteDescripcionDto(String descripcion, String preambulo, String descripcionVistas, String textoAdicional
            , String desReivindicaciones, String tituloDescripcion) {
        this.descripcion = descripcion;
        this.preambulo = preambulo;
        this.descripcionVistas = descripcionVistas;
        this.textoAdicional = textoAdicional;
        this.desReivindicaciones = desReivindicaciones;
        this.tituloDescripcion = tituloDescripcion;
    }
    
    public ReporteDescripcionDto(String descripcion, String preambulo, String descripcionVistas, String textoAdicional
            , String desReivindicaciones, String tituloDescripcion, Integer nPaginas) {
        this.descripcion = descripcion;
        this.preambulo = preambulo;
        this.descripcionVistas = descripcionVistas;
        this.textoAdicional = textoAdicional;
        this.desReivindicaciones = desReivindicaciones;
        this.tituloDescripcion = tituloDescripcion;
        this.paginas = nPaginas;
        
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getPreambulo() {
        return preambulo;
    }

    public void setPreambulo(String preambulo) {
        this.preambulo = preambulo;
    }

    public String getDescripcionVistas() {
        return descripcionVistas;
    }

    public void setDescripcionVistas(String descripcionVistas) {
        this.descripcionVistas = descripcionVistas;
    }

    public String getTextoAdicional() {
        return textoAdicional;
    }

    public void setTextoAdicional(String textoAdicional) {
        this.textoAdicional = textoAdicional;
    }

    public String getDesReivindicaciones() {
        return desReivindicaciones;
    }

    public void setDesReivindicaciones(String desReivindicaciones) {
        this.desReivindicaciones = desReivindicaciones;
    }

    public String getTituloDescripcion() {
        return tituloDescripcion;
    }

    public void setTituloDescripcion(String tituloDescripcion) {
        this.tituloDescripcion = tituloDescripcion;
    }
    
    public Integer getPaginas() {
        return paginas;
    }

    public void setPaginas(Integer nPaginas) {
        this.paginas = nPaginas;
    }
 
    
}