package commandline;

import java.util.Arrays;

/**
 * This is the Card class, which will be stored within a static array of 40 cards per deck.
 * 
 * The name and attributes are separated to make identifying each card easier. 
 */
public class Card {

	String name;
	int[] attributes = new int[5];

	/**
	 * The constructor is actually accessed through the 'createACard' method, which handles separating the name
	 * of the card from the rest of the attributes. 
	 */
	public Card(String name, int[] attributes) {
		this.name = name;
		this.attributes = attributes;
	}
	
	public Card() {		
	}

	@Override
	/**
	 * The toString is formatted to make reading the Test Log and related files easier. 
	 */
	public String toString() {
		return "Card [name=" + name + ", attributes=" + Arrays.toString(attributes) + "]" + "\n";
	}

	public int[] getAttributes() {
		return attributes;
	}

	public void setAttributes(int[] attributes) {
		this.attributes = attributes;
	}

	/**
	 * This create a card method recieves the raw data from the Top Trumps deck and creates Card objects by separating the
	 * attributes from the Card's name. 
	 */
	public static Card createCard(String[] a) {

		int[] b = new int[5];
		String name = a[0];
		for (int i = 0; i < a.length -1 ; i++) {

			b[i] = Integer.parseInt(a[i + 1]);
		}

		return new Card(name, b);
	}


}