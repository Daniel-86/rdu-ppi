/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.gob.impi.rdu.service;

import java.util.Date;
import java.util.List;
import mx.gob.impi.pase.persistence.model.Promovente;
import mx.gob.impi.pase.persistence.model.SisAlerta;
import mx.gob.impi.rdu.dto.*;
import mx.gob.impi.rdu.exposition.patentes.PatentesDisenoIndustrialMB;
import mx.gob.impi.rdu.persistence.model.*;
import mx.gob.impi.sagpat.persistence.model.Solicitud;
import mx.gob.impi.sigappi.persistence.model.KffoliosNotificacion;
import mx.gob.impi.sigappi.persistence.model.KfFolios;
import mx.gob.impi.sigappi.persistence.model.KfAlmacenar;
import mx.gob.impi.sigappi.persistence.model.KfContenedores;
import mx.gob.impi.sigappi.persistence.model.SolicitudInteresados;
import mx.gob.impi.sigappi.persistence.model.TiposRelacion;
import mx.gob.impi.sigappi.persistence.model.UsuariosSigappi;
import mx.gob.impi.sigmar.persistence.model.NotificacionView;

/**
 *
 * @author usradmin
 */
public interface FlujosGralesViewService {

//    List<Tramite> cargarSolicitudes(long idUsuario);

    RespuestaDto EliminarSolicitud(SolicitudPreparacionDto prmTramite);

    RespuestaDto copiarSolicitud(Tramite prmTramite);

    Long insertaFirma(FirmaDto firma);

    FirmaDto loadFirma(Long idFirma);

    List<CertificadoDto> loadAllActiveCerts(Integer idArea);

    Integer updateFirma(FirmaDto firma);

    int insertaFirmaAdmin(CertificadoDto firma);

    int actualizaFirmaAdmin(CertificadoDto firma);

    CertificadoDto getFirmaAdmin(CertificadoDto firma);


    String[] getFoliosSagpat(String expediente, Date hoy);

    Domicilio obtenerDomicilio(DomicilioDto pDomicilio);

    int insertarAnexosDtos(List<AnexosViewDto> anexosDtos);

    List<CodigosPostales> obtenerCodigosPostales(String codigoPostal);

    Date getSysDate();


    public int copiarSolicitudEnSagpat(TramitePatente patente, int pages);
    
    public List<Notificacion> getNotificacionesUserLoad(Integer idUserLoad, Integer promovente);
    public List<Notificacion> getNotificacionesTitular(String titular, Integer promovente);

    public int insertarAlertas(SisAlerta alerta);

    public Promovente buscaPromovente(Long id);

    List<ExpedientesDto> obtenerExpedientesNot(List<Integer> usuarios, Integer idArea, Integer idTipoSolicitud,
            Integer ultimaSemana, Integer ultimoMes, String fechaInicio, String fechaFin);

    List<ExpedientesDto> obtenerExpedientesProm(List<Integer> usuarios, Integer idArea, Integer idTipoSolicitud,
            Integer ultimaSemana, Integer ultimoMes, String fechaInicio, String fechaFin);

    List<ExpedientesDto> obtenerExpedientes(List<Integer> usuarios, Integer idArea, Integer idTipoSolicitud,
            Integer ultimaSemana, Integer ultimoMes, String fechaInicio, String fechaFin);
    
    public int obtenerTotalExpedientes(List<Integer> usuarios, Integer idArea, Integer idTipoSolicitud,
            Integer ultimaSemana, Integer ultimoMes, String fechaInicio, String fechaFin);
    
    public int obtenerTotalExpedientesNot(List<Integer> usuarios, Integer idArea, Integer idTipoSolicitud,
            Integer ultimaSemana, Integer ultimoMes, String fechaInicio, String fechaFin);
    
    public int obtenerTotalExpedientesProm(List<Integer> usuarios, Integer idArea, Integer idTipoSolicitud,
            Integer ultimaSemana, Integer ultimoMes, String fechaInicio, String fechaFin);
    
    List<ExpedientesDto> obtenerExpedientesPaginados(List<Integer> usuarios, Integer idArea, Integer idTipoSolicitud,
            Integer ultimaSemana, Integer ultimoMes, String fechaInicio, String fechaFin, Integer first, Integer pageSize);
    
    List<ExpedientesDto> obtenerExpedientesNotPaginados(List<Integer> usuarios, Integer idArea, Integer idTipoSolicitud,
            Integer ultimaSemana, Integer ultimoMes, String fechaInicio, String fechaFin, Integer first, Integer pageSize);
    
    List<ExpedientesDto> obtenerExpedientesPromPaginados(List<Integer> usuarios, Integer idArea, Integer idTipoSolicitud,
            Integer ultimaSemana, Integer ultimoMes, String fechaInicio, String fechaFin, Integer first, Integer pageSize);
    

