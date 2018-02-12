package cellsociety_team17;

import java.util.ArrayList;
import java.util.List;

import javafx.event.EventHandler;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.shape.Shape;
import javafx.stage.Stage;

public class FireCell extends Cell {
	// myState: 2 is BURNING, 1 is TREE, 0 is EMPTY
	public static final int BURNING = 2;
	public static final int TREE = 1;
	public static final int EMPTY = 0;
	protected static final Color[] STATE_COLORS = { Color.YELLOW, Color.GREEN, Color.RED };
	protected static final double DEFAULT_PROBABILITY = 0.5;
	private Shape myShape;
	private double myProbability;

	public FireCell(int row, int column, int state) {
		super(row, column, state);
		myProbability = DEFAULT_PROBABILITY;
		myShape=this.getMyShape();
		this.getMyShape().setFill(STATE_COLORS[this.getMyState()]);
		this.getMyShape().addEventHandler(MouseEvent.MOUSE_CLICKED, eventHandler);

	}

	public FireCell(int row, int column, int state, double prob) {
		super(row, column, state);
		myProbability = prob;

	}

	EventHandler<MouseEvent> eventHandler = new EventHandler<MouseEvent>() {
		@Override
		public void handle(MouseEvent event) {
			int randomState = (int) (Math.random() * 3);
			setMyState(randomState);
			myShape.setFill(STATE_COLORS[randomState]);
			
		}
	};

	@Override
	List<Cell> update() {
		ArrayList<Cell> newACells = new ArrayList<>();
		/* Loops to see if any of the neighbors are burning */
		for (Cell cell : getMyNeighbors()) {
			if (cell.getMyState() == TREE && this.getMyState() == BURNING) {
				double decimal = Math.random();
				if (decimal < myProbability) {
					cell.setMyState(BURNING);
					cell.updateColor();
					newACells.add(cell);
				}
			}
		}
		// If the tree was burning in the last step, it is empty in this step
		if (this.getMyState() == BURNING) {
			this.setMyState(EMPTY);
		}
		this.updateColor();
		return newACells;
	}

	/*
	 * First go through and set myNextState for all the Cells. Then, go through all
	 * of them again to actually update them simultaneously.
	 */

	void setMyProbability(double p) {
		myProbability = p;
	}

	@Override
	void updateColor() {
		this.getMyShape().setFill(STATE_COLORS[this.getMyState()]);
	}

}
