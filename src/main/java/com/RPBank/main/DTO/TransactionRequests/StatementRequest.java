package com.RPBank.main.DTO.TransactionRequests;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * DTO representing the request for an account statement over a date range.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Schema(description = "Request details for fetching a bank statement within a specific date range.")
public class StatementRequest {

    @Schema(description = "Unique account number", example = "123456789012", required = true)
    private String accountNumber;

    @Schema(description = "Start date of the statement period (yyyy-MM-dd)", example = "2024-01-01", required = true)
    private String startDate;

    @Schema(description = "End date of the statement period (yyyy-MM-dd)", example = "2024-01-31", required = true)
    private String endDate;

    @Override
    public String toString() {
        return String.format(
                "StatementRequest{accountNumber='%s', startDate='%s', endDate='%s'}",
                accountNumber, startDate, endDate
        );
    }
}
