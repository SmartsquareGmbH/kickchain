package de.smartsquare.kickchain;

import de.smartsquare.kickchain.domain.Blockchain;
import de.smartsquare.kickchain.domain.Game;
import de.smartsquare.kickchain.service.ConsensusService;
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
import javax.servlet.http.HttpServletRequest;

@Controller
public class KickchainController {


    private final KickchainService kickchainService;

    private final ConsensusService consensusService;

    private Blockchain kc;

    @Autowired
    public KickchainController(KickchainService kickchainService, ConsensusService consensusService) {
        this.kickchainService = kickchainService;
        this.consensusService = consensusService;
    }

    @PostConstruct
    public void init() {
        kc = kickchainService.create();
    }

    private Blockchain getCurrentChain() {
        return kc;
    }

    @PostMapping(value = "/game/new")
    @ResponseBody
    public int newGame(@RequestBody Game game) throws BlockchainException {
        return kickchainService.newGame(kc, game);
    }

    @GetMapping(value = "/chain")
    @ResponseBody
    public Blockchain fullChain() {
        return kc;
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

