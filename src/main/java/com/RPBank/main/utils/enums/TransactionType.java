package com.RPBank.main.utils.enums;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Standard banking transaction types")
public enum TransactionType {

    @Schema(description = "Funds credited to the account")
    CREDIT,

    @Schema(description = "Funds debited from the account")
    DEBIT,

    @Schema(description = "Transfer of funds between accounts within the same bank")
    INTERNAL_TRANSFER,

    @Schema(description = "Transfer of funds to accounts in other banks (e.g., NEFT/RTGS/IMPS)")
    EXTERNAL_TRANSFER,

    @Schema(description = "Automated Teller Machine withdrawal")
    ATM_WITHDRAWAL,

    @Schema(description = "Cash deposit at the bank branch or ATM")
    CASH_DEPOSIT,

    @Schema(description = "Loan EMI or repayment deducted from the account")
    LOAN_PAYMENT,

    @Schema(description = "Utility or service bill payment")
    BILL_PAYMENT,

    @Schema(description = "Cheque issued to another party")
    CHEQUE_ISSUED,

    @Schema(description = "Cheque received and deposited into the account")
    CHEQUE_DEPOSIT,

    @Schema(description = "Interest credited to the account")
    INTEREST_CREDIT,

    @Schema(description = "Bank charges or service fees debited from the account")
    BANK_CHARGES,

    @Schema(description = "Reversal of a previously processed transaction")
    REVERSAL,

    @Schema(description = "Standing instruction transaction processed automatically")
    STANDING_INSTRUCTION,

    @Schema(description = "Failed or rejected transaction")
    FAILED;

    @Override
    public String toString() {
        return name();
    }
}
