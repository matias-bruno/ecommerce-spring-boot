package com.techlab.spring1.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 *
 * @author matias-bruno
 */

@Data
@AllArgsConstructor
public class LoginResponse {
    private String token;
}
