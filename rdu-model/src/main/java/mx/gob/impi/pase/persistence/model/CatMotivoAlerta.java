package mx.gob.impi.pase.persistence.model;

import java.io.Serializable;

public class CatMotivoAlerta  implements Serializable{
    private Short idMotivoAlerta;

    private String descripcion;

    public Short getIdMotivoAlerta() {
        return idMotivoAlerta;
    }

    public void setIdMotivoAlerta(Short idMotivoAlerta) {
        this.idMotivoAlerta = idMotivoAlerta;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion == null ? null : descripcion.trim();
    }
}
