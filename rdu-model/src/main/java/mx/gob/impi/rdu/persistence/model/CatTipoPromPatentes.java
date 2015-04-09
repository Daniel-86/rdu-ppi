package mx.gob.impi.rdu.persistence.model;

import java.io.Serializable;

public class CatTipoPromPatentes  implements Serializable{
    private String idTipoPromocion;
    private String descripcion;
    private String indDescuento;
    private String indPlazoAdicional;
    private String indExpVinculado;
    private String indExpMultiple;
    private String tarifaArticulo;
    private String tarifaInciso;
    private String articulo;

    public String getIdTipoPromocion() {
        return idTipoPromocion;
    }

    public void setIdTipoPromocion(String idTipoPromocion) {
        this.idTipoPromocion = idTipoPromocion;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getIndDescuento() {
        return indDescuento;
    }

    public void setIndDescuento(String indDescuento) {
        this.indDescuento = indDescuento;
    }

    public String getIndPlazoAdicional() {
        return indPlazoAdicional;
    }

    public void setIndPlazoAdicional(String indPlazoAdicional) {
        this.indPlazoAdicional = indPlazoAdicional;
    }

    public String getIndExpVinculado() {
        return indExpVinculado;
    }

    public void setIndExpVinculado(String indExpVinculado) {
        this.indExpVinculado = indExpVinculado;
    }

    public String getIndExpMultiple() {
        return indExpMultiple;
    }

    public void setIndExpMultiple(String indExpMultiple) {
        this.indExpMultiple = indExpMultiple;
    }

    public String getTarifaArticulo() {
        return tarifaArticulo;
    }

    public void setTarifaArticulo(String tarifaArticulo) {
        this.tarifaArticulo = tarifaArticulo;
    }

    public String getTarifaInciso() {
        return tarifaInciso;
    }

    public void setTarifaInciso(String tarifaInciso) {
        this.tarifaInciso = tarifaInciso;
    }

    public String getArticulo() {
        return articulo;
    }

    public void setArticulo(String articulo) {
        this.articulo = articulo;
    }   
    
}