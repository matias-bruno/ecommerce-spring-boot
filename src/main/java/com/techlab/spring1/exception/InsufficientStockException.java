package com.techlab.spring1.exception;

/**
 *
 * @author matias-bruno
 */
public class InsufficientStockException extends RuntimeException {
    
    public InsufficientStockException(String message) {
        super(message);
    }
}
