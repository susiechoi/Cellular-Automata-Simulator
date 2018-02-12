/**
 * Grid to manage simulation-specific Cell objects. 
 * Depends on Cell class. 
 * Use by constructing Grid (which initializes structure to hold Cells), 
 * 		and updating Grid to update Cells in simulation.
 * @author Susie Choi
 */

package cellsociety_team17;

import java.util.ArrayList;
import java.util.List;
import cellsociety_team17.Cell;
import javafx.scene.Group;
import javafx.scene.input.MouseEvent;
import javafx.scene.shape.Shape;

public class Grid {

	public static final String DEFAULT_NEIGHBORHOOD_SHAPE = "D";
	public static final String DIRECT_NEIGHBORHOOD_INDICATOR = "D";
	public static final String CORNER_NEIGHBORHOOD_INDICATOR = "C";
	public static final String Z_NEIGHBORHOOD_INDICATOR = "Z";
	private static final Object T_NEIGHBORHOOD_INDICATOR = "T";
	
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

	/**
	 * Fewer-arg constructor calls greater-arg Grid constructor 
	 * 		using default neighborhood shape and default toroidality (for setting cell neighbors).
	 */
	public Grid(int width, int height, List<Cell> activeCells) {
		this(width, height, activeCells, DEFAULT_NEIGHBORHOOD_SHAPE, DEFAULT_TOROIDALITY);
	}

	/**
	 * Constructs Grid with Cells whose neighbors are set according to a 
	 * 		specified neighborhood shape and toroidality.
	 * @param int width: width of Grid.
	 * @param int height: height of Grid.
	 * @param List<Cell> activeCells: Cells to add to Grid.
	 * @param String neighborhoodShape: shape of neighborhood to set for each Cell 
	 * 		(e.g. direct neighbors vs. corner neighbors).
	 * @param boolean toroidal: whether to connect Cells on ends (which normally have smaller neighborhoods)
	 * 		to neighbors on other side of Grid.
	 */ 
	public Grid(int width, int height, List<Cell> activeCells, String neighborhoodShape, boolean toroidal) {
		myWidth = width;
		myHeight = height;
		myCells = new Cell[myHeight][myWidth];
		myGroup = new Group();
		myToroidality = toroidal;
		myNeighborType = neighborhoodShape;

		for (Cell cell : activeCells) {
			myCells[cell.getMyRow()][cell.getMyColumn()] = cell;
		}
		for (Cell cell : activeCells) {
			setCellNeighbors(cell, neighborhoodShape);
			myGroup.getChildren().add(cell.getMyShape());
			System.out.println(cell.getMyShape().toString());
		}
	}

	private void setCellNeighbors(Cell cell, String neighborhoodShape) {
// DO NOT DELETE COMMENTED CODE
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
		} else if (neighborhoodShape.equals(T_NEIGHBORHOOD_INDICATOR)) {
			NeighborhoodMakerTShape tNeighbors = new NeighborhoodMakerTShape();
			tNeighbors.setNeighbors(cell);
		}
	}
	
	/**
	 * Updates all Cells in Grid, returning new List of Cells that need to be checked in next step. 
	 * @param List<Cell> activeCells: List of Cells that are active (i.e. non-empty or alive) to check updates for.
	 * @return List<Cell> new List of Cells that are active after having updated the Cells in List argument. 
	 */
	public List<Cell> updateCells(List<Cell> activeCells) {
		List<Cell> newACells = new ArrayList<Cell>();
		for (Cell cell : activeCells) {
			if (cell != null)
				newACells.addAll(cell.update());
		}
		//System.out.println(newACells.size());
		return newACells;
	}

	/**
	 * Returns Group whose children are Cells.
	 * Useful for creating screen for viewing and controlling simulation 
	 * 		(in this project, SimulationView).
	 * @return Group containing Cell objects as children.
	 */
	public Group getGroup() {
		return myGroup;
	}

	/**
	 * Returns int width (in number of columns) of Grid object.
	 * Useful for creating screen for viewing and controlling simulation 
	 * 		(in this project, SimulationView). 
	 * @return int width of Grid object in rows.
	 */
	public int getWidth() {
		return myWidth;
	}

	/**
	 * Returns int width in pixels of Grid object.
	 * Useful for creating screen for viewing and controlling simulation 
	 * 		(in this project, SimulationView). 
	 * @return int width of Grid object in pixels. 
	 */
	public double getWidthInPixels() {
		return myWidth * Cell.CELLSIZE;
	}

	/**
	 * Returns int height (in number of rows) of Grid object.
	 * Useful for creating screen for viewing and controlling simulation 
	 * 		(in this project, SimulationView). 
	 * @return int height of Grid object in rows.
	 */
	public int getHeight() {
		return myHeight;
	}

	/**
	 * Returns int height in pixels of Grid object.
	 * Useful for creating screen for viewing and controlling simulation 
	 * 		(in this project, SimulationView).
	 * @return int height of Grid object in pixels. 
	 */
	public double getHeightInPixels() {
		return myHeight * Cell.CELLSIZE;
	}
	
	/**
	 * Returns String indicating type/shape of neighborhoods surrounding each Cell. 
	 * Useful for restarting simulation with same neighborhood shape argument.
	 * @return String indicating neighborhood type/shape.
	 */
	public String getNeighborType() {
		return myNeighborType;
	}
	
	/**
	 * Returns boolean indicating whether Grid is toroidal. 
	 * Useful for restarting simulation with same toroidality seting.
	 * @return boolean indicating whether Grid is toroidal. 
	 */
	public boolean getToroidal() {
		return myToroidality;
	}

	/**
	 * Abstract class to encapsulate setting neighbors in different orientations. 
	 * Useful for specifying neighborhood orientations (e.g. direction neighbors, corner neighbors, Z-shape neighbors). 
	 * Depends on Grid and Cell classes.
	 * To extend, specify in the "setNeighbors" method which neighbors to add, 
	 * 		in both cases of a toroidal and non-toroidal/regular Grid;
	 * Must also add option for newly-created neighborhood-maker to OptionsScreen.java in form of button that user can select;
	 * Finally, must add additional "if" statement to setCellNeighbors method in Grid.java to ensure that the neighborhood-maker is called. 
	 * @author Susie Choi
	 */
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
	
private class NeighborhoodMakerTShape extends NeighborhoodMaker {
		
		private NeighborhoodMakerTShape() {
		}

		@Override
		protected void setNeighbors(Cell cell) {
			int[] x;
			int[] y;
			if(cell.getMyColumn() % 2 == 1) {
				int[] xE = {cell.getMyRow()-1, cell.getMyRow(), cell.getMyRow()}; 
				int[] yE = {cell.getMyColumn()-1, cell.getMyColumn()-1, cell.getMyColumn()+1};
				x = xE;
				y = yE;
			} else {
				int[] xO = { cell.getMyRow()+1, cell.getMyRow(), cell.getMyRow()}; 
				int[] yO = { cell.getMyColumn()+1, cell.getMyColumn()-1, cell.getMyColumn()+1};
				x = xO;
				y = yO;
			}
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

//	public Shape getShapeType() {
//		return myShapeType;
//	}

	/**
	 * Sets Grid units (i.e. manifestation of Cell objects) to specified Shape 
	 * @param Shape s: Shape for Grid units to take on 
	 */
	public void setMyShape(Shape s) {
		myShapeType = s;
	}
	
	public Cell[][] getMyCells(){
		return myCells == null ? null : myCells.clone();
	}

}