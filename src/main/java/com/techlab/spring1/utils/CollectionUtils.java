package com.techlab.spring1.utils;

import com.techlab.spring1.model.Product;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *
 * @author matias-bruno
 */
public class CollectionUtils {
    public static Map<Long, Product> listToMap(List<Product> products) {
        Map<Long, Product> productMap = new HashMap<>();
        for(Product p : products) {
            productMap.put(p.getId(), p);
        }
        return productMap;
    }
}
