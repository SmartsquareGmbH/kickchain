package de.smartsquare.kickchain.service;

import de.smartsquare.kickchain.BlockchainException;
import de.smartsquare.kickchain.domain.Block;
import de.smartsquare.kickchain.domain.Blockchain;
import de.smartsquare.kickchain.domain.Proof;
import de.smartsquare.kickchain.domain.ZeroPaddedHashProof;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.client.RestTemplate;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class ConsensusService {

    @Value("${consensus.peers}")
    private String peers; // comma separated list of known peers

    private final Proof<Boolean> proof;

    private final Set<String> nodes = new HashSet<>();

    private final Logger logger = LoggerFactory.getLogger(ConsensusService.class);

    @PostConstruct
    public void postConstruct() {
        if (!StringUtils.isEmpty(peers)) {
            nodes.addAll(Arrays.asList(peers.split(",")));
        }
    }

    @Autowired
    public ConsensusService(ZeroPaddedHashProof proof) {
        this.proof = proof;
    }

    public void registerNode(String address) {
        nodes.add(address);
    }

    public void unregisterNode(String address) {
        nodes.remove(address);
    }

    private boolean validChain(Blockchain mine, Blockchain their) throws BlockchainException {
        try {
            Block mineLast = mine.lastBlock();
            Block theirMiddle = their.getByIndex(mineLast.getHeader().getIndex());
            if (!mineLast.getHeader().toHash().equals(theirMiddle.getHeader().toHash())) {
                logger.info("My last block does not match their last block.");
                logger.debug(mineLast.toString());
                logger.debug(theirMiddle.toString());
                return false;
            }

            Block latestBlock = theirMiddle;
            List<Block> collect = their.getChain().stream()
                    .filter(b -> b.getHeader().getIndex() > mineLast.getHeader().getIndex())
                    .collect(Collectors.toList());

            for (Block b : collect) {
                // check proof of work
                if (!proof.apply(latestBlock.getHeader().getProof(), b.getHeader().getProof())) {
                    return false;
                }
                latestBlock = b;
            }

            // check all previous hashes
            Block lastBlock = null;
            for (Block b : their.getChain()) {
                if (lastBlock != null) {
                    logger.info(String.format("Block %d has previous hash %s and lastBlockHash is %s", b.getHeader().getIndex(), b.getHeader().getPreviousHash(), lastBlock.getHeader().toHash()));
                    if (!b.getHeader().getPreviousHash().equals(lastBlock.getHeader().toHash())) {
                        return false;
                    }
                }
                lastBlock = b;
            }

            return true;
        } catch (IOException | NoSuchAlgorithmException ex) {
            throw new BlockchainException("Unable to validate chain", ex);
        }
    }

    public Set<String> getNodes() {
        return nodes;
    }

    public Blockchain resolveConflicts(Blockchain mine) {
        logger.info("resolveConflicts(" + mine + ") called...");
        Blockchain newChain = null;
        int maxLength = mine.getChain().size();
        logger.debug("mine chain size is " + maxLength);
        for (String node : nodes) {
            logger.info("Resolving node" + node + "...");
            try {
                RestTemplate restTemplate = new RestTemplate();
                ResponseEntity<Blockchain> response = restTemplate.getForEntity("http://" + node + "/chain", Blockchain.class);
                logger.debug("response is " + response);
                if (response.getStatusCode().equals(HttpStatus.OK)) {
                    Blockchain blockchain = response.getBody();
                    logger.debug("Blockchain from node " + node + " is " + blockchain);
                    List<Block> chain = blockchain.getChain();
                    logger.debug("their chain size is " + chain.size());
                    if (chain.size() > maxLength && validChain(mine, blockchain)) {
                        maxLength = chain.size();
                        newChain = blockchain;
                    } else {
                        logger.info("Chain from node " + node + " is not longer or not valid.");
                    }
                }
            } catch (Exception ex) {
                logger.error("Unable to resolve conflict with node " + node, ex);
            }
        }
        if (newChain != null) {
            return newChain;
        } else {
            return mine;
        }
    }
}
