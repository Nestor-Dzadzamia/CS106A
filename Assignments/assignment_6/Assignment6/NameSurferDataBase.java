/*
 * File: NameSurferDataBase.java
 * -----------------------------
 * This class keeps track of the complete database of names.
 * The constructor reads in the database from a file, and
 * the only public method makes it possible to look up a
 * name and get back the corresponding NameSurferEntry.
 * Names are matched independent of case, so that "Eric"
 * and "ERIC" are the same names.
 */

import java.io.BufferedReader;
import java.io.IOException;
import java.util.HashMap;

import acmx.export.java.io.FileReader;

public class NameSurferDataBase implements NameSurferConstants {
	// Creating map to save NameSurferEntry class for each name we added 
	private HashMap<String, NameSurferEntry> namesMap = new HashMap<String, NameSurferEntry>();
	
	
/* Constructor: NameSurferDataBase(filename) */
/**
 * Creates a new NameSurferDataBase and initializes it using the
 * data in the specified file.  The constructor throws an error
 * exception if the requested file does not exist or if an error
 * occurs as the file is being read.
 */
	public NameSurferDataBase(String filename) {
		// using try catch for expected Exceptions.
		try {
			BufferedReader reader = new BufferedReader(new FileReader(filename)); // creating the bufferedReader to read the data file.
			
			while (true) {
				String s = reader.readLine(); // reading each line in file.
				if (s == null) break; // we break if file is over.
				
				NameSurferEntry entry = new NameSurferEntry(s); // initializing NameSurferEntry class for each line.
				namesMap.put(entry.getName(), entry); // putting name and NameSurferEntry in HashMap.
			}
			reader.close(); // closing the reader.
		} catch (IOException e) {
			e.printStackTrace(); // printing StackTrace if found exception
		}
	}
	
/* Method: findEntry(name) */
/**
 * Returns the NameSurferEntry associated with this name, if one
 * exists.  If the name does not appear in the database, this
 * method returns null.
 */
	public NameSurferEntry findEntry(String name) {
		return namesMap.get(name);
	}
}

