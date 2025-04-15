
/*
 * File: Breakout.java
 * -------------------
 * Name: Nestor Dzadzamia	
 * Section Leader: Giorgi Kuchava
 * 
 * This file will eventually implement the game of Breakout.
 */
import acm.graphics.*;
import acm.program.*;
import acm.util.*;

import java.applet.*;
import java.awt.*;
import java.awt.event.*;

public class Extension extends GraphicsProgram {
	/** Initializing RandomGenerator */
	private RandomGenerator rgen = RandomGenerator.getInstance();

	/** Width and height of application window in pixels */
	public static final int APPLICATION_WIDTH = 400;
	public static final int APPLICATION_HEIGHT = 600;

	/** Dimensions of game board (usually the same) */
	private static final int WIDTH = APPLICATION_WIDTH;
	private static final int HEIGHT = APPLICATION_HEIGHT;

	/** Dimensions of the paddle */
	private static final int PADDLE_WIDTH = 60;
	private static final int PADDLE_HEIGHT = 10;

	/** Offset of the paddle up from the bottom */
	private static final int PADDLE_Y_OFFSET = 30;

	/** Number of bricks per row */
	private static final int NBRICKS_PER_ROW = 10;

	/** Number of rows of bricks */
	private static final int NBRICK_ROWS = 10;

	/** Separation between bricks */
	private static final int BRICK_SEP = 4;

	/** Width of a brick */
	private static final int BRICK_WIDTH = (WIDTH - (NBRICKS_PER_ROW - 1) * BRICK_SEP) / NBRICKS_PER_ROW;

	/** Height of a brick */
	private static final int BRICK_HEIGHT = 8;

	/** Radius of the ball in pixels */
	private static final int BALL_RADIUS = 10;

	/** Offset of the top brick row from the top */
	private static final int BRICK_Y_OFFSET = 70;

	/** Number of turns */
	private static final int NTURNS = 3;

	/** pause time of Breakout */
	private static final int DELAY = 8;

	/** Initializing Instance Objects */
	private GRect brick;
	private GOval ball;
	private GRoundRect paddle;
	private GObject topLeft;
	private GObject topRight;
	private GObject bottomLeft;
	private GObject bottomRight;
	private GObject collider;
	private GLabel points;
	AudioClip bounceClip = MediaTools.loadAudioClip("bounce.au");

	/** Initializing Ball's Coordinate speeds */
	private double vy = 3;
	private double vx = rgen.nextDouble(1, 3);

	/** making NTURNS adjustable */
	private int gameNum = NTURNS;

	/** Initializing Brick counter */
	private int brickNum = NBRICKS_PER_ROW * NBRICK_ROWS;

	/** Initializing variable to count points */
	private int countPoints = 0;
	
	/** Initializing variable to count paddle touches */
	
	private int countTouches = 0;

	/* Method : init() - Initializes the Game */
	public void init() {
		createBricks();
		createPaddle();
		createBall();
		createScoreboard();
		addMouseListeners();
	}

	/* Method: run() */
	/** Runs the Breakout program. */
	public void run() {
		playTheGame();
	}

	private void playTheGame() {
		// here We randomize if vx will be positive or negative
		if (rgen.nextBoolean(0.5))
			vx *= -1;

		while (true) {
			if (checkIfLost()) break; // if we lost the game must top
			else if (checkIfWon()) break; // same here, if we win we should stop
			checkWalls();

			collider = getCollidingObject();
			// this if statement check's if ball's up or down side is getting //
			// hit
			if (collider != null && collider != paddle && bottomRight != topRight && bottomLeft != topLeft
					&& collider != points) {
				brickNum--;
				remove(collider);
				vy *= -1;
				bounceClip.play(); // plays the sound

				countPoints += 100; // we add brick destruction points
				// we change label as points increase
				points.setLabel("POINTS : " + Integer.toString((int) countPoints));
			}

			// this If statement fixes the bug (when ball moves into the paddle
			// and gets stuck)
			if (collider == paddle) {
				vy = -1 * Math.abs(vy);
				
				countTouches++; // ball touched paddle
				// every 7th touch we increase horizontal speed by 50%
				if (countTouches % 7 == 0) vx *= 1.5;
				bounceClip.play(); // plays the sound
			}

			// this if statement checks if the ball is hitting brick's left or
			// right side.
			// if that's the case we should change vx. (when left and right
			// corners are touching same object)
			if ((collider != paddle && collider == bottomRight && collider == topRight && collider != null
					&& collider != points)
					|| (collider == bottomLeft && collider == topLeft && collider != null && collider != paddle
							&& collider != points)) {
				vx *= -1;
				remove(collider);
				brickNum--;
				bounceClip.play(); // plays the sound

				countPoints += 100; // we add brick destruction points
				points.setLabel("POINTS : " + Integer.toString((int) countPoints));
			}

			ball.move(vx, vy);
			pause(DELAY);
		}
	}

