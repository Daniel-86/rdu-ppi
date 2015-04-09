package mx.gob.impi.sagpat.persistence.model;

import java.io.Serializable;
import java.util.Date;

public class Solicitud implements Serializable {
    private String codOficina;

    private Long numExped;

    private Integer serExped;

    private String tipExped;

    private Integer rowVersion;

    private String tipLibro;

    private Integer serDocum;

    private Long numDocum;

    private Date fecRecepcion;

    private Date fecPresentacion;

    private Date fec1erPrioridad;

    private Date fecDivulgacion;

    private Date fecSolPublAnti;

    private Date fecPublicacion;

    private String codGacetaPubl;

    private Short codLey;

    private Integer numFolioArt2t;

    private Date fecArt2t;

    private Integer numFolioArt10t;

    private Date fecArt10t;

    private Integer numConcesion;

    private Date fecConcesion;

    private String indConcesion;

    private Date fecVigencia;

    private Date fecVencimiento;

    private String titulo;

    private String tipSolicitud;

    private String subtipSolicitud;

    private String tipExpedDividida;

    private Integer serExpedDividida;

    private Long numExpedDividida;

    private Long codPersonaTit1;

    private Long secDireccionTit1;

    private Long codPersonaPro;

    private Long secDireccionPro;

    private Short tipSolicitante;

    private String indTitulInven;

    private String codOficinaRgp;

    private String tipLibroRgp;

    private Short serDocumRgp;

    private Integer numDocumRgp;

    private Integer userIdCapt;

    private Date fecCapt;

    private String idSolicitudPct;

    private Date fecSolicitudPct;

    private String codPaisPublPct;

    private String idPublPct;

    private String tipPublPct;

    private Date fecPublPct;

    private Short fasePct;

    private String tituloIngles;

    private String obsPubl;

    private String fojasDescripcion;

    private String fojasReivindicaciones;

    private String codOficinaDividida;

    private String oficinaRegional;

    private Integer userIdRecepcion;

    private Short codOficinaReceptora;

    private Long numRgp;
    private Persona titularPrincipal;

    public Persona getTitularPrincipal() {
        return titularPrincipal;
    }

    public void setTitularPrincipal(Persona titularPrincipal) {
        this.titularPrincipal = titularPrincipal;
    }
    

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

    public String getTipLibro() {
        return tipLibro;
    }

    public void setTipLibro(String tipLibro) {
        this.tipLibro = tipLibro == null ? null : tipLibro.trim();
    }

    public Integer getSerDocum() {
        return serDocum;
    }

    public void setSerDocum(Integer serDocum) {
        this.serDocum = serDocum;
    }

    public Long getNumDocum() {
        return numDocum;
    }

    public void setNumDocum(Long numDocum) {
        this.numDocum = numDocum;
    }

    public Date getFecRecepcion() {
        return fecRecepcion;
    }

    public void setFecRecepcion(Date fecRecepcion) {
        this.fecRecepcion = fecRecepcion;
    }

    public Date getFecPresentacion() {
        return fecPresentacion;
    }

    public void setFecPresentacion(Date fecPresentacion) {
        this.fecPresentacion = fecPresentacion;
    }

    public Date getFec1erPrioridad() {
        return fec1erPrioridad;
    }

    public void setFec1erPrioridad(Date fec1erPrioridad) {
        this.fec1erPrioridad = fec1erPrioridad;
    }

    public Date getFecDivulgacion() {
        return fecDivulgacion;
    }

    public void setFecDivulgacion(Date fecDivulgacion) {
        this.fecDivulgacion = fecDivulgacion;
    }

    public Date getFecSolPublAnti() {
        return fecSolPublAnti;
    }

    public void setFecSolPublAnti(Date fecSolPublAnti) {
        this.fecSolPublAnti = fecSolPublAnti;
    }

    public Date getFecPublicacion() {
        return fecPublicacion;
    }

    public void setFecPublicacion(Date fecPublicacion) {
        this.fecPublicacion = fecPublicacion;
    }

    public String getCodGacetaPubl() {
        return codGacetaPubl;
    }

    public void setCodGacetaPubl(String codGacetaPubl) {
        this.codGacetaPubl = codGacetaPubl == null ? null : codGacetaPubl.trim();
    }

