package mx.gob.impi.rdu.service.impl;

import java.util.ArrayList;
import java.util.List;
import mx.gob.impi.rdu.dto.SolicitudPreparacionDto;
import mx.gob.impi.rdu.persistence.mappers.ApoderadoXTramitePatenteMapper;
import mx.gob.impi.rdu.persistence.mappers.CatSubtiposolicitudMapper;
import mx.gob.impi.rdu.persistence.mappers.CatTiposolicitudMapper;
import mx.gob.impi.rdu.persistence.mappers.DatoscontactoMapper;
import mx.gob.impi.rdu.persistence.mappers.DomicilioMapper;
import mx.gob.impi.rdu.persistence.mappers.ImagenDibujoMapper;
import mx.gob.impi.rdu.persistence.model.ImagenDibujo;
import mx.gob.impi.rdu.persistence.model.TramitePatente;

import org.springframework.beans.factory.annotation.Autowired;
import mx.gob.impi.rdu.persistence.mappers.InventorXTramitePatenteMapper;
import mx.gob.impi.rdu.persistence.mappers.PagoMapper;
import mx.gob.impi.rdu.persistence.mappers.PersonaMapper;
import mx.gob.impi.rdu.persistence.mappers.PersonaNotXTramitePatenteMapper;
import mx.gob.impi.rdu.persistence.mappers.PrioridadMapper;
import mx.gob.impi.rdu.persistence.mappers.PrioridadXTramitePatenteMapper;
import mx.gob.impi.rdu.persistence.mappers.ReivindicacionMapper;
import mx.gob.impi.rdu.persistence.mappers.SolicitanteXTramitePatenteMapper;
import mx.gob.impi.rdu.persistence.mappers.TramitePatenteMapper;
import mx.gob.impi.rdu.persistence.mappers.TramitePersonaMapper;
import mx.gob.impi.rdu.persistence.mappers.TramitePromocionPatenteMapper;
import mx.gob.impi.rdu.persistence.model.ApoderadoXTramitePatente;
import mx.gob.impi.rdu.persistence.model.CatSubtiposolicitud;
import mx.gob.impi.rdu.persistence.model.CatTiposolicitud;
import mx.gob.impi.rdu.persistence.model.Datoscontacto;
import mx.gob.impi.rdu.persistence.model.Domicilio;
import mx.gob.impi.rdu.persistence.model.InventorXTramitePatente;
import mx.gob.impi.rdu.persistence.model.Pago;
import mx.gob.impi.rdu.persistence.model.Persona;
import mx.gob.impi.rdu.persistence.model.PersonaNotXTramitePatente;
import mx.gob.impi.rdu.persistence.model.Prioridad;
import mx.gob.impi.rdu.persistence.model.PrioridadXTramitePatente;
import mx.gob.impi.rdu.persistence.model.Reivindicacion;
import mx.gob.impi.rdu.persistence.model.SolicitanteXTramitePatente;
import mx.gob.impi.rdu.persistence.model.TramitePersona;
import mx.gob.impi.rdu.service.RduPatentesNegocioService;
import mx.gob.impi.rdu.util.Constantes;
import mx.gob.impi.sagpat.persistence.mappers.DatosSolicitudPctMapper;
import mx.gob.impi.sagpat.persistence.mappers.SolicitudMapper;
import mx.gob.impi.sagpat.persistence.model.DatosSolicitudPct;
import mx.gob.impi.sagpat.persistence.model.DatosSolicitudPctMU;
import mx.gob.impi.sagpat.persistence.model.Solicitud;
import org.apache.log4j.Logger;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

public class RduPatentesNegocioServiceImpl implements RduPatentesNegocioService {

    private Logger log = Logger.getLogger(this.getClass());
    @Autowired
    private SolicitanteXTramitePatenteMapper rduSolicitanteXTramitePatenteMapper;
    private InventorXTramitePatenteMapper rduInventorXTramitePatenteMapper;
    private PrioridadMapper rduPrioridadMapper;
    private PrioridadXTramitePatenteMapper rduPrioridadXTramitePatenteMapper;
    private PersonaMapper rduPersonaMapper;
    private ApoderadoXTramitePatenteMapper rduApoderadoXTramitePatenteMapper;
    private TramitePatenteMapper rduTramitePatenteMapper;
    private TramitePromocionPatenteMapper rduTramitePromocionPatenteMapper;
    private PersonaNotXTramitePatenteMapper rduPersonaNotXTramitePatenteMapper;
    private DomicilioMapper rduDomicilioMapper;
    private DatoscontactoMapper rduDatosContactoMapper;
    private SolicitudMapper solicitudMapper;
    private CatTiposolicitudMapper rduCatTiposolicitudMapper;
    private CatSubtiposolicitudMapper rduCatSubtiposolicitudMapper;
    private mx.gob.impi.sagpat.persistence.mappers.PersonaMapper personaMapperSagpat;
    private PagoMapper rduPagoMapper;
    private ImagenDibujoMapper rduImagenDibujoMapper;
    private ReivindicacionMapper rduReivindicacionMapper;
    private TramitePersonaMapper rduTramitePersonaMapper;
    private DatosSolicitudPctMapper datosSolicitudPctMapper;

    public void setDatosSolicitudPctMapper(DatosSolicitudPctMapper datosSolicitudPctMapper) {
        this.datosSolicitudPctMapper = datosSolicitudPctMapper;
    }

    public void setRduTramitePersonaMapper(TramitePersonaMapper rduTramitePersonaMapper) {
        this.rduTramitePersonaMapper = rduTramitePersonaMapper;
    }

    public void setRduReivindicacionMapper(ReivindicacionMapper rduReivindicacionMapper) {
        this.rduReivindicacionMapper = rduReivindicacionMapper;
    }

    public void setRduImagenDibujoMapper(ImagenDibujoMapper rduImagenDibujoMapper) {
        this.rduImagenDibujoMapper = rduImagenDibujoMapper;
    }

    public void setRduPagoMapper(PagoMapper rduPagoMapper) {
        this.rduPagoMapper = rduPagoMapper;
    }

    public String holaMundo() {
        String resultado = "entra";

        System.out.println("******entra AL>1: servicio.holamundo()");
        return resultado;

    }

    public void setRduCatSubtiposolicitudMapper(CatSubtiposolicitudMapper rduCatSubtiposolicitudMapper) {
        this.rduCatSubtiposolicitudMapper = rduCatSubtiposolicitudMapper;
    }

    public void setRduCatTiposolicitudMapper(CatTiposolicitudMapper rduCatTiposolicitudMapper) {
        this.rduCatTiposolicitudMapper = rduCatTiposolicitudMapper;
    }

