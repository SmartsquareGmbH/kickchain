package de.smartsquare.kickchain;

import com.fasterxml.jackson.databind.ObjectMapper;
import de.smartsquare.kickchain.domain.*;

import java.io.IOException;
import java.io.StringWriter;
import java.security.NoSuchAlgorithmException;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.NoSuchElementException;

public class Kickchain {

    private List<KcBlock> chain = new ArrayList<>();


    public Kickchain() {
        try {
            newBlock(100, "1", null);
        } catch (KcException e) {
            e.printStackTrace();
        }
    }

    void newBlock(long proof, String previousHash, KcGame game) throws KcException {
        try {
            KcBlock block = new KcBlock(chain.size() + 1, Instant.now(), game, proof, previousHash == null ? hashBlock(lastBlock()) : previousHash);
            chain.add(block);
        } catch (Exception e) {
            e.printStackTrace();
            throw new KcException("Unable to create new block.", e);
        }
    }

    public int newGame(KcGame game) throws KcException {

        try {
            long proofOfWork = proofOfWork(lastProof());
            String previousHash = hashBlock(lastBlock());
            newBlock(proofOfWork,previousHash,game);
        } catch (NoSuchAlgorithmException |IOException e) {
            throw new KcException("Unable to get proof of work",e);
        }

        return lastBlock().getIndex();
    }

    private KcBlock lastBlock() throws NoSuchElementException {
        if (chain.isEmpty()) {
            throw new NoSuchElementException("Chain is empty.");
        }
        return chain.get(chain.size() - 1);
    }


    private String hashBlock(KcBlock block) throws IOException, NoSuchAlgorithmException {
        ObjectMapper mapper = new ObjectMapper();
        StringWriter writer = new StringWriter();
        mapper.writeValue(writer, block);
        return MessageDigestUtils.sha256(writer.toString());
    }


    private long lastProof() {
        return lastBlock().getProof();
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
        return guessHash.startsWith("0000");
    }


    public KcFullChain fullChain() {
        return new KcFullChain(Collections.unmodifiableList(chain));
    }
}
