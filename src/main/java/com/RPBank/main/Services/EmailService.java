package com.RPBank.main.Services;

import com.RPBank.main.DTO.EmailDetails;
import com.RPBank.main.Services.interfaces.EmailServicesImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;


@Service
public class EmailService implements EmailServicesImpl {

    private static final Logger logger = LoggerFactory.getLogger(EmailService.class);

    @Autowired
    private JavaMailSender javaMailSender;

    @Value("${spring.mail.username}")
    private String senderMailId;

    @Override
    public ResponseEntity<String> sendEmailAlert(EmailDetails mail) {

        try{
            SimpleMailMessage simpleMailMessage = new SimpleMailMessage();

            simpleMailMessage.setFrom(senderMailId);
            simpleMailMessage.setTo(mail.getRecipient());
            simpleMailMessage.setText(mail.getMessageBody());
            simpleMailMessage.setSubject(mail.getSubject());

            javaMailSender.send(simpleMailMessage);

            logger.info("Email sent successfully to {}", mail.getRecipient());

            return new ResponseEntity<>("Mail sent Succesfully." ,HttpStatus.OK);
        }
        catch(MailException e) {

            logger.error("Failed to send email to {}: {}", mail.getRecipient(), e.getMessage());

            return new ResponseEntity<>("Invalid Email Address!" , HttpStatus.BAD_REQUEST);
        }
        catch (IllegalArgumentException e) {
            logger.error("Mail Sending failed due to : ", e);

            return new ResponseEntity<>("Mail Sending failed!" , HttpStatus.BAD_REQUEST);
        }
        catch (Exception e) {

            logger.error("Unexpected error while sending email: ", e);

            return new ResponseEntity<>("Internal Server Error!" , HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
