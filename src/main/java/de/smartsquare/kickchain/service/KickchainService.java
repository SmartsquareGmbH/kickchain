package de.smartsquare.kickchain.service;

import de.smartsquare.kickchain.BlockchainException;
import de.smartsquare.kickchain.MessageDigestUtils;
import de.smartsquare.kickchain.domain.Block;
import de.smartsquare.kickchain.domain.Game;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

@Service
public class KickchainService {

    private final MiningService miningService;

    public KickchainService(MiningService miningService) {
        this.miningService = miningService;
    }

    public Block create() {
        List<Game> games = null;
        String transactionHash = MessageDigestUtils.transactionHash(games);
        return new Block(0, null, 100, null, transactionHash, games);
    }

    public Block newGame(Block lastBlock, Game game) throws BlockchainException {
        Block minedBlock = miningService.mine(lastBlock, Collections.singletonList(game));
        return minedBlock;
    }

}