    public void setPersonaMapperSagpat(mx.gob.impi.sagpat.persistence.mappers.PersonaMapper personaMapperSagpat) {
        this.personaMapperSagpat = personaMapperSagpat;
    }

    public void setSolicitudMapper(SolicitudMapper solicitudMapper) {
        this.solicitudMapper = solicitudMapper;
    }

    public void setRduDatosContactoMapper(DatoscontactoMapper rduDatosContactoMapper) {
        this.rduDatosContactoMapper = rduDatosContactoMapper;
    }

    public void setRduDomicilioMapper(DomicilioMapper rduDomicilioMapper) {
        this.rduDomicilioMapper = rduDomicilioMapper;
    }

    public void setRduPersonaNotXTramitePatenteMapper(PersonaNotXTramitePatenteMapper rduPersonaNotXTramitePatenteMapper) {
        this.rduPersonaNotXTramitePatenteMapper = rduPersonaNotXTramitePatenteMapper;
    }

    public ApoderadoXTramitePatenteMapper getRduApoderadoXTramitePatenteMapper() {
        return rduApoderadoXTramitePatenteMapper;
    }

    public void setRduApoderadoXTramitePatenteMapper(ApoderadoXTramitePatenteMapper rduApoderadoXTramitePatenteMapper) {
        this.rduApoderadoXTramitePatenteMapper = rduApoderadoXTramitePatenteMapper;
    }

    public void setRduPersonaMapper(PersonaMapper rduPersonaMapper) {
        this.rduPersonaMapper = rduPersonaMapper;
    }

    public void setRduPrioridadXTramitePatenteMapper(PrioridadXTramitePatenteMapper rduPrioridadXTramitePatenteMapper) {
        this.rduPrioridadXTramitePatenteMapper = rduPrioridadXTramitePatenteMapper;
    }

    public void setRduPrioridadMapper(PrioridadMapper rduPrioridadMapper) {
        this.rduPrioridadMapper = rduPrioridadMapper;
    }

    public void setRduInventorXTramitePatenteMapper(InventorXTramitePatenteMapper rduInventorXTramitePatenteMapper) {
        this.rduInventorXTramitePatenteMapper = rduInventorXTramitePatenteMapper;
    }

    public void setRduSolicitanteXTramitePatenteMapper(SolicitanteXTramitePatenteMapper rduSolicitanteXTramitePatenteMapper) {
        this.rduSolicitanteXTramitePatenteMapper = rduSolicitanteXTramitePatenteMapper;
    }

    public void setRduTramitePatenteMapper(TramitePatenteMapper rduTramitePatenteMapper) {
        this.rduTramitePatenteMapper = rduTramitePatenteMapper;
    }

    public boolean insertSolicitanteXTramitePatente(SolicitanteXTramitePatente record) {
        boolean resul = false;
        try {
            this.rduSolicitanteXTramitePatenteMapper.insert(record);

        } catch (Exception e) {
            System.out.println("******Errrorr>>>>>>>>>mensaje: " + e.getMessage() + " localized: " + e.getLocalizedMessage());
        }
        return resul;

    }

    public List<SolicitanteXTramitePatente> obtenerSolicitantesXTramite(Long idTramite) {
        List<SolicitanteXTramitePatente> listSolRel = new ArrayList<SolicitanteXTramitePatente>();
        try {
            listSolRel = this.rduSolicitanteXTramitePatenteMapper.obtenerSolicitantesXTramite(idTramite);

        } catch (Exception e) {
            System.out.println("******Errrorr>>>>>>>>>mensaje: " + e.getMessage() + " localized: " + e.getLocalizedMessage());
            return null;
        }
        return listSolRel;
    }

    public int deleteBySolXTramite(Long idTramite) {
        int resul = 0;
        try {
            resul = this.rduSolicitanteXTramitePatenteMapper.deleteByTramite(idTramite);



        } catch (Exception e) {
            System.out.println("******Errrorr>>>>>>>>>mensaje: " + e.getMessage() + " localized: " + e.getLocalizedMessage());

        }
        return resul;
    }

    public boolean insertInventorXTramitePatente(InventorXTramitePatente record) {
        boolean resul = false;
        try {
            this.rduInventorXTramitePatenteMapper.insertInventorXTramitePatente(record);

        } catch (Exception e) {
            System.out.println("******Errrorr>>>>>>>>>mensaje: " + e.getMessage() + " localized: " + e.getLocalizedMessage());
        }
        return resul;

    }

    public List<InventorXTramitePatente> obtenerInventoresXTramite(Long idTramite) {
        List<InventorXTramitePatente> listInvRel = new ArrayList<InventorXTramitePatente>();
        try {
            listInvRel = this.rduInventorXTramitePatenteMapper.obtenerInventoresXTramite(idTramite);

        } catch (Exception e) {
            System.out.println("******Errrorr>>>>>>>>>mensaje: " + e.getMessage() + " localized: " + e.getLocalizedMessage());
            return null;
        }
        return listInvRel;

    }

    public int deleteInventoresByTramite(Long idTramite) {
        int resul = 0;
        try {
            resul = this.rduInventorXTramitePatenteMapper.deleteInventoresByTramite(idTramite);



        } catch (Exception e) {
            System.out.println("******Errrorr>>>>>>>>>mensaje: " + e.getMessage() + " localized: " + e.getLocalizedMessage());

        }
        return resul;

    }

    public int deletePrioridadByPrimaryKey(Long idPrioridad) {
        int resul = 0;
        try {
            resul = this.rduPrioridadMapper.deleteByPrimaryKey(idPrioridad);



        } catch (Exception e) {
            System.out.println("******Errrorr>>>>>>>>>mensaje: " + e.getMessage() + " localized: " + e.getLocalizedMessage());

        }
        return resul;

    }

    public Prioridad selectPrioridadByPrimaryKey(Long idPrioridad) {

        Prioridad elemento = new Prioridad();
        try {
            elemento = this.rduPrioridadMapper.selectByPrimaryKey(idPrioridad);

        } catch (Exception e) {
            System.out.println("******Errrorr>>>>>>>>>mensaje: " + e.getMessage() + " localized: " + e.getLocalizedMessage());
            return null;
        }
        return elemento;
    }

    public long insertPrioridad(Prioridad record) {


        try {
            this.rduPrioridadMapper.insert(record);
            return record.getIdPrioridad();

        } catch (Exception e) {
            System.out.println("******Errrorr>>>>>>>>>mensaje: " + e.getMessage() + " localized: " + e.getLocalizedMessage());
            return 0;
        }

    }

