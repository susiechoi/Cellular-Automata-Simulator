package cellsociety_team17;

import java.util.ArrayList;

import javafx.scene.paint.Color;

public class WatorCell extends Cell {
	public static final int DEFAULT_FISH_CLOCK = 2;
	public static final int DEFAULT_SHARK_CLOCK = 4;
	public static final int DEFAULT_SHARK_ENERGY = 6;
	public static final int EMPTY = 0;
	public static final int FISH = 1;
	public static final int SHARK = 2;
	public static final Color[] STATE_COLORS = { Color.WHITE, Color.PALEGREEN, Color.CYAN };

	private int fishCyclesReproduce;
	private int sharkCyclesReproduce;
	private int initialSharkEnergy;
	
	private int fishCycles;
	private int sharkCycles;
	private int mySharkEnergy;

	public WatorCell(int row, int column, int state) {
		super(row, column, state);

	}

	public WatorCell(int row, int column, int state, int fishCycles, int sharkCycles, int sharkEnergy) {
		super(row, column, state);
		fishCyclesReproduce = fishCycles;
		sharkCyclesReproduce = sharkCycles;
		initialSharkEnergy = sharkEnergy;
		fishCycles=0;
		sharkCycles=0;
		mySharkEnergy=sharkEnergy;
	}

	@Override
	void update() {
		if (this.getMyState() == FISH) {
			this.fishUpdate();
		}

		if (this.getMyState() == SHARK) {
			this.sharkUpdate();
		}

		updateColor(this);
	}
	

	private void fishUpdate() {
		this.fishCycles++;

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
			
			if (this.fishCycles < this.fishCyclesReproduce) {
				this.setMyState(EMPTY);
			}
			this.fishCycles=0;
		}

	}

	private void sharkUpdate() {
		this.mySharkEnergy--;
		this.sharkCycles++;
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
			this.mySharkEnergy++;
			// give the cell that the shark moved to a shark cycles amount
			updateColor(cellToMoveTo);
			if (this.sharkCycles < this.sharkCyclesReproduce) {
				this.setMyState(EMPTY);
			}
			this.sharkCycles=0;
		}
		
		if (fishSpace.isEmpty() && !emptySpace.isEmpty()) {
			int indexOfMove = (int) Math.random() * emptySpace.size();
			Cell cellToMoveTo = myNeighbors.get(indexOfMove);
			cellToMoveTo.setMyState(SHARK);
			// give the cell that the shark moved to a shark cycles amount
			updateColor(cellToMoveTo);	
			if (sharkCycles < sharkCyclesReproduce) {
				this.setMyState(EMPTY);
			}
			this.sharkCycles=0;
		}	
	}

	private void updateColor(Cell cell) {
		cell.myRectangle.setFill(STATE_COLORS[this.myState]);
	}

	@Override
	void updateColor() {
		// TODO Auto-generated method stub
		
	}
	

}
