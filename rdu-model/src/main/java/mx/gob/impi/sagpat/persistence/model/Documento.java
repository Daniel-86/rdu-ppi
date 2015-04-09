package mx.gob.impi.sagpat.persistence.model;

import java.util.Date;

public class Documento implements java.io.Serializable  {

    private String codOficina;
    private Long numDocum;
    private Integer serDocum;
    private String tipLibro;
    private Integer rowVersion;
    private Date fecProceso;
    private Date fecPresentacion;
    private String tipEntrada;
    private String indRecepcionFax;
    private Short codDelegacion;
    private Date fecDelegacion;
    private Date fecRecepcionFax;
    private String codOficinaOficioAcuse;
    private Short serOficioAcuse;
    private Integer numOficioAcuse;
    private String tipSolicitud;
    private String tipExped;
    private Integer serExped;
    private Long numExped;
    private String codOficinaPs;
    private String tipLibroPs;
    private Short serDocumPs;
    private Integer numDocumPs;
    private String codOficinaSi;
    private String tipLibroSi;
    private Short serDocumSi;
    private Integer numDocumSi;
    private String indOriginalFax;
    private String codOficinaResp;
    private String tipLibroResp;
    private Short serDocumResp;
    private Integer numDocumResp;
    private Date fecCorreo;
    private Date fecNotificacion;
    private String indResponder;
    private String indSistema;
    private String codOficinaOficio;
    private Short serOficio;
    private Integer numOficio;
    private String desDocum;
    private Integer refDocum;
    private String indFaltanExpeds;
    private String codDirec;
    private String codSubdirec;
    private String codDepto;
    private String obs;
    private String subtipSolicitud;
    private Integer numAuxil1;
    private Date fecRecepcion;
    private Date fecRecepcionOrig;
    private Integer numPaginas;
    private Integer numPagsMigradas;
    private String indDepositoMatBiologico;
    private Integer userId;
    private Short codOficinaReceptora;
    private String indCapturaGlosa;
    private String indCapturaDatosProm;
    private String indCapturaDatos;

    public Documento(String codOficina,Date fecProceso, String tipLibro) {
        this.codOficina = codOficina;
        this.tipLibro = tipLibro;
        this.fecProceso = fecProceso;
    }
    
    

    public Documento() {
    }

    
    
    public String getCodOficina() {
        return codOficina;
    }

    public void setCodOficina(String codOficina) {
        this.codOficina = codOficina == null ? null : codOficina.trim();
    }

    public Long getNumDocum() {
        return numDocum;
    }

    public void setNumDocum(Long numDocum) {
        this.numDocum = numDocum;
    }

    public Integer getSerDocum() {
        return serDocum;
    }

    public void setSerDocum(Integer serDocum) {
        this.serDocum = serDocum;
    }

    public String getTipLibro() {
        return tipLibro;
    }

    public void setTipLibro(String tipLibro) {
        this.tipLibro = tipLibro == null ? null : tipLibro.trim();
    }

    public Integer getRowVersion() {
        return rowVersion;
    }

    public void setRowVersion(Integer rowVersion) {
        this.rowVersion = rowVersion;
    }

    public Date getFecProceso() {
        return fecProceso;
    }

    public void setFecProceso(Date fecProceso) {
        this.fecProceso = fecProceso;
    }

    public Date getFecPresentacion() {
        return fecPresentacion;
    }

    public void setFecPresentacion(Date fecPresentacion) {
        this.fecPresentacion = fecPresentacion;
    }

    public String getTipEntrada() {
        return tipEntrada;
    }

    public void setTipEntrada(String tipEntrada) {
        this.tipEntrada = tipEntrada == null ? null : tipEntrada.trim();
    }

    public String getIndRecepcionFax() {
        return indRecepcionFax;
    }

    public void setIndRecepcionFax(String indRecepcionFax) {
        this.indRecepcionFax = indRecepcionFax == null ? null : indRecepcionFax.trim();
    }

    public Short getCodDelegacion() {
        return codDelegacion;
    }

    public void setCodDelegacion(Short codDelegacion) {
        this.codDelegacion = codDelegacion;
    }

    public Date getFecDelegacion() {
        return fecDelegacion;
    }

    public void setFecDelegacion(Date fecDelegacion) {
        this.fecDelegacion = fecDelegacion;
    }

    public Date getFecRecepcionFax() {
        return fecRecepcionFax;
    }

