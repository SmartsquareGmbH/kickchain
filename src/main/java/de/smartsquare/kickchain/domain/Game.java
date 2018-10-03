package de.smartsquare.kickchain.domain;

import java.util.Objects;

public class Game implements BlockContent {

    private Team team1;
    private Team team2;
    private Score score;
    private String signature;

    public Game() {
    }

    public Game(Team team1, Team team2, Score score, String signature) {
        this.team1 = team1;
        this.team2 = team2;
        this.score = score;
        this.signature = signature;
    }

    public Team getTeam1() {
        return team1;
    }

    public Team getTeam2() {
        return team2;
    }

    public Score getScore() {
        return score;
    }

    public String getSignature() {
        return signature;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Game game = (Game) o;
        return Objects.equals(team1, game.team1) &&
                Objects.equals(team2, game.team2) &&
                Objects.equals(score, game.score) &&
                Objects.equals(signature, game.signature);
    }

    @Override
    public int hashCode() {

        return Objects.hash(team1, team2, score, signature);
    }



}
