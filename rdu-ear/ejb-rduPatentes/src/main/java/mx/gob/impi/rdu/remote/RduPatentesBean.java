package mx.gob.impi.rdu.remote;

import java.util.List;
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
import mx.gob.impi.rdu.util.PromocionNoExisteException;
import mx.gob.impi.rdu.util.SolicitudNoExisteException;
import mx.gob.impi.sagpat.persistence.model.DatosSolicitudPct;
import mx.gob.impi.sagpat.persistence.model.DatosSolicitudPctMU;
import mx.gob.impi.sagpat.persistence.model.ResultadoOficioPromocion;
import mx.gob.impi.sagpat.persistence.model.Solicitud;
import mx.gob.impi.sagpat.persistence.model.SolicitudTitularDto;
import mx.gob.impi.sagpat.persistence.model.TramiteOficio;

public interface RduPatentesBean {

    String holaMundo();

    boolean insertSolicitanteXTramitePatente(SolicitanteXTramitePatente record);

    List<SolicitanteXTramitePatente> obtenerSolicitantesXTramite(Long idTramite);

    int deleteBySolXTramite(Long idTramite);

    boolean insertInventorXTramitePatente(InventorXTramitePatente record);

    List<InventorXTramitePatente> obtenerInventoresXTramite(Long idTramite);

    int deleteInventoresByTramite(Long idTramite);

    int deletePrioridadByPrimaryKey(Long idPrioridad);

    Prioridad selectPrioridadByPrimaryKey(Long idPrioridad);

    long insertPrioridad(Prioridad record);

    int updatePrioridadByPrimaryKey(Prioridad record);

    boolean insertRelPrioridadTramite(PrioridadXTramitePatente record);

    List<PrioridadXTramitePatente> obtenerPrioridadXTramite(Long idTramite);

    int deleteRelPrioridadByTramite(Long idTramite);

    List<Persona> selectSolicitanteTramitePatente(Long idTramite);

    List<Persona> selectInventorTramitePatente(Long idTramite);

    List<Prioridad> selectPrioridadesTramitePatente(Long idTramite);

    public List<Persona> obtenerApoderados(Long idTramite);

    public List<Persona> obtenerPersonasNot(Long idTramite);

    int insertarApoderadoXTramite(ApoderadoXTramitePatente apoderadoXTramitePatente);

    int eliminarApoderadosXTramite(Long idTramite);

    TramitePatente selectTramite(Long idTramitePatente);

    int insertarPersonaNotXTramitePatente(PersonaNotXTramitePatente personaNotXTramitePatente);

    TramitePatente obtenerTramitePatenteById(Long idTramite) throws Exception;

    TramitePatente insertarTramitePatente(TramitePatente tramite) throws Exception;

    boolean updateTramitePatenteByPrimaryKey(TramitePatente tramite) throws Exception;

    int updateTramitePatenteSigned(TramitePatente tramite) throws Exception;

    int updateTramitePatente(TramitePatente tramite);
    
    int updateTramitePromocionPatente(SolicitudPreparacionDto tramite);

    Solicitud getExpDivisional(Solicitud exp);
    
    public Solicitud buscaExpedienteDivisional(String oficina, Integer numExp, Integer serExp, String tipExp);

    TramitePatente getDatosTramitePatente(Long idTramite);

    DatosSolicitudPct consultarDatosPCt(String idSolicitudPct);
    List<DatosSolicitudPctMU> consultarDatosPCtMU(String idSolicitudPct);
    //****Metodos para JMS
    public String getCatalogoJms(String nombreCatalogo);
    public String getCatTipoPromocionJms(String nombreCatalogo); 
    public String getTipoPromByOficioJms(String idOficio);
    public String setTramitePromoPatJms(PromocionesPatentes promocionesPatentes);
    
    public List<CatAreaPromPatentes> getCatalogo(String cadenaXML);
    public List<CatTipoPromPatentes> getCatTipoPromPat(String cadenaXML);
    public List<TipoPromPatByOficio> getTipoPromByOficio(String cadenaXML);
    public  String setTramitePromoPatJmsAdmin(String cadenaXML);
    //****
    List<CatTipoPromPatentes> selectTipoPromociones();
    PromocionesConOficioDto getNumeroOficio(PromocionesConOficioDto promocionesConOficio);
    
    ResultadoOficioPromocion obtenerDatosOficioPromocion(TramiteOficio tramiteOficio);
    int guardarPromocionPatente(TramitePromocionPatente promocion) throws SolicitudNoExisteException, PromocionNoExisteException;
    
    void actualizarPersona(Persona persona);
    
    TramitePatente obtenerTramitePromocioneById(Long idTramite) throws Exception;
    
    List<SolicitudTitularDto> selectTitularesByPromocion(SolicitudTitularDto solicitudTitular);
}
