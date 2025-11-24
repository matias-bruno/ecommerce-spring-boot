package com.techlab.spring1;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.techlab.spring1.model.Product;
import com.techlab.spring1.repository.ProductRepository;
import java.io.InputStream;
import java.util.List;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;

/**
 *
 * @author matias-bruno
 */
@Component
public class ProductDataLoader implements CommandLineRunner {

    private final ProductRepository productoRepository;
    private final ObjectMapper objectMapper;

    @Value("classpath:data/productos.json")
    private Resource productosJson;

    public ProductDataLoader(ProductRepository productoRepository, ObjectMapper objectMapper) {
        this.productoRepository = productoRepository;
        this.objectMapper = objectMapper;
    }

    @Override
    public void run(String... args) throws Exception {

        if (productoRepository.count() > 0) {
            System.out.println("Productos ya cargados. Saltando precarga.");
            return;
        }

        try (InputStream inputStream = productosJson.getInputStream()) {

            List<Product> productos = objectMapper.readValue(
                    inputStream,
                    new TypeReference<List<Product>>() {}
            );

            productoRepository.saveAll(productos);

            System.out.println("Productos precargados: " + productos.size());
        }
    }
}

