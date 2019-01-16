package commandline;

public abstract class Player {
	/* abstract class for the players, which will be used to create
	 * the subclasses HumanPlayer and AIPlayer
	 */
	
	int score; // tracks the score
	ArrayList<card> hand = new ArrayList<card>(); // current hand
	
	public Int selectCategory(){
		/* selects which category to play next, from 1-5
		 * 
		 */
	}
	
}
