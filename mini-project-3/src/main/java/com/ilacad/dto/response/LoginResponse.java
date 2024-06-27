package com.ilacad.dto.response;

import com.ilacad.enums.Role;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class LoginResponse {
    private String firstName;
    private String lastName;
    private String email;
    private Role role;
}
