package com.techlab.ecommerce.exception;

/**
 *
 * @author matias-bruno
 */
public class ResourceNotFoundException extends RuntimeException {

    public ResourceNotFoundException(String message) {
        super(message);
    }
}
