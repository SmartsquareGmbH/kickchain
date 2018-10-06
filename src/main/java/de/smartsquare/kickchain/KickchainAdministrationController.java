package de.smartsquare.kickchain;

import de.smartsquare.kickchain.domain.Block;
import de.smartsquare.kickchain.domain.Blockchain;
import de.smartsquare.kickchain.service.ConsensusService;
import de.smartsquare.kickchain.service.DatabaseService;
import de.smartsquare.kickchain.service.KickchainService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/admin")
public class KickchainAdministrationController {


    private final KickchainService kickchainService;

    private final ConsensusService consensusService;

    private final DatabaseService databaseService;

    @Autowired
    public KickchainAdministrationController(KickchainService kickchainService, ConsensusService consensusService, DatabaseService databaseService) {
        this.kickchainService = kickchainService;
        this.consensusService = consensusService;
        this.databaseService = databaseService;
    }

    @GetMapping(value = "/chain/{name}")
    @ResponseBody
    public Blockchain fullChain(@PathVariable("name") String name) {
        return databaseService.loadBlockchain(name);
    }

    @GetMapping(value = "/chain/{name}/new")
    @ResponseBody
    public ResponseEntity<?> newChain(@PathVariable("name") String name) {
        if (databaseService.loadBlockchain(name) != null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Blockchain " + name +" not found.");
        }
        Block genesisBlock = kickchainService.create();
        databaseService.createBlockchain(name, genesisBlock);
        Blockchain blockchain = databaseService.loadBlockchain(name);
        return ResponseEntity.ok(blockchain);
    }

    @GetMapping(value = "/chain/{name}/resolve")
    @ResponseBody
    public ResponseEntity<?> consensus(@PathVariable("name") String name) {
        Blockchain blockchain = databaseService.loadBlockchain(name);
        if (blockchain == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Blockchain " + name +" not found.");
        }
        Blockchain resolvedChain = consensusService.resolveConflicts(blockchain);
        List<Block> nodesToAdd = resolvedChain.getChain().stream()
                .filter(b -> b.getHeader().getIndex() >= blockchain.lastIndex())
                .collect(Collectors.toList());
        for (Block b : nodesToAdd) {
            databaseService.addBlock(name, b);
        }
        return ResponseEntity.ok(resolvedChain);
    }

    @PostMapping(value = "/nodes/register")
    @ResponseBody
    public ResponseEntity<?> registerNodes(@RequestBody String node) {

        if (node == null || node.equals("")) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Error: Please supply a valid list of nodes");
        }
        consensusService.registerNode(node);
        return ResponseEntity.status(HttpStatus.CREATED).body(consensusService.getNodes());
    }

    @PostMapping(value = "/nodes/unregister")
    @ResponseBody
    public ResponseEntity<?> unregisterNodes(@RequestBody String node) {

        if (node == null || node.equals("")) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Error: Please supply a valid list of nodes");
        }
        consensusService.registerNode(node);
        return ResponseEntity.status(HttpStatus.CREATED).body(consensusService.getNodes());
    }

    @GetMapping(value = "/nodes/list")
    @ResponseBody
    public ResponseEntity<?> listNodes() {
        Set<String> nodes = consensusService.getNodes();
        return ResponseEntity.ok(nodes);
    }

}

