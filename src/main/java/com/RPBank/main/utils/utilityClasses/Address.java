package com.RPBank.main.utils.utilityClasses;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Embeddable
public class Address {

    @Column(nullable = false)
    private String houseNumber;

    @Column(nullable = false)
    private String area;

    private String landMark;

    @Column(nullable = false)
    private String PO;

    @Column(nullable = false)
    private String PS;

    @Column(nullable = false)
    private String city;

    @Column(nullable = false)
    private String state;

    @Column(nullable = false)
    private String country;

    @Column(nullable = false)
    private String pinCode;

    @Override
    public String toString() {
        return "Address{" +
                "houseNumber=" + houseNumber +
                ", area='" + area + '\'' +
                ", landMark='" + landMark + '\'' +
                ", PO='" + PO + '\'' +
                ", PS='" + PS + '\'' +
                ", city='" + city + '\'' +
                ", state='" + state + '\'' +
                ", country='" + country + '\'' +
                ", pinCode=" + pinCode +
                '}';
    }
}
