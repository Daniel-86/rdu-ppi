/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package mx.gob.impi.rdu.dto;
import java.io.Serializable;
import mx.gob.impi.rdu.persistence.model.Tramite;

/**
 *
 * @author usradmin
 */
public class RespuestaDto  implements Serializable{
    int respuesta;
    String msgRespuesta;
    Tramite tramite;

    public int getRespuesta() {
        return respuesta;
    }

    public void setRespuesta(int respuesta) {
        this.respuesta = respuesta;
    }

    public String getMsgRespuesta() {
        return msgRespuesta;
    }

    public void setMsgRespuesta(String msgRespuesta) {
        this.msgRespuesta = msgRespuesta;
    }

    public Tramite getTramite() {
        return tramite;
    }

    public void setTramite(Tramite tramite) {
        this.tramite = tramite;
    }

}
