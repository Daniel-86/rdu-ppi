/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package mx.gob.impi.rdu.service;

import java.util.List;
import mx.gob.impi.rdu.persistence.model.CoordinacionEstatal;
import mx.gob.impi.rdu.persistence.model.Usuario;
import mx.gob.impi.rdu.remote.RduUsuariosBeanRemote;
import org.apache.log4j.Logger;

/**
 *
 * @author usradmin
 */
public class UsuarioServiceImpl implements UsuarioService{
    private Logger log = Logger.getLogger(this.getClass());
    private RduUsuariosBeanRemote rduUsuariosBeanRemote;
    
    public void setRduUsuariosBeanRemote(RduUsuariosBeanRemote rduUsuariosBeanRemote) {
        this.rduUsuariosBeanRemote = rduUsuariosBeanRemote;
    }

    public List<Usuario> UsuarioByfiltro(CoordinacionEstatal coordinacionEstatal) {
        try{
            List<Usuario> retUsuarios=this.rduUsuariosBeanRemote.selectByFiltro(coordinacionEstatal);
            return retUsuarios;

        } catch (Exception e) {
            log.fatal("Informacion de Error: -- " + e + " -- ubicado en: " + e.getLocalizedMessage());
            return null;
        }

    }

}
