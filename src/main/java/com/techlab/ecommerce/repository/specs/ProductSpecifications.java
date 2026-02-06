package com.techlab.ecommerce.repository.specs;

import com.techlab.ecommerce.model.Product;
import org.springframework.data.jpa.domain.Specification;

/**
 *
 * @author matias-bruno
 */
public class ProductSpecifications {

    public static Specification<Product> nameLike(String name) {
        return (root, query, cb) -> {
            if (name == null || name.isBlank()) return null;
            return cb.like(cb.lower(root.get("name")), "%" + name.toLowerCase() + "%");
        };
    }
    
    public static Specification<Product> hasCategorySlug(String slug) {
        return (root, query, cb) -> {
            if (slug == null || slug.isBlank()) return null;
            return cb.equal(root.get("category").get("slug"), slug);
        };
    }
    
    public static Specification<Product> priceGreaterThanOrEqualTo(Double minPrice) {
        return (root, query, cb) -> {
            if (minPrice == null) return null;
            return cb.greaterThanOrEqualTo(root.get("price"), minPrice);
        };
    }

    public static Specification<Product> priceLessThanOrEqualTo(Double maxPrice) {
        return (root, query, cb) -> {
            if (maxPrice == null) return null;
            return cb.lessThanOrEqualTo(root.get("price"), maxPrice);
        };
    }
}
