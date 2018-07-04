package de.smartsquare.kickchain.service;

import de.smartsquare.kickchain.BlockchainException;
import de.smartsquare.kickchain.domain.Block;
import de.smartsquare.kickchain.domain.Game;
import org.springframework.stereotype.Service;

import java.util.Collections;

@Service
public class KickchainService {

    private final MiningService miningService;

    public KickchainService(MiningService miningService) {
        this.miningService = miningService;
    }


    public Block create() {
        return new Block(0, null, null, 100, null);
    }

    public Block newGame(Block lastBlock, Game game) throws BlockchainException {
        return miningService.mine(lastBlock, Collections.singletonList(game));
    }

}
