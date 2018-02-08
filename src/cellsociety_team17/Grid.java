package cellsociety_team17;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import cellsociety_team17.Cell;
import javafx.scene.Group;

public class Grid {

	public static final String DEFAULT_NEIGHBORHOOD_SHAPE = "C";
	public static final String NEIGHBORHOOD_METHOD_START = "findNeighbors";

	private int myWidth;
	private int myHeight;
	private Cell[][] myCells; 
	private Group myGroup;

	public Grid(int width, int height, ArrayList<Cell> activeCells) {
		this(width, height, activeCells, DEFAULT_NEIGHBORHOOD_SHAPE);
	}

	public Grid(int width, int height, ArrayList<Cell> activeCells, String neighborhoodShape) {
		myWidth = width;
		myHeight = height;
		myCells = new Cell[myHeight][myWidth]; 
		myGroup = new Group();
		for (Cell cell : activeCells) {
			myCells[cell.myRow][cell.myColumn] = cell; 
		}
		for (Cell cell : activeCells) {
			setCellNeighbors(cell, neighborhoodShape);
			myGroup.getChildren().add(cell.myRectangle);
		}
	}

//	private void setCellNeighbors(Cell cell, String neighborhoodShape) {
//		ArrayList<Cell> inBoundsNeighbors = findInBoundsNeighbors(cell, neighborhoodShape);
//		cell.setNeighbors(inBoundsNeighbors);
//	}

	private ArrayList<Cell> setCellNeighbors(Cell cell, String neighborhoodShape) {
		Method method = null; 
		String methodName = NEIGHBORHOOD_METHOD_START + neighborhoodShape;
		ArrayList<Cell> neighbors = new ArrayList<Cell>(); 
		
		try {
			method = this.getClass().getDeclaredMethod(methodName, Cell.class);
		} // TODO IMPROVE CATCH BLOCKS
		catch (SecurityException e) {
			e.printStackTrace();
		} 
		catch (NoSuchMethodException e) {
			e.printStackTrace();
		}
		
		try {
			method.invoke(this, cell);
		} 
		catch (IllegalArgumentException e) {
			e.printStackTrace();
		}
		catch (IllegalAccessException e) {
			e.printStackTrace();
		}
		catch (InvocationTargetException e) {
			e.printStackTrace();
		}
		
		return neighbors; 
	}

	// D = direct neighbors (N, S, E, W) 
	private void findNeighborsD(Cell cell) {
		ArrayList<Cell> neighbors = new ArrayList<Cell>();
		if (inBounds(cell.myRow-1, cell.myColumn)) neighbors.add(myCells[cell.myRow-1][cell.myColumn]);
		if (inBounds(cell.myRow+1, cell.myColumn)) neighbors.add(myCells[cell.myRow+1][cell.myColumn]);
		if (inBounds(cell.myRow, cell.myColumn-1)) neighbors.add(myCells[cell.myRow][cell.myColumn-1]);
		if (inBounds(cell.myRow, cell.myColumn+1)) neighbors.add(myCells[cell.myRow][cell.myColumn+1]);
		cell.setNeighbors(neighbors); 
	}

	// C = corner neighbors (NE, SE, SW, NW) 
	private void findNeighborsC(Cell cell) {
		ArrayList<Cell> neighbors = new ArrayList<Cell>();
		if (inBounds(cell.myRow-1, cell.myColumn+1)) neighbors.add(myCells[cell.myRow-1][cell.myColumn+1]);
		if (inBounds(cell.myRow+1, cell.myColumn+1)) neighbors.add(myCells[cell.myRow+1][cell.myColumn+1]);
		if (inBounds(cell.myRow+1, cell.myColumn-1)) neighbors.add(myCells[cell.myRow+1][cell.myColumn-1]);
		if (inBounds(cell.myRow-1, cell.myColumn-1)) neighbors.add(myCells[cell.myRow-1][cell.myColumn-1]);
		cell.setNeighbors(neighbors); 
	}

	// Z = Z-shaped neighbors (NW, N, S, SE) 
	private void findNeighborsZ(Cell cell) {
		ArrayList<Cell> neighbors = new ArrayList<Cell>();
		if (inBounds(cell.myRow-1, cell.myColumn-1)) neighbors.add(myCells[cell.myRow-1][cell.myColumn-1]);
		if (inBounds(cell.myRow-1, cell.myColumn)) neighbors.add(myCells[cell.myRow-1][cell.myColumn]);
		if (inBounds(cell.myRow+1, cell.myColumn)) neighbors.add(myCells[cell.myRow+1][cell.myColumn]);
		if (inBounds(cell.myRow+1, cell.myColumn+1)) neighbors.add(myCells[cell.myRow+1][cell.myColumn+1]);
		cell.setNeighbors(neighbors); 
	}

	private boolean inBounds(int row, int col) {
		return (row >= 0 && row < myHeight && col >= 0 && col < myWidth);
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

	//	private class NeighborhoodMaker {
	//		
	//		private NeighborhoodMaker(Cell[][] myGrid, Cell cell, String neighborhoodShape) {
	//			
	//		}
	//		
	//	}


}
