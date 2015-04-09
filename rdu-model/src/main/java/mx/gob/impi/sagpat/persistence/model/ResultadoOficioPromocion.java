/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.gob.impi.sagpat.persistence.model;

import java.io.Serializable;

/**
 *
 * @author User
 */
public class ResultadoOficioPromocion implements Serializable {

    private String descOficio;
    private String tipoPromocion;
    private String descPromocion;
    private String area;
    private String codOficinaExped;
    private String tipoExped;
    private String serExped;
    private String numExped;
    private String numConcesion="";
    private boolean plazoAdicional;
    private boolean aplicaDescuento;
    private String codigoError;
    private int tipoSolicitante;

    public boolean getTieneError() {
        return getCodigoError() != null;
    }

    /**
     * @return the codigoError
     */
    public String getCodigoError() {
        return codigoError;
    }

    /**
     * @param codigoError the codigoError to set
     */
    public void setCodigoError(String codigoError) {
        this.codigoError = codigoError;
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
     * @return the tipoPromocion
     */
    public String getTipoPromocion() {
        return tipoPromocion;
    }

    /**
     * @param tipoPromocion the tipoPromocion to set
     */
    public void setTipoPromocion(String tipoPromocion) {
        this.tipoPromocion = tipoPromocion;
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
     * @return the tipoExped
     */
    public String getTipoExped() {
        return tipoExped;
    }

    /**
     * @param tipoExped the tipoExped to set
     */
    public void setTipoExped(String tipoExped) {
        this.tipoExped = tipoExped;
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
     * @return the plazoAdicional
     */
    public boolean isPlazoAdicional() {
        return plazoAdicional;
    }

    /**
     * @param plazoAdicional the plazoAdicional to set
     */
    public void setPlazoAdicional(boolean plazoAdicional) {
        this.plazoAdicional = plazoAdicional;
    }

    /**
     * @return the aplicaDescuento
     */
    public boolean isAplicaDescuento() {
        return aplicaDescuento;
    }

    /**
     * @param aplicaDescuento the aplicaDescuento to set
     */
    public void setAplicaDescuento(boolean aplicaDescuento) {
        this.aplicaDescuento = aplicaDescuento;
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
     * @return the tipoSolicitante
     */
    public int getTipoSolicitante() {
        return tipoSolicitante;
    }

    /**
     * @param tipoSolicitante the tipoSolicitante to set
     */
    public void setTipoSolicitante(int tipoSolicitante) {
        this.tipoSolicitante = tipoSolicitante;
    }
}