	/* Method Check's if the game is won, returns True or False */
	private boolean checkIfWon() {
		if (brickNum == 0) {
			GLabel win = new GLabel("YOU WON");
			win.setFont(new Font("Arial", Font.BOLD, 30));
			add(win, (WIDTH - win.getWidth()) / 2, (getHeight() + win.getHeight()) / 2);
			remove(ball);

			// we label out the text about final score after game ends
			points.setLabel("YOUR FINAL SCORE : " + Integer.toString((int) countPoints));
			points.setLocation((WIDTH - points.getWidth()) / 2, win.getY() + 50);

			return true;
		}
		return false;
	}

	/* Method Check's if the game is Lost, returns True or False */
	private boolean checkIfLost() {
		if (ball.getY() >= getHeight() - 2 * BALL_RADIUS) {
			gameNum--; // decreasing Game trials left
			remove(ball);
			if (gameNum != 0) {
				add(ball, WIDTH / 2 - BALL_RADIUS, getHeight() / 2 - BALL_RADIUS);
			} else {
				GLabel lost = new GLabel("YOU LOST");
				lost.setFont(new Font("Arial", Font.BOLD, 30));
				add(lost, (WIDTH - lost.getWidth()) / 2, (getHeight() + lost.getHeight()) / 2);

				// we label out the text about final score after game ends
				points.setLabel("YOUR FINAL SCORE : " + Integer.toString((int) countPoints));
				points.setLocation((WIDTH - points.getWidth()) / 2, lost.getY() + 50);
				return true;
			}
		}
		return false;
	}

	private GObject getCollidingObject() {
		/*
		 * in this IF statements we save Objects that each of this 4 points will
		 * reach. this is helpful to check if 2 of this corners (topLeft,
		 * bottomLeft and top Right, bottom Right) are touching anything at the
		 * same time. line 157.
		 */
		if (getElementAt(ball.getLocation()) != null) {
			topLeft = getElementAt(ball.getLocation());
		}
		if (getElementAt(ball.getX() + 2 * BALL_RADIUS, ball.getY()) != null) {
			topRight = getElementAt(ball.getX() + 2 * BALL_RADIUS, ball.getY());
		}
		if (getElementAt(ball.getX(), ball.getY() + 2 * BALL_RADIUS) != null) {
			bottomLeft = getElementAt(ball.getX(), ball.getY() + 2 * BALL_RADIUS);
		}
		if (getElementAt(ball.getX() + 2 * BALL_RADIUS, ball.getY() + 2 * BALL_RADIUS) != null) {
			bottomRight = getElementAt(ball.getX() + 2 * BALL_RADIUS, ball.getY() + 2 * BALL_RADIUS);
		}

		// here we return colliding object
		if (getElementAt(ball.getLocation()) != null)
			return topLeft;
		else if (getElementAt(ball.getX() + 2 * BALL_RADIUS, ball.getY()) != null)
			return topRight;
		else if (getElementAt(ball.getX(), ball.getY() + 2 * BALL_RADIUS) != null)
			return bottomLeft;
		else if (getElementAt(ball.getX() + 2 * BALL_RADIUS, ball.getY() + 2 * BALL_RADIUS) != null)
			return bottomRight;

		return null;
	}

