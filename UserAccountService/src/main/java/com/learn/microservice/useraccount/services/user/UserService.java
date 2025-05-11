package com.learn.microservice.useraccount.services.user;

import com.learn.microservice.useraccount.services.user.dto.UserDTO;
import com.learn.microservice.useraccount.services.user.registration.dto.UserResponseDTO;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.List;

public interface UserService extends UserDetailsService {

    UserDTO createUser(UserDTO userDTO) throws Exception;

    UserDTO getUserDetailByEmail(String email);

    List<UserResponseDTO> getAllUsers();

    UserResponseDTO updateUser(Long id, UserDTO userDTO);
}
