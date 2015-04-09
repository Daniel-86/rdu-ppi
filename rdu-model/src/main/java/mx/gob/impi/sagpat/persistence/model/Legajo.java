package mx.gob.impi.sagpat.persistence.model;

import java.util.Date;

public class Legajo implements java.io.Serializable { 
    private String codOficina;

    private Long numExped;

    private Short secLegajo;

    private Integer serExped;

    private String tipExped;

    private Integer rowVersion;

    private String desLegajo;

    private Integer userId;

    private Date fecUltRelacion;

    private String indMicrofilmado;

    private String indDigitalizado;

    private String indResguardo;

    private Short serUltRelacion;

    private Integer numUltRelacion;

    private Integer numConcesion;

    private String codDirec;

    private String codSubdirec;

    private String codDepto;

    private String obs;

    private String objidFolder;

    private String objidLegajo;

    private String codbarras;

    private Short codOficinaReceptora;

    public String getCodOficina() {
        return codOficina;
    }

    public void setCodOficina(String codOficina) {
        this.codOficina = codOficina == null ? null : codOficina.trim();
    }

    public Long getNumExped() {
        return numExped;
    }

    public void setNumExped(Long numExped) {
        this.numExped = numExped;
    }

    public Short getSecLegajo() {
        return secLegajo;
    }

    public void setSecLegajo(Short secLegajo) {
        this.secLegajo = secLegajo;
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

    public String getDesLegajo() {
        return desLegajo;
    }

    public void setDesLegajo(String desLegajo) {
        this.desLegajo = desLegajo == null ? null : desLegajo.trim();
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Date getFecUltRelacion() {
        return fecUltRelacion;
    }

    public void setFecUltRelacion(Date fecUltRelacion) {
        this.fecUltRelacion = fecUltRelacion;
    }

    public String getIndMicrofilmado() {
        return indMicrofilmado;
    }

    public void setIndMicrofilmado(String indMicrofilmado) {
        this.indMicrofilmado = indMicrofilmado == null ? null : indMicrofilmado.trim();
    }

    public String getIndDigitalizado() {
        return indDigitalizado;
    }

    public void setIndDigitalizado(String indDigitalizado) {
        this.indDigitalizado = indDigitalizado == null ? null : indDigitalizado.trim();
    }

    public String getIndResguardo() {
        return indResguardo;
    }

    public void setIndResguardo(String indResguardo) {
        this.indResguardo = indResguardo == null ? null : indResguardo.trim();
    }

    public Short getSerUltRelacion() {
        return serUltRelacion;
    }

    public void setSerUltRelacion(Short serUltRelacion) {
        this.serUltRelacion = serUltRelacion;
    }

    public Integer getNumUltRelacion() {
        return numUltRelacion;
    }

    public void setNumUltRelacion(Integer numUltRelacion) {
        this.numUltRelacion = numUltRelacion;
    }

    public Integer getNumConcesion() {
        return numConcesion;
    }

    public void setNumConcesion(Integer numConcesion) {
        this.numConcesion = numConcesion;
    }

    public String getCodDirec() {
        return codDirec;
    }

    public void setCodDirec(String codDirec) {
        this.codDirec = codDirec == null ? null : codDirec.trim();
    }

    public String getCodSubdirec() {
        return codSubdirec;
    }

    public void setCodSubdirec(String codSubdirec) {
        this.codSubdirec = codSubdirec == null ? null : codSubdirec.trim();
    }

    public String getCodDepto() {
        return codDepto;
    }

    public void setCodDepto(String codDepto) {
        this.codDepto = codDepto == null ? null : codDepto.trim();
    }

    public String getObs() {
        return obs;
    }

    public void setObs(String obs) {
        this.obs = obs == null ? null : obs.trim();
    }

    public String getObjidFolder() {
        return objidFolder;
    }

    public void setObjidFolder(String objidFolder) {
        this.objidFolder = objidFolder == null ? null : objidFolder.trim();
    }

    public String getObjidLegajo() {
        return objidLegajo;
    }

    public void setObjidLegajo(String objidLegajo) {
        this.objidLegajo = objidLegajo == null ? null : objidLegajo.trim();
    }

    public String getCodbarras() {
        return codbarras;
    }

    public void setCodbarras(String codbarras) {
        this.codbarras = codbarras == null ? null : codbarras.trim();
    }

    public Short getCodOficinaReceptora() {
        return codOficinaReceptora;
    }

    public void setCodOficinaReceptora(Short codOficinaReceptora) {
        this.codOficinaReceptora = codOficinaReceptora;
    }
}