/*
 * File: HangmanLexicon.java
 * -------------------------
 * This file contains a stub implementation of the HangmanLexicon
 * class that you will reimplement for Part III of the assignment.
 */
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.StringTokenizer;
import acm.util.*;
import java.util.ArrayList;

public class HangmanLexicon {
	//In this is arrayList program keeps all the word read from file
	public ArrayList<String> words=new ArrayList<String>();

	public HangmanLexicon(){
		try{
			BufferedReader reader=new BufferedReader(new FileReader("HangmanLexicon.txt"));
			while(true)
			{
				String word=reader.readLine();
				if(word==null)
				{
					break;
				}
				else
				{
					words.add(word);
				}
			}
			reader.close();
		}
		catch(IOException e){
			e.printStackTrace();
		}
	}
	
/** Returns the number of words in the lexicon. */
	public int getWordCount() {
		return words.size();
	}

/** Returns the word at the specified index. */
	public String getWord(int index) {
		return words.get(index);
	}
}
