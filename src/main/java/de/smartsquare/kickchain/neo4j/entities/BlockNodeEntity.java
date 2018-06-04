package de.smartsquare.kickchain.neo4j.entities;

import org.neo4j.ogm.annotation.GeneratedValue;
import org.neo4j.ogm.annotation.Id;
import org.neo4j.ogm.annotation.NodeEntity;

import java.time.Instant;

@NodeEntity
public class BlockNodeEntity {

    @Id
    @GeneratedValue
    private Long id;

    private int index;

    private Instant timestamp;

    private String previousHash;

    private long proof;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    public Instant getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Instant timestamp) {
        this.timestamp = timestamp;
    }

    public String getPreviousHash() {
        return previousHash;
    }

    public void setPreviousHash(String previousHash) {
        this.previousHash = previousHash;
    }

    public long getProof() {
        return proof;
    }

    public void setProof(long proof) {
        this.proof = proof;
    }

    @Override
    public String toString() {
        return "BlockNodeEntity{" +
                "id=" + id +
                ", index=" + index +
                ", timestamp=" + timestamp +
                ", previousHash='" + previousHash + '\'' +
                ", proof=" + proof +
                '}';
    }
}
