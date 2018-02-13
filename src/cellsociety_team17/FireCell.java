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

	/** 
	 * Creates FireCell with specified row, column, and initial state. Uses DEFAULT_PROBABILITY for myProbability
	 * @param row - Cell's row position in grid
	 * @param column - Cell's column position in grid
	 * @param state- Cell's initial state (dead or alive) as indicated by XML document of random assignment 
	 */
	public FireCell(int row, int column, int state) {
		super(row, column, state);
		myProbability = DEFAULT_PROBABILITY;
		myShape=this.getMyShape();
		this.getMyShape().setFill(STATE_COLORS[this.getMyState()]);
		this.getMyShape().addEventHandler(MouseEvent.MOUSE_CLICKED, eventHandler);

	}

	/**
	 *  Creates FireCell using 
	 * @param row - Cell's row position in grid
	 * @param column - Cell's column position in grid
	 * @param state- Cell's initial state (dead or alive) as indicated by XML document of random assignment 
	 * @param prob- FireCell's probability of catching fire if it's neighbor is on fire
	 */
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
	/** 
	 * 
	 * FireCell determines whether the neighbors will start burning based on their probability of burning and FireCell's myState
	 * If myState is equal to 2 or BURNING, then the cell becomes EMPTY 
	 * @return Returns a list of cells to add to the active cells in the grid class 
	 */
	List<Cell> update() {
		ArrayList<Cell> newACells = new ArrayList<>();
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
		if (this.getMyState() == BURNING) {
			this.setMyState(EMPTY);
		}
		this.updateColor();
		return newACells;
	}

	
	
	/**
	 * Changes the myProbability value of FireCell
	 * @param p- probabilty to which the method sets myProbability 
	 */
	public void setMyProbability(double p) {
		myProbability = p;
	}

	@Override
	protected void updateColor() {
		this.getMyShape().setFill(STATE_COLORS[this.getMyState()]);
	}

}
