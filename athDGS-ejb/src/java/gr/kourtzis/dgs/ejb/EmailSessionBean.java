/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gr.kourtzis.dgs.ejb;

import gr.kourtzis.dgs.entity.Account;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import javax.naming.InitialContext;
import javax.naming.NamingException;

/**
 *
 * @author Athanasios Kourtzis
 */
@Stateless
@LocalBean
public class EmailSessionBean {
    private static final String VERIFICATION_MAIL_BODY = "Verify your email address to complete the creation of your DGS account.";
    private static final String VERIFICATION_MAIL_SUBJECT = "New athDGS Account Verification Email";
    
    /**
     * The method is responsible for sending an email with a verification link in
     * order for a user to activate the account he created during the registration.
     * Various object, which are part of am email message, are initialized and 
     * given values. Finally the email message is send to the recipient.
     * @param account An Account object
     */
    public void send(final Account account) {
        try {
            InitialContext context = new InitialContext();
            Session session = (Session) context.lookup("mail/verification");
            session.setDebug(true);

            MimeMessage message = new MimeMessage(session);

            InternetAddress address = new InternetAddress(account.getUser().getEmail());

            message.setRecipient(Message.RecipientType.TO, address);
            message.setSubject(VERIFICATION_MAIL_SUBJECT);
            message.setSentDate(new Date());
            
            Multipart multipart = new MimeMultipart("alternative");
            String body = prepareBodyMessage(account);
            
            MimeBodyPart textPart = new MimeBodyPart();
            textPart.setText(body);
            
            MimeBodyPart htmlPart = new MimeBodyPart();
            String htmlContent = "<html><body>" + body + "<br /> </body></html>";
            htmlPart.setContent(htmlContent, "text/html");
            
            multipart.addBodyPart(textPart);
            multipart.addBodyPart(htmlPart);
            message.setContent(multipart);
            
            Transport.send(message);

        } catch (NamingException | MessagingException ex) {
            Logger.getLogger(EmailSessionBean.class.getName()).log(Level.SEVERE, null, ex);
            throw new RuntimeException(ex);
        }
    }
    
    private String prepareBodyMessage(final Account account) {
        StringBuilder builder = new StringBuilder();
        
        builder.append(VERIFICATION_MAIL_BODY);
        builder.append("\nThe link for the verification is: http://localhost:8080/athDGS-Web/faces");
        builder.append("/VerifyRegistration.xhtml?token=");
        builder.append(account.getActivation().getActivationToken());
        
        return builder.toString();
    }
}
