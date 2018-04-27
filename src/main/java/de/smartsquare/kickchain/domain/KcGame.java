package de.smartsquare.kickchain.domain;

public class KcGame {

    private KcTeam team1;

    private KcTeam team2;

    private KcScore score;

    public KcGame() {
    }

    public KcGame(KcTeam team1, KcTeam team2, KcScore score) {
        this.team1 = team1;
        this.team2 = team2;
        this.score = score;
    }

    public KcTeam getTeam1() {
        return team1;
    }

    public KcTeam getTeam2() {
        return team2;
    }

    public KcScore getScore() {
        return score;
    }
}
