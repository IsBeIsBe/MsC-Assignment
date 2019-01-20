import java.util.Stack;

public abstract class Player {

	private Integer score;
	private Stack<String> deck;
	
	public Player(Integer score, Stack<String> deck) {
		
		this.score = score;
		this.deck = deck;
		
	}
	
	public abstract void selectCategory();

	public Integer getScore() {
		
		return score;
		
	}

	public void setScore(Integer score) {
		
		this.score = score;
		
	}
	
	
	
}
