package com.RPBank.main.Authenticator;

import com.RPBank.main.DAO.UserDAO;
import com.RPBank.main.DTO.BankResponse;
import com.RPBank.main.DTO.UserInfo;
import com.RPBank.main.utils.utilityClasses.AccountUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

public class UserValidation {


    public static ResponseEntity<BankResponse> validateUserDetails(UserDAO userDAO, UserInfo userInfo) {

        if (userDAO.existsByPrimaryPhoneNumber(userInfo.getPrimaryPhoneNumber())) {
            return AccountUtils.buildResponseError(HttpStatus.CONFLICT, "Phone Number Already Exists!");
        }

        if (userDAO.existsByPrimaryEmail(userInfo.getPrimaryEmail())) {
            return AccountUtils.buildResponseError(HttpStatus.CONFLICT, "Email Id Already Exists!");
        }

        return AccountUtils.buildResponseError(HttpStatus.OK, "User Details Validated.");
    }
}
