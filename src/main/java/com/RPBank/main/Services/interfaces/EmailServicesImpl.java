package com.RPBank.main.Services.interfaces;

import com.RPBank.main.DTO.EmailDetails;
import com.RPBank.main.Services.EmailService;
import org.springframework.http.ResponseEntity;

public interface EmailServicesImpl {
    ResponseEntity<String> sendEmailAlert(EmailDetails mail);

    ResponseEntity<String> sendEmailWithAttachment(EmailDetails mail);
}
