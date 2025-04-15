/*
 * File: HangmanCanvas.java
 * ------------------------
 * This file keeps track of the Hangman display.
 */

import java.applet.AudioClip;
import java.awt.Color;
import java.awt.Font;
import java.util.ArrayList;

import acm.graphics.GCanvas;
import acm.graphics.GCompound;
import acm.graphics.GImage;
import acm.graphics.GLabel;
import acm.graphics.GLine;
import acm.graphics.GOval;
import acm.graphics.GPoint;
import acm.util.MediaTools;
import acm.util.RandomGenerator;

public class extensionHangmanCanvas extends GCanvas{
/* Initializing Instance objects */
	private GCompound hangman; // making GCompount so we can attach noose lines together
	private GLabel letters = new GLabel(""); // object to draw all the letters guessed incorrectly.
	private GLabel currentWord = new GLabel("");
	private GPoint drawCoordinate; //instance Object to save drawing coordinates of body parts.  
	private GLabel streakCount = new GLabel("");
	
	// initializing instance body part objects
	private GOval head = null;
	private GLine body = null;
	private GLine leftHand = null;
	private GLine rightHand = null;
	private GLine leftLeg = null;
	private GLine rightLeg = null;
	private GLine leftFoot = null;
	private GLine rightFoot = null;
	
	// initializing win and lose Audio clips
	public AudioClip winClip = MediaTools.loadAudioClip("win.au");
	public AudioClip loseClip = MediaTools.loadAudioClip("lose.au");
	
	private int width;
	private int height;
	
	// initializing Random Generator
	private RandomGenerator rgen = RandomGenerator.getInstance();
	

/** Resets the display so that only the scaffold appears */
	public void reset(int WIDTH_PANNEL, int HEIGHT_PANNEL) {
		width = WIDTH_PANNEL;
		height = HEIGHT_PANNEL;
		removeAll(); // removing all objects as we reset game;
		resetObjects();
		
		letters.setLabel("");
		currentWord.setLabel("");
		letters.setFont(new Font("Arial", Font.PLAIN, 15));
		currentWord.setFont(new Font("Arial", Font.PLAIN, 30));
		hangman = new GCompound();
		drawNoose();
		add(hangman, 0, 0);
		add(currentWord);
		add(letters);
	}
	
