package com.RPBank.main.Services.interfaces;

import com.RPBank.main.DTO.webDTO.LoginCredentials;
import com.RPBank.main.DTO.webDTO.RegistrationInfo;
import org.springframework.http.ResponseEntity;

public interface WebServices {
    ResponseEntity<String> register(RegistrationInfo registrationInfo);

    ResponseEntity<String> login(LoginCredentials loginCredentials);
}
