package mx.gob.impi.rdu.service;

import java.util.ArrayList;
import java.util.List;
import mx.gob.impi.rdu.persistence.model.Perfil;
import mx.gob.impi.rdu.persistence.model.Usuario;
import mx.gob.impi.rdu.persistence.model.UsuarioSeguridad;
import mx.gob.impi.rdu.remote.RduUsuariosBeanRemote;
import mx.gob.impi.rdu.util.ContextUtils;
import mx.gob.impi.rdu.util.security.Actor;
import mx.gob.impi.rdu.util.security.Rol;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import mx.gob.impi.rdu.dto.PromoventeDto;
import mx.gob.impi.rdu.exposition.SesionRDU;
import org.apache.log4j.Logger;

public class AutenticacionServiceImpl implements UserDetailsService, AutenticacionService
{

    private long tiempoBloqueoCastigo;
    private RduUsuariosBeanRemote rduUsuariosBeanRemote;
    private Logger log = Logger.getLogger(this.getClass());

    public void setRduUsuariosBeanRemote(RduUsuariosBeanRemote rduUsuariosBeanRemote) {
        this.rduUsuariosBeanRemote = rduUsuariosBeanRemote;
    }

    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException
    {
        long currentTime = System.currentTimeMillis();
        Actor actor = new Actor();
        try
        {
            //QUITAR EN CASO QUE LA SEGURIDAD SE ENCUENTRE EN LA BD DEL APLICATIVO
            /*
            Usuario usuario = this.rduUsuariosBeanRemote.verificarUsuarioByLogin(username);
            if (usuario == null)
                throw new UsernameNotFoundException("Usuario no existe.");
            List<Rol> roles = this.getRolesFromUsuario(usuario);
            actor.setUsuario(usuario);
            actor.getRoles().addAll(roles);
            PromoventeDto sesionPromovente=new PromoventeDto();
            ContextUtils.crearSesionUsuario(usuario,sesionPromovente);
            */

            //El usuario se recupera del parametro XML que PASE envia a RDU
            ContextUtils.leerParametro();
            SesionRDU obtSession = ContextUtils.obtenerSesionUsuario();
            PromoventeDto sesionPromovente=obtSession.getPromovente();
            Usuario usuario = this.creaUsuarioDePromovente(sesionPromovente);
            if (usuario == null)
                throw new UsernameNotFoundException("Usuario no existe.");
            List<Rol> roles = this.getRolesFromUsuario(usuario);
            actor.setUsuario(usuario);
            actor.getRoles().addAll(roles);
            //crea nuevamente la sesion con el usuario correcto
            ContextUtils.crearSesionUsuario(usuario,sesionPromovente);
            

            if (!actor.isAccountNonLocked())
            {
                long instanteDeBloqueo = actor.getUsuario().getUsuarioSeguridad().getInstanteBloqueo().getTime();
                if (instanteDeBloqueo + tiempoBloqueoCastigo < currentTime)
                {
                    actor.getUsuario().getUsuarioSeguridad().setCuentaNoBloqueda(Boolean.TRUE);
                    actor.getUsuario().getUsuarioSeguridad().setIntentosFallidos(0);                    
                }
            }

        } catch (Exception e)
        {
            log.error("Ocurrio un error en el metodo AutenticacionServiceImpl.loadUserByUsername", e);
        }
        return actor;
    }

    /*
     * Metodo para crear al usuario del promovente, esto por cuestiones del PASE
     */
    private Usuario creaUsuarioDePromovente(PromoventeDto prmPromovente){
        Usuario retUsuario=new Usuario();
        UsuarioSeguridad usrSeg= new UsuarioSeguridad();
        List<Perfil> perfiles = new ArrayList();
        Perfil nvoPerfil = new Perfil();
        retUsuario.setNombre(prmPromovente.getNombre());
        retUsuario.setApellidoPaterno(prmPromovente.getApaterno());
        retUsuario.setApellidoMaterno(prmPromovente.getAmaterno());
        retUsuario.setIdUsuario((long)prmPromovente.getId_promovente());

        //deben ser los que se proporcionan el la pagina de login
        usrSeg.setUsuarioStr(prmPromovente.getLogin());
        usrSeg.setClave(prmPromovente.getPassword());
        usrSeg.setClaveConfirmada(prmPromovente.getPassword());
        usrSeg.setIdSeguridad("1");
        usrSeg.setHabilitado(Boolean.TRUE);
        usrSeg.setCuentaNoBloqueda(Boolean.TRUE);
        usrSeg.setCuentaNoExpirada(Boolean.TRUE);
        usrSeg.setCredencialNoExpirada(Boolean.TRUE);
        retUsuario.setUsuarioSeguridad(usrSeg);
        
        int nIdPerfilPromovente=prmPromovente.getId_perfil();
        switch (nIdPerfilPromovente){
            //usuario maestro
            case 1:
                nvoPerfil.setIdPerfil("5");
                nvoPerfil.setConstante("ROLE_USUARIO_MAESTRO");
            //coordinador recepcion
            case 18:
                nvoPerfil.setIdPerfil(String.valueOf(prmPromovente.getId_perfil()));
                nvoPerfil.setConstante("ROLE_COORDINADOR_RECEPCION");
        }
        nvoPerfil.setNombre(prmPromovente.getDescPerfil());
        perfiles.add(nvoPerfil);
        retUsuario.setPerfiles(perfiles);
        return retUsuario;
    }

    /**
     * Obtiene los perfiles del usuario para el model Rol,
     * Rol implementa las interfaces para la comunicacion con sprin security
     * @param usuario
     * @return 
     */
    public List<Rol> getRolesFromUsuario(Usuario usuario)
    {
        List<Rol> roles = new ArrayList<Rol>();
        for (Perfil perfil : usuario.getPerfiles())
        {
            Rol rol = new Rol();
            rol.setConstante(perfil.getConstante());
            roles.add(rol);
        }
        return roles;
    }

    /**    
     * Tiempo durante el cual la cuenta de un usuario estarÃ¡ bloqueada
     * como castigo por haber fallado su login 'k' veces.     
     * @param tiempoBloqueoCastigo the tiempoBloqueoCastigo to set
     */
    public void setTiempoBloqueoCastigo(long tiempoBloqueoCastigo)
    {
        this.tiempoBloqueoCastigo = tiempoBloqueoCastigo;
    }


}
