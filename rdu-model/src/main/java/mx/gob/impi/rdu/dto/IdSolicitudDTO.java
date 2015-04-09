/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.gob.impi.rdu.dto;

import java.io.Serializable;

/**
 *
 * @author jess
 */
public class IdSolicitudDTO implements  Serializable{
    
    private int tipoSolicitud;
    private int anoSolicitud;
    private int expedienteSolicitud;

    public IdSolicitudDTO(int tipoSolicitud, int anoSolicitud, int expedienteSolicitud) {
        this.tipoSolicitud = tipoSolicitud;
        this.anoSolicitud = anoSolicitud;
        this.expedienteSolicitud = expedienteSolicitud;
    }

    public IdSolicitudDTO() {
    }
    
    

    /**
     * @return the tipoSolicitud
     */
    public int getTipoSolicitud() {
        return tipoSolicitud;
    }

    /**
     * @param tipoSolicitud the tipoSolicitud to set
     */
    public void setTipoSolicitud(int tipoSolicitud) {
        this.tipoSolicitud = tipoSolicitud;
    }

    /**
     * @return the anoSolicitud
     */
    public int getAnoSolicitud() {
        return anoSolicitud;
    }

    /**
     * @param anoSolicitud the anoSolicitud to set
     */
    public void setAnoSolicitud(int anoSolicitud) {
        this.anoSolicitud = anoSolicitud;
    }

    /**
     * @return the expedienteSolicitud
     */
    public int getExpedienteSolicitud() {
        return expedienteSolicitud;
    }

    /**
     * @param expedienteSolicitud the expedienteSolicitud to set
     */
    public void setExpedienteSolicitud(int expedienteSolicitud) {
        this.expedienteSolicitud = expedienteSolicitud;
    }
    
    
    
}
