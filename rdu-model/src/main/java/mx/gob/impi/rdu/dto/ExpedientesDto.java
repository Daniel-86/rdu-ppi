/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.gob.impi.rdu.dto;

import java.io.Serializable;
import java.util.Date;

/**
 *
 * @author usradmin
 */
public class ExpedientesDto implements Serializable {

    private Long idFirma;
    private Long idTramite;
    private String folio;
    private String expediente;
    private Date fechaPresentacion;
    private Date fechaRegistro;
    private byte[] anexoXml;
    private byte[] acusePdf;
    private String subTipoSolicitud;
    private Long idSubtiposolicitud; //ucm - extraer acuse
    private String tipoSolicitud;
    private String area;
    private byte[] acuseLogo;
    private Long idTipomarca;
    private Date fechaRevision;
    private Integer existeAcusePdf;
    private Integer existeAcuseLogo;
    private Integer envioSIGMAR;
    private Date fechaEnvioSIGMAR;
    private Integer envioUCM;
    private Date fechaEnvioUCM;
            

    public Long getIdSubtiposolicitud() {
        return idSubtiposolicitud;
    }

    public void setIdSubtiposolicitud(Long idSubtiposolicitud) {
        this.idSubtiposolicitud = idSubtiposolicitud;
    }

    public byte[] getAcusePdf() {
        return acusePdf;
    }

    public void setAcusePdf(byte[] acusePdf) {
        this.acusePdf = acusePdf;
    }

    public byte[] getAnexoXml() {
        return anexoXml;
    }

    public void setAnexoXml(byte[] anexoXml) {
        this.anexoXml = anexoXml;
    }

    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area;
    }

    public String getExpediente() {
        return expediente;
    }

    public void setExpediente(String expediente) {
        this.expediente = expediente;
    }

    public Date getFechaPresentacion() {
        return fechaPresentacion;
    }

    public void setFechaPresentacion(Date fechaPresentacion) {
        this.fechaPresentacion = fechaPresentacion;
    }

    public Date getFechaRegistro() {
        return fechaRegistro;
    }

    public void setFechaRegistro(Date fechaRegistro) {
        this.fechaRegistro = fechaRegistro;
    }

    public String getFolio() {
        return folio;
    }

    public void setFolio(String folio) {
        this.folio = folio;
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

    public String getSubTipoSolicitud() {
        return subTipoSolicitud;
    }

    public void setSubTipoSolicitud(String subTipoSolicitud) {
        this.subTipoSolicitud = subTipoSolicitud;
    }

    public String getTipoSolicitud() {
        return tipoSolicitud;
    }

    public void setTipoSolicitud(String tipoSolicitud) {
        this.tipoSolicitud = tipoSolicitud;
    }

    public byte[] getAcuseLogo() {
        return acuseLogo;
    }

    public void setAcuseLogo(byte[] acuseLogo) {
        this.acuseLogo = acuseLogo;
    }

    public Long getIdTipomarca() {
        return idTipomarca;
    }

    public void setIdTipomarca(Long idTipomarca) {
        this.idTipomarca = idTipomarca;
    }

    public Date getFechaRevision() {
        return fechaRevision;
    }

    public void setFechaRevision(Date fechaRevision) {
        this.fechaRevision = fechaRevision;
    }

    public Boolean getTieneAcusePdf() {
        if(acusePdf != null){
            return true;
        }
        
        if(existeAcusePdf != null){
            return existeAcusePdf.intValue() == 1;            
        }return false;
    }
    
    public Boolean getTieneAcuseLogo() {
        
        if(acuseLogo != null){
            return true;
        }
        
        if(existeAcuseLogo != null){
            return existeAcuseLogo.intValue() == 1;            
        }return false;
    }

    public void setExisteAcusePdf(Integer existeAcusePdf) {
        this.existeAcusePdf = existeAcusePdf;
    }

    public Integer getExisteAcusePdf() {
        return existeAcusePdf;
    }

    public Integer getExisteAcuseLogo() {
        return existeAcuseLogo;
    }

    public void setExisteAcuseLogo(Integer existeAcuseLogo) {
        this.existeAcuseLogo = existeAcuseLogo;
    }

    
    public Integer getEnvioUCM() {
        return this.envioUCM;          
        
    }

    public void setEnvioUCM(Integer envioUCM) {
        this.envioUCM = envioUCM;
    }

    public Date getFechaEnvioUCM() {
        return fechaEnvioUCM;
    }

    public void setFechaEnvioUCM(Date fechaEnvioUCM) {
        this.fechaEnvioUCM = fechaEnvioUCM;
    }
    
    public Integer getEnvioSIGMAR() {
        return envioSIGMAR;
    }

    public void setEnvioSIGMAR(Integer envioSIGMAR) {
        this.envioSIGMAR = envioSIGMAR;
    }

    public Date getFechaEnvioSIGMAR() {
        return fechaEnvioSIGMAR;
    }

    public void setFechaEnvioSIGMAR(Date fechaEnvioSIGMAR) {
        this.fechaEnvioSIGMAR = fechaEnvioSIGMAR;
    }
}
