package mx.gob.impi.sagpat.persistence.model;

import java.util.Date;

public class Expediente implements java.io.Serializable { 
    private String codOficina;

    private Long numExped;

    private Integer serExped;

    private String tipExped;

    private Integer rowVersion;

    private Short codOrigenExpediente;

    private String tipTramite;

    private Integer numTramite;

    private String tipSolicitud;

    private String tipLibro;

    private Long serDocum;

    private Long numDocum;

    private String indSoloFax;

    private String tipExpedViejaFj;

    private Short serExpedViejaFj;

    private Integer numExpedViejaFj;

    private String tipExpedNuevaFj;

    private Short serExpedNuevaFj;

    private Integer numExpedNuevaFj;

    private Date fecCambioFj;

    private Integer userIdCambioFj;

    private Short codLey;

    private Integer numConcesion;

    private Date fecConcesion;

    private String titulo;

    private String subtipSolicitud;

    private Long codPersonaTit1;

    private Long codPersonaPro;

    private Date fecPresentacion;

    private String codOficinaViejaFj;

    private String codOficinaNuevaFj;

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

    public Short getCodOrigenExpediente() {
        return codOrigenExpediente;
    }

    public void setCodOrigenExpediente(Short codOrigenExpediente) {
        this.codOrigenExpediente = codOrigenExpediente;
    }

    public String getTipTramite() {
        return tipTramite;
    }

    public void setTipTramite(String tipTramite) {
        this.tipTramite = tipTramite == null ? null : tipTramite.trim();
    }

    public Integer getNumTramite() {
        return numTramite;
    }

    public void setNumTramite(Integer numTramite) {
        this.numTramite = numTramite;
    }

    public String getTipSolicitud() {
        return tipSolicitud;
    }

    public void setTipSolicitud(String tipSolicitud) {
        this.tipSolicitud = tipSolicitud == null ? null : tipSolicitud.trim();
    }

    public String getTipLibro() {
        return tipLibro;
    }

    public void setTipLibro(String tipLibro) {
        this.tipLibro = tipLibro == null ? null : tipLibro.trim();
    }

    public Long getSerDocum() {
        return serDocum;
    }

    public void setSerDocum(Long serDocum) {
        this.serDocum = serDocum;
    }

    public Long getNumDocum() {
        return numDocum;
    }

    public void setNumDocum(Long numDocum) {
        this.numDocum = numDocum;
    }

    public String getIndSoloFax() {
        return indSoloFax;
    }

    public void setIndSoloFax(String indSoloFax) {
        this.indSoloFax = indSoloFax == null ? null : indSoloFax.trim();
    }

    public String getTipExpedViejaFj() {
        return tipExpedViejaFj;
    }

    public void setTipExpedViejaFj(String tipExpedViejaFj) {
        this.tipExpedViejaFj = tipExpedViejaFj == null ? null : tipExpedViejaFj.trim();
    }

    public Short getSerExpedViejaFj() {
        return serExpedViejaFj;
    }

    public void setSerExpedViejaFj(Short serExpedViejaFj) {
        this.serExpedViejaFj = serExpedViejaFj;
    }

    public Integer getNumExpedViejaFj() {
        return numExpedViejaFj;
    }

    public void setNumExpedViejaFj(Integer numExpedViejaFj) {
        this.numExpedViejaFj = numExpedViejaFj;
    }

    public String getTipExpedNuevaFj() {
        return tipExpedNuevaFj;
    }

    public void setTipExpedNuevaFj(String tipExpedNuevaFj) {
        this.tipExpedNuevaFj = tipExpedNuevaFj == null ? null : tipExpedNuevaFj.trim();
    }

    public Short getSerExpedNuevaFj() {
        return serExpedNuevaFj;
    }

    public void setSerExpedNuevaFj(Short serExpedNuevaFj) {
        this.serExpedNuevaFj = serExpedNuevaFj;
    }

    public Integer getNumExpedNuevaFj() {
        return numExpedNuevaFj;
    }

    public void setNumExpedNuevaFj(Integer numExpedNuevaFj) {
        this.numExpedNuevaFj = numExpedNuevaFj;
    }

    public Date getFecCambioFj() {
        return fecCambioFj;
    }

    public void setFecCambioFj(Date fecCambioFj) {
        this.fecCambioFj = fecCambioFj;
    }

    public Integer getUserIdCambioFj() {
        return userIdCambioFj;
    }

    public void setUserIdCambioFj(Integer userIdCambioFj) {
        this.userIdCambioFj = userIdCambioFj;
    }

    public Short getCodLey() {
        return codLey;
    }

    public void setCodLey(Short codLey) {
        this.codLey = codLey;
    }

    public Integer getNumConcesion() {
        return numConcesion;
    }

    public void setNumConcesion(Integer numConcesion) {
        this.numConcesion = numConcesion;
    }

    public Date getFecConcesion() {
        return fecConcesion;
    }

    public void setFecConcesion(Date fecConcesion) {
        this.fecConcesion = fecConcesion;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo == null ? null : titulo.trim();
    }

    public String getSubtipSolicitud() {
        return subtipSolicitud;
    }

    public void setSubtipSolicitud(String subtipSolicitud) {
        this.subtipSolicitud = subtipSolicitud == null ? null : subtipSolicitud.trim();
    }

    public Long getCodPersonaTit1() {
        return codPersonaTit1;
    }

    public void setCodPersonaTit1(Long codPersonaTit1) {
        this.codPersonaTit1 = codPersonaTit1;
    }

    public Long getCodPersonaPro() {
        return codPersonaPro;
    }

    public void setCodPersonaPro(Long codPersonaPro) {
        this.codPersonaPro = codPersonaPro;
    }

    public Date getFecPresentacion() {
        return fecPresentacion;
    }

    public void setFecPresentacion(Date fecPresentacion) {
        this.fecPresentacion = fecPresentacion;
    }

    public String getCodOficinaViejaFj() {
        return codOficinaViejaFj;
    }

    public void setCodOficinaViejaFj(String codOficinaViejaFj) {
        this.codOficinaViejaFj = codOficinaViejaFj == null ? null : codOficinaViejaFj.trim();
    }

    public String getCodOficinaNuevaFj() {
        return codOficinaNuevaFj;
    }

    public void setCodOficinaNuevaFj(String codOficinaNuevaFj) {
        this.codOficinaNuevaFj = codOficinaNuevaFj == null ? null : codOficinaNuevaFj.trim();
    }
}