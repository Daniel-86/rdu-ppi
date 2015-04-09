/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.gob.impi.rdu.util;

import java.util.Comparator;
import mx.gob.impi.rdu.persistence.model.Prioridad;

/**
 *
 * @author saulchinaski
 */
public class ComparatorPrioridad  implements Comparator<Prioridad>{
      
    public int compare(Prioridad p1, Prioridad p2) {

                                    int pais = p1.getIdPais().compareTo(p2.getIdPais());
                                    String fechaPresentacion = Util.formatearFecha(p2.getFechaPresentacionExt(), "dd/MM/yyyy");
                                    
                                    pais = p1.getFechaPresentacion().compareTo(fechaPresentacion);
                                    
                                    if(pais == 0)
                                        return ((pais == 0) ? p1.getNumeroExpedienteTmp().compareTo(p2.getNumeroExpedienteTmp()) : pais);
                                    else
                                        return pais;

     }
}
