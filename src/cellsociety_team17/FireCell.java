package cellsociety_team17;

import javafx.scene.paint.Color;

public class FireCell extends Cell {
	public static final int BURNING = 2;
	public static final int TREE = 1;
	public static final int EMPTY = 0;
	// myState: 2 is BURNING, 1 is TREE, 0 is EMPTY
	public static final Color[] STATE_COLORS = { Color.YELLOW, Color.GREEN, Color.RED };
	public static final double DEFAULT_PROBABILITY = 0.5;
	double myProbability;

	public FireCell(int row, int column, int state) {
		super(row, column, state);
		myProbability = DEFAULT_PROBABILITY;
	}

	public FireCell(int row, int column, int state, double prob) {
		super(row, column, state);
		myProbability = prob;

	}

	@Override
	void update() {
		Boolean threatened = false;
		/* Loops to see if any of the neighbors are burning */
		for (Cell cell : myNeighbors) {
			if (cell.myState == BURNING) {
				threatened = (cell.myState == BURNING);
			}
		}

		/*
		 * If at least one of the neighbors is burning, then it generates a random
		 * number between 0 and 1. If they probability is less than this number, the
		 * tree burns.
		 */
		if (threatened && this.myState == 1) {
			Double decimal = Math.random();
			if (decimal < myProbability) {
				this.setMyState(BURNING);
			}
		}
		// If the tree was burning in the last step, it is empty in this step
		if (this.myState == BURNING) {
			this.myState = EMPTY;
		}

		this.updateColor();
	}

	private void updateColor() {
		this.myRectangle.setFill(STATE_COLORS[this.myState]);
	}

}
