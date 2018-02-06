package cellsociety_team17;

import java.util.ArrayList;

import javafx.scene.paint.Color;

public class WatorCell extends Cell {
	public static final int DEFAULT_FISH_CLOCK = 2;
	public static final int DEFAULT_SHARK_CLOCK = 4;
	public static final int DEFAULT_SHARK_ENERGY = 3;
	public static final int EMPTY = 0;
	public static final int FISH = 1;
	public static final int SHARK = 2;
	public static final Color[] STATE_COLORS = { Color.WHITE, Color.PALEGREEN, Color.CYAN };

	private int fishCyclesReproduce;
	private int sharkCyclesReproduce;
	private int initialSharkEnergy;

	private int myFishCycles;
	private int mySharkCycles;
	private int mySharkEnergy;

	public WatorCell(int row, int column, int state) {
		super(row, column, state);
		this.myRectangle.setFill(STATE_COLORS[this.myState]);
		fishCyclesReproduce = DEFAULT_FISH_CLOCK;
		sharkCyclesReproduce = DEFAULT_SHARK_CLOCK;
		initialSharkEnergy = DEFAULT_SHARK_ENERGY;
		myFishCycles = 0;
		mySharkCycles = 0;
		mySharkEnergy = initialSharkEnergy;
	}

	public WatorCell(int row, int column, int state, int fishCycles, int sharkCycles, int sharkEnergy) {
		super(row, column, state);
		fishCyclesReproduce = fishCycles;
		sharkCyclesReproduce = sharkCycles;
		initialSharkEnergy = sharkEnergy;
		myFishCycles = 0;
		mySharkCycles = 0;
		mySharkEnergy = initialSharkEnergy;
		this.myRectangle.setFill(STATE_COLORS[this.myState]);
	}

	@Override
	ArrayList<Cell> update() {
		ArrayList<Cell> newACells = new ArrayList<Cell>();
		if (this.getMyState() == FISH) {
			newACells = this.fishUpdate();
		}

		else if (this.getMyState() == EMPTY) {

		}

		else if (this.getMyState() == SHARK) {
			newACells= sharkDeath(mySharkEnergy);
			if (this.getMyState() == SHARK) {
				newACells = this.sharkUpdate();
			}
		}

		updateColor(this);
		return newACells;
	}

	private ArrayList<Cell> fishUpdate() {
		System.out.println("Fish update");
		this.myFishCycles++;
		ArrayList<Cell> newACells = new ArrayList<Cell>();
		ArrayList<Integer> emptySpace = new ArrayList<Integer>();
		for (Cell neighbor : myNeighbors) {
			if (neighbor.getMyState() == EMPTY) {
				emptySpace.add(myNeighbors.indexOf(neighbor));
			}
		}

		if (!emptySpace.isEmpty()) {
			int indexOfMove = (int) Math.random() * emptySpace.size();
			Cell cellToMoveTo = myNeighbors.get(indexOfMove);
			cellToMoveTo.setMyState(FISH);
			// give the cell that the fished moved to a fish cycles amount
			updateColor(cellToMoveTo);
			newACells.add(cellToMoveTo);
			newACells.addAll(this.myNeighbors);
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

	private ArrayList<Cell> sharkUpdate() {
		System.out.println("Shark update");
		ArrayList<Cell> newACells = new ArrayList<Cell>();
		this.mySharkEnergy--;
		this.mySharkCycles++;
		ArrayList<Integer> fishSpace = new ArrayList<Integer>();
		ArrayList<Integer> emptySpace = new ArrayList<Integer>();
		for (Cell neighbor : myNeighbors) {
			if (neighbor.getMyState() == FISH) {
				fishSpace.add(myNeighbors.indexOf(neighbor));
			}

			else if (neighbor.getMyState() == EMPTY) {
				emptySpace.add(myNeighbors.indexOf(neighbor));
			}
		}

		if (!fishSpace.isEmpty()) {
			int indexOfMove = (int) Math.random() * fishSpace.size();
			Cell cellToMoveTo = myNeighbors.get(indexOfMove);
			cellToMoveTo.setMyState(SHARK);
			System.out.format("%d and %d", cellToMoveTo.myRow, cellToMoveTo.myColumn);
			this.mySharkEnergy++;
			updateColor(cellToMoveTo);
			newACells.add(cellToMoveTo);
			newACells.addAll(this.myNeighbors);
			if (this.mySharkCycles < this.sharkCyclesReproduce) {
				this.setMyState(EMPTY);
				System.out.println("Shark delete 1");
				((WatorCell) cellToMoveTo).mySharkCycles = this.mySharkCycles;
			} else {
				((WatorCell) cellToMoveTo).mySharkCycles = 0;
			}
			this.mySharkCycles = 0;
		}

		else if (fishSpace.isEmpty() && !emptySpace.isEmpty()) {
			int indexOfMove = (int) Math.random() * emptySpace.size();
			Cell cellToMoveTo = myNeighbors.get(indexOfMove);
			cellToMoveTo.setMyState(SHARK);
			newACells.add(cellToMoveTo);
			newACells.addAll(this.myNeighbors);
			updateColor(cellToMoveTo);
			if (mySharkCycles < sharkCyclesReproduce) {
				this.setMyState(EMPTY);
				System.out.println("Shark delete 2");
				((WatorCell) cellToMoveTo).mySharkCycles = this.mySharkCycles;
			} else {
				((WatorCell) cellToMoveTo).mySharkCycles = 0;
			}
			this.mySharkCycles = 0;
		}
		return newACells;
	}

	private ArrayList<Cell> sharkDeath(int sharkEnergy) {
		ArrayList<Cell> newACells = new ArrayList<Cell>();
		if (sharkEnergy == 0) {
			this.setMyState(EMPTY);
			newACells.add(this);
			newACells.addAll(this.myNeighbors);
			
		}
		return newACells;
	}

	private void updateColor(Cell cell) {
		cell.myRectangle.setFill(STATE_COLORS[cell.myState]);
	}

	@Override
	void updateColor() {
		// TODO Auto-generated method stub

	}

}
