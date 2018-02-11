/**
 * Creates Grid of randomized initial configuration, rather than parsed from XML. 
 * 
 * Depends on Grid class. 
 * 
 * Use by initializing with user simulation selection
 * 		after receiving from another simulation selection class (in this case, SplashScreen).  
 * 
 * @author Susie Choi
 */

package cellsociety_team17;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class RandomizedInitConfig {

	public static final int DEFAULT_GRID_SIZE = 10;
	public static final String DEFAULT_SIMULATION_PATH = "cellsociety_team17.";
	public static final String DEFAULT_SIMULATION = "SegregationCell";
	public static final String DEFAULT_NEIGHBOR = "D";
	public static final boolean DEFAULT_TOROIDAL = false; 
	public static final ArrayList<String> TWO_STATE_CELLS = new ArrayList<String>(Arrays.asList("GameOfLifeCell")); 
	public static final ArrayList<String> THREE_STATE_CELLS = new ArrayList<String>(Arrays.asList("FireCell", "SegregationCell", "WatorCell")); 

	private int myGridSize; 
	private String mySimulationType;
	private Grid myGrid; 
	private ArrayList<Cell> myCells; 
	private ArrayList<Cell> myActiveCells; 

	/**
	 * No arg constructor calls full-arg constructor 
	 * using default values for simulation, neighborhood type/shape, and toroidality
	 */
	public RandomizedInitConfig() {
		this(DEFAULT_SIMULATION, DEFAULT_NEIGHBOR, DEFAULT_TOROIDAL);
	}
	
	/**
	 * 1 arg constructor receives simulationType to call full-arg constructor 
	 * using default values for neighborhood type/shape, and toroidality
	 */
	public RandomizedInitConfig(String mySimulationType) {
		this(mySimulationType, DEFAULT_NEIGHBOR, DEFAULT_TOROIDAL);
	}

	/**
	 * Creates RandomizedInitConfig object with a Grid of the specified simulation type, 
	 * ensuring that Grid is set with specified neighborhood type and toroidality. 
	 * @param simulationType: Which simulation user has opted to view.
	 * @param neighborSelection: Which neighborhood type/shape user has selected for Cells of simulation.
	 * @param toroidalSelection: Whether or not Grid is toroidal. 
	 */
	public RandomizedInitConfig(String simulationType, String neighborSelection, boolean toroidalSelection) {		
		myGridSize = DEFAULT_GRID_SIZE;
		mySimulationType = simulationType; 
		myCells = new ArrayList<Cell>();
		myActiveCells = new ArrayList<Cell>();
		generateCells();
		myGrid = new Grid(myGridSize, myGridSize, myCells, neighborSelection, toroidalSelection);
	}

	private void generateCells() {
		Class<?> simulationClass = null;

		try {
			simulationClass = Class.forName(DEFAULT_SIMULATION_PATH+mySimulationType);
		} catch (ClassNotFoundException e) {
			System.out.println("Simulation type named "+mySimulationType+" cannot be found.");
		}

		Constructor<?> simulationCellConstructor = null;
		try {
			simulationCellConstructor = simulationClass.getConstructor(int.class, int.class, int.class);
		} catch (NoSuchMethodException e) {
			System.out.println("Constructor for cell of simulation "+mySimulationType+" cannot found.");
		} catch (SecurityException e) {
			System.out.println("Security exception: may not access constructor for cell of simulation "+mySimulationType);
		}

		for (int row=0; row<myGridSize; row++) {
			for (int col=0; col<myGridSize; col++) {
				Random randomStateGen = new Random();
				int randomState = -1;
				if (TWO_STATE_CELLS.contains(mySimulationType)) {
					randomState = randomStateGen.nextInt(2);
				}
				else if (THREE_STATE_CELLS.contains(mySimulationType)) {
					randomState = randomStateGen.nextInt(3);
				}
				Object simulationCellObj = null;
				
				try {
					simulationCellObj = simulationCellConstructor.newInstance(row, col, randomState);
				} 
				catch (InstantiationException e) {
					System.out.println("Cannot instantiate Cell obj of simulation "+mySimulationType);
				} catch (IllegalAccessException e) {
					System.out.println("Cannot access constructor for Cell obj of simulation "+mySimulationType);
				} catch (IllegalArgumentException e) {
					System.out.println("Invalid argument to .newInstance of Cell obj of simulation "+mySimulationType);
				} catch (InvocationTargetException e) {
					System.out.println("Constructor for Cell obj of simulation "+mySimulationType+" found, and arguments passed, but threw exception.");
					e.printStackTrace();
				}
				
				myCells.add((Cell) simulationCellObj);
				if (randomState != 0) {
					myActiveCells.add((Cell) simulationCellObj);
				}
			}
		}
	}
	
	/**
	 * Returns Grid for placing in view where user can watch and control simulation 
	 * 		(in this project, SimulationView)
	 * @return Grid with randomized initial configuration
	 */
	public Grid getGrid() {
		return myGrid;
	}
	
	/**
	 * Returns List of Cells that are active, i.e. need to be checked for updates. 
	 * Useful for determining which Cells to check in each step of simulation.
	 * @return List of Cells held in randomized Grid
	 */
	public List<Cell> getActiveCells() {
		return myActiveCells;
	}

}
