
/*
 * File: HangmanCanvas.java
 * ------------------------
 * This file keeps track of the Hangman display.
 */

import java.awt.Canvas;

import acm.graphics.*;
import acm.util.ErrorException;

public class HangmanCanvas extends GCanvas {

	// I keep this points as an end points to start next body part with it
	private GPoint keepPoints;

	// This string keeps incorect guesses in label
	private String incorectGuess = "";
	private GLabel incorectGuessLabel;

	// This counts how many mistakes consumer made, to draw body parts
	private int mistakesCounter = 0;
	
	//This is current word label
	private GLabel wordLabel;

	/** Resets the display so that only the scaffold appears */
	public void reset() {
		removeAll();
		mistakesCounter = 0;
		incorectGuess = "";
		wordLabel=new GLabel("");
		incorectGuessLabel=new GLabel("");

		drawFirstTools();
	}

	/*
	 * This func draws first parts of Hangman, which is first 3 line, where
	 * hangman will hang on
	 */
	private void drawFirstTools() {
		GPoint scaffoldStart = new GPoint(getWidth() / 2 - BEAM_LENGTH, getHeight() / 2 - SCAFFOLD_HEIGHT / 2 - 25);
		GLine scaffold = new GLine(scaffoldStart.getX(), scaffoldStart.getY(), getWidth() / 2 - BEAM_LENGTH,
				getHeight() / 2 + SCAFFOLD_HEIGHT / 2 - 25);
		add(scaffold);

		GPoint beamEnd = new GPoint(scaffoldStart.getX() + BEAM_LENGTH, scaffoldStart.getY());
		GLine beam = new GLine(scaffoldStart.getX(), scaffoldStart.getY(), beamEnd.getX(), beamEnd.getY());
		add(beam);

		GLine rope = new GLine(beam.getEndPoint().getX(), beam.getEndPoint().getY(), beam.getEndPoint().getX(),
				beam.getEndPoint().getY() + ROPE_LENGTH);
		add(rope);

		keepPoints = new GPoint(rope.getX() - HEAD_RADIUS, rope.getEndPoint().getY());

		incorectGuessLabel = new GLabel(incorectGuess);
		add(incorectGuessLabel, 40, getHeight() - 25);

		wordLabel = new GLabel("");
		add(wordLabel, 40, getHeight() - 50);

	}

	/**
	 * Updates the word on the screen to correspond to the current state of the
	 * game. The argument string shows what letters have been guessed so far;
	 * unguessed letters are indicated by hyphens.
	 */
	public void displayWord(String word) {
		wordLabel.setLabel(word);

	}

	/**
	 * Updates the display to correspond to an incorrect guess by the user.
	 * Calling this method causes the next body part to appear on the scaffold
	 * and adds the letter to the list of incorrect guesses that appears at the
	 * bottom of the window.
	 */
	public void noteIncorrectGuess(char letter) {
		incorectGuess += letter;
		incorectGuessLabel.setLabel(incorectGuess);
		mistakesCounter++;
		
		switch (mistakesCounter) {
		case 1:
			drawHead();
			break;
		case 2:
			drawBody();
			break;
		case 3:
			drawLeftHand();
			break;
		case 4:
			drawRightHand();
			break;
		case 5:
			drawLeftLeg();
			break;
		case 6:
			drawRightLeg();
			break;
		case 7:
			drawLeftFoot();
			break;
		case 8:
			drawRightFoot();
			break;
		//default: throw new ErrorException("");
		}
	}
	
	//This func draws head
	private void drawHead() {
		GOval head = new GOval(2 * HEAD_RADIUS, 2 * HEAD_RADIUS);
		add(head, keepPoints.getX(), keepPoints.getY());

		keepPoints = new GPoint(head.getX() + HEAD_RADIUS, head.getY() + 2 * HEAD_RADIUS);
	}

	//This func draws body and hips
	private void drawBody() {
		GLine body = new GLine(keepPoints.getX(), keepPoints.getY(), keepPoints.getX(),
				keepPoints.getY() + BODY_LENGTH);
		add(body);

		GLine hips = new GLine(body.getEndPoint().getX() - HIP_WIDTH, body.getEndPoint().getY(),
				body.getEndPoint().getX() + HIP_WIDTH, body.getEndPoint().getY());
		add(hips);
		keepPoints = new GPoint(body.getEndPoint());
	}
	//This func draws left hand
	private void drawLeftHand() {
		double upperArmY = keepPoints.getY() - BODY_LENGTH + ARM_OFFSET_FROM_HEAD;
		GLine upperArm = new GLine(keepPoints.getX(), upperArmY, keepPoints.getX() - UPPER_ARM_LENGTH, upperArmY);
		add(upperArm);

		GLine lowerArm = new GLine(upperArm.getEndPoint().getX(), upperArm.getEndPoint().getY(),
				upperArm.getEndPoint().getX(), upperArm.getEndPoint().getY() + LOWER_ARM_LENGTH);
		add(lowerArm);
	}
	//This func draws right hand
	private void drawRightHand() {
		double upperArmY = keepPoints.getY() - BODY_LENGTH + ARM_OFFSET_FROM_HEAD;
		GLine upperArm = new GLine(keepPoints.getX(), upperArmY, keepPoints.getX() + UPPER_ARM_LENGTH, upperArmY);
		add(upperArm);

		GLine lowerArm = new GLine(upperArm.getEndPoint().getX(), upperArm.getEndPoint().getY(),
				upperArm.getEndPoint().getX(), upperArm.getEndPoint().getY() + LOWER_ARM_LENGTH);
		add(lowerArm);
	}
	//This func draws left leg
	private void drawLeftLeg() {
		double legX = keepPoints.getX() - HIP_WIDTH;
		GLine leg = new GLine(legX, keepPoints.getY(), legX, keepPoints.getY() + LEG_LENGTH);
		add(leg);
	}
	//This func draws right leg
	private void drawRightLeg() {
		double legX = keepPoints.getX() + HIP_WIDTH;
		GLine leg = new GLine(legX, keepPoints.getY(), legX, keepPoints.getY() + LEG_LENGTH);
		add(leg);

		keepPoints = new GPoint(legX - HIP_WIDTH, leg.getEndPoint().getY());
	}
	//This func draws left foot
	private void drawLeftFoot() {
		double footX = keepPoints.getX() - HIP_WIDTH;
		GLine foot = new GLine(footX, keepPoints.getY(), footX - FOOT_LENGTH, keepPoints.getY());
		add(foot);
	}
	//This func draws right foot 
	private void drawRightFoot() {
		double footX = keepPoints.getX() + HIP_WIDTH;
		GLine foot = new GLine(footX, keepPoints.getY(), footX + FOOT_LENGTH, keepPoints.getY());
		add(foot);
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
