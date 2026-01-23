package com.techlab.spring1.util;

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
}
