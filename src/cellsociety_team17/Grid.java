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

	public Grid(int width, int height, int simulationType, ArrayList<Cell> activeCells) {
		myWidth = width;
		myHeight = height;
		myCells = new Cell[myHeight][myWidth]; 
		mySimulationType = simulationType; 
		for (int row=0; row<myHeight; row++) {
			for (int col=0; col<myWidth; col++) {
				myCells[row][col] = new Cell(row, col, 0);
			}
		}
		for (Cell cell : activeCells) {
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
	
	// TODO account for each different Simulation type in cell 
	private ArrayList<Cell> findInBoundsNeighbors(Cell cell) {
		ArrayList<Cell> neighbors = new ArrayList<Cell>();
		if (cell.myRow > 0) neighbors.add(myCells[cell.myRow-1]);
		if (cell.myRow < myHeight-1) neighbors.add(myCells[cell.myRow+1]);
		if (cell.myCol > 0) neighbors.add(myCells[cell.myCol-1]);
		if (cell.myCol < myWidth-1) neighbors.add(myCells[cell.myCol+1]);
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
