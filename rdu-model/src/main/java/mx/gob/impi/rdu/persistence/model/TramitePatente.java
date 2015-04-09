package mx.gob.impi.rdu.persistence.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import mx.gob.impi.rdu.dto.AnexosViewDto;

@SuppressWarnings("serial")
public class TramitePatente implements Serializable {

    private Long idTramitePatente;
    private Long idSubtipoSolicitud;
    private Date fechaDivulgacion;
    private String expDivisional;
    private Date fechaPresentacionExp;
    private String invencion;
    private String resumen;
    private String observaciones;
    private Long idEstatus;
    private Long idUsuarioFirmante;
    private String numeroExp;
    private Date fechaRecepcion;
    private Date fechaCaptura;
    private Long idUsuarioCaptura;
    private Date fechaEstatusActual;
    private Short indActivo;
    CatTiposolicitud tipoSol = new CatTiposolicitud();
    CatSubtiposolicitud subTipoSol = new CatSubtiposolicitud();
    private Pago pago;
    private String expDivisionalAnt;
    private String numSolPCT;
    private Date fechaSolPCT;
    private Date fechaPubPCT;
    private String numPubPCT;
    private String tipoPubPCT;
    private String faseSolPCT;
    private List<Persona> apoderados;
    private List<Persona> PersonasNot;
    private List<Persona> solicitantes;
    private List<Persona> inventores;
    private List<Prioridad> prioridades;
    private Date fechaDivPrevia;
    private boolean material_biologico;
    private boolean pub_anticipada;
    private Integer faseInternacional;
    private Integer hayMatBiologico;
    private Integer hayPubAnticipada;
    List<Anexos> anexos;
    private String[] foliosSeries;
    private List<ImagenDibujo> imagenes = new ArrayList<ImagenDibujo>();
    private List<Reivindicacion> reivindicaciones = new ArrayList<Reivindicacion>();
    private String tipoExpediente;    
    private Date sysDate;
    private String folioFirma;
    /** Parametros para descripcion en texto **/
    private String preambulo;
    private String descripcionVistas;
    private String textoAdicional;
    private String idIdiomaDescripcion;
    private String idiomaReivindicacion;
    private String idiomaResumen;
    private List<AnexosViewDto> lstAnexosDocumento;
    //-------
    private Long idDatoscontacto;
    private Long idDomicilio;
    private Domicilio domicilioObj;
    private Datoscontacto datosContacto = new Datoscontacto();

    
   //IJZA Se agregan variables para tipo de busqueda Nacional/Internacional SIT 18/02/2015
    
    private String cobertura;

    public List<Anexos> getLstPrio() {
        return lstPrio;
    }

    public void setLstPrio(List<Anexos> lstPrio) {
        this.lstPrio = lstPrio;
    }
    private List<Anexos> lstPrio;
   // private Integer paginasDescripcion;

    public List<AnexosViewDto> getLstAnexosDocumento() {
        return lstAnexosDocumento;
    }

    public void setLstAnexosDocumento(List<AnexosViewDto> lstAnexosDocumento) {
        this.lstAnexosDocumento = lstAnexosDocumento;
    }

    public String getFolioFirma() {
        return folioFirma;
    }

    public void setFolioFirma(String folioFirma) {
        this.folioFirma = folioFirma;
    }
    
    public Date getSysDate() {
        return sysDate;
    }

    public void setSysDate(Date sysDate) {
        this.sysDate = sysDate;
    }   

    public String getTipoExpediente() {
        return tipoExpediente;
    }

    public void setTipoExpediente(String tipoExpediente) {
        this.tipoExpediente = tipoExpediente;
    }

    public String[] getFoliosSeries() {
        return foliosSeries;
    }

    public void setFoliosSeries(String[] foliosSeries) {
        this.foliosSeries = foliosSeries;
    }

    public Integer getHayMatBiologico() {
        return hayMatBiologico;
    }

    public void setHayMatBiologico(Integer hayMatBiologico) {
        this.hayMatBiologico = hayMatBiologico;
    }

    public Integer getHayPubAnticipada() {
        return hayPubAnticipada;
    }

    public void setHayPubAnticipada(Integer hayPubAnticipada) {
        this.hayPubAnticipada = hayPubAnticipada;
    }

    public Integer getFaseInternacional() {
        return faseInternacional;
    }

    public void setFaseInternacional(Integer faseInternacional) {
        this.faseInternacional = faseInternacional;
    }

    public boolean isMaterial_biologico() {
        return material_biologico;
    }

    public void setMaterial_biologico(boolean material_biologico) {
        this.material_biologico = material_biologico;
    }

    public boolean isPub_anticipada() {
        return pub_anticipada;
    }

