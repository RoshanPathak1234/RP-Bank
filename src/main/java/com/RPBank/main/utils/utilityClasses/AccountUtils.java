package com.RPBank.main.utils.utilityClasses;

import com.RPBank.main.DTO.BankResponse;
import com.RPBank.main.DTO.ValidationResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.time.LocalDateTime;
import java.time.Year;

public class AccountUtils {

    public static String generateAccountNumber() {

        String year = String.valueOf(Year.now());
        String month = Integer.toString(LocalDateTime.now().getMonthValue());
        String day = Integer.toString(LocalDateTime.now().getDayOfMonth());

        int min = 100000;
        int max = 999999;

        String randomNumber = String.valueOf((int) Math.floor(Math.random() * (max - min + 1) + min));

        return year + month + day + randomNumber;

    }

    public static ResponseEntity<BankResponse> buildResponseError(HttpStatus status, String message) {
        BankResponse response = BankResponse.builder()
                .responseStatus(String.valueOf(status.value()))
                .responseMessage(message)
                .accountInfo(null)
                .build();
        return new ResponseEntity<>(response, status);
    }
    public static ResponseEntity<ValidationResponse> buildValidationResponseError(HttpStatus status, String message) {

        ValidationResponse response = ValidationResponse.builder()
                .responseStatus(String.valueOf(status.value()))
                .responseMessage(message)
                .build();
        return new ResponseEntity<>(response, status);
    }
}
