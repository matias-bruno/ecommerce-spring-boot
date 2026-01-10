package com.techlab.spring1;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.techlab.spring1.model.Product;
import com.techlab.spring1.model.Role;
import com.techlab.spring1.model.User;
import com.techlab.spring1.repository.ProductRepository;
import com.techlab.spring1.repository.RoleRepository;
import com.techlab.spring1.repository.UserRepository;
import java.io.InputStream;
import java.util.HashSet;
import java.util.List;
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
public class ProductDataLoader implements CommandLineRunner {

    private final ProductRepository productoRepository;
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;
    private final ObjectMapper objectMapper;

    @Value("classpath:data/productos.json")
    private Resource productosJson;

    public ProductDataLoader(ProductRepository productoRepository, 
            UserRepository userRepository, RoleRepository roleRepository,
            PasswordEncoder passwordEncoder, ObjectMapper objectMapper) {
        this.productoRepository = productoRepository;
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.objectMapper = objectMapper;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public void run(String... args) throws Exception {
        
        Role adminRole = this.roleRepository.save(new Role("ADMIN"));
        Role userRole = this.roleRepository.save(new Role("USER"));
        
        if (userRepository.findByUsername("admin").isEmpty()) {
            User admin = new User();
            admin.setUsername("admin");
            admin.setPassword(passwordEncoder.encode("password-facil")); // Usa una contraseña segura en producción
            admin.setEmail("admin@ecommerce.com");
            HashSet<Role> roles = new HashSet<>();
            roles.add(adminRole);
            admin.setRoles(roles);
            userRepository.save(admin);
            System.out.println("ADMIN user created!");
        }

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

