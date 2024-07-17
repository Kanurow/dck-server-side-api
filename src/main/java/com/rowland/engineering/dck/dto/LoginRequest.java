package com.rowland.engineering.dck.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class LoginRequest {
    @NotBlank
    private String emailOrPhoneNumber;

    @NotBlank
    private String password;
}
