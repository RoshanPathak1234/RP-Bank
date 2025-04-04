package com.RPBank.main.utils.enums;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Possible statuses for a bank account")
public enum AccountStatus {

    @Schema(description = "Account is active and fully operational.")
    ACTIVE,

    @Schema(description = "Account is inactive but can be reactivated.")
    INACTIVE,

    @Schema(description = "Account is temporarily suspended due to security or compliance reasons.")
    SUSPENDED,

    @Schema(description = "Account is permanently closed and cannot be reactivated.")
    CLOSED,

    @Schema(description = "Account is pending verification or approval.")
    PENDING,

    @Schema(description = "Account is blocked due to suspected fraud or violation of terms.")
    BLOCKED,

    @Schema(description = "Account funds are frozen due to legal or regulatory reasons.")
    FROZEN,

    @Schema(description = "Account has restricted functionalities, such as withdrawal limits.")
    RESTRICTED,

    @Schema(description = "Account has been dormant due to inactivity for an extended period.")
    DORMANT,

    @Schema(description = "Account is under review for compliance or fraud investigation.")
    UNDER_REVIEW,

    @Schema(description = "Account has been successfully verified.")
    VERIFIED,

    @Schema(description = "Account verification is pending or unsuccessful.")
    UNVERIFIED,

    @Schema(description = "Account is locked due to multiple failed login attempts.")
    LOCKED;

    @Override
    public String toString() {
        return name();
    }
}
