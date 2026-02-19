package com.techlab.ecommerce.service.impl;

import com.techlab.ecommerce.dto.LoginRequest;
import com.techlab.ecommerce.dto.UserRequest;
import com.techlab.ecommerce.dto.UserResponse;
import com.techlab.ecommerce.security.JwtUtil;
import com.techlab.ecommerce.service.AuthService;
import com.techlab.ecommerce.service.UserService;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

/**
 *
 * @author matias-bruno
 */

@Service
public class AuthServiceImpl implements AuthService {
    
    private final AuthenticationManager authManager;
    private final JwtUtil jwtUtil;
    private final UserService userService;

    public AuthServiceImpl(AuthenticationManager am, JwtUtil jwtUtil,
            UserService userService) {
        this.authManager = am;
        this.jwtUtil = jwtUtil;
        this.userService = userService;
    }
    
    @Override
    public String login(LoginRequest request) {

        Authentication authentication = authManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword())
        );

        return jwtUtil.generateToken(authentication);
    }
    
    @Override
    public UserResponse register(UserRequest request) {
        return userService.createUser(request);
    }
}
