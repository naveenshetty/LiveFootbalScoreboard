import org.junit.Before;
import org.sportrader.Exception.FinishedMatchException;
import org.sportrader.Exception.InvalidTeamNameException;
import org.sportrader.Exception.MatchNotFoundException;
import org.sportrader.Match;
import org.sportrader.Scoreboard;
import org.junit.Test;

import java.util.List;

import static junit.framework.TestCase.assertEquals;
import static org.junit.Assert.assertTrue;

public class ScoreboardTest {

    private Scoreboard scoreboard;

    @Before
    public void setUp() {
        scoreboard = new Scoreboard();
    }
    //Test Case for startMatch: Test that starting a match add it to the scoreboard with a score 0-0
    @Test
    public void testStartMatch() {

        /*
        Positive scenario
         */
        scoreboard.startMatch("Argentina","Brazil");

        List<Match> matches = scoreboard.getMatches();
        assertEquals(1,matches.size()); //Instead of Assertions. used static while import, making test easy to read and concise
        assertEquals("Argentina",matches.get(0).getHomeTeam());
        assertEquals("Brazil",matches.get(0).getAwayTeam());
        assertEquals(0,matches.get(0).getHomeScore());
        assertEquals(0,matches.get(0).getAwayScore());

    }

    /*
       Negative scenarios
     */
    @Test(expected = InvalidTeamNameException.class)
    public void testStartMatchWithSameTeamsThrowsException() {
        //same teams can't play against themselves
        scoreboard.startMatch("Brazil", "Brazil");
    }

    @Test(expected = MatchNotFoundException.class)
    public void testStartMatchWhenAlreadyInProgressThrowsException() {
        // Start a match and ensure duplicate start throws exception
        scoreboard.startMatch("Spain", "Brazil");
        scoreboard.startMatch("Spain", "Brazil");
    }

    @Test(expected = InvalidTeamNameException.class)
    public  void testStartMatchNull(){
        scoreboard.startMatch(null,"Brazil");
    }

    @Test(expected = InvalidTeamNameException.class)
    public  void testStartMatchEmpty(){
        scoreboard.startMatch("Argentina","");
        scoreboard.startMatch("","");
    }

    @Test(expected = MatchNotFoundException.class)
    public void testStartMatchWithReversedTeamsThrowsException() {
        scoreboard.startMatch("Mexico", "Canada");
        scoreboard.startMatch("Canada", "Mexico");
    }

    @Test(expected = MatchNotFoundException.class)
    public void testStartMatchWithCaseInsensitiveTeamsThrowsException() {
        scoreboard.startMatch("Mexico", "Canada");
        scoreboard.startMatch("mexico", "Canada");  // This should throw an exception
    }

    /*
        Positive scenario
    */
    @Test
   public void testUpdateScore() {
        Scoreboard scoreboard = new Scoreboard();
        scoreboard.startMatch("Argentina", "Brazil");
        scoreboard.updateScore("Argentina", "Brazil", 3, 2);

        Match match = scoreboard.getMatches().get(0);
        assertEquals(3, match.getHomeScore());
        assertEquals(2, match.getAwayScore());
    }

    /*
       Negative scenarios
     */
    @Test(expected = FinishedMatchException.class)
    public void testUpdateScoreForNonExistentMatchThrowsException() {
        // Attempt to update score for a match that doesn't exist or trying to update for the finished match
        scoreboard.updateScore("NonExistentTeam", "AnotherOne", 1, 1);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testUpdateScoreForNegativeScroe() {
        // Attempt to update negative score
        scoreboard.updateScore("Argentina", "Brazil", -1, -2);
        scoreboard.updateScore("Argentina", "Brazil", 1, -2);
    }
    @Test
   public void testFinishMatch() {
        Scoreboard scoreboard = new Scoreboard();
        scoreboard.startMatch("Argentina", "Brazil");
        scoreboard.finishMatch("Argentina", "Brazil");

        List<Match> matches = scoreboard.getMatches();
        assertTrue(matches.isEmpty());
    }

    @Test(expected = MatchNotFoundException.class)
    public void testFinishNonExistentMatch() {
        // Attempt to finish match that doesn't exist
        scoreboard.finishMatch("Spain", "Germany");
    }

    @Test(expected = FinishedMatchException.class)
    public void testUpdateScoreForFinishedMatchThrowsException() {
        scoreboard.startMatch("Argentina", "Brazil");
        scoreboard.finishMatch("Argentina", "Brazil");  // Finish the match
        scoreboard.updateScore("Argentina", "Brazil", 3, 2);
    }


    @Test
    public void testGetSummary() {
        scoreboard.startMatch("Mexico", "Canada");
        scoreboard.updateScore("Mexico", "Canada", 0, 5);

        scoreboard.startMatch("Spain", "Brazil");
        scoreboard.updateScore("Spain", "Brazil", 10, 2);

        scoreboard.startMatch("Germany", "France");
        scoreboard.updateScore("Germany", "France", 2, 2);

        scoreboard.startMatch("Uruguay", "Italy");
        scoreboard.updateScore("Uruguay", "Italy", 6, 6);

        scoreboard.startMatch("Argentina", "Australia");
        scoreboard.updateScore("Argentina", "Australia", 3, 1);

        List<Match> summary = scoreboard.getSummary();

        assertEquals("Uruguay", summary.get(0).getHomeTeam());
        assertEquals("Italy", summary.get(0).getAwayTeam());

        assertEquals("Spain", summary.get(1).getHomeTeam());
        assertEquals("Brazil", summary.get(1).getAwayTeam());

        assertEquals("Mexico", summary.get(2).getHomeTeam());
        assertEquals("Canada", summary.get(2).getAwayTeam());

        assertEquals("Argentina", summary.get(3).getHomeTeam());
        assertEquals("Australia", summary.get(3).getAwayTeam());

        assertEquals("Germany", summary.get(4).getHomeTeam());
        assertEquals("France", summary.get(4).getAwayTeam());
    }

    @Test
    public void testLargeNumberOfMatches() {
        // Performance test case
        for (int i = 0; i < 1000; i++) {
            scoreboard.startMatch("HomeTeam" + i, "AwayTeam" + i);
            scoreboard.updateScore("HomeTeam" + i, "AwayTeam" + i, i, i + 1);
        }
        assertEquals(1000, scoreboard.getSummary().size());
        // Finish all matches to test removal efficiency
        for (int i = 0; i < 1000; i++) {
            scoreboard.finishMatch("HomeTeam" + i, "AwayTeam" + i);
        }
        assertEquals(0, scoreboard.getSummary().size());
    }

}
