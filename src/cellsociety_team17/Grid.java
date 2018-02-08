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
			myCells[cell.getMyRow()][cell.getMyColumn()] = cell; 
		}
		for (Cell cell : activeCells) {
			setCellNeighbors(cell);
			myGroup.getChildren().add(cell.getMyShape());
		}
	}
	
	

	private void setCellNeighbors(Cell cell) {
		ArrayList<Cell> inBoundsNeighbors = findInBoundsNeighbors(cell);
		cell.setNeighbors(inBoundsNeighbors);
	}
	
	private ArrayList<Cell> findInBoundsNeighbors(Cell cell) {
		ArrayList<Cell> neighbors = new ArrayList<Cell>();
		if (cell.getMyRow() > 0) neighbors.add(myCells[cell.getMyRow()-1][cell.getMyColumn()]);
		if (cell.getMyRow() < myHeight-1) neighbors.add(myCells[cell.getMyRow()+1][cell.getMyColumn()]);
		if (cell.getMyColumn() > 0) neighbors.add(myCells[cell.getMyRow()][cell.getMyColumn()-1]);
		if (cell.getMyColumn()< myWidth-1) neighbors.add(myCells[cell.getMyRow()][cell.getMyColumn()+1]);
		return neighbors; 
	}

	public ArrayList<Cell> updateCells(ArrayList<Cell> activeCells) {
		//System.out.println(activeCells);
		ArrayList<Cell> newACells = new ArrayList<Cell>();
		for (Cell cell : activeCells) {
			if (cell != null) newACells.addAll(cell.update()); 
		}
		return newACells;
	}

	public Group getGroup() {
		return myGroup; 
	}

	public int getWidth() {
		return myWidth;
	}
	
	public double getWidthInPixels() {
		return myWidth * Cell.CELLSIZE;
	}

	public int getHeight() {
		return myHeight; 
	}
	
	public double getHeightInPixels() {
		return myHeight * Cell.CELLSIZE;
	}

}
