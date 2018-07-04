package de.smartsquare.kickchain.neo4j.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.ObjectMapper;
import de.smartsquare.kickchain.MessageDigestUtils;
import org.neo4j.ogm.annotation.GeneratedValue;
import org.neo4j.ogm.annotation.Id;
import org.neo4j.ogm.annotation.NodeEntity;
import org.neo4j.ogm.annotation.Relationship;

import java.io.IOException;
import java.io.StringWriter;
import java.security.NoSuchAlgorithmException;
import java.time.Instant;

@NodeEntity
public class BlockNodeEntity {

    @Id
    @GeneratedValue
    private Long id;

    private long index;

    private String blockchain;

    private Instant timestamp;

    private long proof;

    private String previousHash;

    @Relationship(type = "HASGAME", direction = "INCOMING")
    private HasGamesRelationshipEntity game;

    @JsonIgnore
    @Relationship(type = "FOLLOWS")
    private FollowsRelationshipEntity follows;

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

    public FollowsRelationshipEntity getFollows() {
        return follows;
    }

    public void setFollows(FollowsRelationshipEntity follows) {
        this.follows = follows;
    }

    public HasGamesRelationshipEntity getGame() {
        return game;
    }

    public void setGame(HasGamesRelationshipEntity game) {
        this.game = game;
    }

    public String toHash() throws IOException, NoSuchAlgorithmException {
        ObjectMapper mapper = new ObjectMapper();
        StringWriter writer = new StringWriter();
        mapper.writeValue(writer, this);
        return MessageDigestUtils.sha256(writer.toString());
    }

    @Override
    public String toString() {
        return "BlockNodeEntity{" +
                "id=" + id +
                ", index=" + index +
                ", blockchain='" + blockchain + '\'' +
                ", timestamp=" + timestamp +
                ", proof=" + proof +
                ", follows=" + follows +
                '}';
    }

}
