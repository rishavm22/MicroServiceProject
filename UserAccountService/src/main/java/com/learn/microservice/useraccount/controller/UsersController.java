package com.learn.microservice.useraccount.controller;

import com.learn.microservice.useraccount.services.user.UserService;
import com.learn.microservice.useraccount.services.user.auth.AuthRequestDTO;
import com.learn.microservice.useraccount.services.user.auth.AuthResponseDTO;
import com.learn.microservice.useraccount.services.user.auth.AuthService;
import com.learn.microservice.useraccount.services.user.registration.dto.CreateUserRequestDTO;
import com.learn.microservice.useraccount.services.user.dto.UserDTO;
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
@RequestMapping("/users")
public class UsersController {

    @Autowired
    private Environment env;

    @Autowired
    private UserService userService;

    @Autowired
    private AuthService authService;

    @GetMapping("/status/check")
    public String statusCheck() {
        return "UserAccountService is running on port " + env.getProperty("local.server.port");
    }

    @GetMapping("/all")
    public ResponseEntity<List<UserResponseDTO>> getAllUsers() {
        return ResponseEntity.ok(this.userService.getAllUsers());
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<UserResponseDTO> updateUser(@PathVariable("id") Long id, @RequestBody UserDTO userDTO) {
        return ResponseEntity.ok(this.userService.updateUser(id, userDTO));
    }
}
