package com.learn.microservice.useraccount.config.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.learn.microservice.useraccount.services.user.UserService;
import com.learn.microservice.useraccount.services.user.login.dto.LoginRequestDTO;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.core.env.Environment;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.crypto.SecretKey;
import java.io.IOException;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Base64;
import java.util.Date;
import java.util.Objects;

public class AuthenticationFilter extends UsernamePasswordAuthenticationFilter {

    private UserService userService;

    private Environment env;

    public AuthenticationFilter(AuthenticationManager authenticationManager, UserService userService, Environment env) {
        super(authenticationManager);
        this.userService = userService;
        this.env = env;
    }

    public AuthenticationFilter(AuthenticationManager authenticationManager) {
        super(authenticationManager);
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response)
            throws AuthenticationException {
        try {
            LoginRequestDTO loginRequestDTO = new ObjectMapper().readValue(request.getInputStream(),
                    LoginRequestDTO.class);
            return getAuthenticationManager().authenticate(
                    new UsernamePasswordAuthenticationToken(
                            loginRequestDTO.getEmail(),
                            loginRequestDTO.getPassword(),
                            new ArrayList<>()));

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response,
                                            FilterChain chain, Authentication authResult) throws IOException, ServletException {
        var userName = ((User) authResult.getPrincipal()).getUsername();
        var userDTO = this.userService.getUserDetailByEmail(userName);

        String tokenSecret = env.getProperty("token.secret");
        byte[] secretKeyBytes = Base64.getEncoder().encode(tokenSecret.getBytes());
        SecretKey secretKey = Keys.hmacShaKeyFor(secretKeyBytes);

        Instant now = Instant.now();

        String token = Jwts.builder()
                .subject(userDTO.getId())
                .expiration(Date.from(now.plusMillis(Long.parseLong(Objects.requireNonNull(env.getProperty("token.expiration_time"))))))
                .issuedAt(Date.from(now))
                .signWith(secretKey)
                .compact();

        response.addHeader("token", token);
        response.addHeader("userId", userDTO.getId());
    }
}
