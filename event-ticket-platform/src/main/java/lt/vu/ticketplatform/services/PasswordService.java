package lt.vu.ticketplatform.services;

import jakarta.enterprise.context.ApplicationScoped;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Base64;

@ApplicationScoped
public class PasswordService {

    private static final String HASH_ALGORITHM = "SHA-256";
    private static final int SALT_LENGTH = 16;
    private static final String SEPARATOR = ":";

    private final SecureRandom secureRandom = new SecureRandom();

    public String hashPassword(String rawPassword) {
        byte[] salt = new byte[SALT_LENGTH];
        secureRandom.nextBytes(salt);

        byte[] hash = hash(rawPassword, salt);

        return Base64.getEncoder().encodeToString(salt)
                + SEPARATOR
                + Base64.getEncoder().encodeToString(hash);
    }

    public boolean matches(String rawPassword, String storedPasswordHash) {
        if (storedPasswordHash == null || !storedPasswordHash.contains(SEPARATOR)) {
            return false;
        }

        String[] parts = storedPasswordHash.split(SEPARATOR, 2);

        if (parts.length != 2) {
            return false;
        }

        byte[] salt;
        byte[] expectedHash;

        try {
            salt = Base64.getDecoder().decode(parts[0]);
            expectedHash = Base64.getDecoder().decode(parts[1]);
        } catch (IllegalArgumentException e) {
            return false;
        }

        byte[] actualHash = hash(rawPassword, salt);
        return MessageDigest.isEqual(expectedHash, actualHash);
    }

    private byte[] hash(String rawPassword, byte[] salt) {
        try {
            MessageDigest digest = MessageDigest.getInstance(HASH_ALGORITHM);
            digest.update(salt);
            return digest.digest(rawPassword.getBytes(StandardCharsets.UTF_8));
        } catch (NoSuchAlgorithmException e) {
            throw new IllegalStateException("Password hashing algorithm is not available", e);
        }
    }
}