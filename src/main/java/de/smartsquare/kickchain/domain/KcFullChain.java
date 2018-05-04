package de.smartsquare.kickchain.domain;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

public class KcFullChain {

    private List<KcBlock> chain = new ArrayList<>();

    public KcFullChain() {
    }

    public List<KcBlock> getChain() {
        return chain;
    }

    public void setChain(List<KcBlock> chain) {
        this.chain = chain;
    }

    public int addBlock(KcBlock block) {
        getChain().add(block);
        return getChain().size();
    }

    public KcBlock lastBlock() throws NoSuchElementException {
        if (chain.isEmpty()) {
            throw new NoSuchElementException("Chain is empty.");
        }
        return chain.get(chain.size() - 1);
    }

    public int lastIndex() {
        return chain.size();
    }
}
