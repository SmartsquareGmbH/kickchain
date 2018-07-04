package de.smartsquare.kickchain.neo4j.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.neo4j.ogm.annotation.*;

@RelationshipEntity(type = "HASGAME")
public class HasGamesRelationshipEntity {

    @Id
    @GeneratedValue
    private Long id;

    @JsonIgnore
    @EndNode
    private BlockNodeEntity startBlock;

    @StartNode
    private GameNodeEntity endGame;

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

    public GameNodeEntity getEndGame() {
        return endGame;
    }

    public void setEndGame(GameNodeEntity endGame) {
        this.endGame = endGame;
    }
}
