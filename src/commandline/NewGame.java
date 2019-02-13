package commandline;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

public class NewGame {

	// TestLogWriter thisLogWriter = new TestLogWriter(null);
	boolean test = false;
	String log = "\n----------WELCOME TO LOG MODE----------\n";

	int rounds;
	boolean aiWon;
	int selector;
	boolean draw;
	int draws;
	boolean winner;
	int roundWinner;
	Card choosingCard;
	int chosenAttribute;
	String winnerMessage;
	public String finalScores;

	ArrayList<Card> deck = new ArrayList<>();
	ArrayList<Player> players = new ArrayList<>();
	ArrayList<Card> commonPile = new ArrayList<>();
	Card[] cardsInPlay = new Card[5];
	int[] gameStats = new int[7];
	//boolean hasSomeoneWon = false;
	int drawCounter;
	String[] attributeNames;
	int numberOfAIPlayers = 4;
	
	
	public NewGame(ArrayList<Card> inputDeck, String[] names, boolean logMode) {
		this.deck = inputDeck;
		this.attributeNames = names;
		this.test = logMode;
	}
	
	/**
	 * Trying to break this into something modular the API can speak to - each method has a return value necessary for the
	 * online mode to transport something via JSON
	 */
	public void playGame() {

		allocateCards();
		loggingCardAllocation();
		selector = whoPlaysFirst();
		rounds = 0;
		while (!winner) {
			rounds++;
			if (test) {

				log += "\n----------START OF ROUND " + rounds + "----------\n";
			}
			
			System.out.println("\n----------START OF ROUND " + rounds + "----------\n");
		
			
			chosenAttribute = players.get(selector).selectAttribute();
			collectCardsInPlay();
			System.out.println(logCardsInPlay());
			roundWinner = checkWhoWins();
			draw = checkForDraws();
			if (draw){
				allCardsToCommonPile();
			} else if (!draw) {
				allCardsToWinner();
				selector = roundWinner;
				
			}
			System.out.println(loggingCardAllocation());
			winner = checkForOutRightWinner();
			checkAllPlayersForCards();
		
		}
		
		endGameMethod();
		
	}
	
	
	/**
	 * The only void method so far, I think this will have to be incorporated into another 'startAGame' method for the API
	 * to work as intended
	 */
	public void allocateCards() {
		createPlayers(numberOfAIPlayers);
		if (test) {
			log += "\n----------DECK AS READ----------\n" + deck + "\n-----------END OF DECK AS READ----------";
		}
		shuffleDeck();
		dealCards();
		
		System.out.println(loggingCardAllocation());
		
		if (test && rounds == 1) {
			
			
			
			log += "\n----------CARD ALLOCATION----------" + loggingCardAllocation() + "----------END OF CARD ALLOCATION----------\n";
		}
		
	}
	
	public ArrayList<Player> createPlayers(int numberOfAIPlayers) {

		players.add(new HumanPlayer("Human Player"));

		for (int i = 0; i < numberOfAIPlayers; i++) {
			String nameTemp = "AI Player " + (i + 1);
			players.add(new AIPlayer(nameTemp));
		}
		return players;
	}
	
	public void shuffleDeck() {

		Collections.shuffle(deck);
		
		// logging shuffled deck
		
		if (test) {
			log += "\n----------SHUFFLING DECK----------\n" + deck + "\n-----------END OF SHUFFLING DECK----------";
		}
	}
	
	/**
	 * This method deals the cards in the original 'deck' list evenly, accommodating
	 * for a number of players that does not divide the number of Cards evenly.
	 */
	public void dealCards() {

		int i = 0;
		int j = 0;
		int k = (deck.size() / players.size() - 1);

		for (i = 0; i < deck.size(); i++) {

			players.get(j).pushToDeck(deck.get(i));

			if (i == k) {
				j++;
			}
			if (i == 1 + (k * 2)) {
				j++;
			}
			if (i == 2 + (k * 3)) {
				j++;
			}
			if (i == 3 + (k * 4)) {
				j++;
			}
		}
	}
	
	/**
	 * This method randomly generates an int between 0 and 4 to determine the first
	 * player. In the event of the user being able to select how many players they
	 * would like to face, this code will need to be adjusted.
	 * 
	 * @return the int associated with the first player.
	 */
	public int whoPlaysFirst() {
		Random rand = new Random();
		int playerID = rand.nextInt(4);
		return playerID;

	}
	
