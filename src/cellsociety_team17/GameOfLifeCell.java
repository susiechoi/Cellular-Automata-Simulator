package cellsociety_team17;

import java.util.ArrayList;
import java.util.List;

import javafx.scene.paint.Color;

/**
 * @author Judith Sanchez 
 *
 */
public class GameOfLifeCell extends Cell {

	// myState: 0 is dead, 1 is alive
	public static final int DEAD = 0;
	public static final int ALIVE = 1;
	public static final int MIN_ALIVE = 2;
	public static final int MAX_ALIVE = 3;
	public static final int DEAD_TO_ALIVE = 3;

	protected static final Color[] STATE_COLORS = { Color.RED, Color.GREEN };

	/**
	 * Creates GameOfLifeCell with specified row, column, and state
	 * @param row - Cell's row position in grid
	 * @param column - Cell's column position in grid
	 * @param state- Cell's initial state (dead or alive) as indicated by XML document of random assignment 
	 */
	public GameOfLifeCell(int row, int column, int state) {
		super(row, column, state);
		this.getMyShape().setFill(STATE_COLORS[this.getMyState()]);
		System.out.println("new cell");
		

	}

	
	
	@Override
	/** 
	 * GameOfLifeCell creates a list of neighbors around it that are in alive state
	 * It determines the next state of the cell based on the number of alive neighbors
	 * If the cell itself is dead, it determines whether or not it will revive
	 * @return Returns a list of cells to add to the active cells in the grid class 
	 */
	List<Cell> update() {
		System.out.println("update");
		ArrayList<Cell> aliveNeighbors = new ArrayList<>();
		ArrayList<Cell> newACells = new ArrayList<>();
		;
		for (Cell neighbor : this.getMyNeighbors()) {
			if (neighbor.getMyState() == ALIVE) {
				aliveNeighbors.add(neighbor);
			}
		}

		if (this.getMyState() == DEAD) {
			newACells = (ArrayList<Cell>) this.deadUpdate(aliveNeighbors);
		}
		if (this.getMyState() == ALIVE) {
			newACells = (ArrayList<Cell>) this.aliveUpdate(aliveNeighbors);
		}
		return newACells;
	}

	private List<Cell> deadUpdate(List<Cell> alive) {
		ArrayList<Cell> newACells = new ArrayList<>();
		if (alive.size() == DEAD_TO_ALIVE) {
			this.setMyState(ALIVE);
			this.updateColor();
			newACells.add(this);
			newACells.addAll(this.getNeighbors());
		}
		return newACells;
	}

	private List<Cell> aliveUpdate(List<Cell> alive) {
		ArrayList<Cell> newACells = new ArrayList<>();
		if (alive.size() < MIN_ALIVE || alive.size() > MAX_ALIVE) {
			this.setMyState(DEAD);
			this.updateColor();
			newACells.add(this);
			newACells.addAll(this.getNeighbors());
		}
		return newACells;
	}
	
	 @Override
	void updateColor() {
		this.getMyShape().setFill(STATE_COLORS[this.getMyState()]);
	}
}