    public void setPub_anticipada(boolean pub_anticipada) {
        this.pub_anticipada = pub_anticipada;
    }

    public List<Persona> getInventores() {
        return inventores;
    }

    public void setInventores(List<Persona> inventores) {
        this.inventores = inventores;
    }

    public List<Prioridad> getPrioridades() {
        return prioridades;
    }

    public void setPrioridades(List<Prioridad> prioridades) {
        this.prioridades = prioridades;
    }

    public List<Persona> getSolicitantes() {
        return solicitantes;
    }

    public void setSolicitantes(List<Persona> solicitantes) {
        this.solicitantes = solicitantes;
    }

    public Date getFechaDivPrevia() {
        return fechaDivPrevia;
    }

    public void setFechaDivPrevia(Date fechaDivPrevia) {
        this.fechaDivPrevia = fechaDivPrevia;
    }

    public List<Anexos> getAnexos() {
        return anexos;
    }

    public void setAnexos(List<Anexos> anexos) {
        this.anexos = anexos;
    }

    public String getFaseSolPCT() {
        return faseSolPCT;
    }

    public void setFaseSolPCT(String faseSolPCT) {
        this.faseSolPCT = faseSolPCT;
    }

    public String getTipoPubPCT() {
        return tipoPubPCT;
    }

    public void setTipoPubPCT(String tipoPubPCT) {
        this.tipoPubPCT = tipoPubPCT;
    }

    public String getExpDivisionalAnt() {
        return expDivisionalAnt;
    }

    public void setExpDivisionalAnt(String expDivisionalAnt) {
        this.expDivisionalAnt = expDivisionalAnt;
    }

    public Date getFechaPubPCT() {
        return fechaPubPCT;
    }

    public void setFechaPubPCT(Date fechaPubPCT) {
        this.fechaPubPCT = fechaPubPCT;
    }

    public Date getFechaSolPCT() {
        return fechaSolPCT;
    }

    public void setFechaSolPCT(Date fechaSolPCT) {
        this.fechaSolPCT = fechaSolPCT;
    }

    public String getNumPubPCT() {
        return numPubPCT;
    }

    public void setNumPubPCT(String numPubPCT) {
        this.numPubPCT = numPubPCT;
    }

    public String getNumSolPCT() {
        return numSolPCT;
    }

    public void setNumSolPCT(String numSolPCT) {
        this.numSolPCT = numSolPCT;
    }

    public CatSubtiposolicitud getSubTipoSol() {
        return subTipoSol;
    }

    public void setSubTipoSol(CatSubtiposolicitud subTipoSol) {
        this.subTipoSol = subTipoSol;
    }

    public CatTiposolicitud getTipoSol() {
        return tipoSol;
    }

    public void setTipoSol(CatTiposolicitud tipoSol) {
        this.tipoSol = tipoSol;
    }

    public Long getIdTramitePatente() {
        return idTramitePatente;
    }

    public void setIdTramitePatente(Long idTramitePatente) {
        this.idTramitePatente = idTramitePatente;
    }

    public Long getIdSubtipoSolicitud() {
        return idSubtipoSolicitud;
    }

    public void setIdSubtipoSolicitud(Long idSubtipoSolicitud) {
        this.idSubtipoSolicitud = idSubtipoSolicitud;
    }

    public Date getFechaDivulgacion() {
        return fechaDivulgacion;
    }

    public void setFechaDivulgacion(Date fechaDivulgacion) {
        this.fechaDivulgacion = fechaDivulgacion;
    }

    public String getExpDivisional() {
        return expDivisional;
    }

    public void setExpDivisional(String expDivisional) {
        this.expDivisional = expDivisional == null ? null : expDivisional.trim();
    }

    public Date getFechaPresentacionExp() {
        return fechaPresentacionExp;
    }

    public void setFechaPresentacionExp(Date fechaPresentacionExp) {
        this.fechaPresentacionExp = fechaPresentacionExp;
    }

    public String getInvencion() {
        return invencion;
    }

    public void setInvencion(String invencion) {
        this.invencion = invencion == null ? null : invencion.trim();
    }

    public String getResumen() {
        return resumen;
    }

    public void setResumen(String resumen) {
        this.resumen = resumen == null ? null : resumen.trim();
    }

    public String getObservaciones() {
        return observaciones;
    }

    public void setObservaciones(String observaciones) {
        this.observaciones = observaciones == null ? null : observaciones.trim();
    }

    public Long getIdEstatus() {
        return idEstatus;
    }

    public void setIdEstatus(Long idEstatus) {
        this.idEstatus = idEstatus;
    }

    public Long getIdUsuarioFirmante() {
        return idUsuarioFirmante;
    }

