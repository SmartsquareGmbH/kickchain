package de.smartsquare.kickchain;

import com.fasterxml.jackson.databind.ObjectMapper;
import de.smartsquare.kickchain.domain.KcBlock;
import de.smartsquare.kickchain.domain.KcFullChain;
import de.smartsquare.kickchain.domain.KcGame;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.StringWriter;
import java.security.NoSuchAlgorithmException;
import java.time.Instant;

@Service
public class KickchainService {

    // private List<KcBlock> chain = new ArrayList<>();


    public KcFullChain create() {
        KcFullChain fullChain = new KcFullChain();
        try {
            fullChain.addBlock(newBlock(100, "1", null, 1, null));

        } catch (KcException e) {
            e.printStackTrace();
        }
        return fullChain;
    }


    private KcBlock newBlock(long proof, String previousHash, KcGame game, int index, KcBlock lastBlock) throws KcException {
        try {
            return new KcBlock(index, Instant.now(), game, proof, previousHash == null ? hashBlock(lastBlock) : previousHash);
        } catch (Exception e) {
            e.printStackTrace();
            throw new KcException("Unable to create new block.", e);
        }
    }

    public int newGame(KcGame game, KcFullChain fullChain) throws KcException {

        try {
            long proofOfWork = proofOfWork(lastProof(fullChain));
            String previousHash = hashBlock(fullChain.lastBlock());
            KcBlock kcBlock = newBlock(proofOfWork, previousHash, game, fullChain.lastIndex(), fullChain.lastBlock());
            fullChain.addBlock(kcBlock);
        } catch (NoSuchAlgorithmException | IOException e) {
            throw new KcException("Unable to get proof of work", e);
        }

        return fullChain.lastIndex();
    }


    private String hashBlock(KcBlock block) throws IOException, NoSuchAlgorithmException {
        ObjectMapper mapper = new ObjectMapper();
        StringWriter writer = new StringWriter();
        mapper.writeValue(writer, block);
        return MessageDigestUtils.sha256(writer.toString());
    }


    private long lastProof(KcFullChain fullChain) {
        return fullChain.lastBlock().getProof();
    }

    public long proofOfWork(long lastProof) throws NoSuchAlgorithmException {
        long proof = 0;
        while (!validProof(lastProof, proof)) {
            proof = proof + 1;
        }
        return proof;
    }

    private boolean validProof(long lastProof, long proof) throws NoSuchAlgorithmException {
        String guess = String.format("%d%d", lastProof, proof);
        String guessHash = MessageDigestUtils.sha256(Integer.toString(guess.hashCode()));
        return guessHash.startsWith("000000");
    }




}
