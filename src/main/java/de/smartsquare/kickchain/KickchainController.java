package de.smartsquare.kickchain;

import de.smartsquare.kickchain.domain.KcFullChain;
import de.smartsquare.kickchain.domain.KcGame;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class KickchainController {

    Kickchain kc = new Kickchain();


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



}
