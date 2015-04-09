/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.gob.impi.sagpat.persistence.model;

import java.io.Serializable;

/**
 *
 * @author oracle
 */
public class DatosSolicitudPct implements Serializable {

    private String idSolicitudPct;
    private String fechaSolicitudPct;
    private String fechaPublicacionSolicitudPct;
    private String numeroSolicitudPct;
    private String tipoSolicitudPct;

    public String getFechaPublicacionSolicitudPct() {
        return fechaPublicacionSolicitudPct;
    }

    public void setFechaPublicacionSolicitudPct(String fechaPublicacionSolicitudPct) {
        this.fechaPublicacionSolicitudPct = fechaPublicacionSolicitudPct;
    }

    public String getFechaSolicitudPct() {
        return fechaSolicitudPct;
    }

    public void setFechaSolicitudPct(String fechaSolicitudPct) {
        this.fechaSolicitudPct = fechaSolicitudPct;
    }

    public String getIdSolicitudPct() {
        return idSolicitudPct;
    }

    public void setIdSolicitudPct(String idSolicitudPct) {
        this.idSolicitudPct = idSolicitudPct;
    }

    public String getNumeroSolicitudPct() {
        return numeroSolicitudPct;
    }

    public void setNumeroSolicitudPct(String numeroSolicitudPct) {
        this.numeroSolicitudPct = numeroSolicitudPct;
    }

    public String getTipoSolicitudPct() {
        return tipoSolicitudPct;
    }

    public void setTipoSolicitudPct(String tipoSolicitudPct) {
        this.tipoSolicitudPct = tipoSolicitudPct;
    }
}
