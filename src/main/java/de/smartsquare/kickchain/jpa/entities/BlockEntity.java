package de.smartsquare.kickchain.jpa.entities;

import de.smartsquare.kickchain.domain.Game;

import javax.persistence.*;
import java.time.Instant;
import java.util.List;

@Entity
public class BlockEntity {

    @Id
    @GeneratedValue
    private Long id;

    private long index;

    private String blockchain;

    private Instant timestamp;

    private long proof;

    private String previousHash;

    private String transactionHash;

    @OneToMany(fetch = FetchType.EAGER)
    @JoinColumn(name = "game_id")
    private List<GameEntity> games;

    @ManyToOne
    @JoinColumn(name = "follows_block")
    private BlockEntity follows;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public long getIndex() {
        return index;
    }

    public void setIndex(long index) {
        this.index = index;
    }

    public String getBlockchain() {
        return blockchain;
    }

    public void setBlockchain(String blockchain) {
        this.blockchain = blockchain;
    }

    public Instant getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Instant timestamp) {
        this.timestamp = timestamp;
    }

    public long getProof() {
        return proof;
    }

    public void setProof(long proof) {
        this.proof = proof;
    }

    public String getPreviousHash() {
        return previousHash;
    }

    public void setPreviousHash(String previousHash) {
        this.previousHash = previousHash;
    }

    public String getTransactionHash() {
        return transactionHash;
    }

    public void setTransactionHash(String transactionHash) {
        this.transactionHash = transactionHash;
    }

    public List<GameEntity> getGames() {
        return games;
    }

    public void setGames(List<GameEntity> games) {
        this.games = games;
    }

    public BlockEntity getFollows() {
        return follows;
    }

    public void setFollows(BlockEntity follows) {
        this.follows = follows;
    }
}
