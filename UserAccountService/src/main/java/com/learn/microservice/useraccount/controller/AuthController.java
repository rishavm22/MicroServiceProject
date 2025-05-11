package com.learn.microservice.useraccount.controller;

import com.learn.microservice.useraccount.services.user.UserService;
import com.learn.microservice.useraccount.services.user.auth.AuthRequestDTO;
import com.learn.microservice.useraccount.services.user.auth.AuthResponseDTO;
import com.learn.microservice.useraccount.services.user.auth.AuthService;
import com.learn.microservice.useraccount.services.user.dto.UserDTO;
import com.learn.microservice.useraccount.services.user.registration.dto.CreateUserRequestDTO;
import com.learn.microservice.useraccount.services.user.registration.dto.UserResponseDTO;
import jakarta.validation.Valid;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users/auth")
public class AuthController {

    @Autowired
    private UserService userService;

    @Autowired
    private AuthService authService;

    @GetMapping("/users")
    public ResponseEntity<List<UserResponseDTO>> getAllUsers() {
        return ResponseEntity.ok(this.userService.getAllUsers());
    }

    @PostMapping("/reg")
    public ResponseEntity<UserResponseDTO> createUser(@Valid @RequestBody CreateUserRequestDTO createUserRequestDTO) throws Exception {

        var modelMapper = new ModelMapper();
        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
        var userDTO = modelMapper.map(createUserRequestDTO, UserDTO.class);
        userDTO = this.userService.createUser(userDTO);
        var userResponseDTO = modelMapper.map(userDTO, UserResponseDTO.class);

        return new ResponseEntity<>(userResponseDTO, HttpStatus.CREATED);
    }

    @PostMapping("/signIn")
    public ResponseEntity<AuthResponseDTO> getAuthenticated(@RequestBody AuthRequestDTO authRequestDTO) {

        var response = this.authService.sigInUser(authRequestDTO);
        return ResponseEntity.ok(response);

    }

    @PostMapping("/forgot-password")
    public ResponseEntity<String> forgotPassword(@RequestBody UserDTO user) {
        return this.authService.forgetPassword(user);
    }
}
