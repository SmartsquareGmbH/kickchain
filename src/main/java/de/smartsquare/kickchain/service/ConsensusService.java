package de.smartsquare.kickchain.service;

import de.smartsquare.kickchain.KcException;
import de.smartsquare.kickchain.Kickchain;
import de.smartsquare.kickchain.domain.KcBlock;
import de.smartsquare.kickchain.domain.KcFullChain;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class ConsensusService {

    private Set<String> nodes = new HashSet<>();


    public void registerNode(String address) {
        nodes.add(address);
    }

    public boolean validChain(Kickchain mine, Kickchain their) throws KcException {
        try {
            KcBlock latestBlock = their.lastBlock();

            for (KcBlock current : their.) {
                System.out.println(latestBlock);
                System.out.println(current);

                if (!mine.lastBlock().getPreviousHash().equals(hashBlock(latestBlock))) {
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
