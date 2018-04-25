package de.smartsquare.kickchain.domain;

import java.util.List;

public class KcFullChain {

    private List<KcBlock> chain;

    private int length;


    public KcFullChain(List<KcBlock> chain) {
        this.chain = chain;
        length = chain.size();
    }

    public List<KcBlock> getChain() {
        return chain;
    }

    public int getLength() {
        return length;
    }
}
