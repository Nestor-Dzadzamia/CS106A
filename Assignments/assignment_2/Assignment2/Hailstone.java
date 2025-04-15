/*
 * File: Hailstone.java
 * Name: 
 * Section Leader: 
 * --------------------
 * This file is the starter file for the Hailstone problem.
 */

import acm.program.*;

public class Hailstone extends ConsoleProgram {
	public void run() {
		int number = readInt("Enter a number : ");
		int count = 0;

		
		println("The process took " + hailStone(number, count) + " to reach 1");
	}
	
	// Method Returns the total number of trials needed to get the Original number equal to 1.
	private int hailStone(int number, int count) {
		int temp; // We create temp variable to save number's Previous value to print it afterwards.
		if (number <= 0) return -1; // We should only Enter natural numbers, otherwise we Return -1
		
		while (number != 1) {
			temp = number;
			count++;
			if (number % 2 == 0) {
				number /= 2;
				println(temp + " is even so I take half : " + number);
			} else {
				number = number * 3 + 1;
				println(temp + " is odd, so I make 3n + 1: " + number);
			}
		}
		return count;
	}
}

