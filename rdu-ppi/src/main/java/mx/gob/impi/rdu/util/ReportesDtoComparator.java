package mx.gob.impi.rdu.util;

import java.util.Comparator;
import mx.gob.impi.rdu.dto.ReportesDto;

/**
 *
 * @author oracle
 */
public class ReportesDtoComparator implements Comparator<ReportesDto> {

    public int compare(ReportesDto o1, ReportesDto o2) {
        
        return new Integer(o1.getOrden()).compareTo(new Integer(o2.getOrden()));
    }
    
}