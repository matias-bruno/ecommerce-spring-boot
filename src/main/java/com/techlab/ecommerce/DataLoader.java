package com.techlab.ecommerce;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.techlab.ecommerce.dto.ProductRequest;
import com.techlab.ecommerce.model.Category;
import com.techlab.ecommerce.model.Role;
import com.techlab.ecommerce.model.User;
import com.techlab.ecommerce.repository.CategoryRepository;
import com.techlab.ecommerce.repository.ProductRepository;
import com.techlab.ecommerce.repository.UserRepository;
import com.techlab.ecommerce.service.ProductService;
import java.io.IOException;
import java.io.InputStream;
import java.time.LocalDateTime;
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
public class DataLoader implements CommandLineRunner {

    private final ProductRepository productRepository;
    private final ProductService productService;
    private final UserRepository userRepository;
    private final CategoryRepository categoryRepository;
    private final ObjectMapper objectMapper;

    @Value("classpath:data/categories.json")
    private Resource categoriesJson;

    @Value("classpath:data/products.json")
    private Resource productsJson;

    public DataLoader(ProductRepository productoRepository, ProductService productService,
            UserRepository userRepository, CategoryRepository categoryRepository,
            ObjectMapper objectMapper) {
        this.productRepository = productoRepository;
        this.productService = productService;
        this.userRepository = userRepository;
        this.categoryRepository = categoryRepository;
        this.objectMapper = objectMapper;
    }

    @Override
    public void run(String... args) throws Exception {

        loadUsers();

        loadCategories();

        loadProducts();

    }

    private void loadUsers() {
        if (userRepository.findByUsername("admin").isEmpty()) {
            User admin = new User();
            admin.setUsername("admin");
            // Insertamos el hash de una contraseña que sea fácil de recordar
            // Usar contraseña segura en producción
            admin.setPassword("$2a$10$L9EurcZCkfDWbRUwrIis4edIA40315X.HgK2nutBB5082.I.HlV3C");
            admin.setEmail("admin@ecommerce.com");
            admin.setRole(Role.ADMIN);
            admin.setCreatedAt(LocalDateTime.now());
            userRepository.save(admin);
            System.out.println("¡Creado nuevo usuario admin!");
        }

        if (userRepository.findByUsername("newuser").isEmpty()) {
            User newuser = new User();
            newuser.setUsername("newuser");
            newuser.setPassword("$2a$10$aGJhADJZg0upIw9QHQ7OcegxDpnJu6aLnRbk3khxvdWcaqSAWCVym");
            newuser.setEmail("newuser@mail.com");
            newuser.setRole(Role.USER);
            newuser.setCreatedAt(LocalDateTime.now());
            userRepository.save(newuser);
            System.out.println("¡Creado nuevo usuario!");
        }
    }

    private void loadCategories() {
        if (categoryRepository.count() > 0) {
            System.out.println("Categorias ya cargadas. Saltando precarga.");
            return;
        }
        List<Category> categories;
        try (InputStream inputStream = categoriesJson.getInputStream()) {

            categories = objectMapper.readValue(
                    inputStream,
                    new TypeReference<List<Category> >() { }
            );

            categoryRepository.saveAll(categories);

            System.out.println("Categorias precargados: " + categories.size());
        } catch (Exception ex) {
            System.out.println("Ocurrió un error: " + ex.getMessage());
        }
    }

    private void loadProducts() throws IOException {
        if (productRepository.count() > 0) {
            System.out.println("Productos ya cargados. Saltando precarga.");
            return;
        }

        List<ProductRequest> dtoList;
        try (InputStream inputStream = productsJson.getInputStream()) {

            dtoList = objectMapper.readValue(
                    inputStream,
                    new TypeReference<List<ProductRequest> >() { }
            );

            for (ProductRequest dto : dtoList) {
                productService.saveProduct(dto);
            }
            
            System.out.println("Productos precargados: " + productRepository.count());
            
        } catch (Exception ex) {
            System.out.println("Ocurrió un error: " + ex.getMessage());
        }
    }
}
