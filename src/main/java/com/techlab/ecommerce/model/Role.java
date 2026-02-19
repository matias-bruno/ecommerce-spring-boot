package com.techlab.ecommerce.model;

import org.springframework.security.core.GrantedAuthority;

/**
 *
 * @author matias-bruno
 */

public enum Role implements GrantedAuthority {
    ADMIN,
    USER;

    @Override
    public String getAuthority() {
        return "ROLE_" + name();
    }
}