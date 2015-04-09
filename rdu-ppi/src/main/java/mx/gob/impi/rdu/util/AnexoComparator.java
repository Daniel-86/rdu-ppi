/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.gob.impi.rdu.util;

import java.util.Comparator;
import mx.gob.impi.rdu.dto.AnexosViewDto;

/**
 *
 * @author oracle
 */
public class AnexoComparator implements Comparator<AnexosViewDto> {

    public int compare(AnexosViewDto o1, AnexosViewDto o2) {
        return o1.getOrden().compareTo(o2.getOrden());
    }
}
