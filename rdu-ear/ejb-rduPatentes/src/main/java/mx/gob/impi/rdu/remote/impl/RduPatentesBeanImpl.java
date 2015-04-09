package mx.gob.impi.rdu.remote.impl;

import java.util.List;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.interceptor.Interceptors;
import mx.gob.impi.rdu.dto.PromocionesConOficioDto;
import mx.gob.impi.rdu.dto.SolicitudPreparacionDto;
import mx.gob.impi.rdu.persistence.model.ApoderadoXTramitePatente;
import mx.gob.impi.rdu.persistence.model.CatAreaPromPatentes;
import mx.gob.impi.rdu.persistence.model.CatTipoPromPatentes;
import mx.gob.impi.rdu.persistence.model.ImagenDibujo;
import mx.gob.impi.rdu.persistence.model.InventorXTramitePatente;
import mx.gob.impi.rdu.persistence.model.Persona;
import mx.gob.impi.rdu.persistence.model.PersonaNotXTramitePatente;
import mx.gob.impi.rdu.persistence.model.Prioridad;
import mx.gob.impi.rdu.persistence.model.PrioridadXTramitePatente;
import mx.gob.impi.rdu.persistence.model.PromocionesPatentes;
import mx.gob.impi.rdu.persistence.model.SolicitanteXTramitePatente;
import mx.gob.impi.rdu.persistence.model.TipoPromPatByOficio;
import mx.gob.impi.rdu.persistence.model.TramitePatente;
import mx.gob.impi.rdu.persistence.model.TramitePromocionPatente;
import mx.gob.impi.sagpat.persistence.model.DatosSolicitudPct;
import mx.gob.impi.sagpat.persistence.model.DatosSolicitudPctMU;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.ejb.interceptor.SpringBeanAutowiringInterceptor;

import mx.gob.impi.rdu.remote.RduPatentesBeanLocal;
import mx.gob.impi.rdu.remote.RduPatentesBeanRemote;
import mx.gob.impi.rdu.service.AdminJmsService;
import mx.gob.impi.rdu.service.JmsService;
import mx.gob.impi.rdu.service.RduPatentesNegocioService;
import mx.gob.impi.rdu.service.RduPromocionesService;
import mx.gob.impi.rdu.util.PromocionNoExisteException;
import mx.gob.impi.rdu.util.SolicitudNoExisteException;
import mx.gob.impi.sagpat.persistence.model.ResultadoOficioPromocion;
import mx.gob.impi.sagpat.persistence.model.Solicitud;
import mx.gob.impi.sagpat.persistence.model.SolicitudTitularDto;
import mx.gob.impi.sagpat.persistence.model.TramiteOficio;
import mx.gob.impi.sagpat.service.PromocionesService;

@Stateless(name = "EJBRduPatentesBean", mappedName = "EJBRduPatentesBean", description = "")
@Interceptors(SpringBeanAutowiringInterceptor.class)
public class RduPatentesBeanImpl implements RduPatentesBeanRemote, RduPatentesBeanLocal {

    @Autowired
    @Qualifier("rduPatentesNegocioServiceImpl")
    private RduPatentesNegocioService rduPatentesNegocioServiceImpl;
    
    @Autowired
    @Qualifier("rduPromocionesServiceImpl")
    private RduPromocionesService rduPromocionesService;
    
    @Autowired
    @Qualifier("jmsServiceImpl")
    private JmsService jmsServiceImpl;
    
    @Autowired
    @Qualifier("adminJmsServiceImpl")
    private AdminJmsService adminJmsServiceImpl;
    @Autowired
    private PromocionesService promocionesService;

    // Metodos Para Mensajes 
    public String getCatalogoJms(String nombreCatalogo) {
        return jmsServiceImpl.getCatalogoJms(nombreCatalogo);
    }

    public String getCatTipoPromocionJms(String nombreCatalogo) {
        return jmsServiceImpl.getCatTipoPromocionJms(nombreCatalogo);
    }

    public String getTipoPromByOficioJms(String idOficio) {
        return jmsServiceImpl.getTipoPromByOficioJms(idOficio);
    }

    public String setTramitePromoPatJms(PromocionesPatentes promocionesPatentes) {
        return jmsServiceImpl.setTramitePromoPatJms(promocionesPatentes);
    }

    public List<CatAreaPromPatentes> getCatalogo(String cadenaXML) {
        return adminJmsServiceImpl.getCatalogo(cadenaXML);
    }

    public List<CatTipoPromPatentes> getCatTipoPromPat(String cadenaXML) {
        return adminJmsServiceImpl.getCatTipoPromPat(cadenaXML);
    }

