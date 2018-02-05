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

	public SegregationCell(int row, int col, int startState) {
		super(row, col, startState);
		myThreshold = (float) DEFAULT_THRESHOLD;
		this.updateColor();
	}

	public SegregationCell(int row, int col, int startState, float threshold) {
		super(row, col, startState);
		myThreshold = threshold; 
		this.updateColor();
	}

	public void setMyState(int state) {
		myState = state;
	}

	public int getMyState() {
		return myState;
	}

	// TODO ? in Grid class, add sthg so that if it's the segregation simulation, also the immediate diagonal cells are added
	public void setNeighbors(ArrayList<Cell> neighbors) {
		myNeighbors = neighbors; 
	}

	public ArrayList<Cell> getNeighbors(){
		return myNeighbors;
	}

	public void updateState() throws Exception {
		if (this.needToMove()) { 
			this.moveToEmptySpace();
		}
	}

	private boolean needToMove() {
		int neighborsLikeMe = 0;
		int nonEmptyNeighbors = -1; 
		for (Cell neighbor : myNeighbors) {
			if (neighbor.myState != 0) {
				if (neighbor.myState == myState) neighborsLikeMe++; 
				nonEmptyNeighbors++; 
			}
		}
		return ((neighborsLikeMe / nonEmptyNeighbors) < myThreshold);
	}

	private void moveToEmptySpace() throws Exception {
		CopyOnWriteArrayList<Cell> possEmptySpots = new CopyOnWriteArrayList<Cell>();

		for (Cell neighbor : myNeighbors) {
			if (neighbor.myState == 0) {
				neighbor.myState = myState;
				myState = 0; 
				this.updateColor(); 
				neighbor.updateColor(); 
				return;
			} 
			possEmptySpots.add(neighbor);
		}
		for (Cell possSpot : possEmptySpots) {
			
			if (possSpot.myState == 0) {
				possSpot.myState = myState;
				myState = 0; 
				this.updateColor(); 
				possSpot.updateColor();
				return;
			} 
			possEmptySpots.addAll(possSpot.myNeighbors);
		}
		throw new Exception("Threshold unsatisfied, but no empty spots to move to!");
	}


	@Override
	ArrayList<Cell> update() {
		ArrayList<Cell> newACells = new ArrayList<Cell>();
		try {
			updateState();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return newACells;
		
	}

	@Override
	void updateColor() {
		this.myRectangle.setFill(STATE_COLORS[this.myState]);
	}



}