	/**
	 * This method collects the cards in play into an array (cardsInPlay) in order to make choosing a winner and testing
	 * for draws easier. It also seemed like a good place print out what the selector had chosen to play, though this should 
	 * probably be moved somewhere easier to identify.
	 */
	public void collectCardsInPlay() {
		
		String attributeSelection =	players.get(selector).getPlayerName() + " has chosen " + attributeNames[chosenAttribute] 
				+ " from " + players.get(selector).peekACard().getName() + " with a value of " + 
				players.get(selector).peekACard().attributes[chosenAttribute] + "\r\n";
		
		
		for (int i = 0; i < players.size(); i++) {
			if (!players.get(i).checkCards()) {
				System.out.println(players.get(i).getPlayerName() + " has no cards!");
			} else {
				cardsInPlay[i] = players.get(i).popACard();
			}
		}
		


		
		System.out.println(attributeSelection);
		if (test) {
			
				log += "\n-----------CARDS IN PLAY-----\n" + logCardsInPlay() + "\n-----------CARDS IN PLAY END----------\n";
				log += "\n-----------ATTRIBUTE SELECTION-----\n" + attributeSelection + "\n-----------ATTRIBUTE SELECTION END----------\n";
			}
		
	}
	
	/**
	 * This method takes the array of cards in play and sees which is the largest int based on the 'chosen attribute'.
	 * 
	 * It returns the int value of the position of the winning card in the array, which should be the same as the int 
	 * value of the position of the player who it belongs to in the players array list. 
	 * 
	 * @param thisCommonPile
	 * @param chosenAttribute
	 * @return
	 */
	public int checkWhoWins() {
		
		int comparator = cardsInPlay[selector].getAttributes()[chosenAttribute];
		roundWinner = selector;
		for (int i = 0; i <cardsInPlay.length; i++) {
			if (!players.get(i).checkCards() ) {
				
			} else if (cardsInPlay[i].getAttributes()[chosenAttribute] > comparator) {
			
				comparator = cardsInPlay[i].getAttributes()[chosenAttribute];
				
				roundWinner = i;
				}
			}
		
		System.out.println("The winning number was:" + comparator + " and it belongs to " + players.get(roundWinner).getPlayerName());
		return roundWinner;
	}
	
	
	/**
	 * This method extracts the value of the winning attribute based on the winner decided in 'checkWhoWins' and uses 
	 * that to cycle through the cardsInPlay array. If any are found to be of the same value, it declares a draw by 
	 * returning a 'true' value. Otherwise, it returns false. 
	 * 
	 * @param thisCommonPile
	 * @param roundWinner
	 * @param attribute
	 * @return
	 */
	public boolean checkForDraws() {
		boolean checkForDraws = false;
		int comparator = cardsInPlay[roundWinner].getAttributes()[chosenAttribute];
		for (int i = 0; i < cardsInPlay.length; i++) {
			if (i == roundWinner || !players.get(i).checkCards()) {
				
			} else if (cardsInPlay[i].getAttributes()[chosenAttribute] == comparator) {
			
				checkForDraws = true;
				System.out.println("There was a draw! \n");
				draws++;
				}
			}
		
		
		return checkForDraws;
	}
	
	
	/**
	 * This method collects the cards from the cardsInPlay array and adds them to the commonPile, resetting the cardsInPlay
	 * values to null for the next round in the event of players being removed from the game. 
	 * 
	 * @param thisCommonPile
	 */
	public void allCardsToCommonPile() {
		
		for (int i = 0; i < cardsInPlay.length; i++) {
			if (cardsInPlay[i] == null) {
			
			} else {
				commonPile.add(cardsInPlay[i]);
				cardsInPlay[i] = null;
			}
		}
		System.out.println("The common pile now has " + commonPile.size() + " cards:\r\n");
		System.out.println(commonPile);
		
		
		if (test) {
			log += "\n----------COMMON PILE----------\n" + commonPile + "\n----------COMMON PILE END----------\n";
		}
	}
	
