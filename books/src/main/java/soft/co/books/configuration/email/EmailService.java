package soft.co.books.configuration.email;

import org.springframework.mail.MailException;
import org.springframework.mail.MailSender;
import org.springframework.mail.SimpleMailMessage;

public class EmailService implements MailSender {

    @Override
    public void send(SimpleMailMessage simpleMailMessage) throws MailException {
        SimpleMailMessage mailMessage = new SimpleMailMessage();
//        mailMessage.set
    }

    @Override
    public void send(SimpleMailMessage... simpleMailMessages) throws MailException {

    }
}
