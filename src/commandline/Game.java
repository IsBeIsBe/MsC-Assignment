package commandline;

import java.util.ArrayList;

public class Game {
	/*
	 * Class that handles the game. This will keep track of how many
	 * rounds there has been, the winners of the rounds, the deck,
	 * the stack of cards and the stack of communal cards
	 */
	
	ArrayList<Player> players = new ArrayList<>();
	int rounds;
	boolean aiWon;
	//Card[] deck = new Card[40];
	ArrayList<Card> deck = new ArrayList<>();
	
	public void createDeck(Card c) {
		deck.add(c);
	}
	
	public void displayDeck() {
		System.out.println(deck);
	}
	
	public void shuffleDeck(Card[] unshuffled) {
		/*
		 * A deck is passed in which will then be shuffled
		 */
	}
	
	public void dealCards() {
		/*
		 * This will deal the cards amongst the players
		 */
	}

	
}