    public List<TipoPromPatByOficio> getTipoPromByOficio(String cadenaXML) {
        return adminJmsServiceImpl.getTipoPromByOficio(cadenaXML);
    }

    public String setTramitePromoPatJmsAdmin(String cadenaXML) {
        return adminJmsServiceImpl.setTramitePromoPatJms(cadenaXML);
    }

    // ***************************************************************
    
    public void setJmsServiceImpl(JmsService jmsServiceImpl) {
        this.jmsServiceImpl = jmsServiceImpl;
    }

    public void setAdminJmsServiceImpl(AdminJmsService adminJmsServiceImpl) {
        this.adminJmsServiceImpl = adminJmsServiceImpl;
    }

    // ***************************************************************

    public void setRduPromocionesService(RduPromocionesService rduPromocionesService) {
        this.rduPromocionesService = rduPromocionesService;
    }

    public void setRduPatentesNegocioServiceImpl(RduPatentesNegocioService rduPatentesNegocioServiceImpl) {
        this.rduPatentesNegocioServiceImpl = rduPatentesNegocioServiceImpl;
    }

    public boolean insertSolicitanteXTramitePatente(SolicitanteXTramitePatente record) {
        return rduPatentesNegocioServiceImpl.insertSolicitanteXTramitePatente(record);

    }

    public List<SolicitanteXTramitePatente> obtenerSolicitantesXTramite(Long idTramite) {
        return rduPatentesNegocioServiceImpl.obtenerSolicitantesXTramite(idTramite);
    }

    public int deleteBySolXTramite(Long idTramite) {
        return rduPatentesNegocioServiceImpl.deleteBySolXTramite(idTramite);
    }

    public boolean insertInventorXTramitePatente(InventorXTramitePatente record) {
        return rduPatentesNegocioServiceImpl.insertInventorXTramitePatente(record);

    }

    public List<InventorXTramitePatente> obtenerInventoresXTramite(Long idTramite) {
        return rduPatentesNegocioServiceImpl.obtenerInventoresXTramite(idTramite);

    }

    public int deleteInventoresByTramite(Long idTramite) {
        return rduPatentesNegocioServiceImpl.deleteInventoresByTramite(idTramite);

    }

    public int deletePrioridadByPrimaryKey(Long idPrioridad) {
        return rduPatentesNegocioServiceImpl.deletePrioridadByPrimaryKey(idPrioridad);
    }

    public Prioridad selectPrioridadByPrimaryKey(Long idPrioridad) {
        return rduPatentesNegocioServiceImpl.selectPrioridadByPrimaryKey(idPrioridad);
    }

    public long insertPrioridad(Prioridad record) {
        return rduPatentesNegocioServiceImpl.insertPrioridad(record);
    }

    public int updatePrioridadByPrimaryKey(Prioridad record) {
        return rduPatentesNegocioServiceImpl.updatePrioridadByPrimaryKey(record);
    }

    public boolean insertRelPrioridadTramite(PrioridadXTramitePatente record) {
        return rduPatentesNegocioServiceImpl.insertRelPrioridadTramite(record);

    }

    public List<PrioridadXTramitePatente> obtenerPrioridadXTramite(Long idTramite) {
        return rduPatentesNegocioServiceImpl.obtenerPrioridadXTramite(idTramite);
    }

    public int deleteRelPrioridadByTramite(Long idTramite) {
        return rduPatentesNegocioServiceImpl.deleteRelPrioridadByTramite(idTramite);
    }

    public List<Persona> selectSolicitanteTramitePatente(Long idTramite) {
        return rduPatentesNegocioServiceImpl.selectSolicitanteTramitePatente(idTramite);

    }

    public List<Persona> selectInventorTramitePatente(Long idTramite) {
        return rduPatentesNegocioServiceImpl.selectInventorTramitePatente(idTramite);

    }

    public List<Prioridad> selectPrioridadesTramitePatente(Long idTramite) {
        return rduPatentesNegocioServiceImpl.selectPrioridadesTramitePatente(idTramite);
    }

    /**
     * Default constructor
     */
    public RduPatentesBeanImpl() {
    }

    public String holaMundo() {
        return this.rduPatentesNegocioServiceImpl.holaMundo();
    }

    public List<Persona> obtenerApoderados(Long idTramite) {
        return rduPatentesNegocioServiceImpl.obtenerPersonasNot(idTramite);
    }

    public List<Persona> obtenerPersonasNot(Long idTramite) {
        return rduPatentesNegocioServiceImpl.obtenerPersonasNot(idTramite);
    }

