package mx.gob.impi.rdu.persistence.model;

import java.io.Serializable;

public class Domicilionotificacion implements  Serializable {
    private static final long serialVersionUID = 1L;
    private Long idDomicilionotificacion;

    private Long idDomicilio;
    
    private Domicilio domicilio;

    private Long idDatoscontacto;

    private Datoscontacto datosContacto;

    public Domicilionotificacion() {
    }

    public Domicilionotificacion(Domicilio domicilio, Datoscontacto datosContacto) {
        this.domicilio = domicilio;
        this.datosContacto = datosContacto;
    }
    
    

    public Datoscontacto getDatosContacto() {
        return datosContacto;
    }

    public void setDatosContacto(Datoscontacto datosContacto) {
        this.datosContacto = datosContacto;
    }
    
    
    
    public Domicilio getDomicilio() {
        return domicilio;
    }

    public void setDomicilio(Domicilio domicilio) {
        this.domicilio = domicilio;
    }
       
    public Long getIdDatoscontacto() {
        return idDatoscontacto;
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