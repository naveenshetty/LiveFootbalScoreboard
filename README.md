Live Football World Cup Scoreboard

Overview

This Java application provides a "Live Football World Cup Scoreboard" system to manage live match scores. The system allows you to:

1. Start new matches.
2. Update the scores of ongoing matches.
3. Finish matches and remove them from the scoreboard.
4. Retrieve a summary of ongoing matches, sorted by total score and start time.

The application uses object-oriented principles to encapsulate the match and scoreboard functionalities, and it includes test cases for comprehensive testing of all core features.

Features

* Start Match: Add a match to the scoreboard with initial scores of 0-0.
* Update Score: Update the scores of an ongoing match.
* Finish Match: Remove a match from the scoreboard once it is completed.
* Get Summary: Retrieve an up-to-date list of ongoing matches, 
               sorted:Primarily by total score in descending order. 
               Secondarily by start time (most recent first).


Classes
   
1. Match

This class models an individual football match.

Key Properties: homeTeam & awayTeam: Names of the teams.
homeScore & awayScore: Scores of the respective teams.
startTime: Timestamp when the match started.

Methods:
*    setScores(int homeScore, int awayScore): Updates the scores.
*    getTotalScore(): Calculates the total score of the match.

2. Scoreboard

This class manages the list of ongoing matches.
Key Methods:

* startMatch(String homeTeam, String awayTeam): Adds a new match.
* updateScore(String homeTeam, String awayTeam, int homeScore, int awayScore): Updates match scores.
* finishMatch(String homeTeam, String awayTeam): Removes a match from the list.
* getSummary(): Returns a sorted list of matches.

Getting Started
Prerequisites
Java Development Kit (JDK) 8 or higher (Used java 17).
A Java IDE (e.g., IntelliJ IDEA, Eclipse) or a text editor with Java support.
How to Run
Clone the repository or copy the project files.
Open the project in your IDE.
Compile and run the Main class to interact with the scoreboard system.

Testing
The project includes a comprehensive suite of tests in ScoreboardTest. These tests cover:

* Starting matches.
* Updating scores.
* Finishing matches.
* Retrieving summaries.

Key Points

* Assumed that Look up will be less so used List DSA , if we want to look up for more than expected then better to use Map for better performance.
* Thread Safety: The scoreboard uses a synchronized list to handle concurrent access.
* Error Handling: Proper exceptions are thrown for invalid operations (e.g., negative scores, duplicate matches).
* Sorting: Matches are sorted based on the problem statement, ensuring the most exciting matches are listed first.
* This is not a production ready code so if we need to enhance for production code like would integrate a database, logger etc