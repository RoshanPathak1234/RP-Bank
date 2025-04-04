package com.RPBank.main.DTO;

//import com.RPBank.main.utils.validation.ValidEnquiryRequest;
import com.RPBank.main.Authenticator.ValidEnquiryRequest;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ValidEnquiryRequest
@Schema(name = "EnquiryRequest", description = "Request for account enquiry based on customer ID or account number")
public class EnquiryRequest {

    @Schema(description = "Unique customer ID", example = "123456")
    private String customerId;

    @Schema(description = "Account number associated with the user", example = "123456789012")
    private String accountNumber;

    @Schema(description = "Full name of the account holder (optional)", example = "John Doe")
    private String name;
}
