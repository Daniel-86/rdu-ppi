package mx.gob.impi.rdu.service.impl;

import java.util.ArrayList;
import java.util.List;
import mx.gob.impi.rdu.dto.ExpedientesDto;
import mx.gob.impi.rdu.dto.SolicitudPreparacionDto;
import mx.gob.impi.rdu.persistence.model.Pago;
import mx.gob.impi.rdu.persistence.model.Tramite;
import mx.gob.impi.rdu.persistence.model.CatArea;
import mx.gob.impi.rdu.persistence.model.CatTiposolicitud;
import mx.gob.impi.rdu.persistence.model.CatSubtiposolicitud;
import mx.gob.impi.rdu.persistence.model.CatEstatus;
import mx.gob.impi.rdu.persistence.model.Persona;
import mx.gob.impi.rdu.persistence.model.SolicitanteXTramite;
import mx.gob.impi.rdu.persistence.model.TramitePatente;
import mx.gob.impi.rdu.persistence.model.TramitePromocionMarca;
import java.util.Iterator;
import mx.gob.impi.rdu.dto.ArchivoDigitalDto;
import mx.gob.impi.rdu.dto.TramiteDto;
import mx.gob.impi.rdu.persistence.mappers.TramiteMapper;
import mx.gob.impi.rdu.persistence.mappers.CatSubtiposolicitudMapper;
import mx.gob.impi.rdu.persistence.mappers.CatTiposolicitudMapper;
import mx.gob.impi.rdu.persistence.mappers.CatAreaMapper;
import mx.gob.impi.rdu.persistence.mappers.CatEstatusMapper;
import mx.gob.impi.rdu.persistence.mappers.PagoMapper;
import mx.gob.impi.rdu.persistence.mappers.SolicitanteXTramiteMapper;
import mx.gob.impi.rdu.persistence.mappers.TramitePatenteMapper;
import mx.gob.impi.rdu.persistence.mappers.TramitePromocionMarcaMapper;
import mx.gob.impi.rdu.persistence.mappers.TramitePromocionPatenteMapper;
import mx.gob.impi.rdu.service.RduTablerosService;
import mx.gob.impi.rdu.util.TipoTramiteEnum;
import org.apache.log4j.Logger;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

public class RduTablerosServiceImpl implements RduTablerosService {

    private Logger lger = Logger.getLogger(this.getClass());
    private TramiteMapper rduTramiteMapper;
    private CatSubtiposolicitudMapper rduCatSubtiposolicitudMapper;
    private CatTiposolicitudMapper rduCatTiposolicitudMapper;
    private CatAreaMapper rduCatAreaMapper;
    private CatEstatusMapper rduCatEstatusMapper;
    private PagoMapper rduPagoMapper;
    private SolicitanteXTramiteMapper rduSolicitanteXTramiteMapper;
    private TramitePatenteMapper rduTramitePatenteMapper;
    private TramitePromocionMarcaMapper rduTramitePromocionMarcaMapper;
    private TramitePromocionPatenteMapper rduTramitePromocionPatenteMapper;

    public void setRduPagoMapper(PagoMapper rduPagoMapper) {
        this.rduPagoMapper = rduPagoMapper;
    }

    public void setRduTramitePromocionMarcaMapper(TramitePromocionMarcaMapper rduTramitePromocionMarcaMapper) {
        this.rduTramitePromocionMarcaMapper = rduTramitePromocionMarcaMapper;
    }

    public void setRduTramitePatenteMapper(TramitePatenteMapper rduTramitePatenteMapper) {
        this.rduTramitePatenteMapper = rduTramitePatenteMapper;
    }

    public void setRduTramiteMapper(TramiteMapper rduTramiteMapper) {
        this.rduTramiteMapper = rduTramiteMapper;
    }  

    public void setRduCatAreaMapper(CatAreaMapper rduCatAreaMapper) {
        this.rduCatAreaMapper = rduCatAreaMapper;
    }

    public void setRduCatEstatusMapper(CatEstatusMapper rduCatEstatusMapper) {
        this.rduCatEstatusMapper = rduCatEstatusMapper;
    }

    public void setRduCatSubtiposolicitudMapper(CatSubtiposolicitudMapper rduCatSubtiposolicitudMapper) {
        this.rduCatSubtiposolicitudMapper = rduCatSubtiposolicitudMapper;
    }

    public void setRduCatTiposolicitudMapper(CatTiposolicitudMapper rduCatTiposolicitudMapper) {
        this.rduCatTiposolicitudMapper = rduCatTiposolicitudMapper;
    }

