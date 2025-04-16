package com.RPBank.main.Models;

import com.RPBank.main.DTO.AccountDTO;
import com.RPBank.main.DTO.webDTO.LoginCredentials;
import com.RPBank.main.utils.enums.AccountStatus;
import com.RPBank.main.utils.enums.Gender;
import com.RPBank.main.utils.enums.Occupations;
import com.RPBank.main.DTO.Address;
import com.RPBank.main.utils.enums.Role;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "users")
@Schema(name = "User", description = "Represents a bank user with personal and account details.")
public class User implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Schema(description = "Unique customer ID", example = "1001")
    private long customerId;

    @Embedded
    @Schema(description = "Account details of the user")
    private AccountDTO accountDTO;

    @Schema(description = "User's first name", example = "John")
    @Column(nullable = false)
    private String firstName;

    @Schema(description = "User's last name", example = "Doe")
    @Column(nullable = false)
    private String lastName;

    @Enumerated(EnumType.STRING)
    @Schema(description = "User's gender", example = "MALE")
    @Column(nullable = false)
    private Gender gender;

    @Schema(description = "User's date of birth", example = "1990-05-15")
    @Column(nullable = false)
    private LocalDate dateOfBirth;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    @Schema(description = "Role of user", example = "CUSTOMER")
    private Role role;

    @Embedded
    private LoginCredentials loginCredentials;

    @Embedded
    @Schema(description = "User's current address")
    @Column(nullable = false)
    private Address currentAddress;

    @Embedded
    @AttributeOverrides({
            @AttributeOverride(name = "houseNumber", column = @Column(name = "permanent_houseNumber")),
            @AttributeOverride(name = "area", column = @Column(name = "permanent_area")),
            @AttributeOverride(name = "landMark", column = @Column(name = "permanent_landMark")),
            @AttributeOverride(name = "PO", column = @Column(name = "permanent_PO")),
            @AttributeOverride(name = "PS", column = @Column(name = "permanent_PS")),
            @AttributeOverride(name = "city", column = @Column(name = "permanent_city")),
            @AttributeOverride(name = "state", column = @Column(name = "permanent_state")),
            @AttributeOverride(name = "country", column = @Column(name = "permanent_country")),
            @AttributeOverride(name = "pinCode", column = @Column(name = "permanent_pinCode"))
    })
    @Schema(description = "User's permanent address")
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

    @Enumerated(EnumType.STRING)
    @Schema(description = "User's occupation", example = "ENGINEER")
    private Occupations occupation;

    @Enumerated(EnumType.STRING)
    @Schema(description = "Current status of the account", example = "ACTIVE")
    @Column(nullable = false)
    private AccountStatus status;

    @CreationTimestamp
    @Schema(description = "Date when the account was created", example = "2025-04-04T10:15:30")
    private LocalDateTime dateOfCreation;

    @UpdateTimestamp
    @Schema(description = "Last update timestamp", example = "2025-04-04T12:00:00")
    private LocalDateTime updationTime;

    @Override
    public String toString() {
        return "User {" +
                "\n\tcustomerId=" + customerId +
                ",\n\taccountDTO=" + accountDTO +
                ",\n\tfirstName='" + firstName + '\'' +
                ",\n\tlastName='" + lastName + '\'' +
                ",\n\tgender=" + gender +
                ",\n\tdateOfBirth=" + dateOfBirth +
                ",\n\tcurrentAddress=" + currentAddress +
                ",\n\tpermanentAddress=" + permanentAddress +
                ",\n\tprimaryEmail='" + primaryEmail + '\'' +
                ",\n\tsecondaryEmail='" + secondaryEmail + '\'' +
                ",\n\tprimaryPhoneNumber='" + primaryPhoneNumber + '\'' +
                ",\n\tsecondaryPhoneNumber='" + secondaryPhoneNumber + '\'' +
                ",\n\toccupation=" + occupation +
                ",\n\tstatus=" + status +
                ",\n\tdateOfCreation=" + dateOfCreation +
                ",\n\tupdationTime=" + updationTime +
                "\n}";
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority(role.name()));
    }

    @Override
    public String getPassword() {
        return loginCredentials.getPassword();
    }

    @Override
    public String getUsername() {
        return loginCredentials.getUserName();
    }
}
