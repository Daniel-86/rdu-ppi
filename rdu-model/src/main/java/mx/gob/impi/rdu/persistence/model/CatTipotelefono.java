package mx.gob.impi.rdu.persistence.model;

public class CatTipotelefono {
    private Short idTipotelefono;

    private String descripcion;

    private Short indActivo;

    public Short getIdTipotelefono() {
        return idTipotelefono;
    }

    public void setIdTipotelefono(Short idTipotelefono) {
        this.idTipotelefono = idTipotelefono;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion == null ? null : descripcion.trim();
    }

    public Short getIndActivo() {
        return indActivo;
    }

    public void setIndActivo(Short indActivo) {
        this.indActivo = indActivo;
    }
}