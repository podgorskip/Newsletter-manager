package actions.subscription;

import javax.mail.*;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.List;
import java.util.Properties;

public class EmailSender {
    private final String sender;
    private final List<String> recipients;
    private static final String host = "127.0.0.0";
    private final Properties properties;
    private final Session session;

    public EmailSender(String sender, List<String> recipients) {
        this.sender = sender;
        this.recipients = recipients;
        properties = System.getProperties();
        properties.setProperty("mail.smtp.host", host);
        properties.setProperty("mail.smtp.port", "587");
        session = Session.getDefaultInstance(properties);
    }

    public boolean send(String content, String date) {
        try {

            MimeMessage message = new MimeMessage(session);
            message.setFrom(new InternetAddress(sender));

            InternetAddress[] addresses = recipients.stream()
                    .map(email -> {
                        try {
                            return new InternetAddress(email);
                        } catch (AddressException e) {
                            throw new RuntimeException(e);
                        }
                    })
                    .toArray(InternetAddress[]::new);

            message.addRecipients(Message.RecipientType.TO, addresses);

            message.setSubject("Newsletter for " + date);
            message.setText(content);

            Transport.send(message);
            return true;

        } catch (MessagingException e) {
            throw new RuntimeException(e);
        }
    }
}
