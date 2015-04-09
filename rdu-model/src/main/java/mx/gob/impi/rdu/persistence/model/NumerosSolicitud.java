package mx.gob.impi.rdu.persistence.model;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 *
 * @author JBMM
 */
public class NumerosSolicitud implements Serializable{
    private Long idTramitePromocionMarca;
    private Long numero;
    private Short idTiponumero;
    private String productos;

    public String getProductos() {
        return productos;
    }

    public void setProductos(String productos) {
        this.productos = productos;
    }

    public Long getIdTramitePromocionMarca() {
        return idTramitePromocionMarca;
    }

    public void setIdTramitePromocionMarca(Long idTramitePromocionMarca) {
        this.idTramitePromocionMarca = idTramitePromocionMarca;
    }

    public Long getNumero() {
        return numero;
    }

    public void setNumero(Long numero) {
        this.numero = numero;
    }

    public Short getIdTiponumero() {
        return idTiponumero;
    }

    public void setIdTiponumero(Short idTiponumero) {
        this.idTiponumero = idTiponumero;
    }
}