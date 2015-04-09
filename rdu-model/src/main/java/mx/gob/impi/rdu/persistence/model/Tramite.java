package mx.gob.impi.rdu.persistence.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

@SuppressWarnings("serial")
public class Tramite implements Serializable {

    private Long idTramite;
    private Long idSubtiposolicitud;
    private CatSubtiposolicitud subTipoSolicitud;
    private Long idTipomarca;
    private BigDecimal marcacolectiva;
    private String descripcionsignodistintivo;
    private Date fechaprimeruso;
    private Short nousadofecha;
    private String leyendasfigurasreservables;
    private Long idDomicilionotificacion;
    private Domicilionotificacion domicilioNotificacion;
    private Long idPaisprioridad;
    private Pais paisPrioridad;
    private Date fechapresentacionextranjero;
    private Long idEstatus;
    private CatEstatus estatus;
    private Date fechaEstatusActual;
    private Long idUsuariofirmante;
    private Usuario usuarioFirmante;
    private String numeroexpediente;
    private Date fecharecepcion;
    private Date fechapresentacion;
    private Date fechacaptura;
    private String numerorefextranjero;
    private Long idUsuariocaptura;
    private Usuario usuarioCaptura;
    private Integer indActivo;
    private Short hayprioridad;
    private Short conoceClase;
    private Short hayPrioridad;
    private Short hayEstablecimiento;
    private Tipoclaseseleccionada tipoClaseSeleccionada;
    private List<Persona> solicitantes;
    private EstablecimientoXTramite establecimientoXTramite;
    private String productoServicio;
    private Pago pago;
    List<Anexos> anexos;
    private List<Productoservicio> prodServSelec;

    public List<Productoservicio> getProdServSelec() {
        return prodServSelec;
    }

    public void setProdServSelec(List<Productoservicio> prodServSelec) {
        this.prodServSelec = prodServSelec;
    }

    public Short getConoceClase() {
        return conoceClase;
    }

    public void setConoceClase(Short conoceClase) {
        this.conoceClase = conoceClase;
    }

    public Short getHayEstablecimiento() {
        return hayEstablecimiento;
    }

    public void setHayEstablecimiento(Short hayEstablecimiento) {
        this.hayEstablecimiento = hayEstablecimiento;
    }

    public Short getHayPrioridad() {
        return hayPrioridad;
    }

    public void setHayPrioridad(Short hayPrioridad) {
        this.hayPrioridad = hayPrioridad;
    }

    public Pago getPago() {
        return pago;
    }

    public void setPago(Pago pago) {
        this.pago = pago;
    }

    public Short getHayprioridad() {
        return hayprioridad;
    }

    public void setHayprioridad(Short hayprioridad) {
        this.hayprioridad = hayprioridad;
    }

    public String getProductoServicio() {
        return productoServicio;
    }

    public void setProductoServicio(String productoServicio) {
        this.productoServicio = productoServicio;
    }

    public EstablecimientoXTramite getEstablecimientoXTramite() {
        return establecimientoXTramite;
    }

    public void setEstablecimientoXTramite(EstablecimientoXTramite establecimientoXTramite) {
        this.establecimientoXTramite = establecimientoXTramite;
    }

    public List<Persona> getSolicitantes() {
        return solicitantes;
    }

    public Tipoclaseseleccionada getTipoClaseSeleccionada() {
        return tipoClaseSeleccionada;
    }

    public void setSolicitantes(List<Persona> solicitantes) {
        this.solicitantes = solicitantes;
    }

    public void setTipoClaseSeleccionada(Tipoclaseseleccionada tipoClaseSeleccionada) {
        this.tipoClaseSeleccionada = tipoClaseSeleccionada;
    }

    public void setDomicilioNotificacion(Domicilionotificacion domicilioNotificacion) {
        this.domicilioNotificacion = domicilioNotificacion;
    }

    public void setIdDomicilionotificacion(Long idDomicilionotificacion) {
        this.idDomicilionotificacion = idDomicilionotificacion;
    }

    public Domicilionotificacion getDomicilioNotificacion() {
        return domicilioNotificacion;
    }

    public Long getIdDomicilionotificacion() {
        return idDomicilionotificacion;
    }

    public Usuario getUsuarioFirmante() {
        return usuarioFirmante;
    }

    public void setUsuarioFirmante(Usuario usuarioFirmante) {
        this.usuarioFirmante = usuarioFirmante;
    }

    public Long getIdTramite() {
        return idTramite;
    }

    public void setIdTramite(Long idTramite) {
        this.idTramite = idTramite;
    }

    public Long getIdSubtiposolicitud() {
        return idSubtiposolicitud;
    }

    public void setIdSubtiposolicitud(Long idSubtiposolicitud) {
        this.idSubtiposolicitud = idSubtiposolicitud;
    }

