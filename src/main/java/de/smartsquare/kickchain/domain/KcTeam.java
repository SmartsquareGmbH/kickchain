package de.smartsquare.kickchain.domain;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class KcTeam {

    private final List<String> players = new ArrayList<>();

    public KcTeam() {
    }

    public KcTeam(String... players) {
        this.players.addAll(Arrays.asList(players));
    }

    public List<String> getPlayers() {
        return players;
    }

}
