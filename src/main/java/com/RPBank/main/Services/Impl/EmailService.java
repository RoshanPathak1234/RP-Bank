package com.RPBank.main.Services.Impl;

import com.RPBank.main.DTO.EmailDetails;
import com.RPBank.main.Services.interfaces.EmailServices;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.FileSystemResource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import java.io.File;


@Service
@Slf4j
public class EmailService implements EmailServices {


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

            log.info("Email sent successfully to {}", mail.getRecipient());

            return new ResponseEntity<>("Mail sent Successfully." ,HttpStatus.OK);
        }
        catch(MailException e) {

            log.error("Failed to send email to {}: {}", mail.getRecipient(), e.getMessage());

            return new ResponseEntity<>("Invalid Email Address!" , HttpStatus.BAD_REQUEST);
        }
        catch (IllegalArgumentException e) {
            log.error("Mail Sending failed due to : ", e);

            return new ResponseEntity<>("Mail Sending failed!" , HttpStatus.BAD_REQUEST);
        }
        catch (Exception e) {

            log.error("Unexpected error while sending email: ", e);

            return new ResponseEntity<>("Internal Server Error!" , HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public ResponseEntity<String> sendEmailWithAttachment(EmailDetails mail) {
        MimeMessage mimeMessage = javaMailSender.createMimeMessage();

        try {
            MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true);
            helper.setFrom(senderMailId);
            helper.setTo(mail.getRecipient());
            helper.setSubject(mail.getSubject());
            helper.setText(mail.getMessageBody());

            File file = new File(mail.getAttachment());
            if (!file.exists()) {
                String errorMessage = "Attachment file not found: " + mail.getAttachment();
                log.error(errorMessage);
                return ResponseEntity.badRequest().body(errorMessage);
            }

            FileSystemResource fileResource = new FileSystemResource(file);
            helper.addAttachment(fileResource.getFilename(), fileResource);

            javaMailSender.send(mimeMessage);
            log.info("Email sent successfully to {}", mail.getRecipient());
            return ResponseEntity.ok("Email sent successfully to " + mail.getRecipient());

        } catch (MessagingException e) {
            log.error("Failed to send email: {}", e.getMessage(), e);
            return ResponseEntity.internalServerError().body("Failed to send email: " + e.getMessage());
        }

    }

}
