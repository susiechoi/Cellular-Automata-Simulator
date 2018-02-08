package cellsociety_team17;

import java.util.ArrayList;
import java.util.List;

import javafx.scene.shape.Shape;

import javafx.scene.shape.Rectangle;

public abstract class Cell {

	private int myState;
	private int myRow;
	private int myColumn;
	private Shape myShape; 
	private List<Cell> myNeighbors;
	public static final int CELLSIZE = 40;

	public Cell(int row, int column, int state) {
		myState=state;
		myRow= row;
		myColumn= column;
		myShape = new Rectangle(CELLSIZE, CELLSIZE);
		myShape.setTranslateX(myColumn*CELLSIZE);
		myShape.setTranslateY(myRow * CELLSIZE);
	}
	
	public Cell(int row, int column, int state, Shape shape) {
		this(row, column, state);
		myShape = shape;
	}
	
	
	/**
	 * Returns the int value of current state
	 * @return int the current state of the cell
	 */
	public int getMyState() {
		return myState;
	}
	/**
	 * Sets the int value state of the cell
	 * @param state int value of current state
	 */
	public void setMyState(int state) {
		myState = state;
	}
	/**
	 * Returns the int value of the cell's row
	 * @return int row containing the cell
	 */
	public int getMyRow() {
		return myRow;
	}
	/**
	 * Sets the int value of the row in the grid representing this cell
	 * @param r int value of the row in the grid
	 */
	public void setMyRow(int r) {
		myRow = r;
	}
	/**
	 * Returns the int value of the cell's column
	 * @return int column containing the cell
	 */
	public int getMyColumn() {
		return myColumn;
	}
	/**
	 * Sets the int value of the column in the grid representing this cell
	 * @param c int value of the column in the grid
	 */
	public void setMyColumn(int c) {
		myColumn = c;
	}
	/**
	 * returns the Shape instance of this cell
	 * @return Shape of the cell
	 */
	public Shape getMyShape() {
		return myShape;
		
	}
	/**
	 * Sets the shape value of a cell
	 * @param s Shape of the cell
	 */
	public void setMyShape(Shape s) {
		myShape = s;
	}
	
	/**
	 * Gets an ArrayList of cells containing all the neighboring cells
	 * @return ArrayList<CEll>
	 */
	public List<Cell> getNeighbors() {
		return getMyNeighbors();
	}
	/**
	 * Sets an ArrayList of cells containing all the neighboring cells
	 * @param neighbors
	 */
	public void setNeighbors(ArrayList<Cell> neighbors) {
		setMyNeighbors(neighbors);
	}

	abstract ArrayList<Cell> update();
	
	abstract void updateColor();


	public List<Cell> getMyNeighbors() {
		return myNeighbors;
	}


	public void setMyNeighbors(ArrayList<Cell> myNeighbors) {
		this.myNeighbors = myNeighbors;
	}


}

