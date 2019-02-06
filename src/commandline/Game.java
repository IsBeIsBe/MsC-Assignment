package commandline;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

/**
 * The Game class uses the Player and Card classes to play a complete game of
 * Top Trumps. The relevant data from each game is then sent to the database via
 * the static insertGameStats method from the DatabaseInteraction class.
 * 
 * ArrayLists were used form the list of players (to accommodate potentially
 * allowing the user to select how many opponents they would like to play
 * against) as well as the 'commonPile' - a deck used to collect each player's
 * cards at before redistributing them to the winner and a temporary hold for
 * played cards in the event of a draw.
 * 
 * The boolean 'hasSomeoneWon' is used to break the loop of game playing. Int
 * variables such as roundCounter and drawCOunter are used to gather the
 * relevant information for the database elements of the system.
 */
public class Game {

	// TestLogWriter thisLogWriter = new TestLogWriter(null);
	boolean test = true;
	String log = "\n----------WELCOME TO LOG MODE----------\n";

	int rounds;
	boolean aiWon;
	int selector;

	ArrayList<Card> deck = new ArrayList<>();
	ArrayList<Player> players = new ArrayList<>();
	ArrayList<Card> commonPile = new ArrayList<>();
	int[] gameStats = new int[7];
	boolean hasSomeoneWon = false;
	int drawCounter;
	String[] attributeNames;
	boolean logMode;

	/**
	 * The Game class takes the deck of cards being used as an argument in the
	 * constructor in order to allow for potentially selecting from a choice of
	 * available decks.
	 * 
	 * @param inputDeck
	 */
	public Game(ArrayList<Card> inputDeck, String[] names, boolean logMode) {
		this.deck = inputDeck;
		this.attributeNames = names;
		this.logMode = logMode;
	}

	/**
	 * This functionality is included in a method in order to make writing and
	 * reading the Test Log easier.
	 */
	public void displayDeck() {

		System.out.println(deck);
	}

	/**
	 * The createPlayers method establishes an ArrayList of players for the game.
	 * The human player controlled by the user is always the first element in the
	 * structure.
	 * 
	 * The number of players is currently hard-coded in during the 'playGame'
	 * method, with the intention that this might be changed to allow the user to
	 * change the number of players later in development.
	 * 
	 * @param numberOfAIPlayers
	 * @return an array list of the players of each game.
	 */
	public ArrayList<Player> createPlayers(int numberOfAIPlayers) {

		players.add(new HumanPlayer("Human Player"));

		for (int i = 0; i < numberOfAIPlayers; i++) {
			String nameTemp = "AI Player " + (i + 1);
			players.add(new AIPlayer(nameTemp));
		}
		return players;
	}

	/**
	 * Again, containing this functionality in a method helped keep the system
	 * design easier to follow.
	 */
	public void shuffleDeck() {

		Collections.shuffle(deck);
		
		// logging shuffled deck
		
		if (test) {
			log += "\n----------SHUFFLING DECK----------\n" + deck + "\n-----------END OF SHUFFLING DECK----------";
		}
	}

	/**
	 * This method deals the cards in the original 'deck' list evenly, accommodating
	 * for a number of players that does not divide the number of Cards evenly.
	 */
	public void dealCards() {

		int i = 0;
		int j = 0;
		int k = (deck.size() / players.size() - 1);

		for (i = 0; i < deck.size(); i++) {

			players.get(j).pushToDeck(deck.get(i));

			if (i == k) {
				j++;
			}
			if (i == 1 + (k * 2)) {
				j++;
			}
			if (i == 2 + (k * 3)) {
				j++;
			}
			if (i == 3 + (k * 4)) {
				j++;
			}
		}
	}

	/**
	 * This method utilises the 'displayPlayerHand' methods associated with each
	 * Player to print each deck.
	 */
	public String printPlayerCards() {

		String playerCardsLog = "";
		
		for (int i = 0; i < players.size(); i++) {

			playerCardsLog += players.get(i).displayPlayerHand();
		}
		return playerCardsLog;
	}

