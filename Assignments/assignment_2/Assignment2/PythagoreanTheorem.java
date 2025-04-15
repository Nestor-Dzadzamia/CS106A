/*
 * File: PythagoreanTheorem.java
 * Name: 
 * Section Leader: 
 * -----------------------------
 * This file is the starter file for the PythagoreanTheorem problem.
 */

import acm.program.*;

public class PythagoreanTheorem extends ConsoleProgram {
	
	public void run() {
		int a = readInt("a : ");
		int b = readInt("b : ");
		
		double answer = hypotenuse(a, b);
		println("c = " + answer);
	}
	
	
	// returns hypotenuse of a Right Triangle
	private double hypotenuse(int a, int b) {
		if (a == 0 || b == 0) {
			return 0;
		} else {
			double c = Math.sqrt(a * a + b * b);
			return c;
		}
	}
}
