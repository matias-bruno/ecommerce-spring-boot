package com.techlab.ecommerce.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

/**
 *
 * @author matias-bruno
 */

@Data
public class UserRequest {
    @NotBlank(message = "El nombre de usuario es obligatorio")
    @Size(min = 3, max = 20, message = "El nombre de usuario debe tener entre 3 y 20 caracteres")
    private String username;

    @NotBlank(message = "La contraseña es obligatoria")
    // TODO: exigir que la contraseña cumpla condiciones para ser segura
    private String password;

    @Email(message = "Ingrese un correo electrónico válido")
    @NotBlank(message = "El correo electrónico es obligatorio")
    private String email;
}
