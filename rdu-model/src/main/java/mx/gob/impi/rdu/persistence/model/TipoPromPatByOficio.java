package mx.gob.impi.rdu.persistence.model;

import java.io.Serializable;

public class TipoPromPatByOficio  implements Serializable{
    
    private String tipoPromocion;
    private String expediente;
    private String concesion;
    private String desOficio;
    private String desTipoPromocion;
    private String idDepartamento;
    private String desDepartamento;

    public String getTipoPromocion() {
        return tipoPromocion;
    }

    public void setTipoPromocion(String tipoPromocion) {
        this.tipoPromocion = tipoPromocion;
    }

    public String getExpediente() {
        return expediente;
    }

    public void setExpediente(String expediente) {
        this.expediente = expediente;
    }

    public String getConcesion() {
        return concesion;
    }

    public void setConcesion(String concesion) {
        this.concesion = concesion;
    }

    public String getDesOficio() {
        return desOficio;
    }

    public void setDesOficio(String desOficio) {
        this.desOficio = desOficio;
    }

    public String getDesTipoPromocion() {
        return desTipoPromocion;
    }

    public void setDesTipoPromocion(String desTipoPromocion) {
        this.desTipoPromocion = desTipoPromocion;
    }

    public String getIdDepartamento() {
        return idDepartamento;
    }

    public void setIdDepartamento(String idDepartamento) {
        this.idDepartamento = idDepartamento;
    }

    public String getDesDepartamento() {
        return desDepartamento;
    }

    public void setDesDepartamento(String desDepartamento) {
        this.desDepartamento = desDepartamento;
    }
    
}