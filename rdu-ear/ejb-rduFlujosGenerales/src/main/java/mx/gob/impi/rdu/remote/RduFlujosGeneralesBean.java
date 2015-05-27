package mx.gob.impi.rdu.remote;

import java.util.Date;
import java.util.List;
import mx.gob.impi.ingresos.persistence.model.FepsRecibidos;
import mx.gob.impi.pase.persistence.model.Promovente;
import mx.gob.impi.rdu.dto.FolioDto;
import mx.gob.impi.rdu.dto.CertificadoDto;
import mx.gob.impi.rdu.persistence.model.Anexos;
import mx.gob.impi.rdu.persistence.model.Pago;
import mx.gob.impi.rdu.persistence.model.Firma;
import mx.gob.impi.rdu.persistence.model.Folio;
import mx.gob.impi.rdu.persistence.model.Tramite;
import mx.gob.impi.rdu.dto.AnexosViewDto;
import mx.gob.impi.rdu.dto.ExpedientesDto;
import mx.gob.impi.rdu.dto.TramiteDto;
import mx.gob.impi.rdu.dto.FirmaDto;
import mx.gob.impi.rdu.persistence.model.CodigosPostales;
import mx.gob.impi.pase.persistence.model.SisAlerta;
import mx.gob.impi.rdu.dto.ArchivoDigitalDto;
import mx.gob.impi.rdu.dto.SolicitudPreparacionDto;
import mx.gob.impi.rdu.persistence.model.*;
import mx.gob.impi.sagpat.persistence.model.Solicitud;
import mx.gob.impi.sigmar.persistence.model.NotificacionView;
import mx.gob.impi.sigappi.persistence.model.KffoliosNotificacion;
import mx.gob.impi.sigappi.persistence.model.KfFolios;
import mx.gob.impi.sigappi.persistence.model.KfAlmacenar;
import mx.gob.impi.sigappi.persistence.model.KfContenedores;
import mx.gob.impi.sigappi.persistence.model.SolicitudInteresados;
import mx.gob.impi.sigappi.persistence.model.TiposRelacion;
import mx.gob.impi.sigappi.persistence.model.UsuariosSigappi;
import mx.gob.impi.sigappi.persistence.model.Anexo;
import mx.gob.impi.sigappi.persistence.model.Area;
import mx.gob.impi.sigappi.persistence.model.DerechosAsociados;
import mx.gob.impi.sigappi.persistence.model.FigurasJuridicas;
import mx.gob.impi.sigappi.persistence.model.Interesados;
import mx.gob.impi.sigappi.persistence.model.SolicitudRevision;
import mx.gob.impi.sigappi.persistence.model.SolicitudWeb;
import mx.gob.impi.sigappi.persistence.model.TipoAnexo;


public interface RduFlujosGeneralesBean {

    public List<SolicitudPreparacionDto> extraerSolicitudesPreparacion(List<Integer> usuarios, int idTipoTramite);

    public List<Tramite> cargarSolicitudes(long idUsuario);

    Tramite getTramiteById(Long idTramite);

    public Long insertFirma(Firma firm);

    public FirmaDto loadFirma(Long idFirm);

    public Integer updateFirma(FirmaDto firm);

    public int insertFirmaAdmin(CertificadoDto firm);

    public List<CertificadoDto> loadAllActiveCerts(Integer idArea);

    public CertificadoDto selectFirmaAdminByInd(CertificadoDto firm);

    public CertificadoDto selectFirmaAdminEstatusArea(CertificadoDto firm);

    public int updateFirmaAdmin(CertificadoDto firm);

    public int eliminarSolicitud(SolicitudPreparacionDto solicitudPreparacionDto);
    
    public int eliminarPromocion(SolicitudPreparacionDto solicitudPreparacionDto);

    FolioDto selectDynamicFolio(Folio folio);

    public long copiarSolicitud(Tramite prmTramite);

    public int actualizarTramite(Tramite tramite);

    public int insertarpago(Pago pago);

    public TramiteDto obtenerSolicitudes(int idTramite);

    public int actualizarPago(Pago pago);

    public List<Pago> buscarPago(long pago);

    public int insertarAnexos(Anexos anexo);

    public int copiarSolicitudEnSagpat(TramitePatente prmTramite, int paginas);

    public int insertFeps(TramiteDto prmTramite);

    public int insertFepsSagpat(TramitePatente prmTramite);

    public FepsRecibidos selectFepsByFolio(Long fepsFolio);

    public String validatePhrase(String tmp) throws Exception;

    public int insertarAnexosDto(List<AnexosViewDto> anexosDto);

