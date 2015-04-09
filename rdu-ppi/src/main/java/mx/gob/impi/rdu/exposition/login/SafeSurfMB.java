/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.gob.impi.rdu.exposition.login;

import java.io.Serializable;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import mx.gob.impi.rdu.exposition.SesionRDU;
import mx.gob.impi.rdu.persistence.model.Perfil;
import mx.gob.impi.rdu.util.ContextUtils;
import mx.gob.impi.rdu.util.PerfilEnumeration;
import org.apache.log4j.Logger;

/**
 *
 * @author
 */

@ViewScoped
@SuppressWarnings("serial")
@ManagedBean
public class SafeSurfMB  implements Serializable{
private Logger log = Logger.getLogger(this.getClass());
      @PostConstruct
    public void init() {
       
    }
    
    public void allowAccess(int menu) throws Exception{
        //obtener perfil logeado
        SesionRDU obtSession = ContextUtils.obtenerSesionUsuario();
        Perfil perfil = obtSession.getUsuario().getPerfiles().get(0);
        //validar el menu id ubicado en session
        //int menu = obtSession.getIdMenu();
        //comparar perfil con menu id, si no corresponde con el perfil redireccionar 
        if (!continueSurfing(menu, perfil.getIdPerfil())) {
            //FacesContext.getCurrentInstance().getExternalContext().redirect("/rdu-ppi/content/common/authentication/access-denied.faces");
        }    
    }

    public boolean continueSurfing(int menu, String pfl) {

        String idPerfil = pfl;
        //perfil logeado es igual a perfil operador portal
        if (idPerfil.equals(PerfilEnumeration.ROLE_OPERADOR_PORTAL.getIdPerfil())) {
            //validar que pueda ver el menu ....
        } else if (idPerfil.equals(PerfilEnumeration.ROLE_USUARIO_MAESTRO.getIdPerfil())) {
            //usuario maestro puede ver menus 1, 2, 4
            if (menu==1 || menu==2 || menu==4) {
                return true;
            } else {
                return false;
            }
        } else if (idPerfil.equals(PerfilEnumeration.ROLE_USUARIO_PROMOVENTE.getIdPerfil())) {
            //usuario promovente puede ver menus 1,2,4
            if (menu==1 || menu==2 || menu==4) {
                return true;
            } else {
                return false;
            }
        } else if (idPerfil.equals(PerfilEnumeration.ROLE_COORDINADOR_RECEPCION.getIdPerfil())) {
            //usuario puede ver 4,7,17,8     
            if (menu==4 || menu==7 || menu==17 || menu==8) {
                return true;
            } else {
                return false;
            }
        }else if(idPerfil.equals(PerfilEnumeration.ROLE_COORDINADOR_PATENTES.getIdPerfil())) {
            //usuario promovente puede ver menus 1,2,4
            if (menu==42 ) {
                return true;
            } else {
                return false;
            }
        }  else {

            SesionRDU session = ContextUtils.obtenerSesionUsuario();
            if (session != null) {
                session.getPromovente().getId_promovente();

                if (session.getPromovente().getId_promovente() == 20420) {
                    return true;
                } else {
                    return false;
                }
            }
            return false;

        }
        return false;
    }
    
    
    
    
}
