package com.RPBank.main.Services.interfaces;

import com.RPBank.main.DTO.BankResponse;
import com.RPBank.main.DTO.EnquiryRequest;
import org.springframework.http.ResponseEntity;

public interface EnquiryServices {

    ResponseEntity<BankResponse> accountDetailsEnquiry(EnquiryRequest request);
}
