package cellsociety_team17;

import java.lang.reflect.Constructor;
import java.util.ArrayList;

import javafx.scene.Group;
import javafx.scene.control.Cell;

public class Grid {

	private int myWidth;
	private int myHeight;
	private Cell[][] myCells; 
	private Group myGroup;

	public Grid(int width, int height, ArrayList<Cell> cells) {
		myWidth = width;
		myHeight = height;
		myCells = new Cell[myHeight][myWidth]; 
		for (Cell cell : cells) {
			myCells[cell.myRow][cell.myCol] = cell; 
		}
		for (Cell cell : cells) {
			setCellNeighbors(cell);
			myGroup.getChildren().add(cell.myRectangle);
		}
	}

	private void setCellNeighbors(Cell cell) {
		ArrayList<Cell> inBoundsNeighbors = findInBoundsNeighbors(cell);
		cell.setNeighbors(inBoundsNeighbors);
	}
	
	private ArrayList<Cell> findInBoundsNeighbors(Cell cell) {
		ArrayList<Cell> neighbors = new ArrayList<Cell>();
		if (cell.myRow > 0) neighbors.add(myCells[cell.myRow-1][cell.myCol]);
		if (cell.myRow < myHeight-1) neighbors.add(myCells[cell.myRow+1][cell.myCol]);
		if (cell.myCol > 0) neighbors.add(myCells[cell.myRow][cell.myCol-1]);
		if (cell.myCol < myWidth-1) neighbors.add(myCells[cell.myRow][cell.myCol+1]);
		return neighbors; 
	}

	public void updateCells(ArrayList<Cell> activeCells) {
		for (Cell cell : activeCells) {
			cell.update(); 
		}
	}

	public Group getGroup() {
		return myGroup; 
	}

	public int getWidth() {
		return myWidth;
	}

	public int getHeight() {
		return myHeight; 
	}

}
