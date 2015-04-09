/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.gob.impi.sigmar.persistence.model;

import java.io.Serializable;
import java.util.Date;

/**
 *
 * @author oracle
 */
public class NotificacionView implements Serializable {

    private Integer tipoSolicitud;
    private Integer anioSolicitud;
    private String expediente;
    private String oficioSalida;
    private String descripcion;
    private Date fechaMovimiento;
    private String registro;
    private String titular;
    private String denominacion;
    private byte[] archivo;

    public Integer getAnioSolicitud() {
        return anioSolicitud;
    }

    public void setAnioSolicitud(Integer anioSolicitud) {
        this.anioSolicitud = anioSolicitud;
    }

    public String getDenominacion() {
        return denominacion;
    }

    public void setDenominacion(String denominacion) {
        this.denominacion = denominacion;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }
    

    public Date getFechaMovimiento() {
        return fechaMovimiento;
    }

    public void setFechaMovimiento(Date fechaMovimiento) {
        this.fechaMovimiento = fechaMovimiento;
    }

    public String getExpediente() {
        return expediente;
    }

    public void setExpediente(String expediente) {
        this.expediente = expediente;
    }

    public String getOficioSalida() {
        return oficioSalida;
    }

    public void setOficioSalida(String oficioSalida) {
        this.oficioSalida = oficioSalida;
    }

    public String getRegistro() {
        return registro;
    }

    public void setRegistro(String registro) {
        this.registro = registro;
    }

    public Integer getTipoSolicitud() {
        return tipoSolicitud;
    }

    public void setTipoSolicitud(Integer tipoSolicitud) {
        this.tipoSolicitud = tipoSolicitud;
    }

    public String getTitular() {
        return titular;
    }

    public void setTitular(String titular) {
        this.titular = titular;
    }

    public byte[] getArchivo() {
        return archivo;
    }

    public void setArchivo(byte[] archivo) {
        this.archivo = archivo;
    }
}
