package com.techlab.ecommerce;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.techlab.ecommerce.model.Category;
import com.techlab.ecommerce.model.Product;
import com.techlab.ecommerce.model.Role;
import com.techlab.ecommerce.model.User;
import com.techlab.ecommerce.repository.CategoryRepository;
import com.techlab.ecommerce.repository.ProductRepository;
import com.techlab.ecommerce.repository.RoleRepository;
import com.techlab.ecommerce.repository.UserRepository;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.io.Resource;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

/**
 *
 * @author matias-bruno
 */
@Component
public class DataLoader implements CommandLineRunner {

    private final ProductRepository productoRepository;
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final CategoryRepository categoryRepository;
    private final PasswordEncoder passwordEncoder;
    private final ObjectMapper objectMapper;

    @Value("classpath:data/categories.json")
    private Resource categoriesJson;

    @Value("classpath:data/productos.json")
    private Resource productosJson;

    public DataLoader(ProductRepository productoRepository,
            UserRepository userRepository, RoleRepository roleRepository,
            CategoryRepository categoryRepository, PasswordEncoder passwordEncoder,
            ObjectMapper objectMapper) {
        this.productoRepository = productoRepository;
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.categoryRepository = categoryRepository;
        this.objectMapper = objectMapper;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public void run(String... args) throws Exception {

        loadRoles();

        loadUsers();

        loadCategories();

        loadProducts();

    }

    private void loadRoles() {
        if (this.roleRepository.count() == 0) {
            this.roleRepository.save(new Role("ADMIN"));
            this.roleRepository.save(new Role("USER"));
            System.out.println("ADMIN and USER roles created");
        }
    }

    private void loadUsers() {
        if (userRepository.findByUsername("admin").isEmpty()) {
            User admin = new User();
            admin.setUsername("admin");
            admin.setPassword(passwordEncoder.encode("password-facil")); // Usa una contraseña segura en producción
            admin.setEmail("admin@ecommerce.com");
            HashSet<Role> roles = new HashSet<>();
            Optional<Role> optRole = this.roleRepository.findByName("ADMIN");
            if (optRole.isPresent()) {
                roles.add(optRole.get());
                admin.setRoles(roles);
                userRepository.save(admin);
                System.out.println("ADMIN user created!");
            }
        }

        if (userRepository.findByUsername("newuser").isEmpty()) {
            User newuser = new User();
            newuser.setUsername("newuser");
            newuser.setPassword(passwordEncoder.encode("otro-password")); // Usa una contraseña segura en producción
            newuser.setEmail("newuser@mail.com");
            HashSet<Role> roles = new HashSet<>();
            Optional<Role> optRole = this.roleRepository.findByName("USER");
            if (optRole.isPresent()) {
                roles.add(optRole.get());
                newuser.setRoles(roles);
                userRepository.save(newuser);
                System.out.println("User created!");
            }
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
        if (productoRepository.count() > 0) {
            System.out.println("Productos ya cargados. Saltando precarga.");
            return;
        }

        try (InputStream inputStream = productosJson.getInputStream()) {

            List<Product> productos = objectMapper.readValue(
                    inputStream,
                    new TypeReference<List<Product> >() { }
            );

            productoRepository.saveAll(productos);

            System.out.println("Productos precargados: " + productos.size());
        }
    }
}
