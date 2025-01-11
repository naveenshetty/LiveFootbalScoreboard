import org.junit.Before;
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
    @Test(expected = IllegalArgumentException.class)
    public void testStartMatchWithSameTeamsThrowsException() {
        //same teams can't play against themselves
        scoreboard.startMatch("Brazil", "Brazil");
    }

    @Test(expected = IllegalArgumentException.class)
    public void testStartMatchWhenAlreadyInProgressThrowsException() {
        // Start a match and ensure duplicate start throws exception
        scoreboard.startMatch("Spain", "Brazil");
        scoreboard.startMatch("Spain", "Brazil");
    }

    @Test(expected = IllegalArgumentException.class)
    public  void testStartMatchNull(){
        scoreboard.startMatch(null,"Brazil");
    }

    @Test(expected = IllegalArgumentException.class)
    public  void testStartMatchEmpty(){
        scoreboard.startMatch("Argentina","");
        scoreboard.startMatch("","");
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
    @Test(expected = IllegalArgumentException.class)
    public void testUpdateScoreForNonExistentMatchThrowsException() {
        // Attempt to update score for a match that doesn't exist
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

    @Test(expected = IllegalArgumentException.class)
    public void testFinishNonExistentMatch() {
        // Attempt to finish match that doesn't exist
        scoreboard.finishMatch("Spain", "Germany");
    }


    @Test
    public void testGetSummary() {
        Scoreboard scoreboard = new Scoreboard();
        scoreboard.startMatch("Argentina", "Australia");
        scoreboard.startMatch("Spain", "Brazil");
        scoreboard.startMatch("Germany", "France");
        scoreboard.updateScore("Argentina", "Australia", 3, 1);
        scoreboard.updateScore("Spain", "Brazil", 10, 2);
        scoreboard.updateScore("Germany", "France", 2, 2);

        List<Match> summary = scoreboard.getSummary();
        assertEquals("Spain", summary.get(0).getHomeTeam());
        assertEquals("Brazil", summary.get(0).getAwayTeam());
        assertEquals("Argentina", summary.get(1).getHomeTeam());
        assertEquals("Australia", summary.get(1).getAwayTeam());
    }


}
