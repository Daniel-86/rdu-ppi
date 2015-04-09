/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package mx.gob.impi.rdu.exposition.indice;

/**
 *
 * @author usradmin
 */
public class SolicitudRduVO {
    private String nombreSolicitud="";
    private String url="";

    public SolicitudRduVO(String pNombreSolicitud, String pUrl) {
        super();        
        this.nombreSolicitud=pNombreSolicitud;
        this.url=pUrl;

    }

    public SolicitudRduVO() {
    }

    public String getNombreSolicitud() {
        return nombreSolicitud;
    }

    public void setNombreSolicitud(String nombreSolicitud) {
        this.nombreSolicitud = nombreSolicitud;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    

}
