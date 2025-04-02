package com.RPBank.main.Controllers;

import com.RPBank.main.DTO.BankResponse;
import com.RPBank.main.DTO.EnquiryRequest;
import com.RPBank.main.DTO.TransactionRequest;
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

    @PostMapping("/createNewAccount")
    public ResponseEntity<BankResponse> createNewAccount(@RequestBody UserInfo userInfo , @RequestParam AccountType type){
        return newAccountService.createAccount(userInfo , type);
    }

    @GetMapping("/enquiry/accountDetails")
    public  ResponseEntity<BankResponse> enquiryAccountDetails(@RequestBody EnquiryRequest request) {
        return enquiryService.accountDetailsEnquiry(request);
    }

    @PostMapping("/credit")
    public ResponseEntity<BankResponse> creditAmount(@RequestBody TransactionRequest request) {
        return transactionService.creditAmount(request);
    }

    @PostMapping("/debit")
    public ResponseEntity<BankResponse> debitAmount(@RequestBody TransactionRequest request) {
        return transactionService.debitAmount(request);
    }
}
