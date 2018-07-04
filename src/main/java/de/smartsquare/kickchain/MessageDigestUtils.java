package de.smartsquare.kickchain;

import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class MessageDigestUtils {

    public static String sha256(String msg) throws NoSuchAlgorithmException {
        return sha256(msg, StandardCharsets.UTF_8);
    }

    private static String sha256(String msg, Charset charset) throws NoSuchAlgorithmException {
        MessageDigest digest = MessageDigest.getInstance("SHA-256");
        byte[] encodedhash = digest.digest(msg.getBytes(charset));
        return bytesToHex(encodedhash);
    }

    private static String bytesToHex(byte[] hash) {
        StringBuilder hexString = new StringBuilder();
        for (byte aHash : hash) {
            String hex = Integer.toHexString(0xff & aHash);
            if (hex.length() == 1) hexString.append('0');
            hexString.append(hex);
        }
        return hexString.toString();
    }

}
