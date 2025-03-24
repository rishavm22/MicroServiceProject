package com.learn.microservice.useraccount.services.user.registration.dto;

import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
public class UserResponseDTO {

    private String firstName;
    private String lastName;
    private String email;
}
