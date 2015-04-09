package mx.gob.impi.rdu.remote.impl;

import java.util.AbstractList;
import java.util.ArrayList;
import mx.gob.impi.rdu.persistence.model.*;

import java.util.List;

import javax.ejb.Stateless;
import javax.interceptor.Interceptors;
import mx.gob.impi.rdu.dto.PersonaDto;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.ejb.interceptor.SpringBeanAutowiringInterceptor;

import mx.gob.impi.rdu.remote.RduUsuariosBeanLocal;
import mx.gob.impi.rdu.remote.RduUsuariosBeanRemote;
import mx.gob.impi.rdu.service.RduUsuariosService;
import mx.gob.impi.rdu.service.impl.RduUsuariosServiceImpl;
import org.springframework.beans.BeanUtils;

@Stateless (name="EJBRduUsuarios",mappedName="EJBRduUsuarios",description="")
@Interceptors(SpringBeanAutowiringInterceptor.class)
public class RduUsuariosBeanImpl implements RduUsuariosBeanRemote, RduUsuariosBeanLocal
{

    @Autowired
    @Qualifier("rduUsuariosServiceImpl")
    private RduUsuariosService rduUsuariosService;

    public void setRduUsuariosService(RduUsuariosService rduUsuariosService) {
        this.rduUsuariosService = rduUsuariosService;
    }

    public Usuario verificarUsuarioByLogin(String usrname) throws Exception
    {
        return this.rduUsuariosService.verificarUsuarioByLogin(usrname);
    }

    public List<Usuario> selectByFiltro(CoordinacionEstatal coordinacionEstatal) throws Exception
    {
        return this.rduUsuariosService.selectByFiltro(coordinacionEstatal);
        
    }

    public Persona getSolicitanteById(Integer id) {
        System.out.println("*************RduUsuarioBeanImpl.getSolicitanteById()*****************");
       return this.rduUsuariosService.getSolicitanteById(id);
    }

    public int insertSolicitante(Persona solicitante) {
        System.out.println("*************RduUsuarioBeanImpl.insertSolicitanteById()*****************");
        return this.rduUsuariosService.insertSolicitante(solicitante);
    }

    public int deleteSolicitante(Integer idSolicitante) {
        System.out.println("*************RduUsuarioBeanImpl.deleteSolicitanteById()*****************");
        return this.rduUsuariosService.deleteSolicitante(1);
    }

    public int updateSolicitante(Persona solicitante) {
        System.out.println("*************RduUsuarioBeanImpl.updateSolicitanteById()*****************");
        return this.rduUsuariosService.updateSolicitante(solicitante);
    }

    public List<Persona> selectDynamic(Persona solicitante) {
        return rduUsuariosService.selectDynamic(solicitante);
    }

  

}