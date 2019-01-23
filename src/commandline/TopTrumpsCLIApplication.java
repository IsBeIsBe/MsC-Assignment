package commandline;

import java.util.InputMismatchException;
import java.util.Scanner;

/**
 * Top Trumps command line application
 */
public class TopTrumpsCLIApplication {

	/**
	 * This main method is called by TopTrumps.java when the user specifies that they want to run in
	 * command line mode. The contents of args[0] is whether we should write game logs to a file.
 	 * @param args
	 */
	public static void main(String[] args) {

		boolean writeGameLogsToFile = false; // Should we write game logs to file?
//		if (args[0].equalsIgnoreCase("true")) writeGameLogsToFile=true; // Command line selection
		
		// State
		boolean userWantsToQuit = false; // flag to check whether the user wants to quit the application
		
		// Loop until the user wants to exit the game
		while (!userWantsToQuit) {

			// ----------------------------------------------------
			// Add your game logic here based on the requirements
			// ----------------------------------------------------
			
			
			System.out.println("--------------------\r\n" + 
					"--- Top Trumps   ---\r\n" + 
					"--------------------\r\n" + 
					"Do you want to see past results or play a game?\r\n" + 
					"   1: Print Game Statistics\r\n" + 
					"   2: Play game\r\n" + 
					"Enter the number for your selection: ");
			while (true) {
				try {
					
					int selection = askUserForInputSelection();
			
					if (selection == 1) {
//						printGameStatistics();
						System.out.print("Print Game Stats");
						break;
				
					} else if (selection == 2) {
					/*
					 * Code for starting a game goes here
					 */
					System.out.println("Play game");
					break;
				}
			} catch (WrongInputException e) {
				System.out.println("User must enter a 1 or a 2! Please try again: ");
			} 

			}
			userWantsToQuit=true; // use this when the user wants to exit the game
			
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
		if (input == 1 || input == 2) {
			return input;
		} else {
			throw new WrongInputException();
		}
		
		
	}
	
	public static void printGameStatistics() {
		  DatabaseInteraction db = new DatabaseInteraction();

		    db.getGameStats(); 

		    System.out.println("Game Statistics: /n" +
		    "Number of Games: " + db.getGamesPlayed() + "/n" +
		    "Number of Human Wins: " + db.getPlayerWins() + "/n" +
		    "Number of AI Wins: " + db.getCompWins() + "/n" +
		    "Average Number of Draws: " + db.getAvgDraws() + "/n" +
		    "Longest Game: " + db.getLongestRound());
		}

}
