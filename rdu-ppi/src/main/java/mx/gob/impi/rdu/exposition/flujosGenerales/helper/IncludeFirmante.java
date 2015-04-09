/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.gob.impi.rdu.exposition.flujosGenerales.helper;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import mx.gob.impi.rdu.dto.ApoderadoDto;
import mx.gob.impi.rdu.dto.PromoventeDto;
import mx.gob.impi.rdu.exposition.SesionRDU;
import mx.gob.impi.rdu.persistence.model.CatTipopersona;
import mx.gob.impi.rdu.persistence.model.CodigosPostales;
import mx.gob.impi.rdu.persistence.model.EntidadFederativa;
import mx.gob.impi.rdu.persistence.model.Pais;
import mx.gob.impi.rdu.service.FlujosGralesViewServiceImpl;
import mx.gob.impi.rdu.util.Constantes;
import mx.gob.impi.rdu.util.ContextUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.BeanUtils;

/**
 *
 * @author cesar
 */
public class IncludeFirmante implements Serializable{

    private Logger log = Logger.getLogger(this.getClass());
    private List<ApoderadoDto> apoderados = Collections.emptyList();
    private ApoderadoDto apoderado = new ApoderadoDto();
    IncludeDomicilio includeDomicilio = new IncludeDomicilio();
    private List<Pais> paises = Collections.emptyList();
    private List<EntidadFederativa> entidadesFederativas = Collections.emptyList();
    private FlujosGralesViewServiceImpl flujosgralesViewService;
    private List<CatTipopersona> tiposPersona = Collections.emptyList();
    private CatTipopersona tipoPersona = new CatTipopersona();
    private List<Pais> nacionalidades = new ArrayList<Pais>();
    private Pais nacionalidad = new Pais();
    private Short principal;

    public void init(List<Pais> paises, List<EntidadFederativa> entidadesFederativas, FlujosGralesViewServiceImpl flujosgralesViewService, List<CatTipopersona> tiposPersona, List<Pais> nacionalidades) {
        if (log.isInfoEnabled()) {
            log.info("<------------Entrando al metodo IncludeFirmante.init()---------------------->");
        }
        obtenerApoderados();
        SesionRDU obtSession = ContextUtils.obtenerSesionUsuario();
        this.paises = paises;
        this.entidadesFederativas = entidadesFederativas;
        this.flujosgralesViewService = flujosgralesViewService;
        includeDomicilio.initDomicilio(paises, entidadesFederativas, flujosgralesViewService);
        obtenerPromovente(obtSession);
        this.tiposPersona = tiposPersona;
        this.nacionalidades = nacionalidades;
    }

    public void nacionalidad_valueChangeEvent() {
        overrideNombreNacionalidad();
    }

    public void overrideNombreNacionalidad() {
        for (Pais nacionalidadTemp : nacionalidades) {
            if (nacionalidadTemp.getIdPais().intValue() == nacionalidad.getIdPais().intValue()) {
                nacionalidad.setNacionalidad(nacionalidadTemp.getNacionalidad());
                break;
            }
        }
    }

    public void init(List<Pais> paises, List<EntidadFederativa> entidadesFederativas, FlujosGralesViewServiceImpl flujosgralesViewService) {
        if (log.isInfoEnabled()) {
            log.info("<------------Entrando al metodo IncludeFirmante.init()---------------------->");
        }
        obtenerApoderados();
        SesionRDU obtSession = ContextUtils.obtenerSesionUsuario();
        this.paises = paises;
        this.entidadesFederativas = entidadesFederativas;
        this.flujosgralesViewService = flujosgralesViewService;
        includeDomicilio.initDomicilio(paises, entidadesFederativas, flujosgralesViewService);
        obtenerPromovente(obtSession);
    }

