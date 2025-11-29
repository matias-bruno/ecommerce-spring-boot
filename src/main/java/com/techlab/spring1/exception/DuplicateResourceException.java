package com.techlab.spring1.exception;

/**
 *
 * @author matias-bruno
 */
public class DuplicateResourceException extends RuntimeException {

    public DuplicateResourceException(String message) {
        super(message);
    }
}
