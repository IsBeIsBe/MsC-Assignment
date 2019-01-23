package commandline;

import java.util.Scanner;
import java.util.Stack;

public class HumanPlayer extends Player{
	/* 
	 * Class for the Human Player, of which one will exist per game
	 */
	
	private Stack<Card> playerDeck = new Stack<>();

	@Override
	protected int selectAttribute(Card testCard) {
		
		/*
		 * Displays the topmost card and asks the user to input an attribute
		 * between 1 and 5. Returns selected attribute.
		 */
		
		System.out.println("Please select an attribute\n");
		testCard.toString(); // Displays topmost card
		Scanner s = new Scanner(System.in);
		int selectedAttribute = s.nextInt();
		return selectedAttribute;
	}
}