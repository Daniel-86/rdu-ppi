/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.gob.impi.rdu.exposition.flujosGenerales.helper;

import java.io.Serializable;
import java.util.Collections;
import java.util.List;
import java.util.ResourceBundle;
import java.util.regex.Pattern;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import mx.gob.impi.rdu.dto.DomicilioDto;
import mx.gob.impi.rdu.persistence.model.CodigosPostales;
import mx.gob.impi.rdu.persistence.model.Datoscontacto;
import mx.gob.impi.rdu.persistence.model.Domicilio;
import mx.gob.impi.rdu.persistence.model.EntidadFederativa;
import mx.gob.impi.rdu.persistence.model.Pais;
import mx.gob.impi.rdu.service.FlujosGralesViewServiceImpl;
import mx.gob.impi.rdu.util.Constantes;
import org.apache.log4j.Logger;

/**
 *
 * @author cesar
 */
public class IncludeDomicilio implements Serializable{

    private Logger log = Logger.getLogger(this.getClass());
    private static final String BUNDLE_PARAMETROS = "mx.gob.impi.rdu.i18n.CapturaSolicitud";
    private final ResourceBundle bundleParametrosMarcas = ResourceBundle.getBundle(BUNDLE_PARAMETROS);
    public DomicilioDto domicilio = new DomicilioDto();
    public CodigosPostales codigoPostalTable = new CodigosPostales();
    public Pais paisCombo = new Pais();
    public EntidadFederativa entidadFederativaCombo = new EntidadFederativa();
    public List<Pais> paises = Collections.emptyList();
    public List<DomicilioDto> domicilios = Collections.emptyList();
    public List<CodigosPostales> codigosPostalesTable = Collections.emptyList();
    public String codigoPostal = "";
    public List<EntidadFederativa> entidadesFederativas = Collections.emptyList();
    public Datoscontacto datosContacto = new Datoscontacto();
    private FlujosGralesViewServiceImpl flujosgralesViewService;
    private Pais paisApoderado = new Pais();

    public void initDomicilio(List<Pais> paises, List<EntidadFederativa> entidades, FlujosGralesViewServiceImpl flujosgralesViewService) {
        this.paises = paises;
        //Se comento para desabilitar la opcion de default MEXICO
//        paisCombo.setIdPais(Constantes.ID_PAIS);
//        paisCombo.setNombre(Constantes.NOMBRE_PAIS);
        
        //entidadFederativaCombo.setIdEntidadFederativa(Constantes.ID_ENTIDAD_FEDERATIVA.intValue());
        //entidadFederativaCombo.setNombre(Constantes.NOMBRE_ENTIDAD_FEDERATIVA);
        domicilio.setIdPais(paisCombo.getIdPais());
        domicilio.setNombrePais(paisCombo.getNombre());
        //domicilio.setIdEntidad(entidadFederativaCombo.getIdEntidadFederativa().toString());
        //domicilio.setNombreEntidad(entidadFederativaCombo.getNombre());
        this.entidadesFederativas = entidades;
        this.flujosgralesViewService = flujosgralesViewService;
        
        paisApoderado.setIdPais(Constantes.ID_PAIS);
        paisApoderado.setNombre(Constantes.NOMBRE_PAIS);
    }

    public void paisSelect_valueChangeEvent() {
        if (log.isInfoEnabled()) {
            log.info("<------------Entrando al metod SignosDistintivos.paisSelect_valueChangeEvent()---------------------->");
        }
        this.overrideNombrePais();
        domicilio.setIdPais(paisCombo.getIdPais());
        domicilio.setNombrePais(paisCombo.getNombre());
        if (log.isInfoEnabled()) {
            log.info("<--------------Domicilio PAIS------------------->");
            log.info("idPais:" + domicilio.getIdPais());
            log.info("Nombre Pais:" + domicilio.getNombrePais());
        }
        domicilio.setNombreEntidad("");
        domicilio.getEntidad().setNombre("");
        domicilio.setPoblacion("");
        domicilio.setCodigopostal("");
        domicilio.setColonia("");
        domicilio.setCalle("");
        domicilio.setNumExt("");
        domicilio.setNumInt("");
        datosContacto.setTelefono("");
        datosContacto.setTelefonoExt("");
        datosContacto.setFax("");
        datosContacto.setCorreoelectronico("");
        
    }

