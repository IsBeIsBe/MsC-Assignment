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

		try {
			String filePath = "C:\\Users\\isabe\\Documents\\Southside Serpents\\src\\commandline\\StarCitizenDeck.txt";
			fR = new FileReader(filePath);
			Scanner s = new Scanner(fR);

			while (s.hasNextLine()) {
				content += s.nextLine();
				content += "\r\n";
			}

		} catch (FileNotFoundException e) {
			System.out.println("No file found");
			e.printStackTrace();
			
		} catch (NullPointerException e) {
			System.out.println("Please select a file");
//			e.printStackTrace();

		} finally {
			try {
				fR.close();
			} catch (IOException e) {
				e.printStackTrace();
			} catch (NullPointerException e) {
//				e.printStackTrace();
			}
		}
		
		System.out.println(content);
	}

}
