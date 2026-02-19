package com.techlab.ecommerce.dto;

import lombok.Data;

/**
 *
 * @author matias-bruno
 */

@Data
public class UserResponse {
    private Long id;
    private String username;
    private String email;
}