    public void setRduSolicitanteXTramiteMapper(SolicitanteXTramiteMapper rduSolicitanteXTramiteMapper) {
        this.rduSolicitanteXTramiteMapper = rduSolicitanteXTramiteMapper;
    }

    public List<SolicitudPreparacionDto> extraerSolicitudesPreparacion(List<Integer> usuarios, int idTipoTramite) {
        //System.out.println("************ ejecutando RduTablerosServiceImpl.extraerSolicitudesPreparacion.");
        List<SolicitudPreparacionDto> retTramites = new ArrayList();
        try {
            if (idTipoTramite == TipoTramiteEnum.SOL_PATENTES.getIdTipoTramite()) {
                retTramites = this.rduTramitePatenteMapper.solicitudesByUsuarios(usuarios, TipoTramiteEnum.SOL_PATENTES.getIdTipoTramite());
            }else if (idTipoTramite == TipoTramiteEnum.SOL_SIT.getIdTipoTramite()) {
                retTramites = this.rduTramitePatenteMapper.solicitudesByUsuarios(usuarios, TipoTramiteEnum.SOL_SIT.getIdTipoTramite());
            }else if(idTipoTramite == TipoTramiteEnum.PROM_PATENTES.getIdTipoTramite()){
                System.out.println("------------> buscando promociones by usuarios. idTipoTramite: "+idTipoTramite);
//                System.out.println("------------> Usuarios: ");
//                for(Integer i:usuarios){
//                    System.out.print(i+",");
//                }
                retTramites = this.rduTramitePromocionPatenteMapper.promocionesByUsuarios(usuarios, idTipoTramite);
                System.out.println("-----------------> retTramites.size(): "+retTramites.size());
                
                System.out.println("----------------> asignando valores...");
                SolicitudPreparacionDto promocion;
                for(int i=0;i<retTramites.size();i++){
                    promocion=retTramites.get(i);
                    promocion.setIdSubtiposolicitud(promocion.getIdTipoPromocion()+10000);
                    promocion.setIdTramite(promocion.getIdPromocion());
                }
            }            
        } catch (Exception e) {
            System.err.println(e);
            lger.error("Ocurrio un error en el metodo RduTablerosServiceImpl.extraerSolicitudesPreparacion: ", e);
        }
        return retTramites;

    }
    /*
    private List<SolicitudPreparacionDto> recuperaSolicitudes(List<Integer> usuarios, int idTipoTramite) {
    List<SolicitudPreparacionDto> retSolicitudes = new ArrayList();
    //Integer prIdUsuario = (int) idUsuario;
    Integer prIdUsuario = 0;
    SolicitudPreparacionDto solPrep;
    try {
    if (idTipoTramite == TipoTramiteEnum.SOL_MARCAS.getIdTipoTramite()) {
    retSolicitudes=this.rduTramiteMapper.solicitudesByUsuarios(usuarios, TipoTramiteEnum.SOL_MARCAS.getIdTipoTramite());
    }
    if (idTipoTramite == TipoTramiteEnum.SOL_PATENTES.getIdTipoTramite()) {
    retSolicitudes=this.rduTramitePatenteMapper.solicitudesByUsuarios(usuarios, TipoTramiteEnum.SOL_PATENTES.getIdTipoTramite());
    }
    if (idTipoTramite == TipoTramiteEnum.PROM_MARCAS.getIdTipoTramite()) {
    retSolicitudes = this.rduTramitePromocionMarcaMapper.solicitudesByUsuarios(usuarios, TipoTramiteEnum.PROM_MARCAS.getIdTipoTramite());
    }

    } catch (Exception e) {
    lger.error("Ocurrio un error en el metodo RduTablerosServiceImpl.recuperaSolicitudes: ", e);
    }
    return retSolicitudes;
    }
     */

    public List<Tramite> cargarSolicitudes(long idUsuario) {
        //System.out.println("************ ejecutando RduTablerosServiceImpl.CargarSolicitudes.");
        List<Tramite> retTramites = new ArrayList();
        List<Tramite> obtTramites = new ArrayList();
        try {
            obtTramites = this.recuperaTramites(idUsuario);
            //obtener informacion adicional
            for (Iterator iter = obtTramites.iterator(); iter.hasNext();) {
                Tramite oTramite = (Tramite) iter.next();                
                oTramite.setSubTipoSolicitud(this.obtenerSubTipoSolicitud(oTramite.getIdSubtiposolicitud()));
                oTramite.setEstatus(this.obtenerEstatus(oTramite.getIdEstatus()));
                oTramite.setSolicitantes(this.recuperaSolicitantesId(oTramite.getIdTramite()));
                retTramites.add(oTramite);
            }
        } catch (Exception e) {
            lger.error("Ocurrio un error en el metodo RduTablerosServiceImpl.CargarSolicitudes: ", e);
        }
        return retTramites;

    }