	/**
	 * This method adds the cards from the cardsInPlay array to the winning player's hand.
	 * 
	 * @param thisCommonPile
	 * @param roundWinner
	 */
	public void allCardsToWinner() {
		
		String winnerDeclaration = "Player " + players.get(roundWinner).getPlayerName() + " has won with their card "
				+ cardsInPlay[roundWinner].getName() + " which has a " + attributeNames[chosenAttribute] 
						+ " of " + cardsInPlay[roundWinner].attributes[chosenAttribute];
		
		System.out.println(winnerDeclaration);
		
		if (test) {
			log += "\n---------- WINNER ----------\n" + winnerDeclaration + "\n---------- WINNER ----------\n";

		}
		
		players.get(roundWinner).winsRound();
		
		for (int i = 0; i < cardsInPlay.length; i++) {
			if (cardsInPlay[i] == null) {
				
			} else {
				players.get(roundWinner).pushToDeck(cardsInPlay[i]);
				cardsInPlay[i] = null;
			}
		}
		
		if (!commonPile.isEmpty()) {
			int commonSize = commonPile.size();
			for (int i = 0; i < commonSize; i++) { // pushes all of the common pile cards to the winners hand
				players.get(roundWinner).pushToDeck(commonPile.remove(0));
			}
		}
		
	}
	
	
	/**
	 * This method cycles through the arrayList of players and confirms whether they still have cards to play, incrementing
	 * a counter if they do. If the value of the counter is found to be 1, it returns a 'true', indicating an outright winner
	 * has been found in order to break from the 'while' loop. 
	 * @return
	 */
	public boolean checkForOutRightWinner() {
		boolean outrightWinner = false;
		int numOfPlayers = 5;
		for (int i = 0; i < players.size(); i++) {
			if (!players.get(i).checkCards()) {
				numOfPlayers--;
			}
		}
		if (numOfPlayers == 1) {
			outrightWinner = true;
		}
		return outrightWinner;
	}
	
	/**
	 * This method keeps track of what cards each player is holding by reading through their deck. While conceived for the log 
	 * file, it's been useful to keep track of everyone's status in between rounds to ensure the logic is functioning properly. 
	 * @return
	 */
	public String loggingCardAllocation() {

		String loggingCardAllocation = "";

		for (int i = 0; i < players.size(); i++) {

			loggingCardAllocation += "\n" + players.get(i).getPlayerName() + ": \n" + players.get(i).getHand() 
					+ "\n" + "Total Cards: " + players.get(i).playerDeck.size();

		}
		
		if (test) {
			log += loggingCardAllocation;
		}

		return loggingCardAllocation;

	}
	
	/**
	 * Again, this method was originally made to the specification of the logger, but keeping tack of which cards each player is 
	 * playing has also been useful for debugging. 
	 * @return
	 */
	public String logCardsInPlay() {
		String logInfo = "";
		for (int i = 0; i < cardsInPlay.length; i++) {
			if (cardsInPlay[i] == null) {
				logInfo += players.get(i).getPlayerName() + " is no longer in play!\n";
			}
			else {
				logInfo += players.get(i).getPlayerName() + ": " + cardsInPlay[i] + "\n";
			}
		}
		return logInfo;
	}
	
	/**
	 * This is information the user might find useful at the start of each round. It might be better refactored to return a 
	 * string for the API to use, but we can deal with that later. 
	 */
	public void displayRoundStartInfo() {
		
		System.out.println("Round " + rounds + "\r\n");
		
		if (!players.get(0).checkCards()) {
			
			String roundStartInfo = "You drew " + players.get(0).peekACard().getName() + ":\r\n" + "> " + attributeNames[1]
					+ " " + players.get(0).peekACard().getAttributes()[0] + "\r\n" + "> " + attributeNames[2] + " "
					+ players.get(0).peekACard().getAttributes()[1] + "\r\n" + "> " + attributeNames[3] + " "
					+ players.get(0).peekACard().getAttributes()[2] + "\r\n" + "> " + attributeNames[4] + " "
					+ players.get(0).peekACard().getAttributes()[3] + "\r\n" + "> " + attributeNames[5] + " "
					+ players.get(0).peekACard().getAttributes()[4];
			
			System.out.println(roundStartInfo);
			
/*			if (test && rounds == 1) {
				
				log += "\n----------CARD ALLOCATION----------" + roundStartInfo + "----------END OF CARD ALLOCATION----------\n";
			}*/
			
			System.out.println("\r\nThere are " + players.get(0).getHand().size() + " cards in your hand\r\n");
		}
	}
	
