package cellsociety_team17;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.concurrent.CopyOnWriteArrayList;

import cellsociety_team17.Cell;
import javafx.scene.paint.Color;

public class SegregationCell extends Cell {

	public static final Color[] STATE_COLORS = {Color.WHITE, Color.BLUE, Color.RED}; 
	public static final double DEFAULT_THRESHOLD = 0.5; 
	
	// threshold t represents satisfaction with segregation 
	private float myThreshold;
	private ArrayList<Cell> myNonEmptyNeighbors; 

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

	public void setMyState(int state) {
		myState = state;
	}

	public int getMyState() {
		return myState;
	}

	public void setNeighbors(ArrayList<Cell> neighbors) {
		myNeighbors = neighbors; 
	}

	public ArrayList<Cell> getNeighbors(){
		return myNeighbors;
	}
	
	public ArrayList<Cell> update() {
		Cell newACell = null;
		if (this.needToMove()) { 
			if (myNonEmptyNeighbors.size() == 0) {
				newACell = this; 
			}
			else {
				newACell = this.moveToEmptySpace();
			}
		}
		ArrayList<Cell> newACellList = new ArrayList<Cell>();
		newACellList.add(newACell);
		return newACellList;
	}

	private boolean needToMove() {
		int neighborsLikeMe = 0; 
		for (Cell neighbor : myNeighbors) {
			if (neighbor.myState != 0) {
				if (neighbor.myState == myState) neighborsLikeMe++; 
				myNonEmptyNeighbors.add(neighbor); 
			}
		}
		return (myNonEmptyNeighbors.size() == 0 || 
				((neighborsLikeMe / myNonEmptyNeighbors.size()) < myThreshold));
	}

	private Cell moveToEmptySpace() {
		CopyOnWriteArrayList<Cell> possEmptySpots = new CopyOnWriteArrayList<Cell>();

		for (Cell neighbor : myNeighbors) {
			if (neighbor.myState == 0) {
				neighbor.myState = myState;
				myState = 0; 
				this.updateColor(); 
				neighbor.updateColor(); 
				return neighbor;
			} 
			possEmptySpots.add(neighbor);
		}
		for (Cell possSpot : possEmptySpots) {
			
			if (possSpot.myState == 0) {
				possSpot.myState = myState;
				myState = 0; 
				this.updateColor(); 
				possSpot.updateColor();
				return possSpot;
			} 
			possEmptySpots.addAll(possSpot.myNeighbors);
		}
		return this;
	}

	@Override
	void updateColor() {
		this.myRectangle.setFill(STATE_COLORS[this.myState]);
	}


}