	// this method only draws noose
	private void drawNoose() {
		int scaffoldXStart = width / 4 - BEAM_LENGTH;
		int scaffoldYStart = (height - SCAFFOLD_HEIGHT) / 2;
		
		GLine scaffold = new GLine(scaffoldXStart, scaffoldYStart, scaffoldXStart, scaffoldYStart + SCAFFOLD_HEIGHT);
		GLine beam = new GLine(scaffoldXStart, scaffoldYStart, scaffoldXStart + BEAM_LENGTH, scaffoldYStart);
		GLine noose = new GLine(scaffoldXStart + BEAM_LENGTH, scaffoldYStart, 
				scaffoldXStart + BEAM_LENGTH, scaffoldYStart + ROPE_LENGTH);
		
		drawCoordinate = new GPoint(noose.getEndPoint());
		letters.setLocation(scaffold.getEndPoint().getX(), scaffold.getEndPoint().getY() + 100);
		currentWord.setLocation(scaffold.getEndPoint().getX(), scaffold.getEndPoint().getY() + 75);
		
		hangman.add(scaffold);
		hangman.add(beam);
		hangman.add(noose);
	}

/**
 * Updates the word on the screen to correspond to the current
 * state of the game.  The argument string shows what letters have
 * been guessed so far; unguessed letters are indicated by hyphens.
 */
	public void displayWord(String word) {
		currentWord.setLabel(word);
	}

/**
 * Updates the display to correspond to an incorrect guess by the
 * user.  Calling this method causes the next body part to appear
 * on the scaffold and adds the letter to the list of incorrect
 * guesses that appears at the bottom of the window.
 */
	public void noteIncorrectGuess(char letter) {
		String answer = letters.getLabel();
		// we check if the letter is already in out letters list or not.
		if (!answer.contains(Character.toString(letter))) {
			answer += letter; 
		}
		letters.setLabel(answer);
		add(letters);
		remove(streakCount);
		
		// this if statement draws body parts staring with head, ending with feet.
		if (head == null) {
			head = new GOval(2 * HEAD_RADIUS, 2 * HEAD_RADIUS);
			head.setFilled(true);
			head.setFillColor(Color.WHITE);
			add(head, drawCoordinate.getX() - HEAD_RADIUS, drawCoordinate.getY());
			drawCoordinate = new GPoint(head.getX() + HEAD_RADIUS, head.getY() + 2 * HEAD_RADIUS);
		} else if (body == null) {
			body = new GLine(drawCoordinate.getX(), drawCoordinate.getY(),
					drawCoordinate.getX(), drawCoordinate.getY() + BODY_LENGTH);
			add(body);
			drawCoordinate = new GPoint(body.getStartPoint());
		} else if (leftHand == null) {
			leftHand = new GLine(drawCoordinate.getX(), drawCoordinate.getY() + ARM_OFFSET_FROM_HEAD,
					drawCoordinate.getX() - UPPER_ARM_LENGTH, drawCoordinate.getY() + ARM_OFFSET_FROM_HEAD);
			GLine leftLowerHand = new GLine(leftHand.getEndPoint().getX(), leftHand.getEndPoint().getY(),
					leftHand.getEndPoint().getX(), leftHand.getEndPoint().getY() + LOWER_ARM_LENGTH);
			
			drawCoordinate = new GPoint(leftHand.getStartPoint());
			add(leftHand);
			add(leftLowerHand);
		} else if (rightHand == null) {
			rightHand = new GLine(drawCoordinate.getX(), drawCoordinate.getY(),
					drawCoordinate.getX() + UPPER_ARM_LENGTH, drawCoordinate.getY());
			GLine rightLowerHand = new GLine(rightHand.getEndPoint().getX(), rightHand.getEndPoint().getY(),
					rightHand.getEndPoint().getX(), rightHand.getEndPoint().getY() + LOWER_ARM_LENGTH);
			
			
			drawCoordinate = new GPoint(body.getEndPoint());
			add(rightHand);
			add(rightLowerHand);
		} else if (leftLeg == null) {
			GLine hip = new GLine(drawCoordinate.getX(), drawCoordinate.getY(),
					drawCoordinate.getX() - HIP_WIDTH / 2, drawCoordinate.getY());
			leftLeg = new GLine(hip.getEndPoint().getX(), hip.getEndPoint().getY(),
					hip.getEndPoint().getX(), hip.getEndPoint().getY() + LEG_LENGTH);
			
			add(hip);
			add(leftLeg);
		} else if (rightLeg == null) {
			GLine hip = new GLine(drawCoordinate.getX(), drawCoordinate.getY(),
					drawCoordinate.getX() + HIP_WIDTH / 2, drawCoordinate.getY());
			rightLeg = new GLine(hip.getEndPoint().getX(), hip.getEndPoint().getY(),
					hip.getEndPoint().getX(), hip.getEndPoint().getY() + LEG_LENGTH);
			
			drawCoordinate = new GPoint(leftLeg.getEndPoint());
			add(hip);
			add(rightLeg);
		} else if (leftFoot == null) {
			leftFoot = new GLine(drawCoordinate.getX(), drawCoordinate.getY(),
					drawCoordinate.getX() - FOOT_LENGTH, drawCoordinate.getY());
			
			drawCoordinate = new GPoint(rightLeg.getEndPoint());
			add(leftFoot);
		} else if (rightFoot == null) {
			rightFoot = new GLine(drawCoordinate.getX(), drawCoordinate.getY(),
					drawCoordinate.getX() + FOOT_LENGTH, drawCoordinate.getY());
			
			add(rightFoot);
		}
	}
	// this method resets all the body part objects to null
	private void resetObjects() {
		head = null;
		body = null;
		leftHand = null;
		rightHand = null;
		leftLeg = null;
		rightLeg = null;
		leftFoot = null;
		rightFoot = null;
	}
	// this method outputs the lose sign and plays the song.
	public void lose() {
		GLabel lose = new GLabel("GAME OVER - YOU DIED");
		lose.setFont(new Font("Gabriola", Font.BOLD, 35));
		lose.setColor(Color.GRAY);
		add(lose, getWidth() / 2 - lose.getWidth() / 2, getHeight() / 2 + lose.getHeight() / 2);
		loseClip.play();
	}
	// this method outputs the win sign and plays the song.
	public void win() {
		GLabel lose = new GLabel("GAME OVER - YOU SURVIVED");
		lose.setFont(new Font("Gabriola", Font.BOLD, 35));
		lose.setColor(Color.GRAY);
		add(lose, getWidth() / 2 - lose.getWidth() / 2, getHeight() / 2 + lose.getHeight() / 2);
		winClip.play();
	}
	// this method draws the current word guess streak.
	public void drawStreak(int streak) {
		streakCount.setLabel("Current Streak : " + streak);
		streakCount.setFont(new Font("Gabriola", Font.BOLD, 25));
		streakCount.setColor(Color.BLACK);
		add(streakCount, getWidth() / 2 - streakCount.getWidth() / 2, head.getY() - 50);
	}
	
/* Constants for the simple version of the picture (in pixels) */
	private static final int SCAFFOLD_HEIGHT = 360;
	private static final int BEAM_LENGTH = 144;
	private static final int ROPE_LENGTH = 18;
	private static final int HEAD_RADIUS = 36;
	private static final int BODY_LENGTH = 144;
	private static final int ARM_OFFSET_FROM_HEAD = 28;
	private static final int UPPER_ARM_LENGTH = 72;
	private static final int LOWER_ARM_LENGTH = 44;
	private static final int HIP_WIDTH = 36;
	private static final int LEG_LENGTH = 108;
	private static final int FOOT_LENGTH = 28;

}
