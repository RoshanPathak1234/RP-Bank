package com.RPBank.main.utils.enums;

public enum AccountType {
    SAVINGS,
    CURRENT,
    BUSINESS,
    LOAN,
    FIXED_DEPOSIT,
    RECURRING_DEPOSIT,
    JOINT,
    NRI,
    DEMAT,
    SALARY,
    STUDENT,
    ESCROW;

    @Override
    public String toString() {
        return name();
    }
}
