package cellsociety_team17;

import java.util.ArrayList;

import javafx.scene.shape.Rectangle;

public abstract class Cell {

	protected int myState;
	protected int myRow;
	protected int myColumn;
	protected Rectangle myRectangle; 
	protected ArrayList<Cell> myNeighbors;

	public Cell(int row, int column, int state) {
		myState=state;
		myRow= row;
		myColumn= column;
	}

	public int getMyState() {
		return myState;
	}

	public void setMyState(int state) {
		myState = state;
	}

	public ArrayList<Cell> getNeighbors() {
		return myNeighbors;
	}

	public void setNeighbors(ArrayList<Cell> neighbors) {
		myNeighbors = neighbors;
	}

	abstract void update();
}

