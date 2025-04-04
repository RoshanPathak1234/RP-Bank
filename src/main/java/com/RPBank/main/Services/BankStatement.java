package com.RPBank.main.Services;

import com.RPBank.main.Authenticator.ValidateAccountDetails;
import com.RPBank.main.Beans.Transaction;
import com.RPBank.main.DAO.TransactionDAO;
import com.RPBank.main.DAO.UserDAO;
import com.RPBank.main.DTO.ValidationResponse;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class BankStatement {

    @Autowired
    private final TransactionDAO transactionDAO;

    @Autowired
    private final UserDAO userDAO;

    public List<Transaction> generateStatement(String accountNumber, String startDate, String endDate) throws Exception {
        // Validate account number
        ResponseEntity<ValidationResponse> validateAccountResponse = ValidateAccountDetails.validateAccountNumber(userDAO, accountNumber);
        if (!validateAccountResponse.getStatusCode().equals(HttpStatus.OK)) {
            throw new Exception("Invalid account number!");
        }

        // Parse start and end dates
        LocalDate start = LocalDate.parse(startDate, DateTimeFormatter.ISO_DATE);
        LocalDate end = LocalDate.parse(endDate, DateTimeFormatter.ISO_DATE);

        // Fetch transactions within the date range (considering LocalDateTime)
        return transactionDAO.findAll().stream()
                .filter(transaction -> transaction.getAccountNumber().equals(accountNumber))
                .filter(transaction -> {
                    LocalDate transactionDate = transaction.getCreatedAt().toLocalDate(); // Convert LocalDateTime to LocalDate
                    return !transactionDate.isBefore(start) && !transactionDate.isAfter(end);
                })
                .collect(Collectors.toList());
    }
}
