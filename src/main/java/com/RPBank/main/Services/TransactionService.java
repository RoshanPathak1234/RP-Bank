package com.RPBank.main.Services;

import com.RPBank.main.Authenticator.ValidateAccountDetails;
import com.RPBank.main.Beans.User;
import com.RPBank.main.DAO.UserDAO;
import com.RPBank.main.DTO.BankResponse;
import com.RPBank.main.DTO.EmailDetails;
import com.RPBank.main.DTO.TransactionRequests.TransferRequest;
import com.RPBank.main.DTO.TransactionRequests.creditDebitRequest;
import com.RPBank.main.DTO.ValidationResponse;
import com.RPBank.main.Services.interfaces.TransactionServicesImpl;
import com.RPBank.main.DTO.AccountInfo;
import com.RPBank.main.utils.utilityClasses.AccountUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
public class TransactionService implements TransactionServicesImpl {

    @Autowired
    UserDAO userDAO;

    @Autowired
    EmailService emailService;

    @Override
    public ResponseEntity<BankResponse> creditAmount(creditDebitRequest request) {

        ResponseEntity<ValidationResponse> validateResult = ValidateAccountDetails.validateAccountNumber(userDAO , request.getAccountNumber());

        if(validateResult.getStatusCode().equals(HttpStatus.OK)) {
            User user = userDAO.findByAccountInfo_AccountNumber(request.getAccountNumber());
            AccountInfo accountInfo = user.getAccountInfo();
            accountInfo.setAccountBalance(accountInfo.getAccountBalance().add(request.getAmount()));
            user.setAccountInfo(accountInfo);

            userDAO.save(user);

            BankResponse response = BankResponse.builder()
                    .responseStatus(HttpStatus.OK.toString())
                    .responseMessage("Amount credited successfully.")
                    .accountInfo(accountInfo)
                    .build();

            return new ResponseEntity<>(response , HttpStatus.OK);
        }

        return AccountUtils.buildResponseError(HttpStatus.BAD_REQUEST , "Invalid account number!");
    }

    @Override
    public ResponseEntity<BankResponse> debitAmount(creditDebitRequest request) {
        ResponseEntity<ValidationResponse> validateResult = ValidateAccountDetails.validateAccountNumber(userDAO , request.getAccountNumber());

        if(validateResult.getStatusCode().equals(HttpStatus.OK)) {
            User user = userDAO.findByAccountInfo_AccountNumber(request.getAccountNumber());
            AccountInfo accountInfo = user.getAccountInfo();
            if(accountInfo.getAccountBalance().subtract(request.getAmount()).compareTo(BigDecimal.valueOf(1000)) <= 0){
                return AccountUtils.buildResponseError(HttpStatus.BAD_REQUEST , "Transaction failed! you don't have this much balance in your account.");
            }

            accountInfo.setAccountBalance(accountInfo.getAccountBalance().subtract(request.getAmount()));
            user.setAccountInfo(accountInfo);

            userDAO.save(user);

            BankResponse response = BankResponse.builder()
                    .responseStatus(HttpStatus.OK.toString())
                    .responseMessage("Amount credited successfully.")
                    .accountInfo(accountInfo)
                    .build();

            return new ResponseEntity<>(response , HttpStatus.OK);
        }

        return AccountUtils.buildResponseError(HttpStatus.BAD_REQUEST , "Invalid Account number!");
    }

    @Override
    public ResponseEntity<BankResponse> transfer(TransferRequest request) {

        ResponseEntity<ValidationResponse> validateSourceAccount = ValidateAccountDetails.validateAccountHolderByAccountNumber(userDAO , request.getSourceAccountNumber(), request.getSourceAccountName());
        ResponseEntity<ValidationResponse> validateDestinationAccount = ValidateAccountDetails.validateAccountHolderByAccountNumber(userDAO , request.getDestinationAccountNumber(), request.getDestinationAccountName());

        if(!validateSourceAccount.getStatusCode().equals(HttpStatus.OK)) {
            return AccountUtils.buildResponseError(HttpStatus.BAD_REQUEST , validateSourceAccount.getBody().getResponseMessage());
        }

        if(!validateDestinationAccount.getStatusCode().equals(HttpStatus.OK)) {
            return AccountUtils.buildResponseError(HttpStatus.BAD_REQUEST , validateDestinationAccount.getBody().getResponseMessage());
        }

        User sourceUser = userDAO.findByAccountInfo_AccountNumber(request.getSourceAccountNumber());
        User destinationUser = userDAO.findByAccountInfo_AccountNumber(request.getDestinationAccountNumber());

        if(sourceUser.getAccountInfo().getAccountBalance().subtract(request.getAmount()).compareTo(BigDecimal.valueOf(1000)) < 1) {
            return AccountUtils.buildResponseError(HttpStatus.BAD_REQUEST , "You do not have enough account balance! \n you must maintain minimum account balance of Rs. 1000");
        }

        sourceUser.setAccountInfo(AccountInfo.builder()
                        .accountBalance(sourceUser.getAccountInfo().getAccountBalance().subtract(request.getAmount()))
                        .build());

        destinationUser.setAccountInfo(AccountInfo.builder()
                        .accountBalance(destinationUser.getAccountInfo().getAccountBalance().add(request.getAmount()))
                        .build());

        userDAO.save(sourceUser);
        userDAO.save(destinationUser);

        ResponseEntity<String> sourceMail = sendDebitAmountMailAlert(sourceUser, request.getAmount());
        ResponseEntity<String> destinationMail =  sendCreditAmountMailAlert(destinationUser, request.getAmount());

        BankResponse response = BankResponse.builder()
                .accountInfo(sourceUser.getAccountInfo())
                .responseMessage("Amount transferred successfully.")
                .responseStatus(HttpStatus.OK.toString())
                .build();

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    private ResponseEntity<String> sendCreditAmountMailAlert(User user, BigDecimal amount){

        String msg = "Dear " + user.getAccountInfo().getAccountName()
                + ", \nA sum of " + amount + " is credited in your account having account number " + user.getAccountInfo().getAccountNumber()
                + "Your current account balance is : " + user.getAccountInfo().getAccountBalance() + ". \n"
                + "Best regards,\nRP Bank";

        return emailService.sendEmailAlert(EmailDetails.builder()
                        .recipient(user.getPrimaryEmail())
                        .subject("Amount credited to your account.")
                        .messageBody(msg)
                        .build());

    }

    private ResponseEntity<String> sendDebitAmountMailAlert(User user, BigDecimal amount){

        String msg = "Dear " + user.getAccountInfo().getAccountName()
                + ", \nA sum of " + amount + " is debited from your account having account number " + user.getAccountInfo().getAccountNumber()
                + "Your current account balance is : " + user.getAccountInfo().getAccountBalance() + ". \n"
                + "Best regards,\nRP Bank";

        return emailService.sendEmailAlert(EmailDetails.builder()
                        .recipient(user.getPrimaryEmail())
                        .subject("Amount debited from your account.")
                        .messageBody(msg)
                        .build());

    }
}
