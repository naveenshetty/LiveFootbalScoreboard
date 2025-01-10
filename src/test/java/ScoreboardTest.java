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
}
