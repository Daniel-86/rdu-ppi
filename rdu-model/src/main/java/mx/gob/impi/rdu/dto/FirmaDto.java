/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.gob.impi.rdu.dto;

import java.io.Serializable;
import java.util.Date;
import mx.gob.impi.rdu.persistence.model.Pago;


/**
 *
 * @author
 */
public class FirmaDto  implements Serializable{
     private Long idFirma;

    private Long idTramite;

    private String folio;

    private Long expediente;

    private Date fechaPresentacion;

    private Long codigoBarras;

    private Date fechaRegistro;

    private byte[] anexoXml;

    private byte[] acusePdf;
    
    private TramiteDto tramite;
    
    private String firmaSolicitante;
    
    private String firmaImpi;

    private String cadenaImpi;
    
    private String cadenaSolicitante;
    
    private Long idFirmaAdmin;
    
    private Long idPatente;
    
    private Long idPromocion;
    
    private Long envio;
    
    private String certificadora;

    private String nombreFirmante;
    
    private String expedienteId;
    
    private String folioId;
    
    private Long pdfPaginas;
    
    private String claveExpediente;
    
    private PagoDto pago;
    
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

    public PagoDto getPago() {
        return pago;
    }

    public void setPago(PagoDto pago) {
        this.pago = pago;
    }
    public String getFolioId() {
        return folioId;
    }

    public void setFolioId(String folioId) {
        this.folioId = folioId;
    }   

    public String getExpedienteId() {
        return expedienteId;
    }

    public void setExpedienteId(String expedienteId) {
        this.expedienteId = expedienteId;
    }  

    public String getNombreFirmante() {
        return nombreFirmante;
    }

    public void setNombreFirmante(String nombreFirmante) {
        this.nombreFirmante = nombreFirmante;
    }    
    
    public Long getIdPromocion() {
        return idPromocion;
    }

    public void setIdPromocion(Long idPromocion) {
        this.idPromocion = idPromocion;
    }    

    public Long getEnvio() {
        return envio;
    }

    public void setEnvio(Long envio) {
        this.envio = envio;
    }
        
    public Long getIdPatente() {
        return idPatente;
    }

    public void setIdPatente(Long idPatente) {
        this.idPatente = idPatente;
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

    public void setFirmaImpi(String firmaImpi) {
        this.firmaImpi = firmaImpi;
    }

    public void setFirmaSolicitante(String firmaSolicitante) {
        this.firmaSolicitante = firmaSolicitante;
    }

    public String getFirmaSolicitante() {
        return firmaSolicitante;
    }
        
    public byte[] getAcusePdf() {
        return acusePdf;
    }

    public byte[] getAnexoXml() {
        return anexoXml;
    }

    public Long getCodigoBarras() {
        return codigoBarras;
    }

    public Long getExpediente() {
        return expediente;
    }

    public Date getFechaPresentacion() {
        return fechaPresentacion;
    }

    public Date getFechaRegistro() {
        return fechaRegistro;
    }

    public String getFolio() {
        return folio;
    }

    public Long getIdFirma() {
        return idFirma;
    }

    public Long getIdTramite() {
        return idTramite;
    }

    public TramiteDto getTramite() {
        return tramite;
    }

    public void setAcusePdf(byte[] acusePdf) {
        this.acusePdf = acusePdf;
    }

    public void setAnexoXml(byte[] anexoXml) {
        this.anexoXml = anexoXml;
    }

    public void setCodigoBarras(Long codigoBarras) {
        this.codigoBarras = codigoBarras;
    }

    public void setExpediente(Long expediente) {
        this.expediente = expediente;
    }

    public void setFechaPresentacion(Date fechaPresentacion) {
        this.fechaPresentacion = fechaPresentacion;
    }

    public void setFechaRegistro(Date fechaRegistro) {
        this.fechaRegistro = fechaRegistro;
    }

    public void setFolio(String folio) {
        this.folio = folio;
    }

    public void setIdFirma(Long idFirma) {
        this.idFirma = idFirma;
    }

    public void setIdTramite(Long idTramite) {
        this.idTramite = idTramite;
    }

    public void setTramite(TramiteDto tramite) {
        this.tramite = tramite;
    }

    public void setIdFirmaAdmin(Long idFirmaAdmin) {
        this.idFirmaAdmin = idFirmaAdmin;
    }

    public Long getIdFirmaAdmin() {
        return idFirmaAdmin;
    }

    public String getCertificadora() {
        return certificadora;
    }

    public void setCertificadora(String certificadora) {
        this.certificadora = certificadora;
    }    

}
