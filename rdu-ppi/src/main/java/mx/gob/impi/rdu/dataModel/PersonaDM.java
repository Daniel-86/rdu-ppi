/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.gob.impi.rdu.dataModel;

import java.util.List;
import javax.faces.model.ListDataModel;
import mx.gob.impi.rdu.persistence.model.Persona;
import org.primefaces.model.SelectableDataModel;

/**
 *
 * @author cesar
 */
public class PersonaDM extends ListDataModel<Persona> implements SelectableDataModel<Persona> {

    public PersonaDM(List<Persona> data) {
        super(data);
    }

    @Override
    public Persona getRowData(String rowKey) {
        //In a real app, a more efficient way like a query by rowKey should be implemented to deal with huge data

        List<Persona> personas = (List<Persona>) getWrappedData();

        for (Persona persona : personas) {
            if (persona.getIdSolicitante().toString().equals(rowKey)) {
                return persona;
            }
        }

        return null;
    }

    @Override
    public Object getRowKey(Persona persona) {
        return persona.getIdSolicitante();
    }
}
