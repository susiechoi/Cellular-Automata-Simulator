package cellsociety_team17;

import java.util.ArrayList;

public abstract class Cell {

	private int myState;
	private ArrayList<Cell> myNeighbors;

	public Cell(ArrayList<Cell> neighbors) {
		myNeighbors = neighbors;
	}

	public int getMyState() {
		return myState;
	}

	public void setMyState(int state) {
		myState = state;
	}
	
	public ArrayList<Cell> getNeighbors(){
		return myNeighbors;
	}

	abstract void update();
}
