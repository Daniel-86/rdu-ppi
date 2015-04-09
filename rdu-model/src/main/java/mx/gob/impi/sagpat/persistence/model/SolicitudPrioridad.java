package mx.gob.impi.sagpat.persistence.model;

import java.math.BigDecimal;
import java.util.Date;

public class SolicitudPrioridad implements java.io.Serializable { 
    private String codOficina;

    private String codPais;

    private String idSolicitud;

    private Long numExped;

    private Integer serExped;

    private String tipExped;

    private Integer rowVersion;

    private Date fecSolicitud;

    private String reconocida;

    private String obs;

    private BigDecimal idSolicitudAlt;

    private String reconocidaFon;

    public String getCodOficina() {
        return codOficina;
    }

    public void setCodOficina(String codOficina) {
        this.codOficina = codOficina == null ? null : codOficina.trim();
    }

    public String getCodPais() {
        return codPais;
    }

    public void setCodPais(String codPais) {
        this.codPais = codPais == null ? null : codPais.trim();
    }

    public String getIdSolicitud() {
        return idSolicitud;
    }

    public void setIdSolicitud(String idSolicitud) {
        this.idSolicitud = idSolicitud == null ? null : idSolicitud.trim();
    }

    public Long getNumExped() {
        return numExped;
    }

    public void setNumExped(Long numExped) {
        this.numExped = numExped;
    }

    public Integer getSerExped() {
        return serExped;
    }

    public void setSerExped(Integer serExped) {
        this.serExped = serExped;
    }

    public String getTipExped() {
        return tipExped;
    }

    public void setTipExped(String tipExped) {
        this.tipExped = tipExped == null ? null : tipExped.trim();
    }

    public Integer getRowVersion() {
        return rowVersion;
    }

    public void setRowVersion(Integer rowVersion) {
        this.rowVersion = rowVersion;
    }

    public Date getFecSolicitud() {
        return fecSolicitud;
    }

    public void setFecSolicitud(Date fecSolicitud) {
        this.fecSolicitud = fecSolicitud;
    }

    public String getReconocida() {
        return reconocida;
    }

    public void setReconocida(String reconocida) {
        this.reconocida = reconocida == null ? null : reconocida.trim();
    }

    public String getObs() {
        return obs;
    }

    public void setObs(String obs) {
        this.obs = obs == null ? null : obs.trim();
    }

    public BigDecimal getIdSolicitudAlt() {
        return idSolicitudAlt;
    }

    public void setIdSolicitudAlt(BigDecimal idSolicitudAlt) {
        this.idSolicitudAlt = idSolicitudAlt;
    }

    public String getReconocidaFon() {
        return reconocidaFon;
    }

    public void setReconocidaFon(String reconocidaFon) {
        this.reconocidaFon = reconocidaFon == null ? null : reconocidaFon.trim();
    }
}