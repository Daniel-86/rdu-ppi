/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.gob.impi.rdu.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.mail.MailSender;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

/**
 *
 * @author
 */
@Service("mailService")
public class MailServiceImpl implements MailService {

    @Autowired
    private MailSender mailSender;
    @Autowired
    private SimpleMailMessage alertMailMessage;

    public void sendMail(String from, String to, String subject, String body) {

        SimpleMailMessage message = new SimpleMailMessage();

        message.setFrom(from);
        message.setTo(to);
        message.setSubject(subject);
        message.setText(body);
        mailSender.send(message);

    }

    public void sendAlertMail(String alert) {

        SimpleMailMessage mailMessage = new SimpleMailMessage(alertMailMessage);
        mailMessage.setText(alert);
        mailSender.send(mailMessage);

    }

    @Autowired
    private JavaMailSender myMailSender;

    @Override
    public void sendMail(Map properties) throws Exception {

        if (!properties.containsKey("to")) {
            throw new Exception("mail destination is missing");
        }

        MimeMessage message = myMailSender.createMimeMessage();
        try {
            MimeMessageHelper helper = new MimeMessageHelper(message, true);

            helper.setFrom(properties.containsKey("from") ? (String) properties.get("from") : "default@idontcare.com");
            if(properties.get("to") instanceof List)
                for(String dest: (List<String>) properties.get("to")) {
                    helper.addTo(dest);
                }
            else if(properties.get("to") instanceof Object[])
                for (String dest : (String []) properties.get("to")) {
                    helper.addTo(dest);
                }
            else 
                helper.setTo((String) properties.get("to"));
            helper.setSubject(properties.containsKey("subject") ? (String) properties.get("subject") : "useless");
            helper.setText(properties.containsKey("text") ? (String) properties.get("text") : "just ignore this shit");
            if (properties.containsKey("file")) {
                List filePaths = new ArrayList<>();
                filePaths.add(properties.get("file"));
                properties.put("files", filePaths);
            }
            if (properties.containsKey("files")) {
                FileSystemResource file;
                if(properties.get("files") instanceof List)
                    for (String filePath : (List<String>) properties.get("files")) {
                        file = new FileSystemResource(filePath);
                        helper.addAttachment(file.getFilename(), file);
                    }
                else if(properties.get("files") instanceof Object[])
                    for (String filePath : (String[]) properties.get("files")) {
                        file = new FileSystemResource(filePath);
                        helper.addAttachment(file.getFilename(), file);
                    }
                else
                    throw new Exception("'files' parameter must be a List or an array of Strings");
            }
        } catch (MessagingException ex) {
            Logger.getLogger(MailServiceImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
        myMailSender.send(message);
    }

}
