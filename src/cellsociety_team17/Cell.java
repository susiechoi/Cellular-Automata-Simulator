package cellsociety_team17;

import java.util.ArrayList;

public abstract class Cell {

	private int myState;
	private ArrayList<Cell> myNeighbors;

	public Cell(int row, int column, int state) {
		myState=state;
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
	
	public void setNeighbors(ArrayList<Cell> neighbors) {
		myNeighbors= neighbors;
	}
	
	

	abstract void update();
}
