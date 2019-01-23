package commandline;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

public class Game {

	int rounds;
	boolean aiWon;
	ArrayList<Card> deck = new ArrayList<>();
	ArrayList<Player> players = new ArrayList<>();
	
	public Game(ArrayList<Card> inputDeck) {
		this.deck = inputDeck;
	}

	public void displayDeck() {
		
		System.out.println(deck);
	}

	public ArrayList<Player> createPlayers(int numberOfAIPlayers) {

		players.add(new HumanPlayer());

		for (int i = 0; i < numberOfAIPlayers; i++) {

			players.add(new AIPlayer());
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
		
		ArrayList<Card> commonPile = new ArrayList<>();
		
		this.players = createPlayers(4);
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
		int selector = firstPlayer();
		Card firstCard = players.get(selector).peekACard(); // top card of hand
		System.out.println("The player at position " + selector + " is choosing which card to play");
		
		int comparator = players.get(selector).selectAttribute(firstCard); // position in array of highest attribute
		
		System.out.println("player: " + selector + " has chosen the value at position: " + 
		comparator + " from " + firstCard);
		// Card has been selected above
		
		// This will compare the selected card against the other player's cards
		// playersCard will be compared to other cards. Iterate through other players cards
		// comparing the peeked cards of each.
		int winner = selector;
		for (int i = 0; i < 5; i++) {
			if (i == selector) {
				i++;
			} else {
				Card playersCard = players.get(i).peekACard(); // card of other player(s)
				if (playersCard.attributes[comparator] > firstCard.attributes[comparator]) { // if other players card is higher than selectors card then
					winner = i;
				}				
			}
		}

		Player winnerPlayer = players.get(winner);
		
		System.out.println("The player at position " + winner + " has won with his card: "
				+ winnerPlayer.peekACard());
		
		for (int i = 0; i < 5; i++) { // adds the played cards of all the players to the common pile
			commonPile.add(players.get(i).popACard());
		}
		
		for (int i = 0; i < commonPile.size(); i++) { // pushes all of the common pile cards to the winners hand
			players.get(winner).pushToDeck(commonPile.get(i));
		}
		
		for (int i = 0; i < 5; i++) { // CHECK the correct cards in hand - troubleshooting
			players.get(i).displayPlayerHand();
		}
		System.out.println("The end");
		/*
		 * Round should start here?
		 */
		


//		Player selectorTest = players.get(1);
//		System.out.println("Test");
//		Card testCard = selectorTest.popACard();
//		System.out.println(testCard);
//		selectorTest.selectAttribute(testCard);

			
			
		
	}
	
	public int firstPlayer() {
		Random rand = new Random();
		int playerID = rand.nextInt(4);
		return playerID;		
		
	}

}
