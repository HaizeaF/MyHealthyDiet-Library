package emailService;

import java.security.SecureRandom;
import java.util.Properties;
import java.util.ResourceBundle;
import java.util.logging.Logger;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

/**
 * 
 * @author Julen B
 * A Email service that sends an email to a user.
 */

public class MyHealthyDietEmailService {
    private static final Logger LOGGER=Logger.getLogger(MyHealthyDietEmailService.class.getName());
    
    /**
     * Method that sends a email to the user.
     * @param receiver user that request it.
     * @param password
     */

    public static void sendEmail(String receiver, String password) {
        final ResourceBundle bundle = ResourceBundle.getBundle("emailService.emailCredentials");
        final String transmitter = bundle.getString("TRANSMITTER");
        final String emailkey = bundle.getString("EMAILKEY");
    
        String subject = "Password Recovery for Your Account";
        String body = "Dear customer,\n"
                                + "\n"
                                + "We have received a request to reset the password for your account.\n"
                                + "\n"
                                + "To reset your password. Use this password the next time you log in into the app.\n"
                                + "\n"
                                + password
                                + "\n"
                                + "\n"
                                + "If you did not initiate this request, please contact our customer service team immediately at myhealthydiet.jhms@gmail.com. We take the security of your account very seriously and will assist you in resolving any unauthorized access to your account.\n"
                                + "\n"
                                + "Thank you for choosing MyHealthyDiet for your needs. We appreciate your business and look forward to helping you with any future needs.\n"
                                + "\n"
                                + "Sincerely,\n"
                                + "The MyHealthyDiet Team\n"
                                + "\n"
                                + "Please note that this is an automated message and replies to this email will not be read. If you have any further questions, please contact customer service.";
       
        
        Properties prop = System.getProperties();
        //Using username and password authentication
        prop.put("mail.smtp.auth", "true");
        //To connect securely to the SMTP server
        prop.put("mail.smtp.starttls.enable", "true"); 
        //Google SMTP server
        prop.put("mail.smtp.host", "smtp.gmail.com");
        //Google's secure SMTP port
        prop.put("mail.smtp.port", "587");
        //Account mail
        prop.put("mail.smtp.user", transmitter);
        //Account key
        prop.put("mail.smtp.clave", emailkey);
        
        
        Session session = Session.getDefaultInstance(prop);
        MimeMessage message = new MimeMessage(session);

        try {
            LOGGER.info("Sending password recovery email.");
            message.setFrom(new InternetAddress(transmitter));
            message.addRecipient(Message.RecipientType.TO, new InternetAddress(receiver));
            message.setSubject(subject);
            message.setText(body);
            Transport transport = session.getTransport("smtp");
            transport.connect("smtp.gmail.com", transmitter, emailkey);
            transport.sendMessage(message, message.getAllRecipients());
            transport.close();
        } catch (MessagingException me) {
            LOGGER.severe(me.getMessage());
        }
    }
    
    /**
     * 
     * @return password thats generated randomly
     */
    public static StringBuilder generateRandomPassword() {
        LOGGER.info("Generating new password.");
        String characters = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789!@#$%^&*()-_=+";
        SecureRandom random = new SecureRandom();
        StringBuilder password = new StringBuilder();
        
        for (int i = 0; i < 15; i++) {
            password.append(characters.charAt(random.nextInt(characters.length())));
        }
        return password;
        
        
    }
}
