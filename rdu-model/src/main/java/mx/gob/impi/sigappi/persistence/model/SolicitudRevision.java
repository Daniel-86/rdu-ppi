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
public class SolicitudRevision implements Serializable{

    private Integer idSolicitud;
    private String title;
    private Integer codInteresado;
    private Integer codRelacion;
    private Date fechaModificacion;
    private Integer secuencia;
    private String cveUsuario;

    public Integer getIdSolicitud() {
        return idSolicitud;
    }

    public void setIdSolicitud(Integer idSolicitud) {
        this.idSolicitud = idSolicitud;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Integer getCodInteresado() {
        return codInteresado;
    }

    public void setCodInteresado(Integer codInteresado) {
        this.codInteresado = codInteresado;
    }

    public Integer getCodRelacion() {
        return codRelacion;
    }

    public void setCodRelacion(Integer codRelacion) {
        this.codRelacion = codRelacion;
    }

    public Date getFechaModificacion() {
        return fechaModificacion;
    }

    public void setFechaModificacion(Date fechaModificacion) {
        this.fechaModificacion = fechaModificacion;
    }

    public Integer getSecuencia() {
        return secuencia;
    }

    public void setSecuencia(Integer secuencia) {
        this.secuencia = secuencia;
    }

    public String getCveUsuario() {
        return cveUsuario;
    }

    public void setCveUsuario(String cveUsuario) {
        this.cveUsuario = cveUsuario;
    }


    @Override
    public boolean equals(Object o) {
        if (o instanceof SolicitudRevision) {
            SolicitudRevision s = (SolicitudRevision) o;
            return (this.title.equals(s.getTitle())
                    && this.codInteresado.equals(s.getCodInteresado())
                    && this.codRelacion.equals(s.getCodRelacion()));
        }
        if (o instanceof SolicitudInteresados) {
            SolicitudInteresados s = (SolicitudInteresados) o;
            return (this.title.equals(s.getTitle())
                    && this.codInteresado.equals(s.getCodInteresado())
                    && this.codRelacion.equals(s.getCodRelacion()));
        }
        if (o instanceof Notification) {
            Notification n = (Notification) o;
            return (this.title.equals(n.getTitle())
                    && this.codInteresado.equals(n.getUserId())
                    && this.codRelacion.equals(n.getUsertype()));
        }
        return false;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 83 * hash + (this.idSolicitud != null ? this.idSolicitud.hashCode() : 0);
        hash = 83 * hash + (this.title != null ? this.title.hashCode() : 0);
        hash = 83 * hash + (this.codInteresado != null ? this.codInteresado.hashCode() : 0);
        hash = 83 * hash + (this.codRelacion != null ? this.codRelacion.hashCode() : 0);
        return hash;
    }
}
