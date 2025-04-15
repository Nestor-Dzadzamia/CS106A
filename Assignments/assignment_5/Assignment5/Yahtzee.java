/*
 * File: Yahtzee.java
 * ------------------
 * This program will eventually play the Yahtzee game.
 */

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

import acm.io.IODialog;
import acm.program.GraphicsProgram;
import acm.util.RandomGenerator;

public class Yahtzee extends GraphicsProgram implements YahtzeeConstants {

	public static void main(String[] args) {
		new Yahtzee().start(args);
	}

	public void run() {
		IODialog dialog = getDialog();
		nPlayers = dialog.readInt("Enter number of players");
		playerNames = new String[nPlayers];
		for (int i = 1; i <= nPlayers; i++) {
			playerNames[i - 1] = dialog.readLine("Enter name for player " + i);
		}
		display = new YahtzeeDisplay(getGCanvas(), playerNames);
		playGame();
	}

	private void playGame() {
		score = new int[N_CATEGORIES][nPlayers]; // Initializing Score matrix to save information about players and categories
		fillScore(score); // Then we fill the score matrix with -1's. Why? we'll see that later.

		// this 2 for loop is the game's life span, (total trials)
		for (int turn = 0; turn < N_SCORING_CATEGORIES; turn++) {

			for (int player = 1; player <= nPlayers; player++) {
				rollFirstDice(player); // every first roll is different from second and third
				rollSecondAndThirdDice(); // after first roll we roll second and third times. both second and third roll is similar.

				selectCategory(player); // after rolling the all dices we should choose category
				updateTotal(player); // after choosing category we Upadte the total score of player
			}
		}
		totalResults(); // after game ends we calculate total results
		calculateWhoIsWinner(); // and at last we identify the winner.
	}
	
	// method fills the score matrix with -1's
	private void fillScore(int[][] score) {
		for (int[] arr : score) {
			Arrays.fill(arr, -1);
		}
	}
	
	// method updates players total score after choosing the category
	private void updateTotal(int player) {
		int answer = 0;
		for (int i = 0; i < N_CATEGORIES; i++) {
			// so as we filled score matrix with -1's. -1 is the dafault value.
			// as default int matrix would be 0's, but another thing is if we chose incorrect
			// category score must be 0. so to identify if score is default 0 or incorrectly
			// chosen 0 we fill matrix with -1's.
			if (score[i][player - 1] != -1) {
				answer += score[i][player - 1];
			}
		}
		display.updateScorecard(TOTAL, player, answer);
	}

	// method compares players scores to reveal the winner.
	private void calculateWhoIsWinner() {
		int winnerIndex = 0;
		int winnerTotal = 0;
		for (int i = 0; i < nPlayers; i++) {
			if (score[TOTAL - 1][i] > winnerTotal) {
				winnerTotal = score[TOTAL - 1][i];
				winnerIndex = i;
			}
		}

		display.printMessage("Congratulations, " + playerNames[winnerIndex]
				+ ", you're the winner with a total score of " + winnerTotal);
	}
	
	// method calculates the total result for all the players after game is over. (includes bonuses).
	private void totalResults() {
		// first for to iterate over the all players
		for (int i = 0; i < nPlayers; i++) {
			int upperResult = 0;
			int bonus = 0;
			int lowerResult = 0;
			int total = 0;
			
			// second for to calculate upper Score.
			for (int j = 0; j < SIXES; j++) {
				upperResult += score[j][i];
			}
			
			score[UPPER_SCORE - 1][i] = upperResult;
			display.updateScorecard(UPPER_SCORE, i + 1, upperResult);
			// checking if upper score is greater than 63, if so we add up Bonus Points.
			if (score[UPPER_SCORE - 1][i] > 63) {
				bonus = 35;
				score[UPPER_BONUS - 1][i] = 35;
				display.updateScorecard(UPPER_BONUS, i + 1, bonus);
			} else {
				bonus = 0;
				score[UPPER_BONUS - 1][i] = 0;
				display.updateScorecard(UPPER_BONUS, i + 1, bonus);
			}

			// Counting lower Result here
			for (int j = UPPER_BONUS; j < CHANCE; j++) {
				lowerResult += score[j][i];
			}

			score[LOWER_SCORE - 1][i] = lowerResult;
			display.updateScorecard(LOWER_SCORE, i + 1, lowerResult);

			// calculating total
			total = score[UPPER_SCORE - 1][i] + score[LOWER_SCORE - 1][i] + score[UPPER_BONUS - 1][i];
			score[TOTAL - 1][i] = total;
			display.updateScorecard(TOTAL, i + 1, total);
		}
	}

