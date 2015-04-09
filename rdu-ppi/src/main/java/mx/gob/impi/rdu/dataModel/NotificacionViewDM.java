/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.gob.impi.rdu.dataModel;

import java.util.List;
import javax.faces.model.ListDataModel;
import mx.gob.impi.sigmar.persistence.model.NotificacionView;
import org.primefaces.model.SelectableDataModel;

/**
 *
 * @author oracle
 */
public class NotificacionViewDM extends ListDataModel<NotificacionView> implements SelectableDataModel<NotificacionView> {

    public NotificacionViewDM(List<NotificacionView> data) {
        super(data);
    }

    public NotificacionView getRowData(String rowKey) {
        //In a real app, a more efficient way like a query by rowKey should be implemented to deal with huge data

        List<NotificacionView> notificaciones = (List<NotificacionView>) getWrappedData();
        for (NotificacionView not : notificaciones) {
            if (not.getOficioSalida().equals(rowKey)) {
                return not;
            }
        }
        return null;
    }

    public Object getRowKey(NotificacionView notificacion) {
        return notificacion.getOficioSalida();
    }
}
