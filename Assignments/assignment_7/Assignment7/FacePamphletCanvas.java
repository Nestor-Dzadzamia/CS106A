/*
 * File: FacePamphletCanvas.java
 * -----------------------------
 * This class represents the canvas on which the profiles in the social
 * network are displayed.  NOTE: This class does NOT need to update the
 * display when the window is resized.
 */


import acm.graphics.*;
import java.awt.*;
import java.util.*;

public class FacePamphletCanvas extends GCanvas 
					implements FacePamphletConstants {
	
	private GLabel message;
	
	/** 
	 * Constructor
	 * This method takes care of any initialization needed for 
	 * the display
	 */
	public FacePamphletCanvas() {
		// initializing message Object  (so when show Message method is called
		// we dont create new one, we just simply change label of it)
		message = new GLabel(""); 
	}

	
	/** 
	 * This method displays a message string near the bottom of the 
	 * canvas.  Every time this method is called, the previously 
	 * displayed message (if any) is replaced by the new message text 
	 * passed in.
	 */
	public void showMessage(String msg) {
		message.setLabel(msg);
		message.setFont(MESSAGE_FONT);
		add(message, getWidth() / 2 - message.getWidth() / 2, getHeight() - BOTTOM_MESSAGE_MARGIN);
	}
	
	
	/** 
	 * This method displays the given profile on the canvas.  The 
	 * canvas is first cleared of all existing items (including 
	 * messages displayed near the bottom of the screen) and then the 
	 * given profile is displayed.  The profile display includes the 
	 * name of the user from the profile, the corresponding image 
	 * (or an indication that an image does not exist), the status of
	 * the user, and a list of the user's friends in the social network.
	 */
	public void displayProfile(FacePamphletProfile profile) {
		removeAll();
		
		// displaying picture
		if (profile.getImage() != null) {
			GImage picture = profile.getImage();
			picture.setSize(IMAGE_WIDTH, IMAGE_HEIGHT);
			add(picture, LEFT_MARGIN, TOP_MARGIN + IMAGE_MARGIN);
		} else {
			GRect rect = new GRect(IMAGE_WIDTH, IMAGE_HEIGHT);
			rect.setFilled(true);
			rect.setFillColor(Color.white);
			add(rect, LEFT_MARGIN, TOP_MARGIN + IMAGE_MARGIN);
			
			GLabel label = new GLabel("No Image");
			label.setFont(PROFILE_IMAGE_FONT);
			add(label, LEFT_MARGIN + rect.getWidth() / 2 - label.getWidth() / 2, TOP_MARGIN + IMAGE_MARGIN + rect.getHeight() / 2 + label.getHeight() / 2);
		}
		
		// displaying name 
		GLabel name = new GLabel(profile.getName());
		name.setFont(PROFILE_NAME_FONT);
		name.setColor(Color.blue);
		add(name, LEFT_MARGIN, TOP_MARGIN + name.getAscent() / 2);
		
		// displaying friend list
		GLabel label = new GLabel("Friends:");
		label.setFont(PROFILE_FRIEND_LABEL_FONT);
		add(label, getWidth() / 2, TOP_MARGIN + IMAGE_MARGIN);
		
		Iterator<String> it = profile.getFriends();
		double x = getWidth() / 2;
		double y = label.getY() + label.getHeight() / 2;
		while (it.hasNext()) {
			GLabel currFriend = new GLabel(it.next());
			add(currFriend, x, y);
			y += label.getHeight() / 2;
		}
		
		// displaying status
		if (profile.getStatus().equals("")) {
			GLabel status = new GLabel("No current Status");
			status.setFont(PROFILE_STATUS_FONT);
			add(status, LEFT_MARGIN, TOP_MARGIN + IMAGE_MARGIN + IMAGE_HEIGHT + status.getHeight() + STATUS_MARGIN);
		} else {
			GLabel status = new GLabel(profile.getName() + " is " + profile.getStatus());
			status.setFont(PROFILE_STATUS_FONT);
			add(status, LEFT_MARGIN, TOP_MARGIN + IMAGE_MARGIN + IMAGE_HEIGHT + status.getHeight() + STATUS_MARGIN);
		}
		
	}
	
	// Simple method just clears canvas, it's needed when we dont have current profile to display it.
	// we simply erase everything and then output message.
	public void clearCanvas() {
		removeAll();
	}

	
}
