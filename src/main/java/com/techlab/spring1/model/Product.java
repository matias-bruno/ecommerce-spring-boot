package com.techlab.spring1.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.*;
import org.hibernate.validator.constraints.URL;

/**
 *
 * @author matias-bruno
 */
@Entity
@Table(name="products")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Product {
    // Atributos privados
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @NotBlank(message = "El nombre es obligatorio")
    @Column(unique = true)
    @Size(min = 3, max = 100, message = "El nombre debe ser 3 y 100 caracteres")
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
