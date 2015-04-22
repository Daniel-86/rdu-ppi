/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.gob.impi.rdu.exposition.flujosGenerales;

/**
 *
 * @author daniel
 */
public class Notification {
    private String title;
    private String person;
    private Long userId;
    public static enum UserType {
        COMMON, COMPLAINANT, RESPONDENT
    }
    private UserType userType = UserType.COMMON;
    
    public String getTitle() {return title;}
    public String getPerson() {return person;}
    public Long getUserId() {return userId;}
    public UserType getUserType() {return userType;}
    public void setTitle(String title) {this.title = title;}
    public void setPerson(String person) {this.person = person;}
    public void setUserId(Long id) {this.userId = id;}
    public void setUserType(UserType ut) {this.userType = ut;}
}
