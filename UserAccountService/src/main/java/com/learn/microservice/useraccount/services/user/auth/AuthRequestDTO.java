package com.learn.microservice.useraccount.services.user.auth;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AuthRequestDTO {

    private String username;

    private String password;
}
