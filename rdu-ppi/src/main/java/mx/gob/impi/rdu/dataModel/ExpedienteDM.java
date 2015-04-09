/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.gob.impi.rdu.dataModel;

import java.util.List;
import javax.faces.model.ListDataModel;
import mx.gob.impi.rdu.dto.ExpedientesDto;
import mx.gob.impi.rdu.persistence.model.Persona;
import org.primefaces.model.SelectableDataModel;

/**
 *
 * @author cesar
 */
public class ExpedienteDM extends ListDataModel<ExpedientesDto> implements SelectableDataModel<ExpedientesDto> {

    public ExpedienteDM(List<ExpedientesDto> data) {
        super(data);
    }

    @Override
    public ExpedientesDto getRowData(String rowKey) {
        //In a real app, a more efficient way like a query by rowKey should be implemented to deal with huge data

        List<ExpedientesDto> expedientesDto = (List<ExpedientesDto>) getWrappedData();

        for (ExpedientesDto expedienteDto : expedientesDto) {
            if (expedienteDto.getIdFirma().toString().equals(rowKey)) {
                return expedienteDto;
            }
        }

        return null;
    }

    @Override
    public Object getRowKey(ExpedientesDto expedienteDto) {
        return expedienteDto.getIdFirma();
    }
}
