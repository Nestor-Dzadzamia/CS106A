/*
 * File: FindRange.java
 * Name: 
 * Section Leader: 
 * --------------------
 * This file is the starter file for the FindRange problem.
 */

import acm.program.*;

public class FindRange extends ConsoleProgram {
	private static final int SYMBOL = 0;
	
	public void run() {
		println("This program finds the largest and smallest numbers");
		int min = 0;
		int max = 0;
		int number = readInt("? ");
		
		findRange(number, min, max);
	}
	
	private void findRange(int number, int min, int max) {
		if (number != SYMBOL) {
			min = number;	// First Entered Number is Smallest and Largest
			max = number;
		} else {
			println("You have not Entered numbers, so by default :");
		}

		while (number != SYMBOL) {
			number = readInt("? "); // Input Other Values
			if (number >= max && number != SYMBOL) {
				max = number; // Input number is Larger then our Max, so now new Number becomse Max.
			} else if (number <= min && number != SYMBOL) {
				min = number;  // Input number is Smaller then our Min, so now new Number becomse Min.
			}
		}
		
		println("MAX : " + max);
		println("MIN : " + min);
	}
}

