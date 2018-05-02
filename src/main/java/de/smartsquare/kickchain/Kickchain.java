package de.smartsquare.kickchain;

import com.fasterxml.jackson.databind.ObjectMapper;
import de.smartsquare.kickchain.domain.KcBlock;
import de.smartsquare.kickchain.domain.KcFullChain;
import de.smartsquare.kickchain.domain.KcGame;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.io.StringWriter;
import java.security.NoSuchAlgorithmException;
import java.time.Instant;
import java.util.*;

public class Kickchain {

    private List<KcBlock> chain = new ArrayList<>();
    private Set<String> nodes = new HashSet<>();


    public Kickchain() {
        try {
            newBlock(100, "1", null);
            nodes.add("localhost:8080");
        } catch (KcException e) {
            e.printStackTrace();
        }
    }

    private void newBlock(long proof, String previousHash, KcGame game) throws KcException {
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
            newBlock(proofOfWork, previousHash, game);
        } catch (NoSuchAlgorithmException | IOException e) {
            throw new KcException("Unable to get proof of work", e);
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

    public void registerNode(String address) {
        nodes.add(address);
    }

    public boolean validChain(List<KcBlock> pChain) throws KcException {
        try {
            KcBlock latestBlock = pChain.get(0);

            for (KcBlock current : pChain) {
                System.out.println(latestBlock);
                System.out.println(current);

                if (!lastBlock().getPreviousHash().equals(hashBlock(latestBlock))) {
                    return false;
                }
                if (!validProof(latestBlock.getProof(), current.getProof())) {
                    return false;
                }
                latestBlock = current;
            }
            return true;
        } catch (IOException | NoSuchAlgorithmException ex) {
            throw new KcException("Unable to validate chain", ex);
        }
    }

    public Set<String> getNodes() {
        return nodes;
    }

    public List<KcBlock> getChain() {
        return chain;
    }

    public boolean resolveConflicts() throws KcException {
        List<KcBlock> newChain = null;

        int maxLength = chain.size();

        for (String node : nodes) {
            RestTemplate restTemplate = new RestTemplate();
            ResponseEntity<KcFullChain> response = restTemplate.getForEntity("http://" + node + "/chain", KcFullChain.class);

            if (response.getStatusCode().equals(HttpStatus.OK)) {
                if (response.getBody().getLength() > maxLength && validChain(response.getBody().getChain())) {
                    maxLength = response.getBody().getLength();
                    newChain = response.getBody().getChain();
                }
            }
        }
        if (newChain != null) {
            this.chain = newChain;
            return true;
        } else {
            return false;
        }
    }
}
