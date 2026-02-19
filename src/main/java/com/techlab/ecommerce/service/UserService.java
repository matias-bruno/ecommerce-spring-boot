package com.techlab.ecommerce.service;

import com.techlab.ecommerce.dto.UserRequest;
import com.techlab.ecommerce.dto.UserResponse;
import java.util.List;

/**
 *
 * @author matias-bruno
 */
public interface UserService {
    public UserResponse createUser(UserRequest userRequest);
    public UserResponse updateUser(UserRequest userRequest, Long id);
    public List<UserResponse> getUsers();
    public void deleteUser(Long id);
}
