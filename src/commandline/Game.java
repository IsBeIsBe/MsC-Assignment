package commandline;

import java.util.ArrayList;
import java.util.Collections;

public class Game {

	int rounds;
	boolean aiWon;
	ArrayList<Card> deck = new ArrayList<>();
	ArrayList<Player> players = new ArrayList<>();

	public void createDeck(Card c) {
		
		deck.add(c);
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

		createPlayers(3);

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
}
