package com.learn.microservice.useraccount.services.user;

import com.learn.microservice.useraccount.services.user.dto.UserDTO;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface UserService extends UserDetailsService {

    UserDTO createUser(UserDTO userDTO) throws Exception;

    UserDTO getUserDetailByEmail(String email);
}
