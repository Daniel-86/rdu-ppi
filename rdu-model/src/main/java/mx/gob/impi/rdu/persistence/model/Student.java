/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.gob.impi.rdu.persistence.model;

import java.io.Serializable;

/**
 *
 * @author oracle
 */
public class Student implements Serializable {

    private static final long serialVersionUID = 1L;
    private int Id;

    public int getId() {
        return Id;
    }

    public void setId(int id) {
        Id = id;
    }
    private String lastName;
    private String firstName;

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public Student() {
        super();
    }

    public Student(String lastName, String firstName, int Id) {
        super();
        this.lastName = lastName;
        this.firstName = firstName;
        this.Id = Id;


    }
}