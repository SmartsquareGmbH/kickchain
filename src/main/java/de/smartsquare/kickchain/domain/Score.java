package de.smartsquare.kickchain.domain;

public class Score {

    int goals1;

    int goals2;

    public Score() {
    }

    public Score(int goals1, int goals2) {
        this.goals1 = goals1;
        this.goals2 = goals2;
    }

    public int getGoals1() {
        return goals1;
    }

    public int getGoals2() {
        return goals2;
    }
}
