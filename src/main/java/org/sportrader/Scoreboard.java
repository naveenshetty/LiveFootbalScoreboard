package org.sportrader;

import java.util.ArrayList;
import java.util.List;

public class Scoreboard {
    private final List<Match> matches;

    // Constructor to initialize the matches list
    public Scoreboard() {
        this.matches = new ArrayList<>();
    }

    // Method to start a new match and add it to the list
    public void startMatch(String homeTeam, String awayTeam) {
        Match match = new Match(homeTeam, awayTeam);
        matches.add(match);
    }

    // Method to update scores of a specific match
    public void updateScore(String homeTeam, String awayTeam, int homeScore, int awayScore) {
        for (Match match : matches) {
            if (match.getHomeTeam().equals(homeTeam) && match.getAwayTeam().equals(awayTeam)) {
                match.setScores(homeScore, awayScore);
                return;
            }
        }
        throw new IllegalArgumentException("Match not found.");
    }

    // Method to finish a match and remove it from the list
    public void finishMatch(String homeTeam, String awayTeam) {
        matches.removeIf(match -> match.getHomeTeam().equals(homeTeam) && match.getAwayTeam().equals(awayTeam));
    }

    // Method to get summary of matches currently in progress
    public List<Match> getSummary() {
        // Sort matches primarily by descending total score, then by start time
        matches.sort((m1, m2) -> {
            int scoreComparison = Integer.compare(m2.getTotalScore(), m1.getTotalScore());
            if (scoreComparison != 0) {
                return scoreComparison;
            } else {
                return Long.compare(m2.getStartTime(), m1.getStartTime());
            }
        });
        return matches;
    }

    public List<Match> getMatches() {
        return new ArrayList<>(matches); // Return a copy for immutability.
    }
}
