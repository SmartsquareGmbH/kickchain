package de.smartsquare.kickchain.domain;

public class KcScore {

    int goals1;

    int goals2;

    public KcScore() {
    }

    public KcScore(int goals1, int goals2) {
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
