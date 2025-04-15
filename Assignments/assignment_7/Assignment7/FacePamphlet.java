/* 
 * File: FacePamphlet.java
 * -----------------------
 * When it is finished, this program will implement a basic social network
 * management system.
 */

import java.awt.event.ActionEvent;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JTextField;

import acm.graphics.GImage;
import acm.program.Program;
import acm.util.ErrorException;

public class FacePamphlet extends Program implements FacePamphletConstants {

	// Initializing Instance Interactors
	private JTextField changeStatusField;
	private JTextField changePictureField;
	private JTextField addFriendField;
	private JTextField nameField;

	private JLabel name;
	private GImage image;
	private JButton changeStatus;
	private JButton changePicture;
	private JButton addFriend;
	private JButton add;
	private JButton delete;
	private JButton lookup;
	private FacePamphletDatabase data;
	private FacePamphletProfile currProfile;
	private FacePamphletCanvas canvas;
	

	/**
	 * This method has the responsibility for initializing the interactors in the
	 * application, and taking care of any other initialization that needs to be
	 * performed.
	 */
	public void init() {
		// Creating Top side Interactors
		name = new JLabel("Name");
		nameField = new JTextField(TEXT_FIELD_SIZE);
		add = new JButton("Add");
		delete = new JButton("Delete");
		lookup = new JButton("Lookup");

		add(name, NORTH);
		add(nameField, NORTH);
		add(add, NORTH);
		add(delete, NORTH);
		add(lookup, NORTH);

		// Creating Left side Interactors
		changeStatusField = new JTextField(TEXT_FIELD_SIZE);
		changeStatusField.addActionListener(this);
		changeStatus = new JButton("Change Status");
		changePictureField = new JTextField(TEXT_FIELD_SIZE);
		changePictureField.addActionListener(this);
		changePicture = new JButton("Change Picture");
		addFriendField = new JTextField(TEXT_FIELD_SIZE);
		addFriendField.addActionListener(this);
		addFriend = new JButton("Add Friend");

		add(changeStatusField, WEST);
		add(changeStatus, WEST);
		add(new JLabel(EMPTY_LABEL_TEXT), WEST);
		add(changePictureField, WEST);
		add(changePicture, WEST);
		add(new JLabel(EMPTY_LABEL_TEXT), WEST);
		add(addFriendField, WEST);
		add(addFriend, WEST);

		data = new FacePamphletDatabase(); // Initializing FacePamphletDatabe class
		canvas = new FacePamphletCanvas();  // Initializing FacePamphletCanvas class
		add(canvas);

		addActionListeners(); // adding action listeners to activate buttons
	}
	/**
	 * This class is responsible for detecting when the buttons are clicked or
	 * interactors are used, so you will have to add code to respond to these
	 * actions.
	 */
	public void actionPerformed(ActionEvent e) {
		// checking if add button was pressed, + checking if field is clear
		if (e.getSource() == add && !nameField.getText().equals("")) {
			if (data.containsProfile(nameField.getText()) == false) { // checking if Typed profile already exists in data or not
				currProfile = new FacePamphletProfile(nameField.getText()); // if profile is new, current profile becomes this one
				data.addProfile(currProfile);
				canvas.displayProfile(currProfile); // displaying and showing relevant message for profile
				canvas.showMessage("New profile created");
			} else {
				// if typed name is already is data we display appropriate text, displays profile that is in data base.
				currProfile = data.getProfile(nameField.getText());
				canvas.displayProfile(currProfile);
				canvas.showMessage("A profile with the name " + currProfile.getName() + " already exists");
			}
		} else if (e.getSource() == delete) { // checking if pressed button is delete
			if (data.containsProfile(nameField.getText())) { // checking if typed name exists in data, if so we remove it
				data.deleteProfile(nameField.getText());
				canvas.clearCanvas();
				canvas.showMessage("Profile of " + nameField.getText() + " deleted.");
				currProfile = null; // current profile becomes null
			} else {
				canvas.clearCanvas(); // if typed name does't exist in our database we output appropriate text.
				canvas.showMessage("A profile with the name " + nameField.getText() + " does not exist");
			}
		} else if (e.getSource() == lookup) { // checking if pressed lookup button
			if (data.containsProfile(nameField.getText())) { // checking if profile exists in data or not.
				currProfile = data.getProfile(nameField.getText()); // if it exist, current profile changes into that. + we display message.
				canvas.displayProfile(currProfile);
				canvas.showMessage("Displaying " + currProfile.getName());
			} else { // condition if profile does't exist in our data.
				currProfile = null; // current profile becomes null, + we display message
				canvas.clearCanvas();
				canvas.showMessage("A profile with the name " + nameField.getText() + " does not exist");
			}

		} else if (e.getSource() == changeStatus || e.getSource() == changeStatusField) { // checking if we pressed change status button or enter.
			if (currProfile != null) { // checking if current profile is null or not
				if (!changeStatusField.getText().equals("")) { // checking if field is empty, if not so we update current profile and display it.
					currProfile.setStatus(changeStatusField.getText());
					canvas.displayProfile(currProfile);
					canvas.showMessage("Status updated to " + changeStatusField.getText()); // displaying message
				}
			} else {
				canvas.showMessage("Please select a profile to change status"); // if current profile is null we display appropriate message on canvas.
			}
		} else if (e.getSource() == changePicture || e.getSource() == changePictureField) { // checking if change picture button or enter button is pressed.
			if (currProfile != null) { // firstly checking if current profile is null
				try { // using try catch, so if we enter invalid picture name, error occurs.
					if (changePictureField.getText().equals("")) throw new ErrorException(""); // if field is empty we thorw error exception. (by default it weirdly stil updates picture).
					currProfile.setImage(new GImage(changePictureField.getText())); // creating image and displaying it 
					canvas.displayProfile(currProfile); // displayin updated profile
					canvas.showMessage("Picture Updated");

				} catch (ErrorException exception) { // if error appears, (picture name might be invalid), we throw exception.
					canvas.showMessage("Unable to open image file: " + changePictureField.getText());
				}
			} else { // if picture is null we clear canvas and display appropriate message.
				canvas.clearCanvas();
				canvas.showMessage("Please select a profile to change picture");
			}
		// checking if addFriend button or enter button is pressed, + checking if field is empty.
		} else if ((e.getSource() == addFriend || e.getSource() == addFriendField) && !addFriendField.getText().equals("")) {
			// checkinf if current profile and friend profile exists, if so we add eachother in their friendLists, + we display changed currentProfile with message
			if (currProfile != null && data.getProfile(addFriendField.getText()) != null
					&& currProfile.addFriend(addFriendField.getText()) && !currProfile.getName().equals(data.getProfile(addFriendField.getText()).getName())) {
				currProfile.addFriend(addFriendField.getText());
				data.getProfile(addFriendField.getText()).addFriend(currProfile.getName());
				canvas.displayProfile(currProfile);
				canvas.showMessage(addFriendField.getText() + " added as a friend");
			} else if (currProfile == null) { // if current profile is null we output appropriate message.
				canvas.showMessage("Please select a profile to add friend");
			} else if (data.getProfile(addFriendField.getText()) == null) { // if friend's profile does't exist we display that too.
				canvas.showMessage(addFriendField.getText() + " does not exist");
			} else if (currProfile.addFriend(addFriendField.getText()) == true) { // checking if current profile already has relationship with entered person.
				canvas.showMessage(currProfile.getName() + " already has "
						+ data.getProfile(addFriendField.getText()).getName() + " as a friend");
			} else if (currProfile != null && currProfile.getName().equals(addFriendField.getText())) { // checkin if current profile and friend's profile are the same (we cant add ourself as friend)
				canvas.showMessage("You can not add yourself as a friend");
			}
		}
	}

}
