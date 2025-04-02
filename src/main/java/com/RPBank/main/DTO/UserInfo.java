package com.RPBank.main.DTO;

import com.RPBank.main.utils.utilityClasses.Address;
import com.RPBank.main.utils.enums.Gender;
import com.RPBank.main.utils.enums.Occupations;
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

    private String firstName;

    private String lastName;

    private Gender gender;

    private LocalDate date;

    private Address currentAddress;

    private LocalDate dateOfBirth;

    private Address permanentAddress;

    private String primaryEmail;

    private String secondaryEmail;

    private String primaryPhoneNumber;

    private String secondaryPhoneNumber;

    private Occupations occupation;

}
