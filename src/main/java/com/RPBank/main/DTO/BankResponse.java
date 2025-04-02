package com.RPBank.main.DTO;

import com.RPBank.main.utils.utilityClasses.AccountInfo;
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

    private AccountInfo accountInfo;

}
