/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.gob.impi.rdu.dto;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 *
 * @author 
 */
public class PagoDto  implements Serializable{
    
    private Long idPago;

    private String foliopago;

    private String feps;

    private BigDecimal total;

    private Long idTramite;

    private Date fechapago;

    public Date getFechapago() {
        return fechapago;
    }

    public String getFeps() {
        return feps;
    }

    public String getFoliopago() {
        return foliopago;
    }

    public Long getIdPago() {
        return idPago;
    }

    public Long getIdTramite() {
        return idTramite;
    }

    public BigDecimal getTotal() {
        return total;
    }

    public void setFechapago(Date fechapago) {
        this.fechapago = fechapago;
    }

    public void setFeps(String feps) {
        this.feps = feps;
    }

    public void setFoliopago(String foliopago) {
        this.foliopago = foliopago;
    }

    public void setIdPago(Long idPago) {
        this.idPago = idPago;
    }

    public void setIdTramite(Long idTramite) {
        this.idTramite = idTramite;
    }

    public void setTotal(BigDecimal total) {
        this.total = total;
    }

    
    
}
