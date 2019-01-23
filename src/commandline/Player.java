package commandline;

import java.util.ArrayList;
import java.util.Stack;

public abstract class Player {
	
	private int score = 0; // tracks the score
//	private ArrayList<Card> hand = new ArrayList<>(); // current hand
	private Stack<Card> playerDeck = new Stack<>();

//	abstract int selectAttribute();
	
	public void pushToDeck(Card card) {
		
		playerDeck.push(card);	
		
	}
	
	public Card popACard() {
		return playerDeck.pop();
	}
	public void displayPlayerHand() {
		
		System.out.println("Cards in hand: " + playerDeck.size());
		System.out.println("Printing player hand...");
		System.out.println(playerDeck);		
	}

	protected abstract int selectAttribute(Card testCard);
}

