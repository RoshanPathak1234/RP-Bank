package com.RPBank.main.DTO;

import com.RPBank.main.utils.enums.Gender;
import com.RPBank.main.utils.enums.Occupations;
import jakarta.persistence.Column;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserInfo {

    @Column(nullable = false)
    private String firstName;

    @Column(nullable = false)
    private String lastName;

    @Column(nullable = false)
    private Gender gender;

    private LocalDate date;

    @Column(nullable = false)
    private Address currentAddress;

    @Column(nullable = false)
    private LocalDate dateOfBirth;

    @Column(nullable = false)
    private Address permanentAddress;

    @Column(nullable = false)
    private String primaryEmail;

    private String secondaryEmail;

    @Column(nullable = false)
    private String primaryPhoneNumber;

    private String secondaryPhoneNumber;

    @Column(nullable = false)
    private Occupations occupation;

}
