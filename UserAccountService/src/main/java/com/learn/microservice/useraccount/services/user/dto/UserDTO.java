package com.learn.microservice.useraccount.services.user.dto;

import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.io.Serializable;
import java.util.Random;

@Data
@RequiredArgsConstructor
public class UserDTO implements Serializable  {

    private static final long serialVersionUID = 121234324L;

    private Long id;

    private String firstName;

    private String lastName;

    private String email;

    private String password;

    private String encryptedPassword;

    public void setUserId() {
        this.id = Math.abs(new Random().nextLong());
    }
}
