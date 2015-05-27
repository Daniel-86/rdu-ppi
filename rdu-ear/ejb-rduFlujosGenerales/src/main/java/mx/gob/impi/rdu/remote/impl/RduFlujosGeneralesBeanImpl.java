package mx.gob.impi.rdu.remote.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Date;

import javax.ejb.Stateless;
import javax.interceptor.Interceptors;
import mx.gob.impi.ingresos.persistence.model.FepsRecibidos;
import mx.gob.impi.pase.persistence.model.Promovente;
import mx.gob.impi.rdu.dto.AnexosViewDto;
import mx.gob.impi.rdu.dto.ExpedientesDto;
import mx.gob.impi.rdu.dto.CertificadoDto;
import mx.gob.impi.rdu.dto.FolioDto;
import mx.gob.impi.rdu.dto.TramiteDto;
import mx.gob.impi.rdu.dto.FirmaDto;
import mx.gob.impi.rdu.persistence.model.Anexos;
import mx.gob.impi.rdu.persistence.model.CodigosPostales;
import mx.gob.impi.rdu.persistence.model.Pago;
import mx.gob.impi.rdu.persistence.model.Certificado;
import mx.gob.impi.rdu.persistence.model.Firma;
import mx.gob.impi.rdu.persistence.model.Folio;
import mx.gob.impi.rdu.persistence.model.TramitePatente;
import mx.gob.impi.pase.persistence.model.SisAlerta;
import mx.gob.impi.rdu.dto.ArchivoDigitalDto;
import mx.gob.impi.rdu.dto.SolicitudPreparacionDto;
import mx.gob.impi.rdu.persistence.model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.ejb.interceptor.SpringBeanAutowiringInterceptor;

import mx.gob.impi.rdu.remote.RduFlujosGeneralesBeanLocal;
import mx.gob.impi.rdu.remote.RduFlujosGeneralesBeanRemote;
import mx.gob.impi.rdu.service.*;
import mx.gob.impi.sigappi.service.*;
import mx.gob.impi.rdu.util.Util;
import mx.gob.impi.sagpat.persistence.model.Solicitud;
import mx.gob.impi.sigappi.persistence.model.Anexo;
import mx.gob.impi.sigappi.persistence.model.Area;
import mx.gob.impi.sigappi.persistence.model.DerechosAsociados;
import mx.gob.impi.sigappi.persistence.model.FigurasJuridicas;
import mx.gob.impi.sigappi.persistence.model.Interesados;
import mx.gob.impi.sigappi.persistence.model.KfAlmacenar;
import mx.gob.impi.sigappi.persistence.model.KfContenedores;
import mx.gob.impi.sigappi.persistence.model.KfFolios;
import mx.gob.impi.sigappi.persistence.model.KffoliosNotificacion;
import mx.gob.impi.sigappi.persistence.model.SolicitudInteresados;
import mx.gob.impi.sigappi.persistence.model.SolicitudRevision;
import mx.gob.impi.sigappi.persistence.model.SolicitudWeb;
import mx.gob.impi.sigappi.persistence.model.TipoAnexo;
import mx.gob.impi.sigappi.persistence.model.TiposRelacion;
import mx.gob.impi.sigappi.persistence.model.UsuariosSigappi;
import mx.gob.impi.sigmar.persistence.model.NotificacionView;
import org.apache.log4j.Logger;
import org.springframework.beans.BeanUtils;

@Stateless(name = "EjbRduFlujosGenerales", mappedName = "EjbRduFlujosGenerales", description = "")
@Interceptors(SpringBeanAutowiringInterceptor.class)
public class RduFlujosGeneralesBeanImpl implements RduFlujosGeneralesBeanRemote, RduFlujosGeneralesBeanLocal {

