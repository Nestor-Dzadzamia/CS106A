/*
 * File: NameSurferGraph.java
 * ---------------------------
 * This class represents the canvas on which the graph of
 * names is drawn. This class is responsible for updating
 * (redrawing) the graphs whenever the list of entries changes or the window is resized.
 */

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.util.ArrayList;

import acm.graphics.GCanvas;
import acm.graphics.GLabel;
import acm.graphics.GLine;
// Current class creates GCanvas and makes the whole graph and visual thing.
public class NameSurferGraphExtension extends GCanvas
	implements NameSurferConstantsExtension, ComponentListener {
	// creating instance variables
	private ArrayList<NameSurferEntryExtension> entryList; // here we create list to save Entries that have been made.
	private Color red;
	private Color blue;
	private Color yellow;
	

	/**
	* Creates a new NameSurferGraph object that displays the data.
	*/
	public NameSurferGraphExtension() {
		// adding component listeners to make sure that after resizing program, graphs will display normally.
		addComponentListener(this); 
		entryList = new ArrayList<NameSurferEntryExtension>(); // we initalizize variables in constructor
		red = Color.RED;
		blue = Color.BLUE;
		yellow = Color.YELLOW;
	}
	
	/**
	* Clears the list of name surfer entries stored inside this class.
	*/
	public void clear() {
		entryList.clear(); // we clear the list 
		update(); // and update the canvas (since we clear the list, only default lines will be re - drawn).
	}
	
	/* Method: addEntry(entry) */
	/**
	* Adds a new NameSurferEntry to the list of entries on the display.
	* Note that this method does not actually draw the graph, but
	* simply stores the entry; the graph is drawn by calling update.
	*/
	public void addEntry(NameSurferEntryExtension entry) {
		entryList.add(entry); // adding new NameSurfersEntry class that was inputed to our list  
	}
	
	public void delete(String name) {
		for (int i = 0; i < entryList.size(); i++) {
			NameSurferEntryExtension temp = entryList.get(i);
			if (temp.getName().equals(name)) {
				entryList.remove(i);
				return;
			}
		}
		System.out.println("There is no Graph Drawn by this name");
	}
	
	/**
	* Updates the display image by deleting all the graphical objects
	* from the canvas and then reassembling the display according to
	* the list of entries. Your application must call update after
	* calling either clear or addEntry; update is also called whenever
	* the size of the canvas changes.
	*/
	public void update() {
		removeAll(); // each update method should remove everything and recreate the display
		drawDefaultCanvas(); // creating default lines
		
		
		int colorType = 1; // variable to save color type
		// initializing font size variable that is depended on programs width and height, so it changes as we resize canvas.
		int fontSize = getHeight() / 100 + getWidth() / 100;
		// for loop to iterate over the entrylist and draw each of them
		for (int i = 0; i < entryList.size(); i++) {
			drawCurrentEntry(colorType, entryList.get(i), fontSize);
			if (colorType == 4) colorType = 0; // as there should be 4 color types, we reset it to 0 as it reaches to 4.
			colorType++; // we increase color type by 1.
		}
	}
	
	// method gets font size, color type, and NameSurfer Entry class, to make graphs of current class. 
	private void drawCurrentEntry(int colorType, NameSurferEntryExtension e, int fontSize) {
		int rankCount = 0;
		
		// for loop to itarate over the vertical lines of canvas.
		for (double xCoordinate = 0; xCoordinate <= getWidth() - 2 * (double)(getWidth() / NDECADES); xCoordinate += (double)(getWidth() / NDECADES)) {
			double yStart = countYCoordinate(e.getRank(rankCount)); // vertical starting coordinate.
			double yEnd = countYCoordinate(e.getRank(rankCount + 1)); // vertical ending coordinate.
			
			// drawing the conecting line between to vertical lines.
			GLine line = new GLine(xCoordinate , yStart, xCoordinate + getWidth() / NDECADES, yEnd); 
			GLabel label1, label2; // creating labels, one label to display line's start point (name) and one to display end point.
			
			// checking if rank is 0, if so we must change it to *
			if (e.getRank(rankCount) == 0) {
				label1 = new GLabel(e.getName() + " " + "*");
			} else {
				label1 = new GLabel(e.getName() + " " + e.getRank(rankCount));
			}
			
			// now checking +1 index rank , since we add 2 labels in each try.(start point label and end point label - of each graph line);
			if (e.getRank(rankCount + 1) == 0) {
				label2 = new GLabel(e.getName() + " " + "*");
			} else {
				label2 = new GLabel(e.getName() + " " + e.getRank(rankCount + 1));
			}
			
			// creating label font to make it cleaner, + attaching size to labels. 
			label1.setFont(new Font("Arial", Font.BOLD, fontSize));
			label2.setFont(new Font("Arial", Font.BOLD, fontSize));
			
			// creating switch to determine the colortype and after that paint the following graph by the following color.
			switch (colorType) {
			case 2:
				line.setColor(red);
				label1.setColor(red);
				label2.setColor(red);
				break;
			case 3:
				line.setColor(blue);
				label1.setColor(blue);
				label2.setColor(blue);
				break;
			case 4: 
				line.setColor(yellow);
				label1.setColor(yellow);
				label2.setColor(yellow);
				break;
			}
			
			// since graph contains only 10 lines, we use this if statement, otherwise it would draw the last unnecessary line too.
			if (rankCount < 9) rankCount++; 
			
			// adding each label and line to canvas
			add(label1, xCoordinate, yStart); 
			add(label2, xCoordinate + (double)getWidth() / NDECADES, yEnd);
			add(line);
		}
	}
	
	// method counts vetical coordinate of currentRank, (counts the proportion)
	private double countYCoordinate(int currentRank) {
		double answer = 0;
		// checking if current rank is 0, or not 
		if (currentRank == 0) {
			answer = getHeight() - GRAPH_MARGIN_SIZE;
		} else { 
			// if curr rank isn't 0, we count the vertical coordinate.
			answer = ((double)currentRank / MAX_RANK) * (getHeight() - 2 * GRAPH_MARGIN_SIZE) + GRAPH_MARGIN_SIZE;
		}
		return answer;
	}
	
	// method draws default canvas.
	private void drawDefaultCanvas() {
		int countDecade = 0;
		// here we are creating vertical lines of canvas
		for (double i = 0; i <= getWidth() - (double)(getWidth() / NDECADES); i += (double)(getWidth() / NDECADES)) {
			GLine line = new GLine(i, 0, i, getHeight());
			add(line);
			
			// adding decade labels on the bottom
			GLabel decade = new GLabel(Integer.toString(countDecade * 10 + START_DECADE));
			countDecade++;
			add(decade, i, getHeight());
		}
		
		// here we are creating horizontal lines of canvas
		GLine upperLine = new GLine(0, GRAPH_MARGIN_SIZE, getWidth(), GRAPH_MARGIN_SIZE);
		GLine lowerLine = new GLine(0, getHeight() - GRAPH_MARGIN_SIZE, getWidth(), getHeight() - GRAPH_MARGIN_SIZE);
		add(upperLine);
		add(lowerLine);
	}
	
	
	/* Implementation of the ComponentListener interface */
	public void componentHidden(ComponentEvent e) { }
	public void componentMoved(ComponentEvent e) { }
	public void componentResized(ComponentEvent e) { update(); } // as we resize the program we redraw the graph, so it appears it is changing proportionally.
	public void componentShown(ComponentEvent e) { }
}