	/* Method checks the walls and bounces off it */
	private void checkWalls() {
		double diff;
		/*
		 * this if statement checks if ball went outside the canvas and then
		 * correct it's location since speed isn't one so it might never exactly
		 * reach 0 or Canvas width.
		 */

		if (ball.getX() < 0) {
			diff = -ball.getX();
			ball.move(2 * diff, 0);
			vx *= -1; // change vx to bounce of left and right wall
		} else if (ball.getX() > WIDTH - 2 * BALL_RADIUS) {
			diff = WIDTH - ball.getX() - 2 * BALL_RADIUS;
			ball.move(2 * diff, 0);
			vx *= -1;
		}

		/*
		 * this if statement correct's ball's y coordinate, Ball.getY() >
		 * getHeight() - 2 * BALL_RADIUS; isn't needed, since in that case game
		 * or round will end
		 */
		if (ball.getY() < 0) {
			diff = -ball.getY();
			ball.move(0, 2 * diff);
			vy *= -1; // change vy to bounce of top wall
		}
	}

	/* Method Creates ball on canvas */
	private void createBall() {
		ball = new GOval(2 * BALL_RADIUS, 2 * BALL_RADIUS);
		ball.setFilled(true);
		add(ball, WIDTH / 2 - BALL_RADIUS, getHeight() / 2 - BALL_RADIUS);

	}
	
	// this method creates scoreboard
		private void createScoreboard() {
			points = new GLabel("POINTS : " + Integer.toString((int) countPoints));
			points.setFont(new Font("Arial", Font.BOLD, 20));
			add(points, 0, points.getHeight());
		}

	/* Method Creates paddle on canvas */
	private void createPaddle() {
		paddle = new GRoundRect(PADDLE_WIDTH, PADDLE_HEIGHT);
		paddle.setFilled(true);
		add(paddle, (WIDTH - PADDLE_WIDTH) / 2, getHeight() - PADDLE_Y_OFFSET - PADDLE_HEIGHT);
	}

	/*
	 * Method Moves the mouse only in Canvas bounds, (mouse centered to Paddle)
	 */
	@Override
	public void mouseMoved(MouseEvent e) {
		if (e.getX() >= PADDLE_WIDTH / 2 && e.getX() <= WIDTH - PADDLE_WIDTH / 2) {
			paddle.setLocation(e.getX() - PADDLE_WIDTH / 2, getHeight() - PADDLE_Y_OFFSET - PADDLE_HEIGHT);
		}
	}

	/* Method Creates bricks on canvas */
	private void createBricks() {
		// counting x and y star and end coordinates so it ends up in center.
		int yStart = BRICK_Y_OFFSET;
		int yEnd = yStart + (NBRICK_ROWS - 1) * (BRICK_HEIGHT + BRICK_SEP);
		int xStart = (WIDTH - (NBRICKS_PER_ROW * BRICK_WIDTH) - (NBRICKS_PER_ROW - 1) * BRICK_SEP) / 2;
		int xEnd = WIDTH - xStart - BRICK_WIDTH;

		int count = 1; // variable to itarate over rows to color bricks
		for (int i = yStart; i <= yEnd; i += BRICK_HEIGHT + BRICK_SEP, count++) {
			for (int j = xStart; j <= xEnd; j += BRICK_WIDTH + BRICK_SEP) {
				brick = new GRect(BRICK_WIDTH, BRICK_HEIGHT);
				brick.setFilled(true);
				if (count <= 2)
					brick.setColor(Color.RED);
				else if (count > 2 && count <= 4)
					brick.setColor(Color.ORANGE);
				else if (count > 4 && count <= 6)
					brick.setColor(Color.YELLOW);
				else if (count > 6 && count <= 8)
					brick.setColor(Color.GREEN);
				else if (count > 8 && count <= 10)
					brick.setColor(Color.CYAN);
				add(brick, j, i);
			}
		}
	}

}
