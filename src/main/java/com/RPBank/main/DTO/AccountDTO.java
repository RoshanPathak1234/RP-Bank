package com.RPBank.main.DTO;

import com.RPBank.main.utils.enums.AccountType;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.*;

import java.math.BigDecimal;

/**
 * Represents embedded account information within an entity.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Embeddable
@Schema(name = "AccountDTO", description = "Holds account-related details including number, name, balance, and type.")
public class AccountDTO {

    @Schema(
            description = "Unique identifier for the account",
            example = "1234567890123456"
    )
    @Column(nullable = false, unique = true)
    private String accountNumber;

    @Schema(
            description = "Account holder's full name",
            example = "John Doe"
    )
    @Column(nullable = false)
    private String accountName;

    @Schema(
            description = "Current balance in the account",
            example = "15000.75"
    )
    @Column(nullable = false)
    private BigDecimal accountBalance;

    @Schema(
            description = "Type of account",
            example = "SAVINGS"
    )
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private AccountType type;

    @Override
    public String toString() {
        return String.format(
                "AccountDTO { accountNumber='%s', accountName='%s', accountBalance='%s', type='%s' }",
                accountNumber, accountName, accountBalance, type
        );
    }
}
