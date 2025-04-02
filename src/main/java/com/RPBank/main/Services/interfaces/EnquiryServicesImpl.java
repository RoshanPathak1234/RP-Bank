package com.RPBank.main.Services.interfaces;

import com.RPBank.main.DTO.BankResponse;
import com.RPBank.main.DTO.EnquiryRequest;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface EnquiryServicesImpl {

    ResponseEntity<BankResponse> accountDetailsEnquiry(EnquiryRequest request);
}
