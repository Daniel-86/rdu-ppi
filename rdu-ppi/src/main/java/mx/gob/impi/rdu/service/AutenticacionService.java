package mx.gob.impi.rdu.service;

import java.util.List;
import mx.gob.impi.rdu.persistence.model.Usuario;
import mx.gob.impi.rdu.util.security.Rol;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

public interface AutenticacionService
{

    public UserDetails loadUserByUsername(String correo) throws UsernameNotFoundException;

    public List<Rol> getRolesFromUsuario(Usuario usuario);
}
