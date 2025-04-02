package com.RPBank.main.Services.interfaces;

import com.RPBank.main.DTO.EmailDetails;
import org.springframework.http.ResponseEntity;

public interface EmailServicesImpl {
    ResponseEntity<String> sendEmailAlert(EmailDetails mail);
}
