package com.RPBank.main.Authenticator;

import com.RPBank.main.Models.User;
import com.RPBank.main.DAO.UserDAO;
import com.RPBank.main.DTO.ValidationResponse;
import com.RPBank.main.utils.utilityClasses.AccountUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;


public class ValidateAccountDetails {

    public static ResponseEntity<ValidationResponse> validateAccountNumber(UserDAO userDAO , String accountNumber){
        if(userDAO.existsByAccountDTO_AccountNumber(accountNumber)) {
            ValidationResponse response = ValidationResponse.builder()
                    .responseStatus(HttpStatus.OK.toString())
                    .responseMessage("Account number validated successfully.")
                    .build();

            return new ResponseEntity<>(response , HttpStatus.OK);
        }

        return AccountUtils.buildValidationResponseError(HttpStatus.BAD_REQUEST, "Invalid account number!");
    }

    public static ResponseEntity<ValidationResponse> validateCustomerId(UserDAO userDAO , Long customerId){
        if(userDAO.existsByCustomerId(customerId)) {
            ValidationResponse response = ValidationResponse.builder()
                    .responseStatus(HttpStatus.OK.toString())
                    .responseMessage("Customer Id validated successfully.")
                    .build();

            return new ResponseEntity<>(response , HttpStatus.OK);
        }

        return AccountUtils.buildValidationResponseError(HttpStatus.BAD_REQUEST, "Invalid customer id!");
    }

    public static ResponseEntity<ValidationResponse> validateAccountHolderByAccountNumber(UserDAO userDAO , String accountNumber , String accountName) {

        ResponseEntity<ValidationResponse> response = validateAccountNumber(userDAO, accountNumber);

        if(!response.getStatusCode().equals(HttpStatus.OK)) {
            return AccountUtils.buildValidationResponseError((HttpStatus) response.getStatusCode(), response.getBody().getResponseMessage());
        }

        User user = userDAO.findByAccountDTO_AccountNumber(accountNumber);
        String name = user.getAccountDTO().getAccountName();

        if(!accountName.equals(name)) {
            return AccountUtils.buildValidationResponseError(HttpStatus.BAD_REQUEST , "Account credentials not matched!");
        }

        ValidationResponse validationResponse = ValidationResponse.builder()
                .responseStatus(HttpStatus.OK.toString())
                .responseMessage("Account credentials matched successfully.")
                .build();

        return new ResponseEntity<>(validationResponse, HttpStatus.OK);
    }

    public static ResponseEntity<ValidationResponse> validateAccountHolderByCustomerId(UserDAO userDAO , Long CustomerId , String accountName) {

        ResponseEntity<ValidationResponse> response = validateCustomerId(userDAO, CustomerId);

        if(!response.getStatusCode().equals(HttpStatus.OK)) {
            return AccountUtils.buildValidationResponseError((HttpStatus) response.getStatusCode(), response.getBody().getResponseMessage());
        }

        User user = userDAO.findById(CustomerId).get();
        String name = user.getAccountDTO().getAccountName();

        if(!accountName.equals(name)) {
            return AccountUtils.buildValidationResponseError(HttpStatus.BAD_REQUEST , "Account credentials not matched!");
        }

        ValidationResponse validationResponse = ValidationResponse.builder()
                .responseStatus(HttpStatus.OK.toString())
                .responseMessage("Account credentials matched successfully.")
                .build();

        return new ResponseEntity<>(validationResponse, HttpStatus.OK);
    }



}
