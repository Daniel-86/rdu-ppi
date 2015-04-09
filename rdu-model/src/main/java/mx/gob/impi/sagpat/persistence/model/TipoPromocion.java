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
public class TipoPromocion implements Serializable {

    private String desTipoRdu;
    private String articulo;
    private String tipoTramite;
    private String indRenovacion;

    /**
     * @return the desTipoRdu
     */
    public String getDesTipoRdu() {
        return desTipoRdu;
    }

    /**
     * @param desTipoRdu the desTipoRdu to set
     */
    public void setDesTipoRdu(String desTipoRdu) {
        this.desTipoRdu = desTipoRdu;
    }

    /**
     * @return the articulo
     */
    public String getArticulo() {
        return articulo;
    }

    /**
     * @param articulo the articulo to set
     */
    public void setArticulo(String articulo) {
        this.articulo = articulo;
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

    public String getArea() {
        if (tipoTramite != null) {
            if (tipoTramite.equals("PRO2")) {
                return "Forma";
            } else if (tipoTramite.equals("PROM")) {
                return "Fondo";
            } else {
                return "Otra";
            }
        } else {
            return "Otra";
        }
    }

    /**
     * @return the indRenovacion
     */
    public String getIndRenovacion() {
        return indRenovacion;
    }

    /**
     * @param indRenovacion the indRenovacion to set
     */
    public void setIndRenovacion(String indRenovacion) {
        this.indRenovacion = indRenovacion;
    }

    public boolean getPlazoAdicional() {
        if (indRenovacion != null && indRenovacion.equals("N")) {
            return false;
        } else {
            return true;
        }
    }
}
