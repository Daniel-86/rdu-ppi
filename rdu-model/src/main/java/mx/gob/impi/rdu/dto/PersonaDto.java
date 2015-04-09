/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.gob.impi.rdu.dto;

import java.io.Serializable;

/**
 *
 * @author
 */
public class PersonaDto implements Serializable {

    private Long idSolicitante;
    private String nombrecompleto;
    private Long idNacionalidad;
    private Short idTipopersona;
    private Long idDatoscontacto;
    private Long idDomiclio;
    private DomicilioDto domicilioDto = new DomicilioDto();
    private DatoscontactoDto datosContactoDto = new DatoscontactoDto();
    private PaisDto paisDto = new PaisDto();
    private PaisDto nacionalidadDto = new PaisDto();
    private CatTipopersonaDto tipoPersonaDto = new CatTipopersonaDto();

    public PaisDto getNacionalidadDto() {
        return nacionalidadDto;
    }

    public void setNacionalidadDto(PaisDto nacionalidadDto) {
        this.nacionalidadDto = nacionalidadDto;
    }
    
    public DatoscontactoDto getDatosContactoDto() {
        return datosContactoDto;
    }

    public DomicilioDto getDomicilioDto() {
        return domicilioDto;
    }

    public Long getIdDatoscontacto() {
        return idDatoscontacto;
    }

    public Long getIdDomiclio() {
        return idDomiclio;
    }

    public Long getIdNacionalidad() {
        return idNacionalidad;
    }

    public Long getIdSolicitante() {
        return idSolicitante;
    }

    public Short getIdTipopersona() {
        return idTipopersona;
    }

    public String getNombrecompleto() {
        return nombrecompleto;
    }

    public PaisDto getPaisDto() {
        return paisDto;
    }

    public CatTipopersonaDto getTipoPersonaDto() {
        return tipoPersonaDto;
    }

    public void setDatosContactoDto(DatoscontactoDto datosContactoDto) {
        this.datosContactoDto = datosContactoDto;
    }

    public void setDomicilioDto(DomicilioDto domicilioDto) {
        this.domicilioDto = domicilioDto;
    }

    public void setIdDatoscontacto(Long idDatoscontacto) {
        this.idDatoscontacto = idDatoscontacto;
    }

    public void setIdDomiclio(Long idDomiclio) {
        this.idDomiclio = idDomiclio;
    }

    public void setIdNacionalidad(Long idNacionalidad) {
        this.idNacionalidad = idNacionalidad;
    }

    public void setIdSolicitante(Long idSolicitante) {
        this.idSolicitante = idSolicitante;
    }

    public void setIdTipopersona(Short idTipopersona) {
        this.idTipopersona = idTipopersona;
    }

    public void setNombrecompleto(String nombrecompleto) {
        this.nombrecompleto = nombrecompleto;
    }

    public void setPaisDto(PaisDto paisDto) {
        this.paisDto = paisDto;
    }

    public void setTipoPersonaDto(CatTipopersonaDto tipoPersonaDto) {
        this.tipoPersonaDto = tipoPersonaDto;
    }
}
