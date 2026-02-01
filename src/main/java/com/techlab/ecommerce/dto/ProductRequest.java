package com.techlab.ecommerce.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.Data;
import org.hibernate.validator.constraints.URL;

/**
 *
 * @author matias-bruno
 */

@Data
public class ProductRequest {
    
    @NotBlank(message = "El nombre es obligatorio")
    @Size(min = 3, max = 50, message = "El nombre debe ser 3 y 50 caracteres")
    @Pattern(regexp = "^[a-zA-Z0-9áéíóúÁÉÍÓÚüÜñÑ\\s'-]+$", 
             message = "El nombre solo debe contener letras, números y espacios y los caracteres especiales '-")
    private String name;
    
    @NotNull(message = "El precio es obligatorio")
    @Positive(message = "El precio debe ser positivo")
    private Double  price;
    
    @NotNull(message = "El stock es obligatorio")
    @Min(value = 0, message = "El stock no puede ser negativo")
    private Integer stock;
    
    @Size(max = 500, message = "La descripcion no puede exceder los 500 caracteres")
    private String description;

    @URL(message = "La url ingresada no es  válida")
    private String imageUrl;
}
