package org.example.utils;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;

public class PasswordHasher {

    public static String sha256(String password) {
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            byte[] encoded = md.digest(password.getBytes(StandardCharsets.UTF_8));
            return toHex(encoded);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private static String toHex(byte[] hash) {
        StringBuilder sb = new StringBuilder();
        for (byte b : hash) {
            String hex = Integer.toHexString(0xff & b);
            if (hex.length() == 1) sb.append('0');
            sb.append(hex);
        }
        return sb.toString();
    }
}
