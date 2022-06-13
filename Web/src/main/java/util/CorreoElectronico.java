/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package util;

import java.util.Properties;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

public class CorreoElectronico {
    public static void enviarCorreo(String destinatario, String asunto, String cuerpo) throws Exception{
        Properties props = System.getProperties();
        props.put("mail.smtp.host", "smtp.office365.com");  
        props.put("mail.smtp.user", "proyectodiseno2@outlook.com");    
        props.put("mail.smtp.auth", "true");   
        props.put("mail.smtp.starttls.enable", "true"); 
        props.put("mail.smtp.port", "587"); 

        Session session = Session.getDefaultInstance(props);
        MimeMessage message = new MimeMessage(session);

        try {
            message.setFrom(new InternetAddress("proyectodiseno2@outlook.com"));
            message.addRecipient(Message.RecipientType.TO, new InternetAddress(destinatario));   
            message.setSubject(asunto);
            message.setText(cuerpo);
            Transport transport = session.getTransport("smtp");
            transport.connect("proyectodiseno2@outlook.com", "proyecto2!");
            transport.sendMessage(message, message.getAllRecipients());
            transport.close();
        }
        catch (MessagingException me) {
            me.printStackTrace();  
        }
    }   
}
