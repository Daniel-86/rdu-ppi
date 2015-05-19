package mx.gob.impi.sigappi.persistence.model;

import mx.gob.impi.sigappi.persistence.model.*;
import java.io.Serializable;
import java.util.Date;

public class DerechosAsociados implements Serializable {

    private String title;
private Integer codInteresado;
private Integer codRelacion;
private Integer consecutivo;
private Integer tipoSolicitud;
private Integer anoSolicitud;
private Integer expediente;
private Integer clase;
private String codOficina;
private String tipExped;
private Integer serExped;
private Integer numExped;
private Integer numFigura;
private Integer numSubfigura;
private Integer anoRegistro;
private Integer numRegistro;
private String nombre;

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

    public Integer getConsecutivo() {
        return consecutivo;
    }

    public void setConsecutivo(Integer consecutivo) {
        this.consecutivo = consecutivo;
    }

    public Integer getTipoSolicitud() {
        return tipoSolicitud;
    }

    public void setTipoSolicitud(Integer tipoSolicitud) {
        this.tipoSolicitud = tipoSolicitud;
    }

    public Integer getAnoSolicitud() {
        return anoSolicitud;
    }

    public void setAnoSolicitud(Integer anoSolicitud) {
        this.anoSolicitud = anoSolicitud;
    }

    public Integer getExpediente() {
        return expediente;
    }

    public void setExpediente(Integer expediente) {
        this.expediente = expediente;
    }

    public Integer getClase() {
        return clase;
    }

    public void setClase(Integer clase) {
        this.clase = clase;
    }

    public String getCodOficina() {
        return codOficina;
    }

    public void setCodOficina(String codOficina) {
        this.codOficina = codOficina;
    }

    public String getTipExped() {
        return tipExped;
    }

    public void setTipExped(String tipExped) {
        this.tipExped = tipExped;
    }

    public Integer getSerExped() {
        return serExped;
    }

    public void setSerExped(Integer serExped) {
        this.serExped = serExped;
    }

    public Integer getNumExped() {
        return numExped;
    }

    public void setNumExped(Integer numExped) {
        this.numExped = numExped;
    }

    public Integer getNumFigura() {
        return numFigura;
    }

    public void setNumFigura(Integer numFigura) {
        this.numFigura = numFigura;
    }

    public Integer getNumSubfigura() {
        return numSubfigura;
    }

    public void setNumSubfigura(Integer numSubfigura) {
        this.numSubfigura = numSubfigura;
    }

    public Integer getAnoRegistro() {
        return anoRegistro;
    }

    public void setAnoRegistro(Integer anoRegistro) {
        this.anoRegistro = anoRegistro;
    }

    public Integer getNumRegistro() {
        return numRegistro;
    }

    public void setNumRegistro(Integer numRegistro) {
        this.numRegistro = numRegistro;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }


    
}