    public int updatePrioridadByPrimaryKey(Prioridad record) {
        int resul = 0;
        try {
            resul = this.rduPrioridadMapper.updateByPrimaryKey(record);



        } catch (Exception e) {
            System.out.println("******Errrorr>>>>>>>>>mensaje: " + e.getMessage() + " localized: " + e.getLocalizedMessage());

        }
        return resul;
    }

    public boolean insertRelPrioridadTramite(PrioridadXTramitePatente record) {
        boolean resul = false;
        try {
            this.rduPrioridadXTramitePatenteMapper.insertRelPrioridadTramite(record);

        } catch (Exception e) {
            System.out.println("******Errrorr>>>>>>>>>mensaje: " + e.getMessage() + " localized: " + e.getLocalizedMessage());
        }
        return resul;
    }

    public List<PrioridadXTramitePatente> obtenerPrioridadXTramite(Long idTramite) {
        List<PrioridadXTramitePatente> listPrioridades = new ArrayList<PrioridadXTramitePatente>();
        try {
            listPrioridades = this.rduPrioridadXTramitePatenteMapper.obtenerPrioridadXTramite(idTramite);

        } catch (Exception e) {
            System.out.println("******Errrorr>>>>>>>>>mensaje: " + e.getMessage() + " localized: " + e.getLocalizedMessage());
            return null;
        }
        return listPrioridades;
    }

    public int deleteRelPrioridadByTramite(Long idTramite) {
        int resul = 0;
        try {
            resul = this.rduPrioridadXTramitePatenteMapper.deleteRelPrioridadByTramite(idTramite);



        } catch (Exception e) {
            System.out.println("******Errrorr>>>>>>>>>mensaje: " + e.getMessage() + " localized: " + e.getLocalizedMessage());

        }
        return resul;
    }

    public List<Persona> selectSolicitanteTramitePatente(Long idTramite) {
        List<Persona> listSolicitantes = new ArrayList<Persona>();
        try {
            listSolicitantes = this.rduPersonaMapper.selectSolicitanteTramitePatente(idTramite);

        } catch (Exception e) {
            System.out.println("******Errrorr>>>>>>>>>mensaje: " + e.getMessage() + " localized: " + e.getLocalizedMessage());
            return null;
        }
        return listSolicitantes;
    }

    public List<Persona> selectInventorTramitePatente(Long idTramite) {
        List<Persona> listinventores = new ArrayList<Persona>();
        try {
            listinventores = this.rduPersonaMapper.selectInventorTramitePatente(idTramite);

        } catch (Exception e) {
            System.out.println("******Errrorr>>>>>>>>>mensaje: " + e.getMessage() + " localized: " + e.getLocalizedMessage());
            return null;
        }
        return listinventores;
    }

    public List<Prioridad> selectPrioridadesTramitePatente(Long idTramite) {
        List<Prioridad> listprioridades = new ArrayList<Prioridad>();
        try {
            listprioridades = this.rduPrioridadMapper.selectPrioridadesTramitePatente(idTramite);

        } catch (Exception e) {
            System.out.println("******Errrorr>>>>>>>>>mensaje: " + e.getMessage() + " localized: " + e.getLocalizedMessage());
            return null;
        }
        return listprioridades;

    }

    public List<Persona> obtenerApoderados(Long idTramite) {
        try {
            return rduPersonaMapper.selectApoderados(idTramite);
        } catch (Exception e) {
            System.out.println("******Errrorr>>>>>>>>>mensaje: " + e.getMessage() + " localized: " + e.getLocalizedMessage());

        }
        return null;
    }

    public List<Persona> obtenerPersonasNot(Long idTramite) {
        try {
            return rduPersonaMapper.selectPersonasNot(idTramite);
        } catch (Exception e) {
            System.out.println("******Errrorr>>>>>>>>>mensaje: " + e.getMessage() + " localized: " + e.getLocalizedMessage());

        }
        return null;
    }

    public int insertarApoderadoXTramite(ApoderadoXTramitePatente apoderadoXTramitePatente) {
        try {
            return rduApoderadoXTramitePatenteMapper.insertByExample(apoderadoXTramitePatente);
        } catch (Exception e) {
            System.out.println("******Errrorr>>>>>>>>>mensaje: " + e.getMessage() + " localized: " + e.getLocalizedMessage());

        }
        return 0;
    }

    public int eliminarApoderadosXTramite(Long idTramite) {
        try {
            return rduApoderadoXTramitePatenteMapper.deleteByPrimaryKey(idTramite);
        } catch (Exception e) {
            System.out.println("******Errrorr>>>>>>>>>mensaje: " + e.getMessage() + " localized: " + e.getLocalizedMessage());

        }
        return 0;
    }

    public List<TramitePatente> selectTramite() {
        List<TramitePatente> listTramite = new ArrayList<TramitePatente>();
        try {
            listTramite = rduTramitePatenteMapper.selectTramitesPatentes();
        } catch (Exception e) {
            System.out.println("******Errrorr>>>>>>>>>mensaje: " + e.getMessage() + " localized: " + e.getLocalizedMessage());
        }
        return listTramite;
    }

    public int insertarPersonaNotXTramitePatente(PersonaNotXTramitePatente personaNotXTramitePatente) {
        try {
            return rduPersonaNotXTramitePatenteMapper.insertByExample(personaNotXTramitePatente);
        } catch (Exception e) {
            System.out.println("******Errrorr>>>>>>>>>mensaje: " + e.getMessage() + " localized: " + e.getLocalizedMessage());

        }
        return 0;
    }

    public int eliminarPersonaNotXTramitePatente(Long idTramite) {
        try {
            return rduPersonaNotXTramitePatenteMapper.deleteByPrimaryKey(idTramite);
        } catch (Exception e) {
            System.out.println("******Errrorr>>>>>>>>>mensaje: " + e.getMessage() + " localized: " + e.getLocalizedMessage());

        }
        return 0;
    }

    @Transactional(propagation = Propagation.REQUIRED, value = "transactionManager", rollbackFor = Exception.class)

    public TramitePatente obtenerTramitePromocioneById(Long idTramite) throws Exception {
        TramitePatente tramite = new TramitePatente();

        List<Pago> listPago = rduPagoMapper.selectByTramiteId(idTramite);
        if (!listPago.isEmpty()) {
            Pago pago = new Pago();
            pago.setFechaPorPagar(listPago.get(0).getFechaPorPagar());
            pago.setFechapago(listPago.get(0).getFechapago());
            pago.setFeps(listPago.get(0).getFeps());
            pago.setFoliopago(listPago.get(0).getFoliopago());
            pago.setIdPago(listPago.get(0).getIdPago());
            pago.setIdTramite(listPago.get(0).getIdTramite());
            pago.setTotal(listPago.get(0).getTotal());
            tramite.setPago(pago);
        }
        
        return tramite;
    }

