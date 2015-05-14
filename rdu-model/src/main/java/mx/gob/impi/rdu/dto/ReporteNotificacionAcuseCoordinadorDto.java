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
}
