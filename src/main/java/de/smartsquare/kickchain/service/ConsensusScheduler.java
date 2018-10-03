package de.smartsquare.kickchain.service;

import de.smartsquare.kickchain.domain.Block;
import de.smartsquare.kickchain.domain.Blockchain;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

import static de.smartsquare.kickchain.KickchainApiController.KICKCHAIN;

@Service
public class ConsensusScheduler {

    private final DatabaseService jpaService;

    private final ConsensusService consensusService;


    public ConsensusScheduler(DatabaseService jpaService, ConsensusService consensusService) {
        this.jpaService = jpaService;
        this.consensusService = consensusService;
    }

    @Scheduled(fixedRate = 300000L)
    public void resolveKickchainConflicts() {
            Blockchain blockchain = jpaService.loadBlockchain(KICKCHAIN);
        Blockchain resolvedChain = consensusService.resolveConflicts(blockchain);
            List<Block> nodesToAdd = resolvedChain.getChain().stream()
                    .filter(b -> b.getHeader().getIndex() > blockchain.lastIndex())
                    .collect(Collectors.toList());
            for (Block b : nodesToAdd) {
                jpaService.addBlock(KICKCHAIN, b);
            }
    }

}
