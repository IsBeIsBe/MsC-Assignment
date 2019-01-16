package commandline;

import java.util.ArrayList;

public abstract class Player {
	/* abstract class for the players, which will be used to create
	 * the subclasses HumanPlayer and AIPlayer
	 */
	
	int score; // tracks the score
	ArrayList<Card> hand = new ArrayList<>(); // current hand
	
	public int selectAttrinbutes(){
		/* selects which category to play next, from 1-5
		 * 
		 */
		
		int attribute = 0;
		return attribute;
	}
	
}
