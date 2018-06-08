package de.smartsquare.kickchain.domain;

import de.smartsquare.kickchain.BlockchainException;
import de.smartsquare.kickchain.MessageDigestUtils;

import java.security.NoSuchAlgorithmException;
import java.util.Objects;

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

    @Override
    public String getTransactionId() {
        String trxHash = String.format("%d-%d-%d", team1.hashCode(), team2.hashCode(), score.hashCode());
        try {
            return MessageDigestUtils.sha256(trxHash);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            return trxHash;
        }
    }

    @Override
    public String getSignature() throws BlockchainException {
        try {
            return MessageDigestUtils.sha256(String.format("%s", getTransactionId()));
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            throw new BlockchainException("Unable to get Signature for transaction " + getTransactionId());
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Game game = (Game) o;
        return Objects.equals(team1, game.team1) &&
                Objects.equals(team2, game.team2) &&
                Objects.equals(score, game.score) ;
    }

    @Override
    public int hashCode() {

        return Objects.hash(team1, team2, score);
    }
}
