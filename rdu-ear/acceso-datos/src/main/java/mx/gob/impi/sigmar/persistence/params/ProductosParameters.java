/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.gob.impi.sigmar.persistence.params;

/**
 *
 * @author jess
 */
public class ProductosParameters {
    
    private Integer tipoSolicitud;
    private Integer anoSolicitud;
    private Integer expedienteSolicitud;
    
    private String producto;

    /**
     * @return the tipoSolicitud
     */
    public Integer getTipoSolicitud() {
        return tipoSolicitud;
    }

    /**
     * @param tipoSolicitud the tipoSolicitud to set
     */
    public void setTipoSolicitud(Integer tipoSolicitud) {
        this.tipoSolicitud = tipoSolicitud;
    }

    /**
     * @return the anoSolicitud
     */
    public Integer getAnoSolicitud() {
        return anoSolicitud;
    }

    /**
     * @param anoSolicitud the anoSolicitud to set
     */
    public void setAnoSolicitud(Integer anoSolicitud) {
        this.anoSolicitud = anoSolicitud;
    }

    /**
     * @return the expedienteSolicitud
     */
    public Integer getExpedienteSolicitud() {
        return expedienteSolicitud;
    }

    /**
     * @param expedienteSolicitud the expedienteSolicitud to set
     */
    public void setExpedienteSolicitud(Integer expedienteSolicitud) {
        this.expedienteSolicitud = expedienteSolicitud;
    }

    /**
     * @return the producto
     */
    public String getProducto() {
        return producto;
    }

    /**
     * @param producto the producto to set
     */
    public void setProducto(String producto) {
        this.producto = producto;
    }
    
    
    
   }
