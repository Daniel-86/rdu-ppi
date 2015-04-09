/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.gob.impi.rdu.util;

import java.util.Comparator;
import mx.gob.impi.sigmar.persistence.model.NotificacionView;

/**
 *
 * @author oracle
 */
public class ComparatorNotificacion implements Comparator<NotificacionView> {

    public int compare(NotificacionView n1, NotificacionView n2) {
        return n1.getOficioSalida().compareTo(n2.getOficioSalida());
    }
}
