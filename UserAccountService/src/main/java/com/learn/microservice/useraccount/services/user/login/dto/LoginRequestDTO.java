package com.learn.microservice.useraccount.services.user.login.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@RequiredArgsConstructor
@Getter
@Setter
public class LoginRequestDTO {

    @Email(message = "Email is invalid, please enter a valid email")
    private String email;

    @NotNull(message = "Password is required")
    @Size(min = 6, max = 20, message = "Password must be between 6 and 20 characters")
    private String password;
}
