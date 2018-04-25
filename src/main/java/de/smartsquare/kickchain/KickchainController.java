package de.smartsquare.kickchain;

import de.smartsquare.kickchain.domain.KcFullChain;
import de.smartsquare.kickchain.domain.KcTransaction;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class KickchainController {

    Kickchain kc = new Kickchain();


    @PostMapping(value = "/transactions/new")
    @ResponseBody
    public int newTransaction(@RequestBody KcTransaction transaction) {
        return kc.addTransaction(transaction);
    }

    @GetMapping(value = "/chain")
    @ResponseBody
    public KcFullChain fullChain() {
        return kc.fullChain();
    }

}
