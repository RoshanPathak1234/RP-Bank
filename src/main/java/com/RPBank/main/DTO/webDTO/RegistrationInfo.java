package com.RPBank.main.DTO.webDTO;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Schema(description = "User registration data required for creating a new account")
public class RegistrationInfo {

    @Schema(description = "Primary email of the user", example = "user@example.com", required = true)
    private String primaryEmail;

    @Schema(description = "Primary phone number of the user", example = "+919876543210", required = true)
    private String primaryPhone;

    @Schema(description = "Desired username", example = "john_doe", required = true)
    private String userName;

    @Schema(description = "Account password", example = "StrongPassword123", required = true)
    private String password;
}
