package com.techlab.spring1;

import com.techlab.spring1.model.Product;
import com.techlab.spring1.repository.ProductRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class Spring1Application {

    public static void main(String[] args) {
        SpringApplication.run(Spring1Application.class, args);
    }

    // Metodo para tener productos ingresados
    @Bean
    public CommandLineRunner initProducts(ProductRepository productRepository) {
        return args -> {
            // Verificar si la tabla de productos está vacía
            if (productRepository.count() == 0) {
                productRepository.save(new Product("Cafe Morenita", 5000, 5));
                productRepository.save(new Product("Te Virginia", 1000, 10));
                productRepository.save(new Product("Leche Serenisima", 1500, 10));
                productRepository.save(new Product("Lavandina Ayudin", 1000, 10));
                productRepository.save(new Product("Desodorante Axe", 2000, 10));
                System.out.println("5 productos iniciales agregados");
            } else {
                System.out.println("Los productos iniciales ya existen");
            }
        };
    }

}
