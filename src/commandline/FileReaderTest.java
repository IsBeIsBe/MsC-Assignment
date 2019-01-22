package commandline;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FilenameFilter;
import java.io.IOException;
import java.util.Scanner;

public class FileReaderTest {

	public static void main(String[] args) {

		FileReader fR = null;
		Game currentGame = new Game();
		Scanner s = new Scanner (System.in);

		try {
			// String filePath = "C:\\Users\\james\\Desktop\\msc\\MsC-Assignment\\src\\commandline\\StarCitizenDeck.txt";
			//String filePath = "C:\\Users\\isabe\\Documents\\Southside Serpents\\src\\commandline\\StarCitizenDeck.txt";
			
			System.out.println("Please enter file name, Serpents");
			
			String fileName = s.nextLine();
			
			String strFilePath = fileName + ".txt";
	        
	        //file object
	        File file = new File(strFilePath);
	        
	        /*
	         * Use getAbsolutePath method of the File class
	         * to get file's absolute path in file system.
	         */
	        System.out.println( file.getAbsolutePath() );
	        
			String filePath = file.getAbsolutePath();
			
			
			fR = new FileReader(filePath);
			s = new Scanner(fR);
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
		currentGame.players.get(1).selectAttributes();
	}

}
