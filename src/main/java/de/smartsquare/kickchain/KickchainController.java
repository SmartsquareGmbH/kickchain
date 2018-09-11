package de.smartsquare.kickchain;

import de.smartsquare.kickchain.domain.Block;
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
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Controller
public class KickchainController {


    public static final String KICKCHAIN = "Kickchain";
    private final KickchainService kickchainService;

    private final ConsensusService consensusService;

    private final DatabaseService jpaService;

    @Autowired
    public KickchainController(KickchainService kickchainService, ConsensusService consensusService, DatabaseService jpaService) {
        this.kickchainService = kickchainService;
        this.consensusService = consensusService;
        this.jpaService = jpaService;
    }

    @PostConstruct
    public void init() {
        Blockchain blockchain = null;
        try {
            blockchain = jpaService.loadBlockchain(KICKCHAIN);
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
        Blockchain blockchain = jpaService.loadBlockchain(KICKCHAIN);
        Block newBlock = kickchainService.newGame(blockchain.lastBlock(), game);
        jpaService.addBlock(blockchain.getName(), newBlock);
        return ResponseEntity.status(HttpStatus.CREATED).body(fullChain());
    }

    @GetMapping(value = "/player/list")
    @ResponseBody
    public List<String> playerNames() {
        return jpaService.playerNames();
    }

    @GetMapping(value = "/player/{name}/pubkey")
    @ResponseBody
    public String playerPublicKey(@PathVariable("name") String name) {
        return jpaService.getPublicKeyByPlayerName(name);
    }

    @GetMapping(value = "/chain")
    @ResponseBody
    public Blockchain fullChain() {
        return jpaService.loadBlockchain(KICKCHAIN);
    }

    @GetMapping(value = "/chain/new/{name}")
    @ResponseBody
    public Blockchain newChain(@PathVariable("name") String name) {
        Block genesisBlock = kickchainService.create();
        jpaService.createBlockchain(name, genesisBlock);
        return jpaService.loadBlockchain(name);
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
    public ResponseEntity<?> consensus() {
        Blockchain blockchain = jpaService.loadBlockchain(KICKCHAIN);
        Blockchain resolvedChain = consensusService.resolveConflicts(blockchain);
        List<Block> nodesToAdd = resolvedChain.getChain().stream()
                .filter(b -> b.getIndex() > blockchain.lastIndex())
                .collect(Collectors.toList());
        for (Block b : nodesToAdd) {
            jpaService.addBlock(KICKCHAIN, b);
        }
        return ResponseEntity.ok(resolvedChain);
    }

    @GetMapping(value = "/nodes/list")
    @ResponseBody
    public ResponseEntity<?> listNodes() {
        Set<String> nodes = consensusService.getNodes();
        return ResponseEntity.ok(nodes);
    }

}

