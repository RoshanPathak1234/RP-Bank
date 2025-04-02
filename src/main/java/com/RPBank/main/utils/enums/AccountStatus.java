package com.RPBank.main.utils.enums;

public enum AccountStatus {
    ACTIVE,
    INACTIVE,
    SUSPENDED,
    CLOSED,
    PENDING,
    BLOCKED,
    FROZEN,
    RESTRICTED,
    DORMANT,
    UNDER_REVIEW,
    VERIFIED,
    UNVERIFIED,
    LOCKED;

    @Override
    public String toString() {
        return name();
    }
}
