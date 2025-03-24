package com.learn.microservice.useraccount.controller;

import com.learn.microservice.useraccount.services.user.UserService;
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

@RestController
@RequestMapping("/users")
public class UsersController {

    @Autowired
    private Environment env;

    @Autowired
    private UserService userService;

    @GetMapping("/status/check")
    public String statusCheck() {
        return "UserAccountService is running on port " + env.getProperty("local.server.port");
    }

    @PostMapping
    public ResponseEntity<UserResponseDTO> createUser(@Valid @RequestBody CreateUserRequestDTO createUserRequestDTO) {

        var modelMapper = new ModelMapper();
        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
        var userDTO = modelMapper.map(createUserRequestDTO, UserDTO.class);
        userDTO = this.userService.createUser(userDTO);
        var userResponseDTO = modelMapper.map(userDTO, UserResponseDTO.class);

        return new ResponseEntity<>(userResponseDTO, HttpStatus.CREATED);
    }
}
