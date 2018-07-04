package de.smartsquare.kickchain.neo4j.entities;

import org.neo4j.ogm.annotation.*;

import java.time.Instant;

@RelationshipEntity(type = "FOLLOWS")
public class
FollowsRelationshipEntity {

    @Id
    @GeneratedValue
    private Long id;

    @StartNode
    private BlockNodeEntity startBlock;

    @EndNode
    private BlockNodeEntity endBlock;

    private Instant created;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public BlockNodeEntity getStartBlock() {
        return startBlock;
    }

    public void setStartBlock(BlockNodeEntity startBlock) {
        this.startBlock = startBlock;
    }

    public BlockNodeEntity getEndBlock() {
        return endBlock;
    }

    public void setEndBlock(BlockNodeEntity endBlock) {
        this.endBlock = endBlock;
    }

    public Instant getCreated() {
        return created;
    }

    public void setCreated(Instant created) {
        this.created = created;
    }

    @Override
    public String toString() {
        return "FollowsRelationshipEntity{" +
                "id=" + id +
                ", created=" + created +
                '}';
    }
}
