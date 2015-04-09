/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.gob.impi.rdu.util;

import java.util.Comparator;
import mx.gob.impi.rdu.persistence.model.Persona;

/**
 *
 * @author sdiaz
 */
public class ComparatorPersona implements Comparator<Persona>{
    
    
        public int compare(Persona p1, Persona p2) {
           String nombreConcatenado = "";
        nombreConcatenado += p1.getNombrecompleto().trim();
        if(p1.getPrimerApellido()!=null && !p1.getPrimerApellido().equals(""))
            nombreConcatenado += " "+p1.getPrimerApellido().trim();
        if(p1.getSegundoApellido()!=null && !p1.getSegundoApellido().equals(""))
            nombreConcatenado += " "+p1.getSegundoApellido().trim();
            
//            return  p1.getNombrecompletotmp().compareTo(p2.getNombrecompletotmp());
        return  nombreConcatenado.toLowerCase().compareTo(p2.getNombrecompletotmp().toLowerCase());
            
        }
     
   
    
}

