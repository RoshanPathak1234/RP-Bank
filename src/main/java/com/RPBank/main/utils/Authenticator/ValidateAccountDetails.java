package com.RPBank.main.utils.Authenticator;

import com.RPBank.main.DAO.UserDAO;
import com.RPBank.main.DTO.ValidationResponse;
import com.RPBank.main.utils.utilityClasses.AccountUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;


public class ValidateAccountDetails {

    public static ResponseEntity<ValidationResponse> validateAccountNumber(UserDAO userDAO , String accountNumber){
        if(userDAO.existsByAccountInfo_AccountNumber(accountNumber)) {
            ValidationResponse response = ValidationResponse.builder()
                    .responseStatus(HttpStatus.OK.toString())
                    .responseMessage("Account number validated successfully.")
                    .build();
        }

        return AccountUtils.buildValidationResponseError(HttpStatus.BAD_REQUEST, "Invalid account number!");
    }

    public static ResponseEntity<ValidationResponse> validateCustomerId(UserDAO userDAO , Long customerId){
        if(userDAO.existsByCustomerId(customerId)) {
            ValidationResponse response = ValidationResponse.builder()
                    .responseStatus(HttpStatus.OK.toString())
                    .responseMessage("Customer Id validated successfully.")
                    .build();
        }

        return AccountUtils.buildValidationResponseError(HttpStatus.BAD_REQUEST, "Invalid customer id!");
    }
}
