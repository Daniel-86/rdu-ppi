package mx.gob.impi.rdu.persistence.model;

import java.io.Serializable;
import java.util.Date;
import mx.gob.impi.rdu.util.Util;

public class Prioridad implements Serializable {

    private Long idPrioridad;
    private Long idPais;
    private String numeroExpediente;
    private String numeroExpedienteTmp;
    private Date fechaPresentacionExt;
    private String nombrePais;
    private String codigoPais;
    private int idProvisional;
    private String fechaPresentacion;
    private Long idAnexoPrioridad;
    private Long idAnexoTraduccion; 

    public String getNumeroExpedienteTmp() {
        return numeroExpedienteTmp;
    }

    public void setNumeroExpedienteTmp(String numeroExpedienteTmp) {
        this.numeroExpedienteTmp = numeroExpedienteTmp;
    }

    public String getFechaPresentacion() {
        return fechaPresentacion;
    }

    public void setFechaPresentacion(String fechaPresentacion) {
        this.fechaPresentacion = fechaPresentacion;
    }

    public int getIdProvisional() {
        return idProvisional;
    }

    public void setIdProvisional(int idProvisional) {
        this.idProvisional = idProvisional;
    }

    public String getNombrePais() {
        return nombrePais;
    }

    public void setNombrePais(String nombrePais) {
        this.nombrePais = nombrePais;
    }

    public Long getIdPrioridad() {
        return idPrioridad;
    }

    public void setIdPrioridad(Long idPrioridad) {
        this.idPrioridad = idPrioridad;
    }

    public Long getIdPais() {
        return idPais;
    }

    public void setIdPais(Long idPais) {
        this.idPais = idPais;
    }

    public String getNumeroExpediente() {


        return numeroExpediente;
    }

    public void setNumeroExpediente(String numeroExpediente) {
        this.numeroExpediente = numeroExpediente == null ? null : numeroExpediente.trim();
        if (numeroExpediente != null) {
            this.numeroExpedienteTmp = Util.reemplazaAcentos(numeroExpediente.replaceAll("[\u002F\u002C\u002E]", ""));
        }
    }

    public Date getFechaPresentacionExt() {
        return fechaPresentacionExt;
    }

    public void setFechaPresentacionExt(Date fechaPresentacionExt) {
        this.fechaPresentacionExt = fechaPresentacionExt;
    }

    public String getCodigoPais() {
        return codigoPais;
    }

    public void setCodigoPais(String codigoPais) {
        this.codigoPais = codigoPais;
    }

    public Long getIdAnexoPrioridad() {
        return idAnexoPrioridad;
    }

    public void setIdAnexoPrioridad(Long idAnexoPrioridad) {
        this.idAnexoPrioridad = idAnexoPrioridad;
    }

    public Long getIdAnexoTraduccion() {
        return idAnexoTraduccion;
    }

    public void setIdAnexoTraduccion(Long idAnexoTraduccion) {
        this.idAnexoTraduccion = idAnexoTraduccion;
    }
}
