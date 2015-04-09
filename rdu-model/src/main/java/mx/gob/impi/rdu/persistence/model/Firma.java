package mx.gob.impi.rdu.persistence.model;

import java.io.Serializable;
import java.util.Date;

public class Firma implements Serializable {

    private static final long serialVersionUID = 1L;
    private Long idFirma;
    private Long idTramite;
    private String folio;
    private Long expediente;
    private Date fechaPresentacion;
    private Long codigoBarras;
    private Date fechaRegistro;
    private byte[] anexoXml;
    private byte[] acusePdf;
    private Tramite tramite;
    private String firmaImpi;
    private String firmaSolicitante;
    private String cadenaImpi;
    private String cadenaSolicitante;
    private Long idFirmaAdmin;
    private Long envio;
    private Long idPatente;
    private Date fechaRevision;
    private Long pdfPaginas;
    private String claveExpediente;
    private Long idPromocion;
    private Integer descuento;
    
    
    public Integer getDescuento() {
        return descuento;
    }

    public void setDescuento(Integer descuento) {
        this.descuento = descuento;
    }   

    public String getClaveExpediente() {
        return claveExpediente;
    }

    public void setClaveExpediente(String claveExpediente) {
        this.claveExpediente = claveExpediente;
    }

    public Long getPdfPaginas() {
        return pdfPaginas;
    }

    public void setPdfPaginas(Long pdfPaginas) {
        this.pdfPaginas = pdfPaginas;
    }   
    
    public Long getIdPromocion() {
        return idPromocion;
    }

    public void setIdPromocion(Long idPromocion) {
        this.idPromocion = idPromocion;
    }   

    public Long getIdPatente() {
        return idPatente;
    }

    public void setIdPatente(Long idPatente) {
        this.idPatente = idPatente;
    }

    public Long getIdFirmaAdmin() {
        return idFirmaAdmin;
    }

    public void setIdFirmaAdmin(Long idFirmaAdmin) {
        this.idFirmaAdmin = idFirmaAdmin;
    }

    public String getCadenaImpi() {
        return cadenaImpi;
    }

    public String getCadenaSolicitante() {
        return cadenaSolicitante;
    }

    public void setCadenaImpi(String cadenaImpi) {
        this.cadenaImpi = cadenaImpi;
    }

    public void setCadenaSolicitante(String cadenaSolicitante) {
        this.cadenaSolicitante = cadenaSolicitante;
    }

    public String getFirmaImpi() {
        return firmaImpi;
    }

    public String getFirmaSolicitante() {
        return firmaSolicitante;
    }

    public void setFirmaImpi(String firmaImpi) {
        this.firmaImpi = firmaImpi;
    }

    public void setFirmaSolicitante(String firmaSolicitante) {
        this.firmaSolicitante = firmaSolicitante;
    }

    public Tramite getTramite() {
        return tramite;
    }

    public void setTramite(Tramite tramite) {
        this.tramite = tramite;
    }

    public Long getIdFirma() {
        return idFirma;
    }

    public void setIdFirma(Long idFirma) {
        this.idFirma = idFirma;
    }

    public Long getIdTramite() {
        return idTramite;
    }

    public void setIdTramite(Long idTramite) {
        this.idTramite = idTramite;
    }

    public String getFolio() {
        return folio;
    }

    public void setFolio(String folio) {
        this.folio = folio;
    }

    public Long getExpediente() {
        return expediente;
    }

    public void setExpediente(Long expediente) {
        this.expediente = expediente;
    }

    public Date getFechaPresentacion() {
        return fechaPresentacion;
    }

    public void setFechaPresentacion(Date fechaPresentacion) {
        this.fechaPresentacion = fechaPresentacion;
    }

    public Long getCodigoBarras() {
        return codigoBarras;
    }

    public void setCodigoBarras(Long codigoBarras) {
        this.codigoBarras = codigoBarras;
    }

    public Date getFechaRegistro() {
        return fechaRegistro;
    }

    public void setFechaRegistro(Date fechaRegistro) {
        this.fechaRegistro = fechaRegistro;
    }

    public byte[] getAnexoXml() {
        return anexoXml;
    }

    public void setAnexoXml(byte[] anexoXml) {
        this.anexoXml = anexoXml;
    }

    public byte[] getAcusePdf() {
        return acusePdf;
    }

    public void setAcusePdf(byte[] acusePdf) {
        this.acusePdf = acusePdf;
    }

    public Long getEnvio() {
        return envio;
    }

    public void setEnvio(Long envio) {
        this.envio = envio;
    }

    public Date getFechaRevision() {
        return fechaRevision;
    }

    public void setFechaRevision(Date fechaRevision) {
        this.fechaRevision = fechaRevision;
    }
}
