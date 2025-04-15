/*
 * File: StoneMasonKarel.java
 * --------------------------
 * The StoneMasonKarel subclass as it appears here does nothing.
 * When you finish writing it, it should solve the "repair the quad"
 * problem from Assignment 1.  In addition to editing the program,
 * you should be sure to edit this comment so that it no longer
 * indicates that the program does nothing.
 */

import stanford.karel.*;

public class StoneMasonKarel extends SuperKarel {

	public void run() {
		while (frontIsClear()) {
			rebuildTheColumns();
		}
	}
	
	private void rebuildTheColumns() {
		if (noBeepersPresent()) {
			putBeeper();  // I put beeper here in case column is 1 row tall
		}
		
		turnLeft();
		while (frontIsClear()) {
			if(noBeepersPresent()) {
				putBeeper();
			}
			
			if (frontIsClear()) {
				move();
				if (noBeepersPresent()) {
					putBeeper();
				}
			}
		}
		slideDown();
		rotateToNextColumn();
/*	
 * we are in the last column (while loop will stop in 
 * the last column since frontIsClear() will not be true anymore)		
*/	
		if (frontIsBlocked()) {
			turnLeft();
			while (frontIsClear()) {
				if (noBeepersPresent()) {
					putBeeper();
				}
				move();
				
				if (frontIsBlocked()) {
					if (noBeepersPresent()) {
						putBeeper();
						slideDown();
					} else {
						slideDown();
					}
				}
			}
		}
	}
	
	private void rotateToNextColumn() {
			for (int i = 0; i < 4; i++) {
				move();
			}
	}
//		slides all the way down from the top of the column
	private void slideDown() {
		turnAround();
		while (frontIsClear()) {
			move();
		}
		turnLeft();
	}
}
