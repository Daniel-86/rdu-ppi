/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.gob.impi.rdu.dto;

import java.io.Serializable;

/**
 *
 * @author saulchinaski
 */
public class DatoscontactoDto  implements Serializable{
    private Long idDatoscontacto;

    private String telefono;

    private String correoelectronico;

    private Short idTipotelefono;

    private String lada;

    public String getCorreoelectronico() {
        return correoelectronico;
    }

    public Long getIdDatoscontacto() {
        return idDatoscontacto;
    }

    public Short getIdTipotelefono() {
        return idTipotelefono;
    }

    public String getLada() {
        return lada;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setCorreoelectronico(String correoelectronico) {
        this.correoelectronico = correoelectronico;
    }

    public void setIdDatoscontacto(Long idDatoscontacto) {
        this.idDatoscontacto = idDatoscontacto;
    }

    public void setIdTipotelefono(Short idTipotelefono) {
        this.idTipotelefono = idTipotelefono;
    }

    public void setLada(String lada) {
        this.lada = lada;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }
    
    
}
