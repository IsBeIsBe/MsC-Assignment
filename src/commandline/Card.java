package commandline;

import java.util.Arrays;

public class Card {
	/*
	 * Card class, which will be stored within a static array of 40 cards per deck.
	 * Contains a name, and a consistent selection 5 attributes
	 */
	String name;
	int[] attributes = new int[5];

	public Card(String name, int[] attributes) {
		this.name = name;
		this.attributes = attributes;
	}
	
	public Card() {
		
	}

//	public static Card createCard(String[] a) {
//		int[] b = new int[5];
//		String name = a[0];
//		for (int i = 0; i < a.length -1 ; i++) {
//
//			b[i] = Integer.parseInt(a[i + 1]);
//		}
//
//		return new Card(name, b);
//	}
	
	

	@Override
	public String toString() {
		return "Card [name=" + name + ", attributes=" + Arrays.toString(attributes) + "]" + "\n";
	}

	public int[] getAttributes() {
		return attributes;
	}

	public void setAttributes(int[] attributes) {
		this.attributes = attributes;
	}

	public static Card createCard(String[] a) {
		int[] b = new int[5];
		String name = a[0];
		for (int i = 0; i < a.length -1 ; i++) {

			b[i] = Integer.parseInt(a[i + 1]);
		}

		return new Card(name, b);
	}


	

//	public static Card CreateCard(String[] a) {
//		int[] b = new int[5];
//		String name = a[0];
//		for (int i = 0; i < a.length -1 ; i++) {
//
//			b[i] = Integer.parseInt(a[i + 1]);
//		}
//
//		return new Card(name, b);
//	}

}