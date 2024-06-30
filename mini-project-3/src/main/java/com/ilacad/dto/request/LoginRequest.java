package com.ilacad.dto.request;

import lombok.*;

import javax.validation.constraints.NotEmpty;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class LoginRequest {
    @NotEmpty(message = "email cannot be empty")
    private String email;
    @NotEmpty(message = "password name cannot be empty")
    private String password;
}
