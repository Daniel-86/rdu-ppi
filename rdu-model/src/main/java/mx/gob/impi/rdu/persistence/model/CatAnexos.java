package mx.gob.impi.rdu.persistence.model;

import java.io.Serializable;

public class CatAnexos implements Serializable{
    private Long idTipoanexo;

    private String descripcion;

    private String extension;

    private Short indActivo;

    public Long getIdTipoanexo() {
        return idTipoanexo;
    }

    public void setIdTipoanexo(Long idTipoanexo) {
        this.idTipoanexo = idTipoanexo;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion == null ? null : descripcion.trim();
    }

    public String getExtension() {
        return extension;
    }

    public void setExtension(String extension) {
        this.extension = extension == null ? null : extension.trim();
    }

    public Short getIndActivo() {
        return indActivo;
    }

    public void setIndActivo(Short indActivo) {
        this.indActivo = indActivo;
    }
}