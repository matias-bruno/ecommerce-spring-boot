package com.techlab.ecommerce.service;


import com.techlab.ecommerce.dto.LoginRequest;
import com.techlab.ecommerce.dto.UserRequest;
import com.techlab.ecommerce.dto.UserResponse;

/**
 *
 * @author matias-bruno
 */
public interface AuthService {
    public String login(LoginRequest request);
    public UserResponse register(UserRequest request);
}
