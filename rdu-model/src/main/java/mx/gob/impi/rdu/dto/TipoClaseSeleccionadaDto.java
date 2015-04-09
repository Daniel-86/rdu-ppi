/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.gob.impi.rdu.dto;

import java.io.Serializable;

/**
 *
 * @author
 */
public class TipoClaseSeleccionadaDto  implements Serializable{
    
     private Long idTipoclaseseleccionada;

    private Long idTramite;

    private Short idFormaseleccionclase;

    private String descripcionlibre;

    private long  idClase;
    
    private String tipoClase;

    public String getDescripcionlibre() {
        return descripcionlibre;
    }

    public long getIdClase() {
        return idClase;
    }

    public Short getIdFormaseleccionclase() {
        return idFormaseleccionclase;
    }

    public Long getIdTipoclaseseleccionada() {
        return idTipoclaseseleccionada;
    }

    public Long getIdTramite() {
        return idTramite;
    }

    public String getTipoClase() {
        return tipoClase;
    }

    public void setDescripcionlibre(String descripcionlibre) {
        this.descripcionlibre = descripcionlibre;
    }

    public void setIdClase(long idClase) {
        this.idClase = idClase;
    }

    public void setIdFormaseleccionclase(Short idFormaseleccionclase) {
        this.idFormaseleccionclase = idFormaseleccionclase;
    }

    public void setIdTipoclaseseleccionada(Long idTipoclaseseleccionada) {
        this.idTipoclaseseleccionada = idTipoclaseseleccionada;
    }

    public void setIdTramite(Long idTramite) {
        this.idTramite = idTramite;
    }

    public void setTipoClase(String tipoClase) {
        this.tipoClase = tipoClase;
    }
    
    
    
    
}
