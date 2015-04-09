package mx.gob.impi.rdu.persistence.model;

import java.io.Serializable;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlTransient;

@SuppressWarnings("serial")
@XmlAccessorType(XmlAccessType.FIELD)
public class CatSubtiposolicitud implements Serializable {

    public CatSubtiposolicitud() {
    }

    public CatSubtiposolicitud(Short indActivo, Long idTiposolicitud) {
        this.indActivo = indActivo;
        this.idTiposolicitud = idTiposolicitud;
    }
    @XmlElement(name = "idTipoSolicitud")
    private Long idSubtiposolicitud;
    private String descripcion;
    @XmlTransient
    private Long idTiposolicitud;
    private CatTiposolicitud tipoSolicitud;
    @XmlTransient
    private Short indActivo;

    public Long getIdSubtiposolicitud() {
        return idSubtiposolicitud;
    }

    public void setIdSubtiposolicitud(Long idSubtiposolicitud) {
        this.idSubtiposolicitud = idSubtiposolicitud;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion == null ? null : descripcion.trim();
    }

    public Long getIdTiposolicitud() {
        return idTiposolicitud;
    }

    public void setIdTiposolicitud(Long idTiposolicitud) {
        this.idTiposolicitud = idTiposolicitud;
    }

    public CatTiposolicitud getTipoSolicitud() {
        return tipoSolicitud;
    }

    public void setTipoSolicitud(CatTiposolicitud tipoSolicitud) {
        this.tipoSolicitud = tipoSolicitud;
    }

    public Short getIndActivo() {
        return indActivo;
    }

    public void setIndActivo(Short indActivo) {
        this.indActivo = indActivo;
    }
}