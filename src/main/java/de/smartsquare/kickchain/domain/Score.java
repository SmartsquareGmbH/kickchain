package de.smartsquare.kickchain.domain;

import java.util.Objects;

public class Score {

    private int goals1;

    private int goals2;

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Score score = (Score) o;
        return goals1 == score.goals1 &&
                goals2 == score.goals2;
    }

    @Override
    public int hashCode() {
        return Objects.hash(goals1, goals2);
    }

}
