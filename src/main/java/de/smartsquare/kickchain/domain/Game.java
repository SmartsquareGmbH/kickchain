package de.smartsquare.kickchain.domain;

public class Game implements BlockContent {

    private Team team1;

    private Team team2;

    private Score score;

    public Game() {
    }

    public Game(Team team1, Team team2, Score score) {
        this.team1 = team1;
        this.team2 = team2;
        this.score = score;
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
}
