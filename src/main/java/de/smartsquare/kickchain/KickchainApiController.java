package de.smartsquare.kickchain;

import de.smartsquare.kickchain.domain.Block;
import de.smartsquare.kickchain.domain.Blockchain;
import de.smartsquare.kickchain.domain.Game;
import de.smartsquare.kickchain.service.DatabaseService;
import de.smartsquare.kickchain.service.KickchainService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.PostConstruct;

@Controller
public class KickchainApiController {

    public static final String KICKCHAIN = "Kickchain";

    private final KickchainService kickchainService;
    private final DatabaseService databaseService;

    @Autowired
    public KickchainApiController(KickchainService kickchainService, DatabaseService databaseService) {
        this.kickchainService = kickchainService;
        this.databaseService = databaseService;
    }

    @PostConstruct
    public void init() {
        Blockchain blockchain = null;
        try {
            blockchain = databaseService.loadBlockchain(KICKCHAIN);
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (blockchain == null) {
            newChain(KICKCHAIN);
        }
    }

    @PostMapping(value = "/game/new")
    @ResponseBody
    public ResponseEntity<?> newGame(@RequestBody Game game) throws BlockchainException {
        Blockchain blockchain = databaseService.loadBlockchain(KICKCHAIN);
        Block newBlock = kickchainService.newGame(blockchain.lastBlock(), game);
        databaseService.addBlock(blockchain.getName(), newBlock);
        return ResponseEntity.status(HttpStatus.CREATED).body(fullChain());
    }

    @GetMapping(value = "/chain")
    @ResponseBody
    public Blockchain fullChain() {
        return databaseService.loadBlockchain(KICKCHAIN);
    }

    private void newChain(String name) {
        Block genesisBlock = kickchainService.create();
        databaseService.createBlockchain(name, genesisBlock);
    }

}

