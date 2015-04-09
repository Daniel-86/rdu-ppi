package mx.gob.impi.rdu.dto;

import mx.gob.impi.rdu.persistence.model.*;
import java.io.Serializable;

public class DomicilionotificacionDto implements  Serializable {
    private Long idDomicilionotificacion;

    private Long idDomicilio;

    private DomicilioDto domicilioDto = new DomicilioDto();
    
    private Long idDatoscontacto;
    
     private DatoscontactoDto datosContactoDto = new DatoscontactoDto();

    public void setDatosContactoDto(DatoscontactoDto datosContactoDto) {
        this.datosContactoDto = datosContactoDto;
    }
     
    public DatoscontactoDto getDatosContactoDto() {
        return datosContactoDto;
    }

    public Long getIdDatoscontacto() {
        return idDatoscontacto;
    }

    public DomicilioDto getDomicilioDto() {
        return domicilioDto;
    }

    public void setDomicilioDto(DomicilioDto domicilioDto) {
        this.domicilioDto = domicilioDto;
    }
       
    public void setIdDatoscontacto(Long idDatoscontacto) {
        this.idDatoscontacto = idDatoscontacto;
    }

    public Long getIdDomicilio() {
        return idDomicilio;
    }

    public void setIdDomicilio(Long idDomicilio) {
        this.idDomicilio = idDomicilio;
    }

    public Long getIdDomicilionotificacion() {
        return idDomicilionotificacion;
    }

    public void setIdDomicilionotificacion(Long idDomicilionotificacion) {
        this.idDomicilionotificacion = idDomicilionotificacion;
    }

}