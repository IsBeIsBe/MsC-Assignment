package commandline;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import java.util.Scanner;

public class TestLogWriter {
	
	FileWriter logWriter;
	BufferedWriter thisBufWriter; 
	String fileLogInput = "";
	
	public TestLogWriter(String x) {
		this.fileLogInput = x; 
	}

	public void WriteLogFile(String fileLogInput) {
		try {
			File testLog = new File("Test.txt");
			logWriter = new FileWriter(testLog, true);
			BufferedWriter thisBufWriter = new BufferedWriter(logWriter);
			System.out.println("this text here " + testLog);
			thisBufWriter.write(fileLogInput);
			thisBufWriter.newLine();
			thisBufWriter.write("-------------------------------------------------------------------------------------------");
			thisBufWriter.newLine(); 
			thisBufWriter.close(); 
			System.out.println("\n " + thisBufWriter.toString());
		}catch(IOException e) {
			e.printStackTrace();
		}
	}

}