/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package mx.gob.impi.rdu.service;
import java.util.List;
import mx.gob.impi.rdu.persistence.model.*;
/**
 *
 * @author usradmin
 */
public interface UsuarioService {
    public List<Usuario> UsuarioByfiltro(CoordinacionEstatal coordinacionEstatal);

}