    //AQUI RECUPERA LOS APODERADOS
    @Transactional(propagation = Propagation.REQUIRED, value = "transactionManager", rollbackFor = Exception.class)
    public TramitePatente obtenerTramitePatenteById(Long idTramite) throws Exception {
        TramitePatente tramite = new TramitePatente();
        tramite = rduTramitePatenteMapper.selectByPrimaryKey(idTramite);
        tramite.setApoderados(rduPersonaMapper.selectApoderados(tramite.getIdTramitePatente()));
        tramite.setPersonasNot(rduPersonaMapper.selectPersonasNot(tramite.getIdTramitePatente()));
        tramite.setSolicitantes(rduPersonaMapper.selectSolicitanteTramitePatente(tramite.getIdTramitePatente()));
        tramite.setInventores(rduPersonaMapper.selectInventorTramitePatente(tramite.getIdTramitePatente()));
        tramite.setPrioridades(rduPrioridadMapper.selectPrioridadesTramitePatente(tramite.getIdTramitePatente()));
        if (tramite.getIdDomicilio()!=null){
            tramite.setDomicilioObj(this.rduDomicilioMapper.selectByPrimaryKey(tramite.getIdDomicilio()));
        }
        if (tramite.getIdDatoscontacto()!=null){
            tramite.setDatosContacto(this.rduDatosContactoMapper.selectByPrimaryKey(tramite.getIdDatoscontacto()));
        }
        
        List<Pago> listPago = rduPagoMapper.selectByTramiteId(idTramite);
        if (!listPago.isEmpty()) {
            Pago pago = new Pago();
            pago.setFechaPorPagar(listPago.get(0).getFechaPorPagar());
            pago.setFechapago(listPago.get(0).getFechapago());
            pago.setFeps(listPago.get(0).getFeps());
            pago.setFoliopago(listPago.get(0).getFoliopago());
            pago.setIdPago(listPago.get(0).getIdPago());
            pago.setIdTramite(listPago.get(0).getIdTramite());
            pago.setTotal(listPago.get(0).getTotal());
            tramite.setPago(pago);
        }

        //asignar valores de descripcion para tipo de sol y subtipo

        CatSubtiposolicitud subtipoSol = new CatSubtiposolicitud();
        subtipoSol = rduCatSubtiposolicitudMapper.selectByPrimaryKey(tramite.getIdSubtipoSolicitud());
        tramite.setSubTipoSol(subtipoSol);

        CatTiposolicitud tipoSol = new CatTiposolicitud();
        tipoSol = rduCatTiposolicitudMapper.selectByPrimaryKey(subtipoSol.getIdTiposolicitud());
        tramite.setTipoSol(tipoSol);
        tramite.setImagenes(this.rduImagenDibujoMapper.selectSelective(new ImagenDibujo(null, idTramite, null, null, null)));
        tramite.setReivindicaciones(this.rduReivindicacionMapper.selectSelective(new Reivindicacion(null, idTramite, null, null,null)));
        return tramite;

    }

    public TramitePatente selectTramite(Long idTramitePatente) {
        return this.rduTramitePatenteMapper.selectByPrimaryKey(idTramitePatente);
    }

    @Transactional(propagation = Propagation.REQUIRED, value = "transactionManager", rollbackFor = Exception.class)
    public TramitePatente insertarTramitePatente(TramitePatente tramite) throws Exception {
        if(tramite.getDomicilioObj()!=null){
            rduDomicilioMapper.insert(tramite.getDomicilioObj());
            tramite.setIdDomicilio(tramite.getDomicilioObj().getIdDomicilio());  
        }
        if(tramite.getDatosContacto()!=null && tramite.getDatosContacto().getCorreoelectronico()!=null){
            rduDatosContactoMapper.insertSelective(tramite.getDatosContacto());
            tramite.setIdDatoscontacto(tramite.getDatosContacto().getIdDatoscontacto()); 
        }
        this.rduTramitePatenteMapper.insert(tramite);
        log.info("Número de tramite generado: "+tramite.getIdTramitePatente());
        //Apoderados
        if (tramite.getApoderados() != null) {
            for (Persona personaTemp : tramite.getApoderados()) {
                if (personaTemp.getDomicilioObj()!=null){
                    rduDomicilioMapper.insert(personaTemp.getDomicilioObj());
                    personaTemp.setIdDomiclio(personaTemp.getDomicilioObj().getIdDomicilio());   
                }
                if (personaTemp.getIdDatoscontacto() == null && personaTemp.getDatosContacto().getTelefono() == null
                        && personaTemp.getDatosContacto().getFax() == null && personaTemp.getDatosContacto().getCorreoelectronico() == null) {
                    personaTemp.setDatosContacto(null);
                } else {
                    rduDatosContactoMapper.insertSelective(personaTemp.getDatosContacto());
                    personaTemp.setIdDatoscontacto(personaTemp.getDatosContacto().getIdDatoscontacto());
                }
                rduPersonaMapper.insert(personaTemp);
                TramitePersona apoderadoXTramite = new TramitePersona();
                apoderadoXTramite.setIdSolicitante(personaTemp.getIdSolicitante());
                apoderadoXTramite.setIdTramite(tramite.getIdTramitePatente());
                apoderadoXTramite.setEsSolicitante(null);
                apoderadoXTramite.setIdClasePersona(Constantes.CLS_PERSONA_APODERADO);
                rduTramitePersonaMapper.insert(apoderadoXTramite);
            }
        }
        //Personas para oir notificaciones
        if (tramite.getPersonasNot() != null) {
            for (Persona personaTemp : tramite.getPersonasNot()) {
//                rduDomicilioMapper.insert(personaTemp.getDomicilioObj());
//                personaTemp.setIdDomiclio(personaTemp.getDomicilioObj().getIdDomicilio());
//                if (personaTemp.getIdDatoscontacto() == null && personaTemp.getDatosContacto().getTelefono() == null
//                        && personaTemp.getDatosContacto().getFax() == null && personaTemp.getDatosContacto().getCorreoelectronico() == null) {
//                    personaTemp.setDatosContacto(null);
//                } else {
//                    rduDatosContactoMapper.insertSelective(personaTemp.getDatosContacto());
//                    personaTemp.setIdDatoscontacto(personaTemp.getDatosContacto().getIdDatoscontacto());
//                }
                rduPersonaMapper.insert(personaTemp);
                TramitePersona personaNotificacion = new TramitePersona();
                personaNotificacion.setIdSolicitante(personaTemp.getIdSolicitante());
                personaNotificacion.setIdTramite(tramite.getIdTramitePatente());
                personaNotificacion.setEsSolicitante(null);
                personaNotificacion.setIdClasePersona(Constantes.CLS_PERSONA_PARA_OIR_NOTIFICACIONES);
                rduTramitePersonaMapper.insert(personaNotificacion);
            }
            this.insertImagenDibujo(tramite);
            this.insertReivindicacion(tramite);
        }

        if (tramite.getSolicitantes() != null && tramite.getSolicitantes().isEmpty() == false) {
            //guardarSolicitantes(tramite);
            guardarPersonaSolInv(tramite, 1);
        }

        if (tramite.getInventores() != null && tramite.getInventores().isEmpty() == false) {
            //guardarInventores(tramite);
            guardarPersonaSolInv(tramite, 2);
        }

        if (tramite.getPrioridades() != null && tramite.getPrioridades().isEmpty() == false) {
            guardarPrioridades(tramite);
        }


        return tramite;

    }

