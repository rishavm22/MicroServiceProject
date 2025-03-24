package com.learn.microservice.useraccount.services.user.registration.dto;

import jakarta.validation.constraints.*;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@RequiredArgsConstructor
@Getter
@Setter
public class CreateUserRequestDTO {

    @NotNull(message = "First name is required")
    @Size(min = 2, message = "First name must be at least 2 characters")
    private String firstName;

    @NotNull(message = "Last name is required")
    @Size(min = 2, message = "Last name must be at least 2 characters")
    private String lastName;

    @NotNull(message = "Password is required")
    @Size(min = 6, max = 20, message = "Password must be between 6 and 20 characters")
    private String password;

    @Email(message = "Email is invalid, please enter a valid email")
    private String email;

}