	// method rolls the dices for first time
	private void rollFirstDice(int player) {
		// default output message
		display.printMessage(playerNames[player - 1] + "'s turn! Click \"Roll Dice\" button to roll the dice.");
		display.waitForPlayerToClickRoll(player);

		// filling rolledDice array with random nambers from 1 to 6
		for (int i = 0; i < N_DICE; i++) {
			rolledDice[i] = rgen.nextInt(1, 6);
		}
		display.displayDice(rolledDice);
	}

	// method rolls the dices for second and third time
	private void rollSecondAndThirdDice() {
		// for loop repeating 2 times, (second roll and third roll)
		for (int i = 0; i < 2; i++) {
			display.printMessage("Select the Dice you wish to re-roll and click \"Roll Again.\"");
			display.waitForPlayerToSelectDice();

			// we iterate over the rolleddice array and check if we want to reroll any of them.
			// if so we reroll the selected dice.
			for (int j = 0; j < N_DICE; j++) {
				if (display.isDieSelected(j)) {
					rolledDice[j] = rgen.nextInt(1, 6);
				}
			}
			display.displayDice(rolledDice); // display the updated dice.
		}
	}

	// this method selects the category after rolling part is over.
	private void selectCategory(int player) {
		display.printMessage("Select a category for this roll."); // displaying the message
		
		// checking if our selected category is valid. if it is not we are keep searching for it.
		while (true) {
			int category = display.waitForPlayerToSelectCategory();

			if (isValidCategory(player, category)) {
				countCategoryPoints(player, category);
				break;
			}
		}

	}
	
	// method checks if the category we chose if valid.
	private boolean isValidCategory(int player, int category) {
		// category must be filled with -1, should be upper, lower or bonus score.
		if (score[category - 1][player - 1] == -1 && category != UPPER_SCORE && category != UPPER_BONUS
				&& category != LOWER_SCORE && category != TOTAL) {
			return true;
		}
		return false;
	}

	// method gets the player and category indexes and then calculates and writes the new score in score matrix.
	private void countCategoryPoints(int player, int category) {
		int categoryScore = 0; // default category score is 0.
		
		// this if checks if the category is from 1 to 6.
		if (category >= ONES && category <= SIXES) {
			for (int i = 0; i < rolledDice.length; i++) {
				if (rolledDice[i] == category) {
					categoryScore += category;
				}
			}
			// this else if checks three of a kind and four of a kind. both implements the same logic.
		} else if (category == THREE_OF_A_KIND || category == FOUR_OF_A_KIND) {
			if (isSpecialCategory(category)) { // first we check if the category is special.
				for (int i = 0; i < rolledDice.length; i++) {
					categoryScore += rolledDice[i];
				}
			} else {
				categoryScore = 0; // if we chose this category but our dice doesn't reveal this category, then score must be 0.
			}
		} else if (category == FULL_HOUSE) { // full house check, if correct 25 points, if not 0.
			if (isSpecialCategory(category)) {// we check if the category is special.
				categoryScore = 25;
			} else {
				categoryScore = 0;
			}
		} else if (category == SMALL_STRAIGHT) { // small straight check, if correct 30 points, if not 0.
			if (isSpecialCategory(category)) {// we check if the category is special.
				categoryScore = 30;
			} else {
				categoryScore = 0;
			}
		} else if (category == LARGE_STRAIGHT) { // large straight check, if correct 40 points, if not 0.
			if (isSpecialCategory(category)) {// we check if the category is special.
				categoryScore = 40;
			} else {
				categoryScore = 0;
			}
		} else if (category == YAHTZEE) { // yahtzee check, if correct 50 points, if not 0.
			if (isSpecialCategory(category)) {// we check if the category is special.
				categoryScore = 50;
			} else {
				categoryScore = 0;
			}
		} else if (category == CHANCE) { // at last we check the chance category.
			for (int i = 0; i < rolledDice.length; i++) {
				categoryScore += rolledDice[i];
			}
		}

		score[category - 1][player - 1] = categoryScore;
		display.updateScorecard(category, player, categoryScore); // we update the score on display.
	}
	
