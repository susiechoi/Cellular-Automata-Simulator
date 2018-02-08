package cellsociety_team17;

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
	public static final String NEIGHBORHOOD_MAKER_CLASS_NAME = "cellsociety_team17.Grid$NeighborhoodMaker";
	public static final String SET_NEIGHBORS_METHOD_NAME = "setNeighbors";

	private int myWidth;
	private int myHeight;
	private Cell[][] myCells; 
	private Group myGroup;
	private boolean myToroidality; 

	private Shape myShapeType;

	public Grid(int width, int height, List<Cell> activeCells) {
		this(width, height, activeCells, DEFAULT_NEIGHBORHOOD_SHAPE, DEFAULT_TOROIDALITY);
	}

	public Grid(int width, int height, List<Cell> activeCells, String neighborhoodShape, boolean toroidal) {
		myWidth = width;
		myHeight = height;
		myCells = new Cell[myHeight][myWidth]; 
		myGroup = new Group();
		myToroidality = toroidal; 
		for (Cell cell : activeCells) {
			myCells[cell.getMyRow()][cell.getMyColumn()] = cell; 
		}
		for (Cell cell : activeCells) {
			setCellNeighbors(cell, neighborhoodShape);
			myGroup.getChildren().add(cell.getMyShape());
		}
	}



	// TODO IMPROVE CATCH BLOCKS
	private void setCellNeighbors(Cell cell, String neighborhoodShape)  { 

		Class<?> classInstance = null; 
		Method method = null; 
		String subclassName = NEIGHBORHOOD_MAKER_CLASS_NAME + neighborhoodShape;
		String methodName = SET_NEIGHBORS_METHOD_NAME;

		// step 1: class
		try {
			classInstance = Class.forName(subclassName);
		}
		catch (ClassNotFoundException e) {
			System.out.println("Class: "+subclassName+" not found.");
		}

		// step 2: method 
		try {
			method = classInstance.getDeclaredMethod(methodName, Cell.class);
		} 
		catch (NoSuchMethodException e) {
			System.out.println("Neighborhood-setting method for that specific neighborhood grouping not found."
					+ "Use again with default neighborhood-setting method.");
		}
		catch (SecurityException e) {
			System.out.println("Permission issue relating to"+classInstance.getName());
		}

		// step 3: method invocation
		try {
			method.invoke(new NeighborhoodMakerD(), cell);
		} 
		catch (IllegalAccessException e) {
			System.out.println("Class "+this.getClass().getName()+" does not have access to "+subclassName);
		} 
		catch (IllegalArgumentException e) {
			System.out.println("Illegal arguments in invoke method.");
		} 
		catch (InvocationTargetException e) {
			System.out.println("Error came from: "+methodName);
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

	private abstract class NeighborhoodMaker {

		private NeighborhoodMaker() {
		}

		abstract void setNeighbors(Cell cell);

	}

	private class NeighborhoodMakerD extends NeighborhoodMaker {

		private NeighborhoodMakerD() {
		}

		@Override
		protected void setNeighbors(Cell cell) {
			ArrayList<Cell> neighbors = new ArrayList<Cell>();
			if (inBounds(cell.getMyRow()-1, cell.getMyColumn())) {
				neighbors.add(myCells[cell.getMyRow()-1][cell.getMyColumn()]);
			}
			if (inBounds(cell.getMyRow()+1, cell.getMyColumn())) {
				neighbors.add(myCells[cell.getMyRow()+1][cell.getMyColumn()]);
			}
			if (inBounds(cell.getMyRow(), cell.getMyColumn()-1)) {
				neighbors.add(myCells[cell.getMyRow()][cell.getMyColumn()-1]);
			}
			if (inBounds(cell.getMyRow(), cell.getMyColumn()+1)) {
				neighbors.add(myCells[cell.getMyRow()][cell.getMyColumn()+1]);
			}
			if (myToroidality) {
				if (cell.getMyColumn() == 0) {
					neighbors.add(myCells[cell.getMyRow()][myWidth-1]);
				}
				else if (cell.getMyColumn() == myWidth-1) {
					neighbors.add(myCells[cell.getMyRow()][0]);
				}
			}
			cell.setNeighbors(neighbors); 
		}
	}

	private class NeighborhoodMakerC extends NeighborhoodMaker {

		private NeighborhoodMakerC() {
		}

		@Override
		protected void setNeighbors(Cell cell) {
			ArrayList<Cell> neighbors = new ArrayList<Cell>();
			if (inBounds(cell.getMyRow()-1, cell.getMyColumn()+1)) {
				neighbors.add(myCells[cell.getMyRow()-1][cell.getMyColumn()+1]);
			}
			if (inBounds(cell.getMyRow()+1, cell.getMyColumn()+1)) {
				neighbors.add(myCells[cell.getMyRow()+1][cell.getMyColumn()+1]);
			}
			if (inBounds(cell.getMyRow()+1, cell.getMyColumn()-1)) {
				neighbors.add(myCells[cell.getMyRow()+1][cell.getMyColumn()-1]);
			}
			if (inBounds(cell.getMyRow()-1, cell.getMyColumn()-1)) {
				neighbors.add(myCells[cell.getMyRow()-1][cell.getMyColumn()-1]);
			}
			//		if (toroidal) {
			//			if (cell.getMyColumn() == 0) {
			//				neighbors.add(myCells[cell.getMyRow()-1][myWidth-1]);
			//				neighbors.add(myCells[cell.getMyRow()+1][myWidth-1]);
			//			}
			//			else if (cell.getMyColumn() == myWidth-1) {
			//				neighbors.add(myCells[cell.getMyRow()-1][0]);
			//				neighbors.add(myCells[cell.getMyRow()+1][0]);
			//			}
			//		}
			cell.setNeighbors(neighbors); 
		}
	}

	private class NeighborhoodMakerZ extends NeighborhoodMaker {

		private NeighborhoodMakerZ() {
		}

		@Override
		protected void setNeighbors(Cell cell) {
			ArrayList<Cell> neighbors = new ArrayList<Cell>();
			if (inBounds(cell.getMyRow()-1, cell.getMyColumn()-1)) {
				neighbors.add(myCells[cell.getMyRow()-1][cell.getMyColumn()-1]);
			}
			if (inBounds(cell.getMyRow()-1, cell.getMyColumn())) {
				neighbors.add(myCells[cell.getMyRow()-1][cell.getMyColumn()]);
			}
			if (inBounds(cell.getMyRow()+1, cell.getMyColumn())) {
				neighbors.add(myCells[cell.getMyRow()+1][cell.getMyColumn()]);
			}
			if (inBounds(cell.getMyRow()+1, cell.getMyColumn()+1)) {
				neighbors.add(myCells[cell.getMyRow()+1][cell.getMyColumn()+1]);
			}
			//		if (toroidal) {
			//			if (cell.getMyColumn() == 0) {
			//				neighbors.add(myCells[cell.getMyRow()-1][myWidth-1]);
			//			} 
			//			else if (cell.getMyColumn() == myWidth-1) {
			//				neighbors.add(myCells[cell.getMyRow()+1][0]);
			//			}
			//		}
			cell.setNeighbors(neighbors); 
		}
	}

	public Shape getShapeType() {
		return myShapeType;
	}

	public void setMyShape(Shape s) {
		myShapeType=s;
	}

}
