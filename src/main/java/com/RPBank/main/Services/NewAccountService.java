package com.RPBank.main.Services;

import com.RPBank.main.utils.Authenticator.UserValidation;
import com.RPBank.main.Beans.User;
import com.RPBank.main.DAO.UserDAO;
import com.RPBank.main.DTO.*;
import com.RPBank.main.Services.interfaces.AccountServicesImpl;
import com.RPBank.main.utils.enums.AccountType;
import com.RPBank.main.utils.utilityClasses.AccountInfo;
import com.RPBank.main.utils.enums.AccountStatus;
import com.RPBank.main.utils.utilityClasses.AccountUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;


@Service
public class NewAccountService implements AccountServicesImpl {

    @Autowired
    private UserDAO userDAO;

    @Autowired
    private EmailService emailService;

    @Override
    public ResponseEntity<BankResponse> createAccount(UserInfo userInfo , AccountType accountType) {

        ResponseEntity<BankResponse> validationResponse = UserValidation.validateUserDetails(userDAO,userInfo);

        if(validationResponse.getStatusCode() != HttpStatus.OK) {
            return validationResponse;
        }

        AccountInfo newAccountDetails = generateNewAccountDetails(userInfo , accountType);

        while(userDAO.existsByAccountInfo_AccountNumber(newAccountDetails.getAccountNumber())) {
            newAccountDetails = generateNewAccountDetails(userInfo , accountType);
        }


        User newUser = User.builder()
                .firstName(userInfo.getFirstName())
                .lastName(userInfo.getLastName())
                .gender(userInfo.getGender())
                .dateOfBirth(userInfo.getDateOfBirth())
                .primaryPhoneNumber(userInfo.getPrimaryPhoneNumber())
                .secondaryPhoneNumber(userInfo.getSecondaryPhoneNumber())
                .primaryEmail(userInfo.getPrimaryEmail())
                .secondaryEmail(userInfo.getSecondaryEmail())
                .occupation(userInfo.getOccupation())
                .currentAddress(userInfo.getCurrentAddress())
                .permanentAddress(userInfo.getPermanentAddress())
                .accountInfo(newAccountDetails)
                .status(AccountStatus.ACTIVE)
                .build();

        User savedUser = userDAO.save(newUser);

        sendEmailAlert(savedUser);

        BankResponse response = BankResponse.builder()
                .responseStatus((HttpStatus.CREATED).toString())
                .responseMessage("Account created successfully.")
                .accountInfo(newAccountDetails)
                .build();

        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }


    private AccountInfo generateNewAccountDetails(UserInfo userInfo , AccountType accountType) {

        return AccountInfo.builder()
                .type(accountType)
                .accountBalance(BigDecimal.ZERO)
                .accountName(userInfo.getFirstName() + " " + userInfo.getLastName())
                .accountNumber(AccountUtils.generateAccountNumber())
                .build();
    }

    private void sendEmailAlert(User newUser ) {

        String msg = "Dear " + newUser.getAccountInfo().getAccountName() + " , "
                + " \n Congratulations! Your account in RP Bank is successfully created on " +  newUser.getDateOfCreation() +" . " +
                "\nYour account Details are as follows :  " +
                "\n Account Holder's name : "+ newUser.getAccountInfo().getAccountName() +
                "\n Date of Birth :  " + newUser.getDateOfBirth() +
                "\n Account Number : " + newUser.getAccountInfo().getAccountNumber() +
                "\n Account Type : " + newUser.getAccountInfo().getType() +
                "\n Account Balance : " + newUser.getAccountInfo().getAccountBalance()
                + "\nRegards,\nRp Bank" ;

        emailService.sendEmailAlert(EmailDetails.builder()
                .recipient(newUser.getPrimaryEmail())
                .subject("New Account opening confirmation Mail")
                .messageBody(msg)
                .build());
    }






}
