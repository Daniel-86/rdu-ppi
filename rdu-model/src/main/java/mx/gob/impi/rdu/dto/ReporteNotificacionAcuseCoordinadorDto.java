/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.gob.impi.rdu.dto;

import mx.gob.impi.rdu.persistence.model.Notificacion;
import mx.gob.impi.rdu.util.Util;

/**
 *
 * @author
 */
public class ReporteNotificacionAcuseCoordinadorDto {

    private String expediente;
    private String folio;
    private String fecha;
    private String rutaFirmaImpi;
    private String cadenaImpi;
    private String selloImpi;
    private String nombreArchivo;
    private FirmaDto firma;
    private Notificacion notificacion;
    private String codbarras;
    
    private String actor;
    private String demandado;
    private String derechoInvolucrado;
    private String PC;
    private String traslado;
    private String anexos;
    private String analista;
    private String firmante;
    private String usuario;
    private String area;
    
    
    

    public ReporteNotificacionAcuseCoordinadorDto() {
        super();
    }

    public ReporteNotificacionAcuseCoordinadorDto(Notificacion not, FirmaDto firm,String ruta ) {
        this.firma = firm;
        this.notificacion = not;
        this.rutaFirmaImpi=ruta;
        this.convertirTramite();
        
    }

    public void convertirTramite() {
        this.setExpediente(this.notificacion.getExpediente().toString());
        this.setFecha(Util.formatearFecha(firma.getFechaRegistro(), Util.FORMATODDMMYYYYHHMMSS));
        this.setCadenaImpi(firma.getCadenaImpi());
        this.setFolio(firma.getFolio());
        this.setNombreArchivo(this.notificacion.getArchivoNombre());
        this.setSelloImpi(firma.getFirmaImpi());
        this.setFirmante(firma.getNombreFirmante());
          
    
    }

    public FirmaDto getFirma() {
        return firma;
    }

    public void setFirma(FirmaDto firma) {
        this.firma = firma;
    }

    public Notificacion getNotificacion() {
        return notificacion;
    }

    public void setNotificacion(Notificacion notificacion) {
        this.notificacion = notificacion;
    }

    public String getCadenaImpi() {
        return cadenaImpi;
    }

    public void setCadenaImpi(String cadenaImpi) {
        this.cadenaImpi = cadenaImpi;
    }

    public String getExpediente() {
        return expediente;
    }

    public void setExpediente(String expediente) {
        this.expediente = expediente;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public String getFolio() {
        return folio;
    }

    public void setFolio(String folio) {
        this.folio = folio;
    }

    public String getNombreArchivo() {
        return nombreArchivo;
    }

    public void setNombreArchivo(String nombreArchivo) {
        this.nombreArchivo = nombreArchivo;
    }

    public String getRutaFirmaImpi() {
        return rutaFirmaImpi;
    }

    public void setRutaFirmaImpi(String rutaFirmaImpi) {
        this.rutaFirmaImpi = rutaFirmaImpi;
    }

    public String getSelloImpi() {
        return selloImpi;
    }

    public void setSelloImpi(String selloImpi) {
        this.selloImpi = selloImpi;
    }

    public String getActor() {
        return actor;
    }

    public void setActor(String actor) {
        this.actor = actor;
    }

    public String getDemandado() {
        return demandado;
    }

    public void setDemandado(String demandado) {
        this.demandado = demandado;
    }

    public String getDerechoInvolucrado() {
        return derechoInvolucrado;
    }

    public void setDerechoInvolucrado(String derechoInvolucrado) {
        this.derechoInvolucrado = derechoInvolucrado;
    }

    public String getPC() {
        return PC;
    }

    public void setPC(String PC) {
        this.PC = PC;
    }

    public String getTraslado() {
        return traslado;
    }

    public void setTraslado(String traslado) {
        this.traslado = traslado;
    }

    public String getAnexos() {
        return anexos;
    }

    public void setAnexos(String anexos) {
        this.anexos = anexos;
    }

    public String getAnalista() {
        return analista;
    }

    public void setAnalista(String analista) {
        this.analista = analista;
    }

    public String getFirmante() {
        return firmante;
    }

    public void setFirmante(String firmante) {
        this.firmante = firmante;
    }

    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area;
    }

    public String getCodbarras() {
        return codbarras;
    }

    public void setCodbarras(String codbarras) {
        this.codbarras = codbarras;
    }
    
    
}