    List<CodigosPostales> selectByIdCodigosPostales(String codigoPostal);

    List<ExpedientesDto> obtenerExpedientesNot(List<Integer> usuarios, Integer idArea, Integer idTipoSolicitud,
            Integer ultimaSemana, Integer ultimoMes, String fechaInicio, String fechaFin);

    List<ExpedientesDto> obtenerExpedientesProm(List<Integer> usuarios, Integer idArea, Integer idTipoSolicitud,
            Integer ultimaSemana, Integer ultimoMes, String fechaInicio, String fechaFin);

    public List<ExpedientesDto> obtenerExpedientesPorUsr(List<Integer> usuarios, Integer idArea, Integer idTipoSolicitud,
            Integer ultimaSemana, Integer ultimoMes, String fechaInicio, String fechaFin);
    
    public List<ExpedientesDto> obtenerExpedientesPaginadosPorUsr(List<Integer> usuarios, Integer idArea, Integer idTipoSolicitud,
            Integer ultimaSemana, Integer ultimoMes, String fechaInicio, String fechaFin,int start, int maxPerPage);
    
    List<ExpedientesDto> obtenerExpedientesNotPaginados(List<Integer> usuarios, Integer idArea, Integer idTipoSolicitud,
            Integer ultimaSemana, Integer ultimoMes, String fechaInicio, String fechaFin, Integer first, Integer pageSize);
    
    List<ExpedientesDto> obtenerExpedientesPromPaginados(List<Integer> usuarios, Integer idArea, Integer idTipoSolicitud,
            Integer ultimaSemana, Integer ultimoMes, String fechaInicio, String fechaFin, Integer first, Integer pageSize);
    
    public Integer obtenerTotalExpedientesPorUsr(List<Integer> usuarios, Integer idArea, Integer idTipoSolicitud,
            Integer ultimaSemana, Integer ultimoMes, String fechaInicio, String fechaFin);
    
    public int obtenerTotalExpedientesNot(List<Integer> usuarios, Integer idArea, Integer idTipoSolicitud,
            Integer ultimaSemana, Integer ultimoMes, String fechaInicio, String fechaFin);
    
    public int obtenerTotalExpedientesProm(List<Integer> usuarios, Integer idArea, Integer idTipoSolicitud,
            Integer ultimaSemana, Integer ultimoMes, String fechaInicio, String fechaFin);
    
    public int insertarAlertas(SisAlerta alerta);

    public List<SisAlerta> selectAlertas(SisAlerta alerta);

    public List<Tramite> selectTramite();

    public int insertBitacoraErrores(BitacoraErrores bitacora);

    List<BitacoraErrores> selectBitacoraErrores();

    public int eliminarBitacoraErrores(Short idBitacora);

    Date getSysDate();

    List<Firma> selectFirmas();

    int udapteFirmasEnvio(Firma firma);

    public String jobeliminarAcuse();

    public int insertarAnexosDtoPatente(List<AnexosViewDto> anexosDto);

    public int selectsecuencia();

    int insertarAnexosPago(Anexos anexo);

    public int actualizarPromocionMarca(TramitePromocionMarca tramite);

    public String[] getFoliosSagpat(String expediente, Date today);

    Integer updateSelectiveFirma(Firma firma);

    public Promovente selectPromovente(Long id);

    List<Promovente> selectPromoventeByPerfil(Integer idPerfil);

    public List<Pago> selectPagoByTramiteId(Long idTramite);

    public List<Anexos> obtenerAnexosByTramite(Long idTramite);

    Firma obternerFirmaByTramite(Long idTramite);

    List<NotificacionView> consultarNotificacionView(Long oficioSalida);

    List<NotificacionView> consultarNotificacionView(String codOficina, Long anio, Long numOficio);

    List<Notificacion> consultarNotificaciones(Integer idUsuario);
    
    Notificacion selectNotificacionesById(Integer idNotificacion);

    int insertarNotificacion(Notificacion notificacion);

    int updateNotificacion(Notificacion notificacion);

    Long saveNotificacionFirma(NotificacionFirma notifFirma);
    
    NotificacionFirma selectNotificacionFirmaByPrimaryKey(Integer id);

    public List<Notificacion> selectNotificacionesByIds(List<Integer> cadenaIds);

    public List<Notificacion> getNotificacionesUserLoad(Integer idUserLoad, Integer promovente);
    
    public List<Notificacion> getNotificacionesTitular(String titular, Integer promovente);

    public List<Notificacion> getNotificacionesByDate(String fechaInicio, String fechaFin, Integer ultimaSemana, Integer ultimoMes, Integer idUsuarioFirmante);

