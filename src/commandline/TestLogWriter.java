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
		WriteLogFile(fileLogInput);
	}

	public void WriteLogFile(String fileLogInput) {
		try {
			File testLog = new File("TestLog.txt");
		//	logWriter = new FileWriter(testLog, true);
			logWriter = new FileWriter(testLog);
			BufferedWriter thisBufWriter = new BufferedWriter(logWriter);
			thisBufWriter.write(fileLogInput);
			thisBufWriter.newLine();
			thisBufWriter.write("------------------------------THANK YOU FOR LOGGING------------------------------");
			thisBufWriter.newLine(); 
			thisBufWriter.close(); 
			//System.out.println("\n " + thisBufWriter.toString());
		}catch(IOException e) {
			e.printStackTrace();
		}
	}

}