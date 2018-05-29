package de.smartsquare.kickchain.domain;

import de.smartsquare.kickchain.MessageDigestUtils;
import org.springframework.stereotype.Component;

import java.security.NoSuchAlgorithmException;

@Component
public class ZeroPaddedHashProof implements Proof<Boolean> {

    @Override
    public Boolean apply(Long lastProof, Long proof) {
        String guess = String.format("%d%d", lastProof, proof);
        String guessHash = null;
        try {
            guessHash = MessageDigestUtils.sha256(Integer.toString(guess.hashCode()));
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            return false;
        }
        return guessHash.startsWith("0000");
    }

}
