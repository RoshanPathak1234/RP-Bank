package com.RPBank.main.Services.Impl;

import com.RPBank.main.Config.JwtTokenProvider;
import com.RPBank.main.DAO.UserDAO;
import com.RPBank.main.DTO.EmailDetails;
import com.RPBank.main.DTO.webDTO.LoginCredentials;
import com.RPBank.main.DTO.webDTO.RegistrationInfo;
import com.RPBank.main.Models.User;
import com.RPBank.main.Services.interfaces.WebServices;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Slf4j
@Service
public class WebService implements WebServices {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private UserDAO userDAO;

    @Autowired
    private EmailService emailService;

    @Autowired
    private JwtTokenProvider jwtTokenProvider;

    @Override
    public ResponseEntity<String> register(RegistrationInfo registrationInfo) {
        return null;
    }

    @Override
    public ResponseEntity<String> login(LoginCredentials loginCredentials) {
        log.info("Attempting login for user: {}", loginCredentials.getUserName());

        try {
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            loginCredentials.getUserName(),
                            loginCredentials.getPassword()
                    )
            );
            log.info("Authentication successful for user: {}", loginCredentials.getUserName());

            String userEmail = getUserEmail(loginCredentials.getUserName());
            String formattedDateTime = LocalDateTime.now()
                    .format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));

            String messageBody = String.format(
                    "Dear %s,\n\n" +
                            "We noticed a login to your RPBank account on:\n\n" +
                            "Date & Time: %s\n" +
                            "Username: %s\n\n" +
                            "If this was you, no further action is required.\n" +
                            "If you did not perform this login, please contact RPBank support immediately.\n\n" +
                            "Regards,\n" +
                            "RPBank Security Team",
                    loginCredentials.getUserName(),
                    formattedDateTime,
                    loginCredentials.getUserName()
            );

            EmailDetails loginAlert = EmailDetails.builder()
                    .subject("Login Alert")
                    .messageBody(messageBody)
                    .recipient(userEmail)
                    .build();

            emailService.sendEmailAlert(loginAlert);
            log.info("Login alert email sent to: {}", userEmail);

            String token = jwtTokenProvider.generateToken(authentication);
            String responseBody = String.format(
                    "{ \"message\": \"Login successful\", \"token\": \"%s\", \"tokenType\": \"Bearer\" }",
                    token
            );

            return ResponseEntity.ok(responseBody);

        } catch (Exception e) {
            log.error("Authentication failed for user: {} | Reason: {}", loginCredentials.getUserName(), e.getMessage());
            return ResponseEntity.status(401).body("Login failed. Please check your credentials.");
        }
    }

    private String getUserEmail(String username) {
        User user = userDAO.findByLoginCredentials_UserName(username);
        String email = user.getPrimaryEmail();
        log.debug("Fetched email {} for user: {}", email, username);
        return email;
    }
}
