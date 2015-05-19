package mx.gob.impi.sigappi.persistence.model;

import mx.gob.impi.sigappi.persistence.model.*;
import java.io.Serializable;
import java.util.Date;

public class SolicitudInteresados implements Serializable {

    private String title;
    private Integer codInteresado;
    private Integer codRelacion;
    private Date fechaModificacion;
    private Integer secuencia;
    private String cveUsuario;

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
        if(!(o instanceof SolicitudInteresados))
            return false;
        if(o == this)
            return true;
        
        SolicitudInteresados s = (SolicitudInteresados) o;
        return (this.title.equals(s.getTitle()) 
                && this.codInteresado.equals(s.getCodInteresado()) 
                && this.codRelacion.equals(s.getCodRelacion()));
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 89 * hash + (this.title != null ? this.title.hashCode() : 0);
        hash = 89 * hash + (this.codInteresado != null ? this.codInteresado.hashCode() : 0);
        hash = 89 * hash + (this.codRelacion != null ? this.codRelacion.hashCode() : 0);
        return hash;
    }

}
