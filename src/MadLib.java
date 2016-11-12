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
				if(!(line.contains("(") && line.contains(")"))) {
					continue;
				}
				
				int openParen = 0;						// tracks index of open parenthesis	
				int closedParen = 0;					// tracks index of closed parenthesis
				int lastPhraseEnd = 0;					// tracks index of where the last phrase ends
				
				while((openParen = line.indexOf("(", closedParen)) != -1) {			// iterates while there are still '(' left in the line					
					String outputLine = "";
					outputLine = line.substring(lastPhraseEnd, openParen);			// get all words from after the last phrase up until the parenthesis
					
					
					closedParen = line.indexOf(")", openParen+1);					// tracks index of corresponding closed parenthesis
					String wordType = line.substring(openParen+1, closedParen);		// the type of phrase that will be asked
					
					System.out.print(wordType+": ");
					String phrase = scan.nextLine();								// gets the phrase from the user for the word type
					
					
					outputLine += phrase;											// adds the phrase to the output file
					fw.write(outputLine);											// prints from (not including) last phrase to (including) new phrase
					
					lastPhraseEnd = closedParen+1;									// index of last letter of last phrase
					
					// can print to file from after the last phrase up until the current phrase
				}
				fw.write(line.substring(lastPhraseEnd, line.length()));		// adds rest of the line
				fw.write("\n");
			}	
			scan.close();
			br.close();
			fw.close();
		} catch(IOException e) {
			System.out.println("Cannot parse file " + file.getName());
		};
	}	
}
