/*
 * File: NameSurfer.java
 * ---------------------
 * When it is finished, this program will implements the viewer for
 * the baby-name database described in the assignment handout.
 */

import java.awt.event.ActionEvent;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JTextField;

import acm.program.Program;

public class NameSurfer extends Program implements NameSurferConstants {
/* initializing instance variables */
	private JButton clear;
	private JButton graph;
	private JLabel name;
	private JTextField field;
	private NameSurferDataBase data = new NameSurferDataBase("names-data.txt");
	private NameSurferGraph canvas;
	

/* Method: init() */
/**
 * This method has the responsibility for reading in the data base
 * and initializing the interactors at the bottom of the window.
 */
	public void init() {
		// Creating JLabel and 2 Buttons for Program 
	    name = new JLabel("Name");
	    graph = new JButton("Graph");
	    clear = new JButton("Clear");
	    
	    field = new JTextField(20); // estimating field's lenght to 20
	    field.addActionListener(this); // adding actionListener to filed to it reacts when we type enter
	    canvas = new NameSurferGraph();
	    
	    // adding interactors
	    add(name, SOUTH);
	    add(field, SOUTH);
	    add(graph, SOUTH);
	    add(clear, SOUTH);
	    add(canvas, CENTER); // adding canvas
	    
	    addActionListeners();
	}

/* Method: actionPerformed(e) */
/**
 * This class is responsible for detecting when the buttons are
 * clicked, so you will have to define a method to respond to
 * button actions.
 */
	public void actionPerformed(ActionEvent e) {
		// checking if action source is eather graph or field, if it is we do same action for both.
		if (e.getSource() == graph || e.getSource() == field) {
			println("Graph: " + '"' + field.getText() + '"');
			if (data.findEntry(field.getText()) != null) { // first checking if Entered name is in Database or not.
				println("Graph: " + data.findEntry(field.getText()).toString());
				NameSurferEntry entry = data.findEntry(field.getText()); // for Following name input we create Entry class
				canvas.addEntry(entry); // then we add mentioned name's information on canvas.
				canvas.update();

			} else {
				println("This name is not in DataBase"); // if name is not in database, we output the error text.
			}
			field.setText(""); // after name is entered we refresh the textfield.
		} else if (e.getSource() == clear) { // checking if we clicked clear or not
			println("Clear");
			field.setText(""); // clearing textField.
			canvas.clear(); // Clearing the program.
		}
	}
}
