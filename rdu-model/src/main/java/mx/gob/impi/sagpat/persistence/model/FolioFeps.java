package mx.gob.impi.sagpat.persistence.model;

import java.math.BigDecimal;
import java.util.Date;

public class FolioFeps implements java.io.Serializable { 
    private String codOficina;

    private Long ffeps;

    private Integer numDocum;

    private Short serDocum;

    private String tipLibro;

    private String codbarras;

    private String tipEntrada;

    private Date fecCaptura;

    private Date fecPago;

    private BigDecimal cantidadPago;

    private Short coneccionIngresos;

    private BigDecimal tarifa;

    private BigDecimal iva;

    private Short banco;

    private Date fechaModificacion;

    private Integer userModificacion;

    private String tipPago;

    public String getCodOficina() {
        return codOficina;
    }

    public void setCodOficina(String codOficina) {
        this.codOficina = codOficina == null ? null : codOficina.trim();
    }

    public Long getFfeps() {
        return ffeps;
    }

    public void setFfeps(Long ffeps) {
        this.ffeps = ffeps;
    }

    public Integer getNumDocum() {
        return numDocum;
    }

    public void setNumDocum(Integer numDocum) {
        this.numDocum = numDocum;
    }

    public Short getSerDocum() {
        return serDocum;
    }

    public void setSerDocum(Short serDocum) {
        this.serDocum = serDocum;
    }

    public String getTipLibro() {
        return tipLibro;
    }

    public void setTipLibro(String tipLibro) {
        this.tipLibro = tipLibro == null ? null : tipLibro.trim();
    }

    public String getCodbarras() {
        return codbarras;
    }

    public void setCodbarras(String codbarras) {
        this.codbarras = codbarras == null ? null : codbarras.trim();
    }

    public String getTipEntrada() {
        return tipEntrada;
    }

    public void setTipEntrada(String tipEntrada) {
        this.tipEntrada = tipEntrada == null ? null : tipEntrada.trim();
    }

    public Date getFecCaptura() {
        return fecCaptura;
    }

    public void setFecCaptura(Date fecCaptura) {
        this.fecCaptura = fecCaptura;
    }

    public Date getFecPago() {
        return fecPago;
    }

    public void setFecPago(Date fecPago) {
        this.fecPago = fecPago;
    }

    public BigDecimal getCantidadPago() {
        return cantidadPago;
    }

    public void setCantidadPago(BigDecimal cantidadPago) {
        this.cantidadPago = cantidadPago;
    }

    public Short getConeccionIngresos() {
        return coneccionIngresos;
    }

    public void setConeccionIngresos(Short coneccionIngresos) {
        this.coneccionIngresos = coneccionIngresos;
    }

    public BigDecimal getTarifa() {
        return tarifa;
    }

    public void setTarifa(BigDecimal tarifa) {
        this.tarifa = tarifa;
    }

    public BigDecimal getIva() {
        return iva;
    }

    public void setIva(BigDecimal iva) {
        this.iva = iva;
    }

    public Short getBanco() {
        return banco;
    }

    public void setBanco(Short banco) {
        this.banco = banco;
    }

    public Date getFechaModificacion() {
        return fechaModificacion;
    }

    public void setFechaModificacion(Date fechaModificacion) {
        this.fechaModificacion = fechaModificacion;
    }

    public Integer getUserModificacion() {
        return userModificacion;
    }

    public void setUserModificacion(Integer userModificacion) {
        this.userModificacion = userModificacion;
    }

    public String getTipPago() {
        return tipPago;
    }

    public void setTipPago(String tipPago) {
        this.tipPago = tipPago == null ? null : tipPago.trim();
    }
}