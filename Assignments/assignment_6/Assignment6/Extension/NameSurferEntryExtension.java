/*
 * File: NameSurferEntry.java
 * --------------------------
 * This class represents a single entry in the database.  Each
 * NameSurferEntry contains a name and a list giving the popularity
 * of that name for each decade stretching back to 1900.
 */

import acm.util.*;
import java.util.*;

public class NameSurferEntryExtension implements NameSurferConstantsExtension {
	// initiazlining instance variables and Integer Array of decades. 
	private String name = "";
	private String ranks = "";
	private int[] decadeArray = new int[NDECADES];

/* Constructor: NameSurferEntry(line) */
/**
 * Creates a new NameSurferEntry from a data line as it appears
 * in the data file.  Each line begins with the name, which is
 * followed by integers giving the rank of that name for each
 * decade.
 */
	public NameSurferEntryExtension(String line) {
		name = line.substring(0, line.indexOf(" ")); // since first word is line we simple cut from 0 to space index.
		ranks = line.substring(line.indexOf(" ") + 1); // since name is cut we cut off the ranks parts too.
		
		// for loop to itarete over the decade rankings for current Name data.
		for (int i = 0; i < NDECADES - 1; i++) {
			int currentRank = Integer.parseInt(ranks.substring(0, ranks.indexOf(" "))); // cutting of each rank and parseing it to int
			decadeArray[i] = currentRank; // adding each rank to our Array.
			ranks = ranks.substring(ranks.indexOf(" ") + 1); // updating ranks String, (every time we cut out each rank from ranks line); 
		}
		// since substring method won't cut off the last part. (there is no space after last rank)
		// we add it after for loop ends.
		decadeArray[NDECADES - 1] = Integer.parseInt(ranks); 
	}

/* Method: getName() */
/**
 * Returns the name associated with this entry.
 */
	public String getName() {
		return name; // returning input's name. 
	}

/* Method: getRank(decade) */
/**
 * Returns the rank associated with an entry for a particular
 * decade.  The decade value is an integer indicating how many
 * decades have passed since the first year in the database,
 * which is given by the constant START_DECADE.  If a name does
 * not appear in a decade, the rank value is 0.
 */
	public int getRank(int decade) {
		return decadeArray[decade]; // returning decade array's values. 
	}

/* Method: toString() */
/**
 * Returns a string that makes it easy to see the value of a
 * NameSurferEntry.
 */
	public String toString() {
		String s = '"' + name + " ["; // creating first parts of final string.
		for (int i = 0; i < NDECADES - 1; i++) {
			s += decadeArray[i] + " "; // adding each array value by for loop (except last one, last one doen't need space);
		}
		s += decadeArray[NDECADES - 1] + "]\""; // adding the last value and end braces;
		return s; // returning String of decade array.
	}
}

