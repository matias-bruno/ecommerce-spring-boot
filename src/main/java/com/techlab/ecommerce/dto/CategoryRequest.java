package com.techlab.ecommerce.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Data;

/**
 *
 * @author matias-bruno
 */

@Data
public class CategoryRequest {
    @NotBlank(message = "El nombre de categoria es obligatorio.")
    @Size(min = 3, max = 20, message = "El nombre debe tener entre 3 y 20 carácteres.")
    @Pattern(regexp = "^[a-zA-Z0-9áéíóúÁÉÍÓÚüÜñÑ\\s]+$", 
             message = "El nombre solo debe contener letras, números y espacios.")
    private String name;
}
