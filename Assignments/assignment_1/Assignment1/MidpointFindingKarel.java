/*
 * File: MidpointFindingKarel.java
 * -------------------------------
 * When you finish writing it, the MidpointFindingKarel class should
 * leave a beeper on the corner closest to the center of 1st Street
 * (or either of the two central corners if 1st Street has an even
 * number of corners).  Karel can put down additional beepers as it
 * looks for the midpoint, but must pick them up again before it
 * stops.  The world may be of any size, but you are allowed to
 * assume that it is at least as tall as it is wide.
 */

import stanford.karel.*;

public class MidpointFindingKarel extends SuperKarel {
	
	public void run() {
		climbUp();
		slideDown();
	}
	
/*	
 * climbs up in the middle of the top row 
 * (With 1 step right and 2 steps upwards)
*/
	private void climbUp() {
		while (frontIsClear()) {
			oneMoveToEast();
			twoMovesToNorth();
			
		}
	}
	

	private void oneMoveToEast() {
		move();      // 	not necessary, but looks better
	}

	private void twoMovesToNorth() {
		if (leftIsClear()) {
			turnLeft();
			move();
			if (frontIsClear()) {
				move();
			}
			if (frontIsClear()) {
				turnRight();
			}
		}
	}

	// moves straight down and puts a beeper
	private void slideDown() {
		turnAround();
		while (frontIsClear()) {
			move();
		}
		putBeeper();
	}
	
	
}
