package commandline;

import java.sql.*;

/**
 * The DatabaseInteraction class handles submitting and recalling information from the Database. It is sent the relevant data 
 * from the Game class in the first instance, and stores the relevant information as private variables in the latter.  
 */
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
    static final String DB_URL = "jdbc:postgresql://yacata.dcs.gla.ac.uk:5432/m_18_2208247s";
    //tried updating with username in URL as indicated in lecture slides...didn't work


        public DatabaseInteraction(){

        }

        /**
         * Using the results of each game, a static method is used to update the database with the relevant data. 
         * It uses the individuals scores to determine the overall winner of the game. 
         *  
         * @param gameResults
         */
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
                    System.out.println("Connecting to database...");
                    connection = DriverManager.getConnection(DB_URL, USERNAME, PASSWORD);
                    System.out.println("Succesfully connected!");
                    } catch(SQLException e){
                    System.out.println("Connection Failed");
                    e.printStackTrace();
                    return;
                    }
                    
                    if (connection != null){
                    
                    try{
                    System.out.println("Inserting info into table...");
                    stmt = connection.createStatement();
                    //First query
                    String insert = "INSERT INTO game(humanRoundWins, aiOneWins, aiTwoWins, aiThreeWins, aiFourWins, numOfRounds, drawsNumber, overallGameWinner) VALUES(" + humanRoundWins + ","  +  aiOneWins+ "," + aiTwoWins+ "," + aiThreeWins+ "," + aiFourWins+ "," + numOfRounds+ "," + drawsNumber+ "," + overallGameWinner + ")";
                    stmt.executeUpdate(insert);
                    
                
                    
                    }catch (SQLException e){
                    e.printStackTrace();
                    }
                }


        }
                
        /**
         * The getGameStats method returns the required information outlined in the requirements. 
         * 
         * Number of games is calculated by returning a count of the tuples in the table. 
         * Number of AI wins is calculated by searching the 'overall winner column for entries not including 
         * the word 'human'. This number is subtracted from the number of games to find the number of human wins. 
         * The average number of draws and largest number of rounds are calculated using SQL queries. 
         */
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

                }catch (SQLException e ){
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

        /**
         * This method calculates the overall winner of the game through finding the highest individual round score. 
         * @param gameResults
         * @return winnerResult
         */
        public static String calculateOverallWinner(int[] gameResults){
            int winner = 0;
            String winnerResult = "";
            for(int i = 0; i <4; i++){
                if (gameResults[i] > winner){
                    winner = i;
                }
            }

            if (winner == 0){
                winnerResult = "'Human'";
            }
            if (winner == 1){
                winnerResult = "'AI player One'";
            }
            if (winner == 2){
                winnerResult = "'AI player Two'";
            }
            if (winner == 3){
                winnerResult = "'AI player Three'";
            }
            if (winner == 4){
                winnerResult = "'AI player Four'";
            }
            return winnerResult;
        }
    }