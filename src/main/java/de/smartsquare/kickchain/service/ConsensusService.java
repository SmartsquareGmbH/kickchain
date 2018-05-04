package de.smartsquare.kickchain.service;

import de.smartsquare.kickchain.KcException;
import de.smartsquare.kickchain.KickchainService;
import de.smartsquare.kickchain.domain.KcBlock;
import de.smartsquare.kickchain.domain.KcFullChain;
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

    @Autowired
    KickchainService kickchainService;

    private Set<String> nodes = new HashSet<>();


    public void registerNode(String address) {
        nodes.add(address);
    }

    public boolean validChain(KcFullChain mine, KcFullChain their) throws KcException {
        try {
            KcBlock latestBlock = their.lastBlock();

            for (KcBlock current : their.getChain()) {
                System.out.println(latestBlock);
                System.out.println(current);

                if (!mine.lastBlock().getPreviousHash().equals(kickchainService.hashBlock(latestBlock))) {
                    return false;
                }
                if (!kickchainService.validProof(latestBlock.getProof(), current.getProof())) {
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

    public KcFullChain resolveConflicts(KcFullChain mine) throws KcException {
        KcFullChain newChain = null;

        int maxLength = mine.getChain().size();

        for (String node : nodes) {
            RestTemplate restTemplate = new RestTemplate();
            ResponseEntity<KcFullChain> response = restTemplate.getForEntity("http://" + node + "/chain", KcFullChain.class);

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
