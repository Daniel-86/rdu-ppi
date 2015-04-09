package mx.gob.impi.rdu.exposition.login;

import java.util.List;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import mx.gob.impi.rdu.exposition.SesionRDU;
import mx.gob.impi.rdu.persistence.model.Perfil;
import mx.gob.impi.rdu.util.ContextUtils;
import mx.gob.impi.rdu.util.PerfilEnumeration;
import org.apache.log4j.Logger;

/******************************************************************************/
/* Name: Gerardo Roque Celis. roque
/* Date: 08-mar-2012 11:00:56
/* Description:
/******************************************************************************/
@ManagedBean(name = "perfilUsuario")
@RequestScoped
public class PerfilUsuarioMB
{

    private List<Perfil> perfiles;
    private Logger log = Logger.getLogger(this.getClass());

    @PostConstruct
    public void getUsuarioSession()
    {
        try
        {
            SesionRDU session = ContextUtils.obtenerSesionUsuario();
            session = session == null ? new SesionRDU() : session;
            this.perfiles = session.getUsuario().getPerfiles();
        } catch (Exception e)
        {
            log.error("Ocurrio un error al obtener la session del usuario PerfilUsuarioMB.getUsuarioSession", e);
        }
    }

    /**
     * Role Administrador
     * @return 
     */
    public Boolean getAdmin()
    {
        return this.perfiles.contains(new Perfil(PerfilEnumeration.ROLE_ADMIN.getIdPerfil(),PerfilEnumeration.ROLE_ADMIN.getConstante()));
    }

    /**
     * Role Capturista
     * @return 
     */
    public Boolean getCapturista()
    {
        return this.perfiles.contains(new Perfil(PerfilEnumeration.ROLE_CAPTURISTA.getIdPerfil(),PerfilEnumeration.ROLE_ADMIN.getConstante()));
    }

    /**
     * Role responsable de sistema
     * @return 
     */
    public Boolean getResponsable()
    {
        return this.perfiles.contains(new Perfil(PerfilEnumeration.ROLE_RESPONSABLE.getIdPerfil(),PerfilEnumeration.ROLE_ADMIN.getConstante()));
    }

    /**
     * Role operador del portal
     * @return 
     */
    public Boolean getOperadorPortal()
    {
        return this.perfiles.contains(new Perfil(PerfilEnumeration.ROLE_OPERADOR_PORTAL.getIdPerfil(),PerfilEnumeration.ROLE_ADMIN.getConstante()));
    }

    /**
     * Role usuario maestro
     * @return
     */
    public Boolean getUsuarioMaestro()
    {
        return this.perfiles.contains(new Perfil(PerfilEnumeration.ROLE_USUARIO_MAESTRO.getIdPerfil(),PerfilEnumeration.ROLE_ADMIN.getConstante()));
    }
    /**
     * Role usuario maestro
     * @return
     */
    public Boolean getUsuarioPromovente()
    {
        return this.perfiles.contains(new Perfil(PerfilEnumeration.ROLE_USUARIO_PROMOVENTE.getIdPerfil(),PerfilEnumeration.ROLE_ADMIN.getConstante()));
    }

    /**
     * Role Coordinador de recepción
     * @return
     */
    public Boolean getCoordinadorRecepcion()
    {
        return this.perfiles.contains(new Perfil(PerfilEnumeration.ROLE_COORDINADOR_RECEPCION.getIdPerfil(),PerfilEnumeration.ROLE_ADMIN.getConstante()));
    }
      
         public Boolean getCoordinadorPat()
    {
        return this.perfiles.contains(new Perfil(PerfilEnumeration.ROLE_COORDINADOR_PATENTES.getIdPerfil(),PerfilEnumeration.ROLE_COORDINADOR_PATENTES.getConstante()));
    }
    
      public Boolean getExaminadorPat()
    {
        return this.perfiles.contains(new Perfil(PerfilEnumeration.ROLE_EXAMINADOR_PATENTES.getIdPerfil(),PerfilEnumeration.ROLE_EXAMINADOR_PATENTES.getConstante()));
    }
    
    

        /**
     * Role Coordinador de recepción
     * @return
     */
    public Boolean getCoordinadorJob()
    {
            SesionRDU session = ContextUtils.obtenerSesionUsuario();

            if(session!=null){
                session.getPromovente().getId_promovente();

                if(session.getPromovente().getId_promovente()==20420)
                    return true;
                else
                    return false;
            }
            return false;
    }



}
