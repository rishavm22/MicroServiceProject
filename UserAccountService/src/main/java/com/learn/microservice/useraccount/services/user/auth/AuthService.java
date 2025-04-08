package com.learn.microservice.useraccount.services.user.auth;

import com.learn.microservice.useraccount.config.security.CustomUserDetailService;
import com.learn.microservice.useraccount.config.security.jwt.JWTService;
import com.learn.microservice.useraccount.exceptions.UsernameException;
import com.learn.microservice.useraccount.repository.user.UserRepository;
import com.learn.microservice.useraccount.services.user.dto.UserDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    /**
     * The Jwt service.
     */
    private final JWTService jwtService;

    /**
     * The Authentication manager.
     */
    private final AuthenticationManager authenticationManager;

    /**
     * The User repository.
     */
    private final UserRepository userRepository;

    /**
     * The User detail service.
     */
    private final CustomUserDetailService userDetailService;

    @Autowired
    public AuthService(UserRepository userRepository, JWTService jwtService, AuthenticationManager authenticationManager, CustomUserDetailService userDetailService) {
        this.userRepository = userRepository;
        this.jwtService = jwtService;
        this.authenticationManager = authenticationManager;
        this.userDetailService = userDetailService;
    }


    /**
     * Sign in user auth response dto.
     *
     * @param authRequestDTO the auth request dto
     * @return the auth response dto
     */
    public AuthResponseDTO sigInUser(AuthRequestDTO authRequestDTO) {
        var authentication = this.authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(authRequestDTO.getUsername(), authRequestDTO.getPassword()));

        return this.jwtService.generateToken(authRequestDTO.getUsername(), authentication.isAuthenticated());
    }


    /**
     * Forget password response entity.
     *
     * @param user the user
     * @return the response entity
     */
    public ResponseEntity<String> forgetPassword(UserDTO user) {
        UserDetails existingUser = userDetailService.loadUserByUsername(user.getEmail());

        if (existingUser != null) {
            throw new UsernameException(user.getEmail());
        }
        var authentication = this.authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(user.getEmail(), user.getPassword()));

        this.jwtService.generateToken(user.getEmail(), authentication.isAuthenticated());

        // TO DO: send email to reset password
        return ResponseEntity.ok("Reset password link has been sent to your email");
    }
}
