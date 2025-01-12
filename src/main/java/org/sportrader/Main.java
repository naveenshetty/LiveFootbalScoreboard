package org.sportrader;

import java.util.List;

public class Main {
    public static void main(String[] args) {

        System.out.println("Live Football World Cup Scoreboard!");
        Scoreboard scoreboard = new Scoreboard();

        // Start some matches
        scoreboard.startMatch("Mexico", "Canada");
        scoreboard.startMatch("Spain", "Brazil");
        scoreboard.startMatch("Germany", "France");
        scoreboard.startMatch("Uruguay", "Italy");
        scoreboard.startMatch("Argentina", "Australia");

        // Update their scores
        scoreboard.updateScore("Mexico", "Canada", 0, 5);
        scoreboard.updateScore("Spain", "Brazil", 10, 2);
        scoreboard.updateScore("Germany", "France", 2, 2);
        scoreboard.updateScore("Uruguay", "Italy", 6, 6);
        scoreboard.updateScore("Argentina", "Australia", 3, 1);

        // Get and print the summary of ongoing matches
        List<Match> summary = scoreboard.getSummary();
        for (Match match : summary) {
            System.out.println(match.getHomeTeam() + " " + match.getHomeScore() + " - " + match.getAwayTeam() + " " + match.getAwayScore());
        }
    }
}