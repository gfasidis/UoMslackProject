
import java.awt.Desktop;
import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Properties;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.swing.JOptionPane;

public abstract class MailClass {
	
	

	public static void vertificationMail(String to, String vertificatioCode){
		  
		 final String from= "johntokis97@gmail.com";  
		 final String password= "john123456789";
		  
		  Properties props = new Properties();    
          props.put("mail.smtp.host", "smtp.gmail.com");    
          props.put("mail.smtp.socketFactory.port", "465");    
          props.put("mail.smtp.socketFactory.class",    
                    "javax.net.ssl.SSLSocketFactory");    
          props.put("mail.smtp.auth", "true");    
          props.put("mail.smtp.port", "465");
          
          Session session = Session.getDefaultInstance(props,    
           new javax.mail.Authenticator() {    
           protected PasswordAuthentication getPasswordAuthentication() {    
           return new PasswordAuthentication(from,password);  
           }    
          });  
   
          try {    
           MimeMessage message = new MimeMessage(session);    
           message.setFrom(new InternetAddress(from, "UoMslack"));
           message.addRecipient(Message.RecipientType.TO,new InternetAddress(to));    
           message.setSubject("Verification code");    
           message.setContent("Your verification code: " + "<b>" + vertificatioCode + "</b>", "text/html");    
           
           Transport.send(message);    
           JOptionPane.showMessageDialog(null, "Check your email for verification code");    
          } catch (MessagingException e) {throw new RuntimeException(e);} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}  
		
	}
	
	public static void subscribeMail(ArrayList<String> to, String courseTitle, Post aPost){
		  
		 final String from= "johntokis97@gmail.com";  
		 final String password= "john123456789";
		  
		 Properties props = new Properties();    
         props.put("mail.smtp.host", "smtp.gmail.com");    
         props.put("mail.smtp.socketFactory.port", "465");    
         props.put("mail.smtp.socketFactory.class",    
                   "javax.net.ssl.SSLSocketFactory");    
         props.put("mail.smtp.auth", "true");    
         props.put("mail.smtp.port", "465");
         
         Session session = Session.getDefaultInstance(props,    
          new javax.mail.Authenticator() {    
          protected PasswordAuthentication getPasswordAuthentication() {    
          return new PasswordAuthentication(from,password);  
          }    
         });  
  
         try {    
          MimeMessage message = new MimeMessage(session);    
          message.setFrom(new InternetAddress(from, "UoMslack"));
          InternetAddress[] mailAddress_TO = new InternetAddress [to.size()] ;
          for(int i=0;i<to.size();i++){
              mailAddress_TO[i] = new InternetAddress(to.get(i));
          }
          message.addRecipients(Message.RecipientType.BCC, mailAddress_TO);
          message.setSubject("New post in " + courseTitle + " course!");    
          message.setContent("<b>" + aPost.getPostUser() + "</b>" + ": " + "<i>" + aPost.getPostText() + "</i>", "text/html");    

          Transport.send(message);
         } catch (MessagingException e) {throw new RuntimeException(e);} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}  
		
	}
	
	public static void openWebpage(URI uri) {
	    Desktop desktop = Desktop.isDesktopSupported() ? Desktop.getDesktop() : null;
	    if (desktop != null && desktop.isSupported(Desktop.Action.BROWSE)) {
	        try {
	            desktop.browse(uri);
	        } catch (Exception e) {
	            e.printStackTrace();
	        }
	    }
	}

	public static void openWebpage(URL url) {
	    try {
	        openWebpage(url.toURI());
	    } catch (URISyntaxException e) {
	        e.printStackTrace();
	    }
	}
	
	

}