    @Qualifier("rduTablerosService")
    @Autowired
    private RduTablerosService rduTablerosService;
    @Qualifier("rduAnexosService")
    @Autowired
    private RduAnexosService rduAnexosService;
    @Qualifier("rduCodigosPostalesService")
    @Autowired
    private RduCodigosPostalesServices rduCodigosPostalesService;
    @Qualifier("rduComunesService")
    @Autowired
    private RduComunesService rduComunesService;
    @Qualifier("rduFirmaService")
    @Autowired
    private RduFirmaService rduFirmaService;
    @Qualifier("rduFirmaPatenteService")
    @Autowired
    private RduFirmaService rduFirmaPatenteService;
    @Qualifier("rduFuncionesSimplesService")
    @Autowired
    private RduFuncionesSimplesService rduFuncionesSimplesService;
    @Qualifier("rduProcesosService")
    @Autowired
    private RduProcesosService rduProcesosService;
    @Qualifier("rduPagosFepsService")
    @Autowired
    private RduFirmaService rduPagosFepsService;
    @Qualifier("rduPagoService")
    @Autowired
    private RduPagoService rduPagoService;
    @Qualifier("rduNotificacionesService")
    @Autowired
    private RduNotificacionesService rduNotificacionesService;
    
    @Qualifier("rduSigappiService")
    @Autowired
    private RduSigappiService rduSigappiService;
    
    @Qualifier("rduTramiteService")
    @Autowired
    private RduTramiteService rduTramiteService;

    private Logger log = Logger.getLogger(this.getClass());

    public void setRduPagoService(RduPagoService rduPagoService) {
        this.rduPagoService = rduPagoService;
    }

    public void setRduFirmaPatenteService(RduFirmaService rduFirmaPatenteService) {
        this.rduFirmaPatenteService = rduFirmaPatenteService;
    }

    public void setRduPagosFepsService(RduFirmaService rduPagosFepsService) {
        this.rduPagosFepsService = rduPagosFepsService;
    }

    public void setRduFuncionesSimplesService(RduFuncionesSimplesService rduFuncionesSimplesService) {
        this.rduFuncionesSimplesService = rduFuncionesSimplesService;
    }

    public void setRduCodigosPostalesService(RduCodigosPostalesServices rduCodigosPostalesService) {
        this.rduCodigosPostalesService = rduCodigosPostalesService;
    }

    public void setRduFirmaService(RduFirmaService rduFirmaService) {
        this.rduFirmaService = rduFirmaService;
    }

    public void setRduComunesService(RduComunesService rduComunesService) {
        this.rduComunesService = rduComunesService;
    }

    public void setRduAnexosService(RduAnexosService rduAnexosService) {
        this.rduAnexosService = rduAnexosService;
    }

    public void setRduProcesosService(RduProcesosService rduProcesosService) {
        this.rduProcesosService = rduProcesosService;
    }
    
     public void setRduTramiteService(RduTramiteService rduTramiteService) {
        this.rduTramiteService = rduTramiteService;
    }
    
    
    public Tramite getTramiteById(Long idTramite) {
        
        return null;

    }

    public Long insertFirma(Firma firm) {

        return rduFirmaService.insertFirma(firm);

    }

    public CertificadoDto selectFirmaAdminEstatusArea(CertificadoDto firm) {
        Certificado cert = this.rduFirmaService.selectFirmaAdminByEstatus(new Certificado(firm.getIdEstatusCertificado(), firm.getIdCatArea()));
        if (cert != null && cert.getIdCertificado() != null) {
            CertificadoDto cerDto = new CertificadoDto();
            BeanUtils.copyProperties(cert, cerDto);
            BeanUtils.copyProperties(cert.getCatArea(), cerDto.getCatAreaDto());
            BeanUtils.copyProperties(cert.getCatEstatusCertificado(), cerDto.getCatEstatusCertificadoDto());
            return cerDto;

        }
        return null;
    }

    public CertificadoDto selectFirmaAdminByInd(CertificadoDto firm) {
        Certificado cer = this.rduFirmaService.selectFirmaAdminByEstatus(new Certificado(firm.getIdEstatusCertificado(), firm.getIdCatArea()));
        CertificadoDto certDto = new CertificadoDto();
        BeanUtils.copyProperties(cer, certDto);

        return certDto;
    }

    public void setRduTablerosService(RduTablerosService rduTablerosService) {
        this.rduTablerosService = rduTablerosService;
    }

    public List<Tramite> cargarSolicitudes(long idUsuario) {
        //System.out.println("************ entra RduFlujosGeneralesBeanImpl.CargarSolicitudes.");
        return this.rduTablerosService.cargarSolicitudes(idUsuario);
    }

    public int eliminarSolicitud(SolicitudPreparacionDto solicitudPreparacionDto) {
        int rtRespuesta = 0;
        rtRespuesta = this.rduProcesosService.eliminarSolicitud(solicitudPreparacionDto);
        return rtRespuesta;

    }
    
