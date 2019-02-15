package commandline;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.jupiter.api.Test;

class PlayerTest {
	Player testPlayer;
	Card testCard;
	
/*	@BeforeClass
	void createPlayer() {
		int[] testArray = new int[] {2, 4, 2, 1, 8};
		String[] namesArray = new String[] {"Size", "Rarity", "Temper", "Cuteness", "Mischief"};

		testPlayer = new AIPlayer("testPlayer"); 
		testCard = new Card("Abyssinian", testArray, namesArray);

	}*/

	@Test
	void setScoreTest() {	
		testPlayer = new AIPlayer("testPlayer"); 

		testPlayer.setScore(5);
		
		assertEquals(5, testPlayer.getScore());
	}
	
	@Test
	void getPlayerNameTest() {
		testPlayer = new AIPlayer("testPlayer"); 

		assertEquals("testPlayer", testPlayer.getPlayerName());
	}
	
	@Test
	void winsRoundTest() {
		testPlayer = new AIPlayer("testPlayer"); 

		testPlayer.winsRound();
		assertEquals(1, testPlayer.getScore());
	}
	
	@Test
	void setPlayerNameTest() {
		testPlayer = new AIPlayer("testPlayer"); 

		testPlayer.setPlayerName("newName");
		assertEquals("newName", testPlayer.getPlayerName());
	}
	
	@Test
	void isEmptyTest() {
		testPlayer = new AIPlayer("testPlayer"); 

		testPlayer.deckEmptyCheck();
		
		assertEquals(testPlayer.hasCards, false);
	}
	
	@Test
	void deckCheck() {
		int[] testArray = new int[] {2, 4, 2, 1, 8};
		String[] namesArray = new String[] {"Size", "Rarity", "Temper", "Cuteness", "Mischief"};

		testPlayer = new AIPlayer("testPlayer"); 
		testCard = new Card("Abyssinian", testArray, namesArray);
		testPlayer.pushToDeck(testCard);
		testPlayer.deckEmptyCheck();
		
		assertEquals(testPlayer.hasCards, true);
	}
	
	
}