	/**
	 * This method starts the process of playing a game. It creates the selected
	 * number of players, retrieves the deck of Cards and sets the relevant
	 * 'counters' to zero. It then shuffles and distributes the cards amongst the
	 * players. The first player of the game is assigned randomly using the
	 * 'firstPlayer' method described below. The game then enters a while(true) loop
	 * to contain a repeating 'playARound' method - this is broken when
	 * 'hasSomeoneWon' is found to be true.
	 */
	public void playGame() {

		// logging deck as read
		
		if (test) {
			log += "\n----------DECK AS READ----------\n" + deck + "\n-----------END OF DECK AS READ----------";
		}
		
		this.players = createPlayers(4);
		drawCounter = 0;

		for (int i = 0; i < players.size(); i++) {
			System.out.println(players.get(i).getPlayerName());
			System.out.println();
		}

		/*
		 * The following section will be shuffling and dealing cards, with the future
		 * log functionality commented out
		 */

		// String deckContentsLog = deck.toString();
		// thisLogWriter.WriteLogFile("This is the pre-shuffle deck contents: " + "\n" +
		// deckContentsLog);
		shuffleDeck();

		// String postSuffleDeckContentsLog = deck.toString();
		// thisLogWriter.WriteLogFile("\n This is the shuffled deck contents: " + "\n" +
		// postSuffleDeckContentsLog);
		dealCards();

		// Selecting first player by a randomised method and popping their card
		selector = firstPlayer();

		rounds = 0;
		/*
		 * While loop for going through rounds until only one person has a stack left.
		 */

		System.out.println("Game start\r\n");
		while (everyoneHasAStack()) {
			rounds++;
			playARound(selector);
		}

		// This loop finds the winner --------------------------It might be already
		// written to 'winner' though?
		for (int i = 0; i < players.size(); i++) {
			if (!players.get(i).deckEmptyCheck()) {
				System.out.println(players.get(i).getPlayerName() + " has won after " + rounds + " rounds\r\n");
				
				if (test) {
					log += "\n----------WINNER DECLARED----------\n" + players.get(i).getPlayerName() + " has won after " + rounds
							+ " rounds\r\n" + "\n-----------WINNER DECLARED END----------\n";
				}
			}
			
		}

		// The final scores of the game are printed for the user to examine.
		System.out.println("Final scores: ");
		for (int i = 0; i < players.size(); i++) {
			System.out.println(players.get(i).getPlayerName() + ": " + players.get(i).getScore());
		}

		// The next lines collect the data required for the database.
		int scoreOne = players.get(0).getScore();
		int scoreTwo = players.get(1).getScore();
		int scoreThree = players.get(2).getScore();
		int scoreFour = players.get(3).getScore();
		int scoreFive = players.get(4).getScore();

		gameStats[0] = scoreOne;
		gameStats[1] = scoreTwo;
		gameStats[2] = scoreThree;
		gameStats[3] = scoreFour;
		gameStats[4] = scoreFive;
		gameStats[5] = rounds;
		gameStats[6] = drawCounter;

		// endOfGame(gameStats);

		System.out.println("GAME OVER");
		TestLogWriter thisLogWriter = new TestLogWriter(log);
		thisLogWriter.WriteLogFile();

	}

