package com.techlab.spring1.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import java.time.Instant;
import java.util.Map;

/**
 *
 * @author matias-bruno
 */

@JsonInclude(JsonInclude.Include.NON_NULL)
public record ErrorResponse(
        int status,
        String error,
        String message,
        String path,
        Instant timestamp,
        Map<String, String> validationErrors) {
    // Constructor para errores SIN validación
    public ErrorResponse(int status, String error, String message, String path, Instant timestamp) {
        this(status, error, message, path, timestamp, null);
    }

}