    @Transactional(propagation = Propagation.REQUIRED, value = "transactionManager", rollbackFor = Exception.class)
    public int updateTramitePatenteSigned(TramitePatente tramite) throws Exception {
        //System.out.println("actualizando en ejb");
        return this.rduTramitePatenteMapper.updateByPrimaryKey(tramite);
    }

    @Transactional(propagation = Propagation.REQUIRED, value = "transactionManager", rollbackFor = Exception.class)
    public boolean updateTramitePatenteByPrimaryKey(TramitePatente tramite) throws Exception {
        if(tramite.getDomicilioObj()!=null && tramite.getDomicilioObj().getIdDomicilio()!=null){
            rduDomicilioMapper.deleteByPrimaryKey(tramite.getDomicilioObj().getIdDomicilio());
            rduDomicilioMapper.insert(tramite.getDomicilioObj());
            tramite.setIdDomicilio(tramite.getDomicilioObj().getIdDomicilio());  
        }else if(tramite.getDomicilioObj()!=null){ 
            rduDomicilioMapper.insert(tramite.getDomicilioObj());
            tramite.setIdDomicilio(tramite.getDomicilioObj().getIdDomicilio()); 
        }
            
        if(tramite.getDatosContacto()!=null && tramite.getDatosContacto().getIdDatoscontacto()>0){
            rduDatosContactoMapper.deleteByPrimaryKey(tramite.getDatosContacto().getIdDatoscontacto());
            rduDatosContactoMapper.insertSelective(tramite.getDatosContacto());
            tramite.setIdDatoscontacto(tramite.getDatosContacto().getIdDatoscontacto()); 
        }else if(tramite.getDatosContacto()!=null){
            rduDatosContactoMapper.insertSelective(tramite.getDatosContacto());
            tramite.setIdDatoscontacto(tramite.getDatosContacto().getIdDatoscontacto()); 
        }
            
        //System.out.println("actualizando en ejb");
        this.rduTramitePatenteMapper.updateByPrimaryKey(tramite);

        List<TramitePersona> apoderados = rduTramitePersonaMapper.selectPatentes(tramite.getIdTramitePatente(), Constantes.CLS_PERSONA_APODERADO);
        rduTramitePersonaMapper.deleteByPrimaryKey(tramite.getIdTramitePatente(), Constantes.CLS_PERSONA_APODERADO.longValue());

        for (TramitePersona apoderadoXTramitePatente : apoderados) {
            Persona apoderado = rduPersonaMapper.selectByPrimaryKey(apoderadoXTramitePatente.getIdSolicitante());
            rduPersonaMapper.deleteByPrimaryKey(apoderadoXTramitePatente.getIdSolicitante());
            if (apoderado.getDomicilioObj()!=null){
                rduDomicilioMapper.deleteByPrimaryKey(apoderado.getIdDomiclio());
            }
            if (apoderado.getIdDatoscontacto() == null && apoderado.getDatosContacto().getTelefono() == null
                    && apoderado.getDatosContacto().getFax() == null && apoderado.getDatosContacto().getCorreoelectronico() == null) {
                apoderado.setDatosContacto(null);
            } else {
                rduDatosContactoMapper.insertSelective(apoderado.getDatosContacto());
                apoderado.setIdDatoscontacto(apoderado.getDatosContacto().getIdDatoscontacto());
            }
        }
        List<TramitePersona> personasNot = rduTramitePersonaMapper.selectPatentes(tramite.getIdTramitePatente(), Constantes.CLS_PERSONA_PARA_OIR_NOTIFICACIONES);
        rduTramitePersonaMapper.deleteByPrimaryKey(tramite.getIdTramitePatente(), Constantes.CLS_PERSONA_PARA_OIR_NOTIFICACIONES.longValue());
        for (TramitePersona personaNotXTramitePatente : personasNot) {
            Persona personaNot = rduPersonaMapper.selectByPrimaryKey(personaNotXTramitePatente.getIdSolicitante());
            rduPersonaMapper.deleteByPrimaryKey(personaNotXTramitePatente.getIdSolicitante());
//            rduDomicilioMapper.deleteByPrimaryKey(personaNot.getIdDomiclio());
//            if (personaNot.getIdDatoscontacto() == null && personaNot.getDatosContacto().getTelefono() == null
//                    && personaNot.getDatosContacto().getFax() == null && personaNot.getDatosContacto().getCorreoelectronico() == null) {
//                personaNot.setDatosContacto(null);
//            } else {
//                rduDatosContactoMapper.insertSelective(personaNot.getDatosContacto());
//                personaNot.setIdDatoscontacto(personaNot.getDatosContacto().getIdDatoscontacto());
//            }
        }

        //borrar solicitantes
        List<TramitePersona> listRelSolTramite = new ArrayList<TramitePersona>();
        listRelSolTramite = rduTramitePersonaMapper.selectPatentes(tramite.getIdTramitePatente(), Constantes.CLS_PERSONA_SOLICITANTE_PATENTES);

        rduTramitePersonaMapper.deleteByPrimaryKey(tramite.getIdTramitePatente(), Constantes.CLS_PERSONA_SOLICITANTE_PATENTES.longValue());
        //Eliminar los datos del sol, datos cont y domicilio

        for (int j = 0; j < listRelSolTramite.size(); j++) {
            TramitePersona relacion = (TramitePersona) listRelSolTramite.get(j);
            Persona persona = new Persona();
            persona = rduPersonaMapper.selectByPrimaryKey(relacion.getIdSolicitante());
            rduPersonaMapper.deleteByPrimaryKey(persona.getIdSolicitante());
            if (persona.getIdDatoscontacto() != null && persona.getIdDatoscontacto().longValue() != 0) {
                rduDatosContactoMapper.deleteByPrimaryKey(persona.getIdDatoscontacto());
            }
            rduDomicilioMapper.deleteByPrimaryKey(persona.getIdDomiclio());
        }


        //Borrar inventores e insertar los nuevos
        List<TramitePersona> listRelInvTramite = new ArrayList<TramitePersona>();
        listRelInvTramite = rduTramitePersonaMapper.selectPatentes(tramite.getIdTramitePatente(), Constantes.CLS_PERSONA_INVENTOR);

        rduTramitePersonaMapper.deleteByPrimaryKey(tramite.getIdTramitePatente(), Constantes.CLS_PERSONA_INVENTOR.longValue());
        //Eliminar los datos del sol, datos cont y domicilio

        for (int j = 0; j < listRelInvTramite.size(); j++) {
            TramitePersona relacion = (TramitePersona) listRelInvTramite.get(j);
            Persona persona = new Persona();
            persona = rduPersonaMapper.selectByPrimaryKey(relacion.getIdSolicitante());
            rduPersonaMapper.deleteByPrimaryKey(persona.getIdSolicitante());
            if (persona.getIdDatoscontacto() != null && persona.getIdDatoscontacto().longValue() != 0) {
                rduDatosContactoMapper.deleteByPrimaryKey(persona.getIdDatoscontacto());
            }
            rduDomicilioMapper.deleteByPrimaryKey(persona.getIdDomiclio());
        }

        //Generar cadena de IDtramites actuales
        String idsPrioridad= new String ("");
         List<PrioridadXTramitePatente> listRelPriorTramite = new ArrayList<PrioridadXTramitePatente>();
         List<PrioridadXTramitePatente> listRelPriorTramitetmp = new ArrayList<PrioridadXTramitePatente>();
        int i=0;
        for (Prioridad P : tramite.getPrioridades())
        {   if(P.getIdPrioridad() != null)
            {
                listRelPriorTramitetmp=rduPrioridadXTramitePatenteMapper.obtenerPrioridadXIDPrioridad(P.getIdPrioridad().toString());
                if (listRelPriorTramitetmp.size() != 0 )
                {
                listRelPriorTramite.add(i, listRelPriorTramitetmp.get(0));
                i++;
                }
            }
        }
        if(idsPrioridad.length()> 0)
        {
            idsPrioridad= idsPrioridad.substring(0, idsPrioridad.length()-2);
        }
        //Borrar prioridades e insertar los nuevos
        //List<PrioridadXTramitePatente> listRelPriorTramite = new ArrayList<PrioridadXTramitePatente>();
      
        //listRelPriorTramite = rduPrioridadXTramitePatenteMapper.obtenerPrioridadXTramite(tramite.getIdTramitePatente());
        //listRelPriorTramite = rduPrioridadXTramitePatenteMapper.obtenerPrioridadXIDPrioridad(idsPrioridad.toString());

        rduPrioridadXTramitePatenteMapper.deleteRelPrioridadByTramite(tramite.getIdTramitePatente());
        //Eliminar los datos del sol, datos cont y domicilio
        
        for (int j = 0; j < listRelPriorTramite.size(); j++) {
            PrioridadXTramitePatente relacion = (PrioridadXTramitePatente) listRelPriorTramite.get(j);
            Prioridad prioDB = new Prioridad();
            prioDB = rduPrioridadMapper.selectByPrimaryKey(relacion.getIdPrioridad());
            tramite.getPrioridades().get(j).setIdAnexoPrioridad(prioDB.getIdAnexoPrioridad());
            tramite.getPrioridades().get(j).setIdAnexoTraduccion(prioDB.getIdAnexoTraduccion());
            rduPrioridadMapper.deleteByPrimaryKey(relacion.getIdPrioridad());
        }

        //Apoderados
        if (tramite.getApoderados() != null) {
            for (Persona personaTemp : tramite.getApoderados()) {
                if (personaTemp.getIdDatoscontacto() == null && personaTemp.getDatosContacto().getTelefono() == null
                        && personaTemp.getDatosContacto().getFax() == null && personaTemp.getDatosContacto().getCorreoelectronico() == null) {
                    personaTemp.setDatosContacto(null);
                } else {
                    rduDatosContactoMapper.insertSelective(personaTemp.getDatosContacto());
                    personaTemp.setIdDatoscontacto(personaTemp.getDatosContacto().getIdDatoscontacto());
                }
                if (personaTemp.getDomicilioObj()!=null){
                    rduDomicilioMapper.insert(personaTemp.getDomicilioObj());
                    personaTemp.setIdDomiclio(personaTemp.getDomicilioObj().getIdDomicilio());
                }
                rduPersonaMapper.insert(personaTemp);

                TramitePersona apoderadoXTramitePatente = new TramitePersona();
                apoderadoXTramitePatente.setIdSolicitante(personaTemp.getIdSolicitante());
                apoderadoXTramitePatente.setIdTramite(tramite.getIdTramitePatente());
                apoderadoXTramitePatente.setIdClasePersona(Constantes.CLS_PERSONA_APODERADO);
                rduTramitePersonaMapper.insert(apoderadoXTramitePatente);
            }
        }
        //Personas para oir notificaciones
        if (tramite.getPersonasNot() != null) {
            for (Persona personaTemp : tramite.getPersonasNot()) {
//                if (personaTemp.getIdDatoscontacto() == null && personaTemp.getDatosContacto().getTelefono() == null
//                        && personaTemp.getDatosContacto().getFax() == null && personaTemp.getDatosContacto().getCorreoelectronico() == null) {
//                    personaTemp.setDatosContacto(null);
//                } else {
//                    rduDatosContactoMapper.insertSelective(personaTemp.getDatosContacto());
//                    personaTemp.setIdDatoscontacto(personaTemp.getDatosContacto().getIdDatoscontacto());
//                }
//                rduDomicilioMapper.insert(personaTemp.getDomicilioObj());
//                personaTemp.setIdDomiclio(personaTemp.getDomicilioObj().getIdDomicilio());
                rduPersonaMapper.insert(personaTemp);
                TramitePersona personaNotificacion = new TramitePersona();
                personaNotificacion.setIdSolicitante(personaTemp.getIdSolicitante());
                personaNotificacion.setIdTramite(tramite.getIdTramitePatente());
                personaNotificacion.setIdClasePersona(Constantes.CLS_PERSONA_PARA_OIR_NOTIFICACIONES);
                rduTramitePersonaMapper.insert(personaNotificacion);
            }
        }
        this.actualizarImagenDibujo(tramite);
        this.actualizarReivindicacion(tramite);


        if (tramite.getSolicitantes() != null && tramite.getSolicitantes().isEmpty() == false) {
            //guardarSolicitantes(tramite);
            guardarPersonaSolInv(tramite, 1);
        }

        if (tramite.getInventores() != null && tramite.getInventores().isEmpty() == false) {
            //guardarInventores(tramite);
            guardarPersonaSolInv(tramite, 2);
        }

        if (tramite.getPrioridades() != null && tramite.getPrioridades().isEmpty() == false) {
            guardarPrioridades(tramite);
        }

        return true;
    }

