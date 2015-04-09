/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package mx.gob.impi.rdu.exposition.indice;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author usradmin
 */
public class AreaVO {
    private int idArea;
    private String nombreArea="";
    List <SolicitudRduVO> solicitudes;
    List <GestionVO> tableros;

    public AreaVO() {
    }

    public int getIdArea() {
        return idArea;
    }

    public void setIdArea(int idArea) {
        this.idArea = idArea;
    }

    public String getNombreArea() {
        return nombreArea;
    }

    public void setNombreArea(String nombreArea) {
        this.nombreArea = nombreArea;
    }

    public List<SolicitudRduVO> getSolicitudes() {
        return solicitudes;
    }

    public void setSolicitudes(List<SolicitudRduVO> solicitudes) {
        this.solicitudes = solicitudes;
    }

    public List<GestionVO> getTableros() {
        return tableros;
    }

    public void setTableros(List<GestionVO> tableros) {
        this.tableros = tableros;
    }

}
