package com.RPBank.main.utils.enums;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Types of bank accounts available in RP Bank")
public enum AccountType {

    @Schema(description = "Savings account for individuals with interest benefits.")
    SAVINGS,

    @Schema(description = "Current account for businesses with high transaction limits.")
    CURRENT,

    @Schema(description = "Business account for companies and enterprises.")
    BUSINESS,

    @Schema(description = "Loan account used for credit or borrowing money.")
    LOAN,

    @Schema(description = "Fixed deposit account with a specific tenure and interest rate.")
    FIXED_DEPOSIT,

    @Schema(description = "Recurring deposit account for periodic fixed deposits.")
    RECURRING_DEPOSIT,

    @Schema(description = "Joint account shared by multiple account holders.")
    JOINT,

    @Schema(description = "Non-Resident Indian (NRI) account for overseas customers.")
    NRI,

    @Schema(description = "Demat account for holding securities like stocks and bonds.")
    DEMAT,

    @Schema(description = "Salary account for employees with direct deposit benefits.")
    SALARY,

    @Schema(description = "Student account for educational and financial management.")
    STUDENT,

    @Schema(description = "Escrow account used for conditional transactions.")
    ESCROW;

    @Override
    public String toString() {
        return name();
    }
}
