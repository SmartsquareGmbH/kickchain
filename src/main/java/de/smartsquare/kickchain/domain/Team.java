package de.smartsquare.kickchain.domain;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Team {

    private final List<String> players = new ArrayList<>();

    public Team() {
    }

    public Team(String... players) {
        this.players.addAll(Arrays.asList(players));
    }

    public List<String> getPlayers() {
        return players;
    }

}