    public void actualizarImagenDibujo(TramitePatente tramite) {
        rduImagenDibujoMapper.deleteByPrimaryKey(tramite.getIdTramitePatente());
        if (!tramite.getImagenes().isEmpty()) {
            if (!tramite.getImagenes().isEmpty()) {
                ImagenDibujo imagen = null;
                for (int i = 0; i < tramite.getImagenes().size(); i++) {
                    imagen = tramite.getImagenes().get(i);
                    imagen.setIdTramite(tramite.getIdTramitePatente());
                    rduImagenDibujoMapper.insertSelective(imagen);
                    tramite.getImagenes().get(i).setIdTramite(imagen.getIdTramite());
                    tramite.getImagenes().get(i).setIdImagenDibujo(imagen.getIdImagenDibujo());
                }
            }
        }
    }

    public void actualizarReivindicacion(TramitePatente tramite) {
        rduReivindicacionMapper.deleteByTramite(tramite.getIdTramitePatente());
        if (!tramite.getReivindicaciones().isEmpty()) {
            if (!tramite.getReivindicaciones().isEmpty()) {
                Reivindicacion reivindicacion = null;
                for (int i = 0; i < tramite.getReivindicaciones().size(); i++) {
                    reivindicacion = tramite.getReivindicaciones().get(i);
                    reivindicacion.setIdTramite(tramite.getIdTramitePatente());
                    rduReivindicacionMapper.insertSelective(reivindicacion);
                    tramite.getReivindicaciones().get(i).setIdTramite(reivindicacion.getIdTramite());
                    tramite.getReivindicaciones().get(i).setIdReivindicacion(reivindicacion.getIdReivindicacion());
                }
            }
        }
    }