    public void obtenerApoderados() {
        if (log.isInfoEnabled()) {
            log.info("*************+Entrando al metodo IncludeFirmante.obtenerApoderados***************++");
        }
        SesionRDU obtSession = ContextUtils.obtenerSesionUsuario();
        if (null != obtSession) {
            if (obtSession.getPromovente() != null) {
                if (obtSession.getPromovente().getApoderados()!= null && obtSession.getPromovente().getApoderados().size() > 0) {
                    apoderados = obtSession.getPromovente().getApoderados();
                }
            }
        }
    }

    public void apoderadosTable_onRowSelection() {
        validarDomicilioPromovente();
    }

    public void obtenerPromovente(SesionRDU obtSession) {
        if (null != obtSession) {
            PromoventeDto promovente = obtSession.getPromovente();
            BeanUtils.copyProperties(promovente, apoderado);
            this.validarDomicilioPromovente();
        }
    }

    public void validarDomicilioPromovente() {
        includeDomicilio.domicilio.setIdEntidad("0");
        includeDomicilio.entidadFederativaCombo.setIdEntidadFederativa(Constantes.ID_PAIS_MEXICO.intValue());
        includeDomicilio.domicilio.setNombreEntidad("");
        includeDomicilio.entidadFederativaCombo.setNombre("");
        includeDomicilio.domicilio.setCodigopostal("");
        includeDomicilio.domicilio.setPoblacion("");
        includeDomicilio.domicilio.setColonia("");
        includeDomicilio.domicilio.setCalle("");
        //includeDomicilio.datosContacto.setTelefono("");
        //includeDomicilio.datosContacto.setCorreoelectronico("");
        if (this.apoderado.getApaterno() != null && this.apoderado.getAmaterno() != null) {

//            this.apoderado.setNombre(apoderado.getNombre().toUpperCase() + " " + apoderado.getApaterno().toUpperCase() + " " + apoderado.getAmaterno().toUpperCase());
            this.apoderado.setNombre(apoderado.getNombre().toUpperCase());
            
            if(apoderado.getApaterno() != null)
                this.apoderado.setPrimerApellido(apoderado.getApaterno().toUpperCase());
            
            if(apoderado.getAmaterno() != null)
            this.apoderado.setSegundoApellido(apoderado.getAmaterno().toUpperCase());
            
            this.apoderado.setApaterno(null);
            this.apoderado.setAmaterno(null);
        }
        List<CodigosPostales> codigosPostalesTemp = flujosgralesViewService.obtenerCodigosPostales(apoderado.getCodigo_postal());
        if (codigosPostalesTemp.isEmpty() == false) {
            if (codigosPostalesTemp.size() == 1) {
                includeDomicilio.domicilio.setIdEntidad(codigosPostalesTemp.get(0).getCveEstado().toString().toUpperCase());
                includeDomicilio.domicilio.setNombreEntidad(codigosPostalesTemp.get(0).getEstado().toUpperCase());
                includeDomicilio.entidadFederativaCombo.setIdEntidadFederativa(codigosPostalesTemp.get(0).getCveEstado().intValue());
                includeDomicilio.entidadFederativaCombo.setNombre(codigosPostalesTemp.get(0).getEstado().toUpperCase());
                includeDomicilio.domicilio.setCodigopostal(codigosPostalesTemp.get(0).getCodigoPostal().toString());
                includeDomicilio.domicilio.setPoblacion(codigosPostalesTemp.get(0).getMunicipio().toUpperCase());
                includeDomicilio.domicilio.setColonia(codigosPostalesTemp.get(0).getAsentamiento().toString().toUpperCase());
                includeDomicilio.domicilio.setCalle(apoderado.getCalle_numero().toUpperCase() + (apoderado.getNumero_exterior() != null ? apoderado.getNumero_exterior().length() > 0 ? " " + apoderado.getNumero_exterior() : "" : "") + (apoderado.getNumero_interior() != null ? apoderado.getNumero_interior().length() > 0 ? " " + apoderado.getNumero_interior() : "" : ""));
                includeDomicilio.datosContacto.setTelefono(apoderado.getTelefono().toUpperCase());
                includeDomicilio.datosContacto.setCorreoelectronico(apoderado.getEmail());
                includeDomicilio.datosContacto.setFax(apoderado.getFax());
            } else {
                for (CodigosPostales codigos : codigosPostalesTemp) {
                    if (apoderado.getColonia().toUpperCase().equals(codigos.getAsentamiento().toString())) {
                        includeDomicilio.domicilio.setIdEntidad(codigos.getCveEstado().toString().toUpperCase());
                        includeDomicilio.domicilio.setNombreEntidad(codigos.getEstado().toUpperCase());
                        includeDomicilio.entidadFederativaCombo.setIdEntidadFederativa(codigos.getCveEstado().intValue());
                        includeDomicilio.entidadFederativaCombo.setNombre(codigos.getEstado().toUpperCase());
                        includeDomicilio.domicilio.setCodigopostal(codigos.getCodigoPostal().toString());
                        includeDomicilio.domicilio.setPoblacion(codigos.getMunicipio().toUpperCase());
                        includeDomicilio.domicilio.setColonia(codigos.getAsentamiento().toString().toUpperCase());
                        includeDomicilio.domicilio.setCalle(apoderado.getCalle_numero().toUpperCase() + (apoderado.getNumero_exterior() != null ? apoderado.getNumero_exterior().length() > 0 ? " " + apoderado.getNumero_exterior() : "" : "") + (apoderado.getNumero_interior() != null ? apoderado.getNumero_interior().length() > 0 ? " " + apoderado.getNumero_interior() : "" : ""));
                        includeDomicilio.datosContacto.setTelefono(apoderado.getTelefono().toUpperCase());
                        includeDomicilio.datosContacto.setCorreoelectronico(apoderado.getEmail());
                        includeDomicilio.datosContacto.setFax(apoderado.getFax());
                        break;
                    }
                }
            }
        }
        includeDomicilio.datosContacto.setTelefono(apoderado.getTelefono().toUpperCase());
        includeDomicilio.datosContacto.setCorreoelectronico(apoderado.getEmail());
        includeDomicilio.datosContacto.setFax(apoderado.getFax());

    }

