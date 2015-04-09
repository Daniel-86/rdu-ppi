/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.gob.impi.rdu.exposition.catalogos;

import java.io.Serializable;
import java.util.List;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import mx.gob.impi.rdu.service.CatalogosViewServiceImpl;
import org.apache.log4j.Logger;
import mx.gob.impi.rdu.persistence.model.EntidadFederativa;


@ManagedBean(name = "entidadFederativaMB")
@ViewScoped
@SuppressWarnings("serial")
public class EntidadFederativaMB implements Serializable{
    private Logger log = Logger.getLogger(this.getClass());
    private List<EntidadFederativa> entidadesFederativas;
    
    @ManagedProperty(value="#{catalogosViewService}")
    private CatalogosViewServiceImpl catalogosViewService;
   
    public EntidadFederativaMB() { 
    }
    
    public void setCatalogosViewService(CatalogosViewServiceImpl catalogosViewService) {
        this.catalogosViewService = catalogosViewService;
    }

    public List<EntidadFederativa> getEntidadesFederativas() {
        entidadesFederativas = this.catalogosViewService.ConsultarEntidadesFederativas();
        return entidadesFederativas;
    }

    public void setEntidadesFederativas(List<EntidadFederativa> entidadesFederativas) {
        this.entidadesFederativas = entidadesFederativas;
    }
    
}
