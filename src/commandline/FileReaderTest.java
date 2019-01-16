package commandline;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Scanner;

public class FileReaderTest {

	
	public static void main(String[] args) {
		FileReader fR = null;
		String content = "";
		boolean firstLine = true;
		Game currentGame = new Game();
		
		try {
			String filePath = "C:\\Users\\isabe\\Documents\\Southside Serpents\\src\\commandline\\StarCitizenDeck.txt";
			fR = new FileReader(filePath);
			Scanner s = new Scanner(fR);
			String line = s.nextLine();
			String[] attributeNames = line.split(" ");

			while (s.hasNextLine()) {
				line = s.nextLine();
				String[] lineProcess = line.split(" ");
				Card cardObject = Card.CreateCard(lineProcess);
//				System.out.println(cardObject.toString());
				currentGame.createDeck(cardObject);
			}
			
			

		} catch (FileNotFoundException e) {
			System.out.println("No file found");
			e.printStackTrace();

		}  finally {
			try {
				fR.close();
			} catch (IOException e) {
				e.printStackTrace();
			} catch (NullPointerException e) {
//				e.printStackTrace();
			}
		}

		currentGame.displayDeck();
	}

}
