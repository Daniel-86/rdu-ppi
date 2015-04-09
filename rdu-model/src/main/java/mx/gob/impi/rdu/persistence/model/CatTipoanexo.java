package mx.gob.impi.rdu.persistence.model;

@SuppressWarnings("serial")
public class CatTipoanexo implements java.io.Serializable{
    private Integer idTipoanexo;

    private String descripcion;

    private Short obligatorio;

    private Short indActivo;

    private Integer idSubtiposolicitud;

    private Short visiblexcarga;

    private Short orden;

    private Integer idArea;

    public Integer getIdTipoanexo() {
        return idTipoanexo;
    }

    public void setIdTipoanexo(Integer idTipoanexo) {
        this.idTipoanexo = idTipoanexo;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion == null ? null : descripcion.trim();
    }

    public Short getObligatorio() {
        return obligatorio;
    }

    public void setObligatorio(Short obligatorio) {
        this.obligatorio = obligatorio;
    }

    public Short getIndActivo() {
        return indActivo;
    }

    public void setIndActivo(Short indActivo) {
        this.indActivo = indActivo;
    }

    public Integer getIdSubtiposolicitud() {
        return idSubtiposolicitud;
    }

    public void setIdSubtiposolicitud(Integer idSubtiposolicitud) {
        this.idSubtiposolicitud = idSubtiposolicitud;
    }

    public Short getVisiblexcarga() {
        return visiblexcarga;
    }

    public void setVisiblexcarga(Short visiblexcarga) {
        this.visiblexcarga = visiblexcarga;
    }

    public Integer getIdArea() {
        return idArea;
    }

    public void setIdArea(Integer idArea) {
        this.idArea = idArea;
    }

    public Short getOrden() {
        return orden;
    }

    public void setOrden(Short orden) {
        this.orden = orden;
    }

    
}