    public void setFecRecepcionFax(Date fecRecepcionFax) {
        this.fecRecepcionFax = fecRecepcionFax;
    }

    public String getCodOficinaOficioAcuse() {
        return codOficinaOficioAcuse;
    }

    public void setCodOficinaOficioAcuse(String codOficinaOficioAcuse) {
        this.codOficinaOficioAcuse = codOficinaOficioAcuse == null ? null : codOficinaOficioAcuse.trim();
    }

    public Short getSerOficioAcuse() {
        return serOficioAcuse;
    }

    public void setSerOficioAcuse(Short serOficioAcuse) {
        this.serOficioAcuse = serOficioAcuse;
    }

    public Integer getNumOficioAcuse() {
        return numOficioAcuse;
    }

    public void setNumOficioAcuse(Integer numOficioAcuse) {
        this.numOficioAcuse = numOficioAcuse;
    }

    public String getTipSolicitud() {
        return tipSolicitud;
    }

    public void setTipSolicitud(String tipSolicitud) {
        this.tipSolicitud = tipSolicitud == null ? null : tipSolicitud.trim();
    }

    public String getTipExped() {
        return tipExped;
    }

    public void setTipExped(String tipExped) {
        this.tipExped = tipExped == null ? null : tipExped.trim();
    }

    public Integer getSerExped() {
        return serExped;
    }

    public void setSerExped(Integer serExped) {
        this.serExped = serExped;
    }

    public Long getNumExped() {
        return numExped;
    }

    public void setNumExped(Long numExped) {
        this.numExped = numExped;
    }

    public String getCodOficinaPs() {
        return codOficinaPs;
    }

    public void setCodOficinaPs(String codOficinaPs) {
        this.codOficinaPs = codOficinaPs == null ? null : codOficinaPs.trim();
    }

    public String getTipLibroPs() {
        return tipLibroPs;
    }

    public void setTipLibroPs(String tipLibroPs) {
        this.tipLibroPs = tipLibroPs == null ? null : tipLibroPs.trim();
    }

    public Short getSerDocumPs() {
        return serDocumPs;
    }

    public void setSerDocumPs(Short serDocumPs) {
        this.serDocumPs = serDocumPs;
    }

    public Integer getNumDocumPs() {
        return numDocumPs;
    }

    public void setNumDocumPs(Integer numDocumPs) {
        this.numDocumPs = numDocumPs;
    }

    public String getCodOficinaSi() {
        return codOficinaSi;
    }

    public void setCodOficinaSi(String codOficinaSi) {
        this.codOficinaSi = codOficinaSi == null ? null : codOficinaSi.trim();
    }

    public String getTipLibroSi() {
        return tipLibroSi;
    }

    public void setTipLibroSi(String tipLibroSi) {
        this.tipLibroSi = tipLibroSi == null ? null : tipLibroSi.trim();
    }

    public Short getSerDocumSi() {
        return serDocumSi;
    }

    public void setSerDocumSi(Short serDocumSi) {
        this.serDocumSi = serDocumSi;
    }

    public Integer getNumDocumSi() {
        return numDocumSi;
    }

    public void setNumDocumSi(Integer numDocumSi) {
        this.numDocumSi = numDocumSi;
    }

    public String getIndOriginalFax() {
        return indOriginalFax;
    }

    public void setIndOriginalFax(String indOriginalFax) {
        this.indOriginalFax = indOriginalFax == null ? null : indOriginalFax.trim();
    }

    public String getCodOficinaResp() {
        return codOficinaResp;
    }

    public void setCodOficinaResp(String codOficinaResp) {
        this.codOficinaResp = codOficinaResp == null ? null : codOficinaResp.trim();
    }

    public String getTipLibroResp() {
        return tipLibroResp;
    }

    public void setTipLibroResp(String tipLibroResp) {
        this.tipLibroResp = tipLibroResp == null ? null : tipLibroResp.trim();
    }

    public Short getSerDocumResp() {
        return serDocumResp;
    }

    public void setSerDocumResp(Short serDocumResp) {
        this.serDocumResp = serDocumResp;
    }

    public Integer getNumDocumResp() {
        return numDocumResp;
    }

    public void setNumDocumResp(Integer numDocumResp) {
        this.numDocumResp = numDocumResp;
    }

    public Date getFecCorreo() {
        return fecCorreo;
    }

