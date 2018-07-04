package de.smartsquare.kickchain.service;

import de.smartsquare.kickchain.domain.Block;
import de.smartsquare.kickchain.domain.Blockchain;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

import static de.smartsquare.kickchain.KickchainController.KICKCHAIN;

@Service
public class ConsensusScheduler {

    private final DatabaseService databaseService;

    private final ConsensusService consensusService;


    public ConsensusScheduler(DatabaseService databaseService, ConsensusService consensusService) {
        this.databaseService = databaseService;
        this.consensusService = consensusService;
    }

    @Scheduled(fixedRate = 300000L)
    public void resolveKickchainConflicts() {
            Blockchain blockchain = databaseService.loadBlockchain(KICKCHAIN);
        Blockchain resolvedChain = consensusService.resolveConflicts(blockchain);
            List<Block> nodesToAdd = resolvedChain.getChain().stream()
                    .filter(b -> b.getIndex() > blockchain.lastIndex())
                    .collect(Collectors.toList(

                    ));
            for (Block b : nodesToAdd) {
                databaseService.addBlock(KICKCHAIN, b);
            }
    }

}