	public void playARound(int roundSelector) {
		/*
		 * This method handles playing each round of the game -- is takes the player who
		 * chooses the attributes for the round, known as 'selector'. This is assigned
		 * randomly at first, and then the position is taken by whoever wins the round.
		 * 
		 * The 'selector' is asked to choose an attribute from the first card in their
		 * stack (playerDeck). If an AI player is currently the 'selector', it simply
		 * chooses the highest value.
		 * 
		 * The selected value is then compared against the other top cards in each
		 * player's stack to find a winner. The cards are then collected together and
		 * gifted to the winner. In the event of a draw, the cards remain in the
		 * commonPile stack to be won in the next round - the previous winner remains
		 * the 'selector' for this next round.
		 * 
		 * The method checks how many cards each player has and breaks the loop
		 * 'playARound' is contained in when only one player has any cards remaining.
		 */
		
		if (test) {

			log += "\n----------START OF ROUND " + rounds + "----------\n";
		}
		
		if (test && rounds == 1) {
			
			
			
			log += "\n----------CARD ALLOCATION----------" + loggingCardAllocation() + "----------END OF CARD ALLOCATION----------\n";
		}
		
		displayRoundStartInfo();

		Card firstCard = players.get(roundSelector).peekACard(); // top card of hand

		int comparator = players.get(roundSelector).selectAttribute(firstCard); // position of highest attribute

		String attributeSelection = 
				players.get(roundSelector).getPlayerName() + " has chosen " + attributeNames[comparator + 1] + " from "
						+ firstCard.getName() + " with a value of " + firstCard.getAttributes()[comparator] + "\r\n";
		
		System.out.println(attributeSelection);
		// Card has been selected above

		if (test) {
			log += "\n-----------CARDS IN PLAY-----\n" + logCardsInPlay() + "\n-----------CARDS IN PLAY END----------\n";
			log += "\n-----------ATTRIBUTE SELECTION-----\n" + attributeSelection + "\n-----------ATTRIBUTE SELECTION END----------\n";
		}
		
		int winner = roundSelector;

		// These variables help determine if there has been a draw, and adjusts the flow
		// of the game accordingly.
		boolean ifTheresADraw = false;
		/*
		 * This for loop is where the cards are compared. Firstly, the system skips over
		 * the 'selector' player. It then skips any players in the players list who have
		 * no cards left. If a draw is found, the system first completes the loop to
		 * ensure no outright winner can be found, and only then changes ifTheresADraw
		 * to 'true' in order to change how the round plays out.
		 */
		
		//int drawingPlayer;
		int chosenAttribute = firstCard.attributes[comparator];
		
		for (int i = 0; i < players.size(); i++) {
			
			if (i == roundSelector || !players.get(i).checkCards()) {
				i++;
			} else {

				Card playersCard = players.get(i).peekACard(); // card of other player(s)
				
				if (playersCard.attributes[comparator] == chosenAttribute) {
					//drawingPlayer = i;
					for (int j = i + 1; j < players.size(); j++) {
						if (playersCard.attributes[comparator] > chosenAttribute) {
							winner = j;
							chosenAttribute = playersCard.attributes[comparator];
						} else {
							ifTheresADraw = true;
						}
					}
					
					System.out.print("This round was a draw.");
					drawCounter++;
					break;
					// Finally, if any outright winner is found, their position in the players list
					// is recorded.

				} else if (playersCard.attributes[comparator] > chosenAttribute) {
					winner = i;
					chosenAttribute = playersCard.attributes[comparator];
				} else {

					winner = roundSelector;
				}
			}
		}

		/*
		 * If there is no draw, the game continues as normal, declaring the winner,
		 * establishing them as the next 'selector', and awarding them the cards
		 * associated with the round.
		 */
		if (!ifTheresADraw) {

			Player winnerPlayer = players.get(winner);
			players.get(winner).winsRound();

			System.out.println(players.get(winner).getPlayerName() + " has won with his card: "
					+ winnerPlayer.peekACard().getName());

			for (int i = 0; i < players.size(); i++) { // adds the played cards of all the players to the common pile
				if (players.get(i).checkCards()) {
					commonPile.add(players.get(i).popACard());
				}
			}

			// System.out.print("Common Pile: " + commonPile);
			
			int commonSize = commonPile.size();
			for (int i = 0; i < commonSize; i++) { // pushes all of the common pile cards to the winners hand
				players.get(winner).pushToDeck(commonPile.remove(0));
			}

			/*
			 * If there IS a draw, the commonPile is given all player cards from the round.
			 * These are then carried over to be won in the next round.
			 */
		} else {
			for (int i = 0; i < players.size(); i++) { // adds the played cards of all the players to the common pile
				if (players.get(i).checkCards()) {
					commonPile.add(players.get(i).popACard());
				}
			}
			System.out.println(" common pile now has " + commonPile.size() + " cards.\r\n");
			
			if (test) {
				log += "\n----------COMMON PILE----------\n" + commonPile + "\n----------COMMON PILE END----------\n";
			}
			
		}

		selector = winner;
		
		if (test) {
			log += printPlayerCards();
		}

		// Next few lines are Test Log/troubleshooting related.
		System.out.println("\n----------END OF ROUND " + rounds + "----------\n");
		System.out.println("There have been " + drawCounter + " draws.");
		System.out.println("Scores after Round " + rounds + ": ");
		for (int i = 0; i < players.size(); i++) {
			System.out.println(players.get(i).getPlayerName() + ": " + players.get(i).getScore());
		}

		// This method checks which players are still in the game by checking their
		// stacks and changing their 'hasADeck' status.
		// If only one player is found to be left in the game, the while loop is exited.
		kickPlayerOut();

	}

	// private void compareCards(int roundSelector){

	// Card firstCard = players.get(roundSelector).peekACard(); // top card of hand
	// int comparator = players.get(roundSelector).selectAttribute(firstCard);

