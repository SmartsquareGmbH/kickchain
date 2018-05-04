package de.smartsquare.kickchain.domain;

import java.time.Instant;

public class Block<T extends BlockContent> {

    private int index;

    private Instant timestamp;

    private T blockContent;

    private long proof;

    private String previousHash;

    public Block(int index, Instant timestamp, T blockContent, long proof, String previousHash) {
        this.index = index;
        this.timestamp = timestamp;
        this.blockContent = blockContent;
        this.proof = proof;
        this.previousHash = previousHash;
    }

    private Block() {
    }

    public int getIndex() {
        return index;
    }

    public Instant getTimestamp() {
        return timestamp;
    }

    public T getBlockContent() {
        return blockContent;
    }

    public long getProof() {
        return proof;
    }

    public String getPreviousHash() {
        return previousHash;
    }

}
