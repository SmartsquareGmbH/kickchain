package de.smartsquare.kickchain.service;

import de.smartsquare.kickchain.BlockchainException;
import de.smartsquare.kickchain.domain.*;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.List;

@Service
public class MiningService {

    private final Proof<Boolean> proof;

    public MiningService(ZeroPaddedHashProof zeroPaddedHashProof) {
        this.proof = zeroPaddedHashProof;
    }

    public <T extends BlockContent> Blockchain mine(Blockchain currentChain, List<T> transactions) throws BlockchainException {
        try {
            Block lastBlock = currentChain.lastBlock();
            long proofOfWork = proofOfWork(lastBlock.getProof());
            String previousHash = lastBlock.toHash();
            Block<T> block = new Block<>(
                    currentChain.lastIndex(),
                    Instant.now(),
                    transactions,
                    proofOfWork,
                    previousHash);
            currentChain.addBlock(block);
            return currentChain;
        } catch (Exception e) {
            throw new BlockchainException("Unable to get proof of work", e);
        }
    }

    private long proofOfWork(long lastProof) {
        long pow = 0;
        while (!proof.apply(lastProof, pow)) {
            pow = pow + 1;
        }
        return pow;
    }

}