    private List<Persona> recuperaSolicitantesId(long tramite) {
        List<Persona> retSolicitantes = new ArrayList();
        Persona oSolicitante;
        List<SolicitanteXTramite> solicitantesTramite = this.rduSolicitanteXTramiteMapper.obtenerSolicitantesXTramite(tramite);
        if (null != solicitantesTramite) {
            if (solicitantesTramite.size() > 0) {
                for (Iterator iter = solicitantesTramite.iterator(); iter.hasNext();) {
                    SolicitanteXTramite oSolicitanteTramite = (SolicitanteXTramite) iter.next();
                    oSolicitante = new Persona();
                    oSolicitante.setIdSolicitante(oSolicitanteTramite.getIdSolicitante());
                    retSolicitantes.add(oSolicitante);
                }

            }
        }

        return retSolicitantes;
    }

    private List<Tramite> recuperaTramites(long idUsuario) {
        Integer prIdUsuario = (int) idUsuario;
        List<Tramite> retTramites = this.rduTramiteMapper.selectByUsuario(prIdUsuario);
        return retTramites;
    }  

    private CatSubtiposolicitud obtenerSubTipoSolicitud(Long idSubTipoSolicitud) {
        CatSubtiposolicitud retCatSubtiposolicitud = new CatSubtiposolicitud();
        CatTiposolicitud oCatTiposolicitud = new CatTiposolicitud();
        CatArea oCatArea = new CatArea();

        retCatSubtiposolicitud = this.rduCatSubtiposolicitudMapper.selectByPrimaryKey(Long.valueOf(idSubTipoSolicitud));
        oCatTiposolicitud = this.rduCatTiposolicitudMapper.selectByPrimaryKey(retCatSubtiposolicitud.getIdTiposolicitud());
        oCatArea = this.rduCatAreaMapper.selectByPrimaryKey(oCatTiposolicitud.getIdArea());
        oCatTiposolicitud.setArea(oCatArea);
        retCatSubtiposolicitud.setTipoSolicitud(oCatTiposolicitud);

        return retCatSubtiposolicitud;
    }

