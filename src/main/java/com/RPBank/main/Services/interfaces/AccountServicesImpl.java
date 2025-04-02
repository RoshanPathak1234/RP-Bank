package com.RPBank.main.Services.interfaces;

import com.RPBank.main.DTO.BankResponse;
import com.RPBank.main.DTO.UserInfo;
import com.RPBank.main.utils.enums.AccountType;
import org.springframework.http.ResponseEntity;

public interface AccountServicesImpl {
    ResponseEntity<BankResponse> createAccount(UserInfo userInfo , AccountType accountType);

}
