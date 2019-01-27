package commandline;

import java.util.ArrayList;
import java.util.Stack;

public abstract class Player {
	
	private int score = 0; // tracks the score
//	private ArrayList<Card> hand = new ArrayList<>(); // current hand
	private Stack<Card> playerDeck = new Stack<>();
//	abstract int selectAttribute();
	private String playerName;
	private boolean hasCards = true;
	
	
	
	
	public int getScore() {
		return score;
	}

	public void setScore(int score) {
		this.score = score;
	}
	
	public void winsRound() {
		this.score ++;
	}

	public String getPlayerName() {
		return playerName;
	}

	public void setPlayerName(String playerName) {
		this.playerName = playerName;
	}

	public Player(String playerName) {
	this.playerName = playerName;
}

	public void pushToDeck(Card card) {
		
		playerDeck.push(card);	
		
	}
	
	public Card popACard() {
		return playerDeck.pop();
	}
	
	public boolean deckEmptyCheck() {
		
		boolean deckIsEmpty = false;
		
		if (playerDeck.isEmpty()) {
			
			deckIsEmpty = true;
			
		}
		
		return deckIsEmpty;
		
	}
	public boolean checkCards() {
		return hasCards;
	}
	
	public void hasNoCards() {
		this.hasCards = false;
	}
	
	public Card peekACard() {
		return playerDeck.peek();
	}
	public void displayPlayerHand() {
		
		System.out.println("Cards in hand: " + playerDeck.size());
		System.out.println("Printing player hand...");
		System.out.println(playerDeck);		
	}

	protected abstract int selectAttribute(Card testCard);
}