	// for (int i = 0; i < players.size(); i++) {
	// if (i == roundSelector) {
	// i++;
	// } else if (!players.get(i).checkCards()) {
	// i++;
	// } else {

	// Card playersCard = players.get(i).peekACard(); // card of other player(s)
	// if (playersCard.attributes[comparator] == firstCard.attributes[comparator]) {
	// drawingPlayer = i;
	// for (int j = i + 1; j < players.size(); j++) {
	// if (playersCard.attributes[comparator] > firstCard.attributes[comparator]) {
	// winner = j;
	// } else {
	// ifTheresADraw = true;
	// }
	// }
	// System.out.print("This round was a draw,");
	// drawCounter++;
	// break;
	// // Finally, if any outright winner is found, their position in the players
	// list
	// // is recorded.

	// } else if (playersCard.attributes[comparator] >
	// firstCard.attributes[comparator]) {
	// winner = i;
	// }
	// }
	// }
	// }

	
	public String loggingCardAllocation() {

		String loggingCardAllocation = "";

		for (int i = 0; i < players.size(); i++) {

			loggingCardAllocation += "\n" + players.get(i).getPlayerName() + ": \n" + players.get(i).getHand();

		}

		return loggingCardAllocation;

	}
	
	public String logCardsInPlay() {
		String logInfo = "";
		for (int i = 0; i < players.size(); i++) {
			if (!players.get(i).deckEmptyCheck()) {
				 logInfo += "\nCard in play for " + players.get(i).getPlayerName() + ": " + players.get(i).peekACard() + "\n";
			}
		}
		return logInfo;
	}
	
	public void displayRoundStartInfo() {
		
		System.out.println("Round " + rounds + "\r\n");
		
		if (!players.get(0).deckEmptyCheck()) {
			
			String roundStartInfo = "You drew " + players.get(0).peekACard().getName() + ":\r\n" + "> " + attributeNames[1]
					+ " " + players.get(0).peekACard().getAttributes()[0] + "\r\n" + "> " + attributeNames[2] + " "
					+ players.get(0).peekACard().getAttributes()[1] + "\r\n" + "> " + attributeNames[3] + " "
					+ players.get(0).peekACard().getAttributes()[2] + "\r\n" + "> " + attributeNames[4] + " "
					+ players.get(0).peekACard().getAttributes()[3] + "\r\n" + "> " + attributeNames[5] + " "
					+ players.get(0).peekACard().getAttributes()[4];
			
			System.out.println(roundStartInfo);
			
/*			if (test && rounds == 1) {
				
				log += "\n----------CARD ALLOCATION----------" + roundStartInfo + "----------END OF CARD ALLOCATION----------\n";
			}*/
			
			System.out.println("\r\nThere are " + players.get(0).getHand().size() + " cards in your hand\r\n");
		}
	}

	/**
	 * This method cycles through the players ArrayList, and checks if they have any
	 * cards left in their stack in order to determine which Players are still in
	 * the game.
	 */
	public void kickPlayerOut() {

		for (int i = 0; i < players.size(); i++) {

			if (players.get(i).deckEmptyCheck()) {

				players.get(i).hasNoCards();

			}

		}

	}

	/**
	 * This method randomly generates an int between 0 and 4 to determine the first
	 * player. In the event of the user being able to select how many players they
	 * would like to face, this code will need to be adjusted.
	 * 
	 * @return the int associated with the first player.
	 */
	public int firstPlayer() {
		Random rand = new Random();
		int playerID = rand.nextInt(4);
		return playerID;

	}

	/**
	 * This method checks all Player stacks to determine if their is a winner of the
	 * game. If their is, it returns 'false' and exits the while loop associated
	 * with the playARound method.
	 * 
	 * @return Whether or not someone has won the game.
	 */
	public boolean everyoneHasAStack() {
		boolean everyoneHasAStack = true;
		int emptyDecks = 0;
		for (int i = 0; i < players.size(); i++) {
			if (!players.get(i).checkCards()) {
				emptyDecks++;
			}
		}
		if (emptyDecks == (players.size() - 1)) {
			everyoneHasAStack = false;
			hasSomeoneWon = true;

		}

		return everyoneHasAStack;
	}

	/**
	 * This method sends the results of the game to the database after they have
	 * been collected into an Array.
	 */
	public void endOfGame(int[] gameStats) {
		DatabaseInteraction.insertGameStats(gameStats);
	}

}
