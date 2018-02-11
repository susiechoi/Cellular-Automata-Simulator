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
	public static final String DIRECT_NEIGHBORHOOD_INDICATOR = "D";
	public static final String CORNER_NEIGHBORHOOD_INDICATOR = "C";
	public static final String Z_NEIGHBORHOOD_INDICATOR = "Z";
	
	public static final boolean DEFAULT_TOROIDALITY = false;
	public static final String NEIGHBORHOOD_MAKER_CLASS_NAME = "cellsociety_team17.Grid$NeighborhoodMaker";
	public static final String SET_NEIGHBORS_METHOD_NAME = "setNeighbors";

	private int myWidth;
	private int myHeight;
	private Cell[][] myCells;
	private Group myGroup;
	private String myNeighborType; 
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

	private void setCellNeighbors(Cell cell, String neighborhoodShape) {
//		Class<?> classInstance = null;
//		Method method = null;
//		String subclassName = NEIGHBORHOOD_MAKER_CLASS_NAME + neighborhoodShape;
//		String methodName = SET_NEIGHBORS_METHOD_NAME;
//
//		// step 1: class
//		try {
//			classInstance = Class.forName(subclassName);
//		} catch (ClassNotFoundException e) {
//			System.out.println("Class: " + subclassName + " not found.");
//		}
//
//		// step 2: method
//		try {
//			method = classInstance.getDeclaredMethod(methodName, Cell.class);
//		} catch (NoSuchMethodException e) {
//			System.out.println("Neighborhood-setting method for that specific neighborhood grouping not found."
//					+ "Use again with default neighborhood-setting method.");
//		} catch (SecurityException e) {
//			System.out.println("Permission issue relating to" + classInstance.getName());
//		}
//
//		// step 3: method invocation
//		try {
//			method.invoke(new NeighborhoodMakerC(), cell);
//		} catch (IllegalAccessException e) {
//			System.out.println("Class " + this.getClass().getName() + " does not have access to " + subclassName);
//		} catch (IllegalArgumentException e) {
//			System.out.println("Illegal arguments in invoke method.");
//		} catch (InvocationTargetException e) {
//			System.out.println("Error came from: " + methodName);
//		}
		if (neighborhoodShape.equals(DIRECT_NEIGHBORHOOD_INDICATOR)) {
			NeighborhoodMakerDirect dNeighbors = new NeighborhoodMakerDirect();
			dNeighbors.setNeighbors(cell);
		}
		else if (neighborhoodShape.equals(CORNER_NEIGHBORHOOD_INDICATOR)) {
			NeighborhoodMakerCorner cNeighbors = new NeighborhoodMakerCorner();
			cNeighbors.setNeighbors(cell);
		}
		else if (neighborhoodShape.equals(Z_NEIGHBORHOOD_INDICATOR)) {
			NeighborhoodMakerZShape zNeighbors = new NeighborhoodMakerZShape();
			zNeighbors.setNeighbors(cell);
		}
	}
	
	public List<Cell> updateCells(List<Cell> activeCells) {
		List<Cell> newACells = new ArrayList<Cell>();
		for (Cell cell : activeCells) {
			if (cell != null)
				newACells.addAll(cell.update());
		}
		System.out.println(newACells.size());
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
	
	public String getNeighborType() {
		return myNeighborType;
	}
	
	public boolean getToroidal() {
		return myToroidality;
	}

	private abstract class NeighborhoodMaker {

		private NeighborhoodMaker() {
		}
		
		protected boolean inBounds(int row, int col) {
			return (row >= 0 && row < myHeight && col >= 0 && col < myWidth);
		}

		abstract void setNeighbors(Cell cell);
		
		protected boolean inToroidalBounds(int row, int col) {
			return (myToroidality && (row > 0) && (row < myHeight) && ((col < 0) || (col >= myWidth)));
		}

	}

	private class NeighborhoodMakerDirect extends NeighborhoodMaker {
		
		private NeighborhoodMakerDirect() {
		}

		@Override
		protected void setNeighbors(Cell cell) {
			int[] x = {cell.getMyRow()-1, cell.getMyRow()+1, cell.getMyRow(), cell.getMyRow()}; 
			int[] y = {cell.getMyColumn(), cell.getMyColumn(), cell.getMyColumn()-1, cell.getMyColumn()+1};
			
			ArrayList<Cell> neighbors = new ArrayList<Cell>();
			
			int idx = 0; 
			if (inBounds(x[idx], y[idx])) {
				neighbors.add(myCells[x[idx]][y[idx]]);
			} 
			idx++; 
			if (inBounds(x[idx], y[idx])) {
				neighbors.add(myCells[x[idx]][y[idx]]);
			}
			idx++;
			if (inBounds(x[idx], y[idx])) {
				neighbors.add(myCells[x[idx]][y[idx]]);
			}
			else if (inToroidalBounds(x[idx], y[idx])) {
				neighbors.add(myCells[x[idx]][myWidth-1]);
			}
			idx++;
			if (inBounds(x[idx], y[idx])) {
				neighbors.add(myCells[x[idx]][y[idx]]);
			}
			else if (inToroidalBounds(x[idx], y[idx])) {
				neighbors.add(myCells[x[idx]][0]);
			}
			cell.setNeighbors(neighbors);
		}
	}

	private class NeighborhoodMakerCorner extends NeighborhoodMaker {

		private NeighborhoodMakerCorner() {
		}

		@Override
		protected void setNeighbors(Cell cell) {
			int[] x = {cell.getMyRow()-1, cell.getMyRow()+1, cell.getMyRow()+1, cell.getMyRow()-1};
			int[] y = {cell.getMyColumn()+1, cell.getMyColumn()+1, cell.getMyColumn()-1, cell.getMyColumn()-1};
			
			ArrayList<Cell> neighbors = new ArrayList<Cell>();
			
			int idx = 0; 
			if (inBounds(x[idx], y[idx])) {
				neighbors.add(myCells[x[idx]][y[idx]]);
			}
			else if (inToroidalBounds(x[idx], y[idx])) {
				neighbors.add(myCells[x[idx]][0]);
			}
			idx++; 
			if (inBounds(x[idx], y[idx])) {
				neighbors.add(myCells[x[idx]][y[idx]]);
			}
			else if (inToroidalBounds(x[idx],y[idx])) {
				neighbors.add(myCells[x[idx]][0]);
			}
			idx++; 
			if (inBounds(x[idx], y[idx])) {
				neighbors.add(myCells[x[idx]][y[idx]]);
			}
			else if (inToroidalBounds(x[idx], y[idx])) {
				neighbors.add(myCells[x[idx]][0]);
			}
			idx++; 
			if (inBounds(x[idx], y[idx])) {
				neighbors.add(myCells[x[idx]][y[idx]]);
			} 
			else if (inToroidalBounds(x[idx], y[idx])) {
				neighbors.add(myCells[x[idx]][0]);
			}
			cell.setNeighbors(neighbors); 
		}
	}

	private class NeighborhoodMakerZShape extends NeighborhoodMaker {

		private NeighborhoodMakerZShape() {
		}

		@Override
		protected void setNeighbors(Cell cell) {
			int[] x = {cell.getMyRow()-1, cell.getMyRow()-1, cell.getMyRow()+1, cell.getMyRow()+1};
			int[] y = {cell.getMyColumn()-1, cell.getMyColumn(), cell.getMyColumn(), cell.getMyColumn()+1};
			
			ArrayList<Cell> neighbors = new ArrayList<Cell>();
			
			int idx = 0; 
			if (inBounds(x[idx], y[idx])) {
				neighbors.add(myCells[x[idx]][y[idx]]);
			}
			idx++; 
			if (inBounds(x[idx], y[idx])) {
				neighbors.add(myCells[x[idx]][y[idx]]);
			}
			idx++; 
			if (inBounds(x[idx], y[idx])) {
				neighbors.add(myCells[x[idx]][y[idx]]);
			}
			idx++;
			if (inBounds(x[idx], y[idx])) {
				neighbors.add(myCells[x[idx]][y[idx]]);
			}
			idx++; 
			cell.setNeighbors(neighbors); 
		}
	}

	public Shape getShapeType() {
		return myShapeType;
	}

	public void setMyShape(Shape s) {
		myShapeType = s;
	}

}