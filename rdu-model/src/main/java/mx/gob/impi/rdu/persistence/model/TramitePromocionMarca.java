package mx.gob.impi.rdu.persistence.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 *
 * @author JBMM
 */
public class TramitePromocionMarca implements Serializable {

    private Long idTramitePromocionMarca;
    private long idSubtiposolicitud;
    private CatSubtiposolicitud subTipoSolicitud = new CatSubtiposolicitud();
    private Date fechaCaptura;
    private Date fechaEstatusActual;
    private Integer idEstatus;
    private Short indActivo;
    private Integer idUsuariocaptura;
    private BigDecimal idDomicilioNotificacion;
    private Date vigenciaConvenio;
    private Short ejercerProteccion;
    private Short seProtejeTodosProductos;
    private Integer idDenominacion;
    private Long idUsuarioFirmante;
    private Domicilionotificacion notificacion;
    private EstablecimientoXPromomarca establecimiento;
    List<TramitePersona> listaTramitePersona;
    List<NumerosSolicitud> numeros;    
    private String folio;
    private String expediente;
    private Date fechaSysDate;
    private String codBarras;
    private String nombreFirmante;
    private Pago pago;
    private String serieSolicante;

    public String getSerieSolicante() {
        return serieSolicante;
    }
    
    public void setSerieSolicante(String serieSolicante) {
        this.serieSolicante = serieSolicante;
    }  

    public Pago getPago() {
        return pago;
    }

    public void setPago(Pago pago) {
        this.pago = pago;
    }   

    public String getNombreFirmante() {
        return nombreFirmante;
    }

    public void setNombreFirmante(String nombreFirmante) {
        this.nombreFirmante = nombreFirmante;
    }
    
    
      List<Anexos> anexos= new ArrayList<Anexos>();

    public List<Anexos> getAnexos() {
        return anexos;
    }

    public void setAnexos(List<Anexos> anexos) {
        this.anexos = anexos;
    }    

    public String getCodBarras() {
        return codBarras;
    }

    public void setCodBarras(String codBarras) {
        this.codBarras = codBarras;
    }   

    public CatSubtiposolicitud getSubTipoSolicitud() {
        return subTipoSolicitud;
    }

    public void setSubTipoSolicitud(CatSubtiposolicitud subTipoSolicitud) {
        this.subTipoSolicitud = subTipoSolicitud;
    }

    public List<NumerosSolicitud> getNumeros() {
        return numeros;
    }

    public void setNumeros(List<NumerosSolicitud> numeros) {
        this.numeros = numeros;
    }

    public Long getIdTramitePromocionMarca() {
        return idTramitePromocionMarca;
    }

    public void setIdTramitePromocionMarca(Long idTramitePromocionMarca) {
        this.idTramitePromocionMarca = idTramitePromocionMarca;
    }

    public long getIdSubtiposolicitud() {
        return idSubtiposolicitud;
    }

    public void setIdSubtiposolicitud(long idSubtiposolicitud) {
        this.idSubtiposolicitud = idSubtiposolicitud;
    }

    public Date getFechaCaptura() {
        return fechaCaptura;
    }

    public void setFechaCaptura(Date fechaCaptura) {
        this.fechaCaptura = fechaCaptura;
    }

    public Date getFechaEstatusActual() {
        return fechaEstatusActual;
    }

    public void setFechaEstatusActual(Date fechaEstatusActual) {
        this.fechaEstatusActual = fechaEstatusActual;
    }

    public Integer getIdEstatus() {
        return idEstatus;
    }

    public void setIdEstatus(Integer idEstatus) {
        this.idEstatus = idEstatus;
    }

    public Short getIndActivo() {
        return indActivo;
    }

    public void setIndActivo(Short indActivo) {
        this.indActivo = indActivo;
    }

    public Integer getIdUsuariocaptura() {
        return idUsuariocaptura;
    }

    public void setIdUsuariocaptura(Integer idUsuariocaptura) {
        this.idUsuariocaptura = idUsuariocaptura;
    }

    public BigDecimal getIdDomicilioNotificacion() {
        return idDomicilioNotificacion;
    }

    public void setIdDomicilioNotificacion(BigDecimal idDomicilioNotificacion) {
        this.idDomicilioNotificacion = idDomicilioNotificacion;
    }

    public Date getVigenciaConvenio() {
        return vigenciaConvenio;
    }

    public void setVigenciaConvenio(Date vigenciaConvenio) {
        this.vigenciaConvenio = vigenciaConvenio;
    }

    public Short getEjercerProteccion() {
        return ejercerProteccion;
    }

    public void setEjercerProteccion(Short ejercerProteccion) {
        this.ejercerProteccion = ejercerProteccion;
    }

    public Short getSeProtejeTodosProductos() {
        return seProtejeTodosProductos;
    }

    public void setSeProtejeTodosProductos(Short seProtejeTodosProductos) {
        this.seProtejeTodosProductos = seProtejeTodosProductos;
    }

    public Domicilionotificacion getNotificacion() {
        return notificacion;
    }

    public void setNotificacion(Domicilionotificacion notificacion) {
        this.notificacion = notificacion;
    }

    public List<TramitePersona> getListaTramitePersona() {
        return listaTramitePersona;
    }

    public void setListaTramitePersona(List<TramitePersona> listaTramitePersona) {
        this.listaTramitePersona = listaTramitePersona;
    }

    public void setIdDenominacion(Integer idDenominacion) {
        this.idDenominacion = idDenominacion;
    }

    public Integer getIdDenominacion() {
        return idDenominacion;
    }

    public EstablecimientoXPromomarca getEstablecimiento() {
        return establecimiento;
    }

    public void setEstablecimiento(EstablecimientoXPromomarca establecimiento) {
        this.establecimiento = establecimiento;
    }

    public Long getIdUsuarioFirmante() {
        return idUsuarioFirmante;
    }

    public void setIdUsuarioFirmante(Long idUsuarioFirmante) {
        this.idUsuarioFirmante = idUsuarioFirmante;
    }

    public String getExpediente() {
        return expediente;
    }

    public String getFolio() {
        return folio;
    }

    public void setExpediente(String expediente) {
        this.expediente = expediente;
    }

    public void setFolio(String folio) {
        this.folio = folio;
    }

    public Date getFechaSysDate() {
        return fechaSysDate;
    }

    public void setFechaSysDate(Date fechaSysDate) {
        this.fechaSysDate = fechaSysDate;
    }    
    
    
}