	// method checks if the category is special.
	private boolean isSpecialCategory(int category) {
		// we create the map to save dice type form 1 to 6 and it's quantity.
		HashMap<Integer, Integer> map = new HashMap<Integer, Integer>();
		map.put(1, 0);
		map.put(2, 0);
		map.put(3, 0);
		map.put(4, 0);
		map.put(5, 0);
		map.put(6, 0);
		// we fill every dice roll with 0's so it wont be null.

		// we fill the map, map(dice_tyep - quantity).
		for (int i = 0; i < rolledDice.length; i++) {
			if (rolledDice[i] == 1) {
				int temp = map.get(rolledDice[i]) + 1;
				map.put(rolledDice[i], temp);
			} else if (rolledDice[i] == 2) {
				int temp = map.get(rolledDice[i]) + 1;
				map.put(rolledDice[i], temp);
			} else if (rolledDice[i] == 3) {
				int temp = map.get(rolledDice[i]) + 1;
				map.put(rolledDice[i], temp);
			} else if (rolledDice[i] == 4) {
				int temp = map.get(rolledDice[i]) + 1;
				map.put(rolledDice[i], temp);
			} else if (rolledDice[i] == 5) {
				int temp = map.get(rolledDice[i]) + 1;
				map.put(rolledDice[i], temp);
			} else if (rolledDice[i] == 6) {
				int temp = map.get(rolledDice[i]) + 1;
				map.put(rolledDice[i], temp);

			}
		}
		
//		println(map.toString());
//		println(Arrays.toString(rolledDice));

		
		// we check if the category is valid for three of a kind
		if (category == THREE_OF_A_KIND) {
			if (map.get(1) >= 3 || map.get(2) >= 3 || map.get(3) >= 3 || map.get(4) >= 3 || map.get(5) >= 3
					|| map.get(6) >= 3) {
				return true;
			}
			
		// we check if the category is valid for four of a kind
		} else if (category == FOUR_OF_A_KIND) {
			if (map.get(1) >= 4 || map.get(2) >= 4 || map.get(3) >= 4 || map.get(4) >= 4 || map.get(5) >= 4
					|| map.get(6) >= 4 ) {
				return true;
			}
			
		// we check if the category is valid for full house
		} else if (category == FULL_HOUSE) {
			if ((map.get(1) == 3 || map.get(2) == 3 || map.get(3) == 3 || map.get(4) == 3 || map.get(5) == 3
					|| map.get(6) == 3)
					&& (map.get(1) == 2 || map.get(2) == 2 || map.get(3) == 2 || map.get(4) == 2 || map.get(5) == 2
							|| map.get(6) == 2)) {
				return true;
			}
			
		// we check if the category is valid for small straight
		} else if (category == SMALL_STRAIGHT) {
			if (map.get(1) >= 1 && map.get(2) >= 1 && map.get(3) >= 1 && map.get(4) >= 1) {
				return true;
			} else if (map.get(2) >= 1 && map.get(3) >= 1 && map.get(4) >= 1 && map.get(5) >= 1) {
				return true;
			} else if (map.get(3) >= 1 && map.get(4) >= 1 && map.get(5) >= 1 && map.get(6) >= 1) {
				return true;
			}
		// we check if the category is valid for large straight	
		} else if (category == LARGE_STRAIGHT) {
			if (map.get(1) == 1 && map.get(2) == 1 && map.get(3) == 1 && map.get(4) == 1 && map.get(5) == 1) {
				return true;
			} else if (map.get(2) == 1 && map.get(3) == 1 && map.get(4) == 1 && map.get(5) == 1 && map.get(6) == 1) {
				return true;
			}
		// we check if the category is valid for yahtzee		
		} else if (category == YAHTZEE) {
			if (map.get(1) == 5 || map.get(2) == 5 || map.get(3) == 5 || map.get(4) == 5 || map.get(5) == 5
					|| map.get(6) == 5) {
				return true;
			}
		// chance category is always valid
		} else if (category == CHANCE) {
			return true;
		}

		return false;
	}

	/* Private instance variables */
	private ArrayList<Integer> usedCategories = new ArrayList<Integer>();
	private int[][] score;
	private int[] rolledDice = new int[N_DICE];
	private int nPlayers;
	private String[] playerNames;
	private YahtzeeDisplay display;
	private RandomGenerator rgen = new RandomGenerator();

}
