package commandline;

import static org.junit.jupiter.api.Assertions.*;

import java.util.LinkedList;
import java.util.Queue;

import org.junit.jupiter.api.Test;

class TestingTT {

	@Test 
	void checkPlayerTest() {
		
		String testPlayerName = "";
		int testScore = 40; 
		
		HumanPlayer testPlayer = new HumanPlayer(testPlayerName);
		testPlayer.setScore(testScore);
		
		assertEquals(testScore, testPlayer.getScore());
		
		
		
	}
	
	@Test 
	void popCardTest() {
		
		String testName = "testName";
		int[] testIntArray = {1,2,3,4,5};
		String[] testAttributes = {"a","b","c","e","f"};
		
		Card testCard = new Card(testName, testIntArray, testAttributes);
		
		Queue<Card> testDeck = new LinkedList<>();
		
		
		testDeck.add(testCard); 
		
		testDeck.remove(testCard);
		
		
		assertEquals (true, testDeck.isEmpty());
		
		
		
	}
	
	@Test 
	void peekCardTest() {
		
		String testName = "testName";
		int[] testIntArray = {1,2,3,4,5};
		String[] testAttributes = {"a","b","c","e","f"};
		
		 
		
		Card testCard = new Card(testName, testIntArray, testAttributes);
		
		Queue<Card> testDeck = new LinkedList<>();
		
		
		testDeck.add(testCard); 
		
		
		
		
		assertEquals(testCard, testDeck.peek());
		
	
		
		
		
	}

}