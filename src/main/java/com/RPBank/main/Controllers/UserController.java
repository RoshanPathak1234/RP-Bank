package com.RPBank.main.Controllers;

import com.RPBank.main.DTO.BankResponse;
import com.RPBank.main.DTO.EnquiryRequest;
import com.RPBank.main.DTO.TransactionRequests.TransferRequest;
import com.RPBank.main.DTO.TransactionRequests.creditDebitRequest;
import com.RPBank.main.DTO.UserInfo;
import com.RPBank.main.Services.EnquiryService;
import com.RPBank.main.Services.NewAccountService;
import com.RPBank.main.Services.TransactionService;
import com.RPBank.main.utils.enums.AccountType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/user")
public class UserController {

    @Autowired
    NewAccountService newAccountService;

    @Autowired
    EnquiryService enquiryService;

    @Autowired
    TransactionService transactionService;


    @GetMapping("/enquiry/accountDetails")
    public  ResponseEntity<BankResponse> enquiryAccountDetails(@RequestBody EnquiryRequest request) {
        return enquiryService.accountDetailsEnquiry(request);
    }

    @PostMapping("/createNewAccount")
    public ResponseEntity<BankResponse> createNewAccount(@RequestBody UserInfo userInfo , @RequestParam AccountType type){
        return newAccountService.createAccount(userInfo , type);
    }

    @PostMapping("/credit")
    public ResponseEntity<BankResponse> creditAmount(@RequestBody creditDebitRequest request) {
        return transactionService.creditAmount(request);
    }

    @PostMapping("/debit")
    public ResponseEntity<BankResponse> debitAmount(@RequestBody creditDebitRequest request) {
        return transactionService.debitAmount(request);
    }

    @PostMapping("/transfer")
    public ResponseEntity<BankResponse> transfer(@RequestBody TransferRequest request) {
        return transactionService.transfer(request);
    }
}
