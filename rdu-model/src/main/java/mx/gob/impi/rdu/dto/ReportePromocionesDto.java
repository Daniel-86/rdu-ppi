package mx.gob.impi.rdu.dto;

import java.util.Date;
import java.util.List;
import mx.gob.impi.rdu.persistence.model.TramitePatente;
import mx.gob.impi.rdu.persistence.model.TramitePromocionPatente;

/**
 *
 * @author oracle
 */
public class ReportePromocionesDto {
    private String descripcionOficio;
    private String area;
    private String tipoPromocion;
    private String numExpediente;
    private Date fecha;
    private String cadenaOriginal;
    private String folio;

    public ReportePromocionesDto() {
    }
    
    public ReportePromocionesDto(String descripcionOficio, String area, String tipoPromocion) {
        this.descripcionOficio = descripcionOficio;
        this.area = area;
        this.tipoPromocion = tipoPromocion;
    }

    public ReportePromocionesDto(TramitePromocionPatente tramitePromocion, String rutaFirmaImpi, FirmaDto dto, List<ReportesDto> listaTmp) {
    }

    public String getDescripcionOficio() {
        return descripcionOficio;
    }

    public void setDescripcionOficio(String descripcionOficio) {
        this.descripcionOficio = descripcionOficio;
    }

    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area;
    }

    public String getTipoPromocion() {
        return tipoPromocion;
    }

    public void setTipoPromocion(String tipoPromocion) {
        this.tipoPromocion = tipoPromocion;
    }

    public String getNumExpediente() {
        return numExpediente;
    }

    public void setNumExpediente(String numExpediente) {
        this.numExpediente = numExpediente;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public String getCadenaOriginal() {
        return cadenaOriginal;
    }

    public void setCadenaOriginal(String cadenaOriginal) {
        this.cadenaOriginal = cadenaOriginal;
    }

    public String getFolio() {
        return folio;
    }

    public void setFolio(String folio) {
        this.folio = folio;
    }
    
    
}