    public void setIdUsuarioFirmante(Long idUsuarioFirmante) {
        this.idUsuarioFirmante = idUsuarioFirmante;
    }

    public String getNumeroExp() {
        return numeroExp;
    }

    public void setNumeroExp(String numeroExp) {
        this.numeroExp = numeroExp == null ? null : numeroExp.trim();
    }

    public Date getFechaRecepcion() {
        return fechaRecepcion;
    }

    public void setFechaRecepcion(Date fechaRecepcion) {
        this.fechaRecepcion = fechaRecepcion;
    }

    public Date getFechaCaptura() {
        return fechaCaptura;
    }

    public void setFechaCaptura(Date fechaCaptura) {
        this.fechaCaptura = fechaCaptura;
    }

    public Long getIdUsuarioCaptura() {
        return idUsuarioCaptura;
    }

    public void setIdUsuarioCaptura(Long idUsuarioCaptura) {
        this.idUsuarioCaptura = idUsuarioCaptura;
    }

    public Date getFechaEstatusActual() {
        return fechaEstatusActual;
    }

    public void setFechaEstatusActual(Date fechaEstatusActual) {
        this.fechaEstatusActual = fechaEstatusActual;
    }

    public Short getIndActivo() {
        return indActivo;
    }

    public void setIndActivo(Short indActivo) {
        this.indActivo = indActivo;
    }

    public List<Persona> getPersonasNot() {
        return PersonasNot;
    }

    public void setPersonasNot(List<Persona> PersonasNot) {
        this.PersonasNot = PersonasNot;
    }

    public List<Persona> getApoderados() {
        return apoderados;
    }

    public void setApoderados(List<Persona> apoderados) {
        this.apoderados = apoderados;
    }

    public Pago getPago() {
        return pago;
    }

    public void setPago(Pago pago) {
        this.pago = pago;
    }

    public List<ImagenDibujo> getImagenes() {
        return imagenes;
    }

    public void setImagenes(List<ImagenDibujo> imagenes) {
        this.imagenes = imagenes;
    }

    public List<Reivindicacion> getReivindicaciones() {
        return reivindicaciones;
    }

    public void setReivindicaciones(List<Reivindicacion> reivindicaciones) {
        this.reivindicaciones = reivindicaciones;
    }

    public String getPreambulo() {
        return preambulo;
    }

    public void setPreambulo(String preambulo) {
        this.preambulo = preambulo;
    }

    public String getDescripcionVistas() {
        return descripcionVistas;
    }

    public void setDescripcionVistas(String descripcionVistas) {
        this.descripcionVistas = descripcionVistas;
    }

    public String getTextoAdicional() {
        return textoAdicional;
    }

    public void setTextoAdicional(String textoAdicional) {
        this.textoAdicional = textoAdicional;
    }
    
    public String getIdIdiomaDescripcion() {
        return idIdiomaDescripcion;
    }

    public void setIdIdiomaDescripcion(String idIdiomaDescripcion) {
        this.idIdiomaDescripcion = idIdiomaDescripcion;
    }

    public String getIdiomaReivindicacion() {
        return idiomaReivindicacion;
    }

    public void setIdiomaReivindicacion(String idiomaReivindicacion) {
        this.idiomaReivindicacion = idiomaReivindicacion;
    }

    public String getIdiomaResumen() {
        return idiomaResumen;
    }

    public void setIdiomaResumen(String idiomaResumen) {
        this.idiomaResumen = idiomaResumen;
    }
    
//    public Integer getPaginasDescripcion() {
//        return paginasDescripcion;
//    }

//    public void setPaginasDescripcion(Integer paginasDescripcion) {
//        this.paginasDescripcion = paginasDescripcion;
//    }

    public Long getIdDatoscontacto() {
        return idDatoscontacto;
    }

    public void setIdDatoscontacto(Long idDatoscontacto) {
        this.idDatoscontacto = idDatoscontacto;
    }

    public Long getIdDomicilio() {
        return idDomicilio;
    }

    public void setIdDomicilio(Long idDomicilio) {
        this.idDomicilio = idDomicilio;
    }

    public Domicilio getDomicilioObj() {
        return domicilioObj;
    }

    public void setDomicilioObj(Domicilio domicilioObj) {
        this.domicilioObj = domicilioObj;
    }

    public Datoscontacto getDatosContacto() {
        return datosContacto;
    }

    public void setDatosContacto(Datoscontacto datosContacto) {
        this.datosContacto = datosContacto;
    }
    
 
    public String getCobertura() {
        return cobertura;
}


    public void setCobertura(String cobertura) {
        this.cobertura = cobertura;
    }
   
}
