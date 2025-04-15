import acm.program.ConsoleProgram;
import acm.util.RandomGenerator;

/*
 * File: Hangman.java
 * ------------------
 * This program will eventually play the Hangman game from
 * Assignment #4.
 */



public class Hangman extends ConsoleProgram {
	/** Initializing variable for totall attempts */
	private static final int NTURNS = 8;
	
	public static final int WIDTH_PANNEL = 900;
	public static final int HEIGHT_PANNEL = 900;
	/** initializing RandomGenerator */
	RandomGenerator rgen = RandomGenerator.getInstance();
	
	/** initializing adjustable variable to count totall attempts that are left */
	private int turnsLeft = NTURNS;

	/** initializing insantce objects and variables */
	private HangmanCanvas canvas;
	private HangmanLexicon lexicon;
	private String wordToGuess ;
	private String currentWord;
	private String lettersGuessed = "";
	
	// method initializes the program.
	public void init() {
		canvas = new HangmanCanvas(); // initializing canvas class
		canvas.reset(WIDTH_PANNEL, HEIGHT_PANNEL);
		add(canvas);
	}
	
	public void run() {
		setSize(WIDTH_PANNEL, HEIGHT_PANNEL);
		lexicon = new HangmanLexicon(); // initializing lexicon class
		wordToGuess = lexicon.getWord(rgen.nextInt(0, lexicon.getWordCount() - 1)); // generating random word from arraylist


		playTheGame();
	}
	
	// method outputs the ConsoleProgram
	private void playTheGame() {
		println("Welcome To Hungman!");
		currentWord = "";
		
		// in console word to guess must be filled with -'s.
		for (int i = 0; i < wordToGuess.length(); i++) {
			currentWord += "-";
		}
		
		while (turnsLeft > 0 && !wordGuessed()) {
			outputText();
		}
	}
	
	// checks if we guessed word or not.
	private boolean wordGuessed() {
		return currentWord.equals(wordToGuess);
	}
	
	// this method works with both console and graphics programs
	private void outputText() {
		// default game outputs.
		println("The word now looks like this:" + currentWord);
		println("You have " + turnsLeft + " guesses left.");
		
		// initializing our guess characher
		char yourGuess = readLine("Your guess: ").toUpperCase().charAt(0);
		
		// while loop checks if out input is valid or not
		while (yourGuess < 'A' || yourGuess > 'Z') {
			println("Invalid input. Waiting for new One.");
			yourGuess = readLine("Reenter your guess: ").toUpperCase().charAt(0);
		}
		
		String temp = currentWord; // initializing temp to remember currentWord 
		// this for loop updates the word we are guessing.
		for (int i = 0; i < wordToGuess.length(); i++) {
			char currChar = wordToGuess.charAt(i);
			
			if (yourGuess == currChar) {
				currentWord = fillTheGuessedLetter(currChar, i, currentWord);
			}
		}
		
		if (lettersGuessed.contains(Character.toString(yourGuess))) {
			println("You have already Guessed this letter Correctly.");
		} else if (temp.equals(currentWord)) { // if our currentWord didnt update this means our guess was incorrect.
			println("There is no " + yourGuess + "'s in the word");
			turnsLeft--;
			canvas.noteIncorrectGuess(yourGuess);
			// this if checks if we lost game
			if (turnsLeft == 0) {
				println("The word was: " + wordToGuess);
				println("You lose.");
				// this String asks the players if we wishes to continue playing, if so we continue game.
				String continuePlaying = readLine("Do you want to continue playing ? : ");
				if (continuePlaying.equals("YES")) {
					restartGame();
				}
			}
		} else {
			lettersGuessed += yourGuess; // if our currentWord change we guessed the letter correctly
			println("Your Guess is correct. ");
			// checks if we win game or not
			if (currentWord.equals(wordToGuess)) {
				println("You guessed the word: " + wordToGuess);
				println("you Win.");
				// this String asks the players if we wishes to continue playing, if so we continue game.
				String continuePlaying = readLine("Do you want to continue playing ? : ");
				if (continuePlaying.equals("YES")) {
					restartGame();
				}
			}
		}
		canvas.displayWord(currentWord);
	}
	// method restarts game and resets canvas, word and turns.
	private void restartGame() {
		turnsLeft = NTURNS;
		canvas.reset(900, 900);
		wordToGuess = lexicon.getWord(rgen.nextInt(0, lexicon.getWordCount() - 1));
		lettersGuessed = "";
		playTheGame();
	}
	// method updates the currentWord, (-) is changed with char.
	private String fillTheGuessedLetter(char currChar, int i, String currentWord) {
		return currentWord.substring(0, i) + currChar + currentWord.substring(i + 1);
	}
}
