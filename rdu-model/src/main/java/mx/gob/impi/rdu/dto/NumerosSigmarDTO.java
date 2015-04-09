/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package mx.gob.impi.rdu.dto;

import java.io.Serializable;
import java.util.Date;


/**
 *
 * @author Wolf_ito
 */
public class NumerosSigmarDTO implements  Serializable {

    private int tipo_solicitud;
    private String ano_solicitud;
    private long expediente;
    private long registro;
    private String titular;
    private String denominacion;
    private String apoderado;
    private String productos;
    private String key;
    private Date fechaVigencia;

    public String getAno_solicitud() {
        return ano_solicitud;
    }

    public String getApoderado() {
        return apoderado;
    }

    public String getDenominacion() {
        return denominacion;
    }

    public long getExpediente() {
        return expediente;
    }

    public long getRegistro() {
        return registro;
    }

    public int getTipo_solicitud() {
        return tipo_solicitud;
    }

    public String getTitular() {
        return titular;
    }

    public void setAno_solicitud(String ano_solicitud) {
        this.ano_solicitud = ano_solicitud;
    }

    public void setApoderado(String apoderado) {
        this.apoderado = apoderado;
    }

    public void setDenominacion(String denominacion) {
        this.denominacion = denominacion;
    }

    public void setExpediente(long expediente) {
        this.expediente = expediente;
    }

    public void setRegistro(long registro) {
        this.registro = registro;
    }

    public void setTipo_solicitud(int tipo_solicitud) {
        this.tipo_solicitud = tipo_solicitud;
    }

    public void setTitular(String titular) {
        this.titular = titular;
    }

    public Date getFechaVigencia() {
        return fechaVigencia;
    }

    public void setFechaVigencia(Date fechaVigencia) {
        this.fechaVigencia = fechaVigencia;
    }


    /**
     * @return the productos
     */
    public String getProductos() {
        return productos;
    }

    /**
     * @param productos the productos to set
     */
    public void setProductos(String productos) {
        this.productos = productos;
    }

    @Override
    public boolean equals(Object obj) {
        
        if (obj == null) return false;
        if (!(obj instanceof NumerosSigmarDTO)) return false;
        
        
        NumerosSigmarDTO objNumero = (NumerosSigmarDTO)obj;
        
        if (objNumero.tipo_solicitud == 0 ||
                objNumero.ano_solicitud.isEmpty() ||
                objNumero.expediente == 0) return false;
        
        if (objNumero.tipo_solicitud == tipo_solicitud && 
                objNumero.ano_solicitud.equals(ano_solicitud) &&
                objNumero.expediente == expediente) return true;
        
        return false;
    }

    /**
     * @return the key
     */
    public String getKey() {
        return tipo_solicitud + ano_solicitud + expediente;
    }

    




}
