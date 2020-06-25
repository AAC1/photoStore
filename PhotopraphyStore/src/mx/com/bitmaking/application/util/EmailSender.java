package mx.com.bitmaking.application.util;

import java.util.Date;
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
import javax.mail.Transport;
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
    	System.out.println("user:"+mailUser);
    	System.out.println("passwd:"+mailPasswd);
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
        
  //      properties.put("mail.transport.protocol", "smtp");
    //	properties.put("mail.smtp.starttls.enable", "true");
    	properties.put("mail.smtp.user", mailUser);
    	//properties.put("mail.smtp.starttls.required", "true");
    	properties.put("mail.debug", "true");
    //	properties.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
    //	properties.put("mail.host", mailHost);
        //properties.put("mail.imap.host", mailHost);
      //  properties.put("mail.from", mailUser);
       // properties.put("mail.store.protocol", "imap");
       // properties.put("mail.transport.protocol", "smtp");
    //	properties.put("mail.smtp.ssl.trust", "true");
    	session = Session.getDefaultInstance(properties,new javax.mail.Authenticator() {
    		protected PasswordAuthentication getPasswordAuthentication() {
    			return new PasswordAuthentication(mailUser, mailPasswd);
            }
        }); 
    	MimeMessage message = new MimeMessage(session);  
        message.setFrom(new InternetAddress(mailUser));  
        message.setSentDate(new Date());
        String [] adresses = to.split(",");

        for(int i=0;i<adresses.length;i++){
        	System.out.println("emailTosend: "+adresses[i]);
        	message.addRecipient(Message.RecipientType.TO,new InternetAddress(adresses[i]));  
        }
      //  message.setRecipients(Message.RecipientType.TO,InternetAddress.parse(mailUser));  
        message.setSubject(subject);  
        message.setContent(msgHtml, "text/html");  
        
        BodyPart messageBodyPart = new MimeBodyPart();

        // Now set the actual message
        messageBodyPart.setContent(msgHtml, "text/html");  

        // Create a multipar message
        Multipart multipart = new MimeMultipart();

        // Set text message part
        multipart.addBodyPart(messageBodyPart);
        
        if(filename!=null && !"".equals(filename.trim())){
        	System.out.println("Entra a filename: "+filename);
            // Part two is attachment
            messageBodyPart = new MimeBodyPart();
        //	messageBodyPart.setContent(msgHtml, "text/html");
	        DataSource source = new FileDataSource(filename);
	        messageBodyPart.setDataHandler(new DataHandler(source));
	        messageBodyPart.setFileName(filename);
	        multipart.addBodyPart(messageBodyPart);
        }else {

        	System.out.println("NO Entra a filename: "+filename);
        //	messageBodyPart.setContent(msgHtml, "text/html");
        //	multipart.addBodyPart(messageBodyPart);
        }
        

        // Send the complete message parts
        message.setContent(multipart);
        /*
        Transport t = session.getTransport("smtp");
        t.connect(mailUser, mailPasswd);
        t.send(message, message.getAllRecipients());
        t.close();
        */
        Transport.send(message);
    }
}
