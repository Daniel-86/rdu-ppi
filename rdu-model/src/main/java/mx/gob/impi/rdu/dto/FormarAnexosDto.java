/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.gob.impi.rdu.dto;

import java.io.Serializable;

/**
 *
 * @author winter
 */
@SuppressWarnings("serial")
public class FormarAnexosDto implements Serializable {
    
       
        private String nombreDocAnexo;
        private int tamanoAnexo;
        private Long numHojasAnexo;
        private String cadenaAnexos;
        private Integer orden;

   

    public String getNombreDocAnexo() {
        return nombreDocAnexo;
    }

    public void setNombreDocAnexo(String nombreDocAnexo) {
        this.nombreDocAnexo = nombreDocAnexo;
    }

    public int getTamanoAnexo() {
        return tamanoAnexo;
    }

    public void setTamanoAnexo(int tamanoAnexo) {
        this.tamanoAnexo = tamanoAnexo;
    }

    public Long getNumHojasAnexo() {
        return numHojasAnexo;
    }

    public void setNumHojasAnexo(Long numHojasAnexo) {
        this.numHojasAnexo = numHojasAnexo;
    }

    public String getCadenaAnexos() {
        return cadenaAnexos;
    }

    public void setCadenaAnexos(String cadenaAnexos) {
        this.cadenaAnexos = cadenaAnexos;
    }

    public Integer getOrden() {
        return orden;
    }

    public void setOrden(Integer orden) {
        this.orden = orden;
    }

    
}
