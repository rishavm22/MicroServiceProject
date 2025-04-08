package com.learn.microservice.useraccount.services.user.auth;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;
import java.io.Serializable;
import java.time.LocalDateTime;


@AllArgsConstructor
@XmlRootElement(name = "AuthenticationResponse")
@XmlType
@Getter
@Setter
public class AuthResponseDTO implements Serializable {

    private final String token;
    private final LocalDateTime expiresIn;
    private final String tokenType;
    private final String refreshToken;
    private final LocalDateTime refreshExpiresIn;

    public AuthResponseDTO(String token, String refreshToken) {
        this.token = token;
        this.refreshToken = refreshToken;
        this.expiresIn = LocalDateTime.now().plusHours(1);
        this.refreshExpiresIn = LocalDateTime.now().plusHours(12);
        this.tokenType = "bearer";
    }
}
