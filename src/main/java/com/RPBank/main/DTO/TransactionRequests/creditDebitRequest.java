package com.RPBank.main.DTO.TransactionRequests;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class creditDebitRequest {

    private String accountNumber;

    private BigDecimal amount;
}
