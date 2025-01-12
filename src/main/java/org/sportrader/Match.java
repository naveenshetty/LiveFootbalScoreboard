package org.sportrader;

import java.time.Instant;

public class Match {
    private final String homeTeam;
    private final String awayTeam;
    private int homeScore;
    private int awayScore;
    private long startTime;
    private int startOrder; // New field to track the order of addition


    public Match(String homeTeam, String awayTeam, int startOrder) {
        this.homeTeam = homeTeam;
        this.awayTeam = awayTeam;
        this.homeScore = 0;
        this.awayScore = 0;
        //this.startTime = System.currentTimeMillis();
        this.startOrder = startOrder;

    }

    public String getHomeTeam() {
        return homeTeam;
    }

    public String getAwayTeam() {
        return awayTeam;
    }

    public int getHomeScore() {
        return homeScore;
    }

    public int getAwayScore() {
        return awayScore;
    }

    public long getStartTime() {
        return startTime;
    }

    public int getStartOrder() {
        return startOrder;
    }

    public void setScores(int homeScore, int awayScore) {
        this.homeScore = homeScore;
        this.awayScore = awayScore;
    }

    public int getTotalScore() {
        return homeScore + awayScore;
    }
}
