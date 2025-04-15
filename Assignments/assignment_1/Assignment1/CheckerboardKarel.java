/*
 * File: CheckerboardKarel.java
 * ----------------------------
 * When you finish writing it, the CheckerboardKarel class should draw
 * a checkerboard using beepers, as described in Assignment 1.  You
 * should make sure that your program works for all of the sample
 * worlds supplied in the starter folder.
 */

import stanford.karel.*;

public class CheckerboardKarel extends SuperKarel {
	
	public void run() {
		if (frontIsBlocked()) {
			fillTheFirstColumn();
		} else {
			while (frontIsClear()) {
				fillTheRows();
			}
		}
	}

	
// 	fills up any world with only 1 column
	private void fillTheFirstColumn() {
		putBeeper();
		if (leftIsClear()) {
			turnLeft();
		}
		while (frontIsClear()) {
			move();
			if (frontIsClear()) {
				move();
				putBeeper();
			}
		}
	}

//  fills up the row despite it is even or odd.
	private void fillTheRows() {
		if (noBeepersPresent()) {
			putBeeper();
		}
		while (frontIsClear()) {
			move();
			if (frontIsClear()) {
				move();
				putBeeper();
			}
		}
		
		if (frontIsBlocked()) {
			
			if (facingEast()) {           // here We check the side of wall we are facing
				
				if (beepersPresent()) {   // We check if row ended with beeper or not
					rotateFromRight();
					if(frontIsClear()) {
						move();
					}
				} else {
					rotateFromRight();
				}
			} else {
				if (beepersPresent()) {
					rotateFromLeft();
					if (frontIsClear()) {
						move();
					}
				} else {
					rotateFromLeft();
				}
			}
		}
	}
	
// 	rotates while stuck on the left wall
	private void rotateFromLeft() {
		if (rightIsClear()) {
			turnRight();
			move();
			turnRight();
		}
		
	}
	
// 	rotates while stuck on the right wall
	private void rotateFromRight() {
		if (leftIsClear()) {
			turnLeft();
			move();
			turnLeft();
		}
	}
}