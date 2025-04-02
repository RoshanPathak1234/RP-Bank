package com.RPBank.main.Services.interfaces;

import com.RPBank.main.DTO.BankResponse;
import com.RPBank.main.DTO.TransactionRequest;
import org.springframework.http.ResponseEntity;

public interface TransactionServicesImpl {
    ResponseEntity<BankResponse> creditAmount(TransactionRequest transactionInfo);

    ResponseEntity<BankResponse> debitAmount(TransactionRequest transactionInfo);
}
