package com.RPBank.main.DTO.TransactionRequests;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Schema(name = "CreditDebitRequest", description = "Request payload for credit and debit transactions")
public class CreditDebitRequest {

    @Schema(description = "Unique account number of the user", example = "123456789012")
    @NotBlank(message = "Account number cannot be empty")
    private String accountNumber;

    @Schema(description = "Transaction amount (must be positive)", example = "1000.00")
    @NotNull(message = "Amount cannot be null")
    @Min(value = 1, message = "Amount must be at least 1")
    private BigDecimal amount;
}
