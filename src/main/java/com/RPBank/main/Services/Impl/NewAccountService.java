package com.RPBank.main.Services.Impl;

import com.RPBank.main.Authenticator.UserValidation;
import com.RPBank.main.DTO.webDTO.LoginCredentials;
import com.RPBank.main.Models.User;
import com.RPBank.main.DAO.UserDAO;
import com.RPBank.main.DTO.*;
import com.RPBank.main.Services.interfaces.AccountServices;
import com.RPBank.main.utils.enums.AccountType;
import com.RPBank.main.DTO.AccountDTO;
import com.RPBank.main.utils.enums.AccountStatus;
import com.RPBank.main.utils.utilityClasses.AccountUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;


@Service
public class NewAccountService implements AccountServices {

    @Autowired
    private UserDAO userDAO;

    @Autowired
    private EmailService emailService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public ResponseEntity<BankResponse> createAccount(UserInfo userInfo , AccountType accountType) {

        ResponseEntity<BankResponse> validationResponse = UserValidation.validateUserDetails(userDAO,userInfo);

        if(validationResponse.getStatusCode() != HttpStatus.OK) {
            return validationResponse;
        }

        AccountDTO newAccountDetails = generateNewAccountDetails(userInfo , accountType);

        while(userDAO.existsByAccountDTO_AccountNumber(newAccountDetails.getAccountNumber())) {
            newAccountDetails = generateNewAccountDetails(userInfo , accountType);
        }

        LoginCredentials credentials = LoginCredentials.builder()
                .userName(userInfo.getLoginCredentials().getUserName())
                .password(passwordEncoder.encode(userInfo.getLoginCredentials().getPassword()))
                .build();

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
                .accountDTO(newAccountDetails)
                .status(AccountStatus.ACTIVE)
                .loginCredentials(credentials)
                .role(userInfo.getRole())
                .build();

        User savedUser = userDAO.save(newUser);

        sendEmailAlert(savedUser);

        BankResponse response = BankResponse.builder()
                .responseStatus((HttpStatus.CREATED).toString())
                .responseMessage("Account created successfully.")
                .accountDTO(newAccountDetails)
                .build();

        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }


    private AccountDTO generateNewAccountDetails(UserInfo userInfo , AccountType accountType) {

        return AccountDTO.builder()
                .type(accountType)
                .accountBalance(BigDecimal.ZERO)
                .accountName(userInfo.getFirstName() + " " + userInfo.getLastName())
                .accountNumber(AccountUtils.generateAccountNumber())
                .build();
    }

    private void sendEmailAlert(User newUser) {
        String msg = String.format(
                "Dear %s, \n \n"
                        + "Congratulations! Your account in RP Bank has been successfully created on %s.\n\n"
                        + "Your account details are as follows:\n"
                        + "  • Account Holder's Name: %s\n"
                        + "  • Date of Birth: %s\n"
                        + "  • Account Number: %s\n"
                        + "  • Account Type: %s\n"
                        + "  • Account Balance: %s\n\n"
                        + "Best regards,\n"
                        + "RP Bank",
                newUser.getAccountDTO().getAccountName(),
                newUser.getDateOfCreation(),
                newUser.getAccountDTO().getAccountName(),
                newUser.getDateOfBirth(),
                newUser.getAccountDTO().getAccountNumber(),
                newUser.getAccountDTO().getType(),
                newUser.getAccountDTO().getAccountBalance()
        );

        emailService.sendEmailAlert(
                EmailDetails.builder()
                        .recipient(newUser.getPrimaryEmail())
                        .subject("New Account Opening Confirmation")
                        .messageBody(msg)
                        .build()
        );
    }
}
