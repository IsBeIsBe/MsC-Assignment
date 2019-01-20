package commandline;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Scanner;

public class FileReaderTest {

	public static void main(String[] args) {

		FileReader fR = null;
		Game currentGame = new Game();

		try {
			String filePath = "C:\\Users\\james\\Desktop\\msc\\MsC-Assignment\\src\\commandline\\StarCitizenDeck.txt";
			fR = new FileReader(filePath);
			Scanner s = new Scanner(fR);
			String line = s.nextLine();

			while (s.hasNextLine()) {
				line = s.nextLine();
				String[] lineProcess = line.split(" ");
				Card cardObject = Card.CreateCard(lineProcess);
				currentGame.createDeck(cardObject);
			}

		} catch (FileNotFoundException e) {
			System.out.println("No file found");
			e.printStackTrace();

		} finally {
			try {
				fR.close();
			} catch (IOException e) {
				e.printStackTrace();
			} catch (NullPointerException e) {
				e.printStackTrace();
			}
		}

		System.out.println("Printing deck as read...");
		currentGame.displayDeck();
		System.out.println("Shuffling cards...");
		currentGame.shuffleDeck();
		System.out.println("Printing shuffled deck...");
		currentGame.displayDeck();
		currentGame.dealCards();
		System.out.println("Dealing cards...");
		currentGame.printPlayerCards();
	}

}
