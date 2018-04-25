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

    private List<KcTransaction> currentTransactions = new ArrayList<>();


    public Kickchain() {
        try {
            newBlock(100, "1");
        } catch (KcException e) {
            e.printStackTrace();
        }
    }

    public void newBlock(long proof, String previousHash) throws KcException {
        try {
            KcBlock block = new KcBlock(chain.size() + 1, Instant.now(), currentTransactions, proof, previousHash == null ? hashBlock(lastBlock()) : previousHash);
            chain.add(block);
        } catch (Exception e) {
            e.printStackTrace();
            throw new KcException("Unable to create new block.", e);
        }
    }

    public int newTransaction(List<String> playerTeam1, List<String> playerTeam2, int goalsTeam1, int goalsTeam2) {
        KcTeam team1 = new KcTeam((String[])playerTeam1.toArray());
        KcTeam team2 = new KcTeam((String[])playerTeam2.toArray());
        KcScore score = new KcScore(goalsTeam1, goalsTeam2);
        KcTransaction transaction = new KcTransaction(team1, team2, score);

        return addTransaction(transaction);
    }

    public int addTransaction(KcTransaction transaction) {
        currentTransactions.add(transaction);
        return lastBlock().getIndex() + 1;
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
