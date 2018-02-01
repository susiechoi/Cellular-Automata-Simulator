package cellsociety_team17;

import java.lang.reflect.Constructor;
import java.util.ArrayList;
import cellsociety_team17.Cell;
import javafx.scene.Group;

public class Grid {

	private int myWidth;
	private int myHeight;
	private Cell[][] myCells; 
	private Group myGroup;

	public Grid(int width, int height, ArrayList<Cell> activeCells) {
		myWidth = width;
		myHeight = height;
		myCells = new Cell[myHeight][myWidth]; 
		myGroup = new Group();
		for (Cell cell : activeCells) {
			myCells[cell.myRow][cell.myColumn] = cell; 
		}
		for (Cell cell : activeCells) {
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
		if (cell.myRow > 0) neighbors.add(myCells[cell.myRow-1][cell.myColumn]);
		if (cell.myRow < myHeight-1) neighbors.add(myCells[cell.myRow+1][cell.myColumn]);
		if (cell.myColumn > 0) neighbors.add(myCells[cell.myRow][cell.myColumn-1]);
		if (cell.myColumn< myWidth-1) neighbors.add(myCells[cell.myRow][cell.myColumn+1]);
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
