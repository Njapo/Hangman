
/*
 * File: Hangman.java
 * ------------------
 * This program will eventually play the Hangman game from
 * Assignment #4.
 */

import acm.graphics.*;
import acm.program.*;
import acm.util.*;
import java.util.ArrayList;

import java.awt.*;

public class Hangman extends ConsoleProgram {

	private RandomGenerator rgen = RandomGenerator.getInstance();
	private String word;
	private final int MISTAKESLIMIT = 8;
	private ArrayList<Character> wordDashs;
	private HangmanCanvas canvas;

	public void init() {
		canvas = new HangmanCanvas();
		add(canvas);
	}

	public void run() {
		while (true) {
			HangmanLexicon stub = new HangmanLexicon();
			word = stub.getWord(rgen.nextInt(0, stub.getWordCount()));
			println();
			gameIntro();
			playGame();
		}
	}

	/*
	 * This is gameIntro func, which will update data it chooses new word and
	 * clean old hangman and update's canvas also.
	 */
	private void gameIntro() {

		canvas.reset();
		wordDashs = new ArrayList<Character>();
		for (int i = 0; i < word.length(); i++) {
			wordDashs.add('-');
		}

		println("Welcome to Hangman");
		println("The word looks like this: " + arrayListToString(wordDashs));
		println("You Have " + MISTAKESLIMIT + " guesses left.");
		canvas.displayWord(arrayListToString(wordDashs));
	}

	// This func coverts arratList to string, it removes all the [,],','
	// elements
	private String arrayListToString(ArrayList<Character> wordDashs) {
		String wordGuess = wordDashs.toString();
		wordGuess = wordGuess.replace("" + '[', "");
		wordGuess = wordGuess.replace("" + ']', "");
		wordGuess = wordGuess.replaceAll("" + ',', "");

		return wordGuess;
	}

	// This func make consumer play until he/she wins or loses the game
	private void playGame() {
		String usedCharacters = "";
		int mistakeLimit = MISTAKESLIMIT;

		while (true) {
			if (mistakeLimit <= 0) {
				println("You are completly hung");
				println("The word was "+word);
				println("You Lose");
				break;
			} else if (!wordDashs.contains('-')) {
				println("You guessed the word: " + word);
				println("You Won");
				break;
			} else {
				String consumerInput = readLine("Your guess: ");
				consumerInput = enterCorrectInput(consumerInput);

				char consumerLetter = consumerInput.charAt(0);
				consumerLetter = Character.toUpperCase(consumerLetter);

				boolean checker = checkLetterExistence(consumerLetter, wordDashs, usedCharacters);
				mistakeLimit = drawOrSubstitute(checker, mistakeLimit, consumerLetter);
			}
		}
	}

	/*
	 * This func draws another body part if checker is false, It substitutes
	 * wordDash with letters It returns int, to make mistakeLimit changed
	 */
	private int drawOrSubstitute(boolean checker, int mistakeLimit, char consumerLetter) {
		if (checker == true) {
			println("That Guess Is Correct");
			println("The word now looks like this: " + arrayListToString(wordDashs));
			println("You have " + mistakeLimit + " guesses Left");
			canvas.displayWord(arrayListToString(wordDashs));
		} else {
			canvas.noteIncorrectGuess(consumerLetter);
			println("There are no " + consumerLetter + "'s " + "in the word");
			println("The word now looks like this: " + arrayListToString(wordDashs));
			mistakeLimit--;
			println("You have " + mistakeLimit + " guesses Left");
		}
		return mistakeLimit;
	}

	// This func make consumer enter only one character letter
	private String enterCorrectInput(String input) {

		while (input.length() > 1 || (input.length() == 1 && !Character.isLetter(input.charAt(0)))) {
			println("You Can Enter only 1 charachter and It must be letter only");
			println("Enter Again");
			input = readLine("Your guess: ");
		}

		return input;
	}

	/*
	 * This func checks if word constains the letter consumer entered, if so it
	 * returns true and substitute wordDashs with that letter, else it returns
	 * false
	 */
	public boolean checkLetterExistence(char letter, ArrayList<Character> wordDashs, String UsedCharacters) {
		if (word.contains("" + letter)) {
			UsedCharacters += letter;
			for (int i = 0; i < word.length(); i++) {
				if (word.charAt(i) == letter) {
					wordDashs.set(i, letter);
				}
			}

			return true;
		} else {
			return false;
		}
	}
}
