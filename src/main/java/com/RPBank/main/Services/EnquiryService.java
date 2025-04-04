package com.RPBank.main.Services;

import com.RPBank.main.Beans.User;
import com.RPBank.main.DAO.UserDAO;
import com.RPBank.main.DTO.BankResponse;
import com.RPBank.main.DTO.EnquiryRequest;
import com.RPBank.main.Services.interfaces.EnquiryServicesImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import static com.RPBank.main.utils.utilityClasses.AccountUtils.buildResponseError;


@Service
public class EnquiryService implements EnquiryServicesImpl {

    @Autowired
    private UserDAO userDAO;


    @Override
    public ResponseEntity<BankResponse> accountDetailsEnquiry(EnquiryRequest request) {

        if(userDAO.existsByAccountDTO_AccountNumber(request.getAccountNumber())) {

            User user = userDAO.findByAccountDTO_AccountNumber(request.getAccountNumber());

            BankResponse response = BankResponse.builder()
                    .responseStatus(HttpStatus.OK.toString())
                    .responseMessage("Account Details fetched successfully.")
                    .accountDTO(user.getAccountDTO())
                    .build();

            return new ResponseEntity<>(response, HttpStatus.OK);
        }

        return buildResponseError(HttpStatus.BAD_REQUEST , "Invalid account number or Account Number does not exists!");

    }
}
