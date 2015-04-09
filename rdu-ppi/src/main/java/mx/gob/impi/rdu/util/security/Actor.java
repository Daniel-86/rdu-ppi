/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.gob.impi.rdu.util.security;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import mx.gob.impi.rdu.persistence.model.Usuario;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

public class Actor implements UserDetails
{

    private List<GrantedAuthority> roles;
    private Usuario usuario;

    public Actor()
    {
        this.roles = new ArrayList<GrantedAuthority>();
    }

    public Collection<GrantedAuthority> getAuthorities()
    {
        return getRoles();
    }

    public String getPassword()
    {
        return this.usuario.getUsuarioSeguridad().getClave();
    }

    public String getUsername()
    {
        return this.usuario.getUsuarioSeguridad().getUsuarioStr();
    }

    public boolean isAccountNonExpired()
    {
        return this.usuario.getUsuarioSeguridad().getCuentaNoExpirada();
    }

    public boolean isAccountNonLocked()
    {
        return this.usuario.getUsuarioSeguridad().getCuentaNoBloqueda();
    }

    public boolean isCredentialsNonExpired()
    {
        return this.usuario.getUsuarioSeguridad().getCredencialNoExpirada();
    }

    public boolean isEnabled()
    {
        return this.usuario.getUsuarioSeguridad().getHabilitado();
    }

    public List<GrantedAuthority> getRoles()
    {
        return roles;
    }

    public void setRoles(List<GrantedAuthority> roles)
    {
        this.roles = roles;
    }

    /**
     * @return the usuario
     */
    public Usuario getUsuario()
    {
        return usuario;
    }

    /**
     * @param usuario the usuario to set
     */
    public void setUsuario(Usuario usuario)
    {
        this.usuario = usuario;
    }
}
