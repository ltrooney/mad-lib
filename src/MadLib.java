import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.Scanner;


public class MadLib {
	
	File file; 										// mad lib script file
	
	public MadLib(File f) {
		file = f;
	}
	
	public void compileLib() {
		try {
			Scanner scan = new Scanner(System.in);
			
			FileReader fr = new FileReader(file);
			BufferedReader br = new BufferedReader(fr);
			
			String outFileName = "madlib-scripts/out/" + file.getName().substring(0, file.getName().length()-4) + "-out.txt";
			FileWriter fw = new FileWriter(new File(outFileName));
			
			String line = "";								// current line of file being read
			
			System.out.println("Please enter values for each part of speech");
			System.out.println("-------------------------------------------");
			
			// read through each line, terminate at EOF
			while((line = br.readLine())!= null) {
				if(line.contains("(") && line.contains(")")) {
					int openParen = line.indexOf("(");
					int closeParen = line.indexOf(")");
					String wordType = line.substring(openParen+1, closeParen);		// the type of phrase that will be asked
					
					System.out.print(wordType+": ");
					String phrase = scan.nextLine();								// gets the phrase from the user for the word type
					
					
					String outputLine = "";
					outputLine += line.substring(0, line.indexOf("("));				// adds everything before (
					outputLine += phrase;											// adds the phrase to the output file
					outputLine += line.substring(line.indexOf(")")+1, line.length());	// adds everything after )
					
					System.out.println("writing..."+outputLine);
					fw.write(outputLine + "\n");
				}				
			}	
			scan.close();
			br.close();
			fw.close();
		} catch(IOException e) {
			System.out.println("Cannot parse file " + file.getName());
		};
	}	
}
