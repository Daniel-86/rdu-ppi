/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package mx.gob.impi.rdu.dto;

import java.io.Serializable;
import mx.gob.impi.rdu.persistence.model.Domicilio;
import mx.gob.impi.rdu.persistence.model.EntidadFederativa;

/**
 *
 * @author CESAR CASTAÑEDA REYES <WWW.INFOTEC.COM>
 */
public class DomicilioDto implements Serializable{

    private String nombrePais;
    private String nombreEntidad;

     private Long idDomicilio;

    private Long idPais;
    
    private String idEntidad;

    private String calle;

    private String numero;

    private String colonia;

    private String poblacion;

    private String codigopostal;

    private PaisDto paisDto = new PaisDto();
    private EntidadFederativa entidad = new EntidadFederativa();
    
    private String numExt;
    private String numInt;

    public String getCalle() {
        return calle;
    }

    public String getCodigopostal() {
        return codigopostal;
    }

    public String getColonia() {
        return colonia;
    }

    public EntidadFederativa getEntidad() {
        return entidad;
    }

    public Long getIdDomicilio() {
        return idDomicilio;
    }

    public String getIdEntidad() {
        return idEntidad;
    }

    public Long getIdPais() {
        return idPais;
    }

    public String getNumero() {
        return numero;
    }


    public String getPoblacion() {
        return poblacion;
    }

    public void setCalle(String calle) {
        this.calle = calle;
    }

    public void setCodigopostal(String codigopostal) {
        this.codigopostal = codigopostal;
    }

    public void setColonia(String colonia) {
        this.colonia = colonia;
    }

    public void setEntidad(EntidadFederativa entidad) {
        this.entidad = entidad;
    }

    public void setIdDomicilio(Long idDomicilio) {
        this.idDomicilio = idDomicilio;
    }

    public void setIdEntidad(String idEntidad) {
        this.idEntidad = idEntidad;
    }

    public void setIdPais(Long idPais) {
        this.idPais = idPais;
    }

    public void setNumero(String numero) {
        this.numero = numero;
    }

    public PaisDto getPaisDto() {
        return paisDto;
    }

    public void setPaisDto(PaisDto paisDto) {
        this.paisDto = paisDto;
    }



    public void setPoblacion(String poblacion) {
        this.poblacion = poblacion;
    }
    
        
    public String getNombreEntidad() {
        return nombreEntidad;
    }

    public void setNombreEntidad(String nombreEntidad) {
        this.nombreEntidad = nombreEntidad;
    }

    public String getNombrePais() {
        return nombrePais;
    }

    public void setNombrePais(String nombrePais) {
        this.nombrePais = nombrePais;
    }

    public String getNumExt() {
            return numExt;
    }

    public void setNumExt(String numExt) {
        this.numExt = numExt;
    }

    public String getNumInt() {
        return numInt;
    }

    public void setNumInt(String numInt) {
        this.numInt = numInt;
    }

}
