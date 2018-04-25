package de.smartsquare.kickchain.domain;

import java.time.Instant;
import java.util.List;

public class KcBlock {

    private int index;

    Instant timestamp;

    List<KcTransaction> transactions;

    long proof;

    String previousHash;

    public KcBlock(int index, Instant timestamp, List<KcTransaction> transactions, long proof, String previousHash) {
        this.index = index;
        this.timestamp = timestamp;
        this.transactions = transactions;
        this.proof = proof;
        this.previousHash = previousHash;
    }

    public int getIndex() {
        return index;
    }

    public Instant getTimestamp() {
        return timestamp;
    }

    public List<KcTransaction> getTransactions() {
        return transactions;
    }

    public long getProof() {
        return proof;
    }

    public String getPreviousHash() {
        return previousHash;
    }

}