    public void setFecCorreo(Date fecCorreo) {
        this.fecCorreo = fecCorreo;
    }

    public Date getFecNotificacion() {
        return fecNotificacion;
    }

    public void setFecNotificacion(Date fecNotificacion) {
        this.fecNotificacion = fecNotificacion;
    }

    public String getIndResponder() {
        return indResponder;
    }

    public void setIndResponder(String indResponder) {
        this.indResponder = indResponder == null ? null : indResponder.trim();
    }

    public String getIndSistema() {
        return indSistema;
    }

    public void setIndSistema(String indSistema) {
        this.indSistema = indSistema == null ? null : indSistema.trim();
    }

    public String getCodOficinaOficio() {
        return codOficinaOficio;
    }

    public void setCodOficinaOficio(String codOficinaOficio) {
        this.codOficinaOficio = codOficinaOficio == null ? null : codOficinaOficio.trim();
    }

    public Short getSerOficio() {
        return serOficio;
    }

    public void setSerOficio(Short serOficio) {
        this.serOficio = serOficio;
    }

    public Integer getNumOficio() {
        return numOficio;
    }

    public void setNumOficio(Integer numOficio) {
        this.numOficio = numOficio;
    }

    public String getDesDocum() {
        return desDocum;
    }

    public void setDesDocum(String desDocum) {
        this.desDocum = desDocum == null ? null : desDocum.trim();
    }

    public Integer getRefDocum() {
        return refDocum;
    }

    public void setRefDocum(Integer refDocum) {
        this.refDocum = refDocum;
    }

    public String getIndFaltanExpeds() {
        return indFaltanExpeds;
    }

    public void setIndFaltanExpeds(String indFaltanExpeds) {
        this.indFaltanExpeds = indFaltanExpeds == null ? null : indFaltanExpeds.trim();
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

    public String getSubtipSolicitud() {
        return subtipSolicitud;
    }

    public void setSubtipSolicitud(String subtipSolicitud) {
        this.subtipSolicitud = subtipSolicitud == null ? null : subtipSolicitud.trim();
    }

    public Integer getNumAuxil1() {
        return numAuxil1;
    }

    public void setNumAuxil1(Integer numAuxil1) {
        this.numAuxil1 = numAuxil1;
    }

    public Date getFecRecepcion() {
        return fecRecepcion;
    }

    public void setFecRecepcion(Date fecRecepcion) {
        this.fecRecepcion = fecRecepcion;
    }

    public Date getFecRecepcionOrig() {
        return fecRecepcionOrig;
    }

    public void setFecRecepcionOrig(Date fecRecepcionOrig) {
        this.fecRecepcionOrig = fecRecepcionOrig;
    }

    public Integer getNumPaginas() {
        return numPaginas;
    }

    public void setNumPaginas(Integer numPaginas) {
        this.numPaginas = numPaginas;
    }

    public Integer getNumPagsMigradas() {
        return numPagsMigradas;
    }

    public void setNumPagsMigradas(Integer numPagsMigradas) {
        this.numPagsMigradas = numPagsMigradas;
    }

    public String getIndDepositoMatBiologico() {
        return indDepositoMatBiologico;
    }

    public void setIndDepositoMatBiologico(String indDepositoMatBiologico) {
        this.indDepositoMatBiologico = indDepositoMatBiologico == null ? null : indDepositoMatBiologico.trim();
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Short getCodOficinaReceptora() {
        return codOficinaReceptora;
    }

    public void setCodOficinaReceptora(Short codOficinaReceptora) {
        this.codOficinaReceptora = codOficinaReceptora;
    }

    public String getIndCapturaGlosa() {
        return indCapturaGlosa;
    }

    public void setIndCapturaGlosa(String indCapturaGlosa) {
        this.indCapturaGlosa = indCapturaGlosa == null ? null : indCapturaGlosa.trim();
    }

    public String getIndCapturaDatosProm() {
        return indCapturaDatosProm;
    }

    public void setIndCapturaDatosProm(String indCapturaDatosProm) {
        this.indCapturaDatosProm = indCapturaDatosProm == null ? null : indCapturaDatosProm.trim();
    }

    public String getIndCapturaDatos() {
        return indCapturaDatos;
    }

    public void setIndCapturaDatos(String indCapturaDatos) {
        this.indCapturaDatos = indCapturaDatos == null ? null : indCapturaDatos.trim();
    }
}