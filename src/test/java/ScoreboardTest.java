import org.example.Match;
import org.example.Scoreboard;
import org.junit.Test;

import java.util.List;

import static junit.framework.TestCase.assertEquals;

public class ScoreboardTest {


    //Test Case for startMatch: Test that starting a match add it to the scoreboard with a score 0-0
    @Test
    void testStartMatch() {

        Scoreboard scoreboard = new Scoreboard();
        scoreboard.startMatch("Argentina","Brazil");

        List<Match> matches = scoreboard.getMatches();
        assertEquals(1,matches); //Instead of Assertions. used static while import, making test easy to read and concise
        assertEquals("Argentina",matches.get(0).getHomeTeam());
        assertEquals("Brazil",matches.get(0).getAwayHome());
        assertEquals(0,matches.get(0).getHomeScore());
        assertEquals(0,matches.get(0).getAwayScore());

    }

    @Test
    void testUpdateScore() {
        Scoreboard scoreboard = new Scoreboard();
        scoreboard.startMatch("Argentina", "Brazil");
        scoreboard.updateScore("Argentina", "Brazil", 3, 2);

        Match match = scoreboard.getMatches().get(0);
        assertEquals(3, match.getHomeScore());
        assertEquals(2, match.getAwayScore());
    }

    @Test
    void testFinishMatch() {
        Scoreboard scoreboard = new Scoreboard();
        scoreboard.startMatch("Argentina", "Brazil");
        scoreboard.finishMatch("Argentina", "Brazil");

        List<Match> matches = scoreboard.getMatches();
        assertTrue(matches.isEmpty());
    }

    @Test
    void testGetSummary() {
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
