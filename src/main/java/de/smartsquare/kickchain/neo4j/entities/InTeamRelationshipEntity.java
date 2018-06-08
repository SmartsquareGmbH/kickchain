package de.smartsquare.kickchain.neo4j.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.neo4j.ogm.annotation.*;

@RelationshipEntity(type = "IN_TEAM")
public class InTeamRelationshipEntity {

    @Id
    @GeneratedValue
    private Long id;

    @JsonIgnore
    @StartNode
    GameNodeEntity gameNode;

    @EndNode
    PlayerNodeEntity playerNode;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public GameNodeEntity getGameNode() {
        return gameNode;
    }

    public void setGameNode(GameNodeEntity gameNode) {
        this.gameNode = gameNode;
    }

    public PlayerNodeEntity getPlayerNode() {
        return playerNode;
    }

    public void setPlayerNode(PlayerNodeEntity playerNode) {
        this.playerNode = playerNode;
    }

    @Override
    public String toString() {
        return "InTeamRelationshipEntity{" +
                "id=" + id +
                ", gameNode=" + gameNode +
                ", playerNode=" + playerNode +
                '}';
    }
}
