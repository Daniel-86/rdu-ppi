package mx.gob.impi.rdu.persistence.model;

import java.io.Serializable;

public class Datoscontacto implements Serializable{
    private long idDatoscontacto;

    private String telefono;

    private String correoelectronico;

    private Short idTipotelefono;

    private String lada;
    private String fax;
    private String telefonoExt;

    public Datoscontacto(String telefono, String correoelectronico) {
        this.telefono = telefono;
        this.correoelectronico = correoelectronico;
    }

    public Datoscontacto() {
    }
    
    

    public String getFax() {
        return fax;
    }

    public void setFax(String fax) {
        this.fax = fax;
    }
    

    public long getIdDatoscontacto() {
        return idDatoscontacto;
    }

    public void setIdDatoscontacto(long idDatoscontacto) {
        this.idDatoscontacto = idDatoscontacto;
    }

    
    

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono == null ? null : telefono.trim();
    }

    public String getCorreoelectronico() {
        return correoelectronico;
    }

    public void setCorreoelectronico(String correoelectronico) {
        this.correoelectronico = correoelectronico == null ? null : correoelectronico.trim();
    }

    public Short getIdTipotelefono() {
        return idTipotelefono;
    }

    public void setIdTipotelefono(Short idTipotelefono) {
        this.idTipotelefono = idTipotelefono;
    }

    public String getLada() {
        return lada;
    }

    public void setLada(String lada) {
        this.lada = lada == null ? null : lada.trim();
    }

    public String getTelefonoExt() {
        return telefonoExt;
    }

    public void setTelefonoExt(String telefonoExt) {
        this.telefonoExt = telefonoExt;
    }
    
}