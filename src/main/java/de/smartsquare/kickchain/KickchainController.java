package de.smartsquare.kickchain;

import de.smartsquare.kickchain.domain.Blockchain;
import de.smartsquare.kickchain.domain.Game;
import de.smartsquare.kickchain.service.ConsensusService;
import de.smartsquare.kickchain.service.DatabaseService;
import de.smartsquare.kickchain.service.KickchainService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

@Controller
public class KickchainController {


    private final KickchainService kickchainService;

    private final ConsensusService consensusService;

    private final DatabaseService databaseService;

    private Blockchain blockchain;

    @Autowired
    public KickchainController(KickchainService kickchainService, ConsensusService consensusService, DatabaseService databaseService) {
        this.kickchainService = kickchainService;
        this.consensusService = consensusService;
        this.databaseService = databaseService;
    }

    @PostConstruct
    public void init() {
        try {
            blockchain = databaseService.loadBlockchain("Kickchain");
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (blockchain == null) {
            blockchain = kickchainService.create("Kickchain");
            try {
                databaseService.saveBlockchain(blockchain);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private Blockchain getCurrentChain() {
        return blockchain;
    }

    @PostMapping(value = "/game/new")
    @ResponseBody
    public Blockchain newGame(@RequestBody Game game) throws BlockchainException {
        Blockchain blockchain = kickchainService.newGame(this.blockchain, game);
        try {
            databaseService.saveBlockchain(blockchain);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return blockchain;
    }

    @GetMapping(value = "/chain")
    @ResponseBody
    public Blockchain fullChain() {
        return blockchain;
    }

    @GetMapping(value = "/chain/new/{name}")
    @ResponseBody
    public Blockchain newChain(@PathVariable("name") String name) {
        Blockchain blockchain = kickchainService.create(name);
        try {
            databaseService.saveBlockchain(blockchain);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return blockchain;
    }


    @PostMapping(value = "/nodes/register")
    @ResponseBody
    public ResponseEntity<?> registerNodes(@RequestBody String node, HttpServletRequest request) {

        if (node == null || node.equals("")) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Error: Please supply a valid list of nodes");
        }
        consensusService.registerNode(node);
        return ResponseEntity.status(HttpStatus.CREATED).body(consensusService.getNodes());
    }


    @GetMapping(value = "/nodes/resolve")
    @ResponseBody
    public ResponseEntity<?> consensus() throws BlockchainException {
        Blockchain resolvedChain = consensusService.resolveConflicts(getCurrentChain());
        return ResponseEntity.ok(resolvedChain);
    }

}

