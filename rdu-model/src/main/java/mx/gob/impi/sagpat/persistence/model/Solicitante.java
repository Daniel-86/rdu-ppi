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
public class Solicitante implements Serializable {

    private String nomPersona;
    private String descTipo;
    private String tipoPersona;
    private double tipoSolicitante;

    /**
     * @return the nomPersona
     */
    public String getNomPersona() {
        return nomPersona;
    }

    /**
     * @param nomPersona the nomPersona to set
     */
    public void setNomPersona(String nomPersona) {
        this.nomPersona = nomPersona;
    }

    /**
     * @return the descTipo
     */
    public String getDescTipo() {
        return descTipo;
    }

    /**
     * @param descTipo the descTipo to set
     */
    public void setDescTipo(String descTipo) {
        this.descTipo = descTipo;
    }

    /**
     * @return the tipoPersona
     */
    public String getTipoPersona() {
        return tipoPersona;
    }

    /**
     * @param tipoPersona the tipoPersona to set
     */
    public void setTipoPersona(String tipoPersona) {
        this.tipoPersona = tipoPersona;
    }

    /**
     * @return the tipoSolicitante
     */
    public double getTipoSolicitante() {
        return tipoSolicitante;
    }

    /**
     * @param tipoSolicitante the tipoSolicitante to set
     */
    public void setTipoSolicitante(double tipoSolicitante) {
        this.tipoSolicitante = tipoSolicitante;
    }

    public boolean getAplicaDescuento() {
        switch ((int) tipoSolicitante) {
            case 3:
            case 4:
            case 5:
            case 6:
            case 7:
                return true;
            default:
                return false;
        }
    }
}
