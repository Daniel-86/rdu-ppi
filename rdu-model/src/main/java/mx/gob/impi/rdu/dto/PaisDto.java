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
public class PaisDto  implements Serializable{
       
     protected Long idPais;

     private String nacionalidad;
     
     private String nombre;

     private String clavePais;

    public String getClavePais() {
        return clavePais;
    }

    public void setClavePais(String clavePais) {
        this.clavePais = clavePais;
    }     
     
    public Long getIdPais() {
        return idPais;
    }

    public String getNacionalidad() {
        return nacionalidad;
    }

    public void setIdPais(Long idPais) {
        this.idPais = idPais;
    }

    public void setNacionalidad(String nacionalidad) {
        this.nacionalidad = nacionalidad;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
    
    
     
     
}
