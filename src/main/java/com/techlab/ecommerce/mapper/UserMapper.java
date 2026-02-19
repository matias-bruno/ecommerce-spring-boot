package com.techlab.ecommerce.mapper;

import com.techlab.ecommerce.dto.UserResponse;
import com.techlab.ecommerce.model.User;

/**
 *
 * @author matias-bruno
 */
public class UserMapper {
    public static UserResponse toDto(User user) {
        UserResponse userResponse = new UserResponse();
        userResponse.setUsername(user.getUsername());
        userResponse.setEmail(user.getEmail());
        userResponse.setId(user.getId());
        return userResponse;
    }
}
