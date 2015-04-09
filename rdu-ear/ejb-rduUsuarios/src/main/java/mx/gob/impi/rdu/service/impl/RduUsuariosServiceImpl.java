package mx.gob.impi.rdu.service.impl;

import mx.gob.impi.rdu.persistence.model.*;
import mx.gob.impi.rdu.persistence.mappers.*;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import org.apache.log4j.Logger;
import java.util.List;
import java.util.Iterator;
import java.util.ArrayList;
import mx.gob.impi.rdu.service.RduUsuariosService;

public class RduUsuariosServiceImpl implements RduUsuariosService {

    UsuarioMapper rduUsuarioMapper;
    UsuarioSeguridadMapper rduUsuarioSeguridadMapper;
    PerfilMapper rduPerfilMapper;
    UsuarioPerfilMapper rduUsuarioPerfilMapper;
    PersonaMapper personaMapper;
    DomicilioMapper rduDomicilioMapper;
    DatoscontactoMapper rduDatosContactoMapper;
    PaisMapper rduPaisMapper;
    CatTipopersonaMapper rduCatTipoPersonaMapper;
    SolicitanteXTramiteMapper rduSolicitanteXTramiteMapper;

    public void setRduSolicitanteXTramiteMapper(SolicitanteXTramiteMapper rduSolicitanteXTramiteMapper) {
        this.rduSolicitanteXTramiteMapper = rduSolicitanteXTramiteMapper;
    }

    public void setRduCatTipoPersonaMapper(CatTipopersonaMapper rduCatTipoPersonaMapper) {
        this.rduCatTipoPersonaMapper = rduCatTipoPersonaMapper;
    }

    public void setRduDatosContactoMapper(DatoscontactoMapper rduDatosContactoMapper) {
        this.rduDatosContactoMapper = rduDatosContactoMapper;
    }

    public void setRduDomicilioMapper(DomicilioMapper rduDomicilioMapper) {
        this.rduDomicilioMapper = rduDomicilioMapper;
    }

    public void setRduPaisMapper(PaisMapper rduPaisMapper) {
        this.rduPaisMapper = rduPaisMapper;
    }
    //TelefonoMapper rduTelefonoMapper;
    //private final static Logger LOGGER = Logger.getLogger(RduUsuariosServiceImpl.class);
    private Logger LOGGER = Logger.getLogger(this.getClass());

    public void setRduPerfilMapper(PerfilMapper rduPerfilMapper) {
        this.rduPerfilMapper = rduPerfilMapper;
    }

    public void setRduUsuarioMapper(UsuarioMapper rduUsuarioMapper) {
        this.rduUsuarioMapper = rduUsuarioMapper;
    }

    public void setRduUsuarioPerfilMapper(UsuarioPerfilMapper rduUsuarioPerfilMapper) {
        this.rduUsuarioPerfilMapper = rduUsuarioPerfilMapper;
    }

    public void setRduUsuarioSeguridadMapper(UsuarioSeguridadMapper rduUsuarioSeguridadMapper) {
        this.rduUsuarioSeguridadMapper = rduUsuarioSeguridadMapper;
    }

    public void setPersonaMapper(PersonaMapper personaMapper) {
        this.personaMapper = personaMapper;
    }

    /*
    public void setRduTelefonoMapper(TelefonoMapper rduTelefonoMapper) {
    this.rduTelefonoMapper = rduTelefonoMapper;
    }
     */
    @Transactional(propagation = Propagation.REQUIRED)
    public Usuario verificarUsuarioByLogin(String usrname) throws Exception {
        System.out.println("USUARIOSERVICEIMPL - verificarUsuarioByLogin: " + usrname);

        UsuarioSeguridad usuarioSeguridad = new UsuarioSeguridad();
        usuarioSeguridad.setUsuarioStr(usrname);
        usuarioSeguridad = this.rduUsuarioSeguridadMapper.getUsuarioByLogin(usuarioSeguridad);

        if (usuarioSeguridad != null) {
            Usuario usuario = new Usuario();
            usuario.setIdUsuario(usuarioSeguridad.getIdUsuario());
            return seleccionarById(usuario);
        } else {
            return null;
        }

    }

    public Usuario seleccionarById(Usuario usuario) {
        //System.out.println("CONSULTAR Usuario nvo::  " + usuario.getIdTipo());

        usuario = consultarUsuario(usuario);
        consultarUsuarioSeguridad(usuario);
        usuario.setPerfiles(new ArrayList());
        usuario.getPerfiles().addAll(consultarPerfilesUsuario(usuario));

        return usuario;

    }

