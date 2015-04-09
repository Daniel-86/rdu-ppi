/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.gob.impi.rdu.dto;

import java.io.Serializable;

/**
 *
 * @author usradmin
 */
public class PromocionesConOficioDto implements Serializable {
    private String tipTramiteSolic;
    private int numTramiteSolic;
    private String codStatus;
    private String tipoOficio;

    public String getTipTramiteSolic() {
        return tipTramiteSolic;
    }

    public void setTipTramiteSolic(String tipTramiteSolic) {
        this.tipTramiteSolic = tipTramiteSolic;
    }

    public int getNumTramiteSolic() {
        return numTramiteSolic;
    }

    public void setNumTramiteSolic(int numTramiteSolic) {
        this.numTramiteSolic = numTramiteSolic;
    }

    public String getCodStatus() {
        return codStatus;
    }

    public void setCodStatus(String codStatus) {
        this.codStatus = codStatus;
    }

    public String getTipoOficio() {
        return tipoOficio;
    }

    public void setTipoOficio(String tipoOficio) {
        this.tipoOficio = tipoOficio;
    }
       
}