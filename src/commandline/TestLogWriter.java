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
	
	public TestLogWriter(String log) {
		this.fileLogInput = log; 
	}

	public void WriteLogFile() {
		try {
			File testLog = new File("Log.txt");
			logWriter = new FileWriter(testLog);
			BufferedWriter thisBufWriter = new BufferedWriter(logWriter);
			thisBufWriter.write(fileLogInput);
			thisBufWriter.newLine();
			thisBufWriter.close(); 
		}catch(IOException e) {
			e.printStackTrace();
		}
	}

}