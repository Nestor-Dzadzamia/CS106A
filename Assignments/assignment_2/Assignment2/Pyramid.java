/*
 * File: Pyramid.java
 * Name: 
 * Section Leader: 
 * ------------------
 * This file is the starter file for the Pyramid problem.
 * It includes definitions of the constants that match the
 * sample run in the assignment, but you should make sure
 * that changing these values causes the generated display
 * to change accordingly.
 */

import acm.graphics.*;
import acm.program.*;
import acm.util.RandomGenerator;

import java.awt.*;

public class Pyramid extends GraphicsProgram {

/** Width of each brick in pixels */
	private static final int BRICK_WIDTH = 30;

/** Width of each brick in pixels */
	private static final int BRICK_HEIGHT = 12;

/** Number of bricks in the base of the pyramid */
	private static final int BRICKS_IN_BASE = 14;
	
	private RandomGenerator rgen = RandomGenerator.getInstance();
	
	public void run() {
		double xStart = (getWidth() - BRICKS_IN_BASE * BRICK_WIDTH) / 2;
		double xEnd = getWidth() - ((getWidth() - BRICKS_IN_BASE * BRICK_WIDTH) / 2) - BRICK_WIDTH ;
		
		int yStart = getHeight() - BRICK_HEIGHT;
		int yEnd = getHeight() - 14 * BRICK_HEIGHT;
		
		drawPyramid(xStart, xEnd, yStart, yEnd);
	}

	private void drawPyramid(double xStart, double xEnd, int yStart, int yEnd) {
		GRect rect; // Initializing GRect Object outside for loop, so it's only created once
		Color color;
		/* Second for Iterates over the X coordinates, First for over the Y one.
		   Both's end and start coordinates decrease so at the end it becomes the Pyramid.*/
		for (double j = yStart; j >= yEnd; j -= BRICK_HEIGHT) {
			for (double i = xStart; i <= xEnd; i += BRICK_WIDTH) {
				color = rgen.nextColor(); // Random color to make it more fun
				rect = new GRect(BRICK_WIDTH, BRICK_HEIGHT);
				rect.setFillColor(color);
				rect.setFilled(true);
				add(rect, i, j);
			}
			/* at the end of each for loop, X Start and end coordinates should be lowered by 
			   half of Brick's width and height, so it ends up being a Pyramid. */
			xEnd -= BRICK_WIDTH / 2;
			xStart += BRICK_WIDTH / 2;
		}
	}
}

