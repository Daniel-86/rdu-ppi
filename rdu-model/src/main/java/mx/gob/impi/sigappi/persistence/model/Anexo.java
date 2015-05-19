package mx.gob.impi.sigappi.persistence.model;

import mx.gob.impi.sigappi.persistence.model.*;
import java.io.Serializable;
import java.util.Date;

public class Anexo implements Serializable {
private String codbarras;
private Integer secuencia;
private Integer categoria;
private Integer division;
private Integer seccion;
private String numero;
private Integer monto;
private String entidad;
private Date vigencia;
private String codbarrasInterno;
private String folio;
private Date expedicion;
private Integer indPublicidad;

    public String getCodbarras() {
        return codbarras;
    }

    public void setCodbarras(String codbarras) {
        this.codbarras = codbarras;
    }

    public Integer getSecuencia() {
        return secuencia;
    }

    public void setSecuencia(Integer secuencia) {
        this.secuencia = secuencia;
    }

    public Integer getCategoria() {
        return categoria;
    }

    public void setCategoria(Integer categoria) {
        this.categoria = categoria;
    }

    public Integer getDivision() {
        return division;
    }

    public void setDivision(Integer division) {
        this.division = division;
    }

    public Integer getSeccion() {
        return seccion;
    }

    public void setSeccion(Integer seccion) {
        this.seccion = seccion;
    }

    public String getNumero() {
        return numero;
    }

    public void setNumero(String numero) {
        this.numero = numero;
    }

    public Integer getMonto() {
        return monto;
    }

    public void setMonto(Integer monto) {
        this.monto = monto;
    }

    public String getEntidad() {
        return entidad;
    }

    public void setEntidad(String entidad) {
        this.entidad = entidad;
    }

    public Date getVigencia() {
        return vigencia;
    }

    public void setVigencia(Date vigencia) {
        this.vigencia = vigencia;
    }

    public String getCodbarrasInterno() {
        return codbarrasInterno;
    }

    public void setCodbarrasInterno(String codbarrasInterno) {
        this.codbarrasInterno = codbarrasInterno;
    }

    public String getFolio() {
        return folio;
    }

    public void setFolio(String folio) {
        this.folio = folio;
    }

    public Date getExpedicion() {
        return expedicion;
    }

    public void setExpedicion(Date expedicion) {
        this.expedicion = expedicion;
    }

    public Integer getIndPublicidad() {
        return indPublicidad;
    }

    public void setIndPublicidad(Integer indPublicidad) {
        this.indPublicidad = indPublicidad;
    }


}
