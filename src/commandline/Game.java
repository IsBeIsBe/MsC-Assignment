package commandline;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

public class Game {

	int rounds;
	boolean aiWon;
	int selector;
	
	ArrayList<Card> deck = new ArrayList<>();
	ArrayList<Player> players = new ArrayList<>();
	ArrayList<Card> commonPile = new ArrayList<>();
	int[] gameStats = new int[6];
	
	public Game(ArrayList<Card> inputDeck) {
		this.deck = inputDeck;
	}

	public void displayDeck() {
		
		System.out.println(deck);
	}

	public ArrayList<Player> createPlayers(int numberOfAIPlayers) {
		
		players.add(new HumanPlayer("Human Player"));
		

		for (int i = 0; i < numberOfAIPlayers; i++) {
			String nameTemp = "AI Player" + (i + 1);
			players.add(new AIPlayer(nameTemp));
		}
		return players;
	}

	public void shuffleDeck() {

		Collections.shuffle(deck);
	}

	public void dealCards() {

//		createPlayers(3);

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

	public void printPlayerCards() {

		for (int i = 0; i < players.size(); i++) {

			players.get(i).displayPlayerHand();
		}
	}
	
	public void playGame() {
		/*Start of the game
		 * 
		 */
		
		
		this.players = createPlayers(4);
		
		for (int i = 0; i < players.size(); i++) {
		System.out.println(players.get(i).getPlayerName());
		System.out.println();
		}
		
		
		System.out.println("Printing deck as read...");
		displayDeck();
		System.out.println("Shuffling cards...");
		shuffleDeck();
		System.out.println("Printing shuffled deck...");
		displayDeck();
		dealCards();
		System.out.println("Dealing cards...");
		printPlayerCards();
		
		//Selecting first player by randomised method and popping their card
		selector = firstPlayer();
		
		
		/*
		 * While loop for going through rounds until only one person has a stack left.
		 */
		while (everyoneHasAStack()) {
			playARound(selector);
		} 
		
		endOfGame(gameStats);

		
		System.out.println("The end");	
			
		
	}
	
	/*
	 * Code for running a round -- is fed the player who chooses the attributes for the round, known as 'selector'.
	 */
	public void playARound(int roundSelector) {
		
		
		
		Card firstCard = players.get(roundSelector).peekACard(); // top card of hand
		System.out.println(players.get(roundSelector).getPlayerName() + " is choosing which card to play");
		
		int comparator = players.get(roundSelector).selectAttribute(firstCard); // position in array of highest attribute
		
		System.out.println("player: " + roundSelector + " has chosen the value at position: " + 
		comparator + " from " + firstCard);
		// Card has been selected above
		
		// This will compare the selected card against the other player's cards
		// playersCard will be compared to other cards. Iterate through other players cards
		// comparing the peeked cards of each.
		int winner = roundSelector;
		for (int i = 0; i < players.size(); i++) {
			if (i == roundSelector) {
				i++;
			} else {
				
				Card playersCard = players.get(i).peekACard(); // card of other player(s)
				if (playersCard.attributes[comparator] > firstCard.attributes[comparator]) { // if other players card is higher than selectors card then
					winner = i;
					selector = i;
				}				
			}
		}


		
		Player winnerPlayer = players.get(winner);
		
		System.out.println(players.get(winner).getPlayerName() + " has won with his card: "
				+ winnerPlayer.peekACard());
		
		for (int i = 0; i < players.size(); i++) { // adds the played cards of all the players to the common pile
			commonPile.add(players.get(i).popACard());
		}
		
		System.out.print("Common Pile: " + commonPile);
		int commonSize = commonPile.size();
		for (int i = 0; i != 5; i++) { // pushes all of the common pile cards to the winners hand
			players.get(winner).pushToDeck(commonPile.remove(0));
		}
		
		for (int i = 0; i < players.size(); i++) { // CHECK the correct cards in hand - troubleshooting
			System.out.println(players.get(i).getPlayerName()); 
			players.get(i).displayPlayerHand();
		}
		
		kickPlayerOut();

		
		
	}
	
	public void kickPlayerOut() {
		
		for (int i = 0; i < players.size(); i++) {
			
			if (players.get(i).deckEmptyCheck()) {
				
				players.remove(i);
				
			}
			
		}
		
	}
	
	
	public int firstPlayer() {
		Random rand = new Random();
		int playerID = rand.nextInt(4);
		return playerID;		
		
	}
	
	/*
	 * Need to write a method here that essentially checks if there has been a winner by looking at the player stacks. 
	 */
	public boolean everyoneHasAStack() {
		boolean everyoneHasAStack = true;
		if (players.size() == 1) {
			everyoneHasAStack = false;
		}
		
		return everyoneHasAStack;
	}
	
	/*
	 * This method shoulld maybe include the print statements as well but def needs to send the game stats to the Database.
	 */
	public void endOfGame(int[] gameStats) {
		DatabaseInteraction.insertGameStats(gameStats);
	}

}
