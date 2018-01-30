package cellsociety_team17;

import java.util.ArrayList;

import javafx.scene.Group;
import javafx.scene.control.Cell;

public class Grid {

	private int myWidth;
	private int myHeight;
	private int mySimulationType;
	private Cell[][] myCells; 
	private Group myGroup;

	public Grid(int width, int height) {
		this(1, 400, 400, new ArrayList<Cell>()); 
	}

	public Grid(int width, int height, int simulationType, ArrayList<Cell> cells) {
		myWidth = width;
		myHeight = height;
		myCells = new Cell[myHeight][myWidth]; 
		mySimulationType = simulationType; 
		for (Cell cell : cells) {
			myCells[cell.myRow][cell.myCol] = cell; 
			myGroup.getChildren().add(cell.myRectangle);
		}
		for (Cell cell : cells) {
			setCellNeighbors(cell);
		}
	}
	
	// NEED SETNEIGHBORS METHOD IN CELL CLASS
	public void setCellNeighbors(Cell cell) {
		ArrayList<Cell> neighbors = new ArrayList<Cell>();
		if (cell.myRow > 0) neighbors.add(myCells[cell.myRow-1]);
		if (cell.myRow < myHeight) neighbors.add(myCells[cell.myRow+1]);
		if (cell.myCol > 0) neighbors.add(myCells[cell.myCol-1]);
		if (cell.myCol < myWidth) neighbors.add(myCells[cell.myCol+1]);
		cell.setNeighbors(neighbors);
	}

	public void updateCells(ArrayList<Cell> activeCells) {
		for (Cell cell : activeCells) {
			cell.update(); 
		}
	}

	private Group getGroup() {
		return myGroup; 
	}

	private int getWidth() {
		return myWidth;
	}

	private int getHeight() {
		return myHeight; 
	}

}
