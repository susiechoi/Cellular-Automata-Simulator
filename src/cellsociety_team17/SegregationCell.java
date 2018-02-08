package cellsociety_team17;

import java.util.List;
import java.util.Random;
import java.util.concurrent.CopyOnWriteArrayList;

import cellsociety_team17.Cell;
import javafx.scene.paint.Color;

public class SegregationCell extends Cell {

	public static final double DEFAULT_THRESHOLD = 0.5; 

	// threshold t represents satisfaction with segregation 
	private float myThreshold;
	private List<Cell> myNonEmptyNeighbors; 

	public SegregationCell(int row, int col, int startState) {
		super(row, col, startState);
		myThreshold = (float) DEFAULT_THRESHOLD;
		this.updateColor();
		myNonEmptyNeighbors = new ArrayList<Cell>();
	}

	public SegregationCell(int row, int col, int startState, float threshold) {
		super(row, col, startState);
		myThreshold = threshold; 
		this.updateColor();
		myNonEmptyNeighbors = new ArrayList<Cell>();
	}

	@Override
	public void setNeighbors(List<Cell> neighbors) {
		setMyNeighbors(neighbors); 
	}

	@Override
	public List<Cell> getNeighbors(){
		return getMyNeighbors();
	}

	@Override
	public List<Cell> update() {
		Cell newACell = null;
		if (this.needToMove() && !myNonEmptyNeighbors.isEmpty()) { 
			newACell = this.moveToEmptySpace();
		}
		ArrayList<Cell> newACellList = new ArrayList<Cell>();
		newACellList.add(newACell);
		return newACellList;
	}

	private boolean needToMove() {
		int neighborsLikeMe = 0; 
		for (Cell neighbor : getMyNeighbors()) {
			if (neighbor.getMyState() != 0) {
				if (neighbor.getMyState() == this.getMyState()) {
					neighborsLikeMe++; 
				}
				myNonEmptyNeighbors.add(neighbor); 
			}
		}
		return (myNonEmptyNeighbors.isEmpty() || (neighborsLikeMe / myNonEmptyNeighbors.size()) < myThreshold);
	}

	private Cell moveToEmptySpace() {
		//		CopyOnWriteArrayList<Cell> possEmptySpots = new CopyOnWriteArrayList<Cell>();
		//
		//		for (Cell neighbor : myNeighbors) {
		//			if (neighbor.myState == 0) {
		//				neighbor.myState = myState;
		//				myState = 0; 
		//				this.updateColor(); 
		//				neighbor.updateColor(); 
		//				return neighbor;
		//			} 
		//			possEmptySpots.add(neighbor);
		//		}
		//		for (Cell possSpot : possEmptySpots) {
		//			
		//			if (possSpot.myState == 0) {
		//				possSpot.myState = myState;
		//				myState = 0; 
		//				this.updateColor(); 
		//				possSpot.updateColor();
		//				return possSpot;
		//			} 
		//			possEmptySpots.addAll(possSpot.myNeighbors);
		//		}
		//		return this;

		CopyOnWriteArrayList<Cell> possEmptySpots = new CopyOnWriteArrayList<Cell>();
		possEmptySpots.addAll(getMyNeighbors());
		Random randomGen = new Random(); 
		while (true) {
			int randomIndex = randomGen.nextInt(possEmptySpots.size());
			Cell possEmptySpot = possEmptySpots.get(randomIndex);
			if (possEmptySpot.getMyState() == 0) {
				possEmptySpot.setMyState(this.getMyState());
				this.setMyState(0); 
				this.updateColor(); 
				possEmptySpot.updateColor(); 
				return possEmptySpot;
			}
			possEmptySpots.addAll(possEmptySpot.getMyNeighbors());
		}
	}

	@Override
	void updateColor() {
		this.getMyShape().setFill(STATE_COLORS[this.getMyState()]);
	}


}