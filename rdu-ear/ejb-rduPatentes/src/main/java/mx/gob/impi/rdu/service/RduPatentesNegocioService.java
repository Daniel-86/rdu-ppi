package mx.gob.impi.rdu.service;

import java.util.List;
import mx.gob.impi.rdu.dto.SolicitudPreparacionDto;
import mx.gob.impi.rdu.persistence.model.ApoderadoXTramitePatente;
import mx.gob.impi.rdu.persistence.model.ImagenDibujo;
import mx.gob.impi.rdu.persistence.model.InventorXTramitePatente;
import mx.gob.impi.rdu.persistence.model.Persona;
import mx.gob.impi.rdu.persistence.model.PersonaNotXTramitePatente;
import mx.gob.impi.rdu.persistence.model.Prioridad;
import mx.gob.impi.rdu.persistence.model.PrioridadXTramitePatente;
import mx.gob.impi.rdu.persistence.model.SolicitanteXTramitePatente;
import mx.gob.impi.rdu.persistence.model.TramitePatente;
import mx.gob.impi.sagpat.persistence.model.DatosSolicitudPct;
import mx.gob.impi.sagpat.persistence.model.DatosSolicitudPctMU;
import mx.gob.impi.sagpat.persistence.model.Solicitud;

public interface RduPatentesNegocioService {

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

    TramitePatente insertarTramitePatente(TramitePatente tramite) throws Exception;

    public List<Persona> obtenerPersonasNot(Long idTramite);

    int insertarApoderadoXTramite(ApoderadoXTramitePatente apoderadoXTramitePatente);

    int eliminarApoderadosXTramite(Long idTramite);

    TramitePatente selectTramite(Long idTramitePatente);

    int insertarPersonaNotXTramitePatente(PersonaNotXTramitePatente personaNotXTramitePatente);

    int eliminarPersonaNotXTramitePatente(Long idTramite);

    TramitePatente obtenerTramitePatenteById(Long idTramite) throws Exception;

    boolean updateTramitePatenteByPrimaryKey(TramitePatente tramite) throws Exception;

    int updateTramitePatenteSigned(TramitePatente tramite) throws Exception;

    int updateTramitePatente(TramitePatente tramite);
    int updateTramitePromocionPatente(SolicitudPreparacionDto tramite);

    Solicitud getExpDivisional(Solicitud exp);
   
    public Solicitud buscaExpedienteDivisional(String oficina, Integer numExp, Integer serExp, String tipExp);

    void insertImagenDibujo(TramitePatente tramite);

    void actualizarImagenDibujo(TramitePatente tramite);

    TramitePatente getDatosTramitePatente(Long idTramite);

    DatosSolicitudPct consultarDatosPCt(String idSolicitudPct);
    
    List<DatosSolicitudPctMU> consultarDatosPCtMU(String idSolicitudPct);
    
    void actualizarPersona(Persona persona);

    TramitePatente obtenerTramitePromocioneById(Long idTramite) throws Exception;
}
