package com.RPBank.main.DTO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class BankResponse {

    private String responseStatus;

    private String responseMessage;

    private AccountDTO accountDTO;

}
