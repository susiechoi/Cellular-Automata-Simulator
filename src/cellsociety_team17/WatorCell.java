package cellsociety_team17;

import java.util.ArrayList;
import java.util.List;

import javafx.scene.paint.Color;

public class WatorCell extends Cell {
	public static final int DEFAULT_FISH_CLOCK = 2;
	public static final int DEFAULT_SHARK_CLOCK = 4;
	public static final int DEFAULT_SHARK_ENERGY = 3;
	public static final int EMPTY = 0;
	public static final int FISH = 1;
	public static final int SHARK = 2;
	protected static final Color[] STATE_COLORS = { Color.WHITE, Color.PALEGREEN, Color.CYAN };

	private int fishCyclesReproduce;
	private int sharkCyclesReproduce;
	private int initialSharkEnergy;

	private int myFishCycles;
	private int mySharkCycles;
	private int mySharkEnergy;

	/**
	 * Creates WatorCell with specified row, column, and initial state.
	 * Uses DEFAULT values for fishCyclesReproduce, sharkCyclesReproduce, and intitialSharkEnergy
	 * @param row - Cell's row position in grid
	 * @param column - Cell's column position in grid
	 * @param state- Cell's initial state (fish, shark, or empty) as indicated by XML document of random assignment 
	 */
	public WatorCell(int row, int column, int state) {
		super(row, column, state);
		this.getMyShape().setFill(STATE_COLORS[this.getMyState()]);
		fishCyclesReproduce = DEFAULT_FISH_CLOCK;
		sharkCyclesReproduce = DEFAULT_SHARK_CLOCK;
		initialSharkEnergy = DEFAULT_SHARK_ENERGY;
		myFishCycles = 0;
		mySharkCycles = 0;
		mySharkEnergy = initialSharkEnergy;
	}
	
	/**
	 * Creates WatorCell with specified row, column, and initial state.
	 * @param row - Cell's row position in grid
	 * @param column - Cell's column position in grid
	 * @param state- Cell's initial state (fish, shark, or empty) as indicated by XML document of random assignment 
	 * @param fishCycles - How many Cycles it takes for a fish to reproduce
	 * @param sharkCycles - How many Cycles it takes for a shark to reproduce
	 * @param sharkEnergy - Initial energy value for a new shark 
	 */

	public WatorCell(int row, int column, int state, int fishCycles, int sharkCycles, int sharkEnergy) {
		super(row, column, state);
		fishCyclesReproduce = fishCycles;
		sharkCyclesReproduce = sharkCycles;
		initialSharkEnergy = sharkEnergy;
		myFishCycles = 0;
		mySharkCycles = 0;
		mySharkEnergy = initialSharkEnergy;
		this.getMyShape().setFill(STATE_COLORS[this.getMyState()]);
	}

	@Override
	/**
	 * Determines the next state of a cell based on movement rules
	 * Fish move to empty cells. Sharks move to fish cells first, and then empty cells
	 * Shark cells turn to EMPTY if they run out of energy
	 * @return Returns a list of cells to add to the active cells in the grid class 
	 */
	List<Cell> update() {
		ArrayList<Cell> newACells = new ArrayList<>();
		if (this.getMyState() == FISH) {
			newACells = (ArrayList<Cell>) this.fishUpdate();
		}

		else if (this.getMyState() == EMPTY) {

		}

		else if (this.getMyState() == SHARK) {
			newACells = (ArrayList<Cell>) sharkDeath(mySharkEnergy);
			if (this.getMyState() == SHARK) {
				newACells = (ArrayList<Cell>) this.sharkUpdate();
			}
		}

		updateColor(this);
		return newACells;
	}

	private List<Cell> fishUpdate() {
		System.out.println("Fish update");
		this.myFishCycles++;
		ArrayList<Cell> newACells = new ArrayList<>();
		ArrayList<Integer> emptySpace = new ArrayList<>();
		for (Cell neighbor : getMyNeighbors()) {
			if (neighbor.getMyState() == EMPTY) {
				emptySpace.add(getMyNeighbors().indexOf(neighbor));
			}
		}

		if (!emptySpace.isEmpty()) {
			int indexOfMove = (int) Math.random() * emptySpace.size();
			Cell cellToMoveTo = getMyNeighbors().get(indexOfMove);
			cellToMoveTo.setMyState(FISH);
			// give the cell that the fished moved to a fish cycles amount
			updateColor(cellToMoveTo);
			newACells.add(cellToMoveTo);
			newACells.addAll(this.getMyNeighbors());
			if (this.myFishCycles < this.fishCyclesReproduce) {
				this.setMyState(EMPTY);
				((WatorCell) cellToMoveTo).myFishCycles = this.myFishCycles;
			} else {
				((WatorCell) cellToMoveTo).myFishCycles = 0;
			}
			this.myFishCycles = 0;
		}
		return newACells;
	}

