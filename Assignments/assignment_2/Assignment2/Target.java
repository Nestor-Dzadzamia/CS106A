/*
 * File: Target.java
 * Name: 
 * Section Leader: 
 * -----------------
 * This file is the starter file for the Target problem.
 */

import acm.graphics.*;
import acm.program.*;
import java.awt.*;

public class Target extends GraphicsProgram {
	// Adjustable information about Target
	private static final double RADIUS_LARGE = 72;
	private static final double RADIUS_MEDIUM = 72 / 2.54 * 1.65;
	private static final double RADIUS_SMALL = 72 / 2.54 * 0.76;
	
	public void run() {
		double width = getWidth();
		double height = getHeight();

		drawTarget(width, height);
	}
	// We pass drawTarget medthod Width and Height of a default Canvas.
	private void drawTarget(double width, double height) {
		// First Large Circle
		GOval oval = new GOval(2 * RADIUS_LARGE, 2 * RADIUS_LARGE);
		oval.setFilled(true);
		oval.setFillColor(Color.RED);
		add(oval, width / 2 - RADIUS_LARGE, height / 2 - RADIUS_LARGE);
		
		// Second Mid Circle
		oval = new GOval(2 * RADIUS_MEDIUM, 2 * RADIUS_MEDIUM);
		oval.setFilled(true);
		oval.setFillColor(Color.WHITE);
		add(oval, width / 2 - RADIUS_MEDIUM, height / 2 - RADIUS_MEDIUM);
		
		// Third Small Circle
		oval = new GOval(2 * RADIUS_SMALL, 2 * RADIUS_SMALL);
		oval.setFilled(true);
		oval.setFillColor(Color.RED);
		add(oval, width / 2 - RADIUS_SMALL, height / 2 - RADIUS_SMALL);
	}
}
