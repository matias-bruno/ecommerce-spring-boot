package com.techlab.ecommerce.service.impl;

import com.techlab.ecommerce.dto.UserRequest;
import com.techlab.ecommerce.dto.UserResponse;
import com.techlab.ecommerce.exception.DuplicateResourceException;
import com.techlab.ecommerce.mapper.UserMapper;
import com.techlab.ecommerce.model.Role;
import com.techlab.ecommerce.model.User;
import com.techlab.ecommerce.repository.UserRepository;
import com.techlab.ecommerce.service.UserService;
import java.time.LocalDateTime;
import java.util.List;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

/**
 *
 * @author matias-bruno
 */

@Service
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    
    public UserServiceImpl(UserRepository userRepository,
            PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public UserResponse createUser(UserRequest userRequest) {
        // 1. Verificar si el username ya existe
        if (userRepository.existsByUsername(userRequest.getUsername())) {
            throw new DuplicateResourceException("Usuario " + userRequest.getUsername() + " ya existe.");
        }

        // 2. Verificar si el email ya existe
        if (userRepository.existsByEmail(userRequest.getEmail())) {
            throw new DuplicateResourceException("Usuario con email " + userRequest.getEmail() + " ya existe.");
        }
        
        User user = new User();
        user.setUsername(userRequest.getUsername());
        user.setEmail(userRequest.getEmail());
        user.setPassword(passwordEncoder.encode(userRequest.getPassword()));
        user.setRole(Role.USER);
        user.setCreatedAt(LocalDateTime.now());

        userRepository.save(user);
        
        return UserMapper.toDto(user);
    }

    @Override
    public UserResponse updateUser(UserRequest userRequest, Long id) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public List<UserResponse> getUsers() {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public void deleteUser(Long id) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

}
