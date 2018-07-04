package de.smartsquare.kickchain.domain;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

public class Blockchain {

    private String name;

    private List<Block> chain = new ArrayList<>();

    public Blockchain() {
    }

    public Blockchain(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Block> getChain() {
        return chain;
    }

    public void setChain(List<Block> chain) {
        this.chain = chain;
    }

    public void addBlock(Block block) {
        getChain().add(block);
    }

    public Block lastBlock() throws NoSuchElementException {
        if (chain.isEmpty()) {
            throw new NoSuchElementException("Chain is empty.");
        }
        return chain.get(chain.size() - 1);
    }

    public Block getByIndex(long idx) {
        if (chain.isEmpty()) {
            throw new NoSuchElementException("Chain is empty.");
        }
        return chain.stream().filter(b -> b.getIndex() == idx).findFirst().get();
    }

    public int lastIndex() {
        return chain.size();
    }
}
