package com.learn.microservice.useraccount.entities.user;

import com.learn.microservice.useraccount.enums.Role;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.springframework.security.core.CredentialsContainer;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Entity
@Table(name = "users")
@Getter
@Setter
public class User implements Serializable, UserDetails, CredentialsContainer {

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

    @Column(name = "active", columnDefinition = "boolean default false")
    private Boolean active = false;

    @Column
    @Enumerated(EnumType.STRING)
    private Role roles;



    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
//        return Stream.of(getRoles().name()).map(SimpleGrantedAuthority::new)
//                .collect(Collectors.toList());
        return new ArrayList<>();
    }

    @Override
    public String getPassword() {
        return encryptedPassword;
    }

    @Override
    public String getUsername() {
        return email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    @Override
    public void eraseCredentials() {

    }
}
