package com.RPBank.main.DTO;

import com.RPBank.main.DTO.webDTO.LoginCredentials;
import com.RPBank.main.utils.enums.Gender;
import com.RPBank.main.utils.enums.Occupations;
import com.RPBank.main.utils.enums.Role;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.Column;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.*;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Schema(name = "UserInfo", description = "Represents the user details required for account creation and transactions.")
public class UserInfo {

    @Schema(description = "User's first name", example = "John")
    @Column(nullable = false)
    private String firstName;

    @Schema(description = "User's last name", example = "Doe")
    @Column(nullable = false)
    private String lastName;

    @Schema(description = "User's gender", example = "MALE")
    @Column(nullable = false)
    private Gender gender;

    @Schema(description = "Account creation date", example = "2025-04-04")
    private LocalDate date;

    @Schema(description = "User's current address details")
    @Column(nullable = false)
    private Address currentAddress;

    @Schema(description = "User's date of birth", example = "1990-05-15")
    @Column(nullable = false)
    private LocalDate dateOfBirth;

    @Schema(description = "User's permanent address details")
    @Column(nullable = false)
    private Address permanentAddress;

    @Schema(description = "Primary email ID of the user", example = "john.doe@example.com")
    @Column(nullable = false)
    private String primaryEmail;

    @Schema(description = "Secondary email ID (optional)", example = "john.backup@example.com")
    private String secondaryEmail;

    @Schema(description = "Primary phone number of the user", example = "+91-9876543210")
    @Column(nullable = false)
    private String primaryPhoneNumber;

    @Schema(description = "Secondary phone number (optional)", example = "+91-9123456789")
    private String secondaryPhoneNumber;

    @Schema(description = "User's occupation", example = "ENGINEER")
    @Column(nullable = false)
    private Occupations occupation;

    @Enumerated(EnumType.STRING)
    @Schema(description = "User's Role", example = "CUSTOMER")
    @Column(nullable = false)
    private Role role;

    @Column(nullable = false)
    private LoginCredentials loginCredentials;
}
