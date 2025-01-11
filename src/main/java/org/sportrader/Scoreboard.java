package org.sportrader;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Scoreboard {

    /*
       If we Look up is more than expected,we should use Map instead of List for better performance. To Make it simple I have used List
       There is no major difference when look up is less.
     */
    private final List<Match> matches;

    // Constructor to initialize the matches list and also if the scoreboard is accessed concurrently, handled here
    public Scoreboard() {
        this.matches = Collections.synchronizedList(new ArrayList<>());
    }

    /**
     * Start of the Match
     * @param homeTeam Name of the home team.
     * @param awayTeam Name of the away team.
     * @throws IllegalArgumentException if the match is not found or scores are invalid.
     */

    // Method to start a new match and add it to the list
    public void startMatch(String homeTeam, String awayTeam) {

        validateTeamNames(homeTeam, awayTeam);
        checkDuplicateMatch(homeTeam, awayTeam);

        Match match = new Match(homeTeam, awayTeam);
        matches.add(match);
    }

    // Helper method to validate team names
    private void validateTeamNames(String homeTeam, String awayTeam) {
        if (homeTeam == null || homeTeam.isEmpty() || awayTeam == null || awayTeam.isEmpty()) {
            throw new IllegalArgumentException("Team names cannot be null or empty.");
        }
        if (homeTeam.equals(awayTeam)) {
            throw new IllegalArgumentException("A team cannot play against itself.");
        }
    }

    // Helper method to check for duplicate matches
    private void checkDuplicateMatch(String homeTeam, String awayTeam) {
        boolean matchExists = matches.stream()
                .anyMatch(match -> match.getHomeTeam().equals(homeTeam) && match.getAwayTeam().equals(awayTeam));

        if (matchExists) {
            throw new IllegalArgumentException("This match is already in progress.");
        }
    }

    /**
     * Updates the score of an ongoing match.
     * @param homeTeam Name of the home team.
     * @param awayTeam Name of the away team.
     * @param homeScore Score of the home team (must be non-negative).
     * @param awayScore Score of the away team (must be non-negative).
     * @throws IllegalArgumentException if the match is not found or scores are invalid.
     */

    // Method to update scores of a specific match
    public void updateScore(String homeTeam, String awayTeam, int homeScore, int awayScore) {
       //Validate input scores
        validateScores(homeScore, awayScore);

        // Find the match to update
        Match matchToUpdate = matches.stream()
                .filter(match -> match.getHomeTeam().equals(homeTeam) && match.getAwayTeam().equals(awayTeam))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException(
                        "Match not found: " + homeTeam + " vs " + awayTeam));

        // Update the scores
        matchToUpdate.setScores(homeScore, awayScore);
    }

    // Helper method to validate scores
    private void validateScores(int homeScore, int awayScore) {
        if (homeScore < 0 || awayScore < 0) {
            throw new IllegalArgumentException("Scores must be non-negative. Received: "
                    + "homeScore=" + homeScore + ", awayScore=" + awayScore);
        }
    }

    /**
     * Finish of the match
     * @param homeTeam Name of the home team.
     * @param awayTeam Name of the away team.
     * @throws IllegalArgumentException if the match is not found or scores are invalid.
     */
    // Method to finish a match and remove it from the list
    public void finishMatch(String homeTeam, String awayTeam) {

        try {
            boolean matchRemoved = matches.removeIf(match -> match.getHomeTeam().equals(homeTeam) && match.getAwayTeam().equals(awayTeam));
            if (!matchRemoved) {
                throw new IllegalArgumentException("Match not found.");
            }
        }catch (Exception e){
            //System.err.println("Error finishing match: " + e.getMessage());
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
        return Collections.unmodifiableList(matches); // tried improving immutability
    }
}
