package mx.gob.impi.rdu.persistence.model;

import java.io.Serializable;

public class DocumentoArticulo implements Serializable{
    private String articuloPago;

    private Integer idArea;

    private Integer idTipoDocumento;

    private Integer idTipoSolicitud;

    private String inciso;

    private String sentido;

    private Short cantidad;

    private Short indPromocion;

    public String getArticuloPago() {
        return articuloPago;
    }

    public void setArticuloPago(String articuloPago) {
        this.articuloPago = articuloPago == null ? null : articuloPago.trim();
    }

    public Integer getIdArea() {
        return idArea;
    }

    public void setIdArea(Integer idArea) {
        this.idArea = idArea;
    }

    public Integer getIdTipoDocumento() {
        return idTipoDocumento;
    }

    public void setIdTipoDocumento(Integer idTipoDocumento) {
        this.idTipoDocumento = idTipoDocumento;
    }

    public Integer getIdTipoSolicitud() {
        return idTipoSolicitud;
    }

    public void setIdTipoSolicitud(Integer idTipoSolicitud) {
        this.idTipoSolicitud = idTipoSolicitud;
    }

    public String getInciso() {
        return inciso;
    }

    public void setInciso(String inciso) {
        this.inciso = inciso == null ? null : inciso.trim();
    }

    public String getSentido() {
        return sentido;
    }

    public void setSentido(String sentido) {
        this.sentido = sentido == null ? null : sentido.trim();
    }

    public Short getCantidad() {
        return cantidad;
    }

    public void setCantidad(Short cantidad) {
        this.cantidad = cantidad;
    }

    public Short getIndPromocion() {
        return indPromocion;
    }

    public void setIndPromocion(Short indPromocion) {
        this.indPromocion = indPromocion;
    }
}