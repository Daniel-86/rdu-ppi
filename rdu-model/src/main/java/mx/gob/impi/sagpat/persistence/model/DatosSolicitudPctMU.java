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
public class DatosSolicitudPctMU implements Serializable {
    private String idSolicitudPct;
    private String datos;
    private String codigo;

    public String getIdSolicitudPct() {
        return idSolicitudPct;
    }

    public void setIdSolicitudPct(String idSolicitudPct) {
        this.idSolicitudPct = idSolicitudPct;
    }
    public String getDatos() {
        return datos;
    }

    public void setDatos(String datos) {
        this.datos = datos;
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }
}