	private List<Cell> sharkUpdate() {
		System.out.println("Shark update");
		ArrayList<Cell> newACells = new ArrayList<>();
		this.mySharkEnergy--;
		this.mySharkCycles++;
		ArrayList<Integer> fishSpace = new ArrayList<>();
		ArrayList<Integer> emptySpace = new ArrayList<>();
		for (Cell neighbor : getMyNeighbors()) {
			if (neighbor.getMyState() == FISH) {
				fishSpace.add(getMyNeighbors().indexOf(neighbor));
			}

			else if (neighbor.getMyState() == EMPTY) {
				emptySpace.add(getMyNeighbors().indexOf(neighbor));
			}
		}

		if (!fishSpace.isEmpty()) {
			newACells = (ArrayList<Cell>) this.sharkToFish(fishSpace);
		}

		else if (fishSpace.isEmpty() && !emptySpace.isEmpty()) {
			newACells = (ArrayList<Cell>) this.sharktoEmpty(emptySpace);
		}
		return newACells;
	}

	private List<Cell> sharkToFish(List<Integer> fishSpace) {
		ArrayList<Cell> newACells = new ArrayList<>();
		int indexOfMove = (int) Math.random() * fishSpace.size();
		Cell cellToMoveTo = getMyNeighbors().get(indexOfMove);
		cellToMoveTo.setMyState(SHARK);
		System.out.format("%d and %d", cellToMoveTo.getMyRow(), cellToMoveTo.getMyColumn());
		this.mySharkEnergy++;
		updateColor(cellToMoveTo);
		newACells.add(cellToMoveTo);
		newACells.addAll(this.getMyNeighbors());
		if (this.mySharkCycles < this.sharkCyclesReproduce) {
			this.setMyState(EMPTY);
			System.out.println("Shark delete 1");
			((WatorCell) cellToMoveTo).mySharkCycles = this.mySharkCycles;
		} else {
			((WatorCell) cellToMoveTo).mySharkCycles = 0;
		}
		this.mySharkCycles = 0;
		return newACells;
	}

	private List<Cell> sharktoEmpty(List<Integer> emptySpace) {
		ArrayList<Cell> newACells = new ArrayList<>();
		int indexOfMove = (int) Math.random() * emptySpace.size();
		Cell cellToMoveTo = getMyNeighbors().get(indexOfMove);
		cellToMoveTo.setMyState(SHARK);
		newACells.add(cellToMoveTo);
		newACells.addAll(this.getMyNeighbors());
		updateColor(cellToMoveTo);
		if (mySharkCycles < sharkCyclesReproduce) {
			this.setMyState(EMPTY);
			System.out.println("Shark delete 2");
			((WatorCell) cellToMoveTo).mySharkCycles = this.mySharkCycles;
		} else {
			((WatorCell) cellToMoveTo).mySharkCycles = 0;
		}
		this.mySharkCycles = 0;
		return newACells;
	}

	private List<Cell> sharkDeath(int sharkEnergy) {
		ArrayList<Cell> newACells = new ArrayList<>();
		if (sharkEnergy == 0) {
			this.setMyState(EMPTY);
			newACells.add(this);
			newACells.addAll(this.getMyNeighbors());

		}
		return newACells;
	}

	private void updateColor(Cell cell) {
		cell.getMyShape().setFill(STATE_COLORS[cell.getMyState()]);
	}

	@Override
	protected void updateColor() {
		// TODO Auto-generated method stub

	}
	
	/**
	 * Sets the value of sharkCyclesReproduce
	 * @param mSharkCycles- value to which it sets to
	 */
	public void setMySharkCycles(int mSharkCycles) {
		sharkCyclesReproduce = mSharkCycles;
		
	}
	
	/**
	 * Sets the value of sharkCyclesEnergy
	 * @param mSharkEnergy- value to which it sets to
	 */
	
	public void setInitialSharkEnergy(int mSharkEnergy) {
		sharkCyclesReproduce = mSharkEnergy;
		
	}
	
	/**
	 * Sets the value of fishCyclesReproduce
	 * @param mSharkCycles- value to which it sets to
	 */
	public void setMyfishCycles(int mSharkCycles) {
		fishCyclesReproduce = mSharkCycles;
		
	}

}
