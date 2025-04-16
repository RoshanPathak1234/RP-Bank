package com.RPBank.main.Services.Impl;

import com.RPBank.main.Authenticator.ValidateAccountDetails;
import com.RPBank.main.Models.Transaction;
import com.RPBank.main.Models.User;
import com.RPBank.main.DAO.TransactionDAO;
import com.RPBank.main.DAO.UserDAO;
import com.RPBank.main.DTO.*;
import com.RPBank.main.DTO.TransactionRequests.TransferRequest;
import com.RPBank.main.DTO.TransactionRequests.CreditDebitRequest;
import com.RPBank.main.Services.interfaces.TransactionServices;
import com.RPBank.main.utils.enums.TransactionStatus;
import com.RPBank.main.utils.enums.TransactionType;
import com.RPBank.main.utils.utilityClasses.AccountUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
public class TransactionService implements TransactionServices {

    @Autowired
    UserDAO userDAO;

    @Autowired
    EmailService emailService;

    @Autowired
    TransactionDAO transactionDAO;

    @Override
    public ResponseEntity<BankResponse> creditAmount(CreditDebitRequest request) {

        ResponseEntity<ValidationResponse> validateResult = ValidateAccountDetails.validateAccountNumber(userDAO , request.getAccountNumber());

        if(validateResult.getStatusCode().equals(HttpStatus.OK)) {
            User user = userDAO.findByAccountDTO_AccountNumber(request.getAccountNumber());
            AccountDTO accountDTO = user.getAccountDTO();
            accountDTO.setAccountBalance(accountDTO.getAccountBalance().add(request.getAmount()));
            user.setAccountDTO(accountDTO);

            userDAO.save(user);
            sendCreditAmountMailAlert(user , request.getAmount());

            BankResponse response = BankResponse.builder()
                    .responseStatus(HttpStatus.OK.toString())
                    .responseMessage("Amount credited successfully.")
                    .accountDTO(accountDTO)
                    .build();

            TransactionDTO transactionDTO = TransactionDTO.builder()
                    .amount(request.getAmount())
                    .transactionType(TransactionType.CREDIT)
                    .accountNumber(request.getAccountNumber())
                    .build();

            saveTransaction(transactionDTO);

            return new ResponseEntity<>(response , HttpStatus.OK);
        }

        return AccountUtils.buildResponseError(HttpStatus.BAD_REQUEST , "Invalid account number!");
    }

