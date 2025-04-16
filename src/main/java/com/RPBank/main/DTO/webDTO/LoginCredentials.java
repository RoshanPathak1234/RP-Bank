package com.RPBank.main.DTO.webDTO;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Embeddable
public class LoginCredentials {

    @Column(nullable = false)
    private String userName;

    @Column(nullable = false)
    private String password;
}
