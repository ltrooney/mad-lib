import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.Scanner;


public class MadLib {
	
	File file; 								// mad lib file
	Hashtable<String, Integer> phraseCount;
	Hashtable<String, ArrayList<String>> phrases;	// dictionary of words
	int numPhrases = 0; 	// keeps track of how many phrases are in the mad lib
	
	public MadLib(File f) {
		file = f;
		phraseCount = new Hashtable<String, Integer>();
		phrases = new Hashtable<String, ArrayList<String>>();
	}
	
	public void compileLib() {
		parseFile();
		getAnswers();
		createMadLib();
	}
	
	public void parseFile() {
		try {
			FileReader fr = new FileReader(file);
			BufferedReader br = new BufferedReader(fr);
			
			String line = "";
			
			// read through each line, terminate at EOF
			while((line = br.readLine())!= null) {
				if(line.contains("(") && line.contains(")")) {
					int openParen = line.indexOf("(");
					int closeParen = line.indexOf(")");
					String wordType = line.substring(openParen+1, closeParen);		// the type of phrase that will be asked
					
					if(!phraseCount.containsKey(wordType)) {
						phraseCount.put(wordType, 1);	
					} else {
						phraseCount.put(wordType, phraseCount.get(wordType) + 1);
					}
					numPhrases++;
				}
			}			
		} catch(IOException e) {
			System.out.println("Cannot parse file " + file.getName());
		};
	}	
	
	public void getAnswers() {
		Scanner scan = new Scanner(System.in);
		System.out.println("Please enter values for each part of speech");
		System.out.println("-------------------------------------------");
		
		int counter = 1;
		
		// iterate through each empty phrase and get a value for it
		for(Enumeration<String> e = phraseCount.keys(); e.hasMoreElements();) {
			String wordType = e.nextElement();						// word type (key)
			phrases.put(wordType, new ArrayList<String>());
			
			for(int i = 0; i < phraseCount.get(wordType); i++) {
				System.out.print("("+counter+"/"+numPhrases+")  " + wordType + ": ");
				String phrase = scan.nextLine();					// get the phrase
				counter++;
			
				ArrayList<String> a = phrases.get(wordType);
				a.add(phrase);										// appends the phrase to the value of the 'word type' key
				phrases.put(wordType, a);
			}		
		}	
	}
	
	public void createMadLib() {
		try {
			String outFileName = "madlib-scripts/out/" + file.getName().substring(0, file.getName().length()-4) + "-out.txt";
			FileReader fr = new FileReader(file);
			FileWriter fw = new FileWriter(new File(outFileName));
			BufferedReader br = new BufferedReader(fr);
			
			String line = "";
			
			// read through each line, terminate at EOF
			while((line = br.readLine())!= null) {
				if(line.contains("(") && line.contains(")")) {
					int openParen = line.indexOf("(");
					int closeParen = line.indexOf(")");
					String wordType = line.substring(openParen+1, closeParen);
					
					String newLine = "";
					newLine += line.substring(0, line.indexOf("("));	// adds everything before (
					
					ArrayList<String> phrase = phrases.get(wordType);
					newLine += phrase.remove(0);						// adds the phrase to the output file
					
					newLine += line.substring(line.indexOf(")")+1, line.length());	// adds everything after )
					
					System.out.println("writing..."+newLine);
					fw.write(newLine + "\n");
				}
			}	
			
			fw.close();
		} catch(IOException e) {
			System.out.println("Cannot parse file " + file.getName());
		};
	}
}
