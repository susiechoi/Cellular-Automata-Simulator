package cellsociety_team17;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

import cellsociety_team17.Cell;
import javafx.scene.Group;
import javafx.scene.shape.Shape;

public class Grid {

	public static final String DEFAULT_NEIGHBORHOOD_SHAPE = "D";
	public static final boolean DEFAULT_TOROIDALITY = true; 
	public static final String NEIGHBORHOOD_METHOD_START = "findNeighbors";

	private int myWidth;
	private int myHeight;
	private Cell[][] myCells; 
	private Group myGroup;
	private Shape myShapeType;

	public Grid(int width, int height, List<Cell> activeCells) {
		this(width, height, activeCells, DEFAULT_NEIGHBORHOOD_SHAPE, DEFAULT_TOROIDALITY);
	}

	public Grid(int width, int height, List<Cell> activeCells, String neighborhoodShape, boolean toroidal) {
		myWidth = width;
		myHeight = height;
		myCells = new Cell[myHeight][myWidth]; 
		myGroup = new Group();
		for (Cell cell : activeCells) {
			myCells[cell.getMyRow()][cell.getMyColumn()] = cell; 
		}
		for (Cell cell : activeCells) {
			setCellNeighbors(cell, neighborhoodShape, toroidal);
			myGroup.getChildren().add(cell.getMyShape());
		}
	}
	
	

	//	private void setCellNeighbors(Cell cell, String neighborhoodShape) {
	//		ArrayList<Cell> inBoundsNeighbors = findInBoundsNeighbors(cell, neighborhoodShape);
	//		cell.setNeighbors(inBoundsNeighbors);
	//	}

	private List<Cell> setCellNeighbors(Cell cell, String neighborhoodShape, boolean toroidal) {
		Method method = null; 
		String methodName = NEIGHBORHOOD_METHOD_START + neighborhoodShape;
		List<Cell> neighbors = new ArrayList<Cell>(); 

		try {
			method = this.getClass().getDeclaredMethod(methodName, Cell.class, boolean.class);
		} // TODO IMPROVE CATCH BLOCKS
		catch (NoSuchMethodException e) {
			System.out.println("Neighborhood-setting method for that specific neighborhood grouping not found."
					+ "Use again with default neighborhood-setting method.");
			//			 e.printStackTrace(); 
		}

		try {
			method.invoke(this, cell, toroidal);
		} 
		catch (IllegalArgumentException e) {
			System.out.println("Specified method found, but specified arguments are illegal.");
			throw e;
			//			e.printStackTrace();
		}
		catch (IllegalAccessException e) {
			System.out.println("Illegal access");
			//			e.printStackTrace();
		}
		catch (InvocationTargetException e) {
			System.out.println("Invocation target exception");
			//			e.printStackTrace();
		}
		return neighbors; 
	}

	// D = direct neighbors (N, S, E, W) 
	private void findNeighborsD(Cell cell, boolean toroidal) {
		List<Cell> neighbors = new ArrayList<Cell>();
		if (inBounds(cell.getMyRow()-1, cell.getMyColumn())) neighbors.add(myCells[cell.getMyRow()-1][cell.getMyColumn()]);
		if (inBounds(cell.getMyRow()+1, cell.getMyColumn())) neighbors.add(myCells[cell.getMyRow()+1][cell.getMyColumn()]);
		if (inBounds(cell.getMyRow(), cell.getMyColumn()-1)) neighbors.add(myCells[cell.getMyRow()][cell.getMyColumn()-1]);
		if (inBounds(cell.getMyRow(), cell.getMyColumn()+1)) neighbors.add(myCells[cell.getMyRow()][cell.getMyColumn()+1]);
		if (toroidal) {
			if (cell.getMyColumn() == 0) neighbors.add(myCells[cell.getMyRow()][myWidth-1]);
			else if (cell.getMyColumn() == myWidth-1) neighbors.add(myCells[cell.getMyRow()][0]);
		}
		cell.setNeighbors(neighbors); 
	}