    public String cmdBuscarCodigoPostal2_action() {
        if (log.isInfoEnabled()) {
            log.info("<------------Entrando al metod SignosDistintivos.cmdBuscarCodigoPostal2_action()---------------------->");
        }
        if (Pattern.matches(Constantes.PATTERN_CODIGO_POSTAL, codigoPostal) == false) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(bundleParametrosMarcas.getString("mensaje.error.validar.codigopostal")));
        }
        DomicilioDto pDomicilio = new DomicilioDto();
        pDomicilio.setCodigopostal(codigoPostal);
        //domicilios = marcasViewService.consultarDomicilios(pDomicilio);
        codigosPostalesTable = flujosgralesViewService.obtenerCodigosPostales(pDomicilio.getCodigopostal());
        if (log.isInfoEnabled()) {
            for (CodigosPostales temp : codigosPostalesTable) {
                log.info("-------------------> Entidad pais: " + temp.getEstado());
                log.info("-------------------> Poblacion: " + temp.getMunicipio());
                log.info("-------------------> Colonia: " + temp.getAsentamiento());
            }
        }
        pDomicilio.setCodigopostal("");
        codigoPostal = "";
        return null;
    }

    public void codigoPostalTable_onRowSelect() {
        if (log.isInfoEnabled()) {
            domicilio.setIdEntidad(codigoPostalTable.getCveEstado().toString());
            domicilio.setPoblacion(codigoPostalTable.getMunicipio());
            domicilio.setColonia(codigoPostalTable.getAsentamiento());
            domicilio.setCodigopostal(codigoPostalTable.getCodigoPostal());
            log.info("<------------Entrando al metod SignosDistintivos.codigoPostalTable_onRowSelect---------------------->");
            log.info("-------------------> Id domicilio: " + domicilio.getIdDomicilio());
            log.info("-------------------> Nombre pais: " + domicilio.getNombrePais());
            log.info("-------------------> Entidad pais: " + domicilio.getNombreEntidad());
            log.info("-------------------> Poblacion: " + domicilio.getPoblacion());
            log.info("-------------------> Colonia: " + domicilio.getColonia());
            log.info("-------------------> Calle: " + domicilio.getCalle());
        }
        entidadFederativaCombo.setIdEntidadFederativa(Integer.parseInt((domicilio.getIdEntidad())));
        entidadFederativaCombo.setNombre(domicilio.getNombreEntidad());
        codigoPostal = "";
    }

    //Metodos para sobreescribir los atributos para los objetos de los combos
    public void overrideNombrePais() {
        for (Pais pais : paises) {
            if (pais.getIdPais().intValue() == paisCombo.getIdPais().intValue()) {
                paisCombo.setNombre(pais.getNombre());
                break;
            }
        }
    }

    public void entidadFederativaSelect_valueChangeEvent() {
        this.overrideNombreEntidadFederativa();
        if (log.isInfoEnabled()) {
            log.info("<------------Entrando al metod SignosDistintivos.entidadFederativaSelect_valueChangeEvent()---------------------->");
            log.info("idEntidadFederativa:" + entidadFederativaCombo.getIdEntidadFederativa());
            log.info("Nombre Entidad Federativa:" + entidadFederativaCombo.getNombre());
        }
    }

    public void overrideNombreEntidadFederativa() {
        for (EntidadFederativa entidadFederativa : entidadesFederativas) {
            if (entidadFederativa.getIdEntidadFederativa().intValue() == entidadFederativaCombo.getIdEntidadFederativa().intValue()) {
                entidadFederativaCombo.setNombre(entidadFederativa.getNombre());
                break;
            }
        }
    }

    public String getCodigoPostal() {
        return codigoPostal;
    }

    public void setCodigoPostal(String codigoPostal) {
        this.codigoPostal = codigoPostal;
    }

    public DomicilioDto getDomicilio() {
        return domicilio;
    }

    public void setDomicilio(DomicilioDto domicilio) {
        this.domicilio = domicilio;
    }

    public List<DomicilioDto> getDomicilios() {
        return domicilios;
    }

    public void setDomicilios(List<DomicilioDto> domicilios) {
        this.domicilios = domicilios;
    }

    public EntidadFederativa getEntidadFederativaCombo() {
        return entidadFederativaCombo;
    }

    public void setEntidadFederativaCombo(EntidadFederativa entidadFederativaCombo) {
        this.entidadFederativaCombo = entidadFederativaCombo;
    }

    public Pais getPaisCombo() {
        return paisCombo;
    }

    public void setPaisCombo(Pais paisCombo) {
        this.paisCombo = paisCombo;
    }

    public List<Pais> getPaises() {
        return paises;
    }

    public void setPaises(List<Pais> paises) {
        this.paises = paises;
    }

    public List<EntidadFederativa> getEntidadesFederativas() {
        return entidadesFederativas;
    }

    public void setEntidadesFederativas(List<EntidadFederativa> entidadesFederativas) {
        this.entidadesFederativas = entidadesFederativas;
    }

    public CodigosPostales getCodigoPostalTable() {
        return codigoPostalTable;
    }

    public void setCodigoPostalTable(CodigosPostales codigoPostalTable) {
        this.codigoPostalTable = codigoPostalTable;
    }

    public List<CodigosPostales> getCodigosPostalesTable() {
        return codigosPostalesTable;
    }

    public void setCodigosPostalesTable(List<CodigosPostales> codigosPostalesTable) {
        this.codigosPostalesTable = codigosPostalesTable;
    }

    public Datoscontacto getDatosContacto() {
        return datosContacto;
    }

    public void setDatosContacto(Datoscontacto datosContacto) {
        this.datosContacto = datosContacto;
    }

    public void setFlujosgralesViewService(FlujosGralesViewServiceImpl flujosgralesViewService) {
        this.flujosgralesViewService = flujosgralesViewService;
    }

    public Pais getPaisApoderado() {
        return paisApoderado;
    }

    public void setPaisApoderado(Pais paisApoderado) {
        this.paisApoderado = paisApoderado;
    }
    
    
    public String nicelyFormatted() {
        String niceAddress = "";
        niceAddress += domicilio.getCalle() 
                + " " + domicilio.getNumInt() 
                + (!"".equals(domicilio.getNumExt()) && domicilio.getNumExt()!= null? " numExt "+domicilio.getNumExt(): "")
                + " " + domicilio.getColonia()
                + ", " + domicilio.getPoblacion()
                + " " + domicilio.getNombreEntidad()
                + " " + domicilio.getNombrePais()
                + ". CP " + domicilio.getCodigopostal();
        return niceAddress;
    }
    
    public String fullPhone() {
        String fullPhone = "";
        fullPhone += datosContacto.getTelefono() 
                + (!"".equals(datosContacto.getTelefonoExt()) && datosContacto.getTelefonoExt()!= null? datosContacto.getTelefonoExt(): "");
        return fullPhone;
    }
    
}
