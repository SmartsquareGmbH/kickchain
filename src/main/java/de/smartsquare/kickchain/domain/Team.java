package de.smartsquare.kickchain.domain;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

public class Team {

    private final List<String> players = new ArrayList<>();

    public Team() {
    }

    public Team(String... players) {
        this.players.addAll(Arrays.asList(players));
    }

    public Team(List<String> players) {
        this.players.addAll(players);
    }

    public List<String> getPlayers() {
        return players;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Team team = (Team) o;
        return Objects.equals(players, team.players);
    }

    @Override
    public int hashCode() {
        return Objects.hash(players);
    }

}
