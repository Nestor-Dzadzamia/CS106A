
/*
 * File: HangmanLexicon.java
 * -------------------------
 * This file contains a stub implementation of the HangmanLexicon
 * class that you will reimplement for Part III of the assignment.
 */

import java.io.BufferedReader;
import java.io.IOException;
import java.util.ArrayList;

import acmx.export.java.io.FileReader;

public class extensionHangmanLexicon {
	// initializing ArrayList to save lexicon words.
	private ArrayList<String> list = new ArrayList<String>();

	/** Returns the number of words in the lexicon. */
	public int getWordCount() {
		createLexicon();
		return list.size();
	}

	/** Returns the word at the specified index. */
	public String getWord(int index) {
		return list.get(index);
	}

	// method fills the arraylist with words.
	private void createLexicon() {
		try {
			BufferedReader rd = new BufferedReader(new FileReader("HangmanLexicon.txt")); // opening filereader.

			while (true) {
				String word = rd.readLine();
				if (word == null) {
					break;
				}
				list.add(word);
			}
			rd.close(); // closing filereader.
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
