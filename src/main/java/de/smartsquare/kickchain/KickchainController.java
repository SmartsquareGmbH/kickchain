package de.smartsquare.kickchain;

import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;

@Controller
public class KickchainController {


    @XmlRootElement
    public static class Kickchain implements Serializable {

        String text;

        public String getText() {
            return text;
        }

        public void setText(String text) {
            this.text = text;
        }
    }

    @GetMapping(value = "/result/new", produces = MediaType.APPLICATION_JSON_VALUE)
//    @GetMapping(value = "/result/new")
    @ResponseBody
    public Kickchain newResult() {
        Kickchain kc = new Kickchain();
        kc.setText("Hello");
        return kc;
    }

}
