/**
 * Subclass of Cell specific to the segregation simulation, i.e. Schelling's Model of SEgregation. 
 * 
 * Put to use by initializing many SegregationCells in a structure that holds Cells (in this project, Grid)
 * 		and calling update on them at each time step so that they interact with one another (i.e. with their neighbors). 
 * 
 * Note that the key behavior-definer is the "threshold," which represents at what point Cells are satisfied 
 * 		with the segregation around them, i.e. if a Cell has less than threshold proportion of non-empty neighbors
 * 		who are of their same segregation group, that Cell will "move" to an empty spot in the Grid. 
 * 
 * @author Susie Choi
 */

package cellsociety_team17;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.CopyOnWriteArrayList;

import cellsociety_team17.Cell;

public class SegregationCell extends Cell {

	public static final double DEFAULT_THRESHOLD = 0.5;

	private double myThreshold;
	private List<Cell> myNonEmptyNeighbors;

	/**
	 * 3 arg-constructor calls full-arg constructor,
	 * using default threshold value.
	 */
	public SegregationCell(int row, int col, int startState) {
		super(row, col, startState);
		myThreshold = (float) DEFAULT_THRESHOLD;
		this.updateColor();
		myNonEmptyNeighbors = new ArrayList<Cell>();
	}

	/**
	 * Creates SegregationCell with specified row and column in Grid,
	 * 	initial state (i.e. which segregated group the Cell belongs to, 
	 * 	or whether the Cell is empty), and segregation threshold that 
	 * 	defines when Cells are un/satisfied and do/n't need to move
	 * @param row - SegregationCell's row in Grid
	 * @param col - SegregationCell's col in Grid
	 * @param startState - indicates which group SegregationCell belongs to,
	 * 						or alternatively, whether SegregationCell is empty
	 * @param threshold - segregation/satisfaction threshold
	 */
	public SegregationCell(int row, int col, int startState, double threshold) {
		super(row, col, startState);
		myThreshold = threshold;
		this.updateColor();
		myNonEmptyNeighbors = new ArrayList<Cell>();
	}

	/**
	 * Sets neighbors of SegregationCell.
	 * 
	 * Intended to be used with NeighborhoodMakers in Grid class,
	 * 		 which specify different neighbors under different neighborhood type/shape selections.
	 * Custom neighbors can then be easily applied through this method. 
	 * 
	 * @param neighbors: SegregationCell neighbors of this SegregationCell
	 */
	@Override
	public void setNeighbors(List<Cell> neighbors) {
		setMyNeighbors(neighbors);
	}

	/**
	 * SegregationCell checks neighbors around it to determine whether
	 * 		a move to an empty space is necessary. 
	 * Note that empty neighbors are not counted when determining whether the Cell
	 * 		is in a place that satisfies the segregation threshold.
	 * 
	 * @return List containing activeCell to track: 
	 * 		in the case of a move, this will be the old empty-state SegregationCell; 
	 * 		in the case of no move, it will be this SegregationCell.
	 */
	@Override
	public List<Cell> update() {
		Cell newACell = null;
		if (this.needToMove() && !myNonEmptyNeighbors.isEmpty()) {
			newACell = this.moveToEmptySpace();
		}
		else {
			newACell = this;
		}
		ArrayList<Cell> newACellList = new ArrayList<>();
		newACellList.add(newACell);
		return newACellList;
	}

	private boolean needToMove() {
		int neighborsLikeMe = 0;
		for (Cell neighbor : getMyNeighbors()) {
			if (neighbor.getMyState() != 0) {
				if (neighbor.getMyState() == this.getMyState()) {
					neighborsLikeMe++;
				}
				myNonEmptyNeighbors.add(neighbor);
			}
		}
		return (myNonEmptyNeighbors.isEmpty() || (neighborsLikeMe / myNonEmptyNeighbors.size()) < myThreshold);
	}

	private Cell moveToEmptySpace() {
		CopyOnWriteArrayList<Cell> possEmptySpots = new CopyOnWriteArrayList<Cell>();
		possEmptySpots.addAll(getMyNeighbors());
		Random randomGen = new Random();
		while (true) {
			int randomIndex = randomGen.nextInt(possEmptySpots.size());
			Cell possEmptySpot = possEmptySpots.get(randomIndex);
			if (possEmptySpot.getMyState() == 0) {
				possEmptySpot.setMyState(this.getMyState());
				this.setMyState(0);
				this.updateColor();
				possEmptySpot.updateColor();
				return possEmptySpot;
			}
			possEmptySpots.addAll(possEmptySpot.getMyNeighbors());
		}
	}

	@Override
	protected void updateColor() {
		this.getMyShape().setFill(STATE_COLORS[this.getMyState()]);
	}

//	public void setProbability(Float mThreshold) {
//		myThreshold = mThreshold;
//	}

}