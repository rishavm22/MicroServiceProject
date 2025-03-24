package com.learn.microservice.useraccount.entities.user;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Entity
@Table(name = "users")
@Getter
@Setter
public class User implements Serializable {

    private static final long serialVersionUID = -2342342342345234L;

    @Id
    @GeneratedValue
    private Long id;

    @Column(nullable = false, length = 50)
    private String firstName;

    @Column(nullable = false, length = 50)
    private String lastName;

    @Column(nullable = false, length = 120, unique = true)
    private String email;

    @Column(nullable = false, unique = true)
    private String encryptedPassword;

    private Boolean active;
}
