package de.smartsquare.kickchain.neo4j.entities;

import org.neo4j.ogm.annotation.*;

import java.time.Instant;
import java.util.List;

@NodeEntity
public class GameNodeEntity {

    @Id
    @GeneratedValue
    private Long id;

    @Property
    private Instant created;

//    @Relationship(type = "IN_TEAM", direction = "INCOMING")
//    private List<PlayerNodeEntity> team1;
//
//    @Relationship(type = "IN_TEAM", direction = "INCOMING")
//    private List<PlayerNodeEntity> team2;

    @Property
    private List<String> team1;

    @Property
    private List<String> team2;

    @Property
    private int score1;

    @Property
    private int score2;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Instant getCreated() {
        return created;
    }

    public void setCreated(Instant created) {
        this.created = created;
    }

    public List<String> getTeam1() {
        return team1;
    }

    public void setTeam1(List<String> team1) {
        this.team1 = team1;
    }

    public List<String> getTeam2() {
        return team2;
    }

    public void setTeam2(List<String> team2) {
        this.team2 = team2;
    }

    public int getScore1() {
        return score1;
    }

    public void setScore1(int score1) {
        this.score1 = score1;
    }

    public int getScore2() {
        return score2;
    }

    public void setScore2(int score2) {
        this.score2 = score2;
    }

    @Override
    public String toString() {
        return "GameNodeEntity{" +
                "id=" + id +
                ", created=" + created +
                ", team1=" + team1 +
                ", team2=" + team2 +
                ", score1=" + score1 +
                ", score2=" + score2 +
                '}';
    }
}
