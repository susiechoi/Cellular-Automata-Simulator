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
	private int sharkEnergy;


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
		fishCyclesReproduce--;

		ArrayList<Integer> emptySpace = new ArrayList<Integer>();
		for (Cell cell : myNeighbors) {
			if (cell.getMyState() == EMPTY) {
				emptySpace.add(myNeighbors.indexOf(cell));
			}
		}

		if (!emptySpace.isEmpty()) {
			int indexOfMove = (int) Math.random() * emptySpace.size();
			Cell cellToMoveTo = myNeighbors.get(indexOfMove);
			cellToMoveTo.setMyState(FISH);
			updateColor(cellToMoveTo);
			if (fishCyclesReproduce > 0) {
				this.setMyState(EMPTY);
			}
		}

	}

	private void sharkUpdate() {

	}

	private void updateColor(Cell cell) {
		cell.myRectangle.setFill(STATE_COLORS[this.myState]);
	}
	

}
