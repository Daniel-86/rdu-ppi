/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.gob.impi.rdu.util.security;

import mx.gob.impi.rdu.persistence.model.Perfil;
import org.springframework.security.core.GrantedAuthority;


public class Rol extends Perfil implements  GrantedAuthority
{

    public Rol()
    {
        super();        
    }
    
    @Override
    public String getAuthority()
    {
        return getConstante();
    }        
}
