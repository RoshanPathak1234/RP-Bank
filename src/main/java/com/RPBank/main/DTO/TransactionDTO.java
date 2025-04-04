package com.RPBank.main.DTO;

import com.RPBank.main.utils.enums.TransactionType;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.Column;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TransactionDTO {

    @Enumerated(EnumType.STRING)
    @Schema(description = "Type of transaction (CREDIT or DEBIT)", example = "CREDIT")
    @Column(nullable = false)
    private TransactionType transactionType;

    @Schema(description = "Account number associated with the transaction", example = "1234567890")
    @Column(nullable = false)
    private String accountNumber;

    @Schema(description = "Amount involved in the transaction", example = "5000.00")
    @Column(nullable = false)
    private BigDecimal amount;

}
