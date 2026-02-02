package com.techlab.ecommerce.util;

import java.text.Normalizer;

/**
 *
 * @author matias-bruno
 */
public class TextUtils {
    public static String toTitleCase(String text) {
        String[] words = text.trim().split("\\s+");
        StringBuilder sb = new StringBuilder();
        for (String word : words) {
            sb.append(word.substring(0, 1).toUpperCase());
            sb.append(word.substring(1).toLowerCase());
            sb.append(" ");
        }
        return sb.toString().trim();
    }
    
    public static String removeAccents(String text) {
        text = Normalizer.normalize(text, Normalizer.Form.NFD);
        text = text.replaceAll("\\p{InCombiningDiacriticalMarks}+", "");
        return text;
    }

    public static String slugify(String name) {
        String slug = name.toLowerCase();
        slug = removeAccents(slug);
        //slug = slug.replaceAll("ñ", "n");
        slug = slug.replaceAll("[^a-z0-9-]+", " ");
        slug = slug.trim().replaceAll("\\s+", " ");
        slug = slug.replaceAll(" ", "-");
        return slug;
    }
}
