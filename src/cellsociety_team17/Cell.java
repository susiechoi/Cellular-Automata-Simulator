package cellsociety_team17;

import java.util.ArrayList;

public abstract class Cell {

	private int myState; 
	private int myType;

	public int getMyState() {
		return myState;
	}
	
	public void setMyState(int state) {
		myState= state;
	}
	
	public ArrayList<Cell> getNeighbors() {
		ArrayList<Cell> neighbors = new ArrayList<Cell>();
		// This ArrayList needs to be a different size for corner, edge, and inner cells. 
		// So each cell needs to have an indicator for what type it is
		return neighbors;
		
	}
	
	abstract void update();
}
