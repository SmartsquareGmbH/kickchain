package de.smartsquare.kickchain.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import de.smartsquare.kickchain.BlockchainException;
import de.smartsquare.kickchain.MessageDigestUtils;
import de.smartsquare.kickchain.domain.Block;
import de.smartsquare.kickchain.domain.Blockchain;
import de.smartsquare.kickchain.domain.Game;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.StringWriter;
import java.security.NoSuchAlgorithmException;
import java.time.Instant;

@Service
public class KickchainService {


    public Blockchain create(String name) {
        Blockchain fullChain = new Blockchain(name);
        try {
            fullChain.addBlock(newBlock(100, "1", null, 1, null));

        } catch (BlockchainException e) {
            e.printStackTrace();
        }
        return fullChain;
    }


    private Block newBlock(long proof, String previousHash, Game game, int index, Block lastBlock) throws BlockchainException {
        try {
            return new Block(index, Instant.now(), game, proof, previousHash == null ? hashBlock(lastBlock) : previousHash);
        } catch (Exception e) {
            e.printStackTrace();
            throw new BlockchainException("Unable to create new block.", e);
        }
    }

    public Blockchain newGame(Blockchain fullChain, Game game) throws BlockchainException {

        try {
            long proofOfWork = proofOfWork(lastProof(fullChain));
            String previousHash = hashBlock(fullChain.lastBlock());
            Block block = newBlock(proofOfWork, previousHash, game, fullChain.lastIndex(), fullChain.lastBlock());
            fullChain.addBlock(block);
        } catch (NoSuchAlgorithmException | IOException e) {
            throw new BlockchainException("Unable to get proof of work", e);
        }

        return fullChain;
    }


    public String hashBlock(Block block) throws IOException, NoSuchAlgorithmException {
        ObjectMapper mapper = new ObjectMapper();
        StringWriter writer = new StringWriter();
        mapper.writeValue(writer, block);
        return MessageDigestUtils.sha256(writer.toString());
    }


    private long lastProof(Blockchain fullChain) {
        return fullChain.lastBlock().getProof();
    }

    long proofOfWork(long lastProof) throws NoSuchAlgorithmException {
        long proof = 0;
        while (!validProof(lastProof, proof)) {
            proof = proof + 1;
        }
        return proof;
    }

    public boolean validProof(long lastProof, long proof) throws NoSuchAlgorithmException {
        String guess = String.format("%d%d", lastProof, proof);
        String guessHash = MessageDigestUtils.sha256(Integer.toString(guess.hashCode()));
        return guessHash.startsWith("000000");
    }




}
