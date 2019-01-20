package commandline;

import java.util.Stack;

public class AIPlayer extends Player {
	/* 
	 * AI Player class, of which there will be up to four per game
	 */
	private int score;
	private Stack<Card> playerDeck = new Stack<>();
	
/*	public AIPlayer(int score, Stack<Card> playerDeck) {
		super();
		this.score = score;
		this.playerDeck = playerDeck;
	}*/

	public void selectAttribute() {
	/* 
	 * selects highest attribute of card in play
	 */
	
	// peek at top card in stack. Pick highest value from int array 
	// that belongs to card. Picks from card position in int array
	//	that refers to attribute name
		
		int[] currentCard = playerDeck.peek().getAttributes();
		int largest = 0;
		int largestPosition = 0;
		for (int i = 0; i <currentCard.length; i++) {
			if (currentCard[i] >= largest) {
				largest = currentCard[i];
				largestPosition = i;
			}
			
		}
		System.out.println(largest + " " + largestPosition);
				
	int[] cardPlayed = new int[2]; // first int: attribute number, second int: value
	cardPlayed[0] = largest;
	cardPlayed[1] = largestPosition;
	System.out.println(cardPlayed[0] + ", " + cardPlayed[1]);
		
	}

	
	
}
