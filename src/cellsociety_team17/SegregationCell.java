package cellsociety_team17;

public class SegregationCell extends Cell {

	// threshold t of satisfaction w/ segregation
//	public static final float DEFAULT_THRESHOLD = 0.5;
	private float myThreshold;

	public SegregationCell(int row, int col, int startState) {
		super(row, col, startState);
		myThreshold = DEFAULT_THRESHOLD;
	}

	public SegregationCell(int row, int col, int startState, float threshold) {
		super(row, col, startState);
		myThreshold = threshold; 
	}
	

}
