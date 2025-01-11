package org.sportrader;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Scoreboard {
     private final List<Match> matches;

    // Constructor to initialize the matches list and also if the scoreboard is accessed concurrently, handled here
    public Scoreboard() {
        this.matches = Collections.synchronizedList(new ArrayList<>());
    }

    // Method to start a new match and add it to the list
    public void startMatch(String homeTeam, String awayTeam) {

        if (homeTeam == null || homeTeam.isEmpty() || awayTeam == null || awayTeam.isEmpty()) {
            throw new IllegalArgumentException("Team names cannot be null or empty.");
        }
        if (homeTeam.equals(awayTeam)) {
            throw new IllegalArgumentException("A team cannot play against itself.");
        }
        for (Match match : matches) {
            if (match.getHomeTeam().equals(homeTeam) && match.getAwayTeam().equals(awayTeam)) {
                throw new IllegalArgumentException("This match is already in progress.");
            }
        }
        Match match = new Match(homeTeam, awayTeam);
        matches.add(match);
    }

    // Method to update scores of a specific match
    public void updateScore(String homeTeam, String awayTeam, int homeScore, int awayScore) {
        Match matchToUpdate = null;
        for (Match match : matches) {
            if (match.getHomeTeam().equals(homeTeam) && match.getAwayTeam().equals(awayTeam)) {
                matchToUpdate =match;
                break;
            }
        }

        if(matchToUpdate==null){
            throw new IllegalArgumentException("Match not found.");
        }

        if (homeScore < 0 || awayScore < 0) {
            throw new IllegalArgumentException("Scores cannot be negative.");
        }

        try {
            matchToUpdate.setScores(homeScore, awayScore);
        } catch (Exception e) {
            System.err.println("Error updating score: " + e.getMessage());
        }
    }

    // Method to finish a match and remove it from the list
    public void finishMatch(String homeTeam, String awayTeam) {

        try {
            boolean matchRemoved = matches.removeIf(match -> match.getHomeTeam().equals(homeTeam) && match.getAwayTeam().equals(awayTeam));
            if (!matchRemoved) {
                throw new IllegalArgumentException("Match not found.");
            }
        }catch (Exception e){
            System.err.println("Error finishing match: " + e.getMessage());
            throw e;
        }
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
