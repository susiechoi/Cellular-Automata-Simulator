package cellsociety_team17;

public class fireCell extends Cell {

	double myProbability;
	// myState: 2 is BURNING, 1 is TREE, 0 is EMPTY

	public fireCell(int row, int column, int state) {
		super(row, column, state);
	}

	public fireCell(int row, int column, int state, double prob) {
		super(row, column, state);
		myProbability = prob;

	}

	@Override
	void update() {
		Boolean threatened = false;
		/* Loops to see if any of the neighbors are burning */
		for (Cell cell : myNeighbors) {
			if (cell.myState == 2) {
				threatened = (cell.myState == 2);
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
				this.setMyState(2);
			}
		}
		// If the tree was burning in the last step, it is empty in this step
		if (this.myState == 2) {
			this.myState = 0;
		}

	}

}