    Firma obternerFirmaByExpediente(Long expediente, String expedienteSat);
    
    Firma selectFirmaByPrimaryKey(Long idFirma);

    public Notificacion getNotificacionesByFolio(String folio);

    int deleteNotificacionesByIds(List<Integer> cadenaIds);
    
    public ArchivoDigitalDto obtenerAcusePdf(Long idFirma);
    
    public ArchivoDigitalDto obtenerAnexoXml(Long idFirma);
    
    public ArchivoDigitalDto obtenerAcuseLogo(Long idTamite);
    
    int deleteAnexosByIds(Long idAnexo);
    int deleteByTypeAnexo(Anexos anexo);

    public Anexos obtenerAnexosDynamic(Anexos anexo);

    SolicitudPreparacionDto selectPromoByPrimaryKey(Long idTramitePromocionPatente);
    
    public Solicitud selectByExpedienteDivisional(String oficina, Integer numExp, Integer serExp, String tipExp);
    
    // para sigappi
    List<KffoliosNotificacion> selectByOficioSalida(String codbarras);
    
    List<KffoliosNotificacion>  selectANotificarByCodInteresado(Integer codInteresado);
    
    int insert(KffoliosNotificacion kffoliosNotificacion);
    
    int insertSelective(KffoliosNotificacion kffoliosNotificacion);

    List<KfFolios> selectKfFoliosByCodbarras(String codbarras);
    
    int insert(KfFolios kfFolios);
    
    List<KfAlmacenar> selectKfAlmacenarByCodbarras(String codbarras);
    
    List<KfAlmacenar> selectKfAlmacenarByTitle(String title);
    
    int insert(KfAlmacenar kfAlmacenar);
    
    List<KfContenedores> selectKfContenedoresByTitle(String title);
    
    int insert(KfContenedores kfContenedores);
    
    List<KfContenedores> selectKfContenedoresByPC(String pc);
    
    
    List<SolicitudInteresados> selectSolicitudInteresadosByCodInteresado(Integer codInteresado);
    List<SolicitudInteresados> selectSolicitudInteresadosByTitle( String title);
    
    int insert(SolicitudInteresados solicitudInteresados);
    
    List<TiposRelacion> selectTiposRelacionByCodRelacion(Integer codRelacion);
    
    int insert(TiposRelacion  tiposRelacion);
    
    List<UsuariosSigappi> selectUsuariosSigappiByCveUsuario(String cveUsuario);
    
    int insert(UsuariosSigappi usuariosSigappi);
    
    List<TiposRelacion> listTiposRelacion();
    
    List<SolicitudInteresados> selectSolicitudInteresadosByCodInteresadoAndSecuencia(Integer codInteresado, Integer secuencia);
    
    int updateNotificationSubscription(String title, Integer codInteresado, Integer secuencia);
    
    List<Anexo> selectAnexoByCodbarras(String codbarras);
    
    List<Area> selectAreaByCveArea(Integer cveArea);
    
    List<DerechosAsociados> selectDerechosAsociadosByTitle(String title);
    
    List<FigurasJuridicas> selectFigurasJuridicasByNumFigura(Integer numFigura);
    
    List<Interesados> selectInteresadosByCodInteresado(Integer codInteresado);
    
    List<TipoAnexo> selectTipoAnexoByCategoriaDivisionSeccion(Integer categoria,Integer division,Integer seccion);
    
    int insertSolicitudRevision(SolicitudRevision solicitudRevision);
    
    List<SolicitudRevision> findSolicitudRevisionByCodInteresadoAndSecuencia(Integer codInteresado, Integer secuencia);
    
    List<SolicitudRevision> findSolicitudRevisionByCodInteresadoAndSecuenciaAndSession(Integer codInteresado, Integer secuencia, Integer idSolicitud);
    
    int updateSolicitudRevision(SolicitudRevision solicitudRevision);
    
    KfContenedores findKfContenedoresByTitleOrPc(String id);
    
    void deleteSolicitudRevision(SolicitudRevision solicitudRevision);
    
    int appendSolicitudRevision(SolicitudRevision solicitudRevision);
    
    int insertSolicitudWeb(SolicitudWeb solicitudWeb);
    
    int updateSolicitudWeb(SolicitudWeb solicitudWeb);
    
    List<SolicitudWeb> findAllSolicitudWebByUserAndStatus(Integer idPromovente, Integer idStatus);
    
    List<SolicitudWeb> findAllSolicitudWebByUser(Integer idPromovente);
    
    SolicitudWeb findSolicitudWebBySession(Integer idSolicitud);
    
    Integer nextSequence();
}
