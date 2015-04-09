package mx.gob.impi.rdu.ws.vo;

import mx.gob.impi.rdu.persistence.model.CatSubtiposolicitud;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import mx.gob.impi.rdu.persistence.model.Anexos;
import mx.gob.impi.rdu.persistence.model.CatEstatus;
import mx.gob.impi.rdu.persistence.model.Usuario;

/**
 *
 * @author winter
 */
public class TramiteVo {

    public TramiteVo() {
        super();
    }

    public TramiteVo(long idTramite, Integer idSubtiposolicitud, CatSubtiposolicitud subTipoSolicitud,
                     String descripcionsignodistintivo, Date fechaprimeruso, Short nousadofecha,
                     String leyendasfigurasreservables, long idDomicilionotificacion, Integer idPaisprioridad,
                     Date fechapresentacionextranjero, Integer idEstatus, CatEstatus estatus, Date fechaEstatusActual,
                     Integer idUsuariofirmante, Usuario usuarioFirmante, String numeroexpediente, Date fecharecepcion,
                     Date fechapresentacion, Date fechacaptura, String numerorefextranjero, Integer idUsuariocaptura,
                     Usuario usuarioCaptura, Integer indActivo, List<Anexos> anexos) {
        
        this.idTramite = idTramite;
        this.idSubtiposolicitud = idSubtiposolicitud;
        this.subTipoSolicitud = subTipoSolicitud;
        this.descripcionsignodistintivo = descripcionsignodistintivo;
        this.fechaprimeruso = fechaprimeruso;
        this.nousadofecha = nousadofecha;
        this.leyendasfigurasreservables = leyendasfigurasreservables;
        this.idDomicilionotificacion = idDomicilionotificacion;
        this.idPaisprioridad = idPaisprioridad;
        this.fechapresentacionextranjero = fechapresentacionextranjero;
        this.idEstatus = idEstatus;
        this.estatus = estatus;
        this.fechaEstatusActual = fechaEstatusActual;
        this.idUsuariofirmante = idUsuariofirmante;
        this.usuarioFirmante = usuarioFirmante;
        this.numeroexpediente = numeroexpediente;
        this.fecharecepcion = fecharecepcion;
        this.fechapresentacion = fechapresentacion;
        this.fechacaptura = fechacaptura;
        this.numerorefextranjero = numerorefextranjero;
        this.idUsuariocaptura = idUsuariocaptura;
        this.usuarioCaptura = usuarioCaptura;
        this.indActivo = indActivo;
        this.anexos = anexos;
    }


    private long idTramite;

    private Integer idSubtiposolicitud;

    private CatSubtiposolicitud subTipoSolicitud;
    
    private String descripcionsignodistintivo;

    private Date fechaprimeruso;

    private Short nousadofecha;

    private String leyendasfigurasreservables;

    private long idDomicilionotificacion;

    private Integer idPaisprioridad;

    private Date fechapresentacionextranjero;

    private Integer idEstatus;

    private CatEstatus estatus;

    private Date fechaEstatusActual;

    private Integer idUsuariofirmante;

    private Usuario usuarioFirmante;

    private String numeroexpediente;

    private Date fecharecepcion;

    private Date fechapresentacion;

    private Date fechacaptura;

    private String numerorefextranjero;

    private Integer idUsuariocaptura;

    private Usuario usuarioCaptura;

    private Integer indActivo;

    List <Anexos> anexos;

    public long getIdTramite() {
        return idTramite;
    }

    public void setIdTramite(long idTramite) {
        this.idTramite = idTramite;
    }

    public Integer getIdSubtiposolicitud() {
        return idSubtiposolicitud;
    }

    public void setIdSubtiposolicitud(Integer idSubtiposolicitud) {
        this.idSubtiposolicitud = idSubtiposolicitud;
    }

    public CatSubtiposolicitud getSubTipoSolicitud() {
        return subTipoSolicitud;
    }

    public void setSubTipoSolicitud(CatSubtiposolicitud subTipoSolicitud) {
        this.subTipoSolicitud = subTipoSolicitud;
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

    public long getIdDomicilionotificacion() {
        return idDomicilionotificacion;
    }

    public void setIdDomicilionotificacion(long idDomicilionotificacion) {
        this.idDomicilionotificacion = idDomicilionotificacion;
    }

    public Integer getIdPaisprioridad() {
        return idPaisprioridad;
    }

    public void setIdPaisprioridad(Integer idPaisprioridad) {
        this.idPaisprioridad = idPaisprioridad;
    }

    public Date getFechapresentacionextranjero() {
        return fechapresentacionextranjero;
    }

    public void setFechapresentacionextranjero(Date fechapresentacionextranjero) {
        this.fechapresentacionextranjero = fechapresentacionextranjero;
    }

    public Integer getIdEstatus() {
        return idEstatus;
    }

    public void setIdEstatus(Integer idEstatus) {
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

    public Integer getIdUsuariofirmante() {
        return idUsuariofirmante;
    }

    public void setIdUsuariofirmante(Integer idUsuariofirmante) {
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

    public Integer getIdUsuariocaptura() {
        return idUsuariocaptura;
    }

    public void setIdUsuariocaptura(Integer idUsuariocaptura) {
        this.idUsuariocaptura = idUsuariocaptura;
    }

    public Usuario getUsuarioCaptura() {
        return usuarioCaptura;
    }

    public void setUsuarioCaptura(Usuario usuarioCaptura) {
        this.usuarioCaptura = usuarioCaptura;
    }

    public Usuario getUsuarioFirmante() {
        return usuarioFirmante;
    }

    public void setUsuarioFirmante(Usuario usuarioFirmante) {
        this.usuarioFirmante = usuarioFirmante;
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