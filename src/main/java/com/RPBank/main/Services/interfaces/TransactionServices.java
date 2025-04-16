package com.RPBank.main.Services.interfaces;

import com.RPBank.main.Models.Transaction;
import com.RPBank.main.DTO.BankResponse;
import com.RPBank.main.DTO.TransactionDTO;
import com.RPBank.main.DTO.TransactionRequests.TransferRequest;
import com.RPBank.main.DTO.TransactionRequests.CreditDebitRequest;
import org.springframework.http.ResponseEntity;

public interface TransactionServices {
    ResponseEntity<BankResponse> creditAmount(CreditDebitRequest transactionInfo);

    ResponseEntity<BankResponse> debitAmount(CreditDebitRequest transactionInfo);

    ResponseEntity<BankResponse> transfer(TransferRequest request);

    ResponseEntity<Transaction> saveTransaction(TransactionDTO transaction);
}
