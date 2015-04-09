/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package mx.gob.impi.rdu.dto;

import java.io.Serializable;

/**
 *
 * @author iegarcia infotec
 */
public class ArchivoDigitalDto implements Serializable {
    private byte[] archivo;

    public byte[] getArchivo() {
        return archivo;
    }

    public void setArchivo(byte[] archivo) {
        this.archivo = archivo;
    }
    
}
