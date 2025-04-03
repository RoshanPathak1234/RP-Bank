package com.RPBank.main.DTO.TransactionRequests;

import jakarta.persistence.Column;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TransferRequest {

    @Column(nullable = false)
    private String sourceAccountNumber;

    @Column(nullable = false)
    private String sourceAccountName;

    @Column(nullable = false)
    private String destinationAccountNumber;

    @Column(nullable = false)
    private String destinationAccountName;

    @Column(nullable = false)
    private BigDecimal amount;
}
