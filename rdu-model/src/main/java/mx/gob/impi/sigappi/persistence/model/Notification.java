/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.gob.impi.sigappi.persistence.model;

import java.util.Date;
import java.util.Objects;
import mx.gob.impi.sigappi.persistence.model.SolicitudInteresados;

/**
 *
 * @author daniel
 */
public class Notification {
    private String title;
    private String pc;
    private Integer userId;
    public static enum UserType {
        COMMON, COMPLAINANT, RESPONDENT
    }
    private UserType userType = UserType.COMMON;
    private Integer usertype;
    private Date lastUpdated;
    private Integer sequence = -999;
    private String authorizedBy = null;
    private String userTypeDescription;
    private boolean persisted = false;

    public boolean isPersisted() {
        return persisted;
    }
    public void setPersisted(boolean isPersisted) {
        this.persisted = isPersisted;
    }
    public String getUserTypeDescription() {
        return userTypeDescription;
    }
    public void setUserTypeDescription(String userTypeDescription) {
        this.userTypeDescription = userTypeDescription;
    }
    public String getTitle() {return title;}
    public String getPc() {return pc;}
    public Integer getUserId() {return userId;}
    public UserType getUserType() {return userType;}
    public Integer getUsertype() {return usertype;}
    public Date getLastUpdated() {return lastUpdated;}
    public Integer getSequence() {return sequence;}
    public String getAuthorizedBy() {return authorizedBy;}
    public void setTitle(String title) {this.title = title;}
    public void setPc(String pc) {this.pc = pc;}
    public void setUserId(Integer id) {this.userId = id;}
    public void setUserType(UserType ut) {this.userType = ut;}
    public void setUsertype(Integer usertype) {this.usertype = usertype;}
    public void setLastUpdated(Date date) {lastUpdated = date;}
    public void setSequence(Integer s) {sequence = s;}
    public void setAuthorizedBy(String person) {authorizedBy = person;}
    
    public Notification(Notification n) {
        title = n.title;
        userId = n.userId;
        pc = n.pc;
    }
    
    public Notification() {
        
    }
    
    public Notification(SolicitudInteresados s) {
        title = s.getTitle();
        userId = s.getCodInteresado();
        usertype = s.getCodRelacion();
        sequence = s.getSecuencia();
        authorizedBy = s.getCveUsuario();
    }
    
    public Notification(SolicitudRevision s) {
        title = s.getTitle();
        userId = s.getCodInteresado();
        usertype = s.getCodRelacion();
        sequence = s.getSecuencia();
        authorizedBy = s.getCveUsuario();
    }
    
    @Override
    public boolean equals(Object o) {
        if(o instanceof Notification) {
            Notification n = (Notification) o;
            return (this.title.equals(n.getTitle())
                    && this.userId.equals(n.getUserId())
                    && this.usertype.equals(n.getUsertype()));
        }
        if(o instanceof SolicitudInteresados) {
            SolicitudInteresados s = (SolicitudInteresados) o;
            return (this.title.equals(s.getTitle())
                    && this.userId.equals(s.getCodInteresado())
                    && this.usertype.equals(s.getCodRelacion()));
        }
//        if (o instanceof SolicitudRevision) {
//            SolicitudRevision s = (SolicitudRevision) o;
//            return (this.title.equals(s.getTitle())
//                    && this.userId.equals(s.getCodInteresado())
//                    && this.usertype.equals(s.getCodRelacion()));
//        }
        return false;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 37 * hash + Objects.hashCode(this.title);
        hash = 37 * hash + Objects.hashCode(this.userId);
        hash = 37 * hash + Objects.hashCode(this.usertype);
        return hash;
    }
}
