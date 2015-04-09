package mx.gob.impi.sigmar.persistence.model;

import java.io.Serializable;
import java.util.Date;

public class Notificacion  implements Serializable{
    private Short tipoSolicitud;

    private Short anoSolicitud;

    private Integer expediente;

    private String nombre;

    private String direccion;

    private String poblacion;

    private String codPostal;

    private String codPais;

    private Date fechaModificacion;

    private Long codNotificacion;

    public Short getTipoSolicitud() {
        return tipoSolicitud;
    }

    public void setTipoSolicitud(Short tipoSolicitud) {
        this.tipoSolicitud = tipoSolicitud;
    }

    public Short getAnoSolicitud() {
        return anoSolicitud;
    }

    public void setAnoSolicitud(Short anoSolicitud) {
        this.anoSolicitud = anoSolicitud;
    }

    public Integer getExpediente() {
        return expediente;
    }

    public void setExpediente(Integer expediente) {
        this.expediente = expediente;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre == null ? null : nombre.trim();
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion == null ? null : direccion.trim();
    }

    public String getPoblacion() {
        return poblacion;
    }

    public void setPoblacion(String poblacion) {
        this.poblacion = poblacion == null ? null : poblacion.trim();
    }

    public String getCodPostal() {
        return codPostal;
    }

    public void setCodPostal(String codPostal) {
        this.codPostal = codPostal == null ? null : codPostal.trim();
    }

    public String getCodPais() {
        return codPais;
    }

    public void setCodPais(String codPais) {
        this.codPais = codPais == null ? null : codPais.trim();
    }

    public Date getFechaModificacion() {
        return fechaModificacion;
    }

    public void setFechaModificacion(Date fechaModificacion) {
        this.fechaModificacion = fechaModificacion;
    }

    public Long getCodNotificacion() {
        return codNotificacion;
    }

    public void setCodNotificacion(Long codNotificacion) {
        this.codNotificacion = codNotificacion;
    }
}