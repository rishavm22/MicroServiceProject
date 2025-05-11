package com.learn.microservice.useraccount.services.user;

import com.learn.microservice.useraccount.entities.user.User;
import com.learn.microservice.useraccount.exceptions.UsernameException;
import com.learn.microservice.useraccount.repository.user.UserRepository;
import com.learn.microservice.useraccount.services.user.dto.UserDTO;
import com.learn.microservice.useraccount.services.user.registration.dto.UserResponseDTO;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static com.learn.microservice.useraccount.AppConstants.PASSWORD_EXPR;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    private final BCryptPasswordEncoder passwordEncoder;

    @Autowired
    public UserServiceImpl(UserRepository userRepository, BCryptPasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public UserDTO createUser(UserDTO userDTO) throws Exception {
        userDTO.setUserId();

        // Check if the user already exists
        var existingUser = userRepository.findByEmail(userDTO.getEmail());
        if (existingUser.isPresent()) {
            throw new UsernameException(userDTO.getEmail());
        }

        // validate Password
        this.validatePassword(userDTO.getPassword());

        userDTO.setEncryptedPassword(passwordEncoder.encode(userDTO.getPassword()));
        var modelMapper = new ModelMapper();
        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
        var user = modelMapper.map(userDTO, User.class);
        user = this.userRepository.save(user);
        userDTO = modelMapper.map(user, UserDTO.class);
        return userDTO;
    }

    @Override
    public UserDTO getUserDetailByEmail(String email) {
        var user = this.userRepository.findByEmail(email);
        if (user.isEmpty()) {
            throw new UsernameNotFoundException("User not found");
        }
        return new ModelMapper().map(user.get(), UserDTO.class);
    }

    @Override
    public List<UserResponseDTO> getAllUsers() {
        return this.userRepository.findAll().stream().map(user -> new ModelMapper().map(user, UserResponseDTO.class)).toList();
    }

    @Override
    public UserResponseDTO updateUser(Long id, UserDTO userDTO) {

        var userFromDb = this.userRepository.findById(id);
        if (userFromDb.isEmpty()) {
            throw new UsernameNotFoundException("User not found");
        }
        var user = userFromDb.get();
        user.setFirstName(userDTO.getFirstName());
        user.setLastName(userDTO.getLastName());
        user.setActive(userDTO.getActive());
        user =this.userRepository.save(user);
        return new ModelMapper().map(user, UserResponseDTO.class);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        var user = this.userRepository.findByEmail(username);

        if (user.isEmpty()) {
            throw new UsernameNotFoundException("User not found");
        }
        SimpleGrantedAuthority authority = new SimpleGrantedAuthority("ROLE_USER");
        return new org.springframework.security.core.userdetails.User(user.get().getEmail(), user.get().getEncryptedPassword(), Collections.singleton(authority));
    }

    /**
     * At least 8 characters long
     * Contains at least one lowercase letter
     * Contains at least one uppercase letter
     * Contains at least one digit
     * Contains at least one special character from !@#$%^&*()
     *
     * @param password
     * @throws Exception
     */
    private void validatePassword(String password) throws Exception {
        Pattern pattern = Pattern.compile(PASSWORD_EXPR);
        Matcher matcher = pattern.matcher(password);

        if (!matcher.matches()) {
            throw new Exception("Invalid password");
        }
    }
}
