package mx.gob.impi.rdu.dto;

import mx.gob.impi.rdu.persistence.model.*;
import java.io.Serializable;

@SuppressWarnings("serial")
public class CatTipomarcaDto implements Serializable {
    private Long idTipomarca;

    private String descripcion;

    private Short indActivo;

    public Long getIdTipomarca() {
        return idTipomarca;
    }

    public void setIdTipomarca(Long idTipomarca) {
        this.idTipomarca = idTipomarca;
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