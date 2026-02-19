package com.techlab.ecommerce.controller;

import com.techlab.ecommerce.dto.LoginRequest;
import com.techlab.ecommerce.dto.LoginResponse;
import com.techlab.ecommerce.dto.UserRequest;
import com.techlab.ecommerce.dto.UserResponse;
import com.techlab.ecommerce.service.AuthService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author matias-bruno
 */
@RestController
@RequestMapping("/api/auth")
public class AuthController {
    
    private final AuthService authService;
    
    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/login")
    public LoginResponse login(@RequestBody LoginRequest request) {
        String token = authService.login(request);
        return new LoginResponse(token);
    }
    
    @PostMapping("/register")
    public ResponseEntity<UserResponse> register(@Valid @RequestBody UserRequest request) {
        UserResponse userResponse = authService.register(request);
        return new ResponseEntity(userResponse, HttpStatus.CREATED);
    }
}
