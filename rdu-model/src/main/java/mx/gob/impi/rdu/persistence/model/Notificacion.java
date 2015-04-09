package mx.gob.impi.rdu.persistence.model;

import java.io.Serializable;
import java.util.Date;

public class Notificacion implements Serializable {

    private Long idNotificacion;
    private String folio;
    private Integer idUsuarioCarga;
    private Date fechaCarga;
    private Integer idUsuarioFirma;
    private String archivoNombre;
    private Integer idActivo;
    private Integer idEstatus;
    private byte[] archivo;
    private String expediente;
    private String denominacion;
    private String titular;
    private Integer idArea;
    private String nombreExaminador;

    public Notificacion() {
    }
    
    public Notificacion(String folio) {
        this.folio = folio;
    }

    public Notificacion(String folio, String archivoNombre, byte[] archivo) {
        this.folio = folio;
        this.archivoNombre = archivoNombre;
        this.archivo = archivo;
    }

    public Long getIdNotificacion() {
        return idNotificacion;
    }

    public void setIdNotificacion(Long idNotificacion) {
        this.idNotificacion = idNotificacion;
    }

    public String getFolio() {
        return folio;
    }

    public void setFolio(String folio) {
        this.folio = folio == null ? null : folio.trim();
    }

    public Integer getIdUsuarioCarga() {
        return idUsuarioCarga;
    }

    public void setIdUsuarioCarga(Integer idUsuarioCarga) {
        this.idUsuarioCarga = idUsuarioCarga;
    }

    public Date getFechaCarga() {
        return fechaCarga;
    }

    public void setFechaCarga(Date fechaCarga) {
        this.fechaCarga = fechaCarga;
    }

    public Integer getIdUsuarioFirma() {
        return idUsuarioFirma;
    }

    public void setIdUsuarioFirma(Integer idUsuarioFirma) {
        this.idUsuarioFirma = idUsuarioFirma;
    }

    public String getArchivoNombre() {
        return archivoNombre;
    }

    public void setArchivoNombre(String archivoNombre) {
        this.archivoNombre = archivoNombre == null ? null : archivoNombre.trim();
    }

    public Integer getIdActivo() {
        return idActivo;
    }

    public void setIdActivo(Integer idActivo) {
        this.idActivo = idActivo;
    }

    public Integer getIdEstatus() {
        return idEstatus;
    }

    public void setIdEstatus(Integer idEstatus) {
        this.idEstatus = idEstatus;
    }

    public byte[] getArchivo() {
        return archivo;
    }

    public void setArchivo(byte[] archivo) {
        this.archivo = archivo;
    }

    public String getDenominacion() {
        return denominacion;
    }

    public void setDenominacion(String denominacion) {
        this.denominacion = denominacion;
    }

    public String getExpediente() {
        return expediente;
    }

    public void setExpediente(String expediente) {
        this.expediente = expediente;
    }

    public String getTitular() {
        return titular;
    }

    public void setTitular(String titular) {
        this.titular = titular;
    }

    public Integer getIdArea() {
        return idArea;
    }

    public void setIdArea(Integer idArea) {
        this.idArea = idArea;
    }

    public String getNombreExaminador() {
        return nombreExaminador;
    }

    public void setNombreExaminador(String nombreExaminador) {
        this.nombreExaminador = nombreExaminador;
    }
}
