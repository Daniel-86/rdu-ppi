package mx.gob.impi.rdu.persistence.model;

import java.io.Serializable;

/**
 *
 * @author JBMM
 */
public class TramitePersona implements Serializable {

    private Long idSolicitante;
    private Long idTramite;
    private Long idClasePersona;
    private Short esSolicitante;
    Persona persona;

    public Persona getPersona() {
        return persona;
    }

    public void setPersona(Persona persona) {
        this.persona = persona;
    }

    public Long getIdSolicitante() {
        return idSolicitante;
    }

    public void setIdSolicitante(Long idSolicitante) {
        this.idSolicitante = idSolicitante;
    }

    public Long getIdTramite() {
        return idTramite;
    }

    public void setIdTramite(Long idTramite) {
        this.idTramite = idTramite;
    }

    public Long getIdClasePersona() {
        return idClasePersona;
    }

    public void setIdClasePersona(Long idClasePersona) {
        this.idClasePersona = idClasePersona;
    }

    public Short getEsSolicitante() {
        return esSolicitante;
    }

    public void setEsSolicitante(Short esSolicitante) {
        this.esSolicitante = esSolicitante;
    }
}
