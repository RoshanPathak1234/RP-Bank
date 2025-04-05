package com.RPBank.main.Controllers;

import com.RPBank.main.Beans.Transaction;
import com.RPBank.main.DTO.BankResponse;
import com.RPBank.main.DTO.TransactionRequests.CreditDebitRequest;
import com.RPBank.main.DTO.TransactionRequests.StatementRequest;
import com.RPBank.main.DTO.TransactionRequests.TransferRequest;
import com.RPBank.main.Services.BankStatement;
import com.RPBank.main.Services.TransactionService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/transaction")
@Tag(name = "Transaction", description = "Operations related to account transactions")
public class TransactionController {

    @Autowired
    private TransactionService transactionService;

    @Autowired
    private BankStatement bankStatement;

    private static final Logger logger = LoggerFactory.getLogger(TransactionController.class);


    @GetMapping("/statement")
    @Operation(
            summary = "Generate Bank Statement",
            description = "Fetches transactions within a date range for a given account.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Statement generated successfully"),
                    @ApiResponse(responseCode = "400", description = "Invalid input parameters"),
                    @ApiResponse(responseCode = "500", description = "Internal server error")
            }
    )
    public List<Transaction> generateBankStatement(
            @Parameter(description = "Bank statement request object containing account number, start and end date", required = true)
            @ModelAttribute StatementRequest request
    ) {
        try {
            logger.info("Generating bank statement for Account: {}, Start Date: {}, End Date: {}",
                    request.getAccountNumber(), request.getStartDate(), request.getEndDate());

            return bankStatement.generateStatement(request);
        } catch (Exception e) {
            logger.error("Error generating statement for Account: {} | Start Date: {} | End Date: {}",
                    request.getAccountNumber(), request.getStartDate(), request.getEndDate(), e);
            return new ArrayList<>();
        }
    }


    @Operation(
            summary = "Credit Amount to Account",
            description = "Credits the specified amount to the user's account."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Amount deposited successfully."),
            @ApiResponse(responseCode = "400", description = "Bad request. Invalid input data."),
            @ApiResponse(responseCode = "401", description = "Unauthorized. User authentication required."),
            @ApiResponse(responseCode = "500", description = "Internal server error. Unable to process the request.")
    })
    @PostMapping("credit")
    public ResponseEntity<BankResponse> creditAmount(@RequestBody CreditDebitRequest request) {
        return transactionService.creditAmount(request);
    }

    @Operation(
            summary = "Debit Amount from Account",
            description = "Deducts a specified amount from the user's account."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Amount debited successfully."),
            @ApiResponse(responseCode = "400", description = "Bad request. Invalid input data."),
            @ApiResponse(responseCode = "401", description = "Unauthorized. User authentication required."),
            @ApiResponse(responseCode = "403", description = "Forbidden. Insufficient balance."),
            @ApiResponse(responseCode = "404", description = "Not found. Account does not exist."),
            @ApiResponse(responseCode = "500", description = "Internal server error. Unable to process the transaction.")
    })
    @PostMapping("debit")
    public ResponseEntity<BankResponse> debitAmount(@RequestBody CreditDebitRequest request) {
        return transactionService.debitAmount(request);
    }

    @Operation(
            summary = "Transfer Funds Between Accounts",
            description = "Transfers a specified amount from one account to another."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Transfer successful."),
            @ApiResponse(responseCode = "400", description = "Bad request. Invalid transfer details."),
            @ApiResponse(responseCode = "401", description = "Unauthorized. Authentication required."),
            @ApiResponse(responseCode = "403", description = "Forbidden. Insufficient funds or access denied."),
            @ApiResponse(responseCode = "404", description = "Not found. One or both accounts do not exist."),
            @ApiResponse(responseCode = "500", description = "Internal server error. Unable to process the transfer.")
    })
    @PostMapping("transfer")
    public ResponseEntity<BankResponse> transfer(@RequestBody TransferRequest request) {
        return transactionService.transfer(request);
    }
}