    public ApoderadoDto getApoderado() {
        return apoderado;
    }

    public void setApoderado(ApoderadoDto apoderado) {
        this.apoderado = apoderado;
    }

    public List<ApoderadoDto> getApoderados() {
        return apoderados;
    }

    public void setApoderados(List<ApoderadoDto> apoderados) {
        this.apoderados = apoderados;
    }

    public IncludeDomicilio getIncludeDomicilio() {
        return includeDomicilio;
    }

    public void setIncludeDomicilio(IncludeDomicilio includeDomicilio) {
        this.includeDomicilio = includeDomicilio;
    }

    public List<EntidadFederativa> getEntidadesFederativas() {
        return entidadesFederativas;
    }

    public void setEntidadesFederativas(List<EntidadFederativa> entidadesFederativas) {
        this.entidadesFederativas = entidadesFederativas;
    }

    public List<Pais> getPaises() {
        return paises;
    }

    public void setPaises(List<Pais> paises) {
        this.paises = paises;
    }

    public List<CatTipopersona> getTiposPersona() {
        return tiposPersona;
    }

    public void setTiposPersona(List<CatTipopersona> tiposPersona) {
        this.tiposPersona = tiposPersona;
    }

    public CatTipopersona getTipoPersona() {
        return tipoPersona;
    }

    public void setTipoPersona(CatTipopersona tipoPersona) {
        this.tipoPersona = tipoPersona;
    }

    public List<Pais> getNacionalidades() {
        return nacionalidades;
    }

    public void setNacionalidades(List<Pais> nacionalidades) {
        this.nacionalidades = nacionalidades;
    }

    public Pais getNacionalidad() {
        return nacionalidad;
    }

    public void setNacionalidad(Pais nacionalidad) {
        this.nacionalidad = nacionalidad;
    }

    public Short getPrincipal() {
        return principal;
    }

    public void setPrincipal(Short principal) {
        this.principal = principal;
    }
}
