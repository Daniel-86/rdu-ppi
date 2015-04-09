/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package mx.gob.impi.rdu.exposition.usuarios;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import mx.gob.impi.rdu.persistence.model.*;
import mx.gob.impi.rdu.service.UsuarioServiceImpl;
import mx.gob.impi.rdu.service.CatalogosViewServiceImpl;
import org.apache.log4j.Logger;

/**
 *
 * @author usradmin
 */
@ManagedBean(name = "usuarioMB")
@ViewScoped
public class UsuarioMB implements Serializable{

    private Logger log = Logger.getLogger(this.getClass());
    private List<Usuario> usuarios = Collections.emptyList();
    private CoordinacionEstatal coordinacionEstatal = new CoordinacionEstatal();
    private List<CoordinacionEstatal> coordinacionesEstatalesFiltro = Collections.emptyList();
    protected Usuario usuario = new Usuario();

    @ManagedProperty(value = "#{usuarioService}")
    private UsuarioServiceImpl usuarioService;

    @ManagedProperty(value="#{catalogosViewService}")
    private CatalogosViewServiceImpl catalogosViewService;


    public UsuarioMB() {
    }

    public void setUsuarioService(UsuarioServiceImpl usuarioService) {
        this.usuarioService = usuarioService;
    }

    public void setCatalogosViewService(CatalogosViewServiceImpl catalogosViewService) {
        this.catalogosViewService = catalogosViewService;
    }


    public List<Usuario> getUsuarios() {
        return usuarios;
    }

    public void setUsuarios(List<Usuario> usuarios) {
        this.usuarios = usuarios;
    }

    public CoordinacionEstatal getCoordinacionEstatal() {
        return coordinacionEstatal;
    }

    public void setCoordinacionEstatal(CoordinacionEstatal coordinacionEstatal) {
        this.coordinacionEstatal = coordinacionEstatal;
    }

    public List<CoordinacionEstatal> getCoordinacionesEstatalesFiltro() {
        return coordinacionesEstatalesFiltro;
    }

    public void setCoordinacionesEstatalesFiltro(List<CoordinacionEstatal> coordinacionesEstatalesFiltro) {
        this.coordinacionesEstatalesFiltro = coordinacionesEstatalesFiltro;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    
    @PostConstruct
    public void init() {
        try {
            CoordinacionEstatal coodinacionEstatal= new CoordinacionEstatal();
            coodinacionEstatal.setIdCoordinacion("500");
            usuarios=this.usuarioService.UsuarioByfiltro(coodinacionEstatal);

        } catch (Exception e) {
            log.fatal("Informacion de Error: -- " + e + " -- ubicado en: " + e.getLocalizedMessage());
        }
    }

    public void entidadFiltroListener() throws Exception {
        System.out.println("-- entidadFiltroListener --");
        System.out.println("entidad filtro: " + coordinacionEstatal.getEntidadFederativa().getIdEntidadFederativa());

        if (coordinacionEstatal.getEntidadFederativa().getIdEntidadFederativa() != null
                && coordinacionEstatal.getEntidadFederativa().getIdEntidadFederativa() > 0) {
            coordinacionEstatal.setIdEntidadFederativa(coordinacionEstatal.getEntidadFederativa().getIdEntidadFederativa());
            coordinacionEstatal.setIdCoordinacion("");
            coordinacionesEstatalesFiltro = this.catalogosViewService.ConsultarCoordinacionesEstatales(coordinacionEstatal);
            System.out.println("coordinacionesEstatalesFiltro: " + coordinacionesEstatalesFiltro.size());
            usuarios = this.usuarioService.UsuarioByfiltro(coordinacionEstatal);

        } else {
            //usuarios = sniimUsuariosBeanRemote.getAll();
            usuarios = this.usuarioService.UsuarioByfiltro(coordinacionEstatal);
            coordinacionesEstatalesFiltro = Collections.emptyList();
        }
    }
    public void coordinacionFiltroListener() throws Exception {
        System.out.println("-- coordinacionFiltroListener --");
        System.out.println("entidad filtro: " + coordinacionEstatal.getEntidadFederativa().getIdEntidadFederativa());
        System.out.println("coordinacion filtro: " + coordinacionEstatal.getIdCoordinacion());
        usuarios = this.usuarioService.UsuarioByfiltro(coordinacionEstatal);
    }

    public String editar(){
        System.out.println("--- Editar ---");
        System.out.println("selectedUsuario: " + usuario);
        return null;
    }


}
