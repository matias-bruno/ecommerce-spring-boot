package com.techlab.ecommerce.dto;

import lombok.Data;

/**
 *
 * @author matias-bruno
 */
@Data
public class LoginRequest {
    private String username;
    private String password;
}
