package de.smartsquare.kickchain.domain;

import java.time.Instant;
import java.util.List;

public class KcBlock {

    private int index;

    Instant timestamp;

    KcGame game;

    long proof;

    String previousHash;

    public KcBlock(int index, Instant timestamp, KcGame game, long proof, String previousHash) {
        this.index = index;
        this.timestamp = timestamp;
        this.game = game;
        this.proof = proof;
        this.previousHash = previousHash;
    }

    public int getIndex() {
        return index;
    }

    public Instant getTimestamp() {
        return timestamp;
    }

    public KcGame getGame() {
        return game;
    }

    public long getProof() {
        return proof;
    }

    public String getPreviousHash() {
        return previousHash;
    }

}
