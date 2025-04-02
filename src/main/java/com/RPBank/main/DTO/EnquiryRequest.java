package com.RPBank.main.DTO;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class EnquiryRequest {

    private String customerId;

    private String accountNumber;

    private String name;

}
