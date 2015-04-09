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
public class ReportesDto implements Serializable {

    private byte[] reporteBytes;
    private String nombreBookMark;
    private String nombreReporte;
    private Integer orden;

    public byte[] getReporteBytes() {
        return reporteBytes;
    }

    public void setReporteBytes(byte[] reporteBytes) {
        this.reporteBytes = reporteBytes;
    }

    public String getNombreReporte() {
        return nombreReporte;
    }

    public void setNombreReporte(String nombreReporte) {
        this.nombreReporte = nombreReporte;
    }  

    public String getNombreBookMark() {
        return nombreBookMark;
    }

    public void setNombreBookMark(String nombreBookMark) {
        this.nombreBookMark = nombreBookMark;
    }

    public Integer getOrden() {
        return orden;
    }

    public void setOrden(Integer orden) {
        this.orden = orden;
    }   
}
