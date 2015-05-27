/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.gob.impi.sigappi.persistence.model;

import java.io.Serializable;
import java.util.Date;

/**
 *
 * @author daniel
 */
public class SolicitudWeb implements Serializable{
    Integer idSolicitud;
    Date fecha;
    String codBarras;
    Integer idPromovente;
    Integer idStatus;
    Integer tipoDocumento;

    public Integer getIdSolicitud() {
        return idSolicitud;
    }

    public void setIdSolicitud(Integer idSolicitud) {
        this.idSolicitud = idSolicitud;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public String getCodBarras() {
        return codBarras;
    }

    public void setCodBarras(String codBarras) {
        this.codBarras = codBarras;
    }

    public Integer getIdPromovente() {
        return idPromovente;
    }

    public void setIdPromovente(Integer idPromovente) {
        this.idPromovente = idPromovente;
    }

    public Integer getIdStatus() {
        return idStatus;
    }

    public void setIdStatus(Integer idStatus) {
        this.idStatus = idStatus;
    }

    public Integer getTipoDocumento() {
        return tipoDocumento;
    }

    public void setTipoDocumento(Integer tipoDocumento) {
        this.tipoDocumento = tipoDocumento;
    }
}
