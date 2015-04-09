package mx.gob.impi.rdu.persistence.model;

import java.math.BigDecimal;
import java.util.Date;
import java.io.Serializable;

@SuppressWarnings("serial")
public class Pago implements Serializable{
    private Long idPago;

    private String foliopago;

    private String feps;

    private BigDecimal total;

    private Long idTramite;
    private Long idTramitePatentes;
    private Long idTramitePromoMarcas;

    private Date fechapago;

    private Date fechaPorPagar;

    public Long getIdPago() {
        return idPago;
    }

    public void setIdPago(Long idPago) {
        this.idPago = idPago;
    }

    public String getFoliopago() {
        return foliopago;
    }

    public void setFoliopago(String foliopago) {
        this.foliopago = foliopago == null ? null : foliopago.trim();
    }

    public String getFeps() {
        return feps;
    }

    public void setFeps(String feps) {
        this.feps = feps == null ? null : feps.trim();
    }

    public BigDecimal getTotal() {
        return total;
    }

    public void setTotal(BigDecimal total) {
        this.total = total;
    }

    public Long getIdTramite() {
        return idTramite;
    }

    public void setIdTramite(Long idTramite) {
        this.idTramite = idTramite;
    }

    public Date getFechapago() {
        return fechapago;
    }

    public void setFechapago(Date fechapago) {
        this.fechapago = fechapago;
    }

    public Date getFechaPorPagar() {
        return fechaPorPagar;
    }

    public void setFechaPorPagar(Date fechaPorPagar) {
        this.fechaPorPagar = fechaPorPagar;
    }

    public Long getIdTramitePatentes() {
        return idTramitePatentes;
    }

    public void setIdTramitePatentes(Long idTramitePatentes) {
        this.idTramitePatentes = idTramitePatentes;
    }

    public Long getIdTramitePromoMarcas() {
        return idTramitePromoMarcas;
    }

    public void setIdTramitePromoMarcas(Long idTramitePromoMarcas) {
        this.idTramitePromoMarcas = idTramitePromoMarcas;
    }

}