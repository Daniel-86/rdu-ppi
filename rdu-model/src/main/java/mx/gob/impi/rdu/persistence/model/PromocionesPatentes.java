package mx.gob.impi.rdu.persistence.model;

import java.io.Serializable;
import java.util.List;

public class PromocionesPatentes  implements Serializable{

    private String idPromocion;
    private String idUsuario;
    private String idTipoPromPat;
    private String oficioCodOficina;
    private String oficioSerie;
    private String oficioFolio;
    private String plazoAdicional;
    private String indDescuento;
    private String descripcionProm;
    private String actualiza;
    private List<ListaExpedientePromPat> listaExpedientePromPat;

    public String getIdPromocion() {
        return idPromocion;
    }

    public void setIdPromocion(String idPromocion) {
        this.idPromocion = idPromocion;
    }

    public String getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(String idUsuario) {
        this.idUsuario = idUsuario;
    }

    public String getIdTipoPromPat() {
        return idTipoPromPat;
    }

    public void setIdTipoPromPat(String idTipoPromPat) {
        this.idTipoPromPat = idTipoPromPat;
    }

    public String getOficioCodOficina() {
        return oficioCodOficina;
    }

    public void setOficioCodOficina(String oficioCodOficina) {
        this.oficioCodOficina = oficioCodOficina;
    }

    public String getOficioSerie() {
        return oficioSerie;
    }

    public void setOficioSerie(String oficioSerie) {
        this.oficioSerie = oficioSerie;
    }

    public String getOficioFolio() {
        return oficioFolio;
    }

    public void setOficioFolio(String oficioFolio) {
        this.oficioFolio = oficioFolio;
    }

    public String getPlazoAdicional() {
        return plazoAdicional;
    }

    public void setPlazoAdicional(String plazoAdicional) {
        this.plazoAdicional = plazoAdicional;
    }

    public String getIndDescuento() {
        return indDescuento;
    }

    public void setIndDescuento(String indDescuento) {
        this.indDescuento = indDescuento;
    }

    public String getDescripcionProm() {
        return descripcionProm;
    }

    public void setDescripcionProm(String descripcionProm) {
        this.descripcionProm = descripcionProm;
    }

    public List<ListaExpedientePromPat> getListaExpedientePromPat() {
        return listaExpedientePromPat;
    }

    public void setListaExpedientePromPat(List<ListaExpedientePromPat> listaExpedientePromPat) {
        this.listaExpedientePromPat = listaExpedientePromPat;
    }

    public String getActualiza() {
        return actualiza;
    }

    public void setActualiza(String actualiza) {
        this.actualiza = actualiza;
    }
}