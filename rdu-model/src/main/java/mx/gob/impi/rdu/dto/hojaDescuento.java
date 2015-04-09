package mx.gob.impi.rdu.dto;

import java.util.Date;

/**
 *
 * @author JMM
 */
public class hojaDescuento {
    private Date fecha;
    private String numSolicitud;
    private int tipo;
    private String nombre;
    private String anio;
    private String mes;
    private String dia;
    private String nombreApoderado;
    private String imgFirmaImpi;
    private String tipoSolicitud;
    private String nombreFirmante;
    private String selloSolicitante;
    private int flag;

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public String getNumSolicitud() {
        return numSolicitud;
    }

    public void setNumSolicitud(String numSolicitud) {
        this.numSolicitud = numSolicitud;
    }

    public int getTipo() {
        return tipo;
    }

    public void setTipo(int tipo) {
        this.tipo = tipo;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getMes() {
        return mes;
    }

    public void setMes(String mes) {
        this.mes = mes;
    }

    public String getDia() {
        return dia;
    }

    public void setDia(String dia) {
        this.dia = dia;
    }

    public String getAnio() {
        return anio;
    }

    public void setAnio(String anio) {
        this.anio = anio;
    }

    public String getNombreApoderado() {
        return nombreApoderado;
    }

    public void setNombreApoderado(String nombreApoderado) {
        this.nombreApoderado = nombreApoderado;
    }

    public String getImgFirmaImpi() {
        return imgFirmaImpi;
    }

    public void setImgFirmaImpi(String imgFirmaImpi) {
        this.imgFirmaImpi = imgFirmaImpi;
    }

    public String getTipoSolicitud() {
        return tipoSolicitud;
    }

    public void setTipoSolicitud(String tipoSolicitud) {
        this.tipoSolicitud = tipoSolicitud;
    }

    public int getFlag() {
        return flag;
    }

    public void setFlag(int flag) {
        this.flag = flag;
    }
    
    public String getNombreFirmante() {
        return nombreFirmante;
    }

    public void setNombreFirmante(String nombreFirmante) {
        this.nombreFirmante = nombreFirmante;
    }

    public String getSelloSolicitante() {
        return selloSolicitante;
    }

    public void setSelloSolicitante(String selloSolicitante) {
        this.selloSolicitante = selloSolicitante;
    }
}