    public int insertarApoderadoXTramite(ApoderadoXTramitePatente apoderadoXTramitePatente) {
        return rduPatentesNegocioServiceImpl.insertarApoderadoXTramite(apoderadoXTramitePatente);
    }

    public int eliminarApoderadosXTramite(Long idTramite) {
        return rduPatentesNegocioServiceImpl.eliminarApoderadosXTramite(idTramite);
    }

    public TramitePatente selectTramite(Long idTramitePatente) {
        return rduPatentesNegocioServiceImpl.selectTramite(idTramitePatente);
    }

    public int eliminarPersonaNotXTramitePatente(Long idTramite) {
        return rduPatentesNegocioServiceImpl.eliminarPersonaNotXTramitePatente(idTramite);
    }

    public int insertarPersonaNotXTramitePatente(PersonaNotXTramitePatente personaNotXTramitePatente) {
        return rduPatentesNegocioServiceImpl.insertarPersonaNotXTramitePatente(personaNotXTramitePatente);
    }

    public TramitePatente obtenerTramitePatenteById(Long idTramite) throws Exception {
        return rduPatentesNegocioServiceImpl.obtenerTramitePatenteById(idTramite);
    }
    
    public TramitePatente obtenerTramitePromocioneById(Long idTramite) throws Exception {
        return rduPatentesNegocioServiceImpl.obtenerTramitePromocioneById(idTramite);
    }    

    public TramitePatente insertarTramitePatente(TramitePatente tramite) throws Exception {
        return rduPatentesNegocioServiceImpl.insertarTramitePatente(tramite);
    }

    public boolean updateTramitePatenteByPrimaryKey(TramitePatente tramite) throws Exception {
        return rduPatentesNegocioServiceImpl.updateTramitePatenteByPrimaryKey(tramite);
    }

    public int updateTramitePatenteSigned(TramitePatente tramite) throws Exception {
        return rduPatentesNegocioServiceImpl.updateTramitePatenteSigned(tramite);
    }

    public int updateTramitePatente(TramitePatente tramite) {
        return rduPatentesNegocioServiceImpl.updateTramitePatente(tramite);
    }
    
    public int updateTramitePromocionPatente(SolicitudPreparacionDto tramite) {
        return rduPatentesNegocioServiceImpl.updateTramitePromocionPatente(tramite);
    }

    public Solicitud getExpDivisional(Solicitud exp) {
        return rduPatentesNegocioServiceImpl.getExpDivisional(exp);
    }

    public TramitePatente getDatosTramitePatente(Long idTramite) {
        return rduPatentesNegocioServiceImpl.getDatosTramitePatente(idTramite);
    }

    public DatosSolicitudPct consultarDatosPCt(String idSolicitudPct) {
        return rduPatentesNegocioServiceImpl.consultarDatosPCt(idSolicitudPct);
    }
     
    public List<DatosSolicitudPctMU> consultarDatosPCtMU(String idSolicitudPct) {
        return rduPatentesNegocioServiceImpl.consultarDatosPCtMU(idSolicitudPct);
    }

    public List<CatTipoPromPatentes> selectTipoPromociones() {
        return rduPromocionesService.selectTipoPromociones();
    }

    public PromocionesConOficioDto getNumeroOficio(PromocionesConOficioDto promocionesConOficio) {
        return rduPromocionesService.getNumeroOficio(promocionesConOficio);
    }

    public ResultadoOficioPromocion obtenerDatosOficioPromocion(TramiteOficio tramiteOficio) {
        return promocionesService.obtenerDatosOficioPromocion(tramiteOficio);
    }

    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    public int guardarPromocionPatente(TramitePromocionPatente promocion) throws SolicitudNoExisteException, PromocionNoExisteException {
        if (!promocionesService.solicitudValida(promocion)) {
            throw new SolicitudNoExisteException();
        }
        System.out.println("--------------> invocando guardarPromocionPatente desde ejb...");
        return promocionesService.guardarPromocionPatente(promocion);
    }

    public void actualizarPersona(Persona persona) {
        rduPatentesNegocioServiceImpl.actualizarPersona(persona);
    }

    public List<SolicitudTitularDto> selectTitularesByPromocion(SolicitudTitularDto solicitudTitular) {
        return promocionesService.selectTitularesByPromocion(solicitudTitular);
    }

    public Solicitud buscaExpedienteDivisional(String oficina, Integer numExp, Integer serExp, String tipExp) {
        return rduPatentesNegocioServiceImpl.buscaExpedienteDivisional(oficina, numExp, serExp, tipExp);
    } 

            
}
