public void printGameStatistics(){
    DatabaseInteraction db = new DatabaseInteraction();

    db.getGameStats(); 

    System.out.println(
    "Game Statistics: /n
    Number of Games: " + db.getGamesPlayed() + "/n
    Number of Human Wins: " + db.getPlayerWins() + "/n
    Number of AI Wins: " + db.getCompWins() + "/n
    Average Number of Draws: " + db.getAvgDraws() + "/n
    Longest Game: " + db.getLongestRound());
}


