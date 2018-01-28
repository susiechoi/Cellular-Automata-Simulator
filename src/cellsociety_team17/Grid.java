package cellsociety_team17;

import java.util.ArrayList;

public class Grid {

	int mySimulationType;
	int myWidth;
	int myHeight;
	Cell[][] myCells; 

	public Grid(int width, int height) {
		this(1, 400, 400, new ArrayList<Cell>()); 
	}

	public Grid(int simType, int width, int height, ArrayList<Cell> activePts) {
		mySimulationType = simType;
		myWidth = width;
		myHeight = height;
		myCells = new Cell[myWidth][myHeight]; 
		// TODO use active points arraylist
	}
	
	private Group getGroup() {

	}
	
	private int getWidth() {
		return myWidth;
	}
	
	private int getHeight() {
		return myHeight; 
	}

}
