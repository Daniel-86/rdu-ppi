/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.gob.impi.rdu.service;

import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author
 */
public interface MailService {
    
    public void sendMail(String from, String to, String subject, String body) ;
    
     public void sendAlertMail(String alert);
     
    public void sendMail(Map<String, String> properties) throws Exception;
    
}
