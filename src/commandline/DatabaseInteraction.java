package commandline;

import java.sql.DriverManager;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class DatabaseInteraction {
    private int gamesPlayed;
    private int playerWins;
    private int compWins;
    private int avgDraws;
    private int longestRound;
    private int game_number;

    //database sign in credentials
    static final String USERNAME = "m_18_2208247s";
    static final String PASSWORD = "2208247s";
                
    //jdbc driver name and database url
    static final String JDBC_DRIVER = "org.postgresql.Driver";
    static final String DB_URL = "jdbc:postgresql://yacata.dcs.gla.ac.uk:5432/";


        public DatabaseInteraction(){

        }

        public static void insertGameStats(int[] gameResults){
             //load JDBC Driver

             int humanRoundWins = gameResults[0]; 
             int aiOneWins = gameResults[1];
             int aiTwoWins = gameResults[2];
             int aiThreeWins = gameResults[3];
             int aiFourWins = gameResults[4];
             int numOfRounds = gameResults[5];
             int drawsNumber = gameResults[6];

             String overallGameWinner = calculateOverallWinner(gameResults);

             try{
                Class.forName(JDBC_DRIVER);
                } catch (ClassNotFoundException e){
                    System.out.println("Could not find JDBC Driver");
                    e.printStackTrace();
                    return;
                    }
                    
                    System.out.println("Driver found");
                    
                    Connection connection = null;
                    Statement stmt = null;
                    
                    
                    
                    try {
                    connection = DriverManager.getConnection(DB_URL, USERNAME, PASSWORD);
                    } catch(SQLException e){
                    System.out.println("Connection Failed");
                    e.printStackTrace();
                    return;
                    }
                    
                    if (connection != null){
                    
                    try{
                    stmt = connection.createStatement();
                    //First query
                    ResultSet rs = stmt.executeQuery("INSERT INTO game(human_round_win, ai_one_round_win, ai_two_round_win, ai_three_round_win, ai_four_round_win, total_rounds, total_draws, overall_winner) " + 
                    "VALUES (humanRoundWins, aiOneWins, aiTwoWins, aiThreeWins, aiFourWins, numOfRounds, drawsNumber, overallGameWinner)");
    
                
    
                    }catch (SQLException e){
                    e.printStackTrace();
                    }
                }


        }
                
                
        public void getGameStats(){
        //load JDBC Driver
        	int game_number;
            try{
            Class.forName(JDBC_DRIVER);
            } catch (ClassNotFoundException e){
                System.out.println("Could not find JDBC Driver");
                e.printStackTrace();
                return;
                }
                
                System.out.println("Driver found");
                
                Connection connection = null;
                Statement stmt = null;
                
                
                
                try {
                connection = DriverManager.getConnection(DB_URL, USERNAME, PASSWORD);
                } catch(SQLException e){
                System.out.println("Connection Failed");
                e.printStackTrace();
                return;
                }
                
                if (connection != null){
                
                try{
                Statement statement = connection.createStatement();
                //First query
                ResultSet rs = statement.executeQuery("SELECT COUNT(game_number) AS count" +
                " FROM game");
                this.game_number = rs.getInt("count");

                //second query
                rs = statement.executeQuery("SELECT COUNT (overall_winner) AS AIWins"
               + " FROM game" +
                " WHERE overall_winner <> 'human'");
                this.compWins = rs.getInt("AIWins");
                this.playerWins = gamesPlayed - compWins;

                //Getting averages   
                rs = statement.executeQuery("SELECT AVG (total_draws) AS numDraws" + 
                " FROM game");
                this.avgDraws = rs.getInt("numDraws");

                //Longest Round
                rs = statement.executeQuery("SELECT MAX (total_rounds) AS biggstRound" +
                " FROM game");
                this.longestRound = rs.getInt("biggstRound");

                }catch (SQLException e){
                e.printStackTrace();
                }
            }
                
        }

        public int getGamesPlayed(){
            return this.gamesPlayed;
        }

        public int getPlayerWins(){
            return this.playerWins;
        }

        public int getCompWins(){
            return this.compWins;
        }

        public int getAvgDraws(){
            return this.avgDraws;
        }

        public int getLongestRound(){
            return this.longestRound;
        }

        public static String calculateOverallWinner(int[] gameResults){
            int winner = 0;
            String winnerResult = "";
            for(int i = 0; i <4; i++){
                if (gameResults[i] > winner){
                    winner = i;
                }
            }

            if (winner == 0){
                winnerResult = "Human";
            }
            if (winner == 1){
                winnerResult = "AI player One";
            }
            if (winner == 2){
                winnerResult = "AI player Two";
            }
            if (winner == 3){
                winnerResult = "AI player Three";
            }
            if (winner == 4){
                winnerResult = "AI player Four";
            }
            return winnerResult;
        }
    }