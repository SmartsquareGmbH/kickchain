package de.smartsquare.kickchain.service;

import de.smartsquare.kickchain.BlockchainException;
import de.smartsquare.kickchain.domain.Block;
import de.smartsquare.kickchain.domain.Blockchain;
import de.smartsquare.kickchain.domain.Proof;
import de.smartsquare.kickchain.domain.ZeroPaddedHashProof;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.util.HashSet;
import java.util.Set;

@Service
public class ConsensusService {

    private final Proof<Boolean> proof;

    private Set<String> nodes = new HashSet<>();

    @Autowired
    public ConsensusService(ZeroPaddedHashProof proof) {
        this.proof = proof;
    }

    public void registerNode(String address) {
        nodes.add(address);
    }

    private boolean validChain(Blockchain mine, Blockchain their) throws BlockchainException {
        try {
            Block latestBlock = their.lastBlock();

            for (Block current : their.getChain()) {
                System.out.println(latestBlock);
                System.out.println(current);

                if (!mine.lastBlock().getPreviousHash().equals(latestBlock.toHash())) {
                    return false;
                }
                if (!proof.apply(latestBlock.getProof(), current.getProof())) {
                    return false;
                }
                latestBlock = current;
            }
            return true;
        } catch (IOException | NoSuchAlgorithmException ex) {
            throw new BlockchainException("Unable to validate chain", ex);
        }
    }

    public Set<String> getNodes() {
        return nodes;
    }

    public Blockchain resolveConflicts(Blockchain mine) throws BlockchainException {
        Blockchain newChain = null;

        int maxLength = mine.getChain().size();

        for (String node : nodes) {
            RestTemplate restTemplate = new RestTemplate();
            ResponseEntity<Blockchain> response = restTemplate.getForEntity("http://" + node + "/chain", Blockchain.class);

            if (response.getStatusCode().equals(HttpStatus.OK)) {
                if (response.getBody().getChain().size() > maxLength && validChain(mine, response.getBody())) {
                    maxLength = response.getBody().getChain().size();
                    newChain = response.getBody();
                }
            }
        }
        if (newChain != null) {
            return newChain;
        } else {
            return mine;
        }
    }
}
