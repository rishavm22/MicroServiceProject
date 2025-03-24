package com.learn.microservice.useraccount.services.user.dto;

import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.io.Serializable;

@Data
@RequiredArgsConstructor
public class UserDTO implements Serializable  {

    private static final long serialVersionUID = 121234324L;

    private String id;

    private String firstName;

    private String lastName;

    private String email;

    private String password;

    private String encryptedPassword;

    public void setUserId(String userId) {
        this.id = userId;
    }
}
