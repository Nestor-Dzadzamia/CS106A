/*
 * File: ProgramHierarchy.java
 * Name: 
 * Section Leader: 
 * ---------------------------
 * This file is the starter file for the ProgramHierarchy problem.
 */

import java.awt.Font;

import acm.graphics.GLabel;
import acm.graphics.GLine;
import acm.graphics.GRect;
import acm.program.GraphicsProgram;

public class ProgramHierarchy extends GraphicsProgram {
	// Information about Adjustable Details of program
	private static final int RECT_WIDTH = 180;
	private static final int RECT_HEIGHT = 80;
	private static final int WIDTH_GAP_BETWEEN_RECTS = 30;
	private static final int HEIGHT_GAP_BETWEEN_RECTS = 50;
	private static final int FONT_SIZE = 20;
	
	public void run() {
		// Initializing Starting Coordinates
		double xStart = (getWidth() - 3 * RECT_WIDTH - 2 * WIDTH_GAP_BETWEEN_RECTS) / 2;
		double xEnd = getWidth() - xStart - RECT_WIDTH;
		
		double yStart = getHeight() - ((getHeight() - 2 * RECT_HEIGHT - HEIGHT_GAP_BETWEEN_RECTS) / 2) - RECT_HEIGHT;
		double yEnd = yStart - HEIGHT_GAP_BETWEEN_RECTS - RECT_HEIGHT;
		
		drawRectangles(xStart, xEnd, yStart, yEnd);
		drawConnectingLines(xStart, xEnd, yStart, yEnd);
		addLabels(xStart, xEnd, yStart, yEnd);
	}

	private void drawRectangles(double xStart, double xEnd, double yStart, double yEnd) {
		GRect rect; // Making GRect object Outside so it wont create new Objects in for loop
		for (double i = yStart; i >= yEnd; i-= HEIGHT_GAP_BETWEEN_RECTS + RECT_HEIGHT) {
			for (double j = xStart; j <= xEnd; j += RECT_WIDTH + WIDTH_GAP_BETWEEN_RECTS) {
				rect = new GRect(RECT_WIDTH, RECT_HEIGHT);
				add(rect, j, i);
			}
			// Adjusting Coordinates
			xStart += RECT_WIDTH + WIDTH_GAP_BETWEEN_RECTS;
			xEnd -= RECT_WIDTH + WIDTH_GAP_BETWEEN_RECTS;
		}
	}
	
	private void drawConnectingLines(double xStart, double xEnd, double yStart, double yEnd) {
		xStart += RECT_WIDTH; // x Starting Coordinate
		xEnd += RECT_WIDTH;  // y Starting Coordinate
		
		yStart += RECT_HEIGHT / 2; // x ending Coordinate
		yEnd += RECT_HEIGHT; // y ending Coordinate
		
		GLine line; // Making GLine Object outside the for loop, so it won't create new object every time.
		// this for loop makes horizontal lines
		for (double i = xStart; i < xEnd; i+= RECT_WIDTH) {
			line = new GLine(i, yStart, i += WIDTH_GAP_BETWEEN_RECTS, yStart);
			add(line);
		}
		xStart -= RECT_WIDTH / 2;	// new Coordinates for Upper lines 
		yStart -= RECT_HEIGHT / 2;	
		
		xEnd = xStart + 2 * RECT_WIDTH + 2 * WIDTH_GAP_BETWEEN_RECTS;
		yEnd = yStart - HEIGHT_GAP_BETWEEN_RECTS;
		
		double xMid = xStart + RECT_WIDTH + WIDTH_GAP_BETWEEN_RECTS; // that's the Coordinate of rectangle mid-point
		for (double i = xStart; i <= xEnd; i += RECT_WIDTH + WIDTH_GAP_BETWEEN_RECTS) {
			line = new GLine(i, yStart, xMid, yEnd);
			add(line);
		}
	}
	
	private void addLabels(double xStart, double xEnd, double yStart, double yEnd) {
		xStart += RECT_WIDTH / 2;
		yStart += RECT_HEIGHT / 2; 
		GLabel label; // Creating Object outside the for loop
			
		label = new GLabel("GraphicsProgram");
		label.setFont(new Font("Arial", Font.BOLD, FONT_SIZE));
		label.setLocation(xStart - label.getWidth() / 2, yStart + label.getAscent() / 2);
		label.setVisible(true);
		add(label);
		xStart += RECT_WIDTH + WIDTH_GAP_BETWEEN_RECTS;

		label = new GLabel("ConsoleProgram");
		label.setFont(new Font("Arial", Font.BOLD, FONT_SIZE));
		label.setLocation(xStart - label.getWidth() / 2, yStart + label.getAscent() / 2);
		label.setVisible(true);
		add(label);

		label = new GLabel("Program");
		label.setFont(new Font("Arial", Font.BOLD, FONT_SIZE));
		label.setLocation(xStart - label.getWidth() / 2, getHeight() - yStart + label.getAscent() / 2);
		label.setVisible(true);
		add(label);
		xStart += RECT_WIDTH + WIDTH_GAP_BETWEEN_RECTS;

		label = new GLabel("DialogProgram");
		label.setFont(new Font("Arial", Font.BOLD, FONT_SIZE));
		label.setLocation(xStart - label.getWidth() / 2, yStart + label.getAscent() / 2);
		label.setVisible(true);
		add(label);
	}
}