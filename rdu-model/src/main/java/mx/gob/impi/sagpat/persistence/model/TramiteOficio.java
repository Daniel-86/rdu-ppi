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
public class TramiteOficio implements Serializable {

    // Propiedades del Oficio
    private String codOficinaOficio;
    private double serOficio;
    private double numOficio;
    private String tipoOficio;
    // Propiedades del Tramite
    private String tipoTramite;
    private double numTramite;
    private String codStatus;
    //-------------------------------
    private String codOficinaExped;
    private String tipoExped;
    private double serExped;
    private double numExped;
    //-------------------------------
    private String codOficinaExpPro;
    private String tipoExpedExpPro;
    private double serExpedExpPro;
    private double numExpedExpPro;

    public TramiteOficio() {
    }

    public TramiteOficio(String codOficinaOficio, double serOficio, double numOficio) {
        this.codOficinaOficio = codOficinaOficio;
        this.serOficio = serOficio;
        this.numOficio = numOficio;
    }

    public TramiteOficio(String codOficinaExped, String tipoExped, double serExped, double numExped) {
        this.codOficinaExped = codOficinaExped;
        this.tipoExped = tipoExped;
        this.serExped = serExped;
        this.numExped = numExped;
    }

    /**
     * @return the codOficinaOficio
     */
    public String getCodOficinaOficio() {
        return codOficinaOficio;
    }

    /**
     * @param codOficinaOficio the codOficinaOficio to set
     */
    public void setCodOficinaOficio(String codOficinaOficio) {
        this.codOficinaOficio = codOficinaOficio;
    }

    /**
     * @return the serOficio
     */
    public double getSerOficio() {
        return serOficio;
    }

    /**
     * @param serOficio the serOficio to set
     */
    public void setSerOficio(double serOficio) {
        this.serOficio = serOficio;
    }

    /**
     * @return the numOficio
     */
    public double getNumOficio() {
        return numOficio;
    }

    /**
     * @param numOficio the numOficio to set
     */
    public void setNumOficio(double numOficio) {
        this.numOficio = numOficio;
    }

    /**
     * @return the tipOficio
     */
    public String getTipoOficio() {
        return tipoOficio;
    }

    /**
     * @param tipOficio the tipOficio to set
     */
    public void setTipoOficio(String tipOficio) {
        this.tipoOficio = tipOficio;
    }

    /**
     * @return the tipoTramite
     */
    public String getTipoTramite() {
        return tipoTramite;
    }

    /**
     * @param tipoTramite the tipoTramite to set
     */
    public void setTipoTramite(String tipoTramite) {
        this.tipoTramite = tipoTramite;
    }

    /**
     * @return the numTramite
     */
    public double getNumTramite() {
        return numTramite;
    }

    /**
     * @param numTramite the numTramite to set
     */
    public void setNumTramite(double numTramite) {
        this.numTramite = numTramite;
    }

    /**
     * @return the codStatus
     */
    public String getCodStatus() {
        return codStatus;
    }

    /**
     * @param codStatus the codStatus to set
     */
    public void setCodStatus(String codStatus) {
        this.codStatus = codStatus;
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
    public double getSerExped() {
        return serExped;
    }

    /**
     * @param serExped the serExped to set
     */
    public void setSerExped(double serExped) {
        this.serExped = serExped;
    }

    /**
     * @return the numExped
     */
    public double getNumExped() {
        return numExped;
    }

    /**
     * @param numExped the numExped to set
     */
    public void setNumExped(double numExped) {
        this.numExped = numExped;
    }

    /**
     * @return the codOficinaExpPro
     */
    public String getCodOficinaExpPro() {
        return codOficinaExpPro;
    }

    /**
     * @param codOficinaExpPro the codOficinaExpPro to set
     */
    public void setCodOficinaExpPro(String codOficinaExpPro) {
        this.codOficinaExpPro = codOficinaExpPro;
    }

    /**
     * @return the tipoExpedExpPro
     */
    public String getTipoExpedExpPro() {
        return tipoExpedExpPro;
    }

    /**
     * @param tipoExpedExpPro the tipoExpedExpPro to set
     */
    public void setTipoExpedExpPro(String tipoExpedExpPro) {
        this.tipoExpedExpPro = tipoExpedExpPro;
    }

    /**
     * @return the serExpedExpPro
     */
    public double getSerExpedExpPro() {
        return serExpedExpPro;
    }

    /**
     * @param serExpedExpPro the serExpedExpPro to set
     */
    public void setSerExpedExpPro(double serExpedExpPro) {
        this.serExpedExpPro = serExpedExpPro;
    }

    /**
     * @return the numExpedExpPro
     */
    public double getNumExpedExpPro() {
        return numExpedExpPro;
    }

    /**
     * @param numExpedExpPro the numExpedExpPro to set
     */
    public void setNumExpedExpPro(double numExpedExpPro) {
        this.numExpedExpPro = numExpedExpPro;
    }
}
