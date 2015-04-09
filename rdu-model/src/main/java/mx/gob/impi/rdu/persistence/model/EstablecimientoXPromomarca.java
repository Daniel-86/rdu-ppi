package mx.gob.impi.rdu.persistence.model;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 *
 * @author JBMM
 */
public class EstablecimientoXPromomarca implements Serializable{
    private BigDecimal idDomicilio;
    private BigDecimal idTramitePromocionMarca;
    Domicilio domicilio;

    public EstablecimientoXPromomarca() {
    }

    public EstablecimientoXPromomarca(Domicilio domicilio) {
        this.domicilio = domicilio;
    }   
    
    public BigDecimal getIdDomicilio() {
        return idDomicilio;
    }

    public void setIdDomicilio(BigDecimal idDomicilio) {
        this.idDomicilio = idDomicilio;
    }

    public BigDecimal getIdTramitePromocionMarca() {
        return idTramitePromocionMarca;
    }

    public void setIdTramitePromocionMarca(BigDecimal idTramitePromocionMarca) {
        this.idTramitePromocionMarca = idTramitePromocionMarca;
    }

    public Domicilio getDomicilio() {
        return domicilio;
    }

    public void setDomicilio(Domicilio domicilio) {
        this.domicilio = domicilio;
    }
}