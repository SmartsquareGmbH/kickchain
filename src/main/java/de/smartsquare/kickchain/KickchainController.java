package de.smartsquare.kickchain;

import de.smartsquare.kickchain.domain.KcFullChain;
import de.smartsquare.kickchain.domain.KcGame;
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


    @Autowired
    private KickchainService kickchainService;

    private KcFullChain kc;

    @PostConstruct
    public void init() {
        kc = kickchainService.create();
    }


    @PostMapping(value = "/game/new")
    @ResponseBody
    public int newGame(@RequestBody KcGame game) throws KcException {
        return kc.newGame(game);
    }

    @GetMapping(value = "/chain")
    @ResponseBody
    public KcFullChain fullChain() {
        return kc.fullChain();
    }


    @PostMapping(value = "/nodes/register")
    @ResponseBody
    public ResponseEntity<?> registerNodes(@RequestBody String node, HttpServletRequest request) {

        if (node == null || node.equals("")) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Error: Please supply a valid list of nodes");
        }
        kc.registerNode(node);
        return ResponseEntity.status(HttpStatus.CREATED).body(kc.getNodes());
    }


    @GetMapping(value = "/nodes/resolve")
    @ResponseBody
    public ResponseEntity<?> consensus() throws KcException {


        boolean replaced = kc.resolveConflicts();

        if (replaced) {
            return ResponseEntity.ok(kc.getChain());
        } else {
            return ResponseEntity.ok(kc.getChain());
        }
    }

}

