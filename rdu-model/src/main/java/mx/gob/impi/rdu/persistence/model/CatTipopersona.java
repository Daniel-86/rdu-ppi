package mx.gob.impi.rdu.persistence.model;

import java.io.Serializable;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlTransient;

@XmlAccessorType(XmlAccessType.FIELD)
public class CatTipopersona implements Serializable{
    private Short idTipopersona;

    private String descripcion;

    @XmlTransient
    private Short indActivo;

    public Short getIdTipopersona() {
        return idTipopersona;
    }

    public void setIdTipopersona(Short idTipopersona) {
        this.idTipopersona = idTipopersona;
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