    private CatEstatus obtenerEstatus(Long idEstatus) {
        CatEstatus retCatEstatus = new CatEstatus();
        retCatEstatus = this.rduCatEstatusMapper.selectByPrimaryKey(idEstatus);

        return retCatEstatus;
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public long copiarSolicitud(Tramite prmTramite) {
        long idNvaSolicitud = 0;
        try {

            idNvaSolicitud = this.rduTramiteMapper.insert(prmTramite);
            System.out.println("************ entra RduTableroServiceImpl.copiarSolicitud:" + prmTramite.getIdTramite());
            idNvaSolicitud = idNvaSolicitud == 1 ? prmTramite.getIdTramite() : idNvaSolicitud;
        } catch (Exception e) {
            lger.error("Ocurrio un error en el metodo RduTablerosServiceImpl.copiarSolicitud: ", e);
        }
        return idNvaSolicitud;

    }

    public int actualizarSolicitud(Tramite prmTramite) {
        int tramitesActualizados = 0;
        try {
            tramitesActualizados = this.rduTramiteMapper.updateByPrimaryKeySelective(prmTramite);
        } catch (Exception e) {
            lger.error("Ocurrio un error en el metodo RduTablerosServiceImpl.copiarSolicitud: ", e);
        }
        return tramitesActualizados;
    }

    public int insertarpago(Pago pago) {
        int tramitesInsertados = 0;
        try {
            tramitesInsertados = this.rduPagoMapper.insert(pago);
        } catch (Exception e) {
            lger.error("Ocurrio un error en el metodo RduTablerosServiceImpl.copiarSolicitud: ", e);
        }
        return tramitesInsertados;
    }

    public TramiteDto obtenerSolicitudes(int idTramite) {
     //Obtener solicitudes 
        TramiteDto tramite = null;
        lger.info("  --------------**********-----------idTramite:   " + idTramite);

        SolicitudPreparacionDto promocion=null;
        
        if (this.rduTramitePatenteMapper.selectByPrimaryKey(Long.valueOf(idTramite)) != null) {
            TramitePatente patentes = new TramitePatente();
            patentes = this.rduTramitePatenteMapper.selectByPrimaryKey(Long.valueOf(idTramite));
            tramite=new TramiteDto();
            tramite.setIdTramite(patentes.getIdTramitePatente());
//            tramite.setTipoTramite(2);
            //tramite.setTipoTramite(TipoTramiteEnum.SOL_PATENTES.getIdTipoTramite());
        tramite.setTipoTramite(TipoTramiteEnum.SOL_SIT.getIdTipoTramite());
        }else if((promocion=rduTramitePromocionPatenteMapper.selectByPrimaryKey(Long.valueOf(idTramite)))!=null){
            System.out.println("-----------> El tramite es una promocion de patentes...");
            tramite=new TramiteDto();
            tramite.setIdTramite(promocion.getIdPromocion());
            tramite.setTipoTramite(TipoTramiteEnum.PROM_PATENTES.getIdTipoTramite());
        }

        return tramite;
    }

    public int actualizarPago(Pago pago) {
        int tramitesActualizados = 0;
        try {
            tramitesActualizados = this.rduPagoMapper.updatePASE(pago);
        } catch (Exception e) {
            lger.error("Ocurrio un error en el metodo RduTablerosServiceImpl.actualizarPago: ", e);
        }
        return tramitesActualizados;
    }

   public List<Pago> buscarPago(long pago) {
        List<Pago> listaPagos = this.rduPagoMapper.selectByTramiteId(pago);
        return listaPagos;
    }

    //@Deprecated
    public List<ExpedientesDto> obtenerExpedientesPorUsr(List<Integer> usuarios, Integer idArea, Integer idTipoSolicitud,
            Integer ultimaSemana, Integer ultimoMes, String fechaInicio, String fechaFin) {
        List<ExpedientesDto> expedientesResult = new ArrayList<ExpedientesDto>();
        
        lger.info("Metodo obtenerExpedientesPorUsr");
        lger.info("Tipo de Solicitud");
        lger.info(idTipoSolicitud);
        
        if(idTipoSolicitud == 11 || idTipoSolicitud == 4){
            lger.info("Buscando por Patentes");
            expedientesResult = rduTramiteMapper.expedientesPatentesByUsuario(usuarios, idArea, idTipoSolicitud,
                ultimaSemana, ultimoMes, fechaInicio, fechaFin);
        } 
        else if(idTipoSolicitud == 1 || idTipoSolicitud == 3){
            lger.info("Buscando por marcas");
            expedientesResult = this.rduTramiteMapper.expedientesMarcasByUsuario(usuarios, idArea, idTipoSolicitud,
                ultimaSemana, ultimoMes, fechaInicio, fechaFin);
        }        
        
        return expedientesResult;
    }

    public List<ExpedientesDto> obtenerExpedientesNot(List<Integer> usuarios, Integer idArea, Integer idTipoSolicitud,
            Integer ultimaSemana, Integer ultimoMes, String fechaInicio, String fechaFin) {
        return this.rduTramiteMapper.expedienteNotByIdUsuario(usuarios, idArea, idTipoSolicitud, ultimaSemana, ultimoMes, fechaInicio, fechaFin);
    }

    public List<ExpedientesDto> obtenerExpedientesProm(List<Integer> usuarios, Integer idArea, Integer idTipoSolicitud,
            Integer ultimaSemana, Integer ultimoMes, String fechaInicio, String fechaFin) {
        return this.rduTramiteMapper.expedientesPromByIdUsuario(usuarios, idArea, idTipoSolicitud, ultimaSemana, ultimoMes, fechaInicio, fechaFin);
    }

    public List<Tramite> selectTramite() {
        return this.rduTramiteMapper.selectTramite();
    }

    public int actualizarPromocionMarca(TramitePromocionMarca tramite) {
        int tramitesActualizados = 0;
        try {
            tramitesActualizados = this.rduTramitePromocionMarcaMapper.updateByPrimaryKeySelective(tramite);
        } catch (Exception e) {
            lger.error("Ocurrio un error en el metodo RduTablerosServiceImpl.actualizarPromocionMarca: ", e);
        }
        return tramitesActualizados;
    }

    public ArchivoDigitalDto obtenerAcusePdf(Long idFirma) {
        return rduTramiteMapper.obtenerAcusePdf(idFirma);
    }

    public ArchivoDigitalDto obtenerAnexoXml(Long idFirma) {
        return rduTramiteMapper.obtenerAnexoXml(idFirma);
    }

    public ArchivoDigitalDto obtenerAcuseLogo(Long idTamite) {
        return rduTramiteMapper.obtenerAcuseLogo(idTamite);
    }

    public Integer obtenerTotalExpedientesPorUsr(List<Integer> usuarios, Integer idArea, Integer idTipoSolicitud, Integer ultimaSemana, Integer ultimoMes, String fechaInicio, String fechaFin) {
        int total = 0;
        Integer t;
        
         if((idTipoSolicitud == 11 || idTipoSolicitud == 4)){
            lger.info("Buscando por Patentes");
            t = rduTramiteMapper.obtenerTotalExpedientesPatentesByUsuario(usuarios, idArea, idTipoSolicitud, ultimaSemana, ultimoMes, fechaInicio, fechaFin);
            
            total = t == null ? 0 : t.intValue();
        
        } else{
             
             lger.info("Buscando por SIT");
            t = rduTramiteMapper.obtenerTotalExpedientesPatentesByUsuario(usuarios, idArea, idTipoSolicitud, ultimaSemana, ultimoMes, fechaInicio, fechaFin);
            
            total = t == null ? 0 : t.intValue();
         }
         
         
         return total;

    }

    public List<ExpedientesDto> obtenerExpedientesPaginadosPorUsr(List<Integer> usuarios, Integer idArea, Integer idTipoSolicitud, Integer ultimaSemana, Integer ultimoMes, String fechaInicio, String fechaFin, int start, int maxPerPage) {
        List<ExpedientesDto> expedientesResult = new ArrayList<ExpedientesDto>();
        
        lger.info("Metodo obtenerExpedientesPaginadosPorUsr");
        lger.info("Tipo de Solicitud");
        lger.info(idTipoSolicitud);
        
        if(idTipoSolicitud == 11 || idTipoSolicitud == 4){
            lger.info("Buscando por Patentes");
            expedientesResult = rduTramiteMapper.expedientesPatentesPaginadosByUsuario(usuarios, idArea, idTipoSolicitud,
                ultimaSemana, ultimoMes, fechaInicio, fechaFin, start, (start + maxPerPage));
        }else{
             
           lger.info("Buscando por Sit");
            expedientesResult = rduTramiteMapper.expedientesPatentesPaginadosByUsuario(usuarios, idArea, idTipoSolicitud,
                ultimaSemana, ultimoMes, fechaInicio, fechaFin, start, (start + maxPerPage));
         }
            
        
        return expedientesResult;
    }

    public List<ExpedientesDto> obtenerExpedientesNotPaginados(List<Integer> usuarios, Integer idArea, Integer idTipoSolicitud, Integer ultimaSemana, Integer ultimoMes, String fechaInicio, String fechaFin, Integer first, Integer pageSize) {
        return rduTramiteMapper.obtenerExpedientesNotPaginadosByUsuario(usuarios, idArea, idTipoSolicitud, ultimaSemana, ultimoMes, fechaInicio, fechaFin, first, (first + pageSize));
    }

    public List<ExpedientesDto> obtenerExpedientesPromPaginados(List<Integer> usuarios, Integer idArea, Integer idTipoSolicitud, Integer ultimaSemana, Integer ultimoMes, String fechaInicio, String fechaFin, Integer first, Integer pageSize) {
        return rduTramiteMapper.obtenerExpedientesPromPaginadosByUsuario(usuarios, idArea, idTipoSolicitud, ultimaSemana, ultimoMes, fechaInicio, fechaFin, first, (first + pageSize));
    }

    public int obtenerTotalExpedientesNot(List<Integer> usuarios, Integer idArea, Integer idTipoSolicitud, Integer ultimaSemana, Integer ultimoMes, String fechaInicio, String fechaFin) {
        return rduTramiteMapper.obtenerTotalExpedientesNot(usuarios, idArea, idTipoSolicitud, ultimaSemana, ultimoMes, fechaInicio, fechaFin);
    }

    public int obtenerTotalExpedientesProm(List<Integer> usuarios, Integer idArea, Integer idTipoSolicitud, Integer ultimaSemana, Integer ultimoMes, String fechaInicio, String fechaFin) {
        return rduTramiteMapper.obtenerTotalExpedientesProm(usuarios, idArea, idTipoSolicitud, ultimaSemana, ultimoMes, fechaInicio, fechaFin);
    }

    /**
     * @param rduTramitePromocionPatenteMapper the rduTramitePromocionPatenteMapper to set
     */
    public void setRduTramitePromocionPatenteMapper(TramitePromocionPatenteMapper rduTramitePromocionPatenteMapper) {
        this.rduTramitePromocionPatenteMapper = rduTramitePromocionPatenteMapper;
    }

    public SolicitudPreparacionDto selectPromoByPrimaryKey(Long idTramitePromocionPatente) {
        return rduTramitePromocionPatenteMapper.selectByPrimaryKey(idTramitePromocionPatente);
    }
    
    
}
