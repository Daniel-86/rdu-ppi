/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.gob.impi.rdu.exposition.firma;

import java.util.List;
import javax.faces.model.ListDataModel;
import mx.gob.impi.rdu.persistence.model.Notificacion;
import org.primefaces.model.SelectableDataModel;

/**
 *
 * @author
 */
public class NotificacionDataModel  extends ListDataModel<Notificacion> implements SelectableDataModel<Notificacion>  {

    public NotificacionDataModel() {
    }

    public NotificacionDataModel(List<Notificacion> datos) {
        super(datos);
    }
    
    
    
    public Object getRowKey(Notificacion object) {
        return object.getIdNotificacion();
    }

    public Notificacion getRowData(String rowKey) {
         List<Notificacion> nots = (List<Notificacion>) getWrappedData();  
          
        for(Notificacion not : nots) {  
            if(not.getIdNotificacion().toString().equals(rowKey))  
                return not;  
        }  
          
        return null;  
    }
    
}
