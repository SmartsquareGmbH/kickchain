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


    public Blockchain create(String name) {
        Blockchain fullChain = new Blockchain(name);
        fullChain.addBlock(new Block(1, Instant.now(), null, 100, "1"));
        return fullChain;
    }

    public Blockchain newGame(Blockchain fullChain, Game game) throws BlockchainException {
        return miningService.mine(fullChain, Collections.singletonList(game));
    }

}