    private void guardarPersonaSolInv(TramitePatente tramite, int procedencia) throws Exception {

        List<Persona> listaPersonas = new ArrayList<Persona>();
        if (procedencia == 1) {
            listaPersonas = tramite.getSolicitantes();
        }
        if (procedencia == 2) {
            listaPersonas = tramite.getInventores();
        }

        for (int i = 0; i < listaPersonas.size(); i++) {
            Persona solBd = new Persona();
            Domicilio dom = new Domicilio();
            Datoscontacto datosCont = new Datoscontacto();

            TramitePersona solicitanteXTramite = new TramitePersona();
            solicitanteXTramite.setIdTramite(tramite.getIdTramitePatente());
            solicitanteXTramite.setIdClasePersona(Constantes.CLS_PERSONA_SOLICITANTE_PATENTES);

            TramitePersona inventorXTramite = new TramitePersona();
            inventorXTramite.setIdTramite(tramite.getIdTramitePatente());
            inventorXTramite.setIdClasePersona(Constantes.CLS_PERSONA_INVENTOR);


            Persona elementoLista = (Persona) listaPersonas.get(i);
            if (elementoLista.getDatosContacto() != null) {
                if ((elementoLista.getDatosContacto().getCorreoelectronico() != null
                        && elementoLista.getDatosContacto().getCorreoelectronico().length() != 0)
                        || (elementoLista.getDatosContacto().getTelefono() != null && elementoLista.getDatosContacto().getTelefono().length() != 0)
                        || (elementoLista.getDatosContacto().getFax() != null
                        && elementoLista.getDatosContacto().getFax().length() != 0)) {

                    datosCont = elementoLista.getDatosContacto();
                    rduDatosContactoMapper.insert(datosCont);

                }
            }
            dom.setCalle(elementoLista.getDomicilioObj().getCalle());
            dom.setCodigopostal(elementoLista.getDomicilioObj().getCodigopostal());
            dom.setColonia(elementoLista.getDomicilioObj().getColonia());
            dom.setIdPais(elementoLista.getDomicilioObj().getIdPais());
            dom.setNumExt(elementoLista.getDomicilioObj().getNumExt());
            dom.setNumInt(elementoLista.getDomicilioObj().getNumInt());

            if (dom.getIdPais().longValue() == Constantes.ID_PAIS.longValue()) {
                dom.setIdEntidad(String.valueOf(elementoLista.getDomicilioObj().getEntidad().getIdEntidadFederativa().intValue()));
            } else {
                dom.setIdEntidad(elementoLista.getDomicilioObj().getEntidad().getNombre());
            }


            dom.setPoblacion(elementoLista.getDomicilioObj().getPoblacion());
            rduDomicilioMapper.insert(dom);
            dom.setIdDomicilio(dom.getIdDomicilio());

            if (procedencia == 1) {
                solBd.setIdTipoSolicitante(elementoLista.getTipoSolicitante().getIdTipoSolicitante());
                solBd.setIdTipopersona(elementoLista.getTipoPersona().getIdTipopersona());
                solBd.setNombrecompleto(elementoLista.getNombrecompleto());
//                solBd.setIdNacionalidad(elementoLista.getIdNacionalidad());
                        solBd.setIdNacionalidad(elementoLista.getNacionalidad().getIdPais());
                solBd.setPrincipal(elementoLista.getPrincipal());
                solBd.setPrimerApellido(elementoLista.getPrimerApellido());
                solBd.setSegundoApellido(elementoLista.getSegundoApellido());
                
                if (elementoLista.getRfc() != null) {
                    solBd.setRfc(elementoLista.getRfc());

                } else {
                    solBd.setRfc("");

                }
                if (elementoLista.getInventor() != null) {
                    solBd.setInventor(elementoLista.getInventor());
                }
                if (elementoLista.isAplicarDescuento() == true) {
                    solBd.setDescuento(new Short("1"));
                } else {
                    solBd.setDescuento(new Short("0"));
                }
            }

            if (procedencia == 2) {
                solBd.setIdTipopersona(elementoLista.getTipoPersona().getIdTipopersona());
                solBd.setNombrecompleto(elementoLista.getNombrecompleto());
//                solBd.setIdNacionalidad(elementoLista.getIdNacionalidad());
                solBd.setIdNacionalidad(elementoLista.getNacionalidad().getIdPais());
                solBd.setPrimerApellido(elementoLista.getPrimerApellido());
                solBd.setSegundoApellido(elementoLista.getSegundoApellido());
                
                if (elementoLista.getRfc() != null) {
                    solBd.setRfc(elementoLista.getRfc());

                } else {
                    solBd.setRfc("");

                }
            }


            solBd.setIdDomiclio(dom.getIdDomicilio());
            if (datosCont.getIdDatoscontacto() != 0) {
                solBd.setIdDatoscontacto(datosCont.getIdDatoscontacto());
            }


//            rduPersonaMapper.insert(solBd);
            rduPersonaMapper.insertPersona(solBd);
            if (procedencia == 1) {
                solicitanteXTramite.setIdSolicitante(solBd.getIdSolicitante());
                rduTramitePersonaMapper.insert(solicitanteXTramite);
            }

            if (procedencia == 2) {
                inventorXTramite.setIdSolicitante(solBd.getIdSolicitante());
                rduTramitePersonaMapper.insert(inventorXTramite);
            }

            //System.out.println("guardo solicitante/inventor con " + solBd.getIdSolicitante());
        }


    }

