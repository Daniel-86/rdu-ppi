/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.gob.impi.rdu.dto;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import mx.gob.impi.rdu.persistence.model.Anexos;
import mx.gob.impi.rdu.persistence.model.Usuario;

/**
 *
 * @author usradmin
 */
@SuppressWarnings("serial")
public class SolicitudPreparacionDto implements Serializable {

    private Long idTramite;
    private Integer idArea;
    private String descripcionArea;
    private Long idSubtiposolicitud;
    private String descripcionSubtiposolicitud;
    private Long idTiposolicitud;
    private String descripcionTiposolicitud;
    private String url_TipoSolicitud;
    private Date fechacaptura;
    private Date fechaEstatusActual;
    private Long idUsuariocaptura;
    private Usuario usuarioCaptura;
    private Long idUsuariofirmante;
    private Usuario usuarioFirmante;
    private Long idEstatus;
    private String descripcionESTATUS;
    private String sigaccion;
    private String url_SigAccion_Estatus;
    private int idTipoTramite;
    private Long idTipomarca;
    private String descripcionsignodistintivo;
    private Short hayprioridad;
    private int totalSolicitantes;
    private Integer mostrarHerramientas;
    private String invencion;
    private String nombreCortoInvencion;
    // ------------ PROPIEDADES PROMOCIONES --------------------
    private Long idPromocion;
    private String oficioCodOficina;
    private String oficioFolio;
    private String oficioSerie;
    private String descOficio;
    private Long idTipoPromocion;
    private String descTipoPromocion;
    private String codOficinaExped;
    private String tipExped;
    private String serExped;
    private String numExped;
    private String numConcesion;
    private String descPromocion;
    private Integer plazoAdicional;
    private Short descuento;
    private Short mostrarDescuento;
    private Short mostrarPlazoAdicional;
    private Integer tipoSolicitante;
    private String areaPromocion;
    List<Anexos> anexos;

    public SolicitudPreparacionDto() {
    }

    public SolicitudPreparacionDto(Long idTramite, Integer idArea, Long idSubtiposolicitud, Long idTiposolicitud) {
        this.idTramite = idTramite;
        this.idArea = idArea;
        this.idSubtiposolicitud = idSubtiposolicitud;
        this.idTiposolicitud = idTiposolicitud;
    }

    public String getDescripcionArea() {
        return descripcionArea;
    }

    public void setDescripcionArea(String descripcionArea) {
        this.descripcionArea = descripcionArea;
    }

    public String getDescripcionESTATUS() {
        return descripcionESTATUS;
    }

    public void setDescripcionESTATUS(String descripcionESTATUS) {
        this.descripcionESTATUS = descripcionESTATUS;
    }

    public String getSigaccion() {
        return sigaccion;
    }

    public void setSigaccion(String sigaccion) {
        this.sigaccion = sigaccion;
    }

    public String getDescripcionSubtiposolicitud() {
        return descripcionSubtiposolicitud;
    }

    public void setDescripcionSubtiposolicitud(String descripcionSubtiposolicitud) {
        this.descripcionSubtiposolicitud = descripcionSubtiposolicitud;
    }

    public String getDescripcionTiposolicitud() {
        return descripcionTiposolicitud;
    }

    public void setDescripcionTiposolicitud(String descripcionTiposolicitud) {
        this.descripcionTiposolicitud = descripcionTiposolicitud;
    }

    public String getDescripcionsignodistintivo() {
        return descripcionsignodistintivo;
    }

    public void setDescripcionsignodistintivo(String descripcionsignodistintivo) {
        this.descripcionsignodistintivo = descripcionsignodistintivo;
    }

    public Date getFechaEstatusActual() {
        return fechaEstatusActual;
    }

    public void setFechaEstatusActual(Date fechaEstatusActual) {
        this.fechaEstatusActual = fechaEstatusActual;
    }

    public Date getFechacaptura() {
        return fechacaptura;
    }
    
    public Date getFechaCaptura() {
        return fechacaptura;
    }

    public void setFechacaptura(Date fechacaptura) {
        this.fechacaptura = fechacaptura;
    }

    public Short getHayprioridad() {
        return hayprioridad;
    }

    public void setHayprioridad(Short hayprioridad) {
        this.hayprioridad = hayprioridad;
    }

    public Integer getIdArea() {
        return idArea;
    }

    public void setIdArea(Integer idArea) {
        this.idArea = idArea;
    }

    public Long getIdEstatus() {
        return idEstatus;
    }

    public void setIdEstatus(Long idEstatus) {
        this.idEstatus = idEstatus;
    }

    public Long getIdSubtiposolicitud() {
        return idSubtiposolicitud;
    }

