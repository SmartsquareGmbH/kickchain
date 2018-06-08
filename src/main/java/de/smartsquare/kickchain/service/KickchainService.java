package de.smartsquare.kickchain.service;

import de.smartsquare.kickchain.BlockchainException;
import de.smartsquare.kickchain.domain.Block;
import de.smartsquare.kickchain.domain.Blockchain;
import de.smartsquare.kickchain.domain.Game;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.Collections;

@Service
public class KickchainService {

    private final MiningService miningService;

    public KickchainService(MiningService miningService) {
        this.miningService = miningService;
    }


    public Block create(String name) {
        Block block = new Block(1, Instant.now(), null, 100, "1");
        return block;
    }

    public Block newGame(Block lastBlock, Game game) throws BlockchainException {
        Block<Game> minedBlock = miningService.mine(lastBlock, Collections.singletonList(game));
        return minedBlock;
    }

}
