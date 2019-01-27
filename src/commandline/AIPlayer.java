package commandline;

import java.util.Stack;

/**
 * AI Player class, of which there will be up to four per game
 */
public class AIPlayer extends Player {

	/**
	 * This method selects highest attribute of the player's card to play for the round, using a simple loop. 
	 */
	protected int selectAttribute(Card currentCard) {
		
		System.out.println(playerName + " is choosing which card to play\r\n");
		int largest = 0;
		int largestPosition = 0;
		for (int i = 0; i < 5; i++) {
			if (currentCard.attributes[i] > largest) {
				largest = currentCard.attributes[i];
				largestPosition = i;
			}
			
		}
		return largestPosition;
				

		
	}

	public AIPlayer(String playerName) {
		super(playerName);
		// TODO Auto-generated constructor stub
	}	

	
	
}