	// C = corner neighbors (NE, SE, SW, NW) 
	private void findNeighborsC(Cell cell, boolean toroidal) {
		List<Cell> neighbors = new ArrayList<Cell>();
		if (inBounds(cell.getMyRow()-1, cell.getMyColumn()+1)) neighbors.add(myCells[cell.getMyRow()-1][cell.getMyColumn()+1]);
		if (inBounds(cell.getMyRow()+1, cell.getMyColumn()+1)) neighbors.add(myCells[cell.getMyRow()+1][cell.getMyColumn()+1]);
		if (inBounds(cell.getMyRow()+1, cell.getMyColumn()-1)) neighbors.add(myCells[cell.getMyRow()+1][cell.getMyColumn()-1]);
		if (inBounds(cell.getMyRow()-1, cell.getMyColumn()-1)) neighbors.add(myCells[cell.getMyRow()-1][cell.getMyColumn()-1]);
//		if (toroidal) {
//			if (cell.myColumn == 0) {
//				neighbors.add(myCells[cell.myRow-1][myWidth-1]);
//				neighbors.add(myCells[cell.myRow+1][myWidth-1]);
//			}
//			else if (cell.myColumn == myWidth-1) {
//				neighbors.add(myCells[cell.myRow-1][0]);
//				neighbors.add(myCells[cell.myRow+1][0]);
//			}
//		}
		cell.setNeighbors(neighbors); 
	}

	// Z = Z-shaped neighbors (NW, N, S, SE) 
	private void findNeighborsZ(Cell cell, boolean toroidal) {
		List<Cell> neighbors = new ArrayList<Cell>();
		if (inBounds(cell.getMyRow()-1, cell.getMyColumn()-1)) neighbors.add(myCells[cell.getMyRow()-1][cell.getMyColumn()-1]);
		if (inBounds(cell.getMyRow()-1, cell.getMyColumn())) neighbors.add(myCells[cell.getMyRow()-1][cell.getMyColumn()]);
		if (inBounds(cell.getMyRow()+1, cell.getMyColumn())) neighbors.add(myCells[cell.getMyRow()+1][cell.getMyColumn()]);
		if (inBounds(cell.getMyRow()+1, cell.getMyColumn()+1)) neighbors.add(myCells[cell.getMyRow()+1][cell.getMyColumn()+1]);
//		if (toroidal) {
//			if (cell.myColumn == 0) {
//				neighbors.add(myCells[cell.myRow-1][myWidth-1]);
//			} 
//			else if (cell.myColumn == myWidth-1) {
//				neighbors.add(myCells[cell.myRow+1][0]);
//			}
//		}
		cell.setNeighbors(neighbors); 
	}

	private boolean inBounds(int row, int col) {
		return (row >= 0 && row < myHeight && col >= 0 && col < myWidth);
	}

	public List<Cell> updateCells(List<Cell> activeCells) {
		//		System.out.println(activeCells);
		List<Cell> newACells = new ArrayList<Cell>();
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
	
	public Shape getShapeType() {
		return myShapeType;
	}
	
	public void setMyShape(Shape s) {
		myShapeType=s;
	}

	//	private abstract class NeighborhoodMaker {
	//
	//		private NeighborhoodMaker() {
	//
	//		}
	//
	//		abstract List<Cell> getNeighbors(Cell cell); 
	//
	//		private boolean inBounds(int row, int col) {
	//			return (row >= 0 && row < myHeight && col >= 0 && col < myWidth);
	//		}
	//
	//		private class DNeighborhoodMaker extends NeighborhoodMaker {
	//
	//			private DNeighborhoodMaker() {
	//				
	//			}
	//			
	//			@Override
	//			List<Cell> getNeighbors(Cell cell) {
	//				List<Cell> neighbors = new ArrayList<Cell>();
	//				if (inBounds(cell.myRow-1, cell.myColumn)) neighbors.add(myCells[cell.myRow-1][cell.myColumn]);
	//				if (inBounds(cell.myRow+1, cell.myColumn)) neighbors.add(myCells[cell.myRow+1][cell.myColumn]);
	//				if (inBounds(cell.myRow, cell.myColumn-1)) neighbors.add(myCells[cell.myRow][cell.myColumn-1]);
	//				if (inBounds(cell.myRow, cell.myColumn+1)) neighbors.add(myCells[cell.myRow][cell.myColumn+1]);
	//				return neighbors; 
	//			}	
	//		}
	//	}
}
