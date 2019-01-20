package commandline;

import java.util.ArrayList;
import java.util.Random;

public class Round {
	/*
	 * Round class which holds the methods controlling individual
	 * rounds. This will also pass variables to the database
	 */
	
	ArrayList<Player> currentPlayers = new ArrayList<>();
	
	
	public void firstPlayer() {
		Random rand = new Random();
		int playerID = rand.nextInt(4);
		
	}
	
	public void compareCards() {
		
	}
	
	public void decideWinner() {
		/*
		 * method to decide who the winner of a round is
		 */
	}
}
