package cellsociety_team17;

import java.util.ArrayList;

import javafx.scene.shape.Polygon;
import javafx.scene.shape.Rectangle;

public abstract class Cell {

	protected int myState;
	protected int myRow;
	protected int myColumn;
	protected Rectangle myRectangle; 
	protected Polygon myPolygon; 
	protected ArrayList<Cell> myNeighbors;
	public static final int CELLSIZE = 40;

	public Cell(int row, int column, int state) {
		myState=state;
		myRow= row;
		myColumn= column;
		myRectangle = new Rectangle(CELLSIZE, CELLSIZE);
		myRectangle.setX(myColumn*CELLSIZE);
		myRectangle.setY(myRow * CELLSIZE);
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

	abstract ArrayList<Cell> update();
	
	abstract void updateColor();


}

