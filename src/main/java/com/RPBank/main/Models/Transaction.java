package com.RPBank.main.Models;

import com.RPBank.main.utils.enums.TransactionStatus;
import com.RPBank.main.utils.enums.TransactionType;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "transactions")
@Schema(
        name = "Transaction",
        description = "Represents a single transaction in a user's account."
)
public class Transaction {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Schema(description = "Unique identifier for the transaction", example = "c8a3f51e-d3f1-4e91-97cd-24fc81b38c3e")
    @Column(nullable = false)
    private String transactionId;

    @Enumerated(EnumType.STRING)
    @Schema(description = "Type of transaction (CREDIT or DEBIT)", example = "CREDIT")
    @Column(nullable = false)
    private TransactionType transactionType;

    @Schema(description = "Account number associated with the transaction", example = "1234567890")
    @Column(nullable = false)
    private String accountNumber;

    @Schema(description = "Amount involved in the transaction", example = "5000.00")
    @Column(nullable = false)
    private BigDecimal amount;

    @Enumerated(EnumType.STRING)
    @Schema(description = "Status of the transaction", example = "SUCCESS")
    @Column(nullable = false)
    private TransactionStatus status;

    @CreationTimestamp
    @Schema(description = "Timestamp when the transaction was created", example = "2025-04-04T14:30:00")
    private LocalDateTime createdAt;

    @UpdateTimestamp
    @Schema(description = "Timestamp when the transaction was last updated", example = "2025-04-04T15:45:00")
    private LocalDateTime updatedAt;
}