    public int updateTramitePatente(TramitePatente tramite) {
        return rduTramitePatenteMapper.updateByPrimaryKeySelective(tramite);
    }
    
    public int updateTramitePromocionPatente(SolicitudPreparacionDto tramite) {
        return rduTramitePromocionPatenteMapper.updateByPrimaryKeySelective(tramite);
    }

    private boolean guardarPrioridades(TramitePatente tramite) throws Exception {
        boolean edo = false;

        for (int i = 0; i < tramite.getPrioridades().size(); i++) {
            Prioridad prior = new Prioridad();

            PrioridadXTramitePatente relacionPrioTramite = new PrioridadXTramitePatente();
            relacionPrioTramite.setIdTramitePatente(tramite.getIdTramitePatente());

            Prioridad elementoLista = (Prioridad) tramite.getPrioridades().get(i);



            prior.setFechaPresentacionExt(elementoLista.getFechaPresentacionExt());
            prior.setIdPais(elementoLista.getIdPais());
            prior.setIdAnexoPrioridad(elementoLista.getIdAnexoPrioridad());
            prior.setIdAnexoTraduccion(elementoLista.getIdAnexoTraduccion());
            if (elementoLista.getNumeroExpediente() != null && elementoLista.getNumeroExpediente().length() != 0) {
                prior.setNumeroExpediente(elementoLista.getNumeroExpediente());
            } else {
                prior.setNumeroExpediente("");
            }


            rduPrioridadMapper.insert(prior);

            relacionPrioTramite.setIdPrioridad(prior.getIdPrioridad());
            rduPrioridadXTramitePatenteMapper.insertRelPrioridadTramite(relacionPrioTramite);

            //System.out.println("guardo con prioridad " + prior.getIdPrioridad());
            edo = true;
        }

        return edo;

    }

    public Solicitud getExpDivisional(Solicitud exp) {

        Solicitud elemento = new Solicitud();
        mx.gob.impi.sagpat.persistence.model.Persona titularP = new mx.gob.impi.sagpat.persistence.model.Persona();
        try {
            elemento = solicitudMapper.selectByPrimaryKeyView(exp);
            if (elemento != null) {
                if (elemento.getCodPersonaTit1() != null && elemento.getCodPersonaTit1().longValue() != 0) {
                    titularP = personaMapperSagpat.selectViewByPrimaryKey(elemento.getCodPersonaTit1().intValue());
                    elemento.setTitularPrincipal(titularP);
                }
            }
        } catch (Exception e) {
            System.out.println("******Errrorr>>>>>>>>>mensaje: " + e.getMessage() + " localized: " + e.getLocalizedMessage());
            return null;
        }
        return elemento;
    }

    public void insertImagenDibujo(TramitePatente tramite) {
        if (!tramite.getImagenes().isEmpty()) {
            ImagenDibujo imagen = null;
            for (int i = 0; i < tramite.getImagenes().size(); i++) {
                imagen = tramite.getImagenes().get(i);
                imagen.setIdTramite(tramite.getIdTramitePatente());
                this.rduImagenDibujoMapper.insertSelective(imagen);
                tramite.getImagenes().get(i).setIdTramite(imagen.getIdTramite());
                tramite.getImagenes().get(i).setIdImagenDibujo(imagen.getIdImagenDibujo());
            }
        }
    }

    public void insertReivindicacion(TramitePatente tramite) {
        if (!tramite.getReivindicaciones().isEmpty()) {
            Reivindicacion reivindicacion = null;
            for (int i = 0; i < tramite.getReivindicaciones().size(); i++) {
                reivindicacion = tramite.getReivindicaciones().get(i);
                reivindicacion.setIdTramite(tramite.getIdTramitePatente());
                this.rduReivindicacionMapper.insertSelective(reivindicacion);
                tramite.getReivindicaciones().get(i).setIdTramite(reivindicacion.getIdTramite());
                tramite.getReivindicaciones().get(i).setIdReivindicacion(reivindicacion.getIdReivindicacion());
            }
        }
    }

    public TramitePatente getDatosTramitePatente(Long idTramite) {
        return this.rduTramitePatenteMapper.selectDatosBasicos(idTramite);
    }

    public DatosSolicitudPct consultarDatosPCt(String idSolicitudPct) {
        return this.datosSolicitudPctMapper.selectByPrimaryKey(idSolicitudPct);
    }
    
    public List<DatosSolicitudPctMU> consultarDatosPCtMU(String idSolicitudPct) {
        return this.datosSolicitudPctMapper.selectPCT(idSolicitudPct);
    }

    /**
     * @param rduTramitePromocionPatenteMapper the rduTramitePromocionPatenteMapper to set
     */
    public void setRduTramitePromocionPatenteMapper(TramitePromocionPatenteMapper rduTramitePromocionPatenteMapper) {
        this.rduTramitePromocionPatenteMapper = rduTramitePromocionPatenteMapper;
    }

    public void actualizarPersona(Persona persona) {
        this.rduPersonaMapper.updateByPrimaryKeySelective(persona);
    }

    public Solicitud buscaExpedienteDivisional(String oficina, Integer numExp, Integer serExp, String tipExp) {
        return this.solicitudMapper.selectByExpedienteDivisional(oficina, numExp, serExp, tipExp);
    }
}