    public void setIdSubtiposolicitud(Long idSubtiposolicitud) {
        this.idSubtiposolicitud = idSubtiposolicitud;
    }

    public int getIdTipoTramite() {
        return idTipoTramite;
    }

    public void setIdTipoTramite(int idTipoTramite) {
        this.idTipoTramite = idTipoTramite;
    }

    public Long getIdTipomarca() {
        return idTipomarca;
    }

    public void setIdTipomarca(Long idTipomarca) {
        this.idTipomarca = idTipomarca;
    }

    public Long getIdTiposolicitud() {
        return idTiposolicitud;
    }

    public void setIdTiposolicitud(Long idTiposolicitud) {
        this.idTiposolicitud = idTiposolicitud;
    }

    public Long getIdTramite() {
        return idTramite;
    }

    public void setIdTramite(Long idTramite) {
        this.idTramite = idTramite;
    }

    public Long getIdUsuariocaptura() {
        return idUsuariocaptura;
    }

    public void setIdUsuariocaptura(Long idUsuariocaptura) {
        this.idUsuariocaptura = idUsuariocaptura;
    }

    public Long getIdUsuariofirmante() {
        return idUsuariofirmante;
    }

    public void setIdUsuariofirmante(Long idUsuariofirmante) {
        this.idUsuariofirmante = idUsuariofirmante;
    }

    public int getTotalSolicitantes() {
        return totalSolicitantes;
    }

    public void setTotalSolicitantes(int totalSolicitantes) {
        this.totalSolicitantes = totalSolicitantes;
    }

    public String getUrl_SigAccion_Estatus() {
        return url_SigAccion_Estatus;
    }

    public void setUrl_SigAccion_Estatus(String url_SigAccion_Estatus) {
        this.url_SigAccion_Estatus = url_SigAccion_Estatus;
    }

    public String getUrl_TipoSolicitud() {
        return url_TipoSolicitud;
    }

    public void setUrl_TipoSolicitud(String url_TipoSolicitud) {
        this.url_TipoSolicitud = url_TipoSolicitud;
    }

    public Usuario getUsuarioCaptura() {
        return usuarioCaptura;
    }

    public void setUsuarioCaptura(Usuario usuarioCaptura) {
        this.usuarioCaptura = usuarioCaptura;
    }

    public Usuario getUsuarioFirmante() {
        return usuarioFirmante;
    }

    public void setUsuarioFirmante(Usuario usuarioFirmante) {
        this.usuarioFirmante = usuarioFirmante;
    }

    public Integer getMostrarHerramientas() {
        return mostrarHerramientas;
    }

    public void setMostrarHerramientas(Integer mostrarHerramientas) {
        this.mostrarHerramientas = mostrarHerramientas;
    }

    public String getInvencion() {
        return invencion;
    }

    public void setInvencion(String invencion) {
        this.invencion = invencion;
        if (invencion.length() >= 20) {
            this.nombreCortoInvencion = invencion.substring(0, 20);
        } else {
            this.nombreCortoInvencion = invencion;
        }
    }

    public String getNombreCortoInvencion() {
        return nombreCortoInvencion;
    }

    /**
     * @return the idPromocion
     */
    public Long getIdPromocion() {
        return idPromocion;
    }

    /**
     * @param idPromocion the idPromocion to set
     */
    public void setIdPromocion(Long idPromocion) {
        this.idPromocion = idPromocion;
        this.idTramite = idPromocion;
    }

    /**
     * @return the oficioCodOficina
     */
    public String getOficioCodOficina() {
        return oficioCodOficina;
    }

    /**
     * @param oficioCodOficina the oficioCodOficina to set
     */
    public void setOficioCodOficina(String oficioCodOficina) {
        this.oficioCodOficina = oficioCodOficina;
    }

    /**
     * @return the oficioFolio
     */
    public String getOficioFolio() {
        return oficioFolio;
    }

    /**
     * @param oficioFolio the oficioFolio to set
     */
    public void setOficioFolio(String oficioFolio) {
        this.oficioFolio = oficioFolio;
    }

    /**
     * @return the oficioSerie
     */
    public String getOficioSerie() {
        return oficioSerie;
    }

    /**
     * @param oficioSerie the oficioSerie to set
     */
    public void setOficioSerie(String oficioSerie) {
        this.oficioSerie = oficioSerie;
    }

    /**
     * @return the descTipoPromocion
     */
    public String getDescTipoPromocion() {
        return descTipoPromocion;
    }

    /**
     * @param descTipoPromocion the descTipoPromocion to set
     */
    public void setDescTipoPromocion(String descTipoPromocion) {
        this.descTipoPromocion = descTipoPromocion;
    }

