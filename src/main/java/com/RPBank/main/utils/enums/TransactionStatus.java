package com.RPBank.main.utils.enums;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Standard transaction statuses used in banking systems")
public enum TransactionStatus {

    @Schema(description = "Transaction completed successfully")
    SUCCESS,

    @Schema(description = "Transaction failed during processing")
    FAILED,

    @Schema(description = "Transaction is currently in progress")
    PENDING,

    @Schema(description = "Transaction was cancelled before completion")
    CANCELLED,

    @Schema(description = "Transaction has been reversed")
    REVERSED,

    @Schema(description = "Transaction is on hold due to validation or manual approval")
    ON_HOLD,

    @Schema(description = "Transaction is under manual review or investigation")
    UNDER_REVIEW;

    @Override
    public String toString() {
        return name();
    }
}
