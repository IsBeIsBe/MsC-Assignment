package commandline;

import java.util.Scanner;
import java.util.Stack;

public class HumanPlayer extends Player{
	/* 
	 * Class for the Human Player, of which one will exist per game
	 */
	
	private Stack<Card> playerDeck = new Stack<>();
	
	
	

	public HumanPlayer(String playerName) {
		super(playerName);
		// TODO Auto-generated constructor stub
	}




	@Override
	protected int selectAttribute(Card testCard) {
		
		/*
		 * Displays the topmost card and asks the user to input an attribute
		 * between 1 and 5. Returns selected attribute.
		 */
		
		System.out.println("Please select an attribute from the Card\n");
		System.out.println(testCard);		
		while(true) {
			try {
				int selection = askUserForInputSelection();
				selection --;
				return selection;
				} catch (WrongInputException e) {
					System.out.println("User must enter a number between 1 and 5!");
				}
			}
		
	
	}
	
	public static int askUserForInputSelection() throws WrongInputException{
		Scanner scanner = new Scanner(System.in);
		String inputString = scanner.next();
		int input = 0;
		try {
			input = Integer.parseInt(inputString);
		} catch(NumberFormatException e) {
			throw new WrongInputException();
		}
		if (input > 0 && input < 6) {
			return input;
		} else {
			throw new WrongInputException();
		}
		
		
	}
}