    public Usuario consultarUsuario(Usuario usuario) {
        return rduUsuarioMapper.getById(usuario);
    }

    public void consultarUsuarioSeguridad(Usuario usuario) {
        UsuarioSeguridad usuarioSeguridad = new UsuarioSeguridad();
        usuarioSeguridad.setIdUsuario(usuario.getIdUsuario());
        usuarioSeguridad = rduUsuarioSeguridadMapper.getById(usuarioSeguridad);
        usuario.setUsuarioSeguridad(usuarioSeguridad);
    }
    /*
    public void consultarUsuarioTelefonos(Usuario usuario)
    {
    Telefono tel= new Telefono();
    tel.setIdUsuario(usuario.getIdUsuario());
    List<Telefono> telefonos=this.rduTelefonoMapper.selectByExample(tel);
    //usuario.setTelefonos(telefonos);
    }
     */

    public List<Perfil> consultarPerfilesUsuario(Usuario usuario) {

        List<Perfil> perfiles = new ArrayList();
        UsuarioPerfil usuarioPerfil = new UsuarioPerfil();
        usuarioPerfil.setIdUsuario(usuario.getIdUsuario());

        for (UsuarioPerfil consultaUsuarioPerfil : rduUsuarioPerfilMapper.getPerfilAllUsuario(usuarioPerfil)) {

            Perfil perfil = new Perfil();
            perfil.setIdPerfil(consultaUsuarioPerfil.getIdPerfil());
            perfil = rduPerfilMapper.getById(perfil);
            perfiles.add(perfil);
        }
        return perfiles;
    }

    public List<Usuario> selectByFiltro(CoordinacionEstatal coordinacionEstatal) {
        List<Usuario> lstUsuarios = rduUsuarioMapper.selectByFiltro(coordinacionEstatal);

        List<Usuario> retUsuarios = new ArrayList();

        if (lstUsuarios != null) {
            if (lstUsuarios.size() > 0) {
                //recupera usuarioseguridad por cada usuario
                for (Iterator iter = lstUsuarios.iterator(); iter.hasNext();) {
                    Usuario usuario = (Usuario) iter.next();
                    consultarUsuarioSeguridad(usuario);
                    //consultarUsuarioTelefonos(usuario);
                    retUsuarios.add(usuario);
                }

            }
        }

        return retUsuarios;
    }

    public Persona getSolicitanteById(Integer id) {
        return personaMapper.selectByPrimaryKey(new Long(id.longValue()));
    }

    public int insertSolicitante(Persona solicitante) {
        return personaMapper.insert(solicitante);
    }

    public int deleteSolicitante(Integer idSolicitante) {
        return personaMapper.deleteByPrimaryKey(new Long(idSolicitante.longValue()));
    }

    public int updateSolicitante(Persona solicitante) {

        return personaMapper.updateByPrimaryKeySelective(solicitante);
    }

    public List<Persona> selectSolicitantesPorTramite(Long idTramite) {

        return this.getSolicitantesPorTramite(this.getIdSolicitantes(idTramite));

    }

    public List<SolicitanteXTramite> getIdSolicitantes(Long idTramite) {

        return this.rduSolicitanteXTramiteMapper.obtenerSolicitantesXTramite(idTramite);

    }

    public List<Persona> getSolicitantesPorTramite(List<SolicitanteXTramite> solicitantes) {

        List<Persona> sols = new ArrayList<Persona>();

        for (SolicitanteXTramite soli : solicitantes) {
            Persona sol = this.personaMapper.selectByPrimaryKey(soli.getIdSolicitante());
            sol.setDomicilioObj(this.rduDomicilioMapper.selectByPrimaryKey(sol.getIdDomiclio()));
            sol.setDatosContacto(this.rduDatosContactoMapper.selectByPrimaryKey(sol.getIdDatoscontacto()));
            sol.setPais(this.rduPaisMapper.selectByPrimaryKey(sol.getIdNacionalidad()));
            sol.setTipoPersona(this.rduCatTipoPersonaMapper.selectByPrimaryKey(sol.getIdTipopersona()));
            sols.add(sol);
        }

        return sols;
    }

    public List<Persona> selectDynamic(Persona solicitante) {
        System.out.println("Entrando al metodo RduUsuarioServiceImpl.selectDynamic()");
        return personaMapper.selectDynamic(solicitante);
    }
}
