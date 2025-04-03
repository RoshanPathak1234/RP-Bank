package com.RPBank.main.Services.interfaces;

import com.RPBank.main.DTO.BankResponse;
import com.RPBank.main.DTO.TransactionRequests.TransferRequest;
import com.RPBank.main.DTO.TransactionRequests.creditDebitRequest;
import org.springframework.http.ResponseEntity;

public interface TransactionServicesImpl {
    ResponseEntity<BankResponse> creditAmount(creditDebitRequest transactionInfo);

    ResponseEntity<BankResponse> debitAmount(creditDebitRequest transactionInfo);

    ResponseEntity<BankResponse> transfer(TransferRequest request);
}
