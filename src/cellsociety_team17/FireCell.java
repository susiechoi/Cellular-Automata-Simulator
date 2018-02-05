package cellsociety_team17;

import javafx.scene.paint.Color;

public class FireCell extends Cell {
	// myState: 2 is BURNING, 1 is TREE, 0 is EMPTY
	public static final int BURNING = 2;
	public static final int TREE = 1;
	public static final int EMPTY = 0;
	public static final Color[] STATE_COLORS = { Color.YELLOW, Color.GREEN, Color.RED };
	public static final double DEFAULT_PROBABILITY = 0.5;
	
	private double myProbability;

	public FireCell(int row, int column, int state) {
		super(row, column, state);
		myProbability = DEFAULT_PROBABILITY;
		this.myRectangle.setFill(STATE_COLORS[this.myState]);
	}

	public FireCell(int row, int column, int state, double prob) {
		super(row, column, state);
		myProbability = prob;

	}

	@Override
	void update() {
		/* Loops to see if any of the neighbors are burning */
		for (Cell cell : myNeighbors) {
			if (cell.myState == TREE && this.myState == BURNING) {
				double decimal = Math.random();
				if (decimal < myProbability) {
					cell.setMyState(BURNING);
					cell.updateColor();
				}
			}
		}
		// If the tree was burning in the last step, it is empty in this step
		else if (this.myState == BURNING) {
			this.setMyState(EMPTY);
		}
		this.updateColor();
	}
	
	/* First go through and set myNextState for all the Cells. Then, go through all of them 
	 * again to actually update them simultaneously. 
	 */
	

	 void updateColor() {
		this.myRectangle.setFill(STATE_COLORS[this.myState]);
	}

}
