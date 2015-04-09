package mx.gob.impi.rdu.persistence.model;

import java.io.Serializable;

public class ListaExpedientePromPat  implements Serializable{
   
    private String idPromocion;
    private String expCodOficina;
    private String expTipo;
    private String expSerie;
    private String expNumero;
    private String registro;

    public String getIdPromocion() {
        return idPromocion;
    }

    public void setIdPromocion(String idPromocion) {
        this.idPromocion = idPromocion;
    }

    public String getExpCodOficina() {
        return expCodOficina;
    }

    public void setExpCodOficina(String expCodOficina) {
        this.expCodOficina = expCodOficina;
    }

    public String getExpTipo() {
        return expTipo;
    }

    public void setExpTipo(String expTipo) {
        this.expTipo = expTipo;
    }

    public String getExpSerie() {
        return expSerie;
    }

    public void setExpSerie(String expSerie) {
        this.expSerie = expSerie;
    }

    public String getExpNumero() {
        return expNumero;
    }

    public void setExpNumero(String expNumero) {
        this.expNumero = expNumero;
    }

    public String getRegistro() {
        return registro;
    }

    public void setRegistro(String registro) {
        this.registro = registro;
    }
    
    

}