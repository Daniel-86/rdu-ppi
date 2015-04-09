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
public class EstablecimientoXTramiteDto implements  Serializable{
     private Long idDomicilio;

    private Long idTramite;
    
    private DomicilioDto  domicilioDto = new DomicilioDto();

    public DomicilioDto getDomicilioDto() {
        return domicilioDto;
    }

    public Long getIdDomicilio() {
        return idDomicilio;
    }

    public Long getIdTramite() {
        return idTramite;
    }

    public void setDomicilioDto(DomicilioDto domicilioDto) {
        this.domicilioDto = domicilioDto;
    }

    public void setIdDomicilio(Long idDomicilio) {
        this.idDomicilio = idDomicilio;
    }

    public void setIdTramite(Long idTramite) {
        this.idTramite = idTramite;
    }
    
    

}
