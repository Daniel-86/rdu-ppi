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
public class TipoOficio implements Serializable {

    private String tipoPromocion;
    private String tipoPromocionWnot;
    private String descOficio;

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
     * @return the tipoPromocionWnot
     */
    public String getTipoPromocionWnot() {
        return tipoPromocionWnot;
    }

    /**
     * @param tipoPromocionWnot the tipoPromocionWnot to set
     */
    public void setTipoPromocionWnot(String tipoPromocionWnot) {
        this.tipoPromocionWnot = tipoPromocionWnot;
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
}
