package com.techlab.ecommerce.exception;

import com.techlab.ecommerce.dto.ErrorResponse;
import jakarta.persistence.OptimisticLockException;
import jakarta.servlet.http.HttpServletRequest;
import java.time.Instant;
import java.util.HashMap;
import java.util.Map;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 *
 * @author matias-bruno
 */
@RestControllerAdvice
public class GlobalExceptionHandler {
    
    @ExceptionHandler(DataIntegrityViolationException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponse handleDataIntegrityViolation(DataIntegrityViolationException ex,
            HttpServletRequest request) {
        return new ErrorResponse(
            400,
            "Bad Request",
            "La operación fue rechazada por la base de datos",
            request.getRequestURI(),
            Instant.now()
        );
    }

    // Maneja @Valid / @RequestBody errors (HTTP 400)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponse handleValidationExceptions(
            MethodArgumentNotValidException ex,
            HttpServletRequest request) {
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getAllErrors().forEach(error -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errors.put(fieldName, errorMessage);
        });
        return new ErrorResponse(
                400,
                "Bad Request",
                "Errores de validación",
                request.getRequestURI(),
                Instant.now(),
                errors
        );
    }

    @ExceptionHandler(ResourceNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ErrorResponse handleNotFound(ResourceNotFoundException ex,
            HttpServletRequest request) {
        return new ErrorResponse(
                404,
                "Not Found",
                ex.getMessage(),
                request.getRequestURI(),
                Instant.now()
        );
    }

    @ExceptionHandler(DuplicateResourceException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    public ErrorResponse handleConflict(DuplicateResourceException ex,
            HttpServletRequest request) {
        return new ErrorResponse(
                409,
                "Conflict",
                ex.getMessage(),
                request.getRequestURI(),
                Instant.now()
        );
    }

    @ExceptionHandler(InsufficientStockException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponse handleInsufficientStock(InsufficientStockException ex,
            HttpServletRequest request) {
        return new ErrorResponse(
                404,
                "Bad Request",
                ex.getMessage(),
                request.getRequestURI(),
                Instant.now()
        );
    }

    @ExceptionHandler(OptimisticLockException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    public ErrorResponse handleOptimisticLockException(OptimisticLockException ex,
            HttpServletRequest request) {
        return new ErrorResponse(
                409,
                "Conflict",
                "El producto fue actualizado por otro usuario, intente nuevamente.",
                request.getRequestURI(),
                Instant.now()
        );
    }
    
    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ErrorResponse handleGenericException(Exception ex,
            HttpServletRequest request) throws Exception {
        if (ex instanceof AuthenticationException ||
            ex instanceof AccessDeniedException) {
            throw ex; // No se manejan excepciones de seguridad desde aquí
        }
        return new ErrorResponse(
                500,
                "Internal Server Error",
                ex.getMessage(),
                request.getRequestURI(),
                Instant.now()
        );
    }

}