    @Override
    public ResponseEntity<BankResponse> debitAmount(CreditDebitRequest request) {
        ResponseEntity<ValidationResponse> validateResult = ValidateAccountDetails.validateAccountNumber(userDAO , request.getAccountNumber());

        if(validateResult.getStatusCode().equals(HttpStatus.OK)) {
            User user = userDAO.findByAccountDTO_AccountNumber(request.getAccountNumber());
            AccountDTO accountDTO = user.getAccountDTO();
            if(accountDTO.getAccountBalance().subtract(request.getAmount()).compareTo(BigDecimal.valueOf(1000)) <= 0){
                return AccountUtils.buildResponseError(HttpStatus.BAD_REQUEST , "Transaction failed! you don't have this much balance in your account.");
            }

            accountDTO.setAccountBalance(accountDTO.getAccountBalance().subtract(request.getAmount()));
            user.setAccountDTO(accountDTO);

            userDAO.save(user);
            sendDebitAmountMailAlert(user , request.getAmount());

            BankResponse response = BankResponse.builder()
                    .responseStatus(HttpStatus.OK.toString())
                    .responseMessage("Amount debited successfully.")
                    .accountDTO(accountDTO)
                    .build();

            TransactionDTO transactionDTO = TransactionDTO.builder()
                    .amount(request.getAmount())
                    .transactionType(TransactionType.DEBIT)
                    .accountNumber(request.getAccountNumber())
                    .build();

            saveTransaction(transactionDTO);

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

        User sourceUser = userDAO.findByAccountDTO_AccountNumber(request.getSourceAccountNumber());
        User destinationUser = userDAO.findByAccountDTO_AccountNumber(request.getDestinationAccountNumber());

        if(sourceUser.getAccountDTO().getAccountBalance().subtract(request.getAmount()).compareTo(BigDecimal.valueOf(1000)) < 1) {
            return AccountUtils.buildResponseError(HttpStatus.BAD_REQUEST , "You do not have enough account balance! \n you must maintain minimum account balance of Rs. 1000");
        }

        sourceUser.setAccountDTO(AccountDTO.builder()
                        .accountBalance(sourceUser.getAccountDTO().getAccountBalance().subtract(request.getAmount()))
                        .accountName(sourceUser.getAccountDTO().getAccountName())
                        .accountNumber(sourceUser.getAccountDTO().getAccountNumber())
                        .type(sourceUser.getAccountDTO().getType())
                        .build());

        destinationUser.setAccountDTO(AccountDTO.builder()
                        .accountBalance(destinationUser.getAccountDTO().getAccountBalance().add(request.getAmount()))
                        .accountName(destinationUser.getAccountDTO().getAccountName())
                        .accountNumber(destinationUser.getAccountDTO().getAccountNumber())
                        .type(destinationUser.getAccountDTO().getType())
                        .build());

        userDAO.save(sourceUser);
        userDAO.save(destinationUser);

        ResponseEntity<String> sourceMail = sendDebitAmountMailAlert(sourceUser, request.getAmount());
        ResponseEntity<String> destinationMail =  sendCreditAmountMailAlert(destinationUser, request.getAmount());

        BankResponse response = BankResponse.builder()
                .accountDTO(sourceUser.getAccountDTO())
                .responseMessage("Amount transferred successfully.")
                .responseStatus(HttpStatus.OK.toString())
                .build();

        TransactionDTO sourceTransactionDTO = TransactionDTO.builder()
                .amount(request.getAmount())
                .transactionType(TransactionType.DEBIT)
                .accountNumber(request.getSourceAccountNumber())
                .build();

        saveTransaction(sourceTransactionDTO);

        TransactionDTO destinationTransactionDTO = TransactionDTO.builder()
                .amount(request.getAmount())
                .transactionType(TransactionType.CREDIT)
                .accountNumber(request.getDestinationAccountNumber())
                .build();

        saveTransaction(destinationTransactionDTO);

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<Transaction> saveTransaction(TransactionDTO transactionDTO) {

        Transaction transaction = Transaction.builder()
                .transactionType(transactionDTO.getTransactionType())
                .amount(transactionDTO.getAmount())
                .accountNumber(transactionDTO.getAccountNumber())
                .status(TransactionStatus.SUCCESS)
                .build();

        Transaction savedTransaction =  transactionDAO.save(transaction);

        return new ResponseEntity<>(savedTransaction , HttpStatus.OK);
    }

    private ResponseEntity<String> sendCreditAmountMailAlert(User user, BigDecimal amount) {
        String msg = String.format(
                """
                        Dear %s,\s
                        \s
                        A sum of %s has been credited to your account (Account No: %s).
                        Your current account balance is: %s.
                        
                        Best regards,
                        RP Bank""",
                user.getAccountDTO().getAccountName(),
                amount,
                user.getAccountDTO().getAccountNumber(),
                user.getAccountDTO().getAccountBalance()
        );

        return emailService.sendEmailAlert(
                EmailDetails.builder()
                        .recipient(user.getPrimaryEmail())
                        .subject("Amount Credited to Your Account")
                        .messageBody(msg)
                        .build()
        );
    }


    private ResponseEntity<String> sendDebitAmountMailAlert(User user, BigDecimal amount) {
        String msg = String.format(
                """
                        Dear %s,\s
                        \s
                        A sum of %s has been debited from your account (Account No: %s).
                        Your current account balance is: %s.
                        
                        Best regards,
                        RP Bank""",
                user.getAccountDTO().getAccountName(),
                amount,
                user.getAccountDTO().getAccountNumber(),
                user.getAccountDTO().getAccountBalance()
        );

        return emailService.sendEmailAlert(
                EmailDetails.builder()
                        .recipient(user.getPrimaryEmail())
                        .subject("Amount Debited from Your Account")
                        .messageBody(msg)
                        .build()
        );
    }

}