    /**
     * @return the codOficinaExped
     */
    public String getCodOficinaExped() {
        return codOficinaExped;
    }

    /**
     * @param codOficinaExped the codOficinaExped to set
     */
    public void setCodOficinaExped(String codOficinaExped) {
        this.codOficinaExped = codOficinaExped;
    }

    /**
     * @return the tipExped
     */
    public String getTipExped() {
        return tipExped;
    }

    /**
     * @param tipExped the tipExped to set
     */
    public void setTipExped(String tipExped) {
        this.tipExped = tipExped;
    }

    /**
     * @return the serExped
     */
    public String getSerExped() {
        return serExped;
    }

    /**
     * @param serExped the serExped to set
     */
    public void setSerExped(String serExped) {
        this.serExped = serExped;
    }

    /**
     * @return the numExped
     */
    public String getNumExped() {
        return numExped;
    }

    /**
     * @param numExped the numExped to set
     */
    public void setNumExped(String numExped) {
        this.numExped = numExped;
    }

    /**
     * @return the numConcesion
     */
    public String getNumConcesion() {
        return numConcesion;
    }

    /**
     * @param numConcesion the numConcesion to set
     */
    public void setNumConcesion(String numConcesion) {
        this.numConcesion = numConcesion;
    }

    /**
     * @return the descOficio
     */
    public String getDescOficio() {
        return descOficio;
    }

    /**
     * @param descOficio the descOficio to set
     */
    public void setDescOficio(String descOficio) {
        this.descOficio = descOficio;
    }

    public String getNumOficio() {
        if (getOficioCodOficina() != null && getOficioFolio() != null && getOficioSerie() != null) {
            return getOficioCodOficina() + "/" + getOficioFolio() + "/" + getOficioSerie();
        } else {
            return null;
        }
    }

    public String getNumExpediente() {
        return getCodOficinaExped() + "/" + getTipExped() + "/" + getSerExped() + "/" + getNumExped();
    }

    /**
     * @return the tieneOficio
     */
    public boolean isTieneOficio() {
        return (getOficioCodOficina() != null && getOficioFolio() != null && getOficioSerie() != null);
    }

    /**
     * @return the idTipoPromocion
     */
    public Long getIdTipoPromocion() {
        return idTipoPromocion;
    }

    /**
     * @param idTipoPromocion the idTipoPromocion to set
     */
    public void setIdTipoPromocion(Long idTipoPromocion) {
        this.idTipoPromocion = idTipoPromocion;
    }

    /**
     * @return the descPromocion
     */
    public String getDescPromocion() {
        return descPromocion;
    }

    /**
     * @param descPromocion the descPromocion to set
     */
    public void setDescPromocion(String descPromocion) {
        this.descPromocion = descPromocion;
    }

    /**
     * @return the plazoAdicional
     */
    public Integer getPlazoAdicional() {
        return plazoAdicional;
    }

    /**
     * @param plazoAdicional the plazoAdicional to set
     */
    public void setPlazoAdicional(Integer plazoAdicional) {
        this.plazoAdicional = plazoAdicional;
    }

    /**
     * @return the descuento
     */
    public Short getDescuento() {
        return descuento;
    }

    /**
     * @param descuento the descuento to set
     */
    public void setDescuento(Short descuento) {
        this.descuento = descuento;
    }

    /**
     * @return the mostrarDescuento
     */
    public Short getMostrarDescuento() {
        return mostrarDescuento;
    }

    /**
     * @param mostrarDescuento the mostrarDescuento to set
     */
    public void setMostrarDescuento(Short mostrarDescuento) {
        this.mostrarDescuento = mostrarDescuento;
    }

    /**
     * @return the mostrarPlazoAdicional
     */
    public Short getMostrarPlazoAdicional() {
        return mostrarPlazoAdicional;
    }

    /**
     * @param mostrarPlazoAdicional the mostrarPlazoAdicional to set
     */
    public void setMostrarPlazoAdicional(Short mostrarPlazoAdicional) {
        this.mostrarPlazoAdicional = mostrarPlazoAdicional;
    }

    /**
     * @return the tipoSolicitante
     */
    public Integer getTipoSolicitante() {
        return tipoSolicitante;
    }

    /**
     * @param tipoSolicitante the tipoSolicitante to set
     */
    public void setTipoSolicitante(Integer tipoSolicitante) {
        this.tipoSolicitante = tipoSolicitante;
    }

    public String getAreaPromocion() {
        return areaPromocion;
    }

    public void setAreaPromocion(String areaPromocion) {
        this.areaPromocion = areaPromocion;
    }

    public List<Anexos> getAnexos() {
        return anexos;
    }

    public void setAnexos(List<Anexos> anexos) {
        this.anexos = anexos;
    }
    
    
}
