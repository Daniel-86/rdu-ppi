package mx.gob.impi.rdu.persistence.model;

public class TipoDocumento {
    private Integer idArea;

    private Integer idTipoDocumento;

    private String sentido;

    private String descripcion;

    private String descripcionCorta;

    private String idTipoDocumentoSagpat;

    private Short indActivo;

    private Short indSolicitud;

    private Short indPromocion;

    private Short indPermiteCapturaIncompleta;

    private Short indReqPago;

    private Short indSeleccMultip;

    private Short indRespuesta;

    private Short indForzarOficio;

    private Short indBusqueda;

    private Short indPromGenerica;

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

    public String getSentido() {
        return sentido;
    }

    public void setSentido(String sentido) {
        this.sentido = sentido == null ? null : sentido.trim();
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion == null ? null : descripcion.trim();
    }

    public String getDescripcionCorta() {
        return descripcionCorta;
    }

    public void setDescripcionCorta(String descripcionCorta) {
        this.descripcionCorta = descripcionCorta == null ? null : descripcionCorta.trim();
    }

    public String getIdTipoDocumentoSagpat() {
        return idTipoDocumentoSagpat;
    }

    public void setIdTipoDocumentoSagpat(String idTipoDocumentoSagpat) {
        this.idTipoDocumentoSagpat = idTipoDocumentoSagpat == null ? null : idTipoDocumentoSagpat.trim();
    }

    public Short getIndActivo() {
        return indActivo;
    }

    public void setIndActivo(Short indActivo) {
        this.indActivo = indActivo;
    }

    public Short getIndSolicitud() {
        return indSolicitud;
    }

    public void setIndSolicitud(Short indSolicitud) {
        this.indSolicitud = indSolicitud;
    }

    public Short getIndPromocion() {
        return indPromocion;
    }

    public void setIndPromocion(Short indPromocion) {
        this.indPromocion = indPromocion;
    }

    public Short getIndPermiteCapturaIncompleta() {
        return indPermiteCapturaIncompleta;
    }

    public void setIndPermiteCapturaIncompleta(Short indPermiteCapturaIncompleta) {
        this.indPermiteCapturaIncompleta = indPermiteCapturaIncompleta;
    }

    public Short getIndReqPago() {
        return indReqPago;
    }

    public void setIndReqPago(Short indReqPago) {
        this.indReqPago = indReqPago;
    }

    public Short getIndSeleccMultip() {
        return indSeleccMultip;
    }

    public void setIndSeleccMultip(Short indSeleccMultip) {
        this.indSeleccMultip = indSeleccMultip;
    }

    public Short getIndRespuesta() {
        return indRespuesta;
    }

    public void setIndRespuesta(Short indRespuesta) {
        this.indRespuesta = indRespuesta;
    }

    public Short getIndForzarOficio() {
        return indForzarOficio;
    }

    public void setIndForzarOficio(Short indForzarOficio) {
        this.indForzarOficio = indForzarOficio;
    }

    public Short getIndBusqueda() {
        return indBusqueda;
    }

    public void setIndBusqueda(Short indBusqueda) {
        this.indBusqueda = indBusqueda;
    }

    public Short getIndPromGenerica() {
        return indPromGenerica;
    }

    public void setIndPromGenerica(Short indPromGenerica) {
        this.indPromGenerica = indPromGenerica;
    }
}