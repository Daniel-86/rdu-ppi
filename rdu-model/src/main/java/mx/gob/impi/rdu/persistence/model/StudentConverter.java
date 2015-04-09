/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.gob.impi.rdu.persistence.model;

import java.util.ArrayList;
import java.util.List;
import org.apache.commons.beanutils.Converter;



/**
 *
 * @author oracle
 */


public class StudentConverter implements Converter{
	 public static List<Student> studentDB;
         
    static {
        studentDB = new ArrayList<Student>();
        studentDB.add(new Student("William", "Wong", 1));
        studentDB.add(new Student("John", "Smith", 2));
        studentDB.add(new Student("Mari", "Beckley", 3));
        studentDB.add(new Student("Messi", "Leonardo", 4));
    }

    public Object convert(Class type, Object value) {
        throw new UnsupportedOperationException("Not supported yet.");
    }
    
}
