package commandline;

import java.util.ArrayList;
import java.util.Stack;

public abstract class Player {
	
	private int score = 0; // tracks the score
	private ArrayList<Card> hand = new ArrayList<>(); // current hand
	private Stack<Card> playerDeck = new Stack<>();

	public int selectAttributes() {
		/* selects which category to play next, from 1-5
		 * 
		 */	
		int attribute = 0;
		return attribute;
	}
	
	public void pushToDeck(Card card) {
		
		playerDeck.push(card);	
		
	}
	
	public void displayPlayerHand() {
		
		System.out.println("Cards in hand: " + playerDeck.size());
		System.out.println("Printing player hand...");
		System.out.println(playerDeck);		
	}
}
