package mx.gob.impi.rdu.ws.vo;

import java.math.BigDecimal;
import java.util.Date;

/**
 *
 * @author winter
 */
public class PagoVO {

    public PagoVO() {
        super();
    }

    public PagoVO(long idPago, String foliopago, String feps, BigDecimal total, long idTramite, Date fechapago) {
        this.idPago = idPago;
        this.foliopago = foliopago;
        this.feps = feps;
        this.total = total;
        this.idTramite = idTramite;
        this.fechapago = fechapago;
    }


    private long idPago;

    private String foliopago;

    private String feps;

    private BigDecimal total;

    private long idTramite;

    private Date fechapago;

    public long getIdPago() {
        return idPago;
    }

    public void setIdPago(long idPago) {
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

    public long getIdTramite() {
        return idTramite;
    }

    public void setIdTramite(long idTramite) {
        this.idTramite = idTramite;
    }

    public Date getFechapago() {
        return fechapago;
    }

    public void setFechapago(Date fechapago) {
        this.fechapago = fechapago;
    }
}