    public int insertFeps(TramiteDto prmTramite);

    public int insertFepsSagpat(TramitePatente tramite);

    List<Pago> buscarPago(long pago);

    public int insertBitacoraErrores(BitacoraErrores bitacora);

    List<BitacoraErrores> selectBitacoraErrores();

    public int eliminarBitacoraErrores(Short idBitacora);

    int insertarAnexosDtosPatentes(List<AnexosViewDto> anexosDtos);

    public List<SolicitudPreparacionDto> extraerSolicitudesPreparacion(List<Integer> usuarios, int idTipoTramite);

    public String jobeliminarAcuse();

    public String validatePhrase(String tmp) throws Exception;

    public void eliminarTramitesJob();

    public List<Pago> selectPagoByTramiteId(Long idTramite);

    public List<Anexos> obtenerAnexosByTramite(Long idTramite);

    public Firma obtenerFirmaByTramite(Long idTramite);

        List<NotificacionView> consultarNotificacionView(Long oficioSalida);

    List<NotificacionView> consultarNotificacionView(String codOficina, Long anio, Long numOficio);

    List<Notificacion> consultarNotificaciones(Integer idNotificacion);
    
    
    Notificacion selectNotificacionesById(Integer idNotificacion);
    
    public Long saveFirmaNotificacion(NotificacionFirma firma);

    public int updateNotificacionFirmada(Notificacion notificacion);

    int insertarNotificacion(Notificacion notificacion);

    public List<Notificacion> selectNotificacionesByIds(List<Integer> cadenaIds);

    public List<Notificacion> selectNotificacionesByDates(String fechaInicio, String fechaFinal, Integer ultimaSemana, Integer ultimoMes, Integer idUsuarioFirmante);

    public String getEmailByExp(Long expediente, String expedienteSag);

    List<Promovente> selectPromoventeByPerfil(Integer idPerfil);

    Notificacion getNotificacionesByFolio(String folio);

    public int deleteNotificacionesByIds(List<Integer> cadenaIds);

    public Firma getFirmaByExp(Long expediente, String expedienteSag);

    public ArchivoDigitalDto obtenerAcusePdf(Long idFirma);

    public ArchivoDigitalDto obtenerAnexoXml(Long idFirma);

    public ArchivoDigitalDto obtenerAcuseLogo(Long idTamite);

    int deleteAnexosByIds(Long idAnexo);
    int deleteByTypeAnexo(Anexos anexo);

    Prioridad selectPrioridadByPrimaryKey(Long idPrioridad);
    int updatePrioridadByPrimaryKey(Prioridad record);

    public Anexos obtenerAnexosDynamic(Anexos anexo);
    
    public boolean validarSolicitantes(List<Persona> listaSolicitantes);
    public void configfechaPresDiv(PatentesDisenoIndustrialMB formaPatentes);

    SolicitudPreparacionDto selectPromoByPrimaryKey(Long idTramitePromocionPatente);
    
    public void crearAnexoHojaDescuento(Integer flagFechas, Persona solicitante, List<Persona> apoderados, Long tramiteId,String result, String firmante, String selloSolicitante, Integer tipoSolicitud);
    
    public Solicitud selectByExpedienteDivisional(String oficina, Integer numExp, Integer serExp, String tipExp);
    
    
    int insert(KffoliosNotificacion kffoliosNotificacion);
    int insertSelective(KffoliosNotificacion kffoliosNotificacion);

    public List<KffoliosNotificacion> selectByOficioSalida(String codbarras);
    
    int insert(KfFolios kfFolios);
    public List<KfFolios> selectKfFoliosByCodbarras(String codbarras);
    
    int insert(KfAlmacenar kfAlmacenar);
    public List<KfAlmacenar> selectKfAlmacenarByCodbarras(String codbarras);
    public List<KfAlmacenar> selectKfAlmacenarByTitle(String title);
    
    int insert(KfContenedores kfContenedores);
    public List<KfContenedores> selectKfContenedoresByTitle(String title);
    public List<KfContenedores> selectKfContenedoresByPC(String pc);
    
    int insert(SolicitudInteresados solicitudInteresados);
    public List<SolicitudInteresados> selectSolicitudInteresadosByTitle(String title);
    public List<SolicitudInteresados> selectSolicitudInteresadosByCodInteresado(Integer codInteresado);
    
    int insert(TiposRelacion tiposRelacion);
    public List<TiposRelacion> selectTiposRelacionByCodRelacion(Integer codRelacion);
    
    int insert(UsuariosSigappi usuariosSigappi);
    public List<UsuariosSigappi> selectUsuariosSigappiByCveUsuario(String cveUsuario);
}
