package de.smartsquare.kickchain;

import com.fasterxml.jackson.databind.ObjectMapper;
import de.smartsquare.kickchain.domain.Game;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.StringWriter;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.List;

public class MessageDigestUtils {

    private static final Logger logger = LoggerFactory.getLogger(MessageDigestUtils.class);

    public static String transactionHash(List<Game> content) {
        ObjectMapper mapper = new ObjectMapper();
        StringWriter writer = new StringWriter();
        try {
            mapper.writeValue(writer, content);
            String blockAsString = writer.toString();
            logger.info(String.format("Transactions as string: %s", blockAsString));
            return MessageDigestUtils.sha256(blockAsString);
        } catch (NoSuchAlgorithmException | IOException e) {
            logger.error("Unable to hash the list of transactions.", e);
        }
        return null;
    }

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
