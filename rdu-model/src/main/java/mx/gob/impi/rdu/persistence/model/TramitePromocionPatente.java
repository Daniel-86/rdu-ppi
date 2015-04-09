/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.gob.impi.rdu.persistence.model;

import java.io.Serializable;
import java.util.Date;

/**
 *
 * @author User
 */
public class TramitePromocionPatente implements Serializable {

    private int idPromocion;
    private Integer idEstatus;
    private Short indActivo;
    private Integer idUsuariocaptura;
    private String oficioCodOficina;
    private String oficioFolio;
    private String oficioSerie;
    private String descOficio;
    private Integer plazoAdicional;
    private Short descuento;
    private Long idTipoPromocionPatente;
    private String descripcionPromocion;
    private Date fechaCaptura;
    private Date fechaEstatusActual;
    private String codOficinaExped;
    private String tipExped;
    private Integer serExped;
    private Integer numExped;
    private Integer numConcesion;
    private String area;
    private Short mostrarDescuento;
    private Short mostrarPlazoAdicional;
    private Integer tipoSolicitante;

    /**
     * @return the idPromocion
     */
    public int getIdPromocion() {
        return idPromocion;
    }

    /**
     * @param idPromocion the idPromocion to set
     */
    public void setIdPromocion(int idPromocion) {
        this.idPromocion = idPromocion;
    }

    /**
     * @return the idEstatus
     */
    public Integer getIdEstatus() {
        return idEstatus;
    }

    /**
     * @param idEstatus the idEstatus to set
     */
    public void setIdEstatus(Integer idEstatus) {
        this.idEstatus = idEstatus;
    }

    /**
     * @return the indActivo
     */
    public Short getIndActivo() {
        return indActivo;
    }

    /**
     * @param indActivo the indActivo to set
     */
    public void setIndActivo(Short indActivo) {
        this.indActivo = indActivo;
    }

    /**
     * @return the idUsuariocaptura
     */
    public Integer getIdUsuariocaptura() {
        return idUsuariocaptura;
    }

    /**
     * @param idUsuariocaptura the idUsuariocaptura to set
     */
    public void setIdUsuariocaptura(Integer idUsuariocaptura) {
        this.idUsuariocaptura = idUsuariocaptura;
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
     * @return the idTipoPromocionPatente
     */
    public Long getIdTipoPromocionPatente() {
        return idTipoPromocionPatente;
    }

    /**
     * @param idTipoPromocionPatente the idTipoPromocionPatente to set
     */
    public void setIdTipoPromocionPatente(Long idTipoPromocionPatente) {
        this.idTipoPromocionPatente = idTipoPromocionPatente;
    }

    /**
     * @return the descripcionPromocion
     */
    public String getDescripcionPromocion() {
        return descripcionPromocion;
    }

    /**
     * @param descripcionPromocion the descripcionPromocion to set
     */
    public void setDescripcionPromocion(String descripcionPromocion) {
        this.descripcionPromocion = descripcionPromocion;
    }

    /**
     * @return the fechaCaptura
     */
    public Date getFechaCaptura() {
        return fechaCaptura;
    }

    /**
     * @param fechaCaptura the fechaCaptura to set
     */
    public void setFechaCaptura(Date fechaCaptura) {
        this.fechaCaptura = fechaCaptura;
    }

    /**
     * @return the fechaEstatusActual
     */
    public Date getFechaEstatusActual() {
        return fechaEstatusActual;
    }

    /**
     * @param fechaEstatusActual the fechaEstatusActual to set
     */
    public void setFechaEstatusActual(Date fechaEstatusActual) {
        this.fechaEstatusActual = fechaEstatusActual;
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
    public Integer getSerExped() {
        return serExped;
    }

    /**
     * @param serExped the serExped to set
     */
    public void setSerExped(Integer serExped) {
        this.serExped = serExped;
    }

    /**
     * @return the numExped
     */
    public Integer getNumExped() {
        return numExped;
    }

    /**
     * @param numExped the numExped to set
     */
    public void setNumExped(Integer numExped) {
        this.numExped = numExped;
    }

    /**
     * @return the numConcesion
     */
    public Integer getNumConcesion() {
        return numConcesion;
    }

    /**
     * @param numConcesion the numConcesion to set
     */
    public void setNumConcesion(Integer numConcesion) {
        this.numConcesion = numConcesion;
    }

    /**
     * @return the area
     */
    public String getArea() {
        return area;
    }

    /**
     * @param area the area to set
     */
    public void setArea(String area) {
        this.area = area;
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
}
