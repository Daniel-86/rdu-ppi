/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.gob.impi.rdu.dto;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import mx.gob.impi.rdu.persistence.model.*;

/**
 *
 * @author
 */
public class TramiteDto implements Serializable {
    
    private Long idTramite;


    private Long idSubtiposolicitud;

    private CatSubtiposolicitudDto subTipoSolicitudDto = new CatSubtiposolicitudDto();

    private Long idTipomarca;
    
    private CatTipomarcaDto tipoMarcaDto = new CatTipomarcaDto() ;

    private BigDecimal marcacolectiva;

    private String descripcionsignodistintivo;

    private Date fechaprimeruso;

    private Short nousadofecha;

    private String leyendasfigurasreservables;

    private Long idDomicilionotificacion;
    
    private DomicilionotificacionDto domicilioNotificacionDto= new DomicilionotificacionDto();

    private Long idPaisprioridad;

    private Date fechapresentacionextranjero;

    private Long idEstatus;

    private CatEstatusDto estatusDto = new CatEstatusDto();

    private Date fechaEstatusActual;

    private Long idUsuariofirmante;

   // private Usuario usuarioFirmante;

    private String numeroexpediente;

    private Date fecharecepcion;

    private Date fechapresentacion;

    private Date fechacaptura;

    private String numerorefextranjero;

    private Long idUsuariocaptura;

    private Usuario usuarioCaptura;

    private Integer indActivo;
    
    List <Anexos> anexos;
    
    private PagoDto pagoDto = new PagoDto();
    
       private ApoderadoDto apoderado = new ApoderadoDto();
    
    private FirmaDto firma = new FirmaDto();

    private Date fechaSysdate;

    private int tipoTramite;
    
    private Short conoceClase;
    
    private Short hayEstablecimiento;

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

    public Date getFechaSysdate() {
        return fechaSysdate;
    }

    public void setFechaSysdate(Date fechaSysdate) {
        this.fechaSysdate = fechaSysdate;
    }
        
    public PagoDto getPagoDto() {
        return pagoDto;
    }
     
    public void setPagoDto(PagoDto pagoDto) {
        this.pagoDto = pagoDto;
    }
    
    public Long getIdDomicilionotificacion() {
        return idDomicilionotificacion;
    }
    
    private String productoServicio;
   
    private PaisDto paisPrioridadDto= new PaisDto();

    private TipoClaseSeleccionadaDto tipoClaseSeleccionadaDto= new TipoClaseSeleccionadaDto();
    
    List<PersonaDto> solicitantesDto;

    EstablecimientoXTramiteDto establecimientoXTramiteDto = new EstablecimientoXTramiteDto();

    private String firmaImpi;
    private String firmaSolicitante;
    private String certificadora;
    private String serieSolicante;
    private String nombreFirmante;

    public FirmaDto getFirma() {
        return firma;
    }

    public void setFirma(FirmaDto firma) {
        this.firma = firma;
    }
    
    public ApoderadoDto getApoderado() {
        return apoderado;
    }

    public void setApoderado(ApoderadoDto apoderado) {
        this.apoderado = apoderado;
    }
  

    public PaisDto getPaisPrioridadDto() {
        return paisPrioridadDto;
    }

    public void setPaisPrioridadDto(PaisDto paisPrioridadDto) {
        this.paisPrioridadDto = paisPrioridadDto;
    }

    
    
    public String getProductoServicio() {
        return productoServicio;
    }

    public void setProductoServicio(String productoServicio) {
        this.productoServicio = productoServicio;
    }
    
    public void setEstablecimientoXTramiteDto(EstablecimientoXTramiteDto establecimientoXTramiteDto) {
        this.establecimientoXTramiteDto = establecimientoXTramiteDto;
    }

    public EstablecimientoXTramiteDto getEstablecimientoXTramiteDto() {
        return establecimientoXTramiteDto;
    }
            
    public void setSolicitantesDto(List<PersonaDto> solicitantesDto) {
        this.solicitantesDto = solicitantesDto;
    }
    
    public List<PersonaDto> getSolicitantesDto() {
        return solicitantesDto;
    }

    public TipoClaseSeleccionadaDto getTipoClaseSeleccionadaDto() {
        return tipoClaseSeleccionadaDto;
    }

    public void setTipoClaseSeleccionadaDto(TipoClaseSeleccionadaDto tipoClaseSeleccionadaDto) {
        this.tipoClaseSeleccionadaDto = tipoClaseSeleccionadaDto;
    }    


    public void setIdDomicilionotificacion(Long idDomicilionotificacion) {
        this.idDomicilionotificacion = idDomicilionotificacion;
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

    public DomicilionotificacionDto getDomicilioNotificacionDto() {
        return domicilioNotificacionDto;
    }

    public CatEstatusDto getEstatusDto() {
        return estatusDto;
    }

    public CatSubtiposolicitudDto getSubTipoSolicitudDto() {
        return subTipoSolicitudDto;
    }

    public CatTipomarcaDto getTipoMarcaDto() {
        return tipoMarcaDto;
    }

    public void setDomicilioNotificacionDto(DomicilionotificacionDto domicilioNotificacionDto) {
        this.domicilioNotificacionDto = domicilioNotificacionDto;
    }

    public void setEstatusDto(CatEstatusDto estatusDto) {
        this.estatusDto = estatusDto;
    }

    public void setSubTipoSolicitudDto(CatSubtiposolicitudDto subTipoSolicitudDto) {
        this.subTipoSolicitudDto = subTipoSolicitudDto;
    }

    public void setTipoMarcaDto(CatTipomarcaDto tipoMarcaDto) {
        this.tipoMarcaDto = tipoMarcaDto;
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

    public String getCertificadora() {
        return certificadora;
    }

    public String getFirmaImpi() {
        return firmaImpi;
    }

    public String getFirmaSolicitante() {
        return firmaSolicitante;
    }

    public String getNombreFirmante() {
        return nombreFirmante;
    }

    public String getSerieSolicante() {
        return serieSolicante;
    }

    public void setCertificadora(String certificadora) {
        this.certificadora = certificadora;
    }

    public void setFirmaImpi(String firmaImpi) {
        this.firmaImpi = firmaImpi;
    }

    public void setFirmaSolicitante(String firmaSolicitante) {
        this.firmaSolicitante = firmaSolicitante;
    }

    public void setNombreFirmante(String nombreFirmante) {
        this.nombreFirmante = nombreFirmante;
    }

    public void setSerieSolicante(String serieSolicante) {
        this.serieSolicante = serieSolicante;
    }

    public int getTipoTramite() {
        return tipoTramite;
    }

    public void setTipoTramite(int tipoTramite) {
        this.tipoTramite = tipoTramite;
    }
    
    
}
