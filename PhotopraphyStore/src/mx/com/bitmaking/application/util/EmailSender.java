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
import org.springframework.core.env.Environment;

public class EmailSender {
	String  to = "sonoojaiswal1988@gmail.com";//change accordingly  
    String from = "sonoojaiswal1987@gmail.com";
    String host = "localhost";//or IP address 
    
    @Autowired
	private Environment env;
    
    Properties properties = null;  
    
    Session session = null;
    public EmailSender(){
    	properties = System.getProperties();
    	properties.setProperty("mail.smtp.host", env.getProperty("mail.host"));  
    	properties.put("mail.smtp.port", env.getProperty("mail.port"));
        properties.put("mail.smtp.ssl.enable", "true");
        properties.put("mail.smtp.auth", "true");
    	session = Session.getDefaultInstance(properties,new javax.mail.Authenticator() {
    		protected PasswordAuthentication getPasswordAuthentication() {
    			return new PasswordAuthentication(env.getProperty("mail.host"), 
    						env.getProperty("mail.password"));
            }
        });  
    }
    
    
    public void sendMessageHTML(String to,String user,String msgHtml,String subject) throws AddressException, MessagingException{
    	MimeMessage message = new MimeMessage(session);  
        message.setFrom(new InternetAddress(user));  
        message.addRecipient(Message.RecipientType.TO,new InternetAddress(to));  
        message.setSubject(subject);  
        message.setContent(msgHtml, "text/html");  
        
        BodyPart messageBodyPart = new MimeBodyPart();

        // Now set the actual message
        messageBodyPart.setContent(msgHtml, "text/html");  

        // Create a multipar message
        Multipart multipart = new MimeMultipart();

        // Set text message part
        multipart.addBodyPart(messageBodyPart);

        // Part two is attachment
        messageBodyPart = new MimeBodyPart();
        String filename = env.getProperty("exportFile.path")+"/corteCaja.xls";
        DataSource source = new FileDataSource(filename);
        messageBodyPart.setDataHandler(new DataHandler(source));
        messageBodyPart.setFileName(filename);
        multipart.addBodyPart(messageBodyPart);

        // Send the complete message parts
        message.setContent(multipart);
    }
}
