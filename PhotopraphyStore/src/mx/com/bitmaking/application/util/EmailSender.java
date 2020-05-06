package mx.com.bitmaking.application.util;

import java.util.Properties;

import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.mail.BodyPart;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;


public class EmailSender {

	private String mailHost;
	
	private String mailPort;
	
	private String mailUser;
	
	private String mailPasswd;
    
    //@Autowired
	//private Environment env;
    
    Properties properties = null;  
    
    Session session = null;
    public EmailSender(String mailHost,String mailPort,String mailUser,String mailPasswd){
    	this.mailHost = mailHost;
    	this.mailPort = mailPort;
    	this.mailUser = mailUser;
    	this.mailPasswd = mailPasswd;
    }
    
    
    public void sendMessageHTML(String to,String msgHtml,String subject, String filename) throws AddressException, MessagingException{
    	properties = System.getProperties();
    	/*
    	properties.setProperty("mail.smtp.host", env.getProperty("mail.host"));  
    	properties.put("mail.smtp.port", env.getProperty("mail.port"));
        properties.put("mail.smtp.ssl.enable", "true");
        properties.put("mail.smtp.auth", "true");
    	session = Session.getDefaultInstance(properties,new javax.mail.Authenticator() {
    		protected PasswordAuthentication getPasswordAuthentication() {
    			return new PasswordAuthentication(env.getProperty("mail.user"), 
    						env.getProperty("mail.password"));
            }
        });  
    	*/
    	properties.setProperty("mail.smtp.host", mailHost);  
    	properties.put("mail.smtp.port", mailPort);
        properties.put("mail.smtp.ssl.enable", "true");
        properties.put("mail.smtp.auth", "true");
    	session = Session.getDefaultInstance(properties,new javax.mail.Authenticator() {
    		protected PasswordAuthentication getPasswordAuthentication() {
    			return new PasswordAuthentication(mailUser, 
    						mailPasswd);
            }
        }); 
    	MimeMessage message = new MimeMessage(session);  
        message.setFrom(new InternetAddress(mailUser));  
        message.addRecipient(Message.RecipientType.TO,new InternetAddress(to));  
        message.setSubject(subject);  
        message.setContent(msgHtml, "text/html");  
        System.out.println("Entra a sendMessageHTML");
        BodyPart messageBodyPart = new MimeBodyPart();

        // Now set the actual message
        messageBodyPart.setContent(msgHtml, "text/html");  

        // Create a multipar message
        Multipart multipart = new MimeMultipart();

        // Set text message part
        multipart.addBodyPart(messageBodyPart);

        // Part two is attachment
        messageBodyPart = new MimeBodyPart();
        
        if(filename!=null && !"".equals(filename.trim())){
	        DataSource source = new FileDataSource(filename);
	        messageBodyPart.setDataHandler(new DataHandler(source));
	        messageBodyPart.setFileName(filename);
        }
        multipart.addBodyPart(messageBodyPart);

        // Send the complete message parts
        message.setContent(multipart);
    }
}