    public Short getCodLey() {
        return codLey;
    }

    public void setCodLey(Short codLey) {
        this.codLey = codLey;
    }

    public Integer getNumFolioArt2t() {
        return numFolioArt2t;
    }

    public void setNumFolioArt2t(Integer numFolioArt2t) {
        this.numFolioArt2t = numFolioArt2t;
    }

    public Date getFecArt2t() {
        return fecArt2t;
    }

    public void setFecArt2t(Date fecArt2t) {
        this.fecArt2t = fecArt2t;
    }

    public Integer getNumFolioArt10t() {
        return numFolioArt10t;
    }

    public void setNumFolioArt10t(Integer numFolioArt10t) {
        this.numFolioArt10t = numFolioArt10t;
    }

    public Date getFecArt10t() {
        return fecArt10t;
    }

    public void setFecArt10t(Date fecArt10t) {
        this.fecArt10t = fecArt10t;
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

    public String getIndConcesion() {
        return indConcesion;
    }

    public void setIndConcesion(String indConcesion) {
        this.indConcesion = indConcesion == null ? null : indConcesion.trim();
    }

    public Date getFecVigencia() {
        return fecVigencia;
    }

    public void setFecVigencia(Date fecVigencia) {
        this.fecVigencia = fecVigencia;
    }

    public Date getFecVencimiento() {
        return fecVencimiento;
    }

    public void setFecVencimiento(Date fecVencimiento) {
        this.fecVencimiento = fecVencimiento;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo == null ? null : titulo.trim();
    }

    public String getTipSolicitud() {
        return tipSolicitud;
    }

    public void setTipSolicitud(String tipSolicitud) {
        this.tipSolicitud = tipSolicitud == null ? null : tipSolicitud.trim();
    }

    public String getSubtipSolicitud() {
        return subtipSolicitud;
    }

    public void setSubtipSolicitud(String subtipSolicitud) {
        this.subtipSolicitud = subtipSolicitud == null ? null : subtipSolicitud.trim();
    }

    public String getTipExpedDividida() {
        return tipExpedDividida;
    }

    public void setTipExpedDividida(String tipExpedDividida) {
        this.tipExpedDividida = tipExpedDividida == null ? null : tipExpedDividida.trim();
    }

    public Integer getSerExpedDividida() {
        return serExpedDividida;
    }

    public void setSerExpedDividida(Integer serExpedDividida) {
        this.serExpedDividida = serExpedDividida;
    }

    public Long getNumExpedDividida() {
        return numExpedDividida;
    }

    public void setNumExpedDividida(Long numExpedDividida) {
        this.numExpedDividida = numExpedDividida;
    }

    public Long getCodPersonaTit1() {
        return codPersonaTit1;
    }

    public void setCodPersonaTit1(Long codPersonaTit1) {
        this.codPersonaTit1 = codPersonaTit1;
    }

    public Long getSecDireccionTit1() {
        return secDireccionTit1;
    }

    public void setSecDireccionTit1(Long secDireccionTit1) {
        this.secDireccionTit1 = secDireccionTit1;
    }

    public Long getCodPersonaPro() {
        return codPersonaPro;
    }

    public void setCodPersonaPro(Long codPersonaPro) {
        this.codPersonaPro = codPersonaPro;
    }

    public Long getSecDireccionPro() {
        return secDireccionPro;
    }

    public void setSecDireccionPro(Long secDireccionPro) {
        this.secDireccionPro = secDireccionPro;
    }

    public Short getTipSolicitante() {
        return tipSolicitante;
    }

    public void setTipSolicitante(Short tipSolicitante) {
        this.tipSolicitante = tipSolicitante;
    }

    public String getIndTitulInven() {
        return indTitulInven;
    }

    public void setIndTitulInven(String indTitulInven) {
        this.indTitulInven = indTitulInven == null ? null : indTitulInven.trim();
    }

    public String getCodOficinaRgp() {
        return codOficinaRgp;
    }

    public void setCodOficinaRgp(String codOficinaRgp) {
        this.codOficinaRgp = codOficinaRgp == null ? null : codOficinaRgp.trim();
    }

    public String getTipLibroRgp() {
        return tipLibroRgp;
    }

    public void setTipLibroRgp(String tipLibroRgp) {
        this.tipLibroRgp = tipLibroRgp == null ? null : tipLibroRgp.trim();
    }

    public Short getSerDocumRgp() {
        return serDocumRgp;
    }

    public void setSerDocumRgp(Short serDocumRgp) {
        this.serDocumRgp = serDocumRgp;
    }

    public Integer getNumDocumRgp() {
        return numDocumRgp;
    }

    public void setNumDocumRgp(Integer numDocumRgp) {
        this.numDocumRgp = numDocumRgp;
    }

    public Integer getUserIdCapt() {
        return userIdCapt;
    }

    public void setUserIdCapt(Integer userIdCapt) {
        this.userIdCapt = userIdCapt;
    }

    public Date getFecCapt() {
        return fecCapt;
    }

    public void setFecCapt(Date fecCapt) {
        this.fecCapt = fecCapt;
    }

    public String getIdSolicitudPct() {
        return idSolicitudPct;
    }

    public void setIdSolicitudPct(String idSolicitudPct) {
        this.idSolicitudPct = idSolicitudPct == null ? null : idSolicitudPct.trim();
    }

    public Date getFecSolicitudPct() {
        return fecSolicitudPct;
    }

    public void setFecSolicitudPct(Date fecSolicitudPct) {
        this.fecSolicitudPct = fecSolicitudPct;
    }

    public String getCodPaisPublPct() {
        return codPaisPublPct;
    }

    public void setCodPaisPublPct(String codPaisPublPct) {
        this.codPaisPublPct = codPaisPublPct == null ? null : codPaisPublPct.trim();
    }

    public String getIdPublPct() {
        return idPublPct;
    }

    public void setIdPublPct(String idPublPct) {
        this.idPublPct = idPublPct == null ? null : idPublPct.trim();
    }

    public String getTipPublPct() {
        return tipPublPct;
    }

    public void setTipPublPct(String tipPublPct) {
        this.tipPublPct = tipPublPct == null ? null : tipPublPct.trim();
    }

    public Date getFecPublPct() {
        return fecPublPct;
    }

    public void setFecPublPct(Date fecPublPct) {
        this.fecPublPct = fecPublPct;
    }

    public Short getFasePct() {
        return fasePct;
    }

    public void setFasePct(Short fasePct) {
        this.fasePct = fasePct;
    }

    public String getTituloIngles() {
        return tituloIngles;
    }

    public void setTituloIngles(String tituloIngles) {
        this.tituloIngles = tituloIngles == null ? null : tituloIngles.trim();
    }

    public String getObsPubl() {
        return obsPubl;
    }

    public void setObsPubl(String obsPubl) {
        this.obsPubl = obsPubl == null ? null : obsPubl.trim();
    }

    public String getFojasDescripcion() {
        return fojasDescripcion;
    }

    public void setFojasDescripcion(String fojasDescripcion) {
        this.fojasDescripcion = fojasDescripcion == null ? null : fojasDescripcion.trim();
    }

    public String getFojasReivindicaciones() {
        return fojasReivindicaciones;
    }

    public void setFojasReivindicaciones(String fojasReivindicaciones) {
        this.fojasReivindicaciones = fojasReivindicaciones == null ? null : fojasReivindicaciones.trim();
    }

    public String getCodOficinaDividida() {
        return codOficinaDividida;
    }

    public void setCodOficinaDividida(String codOficinaDividida) {
        this.codOficinaDividida = codOficinaDividida == null ? null : codOficinaDividida.trim();
    }

    public String getOficinaRegional() {
        return oficinaRegional;
    }

    public void setOficinaRegional(String oficinaRegional) {
        this.oficinaRegional = oficinaRegional == null ? null : oficinaRegional.trim();
    }

    public Integer getUserIdRecepcion() {
        return userIdRecepcion;
    }

    public void setUserIdRecepcion(Integer userIdRecepcion) {
        this.userIdRecepcion = userIdRecepcion;
    }

    public Short getCodOficinaReceptora() {
        return codOficinaReceptora;
    }

    public void setCodOficinaReceptora(Short codOficinaReceptora) {
        this.codOficinaReceptora = codOficinaReceptora;
    }

    public Long getNumRgp() {
        return numRgp;
    }

    public void setNumRgp(Long numRgp) {
        this.numRgp = numRgp;
    }
}