package com.RPBank.main.utils.utilityClasses;

import com.RPBank.main.utils.enums.AccountType;
import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Embeddable
@Builder
public class AccountInfo {


    @Column(nullable = false)
    private String accountNumber;

    @Column(nullable = false)
    private String accountName;

    @Column(nullable = false)
    private BigDecimal accountBalance;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private AccountType type;

    @Override
    public String toString() {
        return "AccountInfo{" +
                "accountNumber='" + accountNumber + '\'' +
                ", accountName='" + accountName + '\'' +
                ", accountBalance='" + accountBalance + '\'' +
                ", type=" + type +
                '}';
    }
}
