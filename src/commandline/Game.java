package commandline;

import java.util.ArrayList;
import java.util.Collections;

public class Game {

	int rounds;
	boolean aiWon;
	ArrayList<Card> deck = new ArrayList<>();
	ArrayList<Player> players = new ArrayList<>();
	
	public Game(ArrayList<Card> inputDeck) {
		this.deck = inputDeck;
	}

	public void displayDeck() {
		
		System.out.println(deck);
	}

	public ArrayList<Player> createPlayers(int numberOfAIPlayers) {

		players.add(new HumanPlayer());

		for (int i = 0; i < numberOfAIPlayers; i++) {

			players.add(new AIPlayer());
		}
		return players;
	}

	public void shuffleDeck() {

		Collections.shuffle(deck);
	}

	public void dealCards() {

//		createPlayers(3);

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

	public void printPlayerCards() {

		for (int i = 0; i < players.size(); i++) {

			players.get(i).displayPlayerHand();
		}
	}
	
	public void playGame() {
		/*Start of the game
		 * 
		 */
		this.players = createPlayers(4);
		System.out.println("Printing deck as read...");
		displayDeck();
		System.out.println("Shuffling cards...");
		shuffleDeck();
		System.out.println("Printing shuffled deck...");
		displayDeck();
		dealCards();
		System.out.println("Dealing cards...");
		printPlayerCards();
		
		/*
		 * Round should start here?
		 */
		
		// Testing human player
		Card testCard = players.get(0).popACard();
		players.get(0).selectAttribute(testCard);
//		//Testing AI player
//		Player selectorTest = players.get(1);
//		System.out.println("Test");
//		Card testCard = selectorTest.popACard();
//		System.out.println(testCard);
//		selectorTest.selectAttribute(testCard);
////		selectorTest.displayPlayerHand();
			
			
		
	}
	public void printGameStatistics() {
	  DatabaseInteraction db = new DatabaseInteraction();

	    db.getGameStats(); 

	    System.out.println("Game Statistics: /n" +
	    "Number of Games: " + db.getGamesPlayed() + "/n" +
	    "Number of Human Wins: " + db.getPlayerWins() + "/n" +
	    "Number of AI Wins: " + db.getCompWins() + "/n" +
	    "Average Number of Draws: " + db.getAvgDraws() + "/n" +
	    "Longest Game: " + db.getLongestRound());
	}
}