    public int eliminarPromocion(SolicitudPreparacionDto solicitudPreparacionDto) {
        int rtRespuesta = 0;
        rtRespuesta = this.rduProcesosService.eliminarPromocion(solicitudPreparacionDto);
        return rtRespuesta;

    }

    public long copiarSolicitud(Tramite prmTramite) {
        long idNvaSolicitud = 0;
        idNvaSolicitud = this.rduTablerosService.copiarSolicitud(prmTramite);
        return idNvaSolicitud;
    }

    public int actualizarTramite(Tramite tramite) {
        return rduTablerosService.actualizarSolicitud(tramite);
    }

    public FolioDto selectDynamicFolio(Folio folio) {

        Folio fo = null;// this.rduFirmaService.getDinamicFolio(folio);
        FolioDto folioDto = new FolioDto();
        if (fo != null) {
            BeanUtils.copyProperties(fo, folioDto);
            return folioDto;
        } else {
            return null;
        }



    }

    public int insertFeps(TramiteDto prmTramite) {
        try {
            //this.rduFirmaService.copiarSolicitudEnSigmar(prmTramite);
            return this.rduPagosFepsService.insertFeps(prmTramite);
            //return 2;
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }

    public int insertFepsSagpat(TramitePatente prmTramite) {
        try {
            //this.rduFirmaService.copiarSolicitudEnSigmar(prmTramite);
            return this.rduPagosFepsService.insertFepsSagpat(prmTramite);
            //return 2;
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }

    public FepsRecibidos selectFepsByFolio(Long fepsFolio) {
        try {
            //debe regresar siempre nulo para continuacion de insercion firma
            return this.rduPagosFepsService.selectFepsByFolio(fepsFolio);
        } catch (Exception e) {
            log.error("Ocurrio un error al obtener folio feps   " + e.getLocalizedMessage(), e);
            //regresar instancia significa que el proceso de firma se interrumpe
            return new FepsRecibidos();
        }
    }

    public int insertarpago(Pago pago) {
        return rduTablerosService.insertarpago(pago);
    }

    public TramiteDto obtenerSolicitudes(int idTramite) {
        return rduTablerosService.obtenerSolicitudes(idTramite);
    }

    public int actualizarPago(Pago pago) {
        return rduTablerosService.actualizarPago(pago);
    }

    public int insertarAnexos(Anexos anexo) {
        return rduAnexosService.insertarAnexos(anexo);
    }

    public int insertarAnexosDto(List<AnexosViewDto> anexosDto) {
        return this.rduAnexosService.insertarAnexosDto(anexosDto);
    }

    public List<CodigosPostales> selectByIdCodigosPostales(String codigoPostal) {
        return rduCodigosPostalesService.selectByIdCodigosPostales(codigoPostal);
    }

    public List<Pago> buscarPago(long pago) {
        return rduTablerosService.buscarPago(pago);
    }

    public String validatePhrase(String tmp) throws Exception {
        return this.rduFirmaPatenteService.validatePhrase(tmp);
    }

    public List<ExpedientesDto> obtenerExpedientesPorUsr(List<Integer> usuarios, Integer idArea, Integer idTipoSolicitud,
            Integer ultimaSemana, Integer ultimoMes, String fechaInicio, String fechaFin) {
        return this.rduTablerosService.obtenerExpedientesPorUsr(usuarios, idArea, idTipoSolicitud, ultimaSemana,
                ultimoMes, fechaInicio, fechaFin);
    }

    public int insertarAlertas(SisAlerta alerta) {
        int insertar = 0;
        List<SisAlerta> listAlertas;

        listAlertas = this.rduComunesService.selectAlertas(alerta);
        System.out.println(" ** FechaExpira ==> " + alerta.getFechaExpira());
        System.out.println(" ** listAlertas.size ==> " + listAlertas.size());

        if (listAlertas.isEmpty()) {
            return this.rduComunesService.insertarAlertas(alerta);
        } else {
            return insertar;
        }
    }

    public List<SisAlerta> selectAlertas(SisAlerta alerta) {
        return this.rduComunesService.selectAlertas(alerta);
    }

    public int insertFirmaAdmin(CertificadoDto firmaDto) {
        Certificado firma = new Certificado();
        BeanUtils.copyProperties(firmaDto, firma);
        return this.rduFirmaService.insertFirmaAdmin(firma);

    }

    public int updateFirmaAdmin(CertificadoDto firmaDto) {
        Certificado firma = new Certificado();
        BeanUtils.copyProperties(firmaDto, firma);
        return this.rduFirmaService.updateFirmaAdmin(firma);
    }

    public FirmaDto loadFirma(Long idFirm) {
        Firma firma = this.rduFirmaService.loadFirma(idFirm);
        if (firma != null) {
            FirmaDto firmaDto = new FirmaDto();
            BeanUtils.copyProperties(firma, firmaDto);
            return firmaDto;
        } else {
            return null;
        }
    }

    public Integer updateFirma(FirmaDto firmDto) {
        Firma firma = new Firma();
        BeanUtils.copyProperties(firmDto, firma);
        return this.rduFirmaService.updateFirma(firma);
    }

    public List<CertificadoDto> loadAllActiveCerts(Integer idArea) {
        List<CertificadoDto> allCerts = new ArrayList<CertificadoDto>();
        CertificadoDto certDto;
        for (Certificado cert : Util.checkListNull(this.rduFirmaService.loadAllActiveCerts(idArea))) {
            certDto = new CertificadoDto();
            BeanUtils.copyProperties(cert, certDto);
            BeanUtils.copyProperties(cert.getCatEstatusCertificado(), certDto.getCatEstatusCertificadoDto());
            BeanUtils.copyProperties(cert.getCatArea(), certDto.getCatAreaDto());
            allCerts.add(certDto);
        }
        return allCerts;
    }

    public Date getSysDate() {
        return rduFuncionesSimplesService.getSysDate();
    }

    public int insertBitacoraErrores(BitacoraErrores bitacora) {
        return this.rduComunesService.insertBitacoraErrores(bitacora);
    }

    public List<BitacoraErrores> selectBitacoraErrores() {
        return this.rduComunesService.selectBitacoraErrores();
    }

    public List<Tramite> selectTramite() {
        return this.rduTablerosService.selectTramite();
    }

    public int eliminarBitacoraErrores(Short idBitacora) {
        return this.rduComunesService.eliminarBitacoraErrores(idBitacora);
    }

    public List<Firma> selectFirmas() {
        return this.rduFirmaService.selectFirmas();
    }

    public int udapteFirmasEnvio(Firma firma) {
        return this.rduFirmaService.udapteFirmasEnvio(firma);
    }

    public String jobeliminarAcuse() {
        return this.rduProcesosService.jobeliminarAcuse();
    }

    public int insertarAnexosDtoPatente(List<AnexosViewDto> anexosDto) {
        return this.rduAnexosService.insertarAnexosPatente(anexosDto);
    }

    public List<SolicitudPreparacionDto> extraerSolicitudesPreparacion(List<Integer> usuarios, int idTipoTramite) {
        return this.rduTablerosService.extraerSolicitudesPreparacion(usuarios, idTipoTramite);
    }

    public int selectsecuencia() {
        return this.rduProcesosService.selectsecuencia();
    }

    public int insertarAnexosPago(Anexos anexo) {
        return this.rduAnexosService.insertarAnexosPago(anexo);
    }

    public int copiarSolicitudEnSagpat(TramitePatente prmTramite, int paginas) {
        try {


            //  String folios[]=               this.rduFirmaPatenteService.generarFoliosSagpat(prmTramite, 10);
            // prmTramite.setFoliosSeries(folios);
            return this.rduFirmaPatenteService.copiarSolicitudEnSagpat(prmTramite, paginas);
        } catch (Exception e) {

            log.error("Ocurrio un error al copiar solicitud al sagpat " + e.getLocalizedMessage(), e);
            e.printStackTrace();

            return 0;
        }

    }

    public String[] getFoliosSagpat(String expediente, Date today) {
        String folios[] = null;
        try {
            folios = this.rduFirmaPatenteService.generarFoliosSagpat(expediente, today);
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println(" >> " + e.getMessage());
            System.out.println(" >>>> " + e.getLocalizedMessage());

            return null;

        }
        return folios;
    }

    public int actualizarPromocionMarca(TramitePromocionMarca tramite) {
        return rduTablerosService.actualizarPromocionMarca(tramite);
    }

    public Promovente selectPromovente(Long id) {
        Promovente prm = this.rduComunesService.selectPromovente(id);
        return prm;
    }

    public List<Promovente> selectPromoventeByPerfil(Integer idPerfil) {
        return this.rduComunesService.selectPromoventeByPerfil(idPerfil);
    }

    public Integer updateSelectiveFirma(Firma firma) {
        return this.rduFirmaService.updateSelectiveFirma(firma);
    }

    public List<Anexos> obtenerAnexosByTramite(Long idTramite) {
        return rduAnexosService.getAnexosByTramite(idTramite);
    }

    public List<Pago> selectPagoByTramiteId(Long idTramite) {

        return this.rduPagoService.selectByTramiteId(idTramite);

    }

    public Firma obternerFirmaByTramite(Long idTramite) {
        return rduFirmaService.obternerFirmaByTramite(idTramite);
    }

    public List<NotificacionView> consultarNotificacionView(Long oficioSalida) {
        return rduNotificacionesService.consultarNotificacionView(oficioSalida);
    }

    public List<NotificacionView> consultarNotificacionView(String codOficina, Long anio, Long numOficio) {
        return rduNotificacionesService.consultarNotificacionView(codOficina, anio, numOficio);
    }

    public List<Notificacion> consultarNotificaciones(Integer idUsuario) {
        return rduNotificacionesService.consultarNotificaciones(idUsuario);
    }
    
    public Notificacion selectNotificacionesById(Integer idNotificacion) {
        return rduNotificacionesService.selectNotificacionesById(idNotificacion);
    }
    

    public int insertarNotificacion(Notificacion notificacion) {
        return rduNotificacionesService.insertarNotificacion(notificacion);
    }

    public int updateNotificacion(Notificacion notificacion) {
        return this.rduNotificacionesService.updateNotificacion(notificacion);
    }

    public Long saveNotificacionFirma(NotificacionFirma notifFirma) {

        return this.rduNotificacionesService.saveNotificacionFirma(notifFirma);

    }

    public List<Notificacion> selectNotificacionesByIds(List<Integer> cadenaIds){
        return this.rduNotificacionesService.selectNotificacionesByIds(cadenaIds);
    }

    public int deleteNotificacionesByIds(List<Integer> cadenaIds){
            return this.rduNotificacionesService.deleteNotificacionesByIds(cadenaIds);
    }

    public List<Notificacion>getNotificacionesUserLoad(Integer idUserLoad, Integer promovente){
        return this.rduNotificacionesService.getNotificacionesUserLoad(idUserLoad, promovente);
    }
    
    public List<Notificacion>getNotificacionesTitular(String titular, Integer promovente){
        return this.rduNotificacionesService.getNotificacionesTitular(titular, promovente);
    }

    public Firma obternerFirmaByExpediente(Long expediente, String expedienteSat){
        return this.rduFirmaService.obternerFirmaByExpediente(expediente, expedienteSat);
    }

    public Firma selectFirmaByPrimaryKey(Long idFirma) {
        return this.rduFirmaService.selectFirmaByPrimaryKey(idFirma); //To change body of generated methods, choose Tools | Templates.
    }


    public List<Notificacion> getNotificacionesByDate(String fechaInicio, String fechaFin, Integer ultimaSemana,Integer ultimoMes, Integer idUsuarioFirmante){
        return this.rduNotificacionesService.getNotificacionesByDate(fechaInicio, fechaFin, ultimaSemana, ultimoMes, idUsuarioFirmante);
    }

    public Notificacion getNotificacionesByFolio(String folio) {
        return rduNotificacionesService.getNotificacionesByFolio(folio);
    }

    public List<ExpedientesDto> obtenerExpedientesNot(List<Integer> usuarios, Integer idArea, Integer idTipoSolicitud,
            Integer ultimaSemana, Integer ultimoMes, String fechaInicio, String fechaFin) {
        return rduTablerosService.obtenerExpedientesNot(usuarios, idArea, idTipoSolicitud, ultimaSemana,
                ultimoMes, fechaInicio, fechaFin);
    }
     public List<ExpedientesDto> obtenerExpedientesProm(List<Integer> usuarios, Integer idArea, Integer idTipoSolicitud,
            Integer ultimaSemana, Integer ultimoMes, String fechaInicio, String fechaFin) {
        return rduTablerosService.obtenerExpedientesProm(usuarios, idArea, idTipoSolicitud, ultimaSemana,
                ultimoMes, fechaInicio, fechaFin);
    }

    public ArchivoDigitalDto obtenerAcusePdf(Long idFirma) {
        return rduTablerosService.obtenerAcusePdf(idFirma);
    }

    public ArchivoDigitalDto obtenerAnexoXml(Long idFirma) {
        return rduTablerosService.obtenerAnexoXml(idFirma);
    }

    public ArchivoDigitalDto obtenerAcuseLogo(Long idTamite) {
        return rduTablerosService.obtenerAcuseLogo(idTamite);
    }

    public Integer obtenerTotalExpedientesPorUsr(List<Integer> usuarios, Integer idArea, Integer idTipoSolicitud, Integer ultimaSemana, Integer ultimoMes, String fechaInicio, String fechaFin) {
        return rduTablerosService.obtenerTotalExpedientesPorUsr(usuarios, idArea, idTipoSolicitud, ultimaSemana, ultimoMes, fechaInicio, fechaFin);
    }

    public List<ExpedientesDto> obtenerExpedientesPaginadosPorUsr(List<Integer> usuarios, Integer idArea, Integer idTipoSolicitud, Integer ultimaSemana, Integer ultimoMes, String fechaInicio, String fechaFin, int start, int maxPerPage) {
        return rduTablerosService.obtenerExpedientesPaginadosPorUsr(usuarios, idArea, idTipoSolicitud, ultimaSemana, ultimoMes, fechaInicio, fechaFin, start, maxPerPage);
    }

    public List<ExpedientesDto> obtenerExpedientesNotPaginados(List<Integer> usuarios, Integer idArea, Integer idTipoSolicitud, Integer ultimaSemana, Integer ultimoMes, String fechaInicio, String fechaFin, Integer first, Integer pageSize) {
        return rduTablerosService.obtenerExpedientesNotPaginados(usuarios, idArea, idTipoSolicitud, ultimaSemana, ultimoMes, fechaInicio, fechaFin, first, pageSize);
    }

    public List<ExpedientesDto> obtenerExpedientesPromPaginados(List<Integer> usuarios, Integer idArea, Integer idTipoSolicitud, Integer ultimaSemana, Integer ultimoMes, String fechaInicio, String fechaFin, Integer first, Integer pageSize) {
        return rduTablerosService.obtenerExpedientesPromPaginados(usuarios, idArea, idTipoSolicitud, ultimaSemana, ultimoMes, fechaInicio, fechaFin, first, pageSize);
    }

    public int obtenerTotalExpedientesNot(List<Integer> usuarios, Integer idArea, Integer idTipoSolicitud, Integer ultimaSemana, Integer ultimoMes, String fechaInicio, String fechaFin) {
        return rduTablerosService.obtenerTotalExpedientesNot(usuarios, idArea, idTipoSolicitud, ultimaSemana, ultimoMes, fechaInicio, fechaFin);
    }

    public int obtenerTotalExpedientesProm(List<Integer> usuarios, Integer idArea, Integer idTipoSolicitud, Integer ultimaSemana, Integer ultimoMes, String fechaInicio, String fechaFin) {
        return rduTablerosService.obtenerTotalExpedientesProm(usuarios, idArea, idTipoSolicitud, ultimaSemana, ultimoMes, fechaInicio, fechaFin);
    }
    
    public int deleteAnexosByIds(Long idAnexo) {
        return rduAnexosService.deleteAnexosByIds(idAnexo);
    }

    public int deleteByTypeAnexo(Anexos anexo) {
        return rduAnexosService.deleteByTypeAnexo(anexo);
    }

    public Anexos obtenerAnexosDynamic(Anexos anexo) {
        return rduAnexosService.obtenerAnexosDynamic(anexo);
    }

    public SolicitudPreparacionDto selectPromoByPrimaryKey(Long idTramitePromocionPatente) {
        return this.rduTablerosService.selectPromoByPrimaryKey(idTramitePromocionPatente);
    }

    public Solicitud selectByExpedienteDivisional(String oficina, Integer numExp, Integer serExp, String tipExp) {
        return this.rduTramiteService.selectByExpedienteDivisional(oficina, numExp, serExp, tipExp);
    }
    
    /*     para sigappi      */
    
    public List<KffoliosNotificacion> selectByOficioSalida(String codbarras) {
        return rduSigappiService.selectByOficioSalida(codbarras);
    }

    public List<KffoliosNotificacion> selectANotificarByCodInteresado(Integer codInteresado) {
        return rduSigappiService.selectANotificarByCodInteresado(codInteresado); //To change body of generated methods, choose Tools | Templates.
    }
    
    public int insert(KffoliosNotificacion kffoliosNotificacion) {
        return rduSigappiService.insert(kffoliosNotificacion);
    }
    
    public int insertSelective(KffoliosNotificacion kffoliosNotificacion) {
        return rduSigappiService.insertSelective(kffoliosNotificacion);
    }

    public List<KfFolios> selectKfFoliosByCodbarras(String codbarras) {
        return rduSigappiService.selectKfFoliosByCodbarras(codbarras); //To change body of generated methods, choose Tools | Templates.
    }

    public int insert(KfFolios kfFolios) {
        return rduSigappiService.insert(kfFolios); //To change body of generated methods, choose Tools | Templates.
    }

    public List<KfAlmacenar> selectKfAlmacenarByCodbarras(String codbarras) {
        return rduSigappiService.selectKfAlmacenarByCodbarras(codbarras); //To change body of generated methods, choose Tools | Templates.
    }

    public List<KfAlmacenar> selectKfAlmacenarByTitle(String title) {
        return rduSigappiService.selectKfAlmacenarByTitle(title); //To change body of generated methods, choose Tools | Templates.
    }

    public int insert(KfAlmacenar kfAlmacenar) {
        return rduSigappiService.insert(kfAlmacenar); //To change body of generated methods, choose Tools | Templates.
    }

    public List<KfContenedores> selectKfContenedoresByTitle(String title) {
        return rduSigappiService.selectKfContenedoresByTitle(title); //To change body of generated methods, choose Tools | Templates.
    }

    public int insert(KfContenedores kfContenedores) {
        return rduSigappiService.insert(kfContenedores); //To change body of generated methods, choose Tools | Templates.
    }

    public List<KfContenedores> selectKfContenedoresByPC(String pc) {
        return rduSigappiService.selectKfContenedoresByPC(pc); //To change body of generated methods, choose Tools | Templates.
    }

    public List<SolicitudInteresados> selectSolicitudInteresadosByCodInteresado(Integer codInteresado) {
        return rduSigappiService.selectSolicitudInteresadosByCodInteresado(codInteresado); //To change body of generated methods, choose Tools | Templates.
    }

    public List<SolicitudInteresados> selectSolicitudInteresadosByTitle(String title) {
        return rduSigappiService.selectSolicitudInteresadosByTitle(title); //To change body of generated methods, choose Tools | Templates.
    }

    public int insert(SolicitudInteresados solicitudInteresados) {
        return rduSigappiService.insert(solicitudInteresados); //To change body of generated methods, choose Tools | Templates.
    }

    public List<TiposRelacion> selectTiposRelacionByCodRelacion(Integer codRelacion) {
        return rduSigappiService.selectTiposRelacionByCodRelacion(codRelacion); //To change body of generated methods, choose Tools | Templates.
    }

    public int insert(TiposRelacion tiposRelacion) {
        return rduSigappiService.insert(tiposRelacion); //To change body of generated methods, choose Tools | Templates.
    }

    public List<UsuariosSigappi> selectUsuariosSigappiByCveUsuario(String cveUsuario) {
        return rduSigappiService.selectUsuariosSigappiByCveUsuario(cveUsuario); //To change body of generated methods, choose Tools | Templates.
    }

    public int insert(UsuariosSigappi usuariosSigappi) {
        return rduSigappiService.insert(usuariosSigappi); //To change body of generated methods, choose Tools | Templates.
    }
    
    public List<TiposRelacion> listTiposRelacion() {
        return rduSigappiService.listTiposRelacion();
    }
    
    public List<SolicitudInteresados> selectSolicitudInteresadosByCodInteresadoAndSecuencia(Integer codInteresado, Integer secuencia) {
        return rduSigappiService.selectSolicitudInteresadosByCodInteresadoAndSecuencia(codInteresado, secuencia);
    }
    
    public int updateNotificationSubscription(String title, Integer codInteresado, Integer secuencia) {
        return rduSigappiService.updateNotificationSubscription(title, codInteresado, secuencia);
    }

    public List<Anexo> selectAnexoByCodbarras(String codbarras) {
        return rduSigappiService.selectAnexoByCodbarras(codbarras); //To change body of generated methods, choose Tools | Templates.
    }

    public List<Area> selectAreaByCveArea(Integer cveArea) {
        return rduSigappiService.selectAreaByCveArea(cveArea); //To change body of generated methods, choose Tools | Templates.
    }

    public List<DerechosAsociados> selectDerechosAsociadosByTitle(String title) {
        return rduSigappiService.selectDerechosAsociadosByTitle(title); //To change body of generated methods, choose Tools | Templates.
    }

    public List<FigurasJuridicas> selectFigurasJuridicasByNumFigura(Integer numFigura) {
        return rduSigappiService.selectFigurasJuridicasByNumFigura(numFigura); //To change body of generated methods, choose Tools | Templates.
    }

    public List<Interesados> selectInteresadosByCodInteresado(Integer codInteresado) {
        return rduSigappiService.selectInteresadosByCodInteresado(codInteresado); //To change body of generated methods, choose Tools | Templates.
    }

    public List<TipoAnexo> selectTipoAnexoByCategoriaDivisionSeccion(Integer categoria, Integer division, Integer seccion) {
        return rduSigappiService.selectTipoAnexoByCategoriaDivisionSeccion(categoria, division, seccion); //To change body of generated methods, choose Tools | Templates.
    }

    public NotificacionFirma selectNotificacionFirmaByPrimaryKey(Integer id) {
        return this.rduNotificacionesService.selectNotificacionFirmaByPrimaryKey(id); //To change body of generated methods, choose Tools | Templates.
    }

    public int insertSolicitudRevision(SolicitudRevision solicitudRevision) {
        return rduSigappiService.insertSolicitudRevision(solicitudRevision);
    }

    public List<SolicitudRevision> findSolicitudRevisionByCodInteresadoAndSecuencia(Integer codInteresado, Integer secuencia) {
        return rduSigappiService.findSolicitudRevisionByCodInteresadoAndSecuencia(codInteresado, secuencia);
    }
    
    public List<SolicitudRevision> findSolicitudRevisionByCodInteresadoAndSecuenciaAndSession(Integer codInteresado, Integer secuencia, Integer idSolicitud) {
        return rduSigappiService.findSolicitudRevisionByCodInteresadoAndSecuenciaAndSession(codInteresado, secuencia, idSolicitud);
    }

    public int updateSolicitudRevision(SolicitudRevision solicitudRevision) {
        return rduSigappiService.updateSolicitudRevision(solicitudRevision);
    }

    public KfContenedores findKfContenedoresByTitleOrPc(String id) {
        return rduSigappiService.findKfContenedoresByTitleOrPc(id);
    }
    
    public void deleteSolicitudRevision(SolicitudRevision solicitudRevision) {
        rduSigappiService.deleteSolicitudRevision(solicitudRevision);
    }
    
    public int appendSolicitudRevision(SolicitudRevision solicitudRevision) {
        return rduSigappiService.appendSolicitudRevision(solicitudRevision);
    }
  
    public int insertSolicitudWeb(SolicitudWeb solicitudWeb) {
        return rduSigappiService.insertSolicitudWeb(solicitudWeb);
    }
    
    public int updateSolicitudWeb(SolicitudWeb solicitudWeb) {
        return rduSigappiService.updateSolicitudWeb(solicitudWeb);
    }
    
    public List<SolicitudWeb> findAllSolicitudWebByUserAndStatus(Integer idPromovente, Integer idStatus) {
        return rduSigappiService.findAllSolicitudWebByUserAndStatus(idPromovente, idStatus);
    }
    
    public List<SolicitudWeb> findAllSolicitudWebByUser(Integer idPromovente) {
        return rduSigappiService.findAllSolicitudWebByUser(idPromovente);
    }
    
    public SolicitudWeb findSolicitudWebBySession(Integer idSolicitud) {
        return rduSigappiService.findSolicitudWebBySession(idSolicitud);
    }
    
    public Integer nextSequence() {
        return rduSigappiService.nextSequence();
    }
    
}
