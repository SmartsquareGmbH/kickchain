package de.smartsquare.kickchain.service;

import de.smartsquare.kickchain.BlockchainException;
import de.smartsquare.kickchain.domain.Block;
import de.smartsquare.kickchain.domain.Game;
import de.smartsquare.kickchain.domain.Proof;
import de.smartsquare.kickchain.domain.ZeroPaddedHashProof;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.List;

@Service
public class MiningService {

    private final Proof<Boolean> proof;

    public MiningService(ZeroPaddedHashProof zeroPaddedHashProof) {
        this.proof = zeroPaddedHashProof;
    }

    Block mine(Block lastBlock, List<Game> transactions) throws BlockchainException {
        try {
            long proofOfWork = proofOfWork(lastBlock.getProof());
            String previousHash = lastBlock.toHash();
            return new Block(
                    lastBlock.getIndex() + 1,
                    Instant.now(),
                    transactions,
                    proofOfWork,
                    previousHash);
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