	public void checkAllPlayersForCards(){
		for (int i = 0; i < players.size(); i++) {
			players.get(i).deckEmptyCheck();
			if (players.get(i).checkCards()) {
				System.out.println(players.get(i).getPlayerName() + " still has cards!");
			} else {
				System.out.println(players.get(i).getPlayerName()+ " is no longer in play!");
			}
		}
	}

	
	/**
	 * This game collects all the loose aspects of the system associated with ending the game: declaring the winner, logging the 
	 * final scores, creating the gameStats array for the database, sending that array to the database, and sending the completed
	 * log file to the filewriter if the game is in log mode. 
	 */
	public void endGameMethod() {
		int winner = 7;
		for (int i = 0; i < players.size(); i++) {
			if (players.get(i).checkCards()) {
				winner = i;
			}
		}
		winnerMessage = players.get(winner).getPlayerName() + " has won after " + rounds + " rounds\r\n";
		
		if (test) {
			log += "\n----------WINNER DECLARED----------\n" + players.get(winner).getPlayerName() + " has won after " + rounds
					+ " rounds\r\n" + "\n-----------WINNER DECLARED END----------\n";
		}
		// The final scores of the game are printed for the user to examine.
		finalScores += "Final scores: \n";
		for (int i = 0; i < players.size(); i++) {
			finalScores += players.get(i).getPlayerName() + ": " + players.get(i).getScore() + "\n";
		}
		System.out.println(finalScores);
		if (draws > 0) {
			System.out.println("There were " + draws + " draws.");
		}

		// The next lines collect the data required for the database.
		int scoreOne = players.get(0).getScore();
		int scoreTwo = players.get(1).getScore();
		int scoreThree = players.get(2).getScore();
		int scoreFour = players.get(3).getScore();
		int scoreFive = players.get(4).getScore();

		gameStats[0] = scoreOne;
		gameStats[1] = scoreTwo;
		gameStats[2] = scoreThree;
		gameStats[3] = scoreFour;
		gameStats[4] = scoreFive;
		gameStats[5] = rounds;
		gameStats[6] = drawCounter;

		// endOfGame(gameStats);

		System.out.println("GAME OVER");
		if (test) {
			TestLogWriter thisLogWriter = new TestLogWriter(log);
			thisLogWriter.WriteLogFile();
		}
		
	}




/*
 * Below this point are methods specific to the API - grouping together select methods to return values more suited 
 * to the onine version and its requirements. It may be the case that these are refactored into the command line 
 * version later.
 * 
 * Most of the methods above should be re-usable for the API as well, especially things like displayRoundStartInfo and
 * endGameMethod, without having to alter them. 
 */
	
	/**
	 * This method initialises the game, shuffles the deck and selects who plays first. 
	 * 
	 * It links to the startAndSelectFirstPlayer() method in the API
	 * @return
	 */
	public int startAndSelectFirstPlayer() {
		allocateCards();
		selector = whoPlaysFirst();
		rounds = 1;
		
		return selector;
	}

	/**
	 * This calls the select attribute method from the player. While it should work for the AI player, the method may 
	 * need to be re-thought for collecting input from the human player. 
	 * 
	 * It links to the getChosenAttribute method in the API
	 * @return
	 */
	public int returnChosenAttribute(int selector) {
		setChosenAttribute(players.get(selector).selectAttribute());
		
		return chosenAttribute;
	}

	
	/**
	 * This method finds the integer value of the winner of each round (relative to their index in the players array list).
	 * It also collects the active cards in the cardsInPlay array (we'll have to check that is accessible,it should be if it's 
	 * global scope?)
	 * It also relies upon the chosenAttribute value, hence the setChosenAttribute method below, to ensure this is the same 
	 * as the one foud in getChosenAttribute.
	 * @return
	 */
	public int findWinnerOfRound() {
		collectCardsInPlay();
		roundWinner = checkWhoWins();
		return roundWinner;
	}
	/**
	 * This method sets the value of the chosenAttribute value. It will be called from the getChosenAttribute but could also be 
	 * called from the API if needs be. 
	 * @param value
	 */
	public void setChosenAttribute(int value) {
		this.chosenAttribute = value;
	}
	
	/**
	 * This method condenses the aspect of the system checking for draws. It returns whether there has been a draw, but also 
	 * hopefully has updated the variables accordingly already, so the return value should just be used to declare in the 
	 * webpage if there was a draw or not. 
	 */
	public boolean drawDecisions() {
		draw = checkForDraws();
		if (draw){
			allCardsToCommonPile();
		} else {
			allCardsToWinner();
			selector = roundWinner;
			
		}
		
		return draw;
	}
	
	/**
	 * This method is maybe a bit pointless but just condenses the check for a winner with incrementing the rounds. 
	 * The value it returns can be used to break the game loop, same as above in the playGame() method. 
	 * @return
	 */
	public boolean isThereAWinner() {
		winner = checkForOutRightWinner();
		rounds++;
		return winner;
	}

	public String getPlayerCardForAPI(int player) {
		
		String card = players.get(player).peekACard().toStringAPI();
		return card;
		
	}
	
	
	public String getSelector() {
		
		String selector = String.valueOf(this.selector);
		
		return selector;
	}
	
	public void setSelector(int selectorValue) {
		this.selector = selectorValue;
	}
}