    public CatSubtiposolicitud getSubTipoSolicitud() {
        return subTipoSolicitud;
    }

    public void setSubTipoSolicitud(CatSubtiposolicitud subTipoSolicitud) {
        this.subTipoSolicitud = subTipoSolicitud;
    }

    public Long getIdTipomarca() {
        return idTipomarca;
    }

    public void setIdTipomarca(Long idTipomarca) {
        this.idTipomarca = idTipomarca;
    }

    public BigDecimal getMarcacolectiva() {
        return marcacolectiva;
    }

    public void setMarcacolectiva(BigDecimal marcacolectiva) {
        this.marcacolectiva = marcacolectiva;
    }

    public String getDescripcionsignodistintivo() {
        return descripcionsignodistintivo;
    }

    public void setDescripcionsignodistintivo(String descripcionsignodistintivo) {
        this.descripcionsignodistintivo = descripcionsignodistintivo == null ? null : descripcionsignodistintivo.trim();
    }

    public Date getFechaprimeruso() {
        return fechaprimeruso;
    }

    public void setFechaprimeruso(Date fechaprimeruso) {
        this.fechaprimeruso = fechaprimeruso;
    }

    public Short getNousadofecha() {
        return nousadofecha;
    }

    public void setNousadofecha(Short nousadofecha) {
        this.nousadofecha = nousadofecha;
    }

    public void setPaisPrioridad(Pais paisPrioridad) {
        this.paisPrioridad = paisPrioridad;
    }

    public Pais getPaisPrioridad() {
        return paisPrioridad;
    }

    public String getLeyendasfigurasreservables() {
        return leyendasfigurasreservables;
    }

    public void setLeyendasfigurasreservables(String leyendasfigurasreservables) {
        this.leyendasfigurasreservables = leyendasfigurasreservables == null ? null : leyendasfigurasreservables.trim();
    }

    public Long getIdPaisprioridad() {
        return idPaisprioridad;
    }

    public void setIdPaisprioridad(Long idPaisprioridad) {
        this.idPaisprioridad = idPaisprioridad;
    }

    public Date getFechapresentacionextranjero() {
        return fechapresentacionextranjero;
    }

    public void setFechapresentacionextranjero(Date fechapresentacionextranjero) {
        this.fechapresentacionextranjero = fechapresentacionextranjero;
    }

    public Long getIdEstatus() {
        return idEstatus;
    }

    public void setIdEstatus(Long idEstatus) {
        this.idEstatus = idEstatus;
    }

    public CatEstatus getEstatus() {
        return estatus;
    }

    public void setEstatus(CatEstatus estatus) {
        this.estatus = estatus;
    }

    public Date getFechaEstatusActual() {
        return fechaEstatusActual;
    }

    public void setFechaEstatusActual(Date fechaEstatusActual) {
        this.fechaEstatusActual = fechaEstatusActual;
    }

    public Long getIdUsuariofirmante() {
        return idUsuariofirmante;
    }

    public void setIdUsuariofirmante(Long idUsuariofirmante) {
        this.idUsuariofirmante = idUsuariofirmante;
    }

    public String getNumeroexpediente() {
        return numeroexpediente;
    }

    public void setNumeroexpediente(String numeroexpediente) {
        this.numeroexpediente = numeroexpediente == null ? null : numeroexpediente.trim();
    }

    public Date getFecharecepcion() {
        return fecharecepcion;
    }

    public void setFecharecepcion(Date fecharecepcion) {
        this.fecharecepcion = fecharecepcion;
    }

    public Date getFechapresentacion() {
        return fechapresentacion;
    }

    public void setFechapresentacion(Date fechapresentacion) {
        this.fechapresentacion = fechapresentacion;
    }

    public Date getFechacaptura() {
        return fechacaptura;
    }

    public void setFechacaptura(Date fechacaptura) {
        this.fechacaptura = fechacaptura;
    }

    public String getNumerorefextranjero() {
        return numerorefextranjero;
    }

    public void setNumerorefextranjero(String numerorefextranjero) {
        this.numerorefextranjero = numerorefextranjero == null ? null : numerorefextranjero.trim();
    }

    public Long getIdUsuariocaptura() {
        return idUsuariocaptura;
    }

    public void setIdUsuariocaptura(Long idUsuariocaptura) {
        this.idUsuariocaptura = idUsuariocaptura;
    }

    public Usuario getUsuarioCaptura() {
        return usuarioCaptura;
    }

    public void setUsuarioCaptura(Usuario usuarioCaptura) {
        this.usuarioCaptura = usuarioCaptura;
    }

    public Integer getIndActivo() {
        return indActivo;
    }

    public void setIndActivo(Integer indActivo) {
        this.indActivo = indActivo;
    }

    public List<Anexos> getAnexos() {
        return anexos;
    }

    public void setAnexos(List<Anexos> anexos) {
        this.anexos = anexos;
    }
}