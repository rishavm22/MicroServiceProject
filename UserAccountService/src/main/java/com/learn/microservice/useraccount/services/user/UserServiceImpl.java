package com.learn.microservice.useraccount.services.user;

import com.learn.microservice.useraccount.entities.user.User;
import com.learn.microservice.useraccount.repository.user.UserRepository;
import com.learn.microservice.useraccount.services.user.dto.UserDTO;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.UUID;

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
    public UserDTO createUser(UserDTO userDTO) {
        userDTO.setUserId(UUID.randomUUID().toString());
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
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        var user = this.userRepository.findByEmail(username);

        if (user.isEmpty()) {
            throw new UsernameNotFoundException("User not found");
        }
        SimpleGrantedAuthority authority = new SimpleGrantedAuthority("ROLE_USER");
        return new org.springframework.security.core.userdetails.User(user.get().getEmail(), user.get().getEncryptedPassword(), Collections.singleton(authority));
    }
}
