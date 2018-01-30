package cellsociety_team17;

public class SegregationCell extends Cell {

	// threshold t represents satisfaction with segregation 
	// TODO add starting proportions of group 1 and group 2 to XML (all non-assigned states will just become state 0, empty) 
	// TODO in Grid class, add sthg so that if it's the segregation simulation, also the immediate diagonal cells are added
	private float myThreshold;

	//	public SegregationCell(int row, int col, int startState) {
	//		super(row, col, startState);
	//	}

	public SegregationCell(int row, int col, int startState, float threshold) {
		super(row, col, startState);
		myThreshold = threshold; 
	}
	
	public void setMyState(int state) {
		myState = state;
	}
	
	public int getMyState() {
		return myState;
	}
	
	public void setNeighbors(ArrayList<Cell> neighbors) {
		myNeighbors = neighbors; 
	}
	
	public ArrayList<Cell> getNeighbors(){
		return myNeighbors;
	}

	public void updateState() {
		int neighborsLikeMe = 0;
		int nonEmptyNeighbors = -1; 
		for (Cell neighbor : myNeighbors) {
			if (neighbor.myState != 0) {
				if (neighbor.myState = myState) neighborsLikeMe++; 
				nonEmptyNeighbors++; 
			}
		}
		if (neighborsLikeMe / nonEmptyNeighbors > myThreshold) // UPDATE HERE
	}
	
	private boolean needToMove() {
		
	}

}