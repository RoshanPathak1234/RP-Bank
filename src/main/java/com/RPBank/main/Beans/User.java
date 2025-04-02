package com.RPBank.main.Beans;


import com.RPBank.main.utils.enums.AccountStatus;
import com.RPBank.main.utils.enums.Gender;
import com.RPBank.main.utils.enums.Occupations;
import com.RPBank.main.utils.utilityClasses.AccountInfo;
import com.RPBank.main.utils.utilityClasses.Address;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name="users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private long customerId;

    @Embedded
    private AccountInfo accountInfo;

    @Column(nullable = false)
    private String firstName;

    @Column(nullable = false)
    private String lastName;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Gender gender;

    @Column(nullable = false)
    private LocalDate dateOfBirth;

    @Embedded
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
    @Column(nullable = false)
    private Address permanentAddress;

    @Column(nullable = false)
    private String primaryEmail;


    private String secondaryEmail;

    @Column(nullable = false)
    private String primaryPhoneNumber;


    private String secondaryPhoneNumber;

    @Enumerated(EnumType.STRING)
    private Occupations occupation;


    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private AccountStatus status;

    @CreationTimestamp
    private LocalDateTime dateOfCreation;

    @UpdateTimestamp
    private LocalDateTime updationTime;

    @Override
    public String toString() {
        return "User{" +
                "id=" + customerId +
                ", accountInfo=" + accountInfo +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", gender=" + gender +
                ", dateOfBirth=" + dateOfBirth +
                ", currentAddress=" + currentAddress +
                ", permanentAddress=" + permanentAddress +
                ", primaryEmail='" + primaryEmail + '\'' +
                ", secondaryEmail='" + secondaryEmail + '\'' +
                ", primaryPhoneNumber='" + primaryPhoneNumber + '\'' +
                ", secondaryPhoneNumber='" + secondaryPhoneNumber + '\'' +
                ", occupation=" + occupation +
                ", status='" + status + '\'' +
                ", dateOfCreation=" + dateOfCreation +
                ", updationTime=" + updationTime +
                '}';
    }
}
