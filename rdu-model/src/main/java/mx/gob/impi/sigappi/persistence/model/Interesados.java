package mx.gob.impi.sigappi.persistence.model;

import mx.gob.impi.sigappi.persistence.model.*;
import java.io.Serializable;
import java.util.Date;

public class Interesados implements Serializable {

    private Integer codInteresado;
private String tipoPersona;
private String nombre;
private String codNacionalidad;
private Date fechaModificacion;
private String cveUsuario;

    public Integer getCodInteresado() {
        return codInteresado;
    }

    public void setCodInteresado(Integer codInteresado) {
        this.codInteresado = codInteresado;
    }

    public String getTipoPersona() {
        return tipoPersona;
    }

    public void setTipoPersona(String tipoPersona) {
        this.tipoPersona = tipoPersona;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getCodNacionalidad() {
        return codNacionalidad;
    }

    public void setCodNacionalidad(String codNacionalidad) {
        this.codNacionalidad = codNacionalidad;
    }

    public Date getFechaModificacion() {
        return fechaModificacion;
    }

    public void setFechaModificacion(Date fechaModificacion) {
        this.fechaModificacion = fechaModificacion;
    }

    public String getCveUsuario() {
        return cveUsuario;
    }

    public void setCveUsuario(String cveUsuario) {
        this.cveUsuario = cveUsuario;
    }


}
