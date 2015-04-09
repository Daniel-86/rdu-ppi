/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.gob.impi.rdu.dto;

import java.io.FileInputStream;
import java.io.InputStream;

/**
 *
 * @author cesar
 */
public class ReporteDibujo {

    private InputStream imagen;
    private String numero;
    private String expedienteDivisional;

    public ReporteDibujo(InputStream imagen, String numero, String expedienteDivisional) {
        this.imagen = imagen;
        this.numero = numero;
        this.expedienteDivisional = expedienteDivisional;
    }

    public ReporteDibujo(InputStream imagen, String numero) {
        this.imagen = imagen;
        this.numero = numero;
    }

    public InputStream getImagen() {
        return imagen;
    }

    public void setImagen(InputStream imagen) {
        this.imagen = imagen;
    }

    public String getNumero() {
        return numero;
    }

    public void setNumero(String numero) {
        this.numero = numero;
    }

    public String getExpedienteDivisional() {
        return expedienteDivisional;
    }

    public void setExpedienteDivisional(String expedienteDivisional) {
        this.expedienteDivisional = expedienteDivisional;
    }
}
