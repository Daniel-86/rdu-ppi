/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package mx.gob.impi.rdu.exposition.indice;

/**
 *
 * @author usradmin
 */
public class GestionVO {
    private String nombreTablero="";
    private String url="";

    public GestionVO(String pNombreTablero, String pUrl) {
        super();
        this.nombreTablero=pNombreTablero;
        this.url=pUrl;

    }

    public GestionVO() {
    }

    public String getNombreTablero() {
        return nombreTablero;
    }

    public void setNombreTablero(String nombreTablero) {
        this.nombreTablero = nombreTablero;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

}
