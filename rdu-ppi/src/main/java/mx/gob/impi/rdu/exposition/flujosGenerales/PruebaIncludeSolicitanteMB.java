/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.gob.impi.rdu.exposition.flujosGenerales;

import java.util.Collections;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import mx.gob.impi.rdu.exposition.flujosGenerales.helper.IncludeDomicilio;
import mx.gob.impi.rdu.exposition.flujosGenerales.helper.IncludeFirmante;
import mx.gob.impi.rdu.persistence.model.EntidadFederativa;
import mx.gob.impi.rdu.persistence.model.Pais;
import mx.gob.impi.rdu.service.CatalogosViewServiceImpl;
import mx.gob.impi.rdu.service.FlujosGralesViewServiceImpl;
import mx.gob.impi.rdu.util.Constantes;
import org.apache.log4j.Logger;

/**
 *
 * @author cesar
 */
@ManagedBean
@ViewScoped
public class PruebaIncludeSolicitanteMB {

    Logger log = Logger.getLogger(this.getClass());
    private List<Pais> paises = Collections.emptyList();
    private List<EntidadFederativa> entidadesFederativas = Collections.emptyList();
    @ManagedProperty(value = "#{catalogosViewService}")
    private CatalogosViewServiceImpl catalogosViewService;
    @ManagedProperty(value = "#{flujosgralesViewService}")
    private FlujosGralesViewServiceImpl flujosgralesViewService;
    IncludeFirmante firmante = new IncludeFirmante();
    IncludeDomicilio includeDomicilio = new IncludeDomicilio();
    IncludeDomicilio includeDomicilio2 = new IncludeDomicilio();

    @PostConstruct
    public void init() {
        if (log.isInfoEnabled()) {
            log.info(">>>>>>>>>>>>>>>>>>>>>>>Entrando al metodo PruebaIncludeSolicitanteMB.init()>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>");
        }
        Pais paisArea = new Pais();
        paisArea.setIdArea(Constantes.AREA_MARCAS);
        paises = catalogosViewService.consultarPaises(paisArea);
        entidadesFederativas = catalogosViewService.ConsultarEntidadesFederativas();
        firmante.init(paises, entidadesFederativas, flujosgralesViewService);
        includeDomicilio.initDomicilio(paises, entidadesFederativas, flujosgralesViewService);
        includeDomicilio2.initDomicilio(paises, entidadesFederativas, flujosgralesViewService);
    }

    public String mostrarDatos() {
        if (log.isInfoEnabled()) {
            log.info("Nombre: " + firmante.getApoderado().getNombre());
            log.info("Pais: " + firmante.getIncludeDomicilio().getPaisCombo().getNombre());
            firmante.getIncludeDomicilio().overrideNombreEntidadFederativa();
            log.info("Entidad: " + firmante.getIncludeDomicilio().getEntidadFederativaCombo().getNombre());
            log.info("Codigo postal: " + firmante.getIncludeDomicilio().getDomicilio().getCodigopostal());
            log.info("Poblacion: " + firmante.getIncludeDomicilio().getDomicilio().getPoblacion());
            log.info("Colonia: " + firmante.getIncludeDomicilio().getDomicilio().getColonia());
            log.info("Calle: " + firmante.getIncludeDomicilio().getDomicilio().getCalle());
            log.info("Telefono: " + firmante.getIncludeDomicilio().getDatosContacto().getTelefono());
            log.info("Correo: " + firmante.getIncludeDomicilio().getDatosContacto().getCorreoelectronico());
            log.info("Codigo postal 2:" + includeDomicilio.getDomicilio().getCodigopostal());
        }
        return null;
    }

    public void setCatalogosViewService(CatalogosViewServiceImpl catalogosViewService) {
        this.catalogosViewService = catalogosViewService;
    }

    public void setFlujosgralesViewService(FlujosGralesViewServiceImpl flujosgralesViewService) {
        this.flujosgralesViewService = flujosgralesViewService;
    }

    public List<EntidadFederativa> getEntidadesFederativas() {
        return entidadesFederativas;
    }

    public void setEntidadesFederativas(List<EntidadFederativa> entidadesFederativas) {
        this.entidadesFederativas = entidadesFederativas;
    }

    public IncludeFirmante getFirmante() {
        return firmante;
    }

    public void setFirmante(IncludeFirmante firmante) {
        this.firmante = firmante;
    }

    public List<Pais> getPaises() {
        return paises;
    }

    public void setPaises(List<Pais> paises) {
        this.paises = paises;
    }

    public IncludeDomicilio getIncludeDomicilio() {
        return includeDomicilio;
    }

    public void setIncludeDomicilio(IncludeDomicilio includeDomicilio) {
        this.includeDomicilio = includeDomicilio;
    }

    public IncludeDomicilio getIncludeDomicilio2() {
        return includeDomicilio2;
    }

    public void setIncludeDomicilio2(IncludeDomicilio includeDomicilio2) {
        this.includeDomicilio2 = includeDomicilio2;
    }
}
