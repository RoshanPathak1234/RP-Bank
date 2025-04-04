package com.RPBank.main.Controllers;

import com.RPBank.main.DTO.BankResponse;
import com.RPBank.main.DTO.EnquiryRequest;
import com.RPBank.main.DTO.UserInfo;
import com.RPBank.main.Services.EnquiryService;
import com.RPBank.main.Services.NewAccountService;
import com.RPBank.main.utils.enums.AccountType;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/user")
@Tag(name="user account management api")
public class UserController {

    @Autowired
    NewAccountService newAccountService;

    @Autowired
    EnquiryService enquiryService;


    @Operation(
            summary = "Fetch Account Details",
            description = "Retrieves detailed account information, including balance and transaction history."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Account details retrieved successfully."),
            @ApiResponse(responseCode = "400", description = "Bad request. Invalid input parameters."),
            @ApiResponse(responseCode = "401", description = "Unauthorized. Authentication required."),
            @ApiResponse(responseCode = "404", description = "Not found. Account does not exist."),
            @ApiResponse(responseCode = "500", description = "Internal server error. Unable to fetch account details.")
    })
    @GetMapping("/enquiry/accountDetails")
    public  ResponseEntity<BankResponse> enquiryAccountDetails(@RequestBody EnquiryRequest request) {
        return enquiryService.accountDetailsEnquiry(request);
    }

    @Operation(
            summary = "Create New User Account",
            description = "Creates a new user account and assigns a unique Account ID and Customer ID."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Account created successfully."),
            @ApiResponse(responseCode = "400", description = "Bad request. Invalid input data."),
            @ApiResponse(responseCode = "409", description = "Conflict. Account already exists."),
            @ApiResponse(responseCode = "500", description = "Internal server error. Unable to process the request.")
    })
    @PostMapping("/createNewAccount")
    public ResponseEntity<BankResponse> createNewAccount(@RequestBody UserInfo userInfo , @RequestParam AccountType type){
        return newAccountService.createAccount(userInfo , type);
    }
}
