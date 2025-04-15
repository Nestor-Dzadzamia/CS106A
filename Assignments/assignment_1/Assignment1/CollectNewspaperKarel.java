/*
 * File: CollectNewspaperKarel.java
 * --------------------------------
 * At present, the CollectNewspaperKarel subclass does nothing.
 * Your job in the assignment is to add the necessary code to
 * instruct Karel to walk to the door of its house, pick up the
 * newspaper (represented by a beeper, of course), and then return
 * to its initial position in the upper left corner of the house.
 */

import stanford.karel.*;

public class CollectNewspaperKarel extends SuperKarel {

	public void run() {
		moveToNewspaper();
		pickBeeper();
		getBack();
		
	}
//	  moves to newspaper to pick it up
	public void moveToNewspaper() {
		doubleMove();
		moveRight();
		moveLeft();
	}
//	  Gets back to the Starting Position
	public void getBack() {
		moveBack();
		moveLeft();
		turnRight();
		doubleMoveBack();
	}
	
	private void moveRight() {
		turnRight();
		move();
	}

	private void moveLeft() {
		turnLeft();
		move();
	}

	private void doubleMove() {
		move();
		move();
	}

	private void doubleMoveBack() {
		turnAround();
		move();
		move();
		turnAround();
	}

	private void moveBack() {
		turnAround();
		move();
		turnAround();
	}
	
	
}
