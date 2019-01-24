package commandline;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

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
		
		//Selecting first player by randomised method and popping their card
		int selector = firstPlayer();
		Card firstCard = players.get(selector).popACard();
		System.out.println("The player at position " + selector + " is choosing which card to play");
		
		int comparator = players.get(selector).selectAttribute(firstCard);
		
		System.out.println("player: " + players.get(selector) + " has chosen the value at position: " + 
		comparator + " from " + firstCard);
		
		ArrayList<Card> commonPile = deck;
//		commonPile.ensureCapacity(5);

		commonPile.add(selector, firstCard);

		
		for (int i = 0; i < 5; i++) {
			if (i == selector) {
				i++;
			} else {
				commonPile.add(i, players.get(i).popACard());
			}
		}
		
		
		
		//Winner equals card at position 'winner' in the commonPile
		int winner = selector;
		for (int i = 0; i < 5; i++) {
			if (commonPile.get(i).attributes[comparator] > commonPile.get(selector).attributes[comparator]) {
				winner = i;
			}
		}
		Player winnerPlayer = players.get(winner);
		
		System.out.println("The player at position " + winner + " has won with his card: "
				+ commonPile.get(winner));
		System.out.println("The end");
		/*
		 * Round should start here?
		 */
		


//		Player selectorTest = players.get(1);
//		System.out.println("Test");
//		Card testCard = selectorTest.popACard();
//		System.out.println(testCard);
//		selectorTest.selectAttribute(testCard);

			
			
		
	}
	
	public int firstPlayer() {
		Random rand = new Random();
		int playerID = rand.nextInt(4);
		return playerID;		
		
	}

}
