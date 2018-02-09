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
	public static final ArrayList<String> TWO_STATE_CELLS = new ArrayList<String>(Arrays.asList("GameOfLifeCell")); 
	public static final ArrayList<String> THREE_STATE_CELLS = new ArrayList<String>(Arrays.asList("FireCell", "SegregationCell", "WatorCell")); 

	private int myGridSize; 
	private String mySimulationType;
	private Grid myGrid; 
	private ArrayList<Cell> myCells; 
	private ArrayList<Cell> myActiveCells; 

	public RandomizedInitConfig() {
		this(DEFAULT_GRID_SIZE, DEFAULT_SIMULATION);
	}
	
	public RandomizedInitConfig(String mySimulationType) {
		this(DEFAULT_GRID_SIZE, mySimulationType);
	}

	public RandomizedInitConfig(int gridSize, String simulationType) {		
		myGridSize = gridSize;
		mySimulationType = simulationType; 
		myCells = new ArrayList<Cell>();
		myActiveCells = new ArrayList<Cell>();
		generateCells();
		myGrid = new Grid(DEFAULT_GRID_SIZE, DEFAULT_GRID_SIZE, myCells);
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
	
	public Grid getGrid() {
		return myGrid;
	}
	
	public List<Cell> getCells() {
		return myCells;
	}
	
	public List<Cell> getActiveCells() {
		return myActiveCells;
	}

}
