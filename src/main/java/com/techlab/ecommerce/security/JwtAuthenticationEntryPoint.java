package com.techlab.ecommerce.security;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.Instant;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.LockedException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

/**
 *
 * @author matias-bruno
 */
@Component
public class JwtAuthenticationEntryPoint implements AuthenticationEntryPoint {

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, 
            AuthenticationException authException) throws IOException, ServletException {
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        response.setContentType("application/json");
        
        String message;
        
        if (authException instanceof BadCredentialsException) {
            message = "Usuario o contraseña incorrectos";
        } else if (authException instanceof DisabledException) {
            message = "La cuenta de usuario fue eliminada";
        } else if (authException instanceof LockedException) {
            message = "La cuenta se encuentra suspendida. Contacte al soporte.";
        } else {
            message = "La autenticación no se pudo realizar";
        }
        
        response.getWriter().write("""
            {
              "status": 401,
              "error": "Unauthorized",
              "message": "%s",
              "path": "%s",
              "timestamp": "%s"
            }
        """.formatted(message, request.getRequestURI(), Instant.now()));
    }

}
