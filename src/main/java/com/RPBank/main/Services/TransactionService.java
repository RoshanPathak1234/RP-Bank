package com.RPBank.main.Services;

import com.RPBank.main.Beans.User;
import com.RPBank.main.DAO.UserDAO;
import com.RPBank.main.DTO.BankResponse;
import com.RPBank.main.DTO.TransactionRequest;
import com.RPBank.main.DTO.ValidationResponse;
import com.RPBank.main.Services.interfaces.TransactionServicesImpl;
import com.RPBank.main.utils.Authenticator.ValidateAccountDetails;
import com.RPBank.main.utils.utilityClasses.AccountInfo;
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

    @Override
    public ResponseEntity<BankResponse> creditAmount(TransactionRequest request) {

//        ResponseEntity<ValidationResponse> validateResult = ValidateAccountDetails.validateAccountNumber(userDAO , request.getAccountNumber());

        if(userDAO.existsByAccountInfo_AccountNumber(request.getAccountNumber())) {
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
    public ResponseEntity<BankResponse> debitAmount(TransactionRequest request) {
//        ResponseEntity<ValidationResponse> validateResult = ValidateAccountDetails.validateAccountNumber(userDAO , request.getAccountNumber());

        if(userDAO.existsByAccountInfo_AccountNumber(request.getAccountNumber())) {
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
}
